package br.com.bmv.erp.cardapio.repository;

import br.com.bmv.erp.cardapio.entity.GrupoComplemento;
import br.com.bmv.erp.cardapio.entity.OpcaoComplemento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpcaoComplementoRepository extends JpaRepository<OpcaoComplemento, Long> {

    List<OpcaoComplemento> findByGrupoComplemento(GrupoComplemento grupoComplemento);
}
