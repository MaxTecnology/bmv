package br.com.bmv.erp.empresa.service;

import br.com.bmv.erp.comum.ResourceNotFoundException;
import br.com.bmv.erp.core.entity.Revendedor;
import br.com.bmv.erp.core.repository.RevendedorRepository;
import br.com.bmv.erp.empresa.entity.Empresa;
import br.com.bmv.erp.empresa.repository.EmpresaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final RevendedorRepository revendedorRepository;

    @Autowired
    public EmpresaService(EmpresaRepository empresaRepository, RevendedorRepository revendedorRepository) {
        this.empresaRepository = empresaRepository;
        this.revendedorRepository = revendedorRepository;
    }

    public List<Empresa> findAll() {
        return empresaRepository.findAll();
    }

    public Empresa findById(Long id) {
        return empresaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada com id: " + id));
    }

    public List<Empresa> findByRevendedor(Long revendedorId) {
        Revendedor revendedor = revendedorRepository.findById(revendedorId)
                .orElseThrow(() -> new ResourceNotFoundException("Revendedor não encontrado com id: " + revendedorId));
        return empresaRepository.findByRevendedor(revendedor);
    }

    public List<Empresa> findByRevendedorAndStatus(Long revendedorId, String status) {
        Revendedor revendedor = revendedorRepository.findById(revendedorId)
                .orElseThrow(() -> new ResourceNotFoundException("Revendedor não encontrado com id: " + revendedorId));
        return empresaRepository.findByRevendedorAndStatus(revendedor, status);
    }

    public Long countActiveByRevendedor(Long revendedorId) {
        return empresaRepository.countActiveByRevendedor(revendedorId);
    }

    @Transactional
    public Empresa save(Empresa empresa) {
        Revendedor revendedor = revendedorRepository.findById((long) empresa.getRevendedor().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Revendedor não encontrado com id: " + empresa.getRevendedor().getId()));
        empresa.setRevendedor(revendedor);

        return empresaRepository.save(empresa);
    }

    @Transactional
    public Empresa update(Long id, Empresa empresaDetails) {
        Empresa empresa = findById(id);

        empresaDetails.setRevendedor(empresa.getRevendedor());

        empresa.setNome(empresaDetails.getNome());
        empresa.setCnpj(empresaDetails.getCnpj());
        empresa.setEndereco(empresaDetails.getEndereco());
        empresa.setTelefone(empresaDetails.getTelefone());
        empresa.setEmail(empresaDetails.getEmail());
        empresa.setLogo(empresaDetails.getLogo());
        empresa.setCoresTema(empresaDetails.getCoresTema());
        empresa.setDominioPersonalizado(empresaDetails.getDominioPersonalizado());
        empresa.setDataExpiracao(empresaDetails.getDataExpiracao());
        empresa.setPlano(empresaDetails.getPlano());
        empresa.setStatus(empresaDetails.getStatus());

        return empresaRepository.save(empresa);
    }

    @Transactional
    public void delete(Long id) {
        Empresa empresa = findById(id);
        empresaRepository.delete(empresa);
    }

    @Transactional
    public Empresa changeStatus(Long id, String status) {
        Empresa empresa = findById(id);
        empresa.setStatus(status);
        return empresaRepository.save(empresa);
    }
}
