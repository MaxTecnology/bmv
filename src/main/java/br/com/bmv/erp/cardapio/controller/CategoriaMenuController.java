package br.com.bmv.erp.cardapio.controller;

import br.com.bmv.erp.cardapio.entity.CategoriaMenu;
import br.com.bmv.erp.cardapio.service.CategoriaMenuService;
import br.com.bmv.erp.comum.ResourceNotFoundException;
import br.com.bmv.erp.empresa.entity.Empresa;
import br.com.bmv.erp.empresa.repository.EmpresaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/categorias-menu")
public class CategoriaMenuController {

    private final CategoriaMenuService categoriaService;
    private final EmpresaRepository empresaRepository;

    @Autowired
    public CategoriaMenuController(CategoriaMenuService categoriaService, EmpresaRepository empresaRepository) {
        this.categoriaService = categoriaService;
        this.empresaRepository = empresaRepository;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaMenuDTO>> getAllCategorias() {
        List<CategoriaMenu> categorias = categoriaService.findAll();
        List<CategoriaMenuDTO> categoriaDTOs = categorias.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categoriaDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaMenuDTO> getCategoriaById(@PathVariable Long id) {
        CategoriaMenu categoria = categoriaService.findById(id);
        return ResponseEntity.ok(convertToDTO(categoria));
    }

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<CategoriaMenuDTO>> getCategoriasByEmpresa(@PathVariable Long empresaId) {
        List<CategoriaMenu> categorias = categoriaService.findByEmpresa(empresaId);
        List<CategoriaMenuDTO> categoriaDTOs = categorias.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categoriaDTOs);
    }

    @PostMapping
    public ResponseEntity<CategoriaMenuDTO> createCategoria(@Valid @RequestBody CategoriaMenuDTO categoriaDTO) {
        CategoriaMenu categoria = convertToEntity(categoriaDTO);
        CategoriaMenu savedCategoria = categoriaService.save(categoria);
        return new ResponseEntity<>(convertToDTO(savedCategoria), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaMenuDTO> updateCategoria(
            @PathVariable Long id,
            @Valid @RequestBody CategoriaMenuDTO categoriaDTO) {
        CategoriaMenu categoria = convertToEntity(categoriaDTO);
        CategoriaMenu updatedCategoria = categoriaService.update(id, categoria);
        return ResponseEntity.ok(convertToDTO(updatedCategoria));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<CategoriaMenuDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        CategoriaMenu updatedCategoria = categoriaService.changeStatus(id, status);
        return ResponseEntity.ok(convertToDTO(updatedCategoria));
    }

    private CategoriaMenuDTO convertToDTO(CategoriaMenu categoria) {
        CategoriaMenuDTO dto = new CategoriaMenuDTO();
        dto.setId(categoria.getId());
        dto.setEmpresaId(categoria.getEmpresa().getId());
        dto.setNome(categoria.getNome());
        dto.setDescricao(categoria.getDescricao());
        dto.setOrdem(categoria.getOrdem());
        dto.setImagem(categoria.getImagem());
        dto.setStatus(categoria.getStatus());
        return dto;
    }

    private CategoriaMenu convertToEntity(CategoriaMenuDTO dto) {
        CategoriaMenu categoria = new CategoriaMenu();
        categoria.setId(dto.getId());

        if (dto.getEmpresaId() != null) {
            Empresa empresa = empresaRepository.findById(dto.getEmpresaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada com id: " + dto.getEmpresaId()));
            categoria.setEmpresa(empresa);
        }

        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());
        categoria.setOrdem(dto.getOrdem());
        categoria.setImagem(dto.getImagem());
        categoria.setStatus(dto.getStatus());
        return categoria;
    }
}
