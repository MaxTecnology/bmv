// empresa/controller/EmpresaController.java
package br.com.bmv.erp.empresa.controller;

import br.com.bmv.erp.comum.ResourceNotFoundException;
import br.com.bmv.erp.core.entity.Revendedor;
import br.com.bmv.erp.core.repository.RevendedorRepository;
import br.com.bmv.erp.empresa.dto.EmpresaDTO;
import br.com.bmv.erp.empresa.entity.Empresa;
import br.com.bmv.erp.empresa.service.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaController {

    private final EmpresaService empresaService;
    private final RevendedorRepository revendedorRepository;

    @Autowired
    public EmpresaController(EmpresaService empresaService, RevendedorRepository revendedorRepository) {
        this.empresaService = empresaService;
        this.revendedorRepository = revendedorRepository;
    }

    @GetMapping
    public ResponseEntity<List<EmpresaDTO>> getAllEmpresas() {
        List<Empresa> empresas = empresaService.findAll();
        List<EmpresaDTO> empresaDTOs = empresas.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(empresaDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaDTO> getEmpresaById(@PathVariable Long id) {
        Empresa empresa = empresaService.findById(id);
        return ResponseEntity.ok(convertToDTO(empresa));
    }

    @GetMapping("/revendedor/{revendedorId}")
    public ResponseEntity<List<EmpresaDTO>> getEmpresasByRevendedor(@PathVariable Long revendedorId) {
        List<Empresa> empresas = empresaService.findByRevendedor(revendedorId);
        List<EmpresaDTO> empresaDTOs = empresas.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(empresaDTOs);
    }

    @GetMapping("/revendedor/{revendedorId}/status/{status}")
    public ResponseEntity<List<EmpresaDTO>> getEmpresasByRevendedorAndStatus(
            @PathVariable Long revendedorId,
            @PathVariable String status) {
        List<Empresa> empresas = empresaService.findByRevendedorAndStatus(revendedorId, status);
        List<EmpresaDTO> empresaDTOs = empresas.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(empresaDTOs);
    }

    @PostMapping
    public ResponseEntity<EmpresaDTO> createEmpresa(@Valid @RequestBody EmpresaDTO empresaDTO) {
        Empresa empresa = convertToEntity(empresaDTO);
        Empresa savedEmpresa = empresaService.save(empresa);
        return new ResponseEntity<>(convertToDTO(savedEmpresa), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpresaDTO> updateEmpresa(
            @PathVariable Long id,
            @Valid @RequestBody EmpresaDTO empresaDTO) {
        Empresa empresa = convertToEntity(empresaDTO);
        Empresa updatedEmpresa = empresaService.update(id, empresa);
        return ResponseEntity.ok(convertToDTO(updatedEmpresa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmpresa(@PathVariable Long id) {
        empresaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<EmpresaDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        Empresa updatedEmpresa = empresaService.changeStatus(id, status);
        return ResponseEntity.ok(convertToDTO(updatedEmpresa));
    }

    private EmpresaDTO convertToDTO(Empresa empresa) {
        EmpresaDTO dto = new EmpresaDTO();
        dto.setId(empresa.getId());
        dto.setRevendedorId((long) empresa.getRevendedor().getId());
        dto.setNome(empresa.getNome());
        dto.setCnpj(empresa.getCnpj());
        dto.setEndereco(empresa.getEndereco());
        dto.setTelefone(empresa.getTelefone());
        dto.setEmail(empresa.getEmail());
        dto.setLogo(empresa.getLogo());
        dto.setCoresTema(empresa.getCoresTema());
        dto.setDominioPersonalizado(empresa.getDominioPersonalizado());
        dto.setDataExpiracao(empresa.getDataExpiracao());
        dto.setPlano(empresa.getPlano());
        dto.setStatus(empresa.getStatus());
        return dto;
    }

    private Empresa convertToEntity(EmpresaDTO dto) {
        Empresa empresa = new Empresa();
        empresa.setId(dto.getId());

        if (dto.getRevendedorId() != null) {
            Revendedor revendedor = revendedorRepository.findById(dto.getRevendedorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Revendedor não encontrado com id: " + dto.getRevendedorId()));
            empresa.setRevendedor(revendedor);
        }

        empresa.setNome(dto.getNome());
        empresa.setCnpj(dto.getCnpj());
        empresa.setEndereco(dto.getEndereco());
        empresa.setTelefone(dto.getTelefone());
        empresa.setEmail(dto.getEmail());
        empresa.setLogo(dto.getLogo());
        empresa.setCoresTema(dto.getCoresTema());
        empresa.setDominioPersonalizado(dto.getDominioPersonalizado());
        empresa.setDataExpiracao(dto.getDataExpiracao());
        empresa.setPlano(dto.getPlano());
        empresa.setStatus(dto.getStatus());
        return empresa;
    }
}