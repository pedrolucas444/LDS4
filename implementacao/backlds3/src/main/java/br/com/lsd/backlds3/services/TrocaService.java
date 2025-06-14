package br.com.lsd.backlds3.services;

import br.com.lsd.backlds3.models.Aluno;
import br.com.lsd.backlds3.models.Vantagem;
import br.com.lsd.backlds3.models.Transacao;
import br.com.lsd.backlds3.repositories.AlunoRepository;
import br.com.lsd.backlds3.repositories.VantagemRepository;
import br.com.lsd.backlds3.repositories.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
public class TrocaService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private VantagemRepository vantagemRepository;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private EmailService emailService;

    public void solicitarTroca(Long vantagemId, Long alunoId) {
        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));

        Vantagem vantagem = vantagemRepository.findById(vantagemId)
                .orElseThrow(() -> new IllegalArgumentException("Vantagem não encontrada"));

        if (aluno.getSaldoMoedas() < vantagem.getValor()) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }

        Transacao transacao = new Transacao(aluno, "TROCA", vantagem.getValor(), new Date());
        transacao.setEmpresa(vantagem.getEmpresa());
        transacaoRepository.save(transacao);

        aluno.setSaldoMoedas(aluno.getSaldoMoedas() - vantagem.getValor());
        alunoRepository.save(aluno);

        // Gerar código/cupom
        String codigoCupom = String.valueOf(1000 + new Random().nextInt(9000));

        // Enviar e-mail para o aluno
        if (aluno.getEmail() != null) {
            emailService.sendEmail(
                aluno.getEmail(),
                "Cupom de Vantagem",
                "Parabéns! Você resgatou a vantagem '" + vantagem.getDescricao() + "'.\nCódigo do cupom: " + codigoCupom
            );
        }

        // Enviar e-mail para o professor (assumindo que a vantagem tem um professor associado)
        if (vantagem.getEmpresa() != null && vantagem.getEmpresa().getEmail() != null) {
            emailService.sendEmail(
                vantagem.getEmpresa().getEmail(),
                "Cupom de Vantagem Resgatado",
                "O aluno '" + aluno.getNome() + "' resgatou a vantagem '" + vantagem.getDescricao() + ".\nCódigo do cupom: " + codigoCupom
            );
        }
    }
}
