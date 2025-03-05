package br.com.bmv.erp.licenciamento.service;

import br.com.bmv.erp.comum.ResourceNotFoundException;
import br.com.bmv.erp.licenciamento.dto.LicenseValidationResult;
import br.com.bmv.erp.licenciamento.dto.TotemActivationRequest;
import br.com.bmv.erp.licenciamento.entity.AtivacaoTotem;
import br.com.bmv.erp.licenciamento.entity.LicencaTotem;
import br.com.bmv.erp.licenciamento.repository.AtivacaoTotemRepository;
import br.com.bmv.erp.totem.entity.Totem;
import br.com.bmv.erp.totem.repository.TotemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AtivacaoService {

    private final AtivacaoTotemRepository ativacaoRepository;
    private final TotemRepository totemRepository;
    private final LicencaService licencaService;

    @Autowired
    public AtivacaoService(AtivacaoTotemRepository ativacaoRepository,
                           TotemRepository totemRepository,
                           LicencaService licencaService) {
        this.ativacaoRepository = ativacaoRepository;
        this.totemRepository = totemRepository;
        this.licencaService = licencaService;
    }

    public List<AtivacaoTotem> findAll() {
        return ativacaoRepository.findAll();
    }

    public AtivacaoTotem findById(Long id) {
        return ativacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ativação não encontrada com id: " + id));
    }

    public AtivacaoTotem findByCodigoAtivacao(String codigoAtivacao) {
        return ativacaoRepository.findByCodigoAtivacao(codigoAtivacao)
                .orElseThrow(() -> new ResourceNotFoundException("Ativação não encontrada com código: " + codigoAtivacao));
    }

    @Transactional
    public AtivacaoTotem activateTotem(TotemActivationRequest request) {
        // Validar a licença
        LicenseValidationResult validationResult = licencaService.validateLicense(request.getChaveLicenca());
        if (!validationResult.isValid()) {
            throw new IllegalArgumentException("Licença inválida: " + validationResult.getReason());
        }

        // Buscar o totem pelo hardware_id
        Totem totem = totemRepository.findByHardwareId(request.getHardwareId())
                .orElseThrow(() -> new ResourceNotFoundException("Totem não encontrado com hardware_id: " + request.getHardwareId()));

        // Verificar se o totem já está ativado
        ativacaoRepository.findByTotem(totem).ifPresent(ativacao -> {
            throw new IllegalArgumentException("Este totem já está ativado");
        });

        // Buscar a licença
        LicencaTotem licenca = licencaService.findById(validationResult.getLicenseId());

        // Verificar se o totem pertence à mesma empresa da licença
        if (!totem.getEmpresa().getId().equals(licenca.getEmpresa().getId())) {
            throw new IllegalArgumentException("O totem não pertence à mesma empresa da licença");
        }

        // Criar a ativação
        AtivacaoTotem ativacao = new AtivacaoTotem();
        ativacao.setTotem(totem);
        ativacao.setLicenca(licenca);
        ativacao.setCodigoAtivacao(generateActivationCode());
        ativacao.setHardwareId(totem.getHardwareId());
        ativacao.setIpAtivacao(request.getIpAddress());
        ativacao.setDataAtivacao(LocalDateTime.now());
        ativacao.setUltimoCheckin(LocalDateTime.now());
        ativacao.setStatus("ATIVO");

        // Atualizar o status do totem
        totem.setStatus("ATIVO");
        totem.setIp(request.getIpAddress());
        totem.setSistemaOperacional(request.getSistemaOperacional());
        totem.setVersaoSoftware(request.getVersaoSoftware());
        totem.setUltimaSincronizacao(LocalDateTime.now());
        totemRepository.save(totem);

        return ativacaoRepository.save(ativacao);
    }

    @Transactional
    public void deactivateTotem(Long ativacaoId) {
        AtivacaoTotem ativacao = findById(ativacaoId);
        ativacao.setStatus("INATIVO");

        // Atualizar o status do totem
        Totem totem = ativacao.getTotem();
        totem.setStatus("INATIVO");
        totemRepository.save(totem);

        ativacaoRepository.save(ativacao);
    }

    @Transactional
    public AtivacaoTotem updateCheckin(String codigoAtivacao) {
        AtivacaoTotem ativacao = findByCodigoAtivacao(codigoAtivacao);
        ativacao.setUltimoCheckin(LocalDateTime.now());
        return ativacaoRepository.save(ativacao);
    }

    private String generateActivationCode() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }
}
