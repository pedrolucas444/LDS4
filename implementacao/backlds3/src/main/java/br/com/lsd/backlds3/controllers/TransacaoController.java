package br.com.lsd.backlds3.controllers;

import br.com.lsd.backlds3.models.Transacao;
import br.com.lsd.backlds3.services.TransacaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @GetMapping
    public ResponseEntity<List<Transacao>> getAllTransacoes() {
        List<Transacao> transacoes = transacaoService.findAll();
        return new ResponseEntity<>(transacoes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transacao> getTransacaoById(@PathVariable Long id) {
        Optional<Transacao> transacao = transacaoService.findById(id);
        return transacao.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Transacao> createTransacao(@RequestBody Transacao transacao) {
        Transacao createdTransacao = transacaoService.save(transacao);
        return new ResponseEntity<>(createdTransacao, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transacao> updateTransacao(@PathVariable Long id, @RequestBody Transacao transacao) {
        Transacao updatedTransacao = transacaoService.update(id, transacao);
        if (updatedTransacao != null) {
            return new ResponseEntity<>(updatedTransacao, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransacao(@PathVariable Long id) {
        boolean deleted = transacaoService.delete(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
