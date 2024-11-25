package org.nandwal.spring.inventory.repository;

import org.nandwal.spring.inventory.entity.Inventory;
import org.springframework.data.repository.CrudRepository;

public interface InventoryRepository extends CrudRepository<Inventory, String> {
}
