package br.com.lsd.backlds3.services;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lsd.backlds3.DTOs.ExtratoAlunoDTO;
import br.com.lsd.backlds3.DTOs.ProfessorResumoDTO;
import br.com.lsd.backlds3.DTOs.TransacaoAlunoDTO;
import br.com.lsd.backlds3.models.Aluno;
import br.com.lsd.backlds3.models.Transacao;
import br.com.lsd.backlds3.models.Vantagem;
import br.com.lsd.backlds3.repositories.AlunoRepository;
import br.com.lsd.backlds3.repositories.TransacaoRepository;
import br.com.lsd.backlds3.repositories.VantagemRepository;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private VantagemRepository vantagemRepository;

    @Autowired
    private TransacaoRepository transacaoRepository;

    public Aluno createAluno(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    public Optional<Aluno> getAlunoById(Long id) {
        return alunoRepository.findById(id);
    }

    public List<Aluno> getAllAlunos() {
        return alunoRepository.findAll();
    }

    public Aluno updateAluno(Long id, Aluno alunoDetails) {
        Aluno aluno = alunoRepository.findById(id).orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
        aluno.setCpf(alunoDetails.getCpf());
        aluno.setCurso(alunoDetails.getCurso());
        aluno.setEmail(alunoDetails.getEmail());
        aluno.setEndereco(alunoDetails.getEndereco());
        aluno.setInstituicao(alunoDetails.getInstituicao());
        aluno.setNotificacoes(alunoDetails.getNotificacoes());
        aluno.setNome(alunoDetails.getNome());
        aluno.setSaldoMoedas(alunoDetails.getSaldoMoedas());
        return alunoRepository.save(aluno);
    }

    public void deleteAluno(Long id) {
        alunoRepository.deleteById(id);
    }

    public ExtratoAlunoDTO consultarExtrato(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        List<Transacao> transacoes = transacaoRepository.findByAlunoId(id);
        List<TransacaoAlunoDTO> transacoesDTO = transacoes.stream().map(transacao -> {

            ProfessorResumoDTO professorResumo = null;

            if (transacao.getProfessor() != null) {
                professorResumo = new ProfessorResumoDTO(
                        transacao.getProfessor().getId(),
                        transacao.getProfessor().getNome());
            }

            return new TransacaoAlunoDTO(
                    transacao.getId(),
                    transacao.getTipo(),
                    transacao.getMontante(),
                    transacao.getData(),
                    professorResumo,
                    transacao.getEmpresa());
        }).collect(Collectors.toList());

        int saldo = aluno.getSaldoMoedas();
        return new ExtratoAlunoDTO(saldo, transacoesDTO);
    }

    public void resgatarVantagem(Long alunoId, Long vantagemId) {
        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        Vantagem vantagem = vantagemRepository.findById(vantagemId)
                .orElseThrow(() -> new RuntimeException("Vantagem não encontrada"));

        if (aluno.getSaldoMoedas() < vantagem.getValor()) {
            throw new RuntimeException("Saldo insuficiente");
        }

        aluno.setSaldoMoedas(aluno.getSaldoMoedas() - vantagem.getValor());
        alunoRepository.save(aluno);

        Transacao transacao = new Transacao();
        transacao.setTipo("Resgate de Vantagem");
        transacao.setMontante(vantagem.getValor());
        transacao.setAluno(aluno);
        transacao.setData(new Date());
        transacao.setEmpresa(vantagem.getEmpresa());

        transacaoRepository.save(transacao);
    }
}
