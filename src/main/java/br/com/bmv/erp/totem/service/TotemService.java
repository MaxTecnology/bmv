package br.com.bmv.erp.totem.service;

import br.com.bmv.erp.comum.ResourceNotFoundException;
import br.com.bmv.erp.empresa.entity.Empresa;
import br.com.bmv.erp.empresa.repository.EmpresaRepository;
import br.com.bmv.erp.licenciamento.dto.TotemRegistrationRequest;
import br.com.bmv.erp.totem.entity.Totem;
import br.com.bmv.erp.totem.repository.TotemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TotemService {

    private final TotemRepository totemRepository;
    private final EmpresaRepository empresaRepository;

    @Autowired
    public TotemService(TotemRepository totemRepository, EmpresaRepository empresaRepository) {
        this.totemRepository = totemRepository;
        this.empresaRepository = empresaRepository;
    }

    public List<Totem> findAll() {
        return totemRepository.findAll();
    }

    public Totem findById(Long id) {
        return totemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Totem não encontrado com id: " + id));
    }

    public List<Totem> findByEmpresa(Long empresaId) {
        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada com id: " + empresaId));
        return totemRepository.findByEmpresa(empresa);
    }

    public List<Totem> findByEmpresaAndStatus(Long empresaId, String status) {
        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada com id: " + empresaId));
        return totemRepository.findByEmpresaAndStatus(empresa, status);
    }

    public Long countActiveByEmpresa(Long empresaId) {
        return totemRepository.countActiveByEmpresa(empresaId);
    }

    @Transactional
    public Totem save(Totem totem) {
        return totemRepository.save(totem);
    }

    @Transactional
    public Totem registerTotem(Long empresaId, TotemRegistrationRequest request) {
        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada com id: " + empresaId));

        totemRepository.findByHardwareId(request.getHardwareId())
                .ifPresent(totem -> {
                    throw new IllegalArgumentException("Já existe um totem com este ID de hardware: " + request.getHardwareId());
                });

        totemRepository.findByEmpresaAndCodigo(empresa, request.getCodigo())
                .ifPresent(totem -> {
                    throw new IllegalArgumentException("Já existe um totem com este código para esta empresa: " + request.getCodigo());
                });

        Totem totem = new Totem();
        totem.setEmpresa(empresa);
        totem.setCodigo(request.getCodigo());
        totem.setNome(request.getNome());
        totem.setLocalizacao(request.getLocalizacao());
        totem.setIp(request.getIp());
        totem.setMacAddress(request.getMacAddress());
        totem.setHardwareId(request.getHardwareId());
        totem.setSistemaOperacional(request.getSistemaOperacional());
        totem.setVersaoSoftware(request.getVersaoSoftware());
        totem.setStatus("INATIVO");  // Inicia como inativo até ser ativado

        return totemRepository.save(totem);
    }

    @Transactional
    public Totem update(Long id, Totem totemDetails) {
        Totem totem = findById(id);

        totemDetails.setEmpresa(totem.getEmpresa());
        totemDetails.setHardwareId(totem.getHardwareId());

        totem.setCodigo(totemDetails.getCodigo());
        totem.setNome(totemDetails.getNome());
        totem.setLocalizacao(totemDetails.getLocalizacao());
        totem.setIp(totemDetails.getIp());
        totem.setMacAddress(totemDetails.getMacAddress());
        totem.setSistemaOperacional(totemDetails.getSistemaOperacional());
        totem.setVersaoSoftware(totemDetails.getVersaoSoftware());
        totem.setStatus(totemDetails.getStatus());
        totem.setUltimaSincronizacao(totemDetails.getUltimaSincronizacao());

        return totemRepository.save(totem);
    }

    @Transactional
    public void delete(Long id) {
        Totem totem = findById(id);
        totemRepository.delete(totem);
    }

    @Transactional
    public Totem changeStatus(Long id, String status) {
        Totem totem = findById(id);
        totem.setStatus(status);
        return totemRepository.save(totem);
    }
}
