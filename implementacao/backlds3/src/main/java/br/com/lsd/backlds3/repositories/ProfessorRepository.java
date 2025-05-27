package br.com.lsd.backlds3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.lsd.backlds3.models.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    Professor findByCpf(String cpf);
}
