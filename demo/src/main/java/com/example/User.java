package com.example;

public class User {
    private final String username;
    private final String name;
    private final UserRole role;

    public User(String username, String name, UserRole role) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username não pode ser vazio.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nome do usuário não pode ser vazio.");
        }
        if (role == null) {
            throw new IllegalArgumentException("Perfil do usuário não pode ser nulo.");
        }

        this.username = username;
        this.name = name;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public UserRole getRole() {
        return role;
    }

    @Override
    public String toString() {
        return name + " (" + role + ")";
    }
}
