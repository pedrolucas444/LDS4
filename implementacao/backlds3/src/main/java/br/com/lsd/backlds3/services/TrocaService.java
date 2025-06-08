package br.com.lsd.backlds3.services;

import br.com.lsd.backlds3.models.Aluno;
import br.com.lsd.backlds3.models.Vantagem;
import br.com.lsd.backlds3.models.Transacao;
import br.com.lsd.backlds3.repositories.AlunoRepository;
import br.com.lsd.backlds3.repositories.VantagemRepository;
import br.com.lsd.backlds3.repositories.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrocaService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private VantagemRepository vantagemRepository;

    @Autowired
    private TransacaoRepository transacaoRepository;

    public void solicitarTroca(Long vantagemId, Long alunoId) {
        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));

        Vantagem vantagem = vantagemRepository.findById(vantagemId)
                .orElseThrow(() -> new IllegalArgumentException("Vantagem não encontrada"));

        if (aluno.getSaldo() < vantagem.getCusto()) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }

        Transacao transacao = new Transacao(aluno, vantagem);
        transacaoRepository.save(transacao);

        aluno.setSaldo(aluno.getSaldo() - vantagem.getCusto());
        alunoRepository.save(aluno);
    }
}
