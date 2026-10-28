package saep;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ItenDAO {
    private static final List<Almoxarifado> bancoItens = new ArrayList<>();
    private static final List<MovimentacaoEstoque> movimentacoes = new ArrayList<>();
    private static int contador = 1;
    private static int contadorMovimentacoes = 1;

    public void cadastrar(Almoxarifado item) {
        if (item == null) {
            throw new IllegalArgumentException("Item não pode ser nulo.");
        }

        if (item.getId() == 0) {
            item.setId(contador++);
        }

        bancoItens.add(item);
    }

    public List<Almoxarifado> consultar() {
        return new ArrayList<>(bancoItens);
    }

    public Almoxarifado consultarPorId(int id) {
        for (Almoxarifado item : bancoItens) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public void alterar(Almoxarifado item) {
        for (int i = 0; i < bancoItens.size(); i++) {
            if (bancoItens.get(i).getId() == item.getId()) {
                bancoItens.set(i, item);
                return;
            }
        }
    }

    public void excluir(int id) {
        bancoItens.removeIf(item -> item.getId() == id);
    }

    public void registrarMovimentacao(int itemId, TipoMovimentacao tipo, int quantidade,
                                     Usuario usuarioResponsavel, String descricao) {
        if (tipo == null) {
            throw new IllegalArgumentException("Tipo da movimentação é obrigatório.");
        }
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }
        if (usuarioResponsavel == null) {
            throw new IllegalArgumentException("O usuário responsável é obrigatório.");
        }

        Almoxarifado item = consultarPorId(itemId);
        if (item == null) {
            throw new IllegalArgumentException("Item não encontrado para movimentação.");
        }

        int saldoAtual = item.getQuantidade();
        if (tipo == TipoMovimentacao.SAIDA && saldoAtual < quantidade) {
            throw new IllegalArgumentException("Estoque insuficiente para a saída solicitada.");
        }

        int saldoNovo = tipo == TipoMovimentacao.ENTRADA
                ? saldoAtual + quantidade
                : saldoAtual - quantidade;

        item.setQuantidade(saldoNovo);

        movimentacoes.add(new MovimentacaoEstoque(
                contadorMovimentacoes++,
                itemId,
                tipo,
                quantidade,
                LocalDateTime.now(),
                usuarioResponsavel,
                descricao == null ? "" : descricao
        ));
    }

    public List<MovimentacaoEstoque> consultarMovimentacoes() {
        return new ArrayList<>(movimentacoes);
    }

    public void limparDados() {
        bancoItens.clear();
        movimentacoes.clear();
        contador = 1;
        contadorMovimentacoes = 1;
    }
}
