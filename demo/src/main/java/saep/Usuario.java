package saep;

public class Usuario {
     private String nome;
     private String login;
     private String senha;
     private PerfilUsuario perfil;

     public Usuario(String nome, String login, String senha) {
         this(nome, login, senha, PerfilUsuario.OPERADOR_ALMOXARIFADO);
     }

     public Usuario(String nome, String login, String senha, PerfilUsuario perfil) {
         this.nome = nome;
         this.login = login;
         this.senha = senha;
         this.perfil = perfil == null ? PerfilUsuario.OPERADOR_ALMOXARIFADO : perfil;
     }

     public String getNome() {
         return nome;
     }

     public String getLogin() {
         return login;
     }

     public String getSenha() {
         return senha;
     }

     public PerfilUsuario getPerfil() {
         return perfil;
     }

     public void setPerfil(PerfilUsuario perfil) {
         this.perfil = perfil == null ? PerfilUsuario.OPERADOR_ALMOXARIFADO : perfil;
     }
}
