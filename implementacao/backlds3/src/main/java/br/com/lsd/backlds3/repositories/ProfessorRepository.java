package br.com.lsd.backlds3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.lsd.backlds3.models.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {

}
