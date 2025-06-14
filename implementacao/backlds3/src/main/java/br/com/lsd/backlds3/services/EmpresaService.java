package br.com.lsd.backlds3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lsd.backlds3.models.Empresa;
import br.com.lsd.backlds3.repositories.EmpresaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {
    @Autowired
    private EmpresaRepository empresaRepository;

    public Empresa createEmpresa(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    public List<Empresa> getAllEmpresas() {
        return empresaRepository.findAll();
    }

    public Optional<Empresa> getEmpresaByid(Long id) {
        return empresaRepository.findById(id);
    }

    public Empresa updateEmpresa(Long id, Empresa empresaDetails) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
        empresa.setCnpj(empresaDetails.getCnpj());
        empresa.setEmail(empresaDetails.getEmail());
        empresa.setNome(empresaDetails.getNome());
        empresa.setVantagens(empresaDetails.getVantagens());
        return empresaRepository.save(empresa);
    }

    public void deleteEmpresa(Long id) {
        empresaRepository.deleteById(id);
    }
}
