package br.com.senai.almoxarifado.repository;
import br.com.senai.almoxarifado.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface ProdutoRepository extends JpaRepository<Produto,Long> {
 List<Produto> findByNomeContainingIgnoreCaseOrderByNomeAsc(String nome);
 Optional<Produto> findByIdAndVersaoIsNotNull(Long id);
}
