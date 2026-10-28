package br.com.senai.almoxarifado.service;

import br.com.senai.almoxarifado.model.*;
import br.com.senai.almoxarifado.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class MovimentacaoService {
    private final ProdutoRepository produtos; private final MovimentacaoRepository movs; private final UsuarioRepository usuarios;
    public MovimentacaoService(ProdutoRepository p,MovimentacaoRepository m,UsuarioRepository u){produtos=p;movs=m;usuarios=u;}
    @Transactional public Movimentacao registrar(Long produtoId,BigDecimal quantidade,String observacao,String username,TipoMovimentacao tipo){
        if(quantidade==null||quantidade.signum()<=0)throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        Produto p=produtos.findById(produtoId).orElseThrow(()->new RecursoNaoEncontradoException("Produto não encontrado."));
        if(tipo==TipoMovimentacao.SAIDA && p.getSaldo().compareTo(quantidade)<0)
            throw new IllegalStateException("Estoque insuficiente. Disponível: "+p.getSaldo()+", solicitado: "+quantidade+".");
        if(tipo==TipoMovimentacao.SAIDA)p.setSaldo(p.getSaldo().subtract(quantidade));
        else p.setSaldo(p.getSaldo().add(quantidade));
        produtos.save(p);
        Usuario u=usuarios.findByUsername(username).orElseThrow(()->new RecursoNaoEncontradoException("Usuário responsável não encontrado."));
        Movimentacao m=new Movimentacao();m.setProduto(p);m.setQuantidade(quantidade);m.setObservacao(observacao);m.setResponsavel(u);m.setTipo(tipo);
        return movs.save(m);
    }
    public List<Movimentacao> listar(Long produtoId){return produtoId==null?movs.findAllByOrderByDataHoraDesc():movs.findByProdutoIdOrderByDataHoraDesc(produtoId);}
}
