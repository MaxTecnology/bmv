package br.com.bmv.erp.licenciamento.service;

import br.com.bmv.erp.comum.ResourceNotFoundException;
import br.com.bmv.erp.empresa.entity.Empresa;
import br.com.bmv.erp.empresa.repository.EmpresaRepository;
import br.com.bmv.erp.licenciamento.dto.LicenseValidationResult;
import br.com.bmv.erp.licenciamento.entity.LicencaTotem;
import br.com.bmv.erp.licenciamento.repository.AtivacaoTotemRepository;
import br.com.bmv.erp.licenciamento.repository.HeartbeatTotemRepository;
import br.com.bmv.erp.licenciamento.repository.LicencaTotemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class LicencaService {

    private final LicencaTotemRepository licencaRepository;
    private final EmpresaRepository empresaRepository;
    private final AtivacaoTotemRepository ativacaoRepository;
    private final HeartbeatTotemRepository heartbeatRepository;

    @Autowired
    public LicencaService(LicencaTotemRepository licencaRepository,
                          EmpresaRepository empresaRepository,
                          AtivacaoTotemRepository ativacaoRepository,
                          HeartbeatTotemRepository heartbeatRepository) {
        this.licencaRepository = licencaRepository;
        this.empresaRepository = empresaRepository;
        this.ativacaoRepository = ativacaoRepository;
        this.heartbeatRepository = heartbeatRepository;
    }

    public List<LicencaTotem> findAll() {
        return licencaRepository.findAll();
    }

    public LicencaTotem findById(Long id) {
        return licencaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Licença não encontrada com id: " + id));
    }

    public List<LicencaTotem> findByEmpresa(Long empresaId) {
        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada com id: " + empresaId));
        return licencaRepository.findByEmpresa(empresa);
    }

    public List<LicencaTotem> findValidLicensesByEmpresa(Long empresaId) {
        return licencaRepository.findValidLicensesByEmpresa(empresaId, LocalDateTime.now());
    }

    public LicencaTotem findByChaveLicenca(String chaveLicenca) {
        return licencaRepository.findByChaveLicenca(chaveLicenca)
                .orElseThrow(() -> new ResourceNotFoundException("Licença não encontrada com chave: " + chaveLicenca));
    }

    @Transactional
    public LicencaTotem generateLicense(Long empresaId, Integer maxTotems, LocalDateTime dataExpiracao) {
        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada com id: " + empresaId));

        LicencaTotem licenca = new LicencaTotem();
        licenca.setEmpresa(empresa);
        licenca.setChaveLicenca(generateLicenseKey());
        licenca.setDataEmissao(LocalDateTime.now());
        licenca.setDataExpiracao(dataExpiracao);
        licenca.setMaxTotems(maxTotems);
        licenca.setStatus("ATIVO");

        return licencaRepository.save(licenca);
    }

    @Transactional
    public LicencaTotem renewLicense(Long licencaId, LocalDateTime novaDataExpiracao) {
        LicencaTotem licenca = findById(licencaId);
        licenca.setDataExpiracao(novaDataExpiracao);
        return licencaRepository.save(licenca);
    }

    @Transactional
    public void revokeLicense(Long licencaId) {
        LicencaTotem licenca = findById(licencaId);
        licenca.setStatus("REVOGADA");
        licencaRepository.save(licenca);
    }

    public LicenseValidationResult validateLicense(String chaveLicenca) {
        try {
            LicencaTotem licenca = findByChaveLicenca(chaveLicenca);

            // Verificar se a licença está ativa
            if (!"ATIVO".equals(licenca.getStatus())) {
                return new LicenseValidationResult(false, "Licença inativa ou revogada", licenca.getId(), null, null);
            }

            // Verificar se a licença não expirou
            if (licenca.getDataExpiracao().isBefore(LocalDateTime.now())) {
                return new LicenseValidationResult(false, "Licença expirada", licenca.getId(), null, null);
            }

            // Verificar o número de ativações ativas
            Long activeCount = ativacaoRepository.countActiveByLicenca(licenca.getId());

            if (activeCount >= licenca.getMaxTotems()) {
                return new LicenseValidationResult(false, "Limite de totems atingido", licenca.getId(),
                        activeCount.intValue(), licenca.getMaxTotems());
            }

            return new LicenseValidationResult(true, null, licenca.getId(), activeCount.intValue(), licenca.getMaxTotems());
        } catch (ResourceNotFoundException e) {
            return new LicenseValidationResult(false, "Licença não encontrada", null, null, null);
        }
    }

    public LicenseValidationResult checkLicenseUsage(Long licencaId) {
        LicencaTotem licenca = findById(licencaId);

        // Verificar o número de heartbeats ativos (últimos 15 minutos)
        LocalDateTime since = LocalDateTime.now().minusMinutes(15);
        Long activeHeartbeats = heartbeatRepository.countActiveHeartbeatsByLicenca(licencaId, since);

        if (activeHeartbeats > licenca.getMaxTotems()) {
            return new LicenseValidationResult(false, "Uso simultâneo excedido", licenca.getId(),
                    activeHeartbeats.intValue(), licenca.getMaxTotems());
        }

        return new LicenseValidationResult(true, null, licenca.getId(), activeHeartbeats.intValue(), licenca.getMaxTotems());
    }

    private String generateLicenseKey() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }
}
