package br.com.senai.almoxarifado.web;

import br.com.senai.almoxarifado.model.Movimentacao;
import br.com.senai.almoxarifado.model.TipoMovimentacao;
import br.com.senai.almoxarifado.service.MovimentacaoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController @RequestMapping("/api/movimentacoes")
public class MovimentacaoController {
    private final MovimentacaoService service;
    public MovimentacaoController(MovimentacaoService service){this.service=service;}
    public record MovimentoRequest(@NotNull @Positive Long produtoId,@NotNull @DecimalMin(value="0.01") BigDecimal quantidade,@Size(max=500) String observacao){}
    @PostMapping("/entrada") public Movimentacao entrada(@Valid @RequestBody MovimentoRequest r,Authentication auth){return service.registrar(r.produtoId(),r.quantidade(),r.observacao(),auth.getName(),TipoMovimentacao.ENTRADA);}
    @PostMapping("/saida") public Movimentacao saida(@Valid @RequestBody MovimentoRequest r,Authentication auth){return service.registrar(r.produtoId(),r.quantidade(),r.observacao(),auth.getName(),TipoMovimentacao.SAIDA);}
    @GetMapping public List<Movimentacao> listar(@RequestParam(required=false) Long produtoId){return service.listar(produtoId);}
}
