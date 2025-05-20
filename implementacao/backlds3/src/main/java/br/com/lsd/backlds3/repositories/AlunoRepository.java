package br.com.lsd.backlds3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.lsd.backlds3.models.Aluno;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

}
