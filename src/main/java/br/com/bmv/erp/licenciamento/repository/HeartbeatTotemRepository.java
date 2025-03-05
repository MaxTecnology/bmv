package br.com.bmv.erp.licenciamento.repository;

import br.com.bmv.erp.licenciamento.entity.AtivacaoTotem;
import br.com.bmv.erp.licenciamento.entity.HeartbeatTotem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HeartbeatTotemRepository extends JpaRepository<HeartbeatTotem, Long> {

    List<HeartbeatTotem> findByAtivacao(AtivacaoTotem ativacao);

    @Query("SELECT h FROM HeartbeatTotem h WHERE h.ativacao = :ativacao AND h.timestamp > :since ORDER BY h.timestamp DESC")
    List<HeartbeatTotem> findRecentByAtivacao(@Param("ativacao") AtivacaoTotem ativacao, @Param("since") LocalDateTime since);

    @Query("SELECT COUNT(DISTINCT h.ativacao.id) FROM HeartbeatTotem h " +
            "WHERE h.ativacao.licenca.id = :licencaId AND h.timestamp > :since AND h.status = 'ATIVO'")
    Long countActiveHeartbeatsByLicenca(@Param("licencaId") Long licencaId, @Param("since") LocalDateTime since);
}
