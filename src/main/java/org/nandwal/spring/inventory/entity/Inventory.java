package org.nandwal.spring.inventory.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orders_table")
public class Inventory {
    @Id
    private String itemId;
    private int quantity;
    private String itemName;
    private String itemType;

    public Inventory() {
    }

    public Inventory(String itemId, int quantity, String itemName, String itemType) {
        super();
        this.itemId = itemId;
        this.quantity = quantity;
        this.itemName = itemName;
        this.itemType = itemType;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
}
