package br.com.bmv.erp.cardapio.repository;

import br.com.bmv.erp.cardapio.entity.GrupoComplemento;
import br.com.bmv.erp.empresa.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrupoComplementoRepository extends JpaRepository<GrupoComplemento, Long> {

    List<GrupoComplemento> findByEmpresa(Empresa empresa);
}
