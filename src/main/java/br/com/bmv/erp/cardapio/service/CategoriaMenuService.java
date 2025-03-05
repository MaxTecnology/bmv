package br.com.bmv.erp.cardapio.service;

import br.com.bmv.erp.cardapio.entity.CategoriaMenu;
import br.com.bmv.erp.cardapio.repository.CategoriaMenuRepository;
import br.com.bmv.erp.comum.ResourceNotFoundException;
import br.com.bmv.erp.empresa.entity.Empresa;
import br.com.bmv.erp.empresa.repository.EmpresaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaMenuService {

    private final CategoriaMenuRepository categoriaRepository;
    private final EmpresaRepository empresaRepository;

    @Autowired
    public CategoriaMenuService(CategoriaMenuRepository categoriaRepository, EmpresaRepository empresaRepository) {
        this.categoriaRepository = categoriaRepository;
        this.empresaRepository = empresaRepository;
    }

    public List<CategoriaMenu> findAll() {
        return categoriaRepository.findAll();
    }

    public CategoriaMenu findById(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com id: " + id));
    }

    public List<CategoriaMenu> findByEmpresa(Long empresaId) {
        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada com id: " + empresaId));
        return categoriaRepository.findByEmpresaOrderByOrdem(empresa);
    }

    public List<CategoriaMenu> findByEmpresaAndStatus(Long empresaId, String status) {
        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada com id: " + empresaId));
        return categoriaRepository.findByEmpresaAndStatus(empresa, status);
    }

    @Transactional
    public CategoriaMenu save(CategoriaMenu categoria) {
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public CategoriaMenu update(Long id, CategoriaMenu categoriaDetails) {
        CategoriaMenu categoria = findById(id);

        // Não permitir alterar a empresa
        categoriaDetails.setEmpresa(categoria.getEmpresa());

        categoria.setNome(categoriaDetails.getNome());
        categoria.setDescricao(categoriaDetails.getDescricao());
        categoria.setOrdem(categoriaDetails.getOrdem());
        categoria.setImagem(categoriaDetails.getImagem());
        categoria.setStatus(categoriaDetails.getStatus());

        return categoriaRepository.save(categoria);
    }

    @Transactional
    public void delete(Long id) {
        CategoriaMenu categoria = findById(id);
        categoriaRepository.delete(categoria);
    }

    @Transactional
    public CategoriaMenu changeStatus(Long id, String status) {
        CategoriaMenu categoria = findById(id);
        categoria.setStatus(status);
        return categoriaRepository.save(categoria);
    }
}
