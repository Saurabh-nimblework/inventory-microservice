package org.nandwal.spring.inventory.kafka;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Supplier;

@Configuration
public class InventoryProducerConfig {

    private final BlockingQueue<String> inventoryQueue = new LinkedBlockingQueue<>();

    // Supplier to send inventory updates to a topic
    @Bean
    public Supplier<String> sendInventoryUpdates() {
        return () -> inventoryQueue.poll(); // Sends the next message from the queue
    }

    // Method to publish an inventory update
    public void publishInventory(String message) {
        inventoryQueue.offer(message);
    }
}
