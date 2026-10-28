package br.com.senai.almoxarifado.web;

import br.com.senai.almoxarifado.model.Produto;
import br.com.senai.almoxarifado.repository.ProdutoRepository;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController @RequestMapping("/api/relatorios")
public class RelatorioController {
    private final ProdutoRepository repo;
    public RelatorioController(ProdutoRepository repo){this.repo=repo;}
    public record LinhaEstoque(Long produtoId,String produto,BigDecimal saldo,BigDecimal estoqueMinimo,boolean abaixoDoMinimo){}
    @GetMapping("/estoque") public List<LinhaEstoque> estoque(){return repo.findAll().stream().map(this::linha).toList();}
    private LinhaEstoque linha(Produto p){return new LinhaEstoque(p.getId(),p.getNome(),p.getSaldo(),p.getEstoqueMinimo(),p.getSaldo().compareTo(p.getEstoqueMinimo())<0);}
}
