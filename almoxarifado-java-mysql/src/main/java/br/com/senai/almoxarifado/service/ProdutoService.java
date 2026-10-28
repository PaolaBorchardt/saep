package br.com.senai.almoxarifado.service;

import br.com.senai.almoxarifado.model.Produto;
import br.com.senai.almoxarifado.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class ProdutoService {
    private final ProdutoRepository produtos; private final MovimentacaoRepository movs;
    public ProdutoService(ProdutoRepository produtos, MovimentacaoRepository movs){this.produtos=produtos;this.movs=movs;}
    public List<Produto> listar(String nome){return nome==null||nome.isBlank()?produtos.findAll():produtos.findByNomeContainingIgnoreCaseOrderByNomeAsc(nome);}
    public Produto buscar(Long id){return produtos.findById(id).orElseThrow(()->new RecursoNaoEncontradoException("Produto não encontrado."));}
    @Transactional public Produto criar(String nome,String descricao,BigDecimal saldo,BigDecimal minimo){
        if(saldo.signum()<0||minimo.signum()<0) throw new IllegalArgumentException("Saldo e estoque mínimo não podem ser negativos.");
        Produto p=new Produto();p.setNome(nome);p.setDescricao(descricao);p.setSaldo(saldo);p.setEstoqueMinimo(minimo);return produtos.save(p);
    }
    @Transactional public Produto atualizar(Long id,String nome,String descricao,BigDecimal minimo){
        Produto p=buscar(id);if(minimo.signum()<0)throw new IllegalArgumentException("Estoque mínimo não pode ser negativo.");
        p.setNome(nome);p.setDescricao(descricao);p.setEstoqueMinimo(minimo);return produtos.save(p);
    }
    @Transactional public void excluir(Long id){
        Produto p=buscar(id);if(movs.existsByProdutoId(id))throw new IllegalStateException("Não é possível excluir produto com movimentações; histórico deve ser preservado.");
        produtos.delete(p);
    }
}
