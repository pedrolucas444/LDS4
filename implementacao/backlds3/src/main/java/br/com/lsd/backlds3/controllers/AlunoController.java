package br.com.lsd.backlds3.controllers;

import br.com.lsd.backlds3.DTOs.ExtratoAlunoDTO;
import br.com.lsd.backlds3.DTOs.LoginDTO;
import br.com.lsd.backlds3.models.Aluno;
import br.com.lsd.backlds3.repositories.AlunoRepository;
import br.com.lsd.backlds3.services.AlunoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"})
@RestController
@RequestMapping("/api/alunos")
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private AlunoService alunoService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        String cpf = loginDTO.getIdentificador();
        String cpfLimpo = cpf != null ? cpf.replaceAll("[^\\d]", "") : "";

        Aluno aluno = alunoRepository.findByCpf(cpfLimpo);

        if (aluno == null || !aluno.getSenha().equals(loginDTO.getSenha())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("CPF ou senha inválidos");
        }

        return ResponseEntity.ok(aluno); // ou gerar um token JWT
    }

    @Operation(description = "Cria um aluno")
    @PostMapping
    public Aluno createAluno(@RequestBody Aluno aluno) {
        return alunoService.createAluno(aluno);
    }

    @Operation(description = "Retorna um aluno por id")
    @GetMapping("/{id}")
    public ResponseEntity<Aluno> getAlunoById(@PathVariable Long id) {
        Optional<Aluno> alunoOptional = alunoService.getAlunoById(id);
        return alunoOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(description = "Retorna todos os alunos")
    @GetMapping
    public List<Aluno> getAllAlunos() {
        return alunoService.getAllAlunos();
    }

    @Operation(description = "Altera um aluno")
    @PutMapping("/{id}")
    public Aluno updateAluno(@PathVariable Long id, @RequestBody Aluno alunoDetails) {
        return alunoService.updateAluno(id, alunoDetails);
    }

    @Operation(description = "Deleta um aluno")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAluno(@PathVariable Long id) {
        alunoService.deleteAluno(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/extrato")
    public ResponseEntity<ExtratoAlunoDTO> consultarExtrato(@PathVariable Long id) {
        ExtratoAlunoDTO extrato = alunoService.consultarExtrato(id);
        return ResponseEntity.ok(extrato);
    }
}
