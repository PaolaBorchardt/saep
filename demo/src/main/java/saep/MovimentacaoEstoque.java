package saep;

import java.time.LocalDateTime;

public class MovimentacaoEstoque {
    private int id;
    private int itemId;
    private TipoMovimentacao tipo;
    private int quantidade;
    private LocalDateTime dataHora;
    private Usuario usuarioResponsavel;
    private String descricao;

    public MovimentacaoEstoque(int id, int itemId, TipoMovimentacao tipo, int quantidade,
                              LocalDateTime dataHora, Usuario usuarioResponsavel, String descricao) {
        this.id = id;
        this.itemId = itemId;
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.dataHora = dataHora;
        this.usuarioResponsavel = usuarioResponsavel;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public int getItemId() {
        return itemId;
    }

    public TipoMovimentacao getTipo() {
        return tipo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public Usuario getUsuarioResponsavel() {
        return usuarioResponsavel;
    }

    public String getDescricao() {
        return descricao;
    }
}