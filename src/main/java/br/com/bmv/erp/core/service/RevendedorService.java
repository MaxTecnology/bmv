package br.com.bmv.erp.core.service;

import br.com.bmv.erp.comum.ResourceNotFoundException;
import br.com.bmv.erp.core.entity.Revendedor;
import br.com.bmv.erp.core.repository.RevendedorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RevendedorService {

    private final RevendedorRepository revendedorRepository;

    @Autowired
    public RevendedorService(RevendedorRepository revendedorRepository) {
        this.revendedorRepository = revendedorRepository;
    }

    public List<Revendedor> findAll() {
        return revendedorRepository.findAll();
    }

    public Revendedor findById(Long id) {
        return revendedorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Revendedor não encontrado com id: " + id));
    }

    public List<Revendedor> findByNome(String nome){
        return revendedorRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Revendedor findByCnpj(String cnpj){
        return revendedorRepository.findByCnpj(cnpj)
                .orElseThrow(() -> new ResourceNotFoundException("Revendedor não encontrado com o CNPJ: "+ cnpj));
    }

    public Revendedor save(Revendedor revendedor) {
        return revendedorRepository.save(revendedor);
    }

    public Revendedor update(Long id, Revendedor revendedorDetails){
        Revendedor revendedor = findById(id);

        revendedor.setNome(revendedorDetails.getNome());
        revendedor.setCnpj(revendedorDetails.getCnpj());
        revendedor.setRazaoSocial(revendedorDetails.getRazaoSocial());
        revendedor.setEndereco(revendedorDetails.getEndereco());
        revendedor.setTelefone(revendedorDetails.getTelefone());
        revendedor.setEmail(revendedorDetails.getEmail());
        revendedor.setLogo(revendedorDetails.getLogo());
        revendedor.setSite(revendedorDetails.getSite());
        revendedor.setStatus(revendedorDetails.getStatus());
        revendedor.setObservacoes(revendedorDetails.getObservacoes());

        return revendedorRepository.save(revendedor);
    }
    @Transactional
    public void delete(Long id) {
        Revendedor revendedor = findById(id);
        revendedorRepository.delete(revendedor);
    }

    @Transactional
    public Revendedor changeStatus(Long id, String status) {
        Revendedor revendedor = findById(id);
        revendedor.setStatus(status);
        return revendedorRepository.save(revendedor);
    }
}
