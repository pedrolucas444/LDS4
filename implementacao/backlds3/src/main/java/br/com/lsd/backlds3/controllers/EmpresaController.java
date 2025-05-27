package br.com.lsd.backlds3.controllers;

import br.com.lsd.backlds3.DTOs.EmpresaDTO;
import br.com.lsd.backlds3.DTOs.LoginDTO;
import br.com.lsd.backlds3.models.Empresa;
import br.com.lsd.backlds3.repositories.EmpresaRepository;
import br.com.lsd.backlds3.services.EmpresaService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("api/empresa")
public class EmpresaController {
    @Autowired
    private EmpresaService empresaService;
    @Autowired
    private EmpresaRepository empresaRepository;

      @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        Empresa empresa = empresaRepository.findByCnpj(loginDTO.getIdentificador());

        if (empresa == null || !empresa.getSenha().equals(loginDTO.getSenha())) {
            return ResponseEntity.status(401).body("CNPJ ou senha inválidos");
        }

        return ResponseEntity.ok(empresa); // ou um DTO de resposta, se preferir
    }

    @PostMapping
    public Empresa createEmpresa(@RequestBody EmpresaDTO dto) {
    Empresa empresa = new Empresa();
    empresa.setCnpj(dto.getCnpj());
    empresa.setNome(dto.getNome());
    empresa.setSenha(dto.getSenha());
    return empresaService.createEmpresa(empresa);
}


    @Operation(description = "Retorna todas as empresas")
    @GetMapping
    public List<Empresa> getAllEmpresas() {
        return empresaService.getAllEmpresas();
    }
}
