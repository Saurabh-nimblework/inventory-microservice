package org.nandwal.spring.inventory.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.nandwal.spring.inventory.entity.Inventory;
import org.nandwal.spring.inventory.service.InventoryService;
import org.nandwal.spring.inventory.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class OrderListener {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final InventoryProducerConfig inventoryProducerConfig;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    public OrderListener(InventoryProducerConfig inventoryProducerConfig) {
        this.inventoryProducerConfig = inventoryProducerConfig;
    }

    private Boolean isInventoryAvailable(Order order, Inventory inventory) {
        return inventory != null && inventory.getQuantity() >= order.getQuantity();
    }

    @Bean
    public Consumer<Order> receiveOrderDetails() {
        return order -> {
            try {
//                Order order = objectMapper.readValue(message, Order.class);
                Inventory inventory = inventoryService.getInventoryById(order.getItemId());
                Boolean isInventoryAvailable = isInventoryAvailable(order, inventory);
                if(isInventoryAvailable) {
                    order.setStatus("confirmed");
                    inventory.setQuantity(inventory.getQuantity()- order.getQuantity());
                    inventoryService.saveInventory(inventory);
                }
                else {
                    order.setStatus("failed");
                }

                String orderStatusUpdatedJson = objectMapper.writeValueAsString(order);
                inventoryProducerConfig.publishInventory(orderStatusUpdatedJson);
                log.info("Stock availability for item id: " + order.getItemId() + " --> " + isInventoryAvailable);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}