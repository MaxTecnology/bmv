package br.com.bmv.erp.totem.controller;

import br.com.bmv.erp.comum.ResourceNotFoundException;
import br.com.bmv.erp.empresa.entity.Empresa;
import br.com.bmv.erp.empresa.repository.EmpresaRepository;
import br.com.bmv.erp.licenciamento.dto.TotemRegistrationRequest;
import br.com.bmv.erp.totem.dto.TotemDTO;
import br.com.bmv.erp.totem.entity.Totem;
import br.com.bmv.erp.totem.service.TotemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(("/api/v1/totems"))
public class TotemController {

    private final TotemService totemService;
    private final EmpresaRepository empresaRepository;

    @Autowired
    public TotemController(TotemService totemService, EmpresaRepository empresaRepository) {
        this.totemService = totemService;
        this.empresaRepository = empresaRepository;
    }

    @GetMapping
    public ResponseEntity<List<TotemDTO>> getAllTotems() {
        List<Totem> totems = totemService.findAll();
        List<TotemDTO> totemDTOs = totems.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(totemDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TotemDTO> getTotemById(@PathVariable Long id) {
        Totem totem = totemService.findById(id);
        return ResponseEntity.ok(convertToDTO(totem));
    }

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<TotemDTO>> getTotemsByEmpresa(@PathVariable Long empresaId) {
        List<Totem> totems = totemService.findByEmpresa(empresaId);
        List<TotemDTO> totemDTOs = totems.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(totemDTOs);
    }

    @PostMapping("/empresa/{empresaId}/register")
    public ResponseEntity<TotemDTO> registerTotem(
            @PathVariable Long empresaId,
            @Valid @RequestBody TotemRegistrationRequest request) {
        Totem registeredTotem = totemService.registerTotem(empresaId, request);
        return new ResponseEntity<>(convertToDTO(registeredTotem), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TotemDTO> updateTotem(
            @PathVariable Long id,
            @Valid @RequestBody TotemDTO totemDTO) {
        Totem totem = convertToEntity(totemDTO);
        Totem updatedTotem = totemService.update(id, totem);
        return ResponseEntity.ok(convertToDTO(updatedTotem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTotem(@PathVariable Long id) {
        totemService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TotemDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        Totem updatedTotem = totemService.changeStatus(id, status);
        return ResponseEntity.ok(convertToDTO(updatedTotem));
    }

    private TotemDTO convertToDTO(Totem totem) {
        TotemDTO dto = new TotemDTO();
        dto.setId(totem.getId());
        dto.setEmpresaId(totem.getEmpresa().getId());
        dto.setCodigo(totem.getCodigo());
        dto.setNome(totem.getNome());
        dto.setLocalizacao(totem.getLocalizacao());
        dto.setIp(totem.getIp());
        dto.setMacAddress(totem.getMacAddress());
        dto.setHardwareId(totem.getHardwareId());
        dto.setSistemaOperacional(totem.getSistemaOperacional());
        dto.setVersaoSoftware(totem.getVersaoSoftware());
        dto.setStatus(totem.getStatus());
        dto.setUltimaSincronizacao(totem.getUltimaSincronizacao());
        return dto;
    }

    private Totem convertToEntity(TotemDTO dto) {
        Totem totem = new Totem();
        totem.setId(dto.getId());

        if (dto.getEmpresaId() != null) {
            Empresa empresa = empresaRepository.findById(dto.getEmpresaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada com id: " + dto.getEmpresaId()));
            totem.setEmpresa(empresa);
        }

        totem.setCodigo(dto.getCodigo());
        totem.setNome(dto.getNome());
        totem.setLocalizacao(dto.getLocalizacao());
        totem.setIp(dto.getIp());
        totem.setMacAddress(dto.getMacAddress());
        totem.setHardwareId(dto.getHardwareId());
        totem.setSistemaOperacional(dto.getSistemaOperacional());
        totem.setVersaoSoftware(dto.getVersaoSoftware());
        totem.setStatus(dto.getStatus());
        totem.setUltimaSincronizacao(dto.getUltimaSincronizacao());
        return totem;
    }
}
