package saep;

import java.util.ArrayList;
import java.util.List;

public class CadastroUsuario {
    private static final List<Usuario> usuarios = new ArrayList<>();
    private static Usuario usuarioLogado;

    public static boolean cadastrarUsuario(Usuario usuario) {
        if (usuario == null || usuario.getLogin() == null || usuario.getLogin().trim().isEmpty()) {
            return false;
        }

        for (Usuario existente : usuarios) {
            if (existente.getLogin().equalsIgnoreCase(usuario.getLogin().trim())) {
                return false;
            }
        }

        usuarios.add(usuario);
        return true;
    }

    public static boolean autenticar(String login, String senha) {
        if (login == null || senha == null) {
            return false;
        }

        String loginNormalizado = login.trim();
        for (Usuario usuarioAtual : usuarios) {
            if (usuarioAtual.getLogin().equalsIgnoreCase(loginNormalizado)
                    && usuarioAtual.getSenha().equals(senha)) {
                usuarioLogado = usuarioAtual;
                return true;
            }
        }
        usuarioLogado = null;
        return false;
    }

    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public static void limparUsuarios() {
        usuarios.clear();
        usuarioLogado = null;
    }

    public static void sairSistema() {
        usuarioLogado = null;
    }
}
