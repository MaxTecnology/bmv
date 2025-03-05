package br.com.bmv.erp.empresa.repository;

import br.com.bmv.erp.core.entity.Revendedor;
import br.com.bmv.erp.empresa.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PatchMapping;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    List<Empresa> findByRevendedor(Revendedor revendedor);

    List<Empresa> findByRevendedorAndStatus(Revendedor revendedor, String status);

    Optional<Empresa> findByCnpj(String cnpj);

    @Query("SELECT COUNT(e) FROM Empresa e WHERE e.revendedor.id = :revendedorId AND e.status = 'ATIVO'")
    Long countActiveByRevendedor(@Param("revendedorId") Long revendedorId);

    List<Empresa> findByNomeContainingIgnoreCase(String nome);
}
