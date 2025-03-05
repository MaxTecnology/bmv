package br.com.bmv.erp.core.repository;

import br.com.bmv.erp.core.entity.Revendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RevendedorRepository extends JpaRepository<Revendedor, Long> {

    List<Revendedor> findByNomeContainingIgnoreCase(String nome);

    Optional<Revendedor> findByCnpj(String cnpj);

    List<Revendedor> findByStatus(String status);
}
