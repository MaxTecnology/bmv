package br.com.bmv.erp.cardapio.repository;

import br.com.bmv.erp.cardapio.entity.CategoriaMenu;
import br.com.bmv.erp.empresa.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaMenuRepository extends JpaRepository<CategoriaMenu, Long> {

    List<CategoriaMenu> findByEmpresa(Empresa empresa);

    List<CategoriaMenu> findByEmpresaAndStatus(Empresa empresa, String status);

    List<CategoriaMenu> findByEmpresaOrderByOrdem(Empresa empresa);
}
