package br.com.bmv.erp.cardapio.repository;

import br.com.bmv.erp.cardapio.entity.Complemento;
import br.com.bmv.erp.empresa.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplementoRepository extends JpaRepository<Complemento, Long> {

    List<Complemento> findByEmpresa(Empresa empresa);

    List<Complemento> findByEmpresaAndDisponivel(Empresa empresa, Boolean disponivel);
}
