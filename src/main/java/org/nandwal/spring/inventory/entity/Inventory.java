package org.nandwal.spring.inventory.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    @Id
    private String itemId;
    private int quantity;
    private String itemName;
    private String itemType;
}
