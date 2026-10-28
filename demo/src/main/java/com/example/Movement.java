package com.example;

import java.time.LocalDateTime;

public class Movement {
    private final User user;
    private final MovementType type;
    private final Product product;
    private final int quantity;
    private final int stockBefore;
    private final int stockAfter;
    private final String description;
    private final LocalDateTime timestamp;

    public Movement(User user, MovementType type, Product product, int quantity,
                    int stockBefore, int stockAfter, String description, LocalDateTime timestamp) {
        this.user = user;
        this.type = type;
        this.product = product;
        this.quantity = quantity;
        this.stockBefore = stockBefore;
        this.stockAfter = stockAfter;
        this.description = description;
        this.timestamp = timestamp;
    }

    public User getUser() {
        return user;
    }

    public MovementType getType() {
        return type;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getStockBefore() {
        return stockBefore;
    }

    public int getStockAfter() {
        return stockAfter;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return timestamp + " | " + user.getUsername() + " | " + type.getDescricao() +
                " | " + product.getCode() + " | Qtde: " + quantity +
                " | Antes: " + stockBefore + " | Depois: " + stockAfter +
                " | Desc: " + description;
    }
}
