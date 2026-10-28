package br.com.senai.almoxarifado.repository;
import br.com.senai.almoxarifado.model.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface MovimentacaoRepository extends JpaRepository<Movimentacao,Long> {
 List<Movimentacao> findAllByOrderByDataHoraDesc();
 List<Movimentacao> findByProdutoIdOrderByDataHoraDesc(Long produtoId);
 boolean existsByProdutoId(Long produtoId);
}
