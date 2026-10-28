package br.com.senai.almoxarifado.service;

import br.com.senai.almoxarifado.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsService implements UserDetailsService {
    private final UsuarioRepository repo;
    public UsuarioDetailsService(UsuarioRepository repo){this.repo=repo;}
    @Override public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var u=repo.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("Usuário não encontrado"));
        return User.withUsername(u.getUsername()).password(u.getSenha()).roles(u.getPerfil().name())
                .disabled(!u.isAtivo()).build();
    }
}
