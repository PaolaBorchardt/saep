package br.com.senai.almoxarifado.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="usuarios", uniqueConstraints=@UniqueConstraint(columnNames="username"))
public class Usuario {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false, length=80) private String nome;
    @Column(nullable=false, length=60) private String username;
    @Column(nullable=false) private String senha;
    @Enumerated(EnumType.STRING) @Column(nullable=false, length=20) private Perfil perfil;
    @Column(nullable=false) private boolean ativo=true;
    @Column(nullable=false) private LocalDateTime criadoEm=LocalDateTime.now();

    public Long getId(){return id;} public String getNome(){return nome;} public void setNome(String v){nome=v;}
    public String getUsername(){return username;} public void setUsername(String v){username=v;}
    public String getSenha(){return senha;} public void setSenha(String v){senha=v;}
    public Perfil getPerfil(){return perfil;} public void setPerfil(Perfil v){perfil=v;}
    public boolean isAtivo(){return ativo;} public void setAtivo(boolean v){ativo=v;}
    public LocalDateTime getCriadoEm(){return criadoEm;}
}
