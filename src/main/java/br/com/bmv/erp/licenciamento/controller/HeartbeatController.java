package br.com.bmv.erp.licenciamento.controller;


import br.com.bmv.erp.licenciamento.dto.HeartbeatRequest;
import br.com.bmv.erp.licenciamento.entity.HeartbeatTotem;
import br.com.bmv.erp.licenciamento.service.HeartbeatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/heartbeats")
public class HeartbeatController {

    private final HeartbeatService heartbeatService;

    @Autowired
    public HeartbeatController(HeartbeatService heartbeatService) {
        this.heartbeatService = heartbeatService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerHeartbeat(@Valid @RequestBody HeartbeatRequest request) {
        HeartbeatTotem heartbeat = heartbeatService.registerHeartbeat(request);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("timestamp", heartbeat.getTimestamp());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/ativacao/{ativacaoId}")
    public ResponseEntity<List<HeartbeatTotem>> getHeartbeatsByAtivacao(@PathVariable Long ativacaoId) {
        List<HeartbeatTotem> heartbeats = heartbeatService.findByAtivacao(ativacaoId);
        return ResponseEntity.ok(heartbeats);
    }

    @GetMapping("/ativacao/{ativacaoId}/recentes")
    public ResponseEntity<List<HeartbeatTotem>> getRecentHeartbeatsByAtivacao(
            @PathVariable Long ativacaoId,
            @RequestParam(defaultValue = "15") Integer minutesAgo) {
        LocalDateTime since = LocalDateTime.now().minusMinutes(minutesAgo);
        List<HeartbeatTotem> heartbeats = heartbeatService.findRecentByAtivacao(ativacaoId, since);
        return ResponseEntity.ok(heartbeats);
    }

    @GetMapping("/licenca/{licencaId}/check")
    public ResponseEntity<Map<String, Object>> checkActiveLicenseUsage(@PathVariable Long licencaId) {
        boolean isValid = heartbeatService.checkActiveLicenseUsage(licencaId);

        Map<String, Object> response = new HashMap<>();
        response.put("valid", isValid);

        return ResponseEntity.ok(response);
    }
}
