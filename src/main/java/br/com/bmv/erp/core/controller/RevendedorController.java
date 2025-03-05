package br.com.bmv.erp.core.controller;

import br.com.bmv.erp.core.dto.RevendedorDTO;
import br.com.bmv.erp.core.entity.Revendedor;
import br.com.bmv.erp.core.service.RevendedorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/revendedores")
public class RevendedorController {
    private final RevendedorService revendedorService;

    @Autowired
    public RevendedorController(RevendedorService revendedorService) {
        this.revendedorService = revendedorService;
    }

    @GetMapping
    public ResponseEntity<List<RevendedorDTO>> getAllRevendedores(){
        List<Revendedor> revendedores = revendedorService.findAll();
        List<RevendedorDTO> revendedorDTOS = revendedores.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(revendedorDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RevendedorDTO> getRevendedorById(@PathVariable Long id) {
        Revendedor revendedor = revendedorService.findById(id);
        return ResponseEntity.ok(convertToDTO(revendedor));
    }

    @PostMapping
    public ResponseEntity<RevendedorDTO> createRevendedor(@Valid @RequestBody RevendedorDTO revendedorDTO) {
        Revendedor revendedor = convertToEntity(revendedorDTO);
        Revendedor saveRevendedor = revendedorService.save(revendedor);
        return new ResponseEntity<>(convertToDTO(saveRevendedor), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RevendedorDTO> updateRevendedor(@PathVariable Long id, @Valid @RequestBody RevendedorDTO revendedorDTO) {
        Revendedor revendedor = convertToEntity(revendedorDTO);
        Revendedor updateRevendedor = revendedorService.update(id, revendedor);
        return ResponseEntity.ok(convertToDTO(updateRevendedor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRevendedor(@PathVariable Long id) {
        revendedorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<RevendedorDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        Revendedor updateRevendedor = revendedorService.changeStatus(id, status);
        return ResponseEntity.ok(convertToDTO(updateRevendedor));
    }

    private RevendedorDTO convertToDTO(Revendedor revendedor) {
        RevendedorDTO dto = new RevendedorDTO();
        dto.setId((long) revendedor.getId());
        dto.setNome(revendedor.getNome());
        dto.setCnpj(revendedor.getCnpj());
        dto.setRazaoSocial(revendedor.getRazaoSocial());
        dto.setEndereco(revendedor.getEndereco());
        dto.setTelefone(revendedor.getTelefone());
        dto.setEmail(revendedor.getEmail());
        dto.setLogo(revendedor.getLogo());
        dto.setSite(revendedor.getSite());
        dto.setStatus(revendedor.getStatus());
        dto.setObservacoes(revendedor.getObservacoes());
        return dto;
    }

    private Revendedor convertToEntity(RevendedorDTO dto) {
        Revendedor revendedor = new Revendedor();
        revendedor.setId(Math.toIntExact(dto.getId()));
        revendedor.setNome(dto.getNome());
        revendedor.setCnpj(dto.getCnpj());
        revendedor.setRazaoSocial(dto.getRazaoSocial());
        revendedor.setEndereco(dto.getEndereco());
        revendedor.setTelefone(dto.getTelefone());
        revendedor.setEmail(dto.getEmail());
        revendedor.setLogo(dto.getLogo());
        revendedor.setSite(dto.getSite());
        revendedor.setStatus(dto.getStatus());
        revendedor.setObservacoes(dto.getObservacoes());
        return revendedor;
    }
}
