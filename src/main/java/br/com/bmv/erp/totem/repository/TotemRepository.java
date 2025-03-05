package br.com.bmv.erp.totem.repository;

import br.com.bmv.erp.empresa.entity.Empresa;
import br.com.bmv.erp.totem.entity.Totem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;

@ResponseStatus
public interface TotemRepository extends JpaRepository<Totem, Long> {

    List<Totem> findByEmpresa(Empresa empresa);

    Optional<Totem> findByHardwareId(String hardwareId);

    Optional<Totem> findByEmpresaAndCodigo(Empresa empresa, String codigo);

    @Query("SELECT COUNT(t) FROM Totem t WHERE t.empresa.id = :empresaId AND t.status = 'ATIVO'")
    Long countActiveByEmpresa(@Param("empresaId") Long empresaId);

    List<Totem> findByEmpresaAndStatus(Empresa empresa, String status);
}
