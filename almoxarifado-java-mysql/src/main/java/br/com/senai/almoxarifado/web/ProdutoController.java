package br.com.senai.almoxarifado.web;

import br.com.senai.almoxarifado.model.Produto;
import br.com.senai.almoxarifado.service.ProdutoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController @RequestMapping("/api/produtos")
public class ProdutoController {
    private final ProdutoService service;
    public ProdutoController(ProdutoService service){this.service=service;}
    public record ProdutoRequest(@NotBlank @Size(max=120) String nome,@Size(max=500) String descricao,
      @NotNull @DecimalMin("0.00") BigDecimal quantidadeInicial,@NotNull @DecimalMin("0.00") BigDecimal estoqueMinimo){}
    public record ProdutoUpdate(@NotBlank @Size(max=120) String nome,@Size(max=500) String descricao,@NotNull @DecimalMin("0.00") BigDecimal estoqueMinimo){}
    @GetMapping public List<Produto> listar(@RequestParam(required=false) String nome){return service.listar(nome);}
    @GetMapping("/{id}") public Produto buscar(@PathVariable Long id){return service.buscar(id);}
    @PostMapping @PreAuthorize("hasRole('ADMIN')") public Produto criar(@Valid @RequestBody ProdutoRequest r){return service.criar(r.nome(),r.descricao(),r.quantidadeInicial(),r.estoqueMinimo());}
    @PutMapping("/{id}") @PreAuthorize("hasRole('ADMIN')") public Produto atualizar(@PathVariable Long id,@Valid @RequestBody ProdutoUpdate r){return service.atualizar(id,r.nome(),r.descricao(),r.estoqueMinimo());}
    @DeleteMapping("/{id}") @PreAuthorize("hasRole('ADMIN')") public void excluir(@PathVariable Long id){service.excluir(id);}
}
