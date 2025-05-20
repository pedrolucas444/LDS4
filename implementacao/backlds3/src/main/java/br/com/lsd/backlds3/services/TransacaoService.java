package br.com.lsd.backlds3.services;

import br.com.lsd.backlds3.models.Transacao;
import br.com.lsd.backlds3.repositories.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    public List<Transacao> findAll() {
        return transacaoRepository.findAll();
    }

    public Optional<Transacao> findById(Long id) {
        return transacaoRepository.findById(id);
    }

    public Transacao save(Transacao transacao) {
        return transacaoRepository.save(transacao);
    }

    public Transacao update(Long id, Transacao updatedTransacao) {
        return transacaoRepository.findById(id)
                .map(existingTransacao -> {
                    existingTransacao.setTipo(updatedTransacao.getTipo());
                    existingTransacao.setMontante(updatedTransacao.getMontante());
                    existingTransacao.setData(updatedTransacao.getData());
                    existingTransacao.setProfessor(updatedTransacao.getProfessor());
                    existingTransacao.setAluno(updatedTransacao.getAluno());
                    return transacaoRepository.save(existingTransacao);
                })
                .orElse(null);
    }

    public boolean delete(Long id) {
        return transacaoRepository.findById(id)
                .map(transacao -> {
                    transacaoRepository.delete(transacao);
                    return true;
                })
                .orElse(false);
    }
}
