package com.example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WarehouseSystem {
    private final Map<String, User> users = new HashMap<>();
    private final Map<String, Product> products = new HashMap<>();
    private final List<Movement> movements = new ArrayList<>();

    public WarehouseSystem() {
        registerUser(new User("admin", "Administrador do Sistema", UserRole.ADMINISTRADOR));
        registerUser(new User("operador", "Operador Almoxarifado", UserRole.OPERADOR));
        addProduct(new Product("P001", "Parafuso", 100));
        addProduct(new Product("P002", "Porca", 80));
        addProduct(new Product("P003", "Fita Isolante", 50));
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public Map<String, User> getUsers() {
        return Collections.unmodifiableMap(users);
    }

    public Map<String, Product> getProducts() {
        return Collections.unmodifiableMap(products);
    }

    public List<Movement> getMovements() {
        return Collections.unmodifiableList(movements);
    }

    public void registerUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Usuário não pode ser nulo.");
        }
        users.put(user.getUsername(), user);
    }

    public void createUser(User actor, User newUser) {
        validateActor(actor);
        requirePermission(actor, "criar usuários");
        registerUser(newUser);
    }

    public void deleteUser(User actor, String username) {
        validateActor(actor);
        requirePermission(actor, "remover usuários");
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Usuário para remoção não informado.");
        }
        if ("admin".equalsIgnoreCase(username)) {
            throw new IllegalArgumentException("O usuário administrador principal não pode ser removido.");
        }
        users.remove(username);
    }

    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Produto não pode ser nulo.");
        }
        products.put(product.getCode(), product);
    }

    public void addProduct(User actor, Product product) {
        validateActor(actor);
        requirePermission(actor, "cadastrar produtos");
        addProduct(product);
    }

    public int getStock(String productCode) {
        Product product = products.get(productCode);
        if (product == null) {
            throw new IllegalArgumentException("Produto não encontrado: " + productCode);
        }
        return product.getQuantity();
    }

    public void registerMovement(User actor, MovementType type, String productCode, int quantity, String description) {
        validateActor(actor);

        if (type == null) {
            throw new IllegalArgumentException("Tipo de movimentação inválido.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantidade da movimentação deve ser maior que zero.");
        }
        if (productCode == null || productCode.isBlank()) {
            throw new IllegalArgumentException("Código do produto não informado.");
        }
        if (description == null || description.isBlank()) {
            description = "Sem descrição";
        }

        if (!canExecuteMovement(actor, type)) {
            throw new IllegalArgumentException("Usuário " + actor.getUsername() + " não possui permissão para " + type.getDescricao() + ".");
        }

        Product product = products.get(productCode);
        if (product == null) {
            throw new IllegalArgumentException("Produto não encontrado: " + productCode);
        }

        int before = product.getQuantity();
        int after = before;

        switch (type) {
            case ENTRADA -> after = before + quantity;
            case SAIDA -> {
                if (before < quantity) {
                    throw new IllegalArgumentException("Estoque insuficiente para a saída do produto " + product.getName() + ".");
                }
                after = before - quantity;
            }
            case AJUSTE -> after = before + quantity;
            default -> throw new IllegalArgumentException("Tipo de movimentação não suportado: " + type);
        }

        product.setQuantity(after);

        Movement movement = new Movement(
                actor,
                type,
                product,
                quantity,
                before,
                after,
                description,
                LocalDateTime.now()
        );

        movements.add(movement);
    }

    public void printMovementReport() {
        if (movements.isEmpty()) {
            System.out.println("Nenhuma movimentação registrada até o momento.");
            return;
        }

        for (Movement movement : movements) {
            System.out.println(movement);
        }
    }

    private boolean canExecuteMovement(User actor, MovementType type) {
        if (actor == null || actor.getRole() == null) {
            return false;
        }

        if (actor.getRole() == UserRole.ADMINISTRADOR) {
            return true;
        }

        return type == MovementType.ENTRADA || type == MovementType.SAIDA;
    }

    private void validateActor(User actor) {
        if (actor == null || actor.getUsername() == null || !users.containsKey(actor.getUsername())) {
            throw new IllegalArgumentException("Usuário não autenticado ou não cadastrado no sistema.");
        }
    }

    private void requirePermission(User actor, String action) {
        if (actor.getRole() != UserRole.ADMINISTRADOR) {
            throw new IllegalArgumentException("Usuário " + actor.getUsername() + " não tem permissão para " + action + ".");
        }
    }
}
