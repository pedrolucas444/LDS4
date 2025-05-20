package br.com.lsd.backlds3.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import br.com.lsd.backlds3.models.Vantagem;
import br.com.lsd.backlds3.services.VantagemService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("api/vantagens")
public class VantagemController {
    @Autowired
    VantagemService vantagemService;

    @PostMapping
    public Vantagem createVantagem(@RequestBody Vantagem vantagem) {
        return vantagemService.createVantagem(vantagem);
    }

    @GetMapping
    public List<Vantagem> getAllVantagens() {
        return vantagemService.getVantagens();
    }

    @PutMapping("/{id}")
    public Vantagem updateVantagem(@PathVariable Long id, @RequestBody Vantagem vantagemDetails) {
        return vantagemService.updateVantagem(id, vantagemDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVantagem(@PathVariable Long id) {
        vantagemService.deleteVantagem(id);
        return ResponseEntity.noContent().build();
    }

}
