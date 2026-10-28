package com.example;

public class Product {
    private final String code;
    private final String name;
    private int quantity;

    public Product(String code, String name, int quantity) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Código do produto não pode ser vazio.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nome do produto não pode ser vazio.");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantidade inicial não pode ser negativa.");
        }

        this.code = code;
        this.name = name;
        this.quantity = quantity;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantidade final não pode ser negativa.");
        }
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return code + " - " + name + " (Saldo: " + quantity + ")";
    }
}
