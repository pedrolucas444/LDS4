package br.com.lsd.backlds3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.lsd.backlds3.DTOs.ExtratoProfessorDTO;
import br.com.lsd.backlds3.DTOs.LoginDTO;
import br.com.lsd.backlds3.models.Professor;
import br.com.lsd.backlds3.repositories.ProfessorRepository;
import br.com.lsd.backlds3.services.ProfessorService;

import java.util.List;

@RestController
@RequestMapping("api/professores")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;
    @Autowired
    private ProfessorRepository professorRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        Professor prof = professorRepository.findByCpf(loginDTO.getIdentificador());

        if (prof == null || !prof.getSenha().equals(loginDTO.getSenha())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("CPF ou senha inválidos");
        }

        return ResponseEntity.ok(prof);
    }

    @GetMapping
    public List<Professor> listarTodos() {
        return professorService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Professor> buscarPorId(@PathVariable Long id) {
        return professorService.buscarPorId(id)
                .map(professor -> ResponseEntity.ok().body(professor))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Professor> salvar(@RequestBody Professor professor) {
        Professor novoProfessor = professorService.salvar(professor);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProfessor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Professor> atualizar(@PathVariable Long id, @RequestBody Professor professorAtualizado) {
        try {
            Professor professorAtualizadoObj = professorService.atualizar(id, professorAtualizado);
            return ResponseEntity.ok().body(professorAtualizadoObj);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        professorService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{professorId}/enviar-moedas/{alunoId}")
    public ResponseEntity<String> enviarMoedas(@PathVariable Long professorId, @PathVariable Long alunoId,
            @RequestParam int montante, @RequestParam String motivo) {
        try {
            professorService.enviarMoedas(professorId, alunoId, montante, motivo);
            return ResponseEntity.ok("Moedas enviadas com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/extrato")
    public ResponseEntity<ExtratoProfessorDTO> consultarExtrato(@PathVariable Long id) {
        ExtratoProfessorDTO extrato = professorService.consultarExtrato(id);
        return ResponseEntity.ok(extrato);
    }

}
