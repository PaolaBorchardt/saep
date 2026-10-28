package com.example;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseSystemTest {

    @Test
    void shouldUpdateStockAndRegisterMovement() {
        WarehouseSystem system = new WarehouseSystem();
        User operador = new User("operador1", "Operador Almoxarifado", UserRole.OPERADOR);
        Product produto = new Product("P1", "Parafuso", 100);

        system.registerUser(operador);
        system.addProduct(produto);

        system.registerMovement(operador, MovementType.ENTRADA, "P1", 50, "Reposição de estoque");

        assertEquals(150, system.getStock("P1"));
        assertEquals(1, system.getMovements().size());
        assertEquals("operador1", system.getMovements().get(0).getUser().getUsername());
        assertNotNull(system.getMovements().get(0).getTimestamp());
    }

    @Test
    void shouldRejectUnauthorizedOperationForOperator() {
        WarehouseSystem system = new WarehouseSystem();
        User operador = new User("operador1", "Operador Almoxarifado", UserRole.OPERADOR);
        User admin = new User("admin", "Administrador do Sistema", UserRole.ADMINISTRADOR);

        system.registerUser(operador);
        system.registerUser(admin);
        system.addProduct(new Product("P2", "Porca", 20));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                system.registerMovement(operador, MovementType.AJUSTE, "P2", 10, "Ajuste manual")
        );

        assertTrue(exception.getMessage().contains("permissão"));
    }

    @Test
    void shouldAllowAdminToCreateAndDeleteUsers() {
        WarehouseSystem system = new WarehouseSystem();
        User admin = new User("admin", "Administrador do Sistema", UserRole.ADMINISTRADOR);
        system.registerUser(admin);

        User operador = new User("operador2", "Operador Almoxarifado", UserRole.OPERADOR);
        system.createUser(admin, operador);

        assertTrue(system.getUsers().containsKey("operador2"));

        system.deleteUser(admin, "operador2");
        assertFalse(system.getUsers().containsKey("operador2"));
    }
}
