package saep;

public enum PerfilUsuario {
    OPERADOR_ALMOXARIFADO("Operador de Almoxarifado"),
    ADMINISTRADOR_SISTEMA("Administrador do Sistema");

    private final String descricao;

    PerfilUsuario(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean podeGerenciarUsuarios() {
        return this == ADMINISTRADOR_SISTEMA;
    }

    public boolean podeExcluirItens() {
        return this == ADMINISTRADOR_SISTEMA || this == OPERADOR_ALMOXARIFADO;
    }
}