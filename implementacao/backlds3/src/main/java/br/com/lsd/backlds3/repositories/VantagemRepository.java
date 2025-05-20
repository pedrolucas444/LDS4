package br.com.lsd.backlds3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.lsd.backlds3.models.Vantagem;

@Repository
public interface VantagemRepository extends JpaRepository<Vantagem, Long> {

}
