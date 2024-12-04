package org.nandwal.spring.inventory.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.nandwal.spring.inventory.entity.Inventory;
import org.nandwal.spring.inventory.entity.Order;
import org.nandwal.spring.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
@Slf4j
public class OrderListener {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private InventoryService inventoryService;

    private Boolean isInventoryAvailable(Order order, Inventory inventory) {
        return inventory != null && inventory.getQuantity() >= order.getQuantity();
    }

    @Bean
    public Function<Order, Order> receiveOrderDetailsAndPublish() {
        return order -> {
            try {
                Inventory inventory = inventoryService.getInventoryById(order.getItemId());
                Boolean isInventoryAvailable = isInventoryAvailable(order, inventory);
                if (isInventoryAvailable) {
                    order.setStatus("confirmed");
                    inventory.setQuantity(inventory.getQuantity() - order.getQuantity());
                    inventoryService.saveInventory(inventory);
                } else {
                    order.setStatus("failed");
                }

                log.info("Stock availability for item id: " + order.getItemId() + " --> " + isInventoryAvailable);
                return order; // Pass the updated order to the next binding
            } catch (Exception e) {
                log.error("Error processing order: " + e.getMessage());
                throw new RuntimeException(e);
            }
        };
    }
}