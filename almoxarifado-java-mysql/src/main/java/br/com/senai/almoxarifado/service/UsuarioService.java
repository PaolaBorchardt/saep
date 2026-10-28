package br.com.senai.almoxarifado.service;

import br.com.senai.almoxarifado.model.*;
import br.com.senai.almoxarifado.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository repo; private final PasswordEncoder encoder;
    public UsuarioService(UsuarioRepository repo, PasswordEncoder encoder){this.repo=repo;this.encoder=encoder;}
    @Transactional public Usuario criar(String nome,String username,String senha,Perfil perfil){
        if(repo.existsByUsername(username)) throw new IllegalArgumentException("Nome de usuário já cadastrado.");
        Usuario u=new Usuario(); u.setNome(nome);u.setUsername(username);u.setSenha(encoder.encode(senha));u.setPerfil(perfil);return repo.save(u);
    }
    public List<Usuario> listar(){return repo.findAll();}
}
