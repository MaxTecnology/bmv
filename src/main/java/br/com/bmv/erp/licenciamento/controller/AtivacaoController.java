package br.com.bmv.erp.licenciamento.controller;


import br.com.bmv.erp.licenciamento.dto.AtivacaoTotemDTO;
import br.com.bmv.erp.licenciamento.dto.TotemActivationRequest;
import br.com.bmv.erp.licenciamento.entity.AtivacaoTotem;
import br.com.bmv.erp.licenciamento.service.AtivacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/ativacoes")
public class AtivacaoController {

    private final AtivacaoService ativacaoService;

    @Autowired
    public AtivacaoController(AtivacaoService ativacaoService) {
        this.ativacaoService = ativacaoService;
    }

    @GetMapping
    public ResponseEntity<List<AtivacaoTotemDTO>> getAllAtivacoes() {
        List<AtivacaoTotem> ativacoes = ativacaoService.findAll();
        List<AtivacaoTotemDTO> ativacaoDTOs = ativacoes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ativacaoDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtivacaoTotemDTO> getAtivacaoById(@PathVariable Long id) {
        AtivacaoTotem ativacao = ativacaoService.findById(id);
        return ResponseEntity.ok(convertToDTO(ativacao));
    }

    @PostMapping("/ativar")
    public ResponseEntity<AtivacaoTotemDTO> activateTotem(@Valid @RequestBody TotemActivationRequest request) {
        AtivacaoTotem ativacao = ativacaoService.activateTotem(request);
        return new ResponseEntity<>(convertToDTO(ativacao), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> deactivateTotem(@PathVariable Long id) {
        ativacaoService.deactivateTotem(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/checkin")
    public ResponseEntity<AtivacaoTotemDTO> updateCheckin(@RequestParam String codigoAtivacao) {
        AtivacaoTotem ativacao = ativacaoService.updateCheckin(codigoAtivacao);
        return ResponseEntity.ok(convertToDTO(ativacao));
    }

    private AtivacaoTotemDTO convertToDTO(AtivacaoTotem ativacao) {
        AtivacaoTotemDTO dto = new AtivacaoTotemDTO();
        dto.setId(ativacao.getId());
        dto.setTotemId(ativacao.getTotem().getId());
        dto.setLicencaId(ativacao.getLicenca().getId());
        dto.setCodigoAtivacao(ativacao.getCodigoAtivacao());
        dto.setHardwareId(ativacao.getHardwareId());
        dto.setIpAtivacao(ativacao.getIpAtivacao());
        dto.setDataAtivacao(ativacao.getDataAtivacao());
        dto.setUltimoCheckin(ativacao.getUltimoCheckin());
        dto.setStatus(ativacao.getStatus());
        return dto;
    }
}
