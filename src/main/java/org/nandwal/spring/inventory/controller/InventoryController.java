package org.nandwal.spring.inventory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.nandwal.spring.inventory.entity.Inventory;
import org.nandwal.spring.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    private final ObjectMapper objectMapper;

    @Autowired
    public InventoryController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @GetMapping("/all")
    public List<Inventory> getInventory() {
        return inventoryService.getInventory();
    }

    @GetMapping("/{id}")
    public Inventory getInventoryById(@PathVariable("id") String itemId) {
        return inventoryService.getInventoryById(itemId);
    }

    @PostMapping("/all")
    public void saveInventory(@RequestBody Inventory inventory) {
        try {
//            String inventoryJson = objectMapper.writeValueAsString(inventory);
//            kafkaProducerConfig.publish(inventoryJson);
            inventoryService.saveInventory(inventory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PutMapping("/{id}")
    public void updateInventory(@RequestBody Inventory inventory) {
        inventoryService.updateInventory(inventory);
    }

    @DeleteMapping("/{id}")
    public void deleteInventory(@PathVariable String id) {
        inventoryService.deleteInventory(id);
    }


}
