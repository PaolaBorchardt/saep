package com.example;

import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final WarehouseSystem SYSTEM = new WarehouseSystem();
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("SISTEMA DE ALMOXARIFADO");
        System.out.println("========================================");

        while (true) {
            System.out.println("\nMenu principal");
            System.out.println("1 - Entrar como Administrador");
            System.out.println("2 - Entrar como Operador Almoxarifado");
            System.out.println("3 - Visualizar estoque atual");
            System.out.println("4 - Visualizar movimentações");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            String option = SCANNER.nextLine();

            switch (option) {
                case "1" -> openAdminMenu();
                case "2" -> openOperatorMenu();
                case "3" -> showStock();
                case "4" -> showMovements();
                case "0" -> {
                    System.out.println("Sistema encerrado.");
                    return;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private static void openAdminMenu() {
        User admin = SYSTEM.getUser("admin");
        if (admin == null) {
            System.out.println("Administrador não encontrado.");
            return;
        }

        while (true) {
            System.out.println("\nPerfil: Administrador do Sistema");
            System.out.println("1 - Listar produtos");
            System.out.println("2 - Cadastrar novo produto");
            System.out.println("3 - Registrar movimentação");
            System.out.println("4 - Criar usuário");
            System.out.println("5 - Excluir usuário");
            System.out.println("6 - Visualizar movimentações");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");

            String option = SCANNER.nextLine();

            switch (option) {
                case "1" -> showStock();
                case "2" -> registerNewProduct(admin);
                case "3" -> registerMovement(admin);
                case "4" -> createUser(admin);
                case "5" -> deleteUser(admin);
                case "6" -> showMovements();
                case "0" -> { return; }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private static void openOperatorMenu() {
        User operador = SYSTEM.getUser("operador");
        if (operador == null) {
            System.out.println("Operador não encontrado.");
            return;
        }

        while (true) {
            System.out.println("\nPerfil: Operador Almoxarifado");
            System.out.println("1 - Listar produtos");
            System.out.println("2 - Registrar entrada");
            System.out.println("3 - Registrar saída");
            System.out.println("4 - Visualizar movimentações");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");

            String option = SCANNER.nextLine();

            switch (option) {
                case "1" -> showStock();
                case "2" -> registerMovementWithType(operador, MovementType.ENTRADA);
                case "3" -> registerMovementWithType(operador, MovementType.SAIDA);
                case "4" -> showMovements();
                case "0" -> { return; }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private static void showStock() {
        System.out.println("\n=== Estoque atual ===");
        for (Map.Entry<String, Product> entry : SYSTEM.getProducts().entrySet()) {
            Product product = entry.getValue();
            System.out.println(product.getCode() + " - " + product.getName() + " | Saldo: " + product.getQuantity());
        }
    }

    private static void showMovements() {
        System.out.println("\n=== Histórico de movimentações ===");
        SYSTEM.printMovementReport();
    }

    private static void registerNewProduct(User actor) {
        System.out.print("Código do produto: ");
        String code = SCANNER.nextLine();
        System.out.print("Nome do produto: ");
        String name = SCANNER.nextLine();
        System.out.print("Quantidade inicial: ");
        int quantity = Integer.parseInt(SCANNER.nextLine());

        try {
            SYSTEM.addProduct(actor, new Product(code, name, quantity));
            System.out.println("Produto cadastrado com sucesso.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void registerMovement(User actor) {
        System.out.print("Tipo da movimentação (ENTRADA/SAIDA/AJUSTE): ");
        String typeInput = SCANNER.nextLine().trim().toUpperCase();
        System.out.print("Código do produto: ");
        String code = SCANNER.nextLine();
        System.out.print("Quantidade: ");
        int quantity = Integer.parseInt(SCANNER.nextLine());
        System.out.print("Descrição: ");
        String description = SCANNER.nextLine();

        try {
            MovementType type = MovementType.valueOf(typeInput);
            SYSTEM.registerMovement(actor, type, code, quantity, description);
            System.out.println("Movimentação registrada com sucesso.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void registerMovementWithType(User actor, MovementType type) {
        System.out.print("Código do produto: ");
        String code = SCANNER.nextLine();
        System.out.print("Quantidade: ");
        int quantity = Integer.parseInt(SCANNER.nextLine());
        System.out.print("Descrição: ");
        String description = SCANNER.nextLine();

        try {
            SYSTEM.registerMovement(actor, type, code, quantity, description);
            System.out.println("Movimentação de " + type.getDescricao() + " registrada com sucesso.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void createUser(User actor) {
        System.out.print("Username: ");
        String username = SCANNER.nextLine();
        System.out.print("Nome completo: ");
        String name = SCANNER.nextLine();
        System.out.print("Perfil (OPERADOR/ADMINISTRADOR): ");
        String roleInput = SCANNER.nextLine().trim().toUpperCase();

        try {
            UserRole role = UserRole.valueOf(roleInput);
            SYSTEM.createUser(actor, new User(username, name, role));
            System.out.println("Usuário cadastrado com sucesso.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void deleteUser(User actor) {
        System.out.print("Username do usuário a ser removido: ");
        String username = SCANNER.nextLine();

        try {
            SYSTEM.deleteUser(actor, username);
            System.out.println("Usuário removido com sucesso.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}