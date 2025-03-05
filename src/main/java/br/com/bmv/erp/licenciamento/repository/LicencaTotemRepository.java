package br.com.bmv.erp.licenciamento.repository;

import br.com.bmv.erp.empresa.entity.Empresa;
import br.com.bmv.erp.licenciamento.entity.LicencaTotem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LicencaTotemRepository extends JpaRepository<LicencaTotem, Long> {

    List<LicencaTotem> findByEmpresa(Empresa empresa);

    Optional<LicencaTotem> findByChaveLicenca(String chaveLicenca);

    @Query("SELECT l FROM LicencaTotem l WHERE l.empresa.id = :empresaId AND l.status = 'ATIVO' AND l.dataExpiracao > :now")
    List<LicencaTotem> findValidLicensesByEmpresa(@Param("empresaId") Long empresaId, @Param("now") LocalDateTime now);
}
