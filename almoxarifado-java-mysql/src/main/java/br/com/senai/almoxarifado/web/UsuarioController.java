package br.com.senai.almoxarifado.web;

import br.com.senai.almoxarifado.model.*;
import br.com.senai.almoxarifado.service.UsuarioService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController @RequestMapping("/api/usuarios") @PreAuthorize("hasRole('ADMIN')")
public class UsuarioController {
    private final UsuarioService service;
    public UsuarioController(UsuarioService service){this.service=service;}
    public record UsuarioRequest(@NotBlank @Size(max=80) String nome,@NotBlank @Size(max=60) String username,@NotBlank @Size(min=8) String senha,@NotNull Perfil perfil){}
    public record UsuarioResponse(Long id,String nome,String username,Perfil perfil,boolean ativo,LocalDateTime criadoEm){}
    @PostMapping public UsuarioResponse criar(@Valid @RequestBody UsuarioRequest r){return dto(service.criar(r.nome(),r.username(),r.senha(),r.perfil()));}
    @GetMapping public List<UsuarioResponse> listar(){return service.listar().stream().map(UsuarioController::dto).toList();}
    private static UsuarioResponse dto(Usuario u){return new UsuarioResponse(u.getId(),u.getNome(),u.getUsername(),u.getPerfil(),u.isAtivo(),u.getCriadoEm());}
}
