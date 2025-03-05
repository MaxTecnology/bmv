package br.com.bmv.erp.cardapio.repository;

import br.com.bmv.erp.cardapio.entity.CategoriaMenu;
import br.com.bmv.erp.cardapio.entity.ItemMenu;
import br.com.bmv.erp.empresa.entity.Empresa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemMenuRepository {


    List<ItemMenu> findByCategoria(CategoriaMenu categoria);

    List<ItemMenu> findByEmpresaAndDestaque(Empresa empresa, Boolean destaque);

    List<ItemMenu> findByEmpresaAndDisponivel(Empresa empresa, Boolean disponivel);

    @Query("SELECT i FROM ItemMenu i WHERE i.empresa.id = :empresaId AND i.nome LIKE %:termo% OR i.descricao LIKE %:termo%")
    List<ItemMenu> buscarPorTermo(@Param("empresaId") Long empresaId, @Param("termo") String termo);
}
