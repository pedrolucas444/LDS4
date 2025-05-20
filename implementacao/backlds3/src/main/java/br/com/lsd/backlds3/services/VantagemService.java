package br.com.lsd.backlds3.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lsd.backlds3.models.Vantagem;
import br.com.lsd.backlds3.repositories.VantagemRepository;

@Service
public class VantagemService {

    @Autowired
    VantagemRepository vantagemRepository;

    public Vantagem createVantagem(Vantagem vantagem) {
        return vantagemRepository.save(vantagem);
    }

    public List<Vantagem> getVantagens() {
        return vantagemRepository.findAll();
    }

    public Vantagem updateVantagem(Long id, Vantagem vantagemDetaisl) {
        Vantagem vantagem = vantagemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vantagem não encontrada"));
        vantagem.setValor(vantagemDetaisl.getValor());
        vantagem.setDescricao(vantagem.getDescricao());
        vantagem.setFoto(vantagemDetaisl.getFoto());
        return vantagemRepository.save(vantagem);
    }

    public void deleteVantagem(Long id) {
        vantagemRepository.deleteById(id);
    }

}
