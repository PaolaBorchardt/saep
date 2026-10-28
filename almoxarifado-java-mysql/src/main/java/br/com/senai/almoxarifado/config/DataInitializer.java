package br.com.senai.almoxarifado.config;

import br.com.senai.almoxarifado.model.Perfil;
import br.com.senai.almoxarifado.repository.UsuarioRepository;
import br.com.senai.almoxarifado.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
    @Bean CommandLineRunner seed(UsuarioRepository repo, UsuarioService service) {
        return args -> {
            if (!repo.existsByUsername("admin")) service.criar("Administrador","admin","Admin123!",Perfil.ADMIN);
            if (!repo.existsByUsername("operador")) service.criar("Operador do Estoque","operador","Operador123!",Perfil.OPERADOR);
        };
    }
}
