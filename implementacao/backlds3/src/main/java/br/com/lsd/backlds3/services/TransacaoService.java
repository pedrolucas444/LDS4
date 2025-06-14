package br.com.lsd.backlds3.services;

import br.com.lsd.backlds3.DTOs.AlunoResumoDTO;
import br.com.lsd.backlds3.DTOs.ExtratoAlunoDTO;
import br.com.lsd.backlds3.DTOs.ExtratoProfessorDTO;
import br.com.lsd.backlds3.DTOs.ProfessorResumoDTO;
import br.com.lsd.backlds3.DTOs.TransacaoAlunoDTO;
import br.com.lsd.backlds3.DTOs.TransacaoProfessorDTO;
import br.com.lsd.backlds3.models.Transacao;
import br.com.lsd.backlds3.repositories.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public ExtratoAlunoDTO getExtratoAluno(Long alunoId) {
    List<Transacao> transacoes = transacaoRepository.findByAlunoId(alunoId);

    List<TransacaoAlunoDTO> dtoList = transacoes.stream().map(transacao -> {
        return new TransacaoAlunoDTO(
            transacao.getId(),
            transacao.getTipo(),
            transacao.getMontante(),
            transacao.getData(),
            new ProfessorResumoDTO(transacao.getProfessor().getId(), transacao.getProfessor().getNome()),
            transacao.getEmpresa()
        );
    }).toList();

    int saldo = transacoes.stream().mapToInt(Transacao::getMontante).sum();
    return new ExtratoAlunoDTO(saldo, dtoList);
}
    public ExtratoProfessorDTO getExtratoProfessor(Long professorId) {
    List<Transacao> transacoes = transacaoRepository.findByProfessorId(professorId);

    List<TransacaoProfessorDTO> dtoList = transacoes.stream().map(transacao -> {
        return new TransacaoProfessorDTO(
            transacao.getId(),
            transacao.getTipo(),
            transacao.getMontante(),
            transacao.getData(),
            new AlunoResumoDTO(transacao.getAluno().getId(), transacao.getAluno().getNome())
        );
    }).toList();

    int saldo = transacoes.stream().mapToInt(Transacao::getMontante).sum();
    return new ExtratoProfessorDTO(saldo, dtoList);
}
}
