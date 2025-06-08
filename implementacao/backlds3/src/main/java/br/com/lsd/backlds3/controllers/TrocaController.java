    package br.com.lsd.backlds3.controllers;

import br.com.lsd.backlds3.services.TrocaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trocas")
public class TrocaController {

    @Autowired
    private TrocaService trocaService;

    @PostMapping("/{vantagemId}")
    public ResponseEntity<?> solicitarTroca(@PathVariable Long vantagemId, @RequestParam Long alunoId) {
        try {
            trocaService.solicitarTroca(vantagemId, alunoId);
            return ResponseEntity.ok("Troca realizada com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
