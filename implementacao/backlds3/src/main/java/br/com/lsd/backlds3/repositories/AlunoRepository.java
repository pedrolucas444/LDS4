package br.com.lsd.backlds3.repositories;

import br.com.lsd.backlds3.models.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    Aluno findByCpf(String cpf);
}
