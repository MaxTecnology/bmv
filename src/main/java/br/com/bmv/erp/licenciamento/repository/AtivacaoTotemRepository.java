package br.com.bmv.erp.licenciamento.repository;

import br.com.bmv.erp.licenciamento.entity.AtivacaoTotem;
import br.com.bmv.erp.licenciamento.entity.LicencaTotem;
import br.com.bmv.erp.totem.entity.Totem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AtivacaoTotemRepository extends JpaRepository<AtivacaoTotem, Long> {

    List<AtivacaoTotem> findByLicencaAndStatus(LicencaTotem licenca, String status);

    Optional<AtivacaoTotem> findByTotem(Totem totem);

    Optional<AtivacaoTotem> findByCodigoAtivacao(String codigoAtivacao);

    @Query("SELECT COUNT(a) FROM AtivacaoTotem a WHERE a.licenca.id = :licencaId AND a.status = 'ATIVO'")
    Long countActiveByLicenca(@Param("licencaId") Long licencaId);
}
