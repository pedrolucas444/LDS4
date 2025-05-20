package br.com.lsd.backlds3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lsd.backlds3.DTOs.AlunoResumoDTO;
import br.com.lsd.backlds3.DTOs.ExtratoProfessorDTO;
import br.com.lsd.backlds3.DTOs.ProfessorResumoDTO;
import br.com.lsd.backlds3.DTOs.TransacaoProfessorDTO;
import br.com.lsd.backlds3.models.Aluno;
import br.com.lsd.backlds3.models.Professor;
import br.com.lsd.backlds3.models.Transacao;
import br.com.lsd.backlds3.repositories.ProfessorRepository;
import br.com.lsd.backlds3.repositories.TransacaoRepository;
import br.com.lsd.backlds3.repositories.AlunoRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private TransacaoRepository transacaoRepository;

    public List<Professor> listarTodos() {
        return professorRepository.findAll();
    }

    public Optional<Professor> buscarPorId(Long id) {
        return professorRepository.findById(id);
    }

    public Professor salvar(Professor professor) {
        return professorRepository.save(professor);
    }

    public Professor atualizar(Long id, Professor professorAtualizado) {
        return professorRepository.findById(id)
                .map(professor -> {
                    professor.setNome(professorAtualizado.getNome());
                    professor.setEmail(professorAtualizado.getEmail());
                    professor.setSenha(professorAtualizado.getSenha());
                    professor.setCpf(professorAtualizado.getCpf());
                    professor.setDepartamento(professorAtualizado.getDepartamento());
                    professor.setSaldoMoedas(professorAtualizado.getSaldoMoedas());
                    return professorRepository.save(professor);
                }).orElseThrow(() -> new RuntimeException("Professor não encontrado"));
    }

    public void deletar(Long id) {
        professorRepository.deleteById(id);
    }

    public void enviarMoedas(Long professorId, Long alunoId, int montante, String motivo) {
        Professor professor = professorRepository.findById(professorId)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        if (professor.getSaldoMoedas() < montante) {
            throw new RuntimeException("Saldo de moedas insuficiente");
        }

        professor.setSaldoMoedas(professor.getSaldoMoedas() - montante);
        professorRepository.save(professor);

        aluno.setSaldoMoedas(aluno.getSaldoMoedas() + montante);
        alunoRepository.save(aluno);

        Transacao transacao = new Transacao();
        transacao.setTipo("Envio de moedas");
        transacao.setMontante(montante);
        transacao.setAluno(aluno);
        transacao.setProfessor(professor);
        transacao.setData(new Date());

        transacaoRepository.save(transacao);
    }

    public ExtratoProfessorDTO consultarExtrato(Long professorId) {
        Professor professor = professorRepository.findById(professorId)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

        List<Transacao> transacoes = transacaoRepository.findByProfessorId(professorId);
        int saldo = professor.getSaldoMoedas();

        List<TransacaoProfessorDTO> trasacoesDTO = transacoes.stream().map(transacao -> {
            AlunoResumoDTO alunoResumo = new AlunoResumoDTO(
                    transacao.getAluno().getId(),
                    transacao.getAluno().getNome());

            return new TransacaoProfessorDTO(
                    transacao.getId(),
                    transacao.getTipo(),
                    transacao.getMontante(),
                    transacao.getData(),
                    alunoResumo);

        }).collect(Collectors.toList());

        return new ExtratoProfessorDTO(saldo, trasacoesDTO);
    }

}
