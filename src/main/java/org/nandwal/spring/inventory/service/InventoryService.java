package org.nandwal.spring.inventory.service;

import org.nandwal.spring.inventory.entity.Inventory;
import org.nandwal.spring.inventory.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public List<Inventory> getInventory() {
        List<Inventory> inventory = new ArrayList<>();
        inventoryRepository.findAll().forEach(inventory::add);
        return inventory;
    }

    public Inventory getInventoryById(String id) {
        return inventoryRepository.findById(id).orElse(null);
    }

    public void saveInventory(Inventory inventory) {
        inventoryRepository.save(inventory);
    }

    public void updateInventory(Inventory inventory) {
        inventoryRepository.save(inventory);
    }


    public void deleteInventory(String id) {
        inventoryRepository.deleteById(id);
    }
}
