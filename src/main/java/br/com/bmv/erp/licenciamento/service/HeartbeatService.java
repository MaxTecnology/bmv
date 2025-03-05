package br.com.bmv.erp.licenciamento.service;

import br.com.bmv.erp.licenciamento.dto.HeartbeatRequest;
import br.com.bmv.erp.licenciamento.entity.AtivacaoTotem;
import br.com.bmv.erp.licenciamento.entity.HeartbeatTotem;
import br.com.bmv.erp.licenciamento.repository.HeartbeatTotemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HeartbeatService {

    private final HeartbeatTotemRepository heartbeatRepository;
    private final AtivacaoService ativacaoService;
    private final LicencaService licencaService;

    @Autowired
    public HeartbeatService(HeartbeatTotemRepository heartbeatRepository,
                            AtivacaoService ativacaoService,
                            LicencaService licencaService) {
        this.heartbeatRepository = heartbeatRepository;
        this.ativacaoService = ativacaoService;
        this.licencaService = licencaService;
    }

    @Transactional
    public HeartbeatTotem registerHeartbeat(HeartbeatRequest request) {
        // Buscar a ativação pelo código
        AtivacaoTotem ativacao = ativacaoService.findByCodigoAtivacao(request.getCodigoAtivacao());

        // Atualizar o último checkin da ativação
        ativacaoService.updateCheckin(request.getCodigoAtivacao());

        // Verificar se a licença ainda é válida
        licencaService.validateLicense(ativacao.getLicenca().getChaveLicenca());

        // Registrar o heartbeat
        HeartbeatTotem heartbeat = new HeartbeatTotem();
        heartbeat.setAtivacao(ativacao);
        heartbeat.setTimestamp(LocalDateTime.now());
        heartbeat.setIpAddress(request.getIpAddress());
        heartbeat.setStatus(request.getStatus());
        heartbeat.setVersaoSoftware(request.getVersaoSoftware());
        heartbeat.setMetricasSistema(request.getMetricasSistema());

        return heartbeatRepository.save(heartbeat);
    }

    public List<HeartbeatTotem> findByAtivacao(Long ativacaoId) {
        AtivacaoTotem ativacao = ativacaoService.findById(ativacaoId);
        return heartbeatRepository.findByAtivacao(ativacao);
    }

    public List<HeartbeatTotem> findRecentByAtivacao(Long ativacaoId, LocalDateTime since) {
        AtivacaoTotem ativacao = ativacaoService.findById(ativacaoId);
        return heartbeatRepository.findRecentByAtivacao(ativacao, since);
    }

    public boolean checkActiveLicenseUsage(Long licencaId) {
        return licencaService.checkLicenseUsage(licencaId).isValid();
    }
}
