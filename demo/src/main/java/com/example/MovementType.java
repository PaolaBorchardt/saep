package com.example;

public enum MovementType {
    ENTRADA("Entrada"),
    SAIDA("Saída"),
    AJUSTE("Ajuste");

    private final String descricao;

    MovementType(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
