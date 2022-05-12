package br.edu.ifms.bookflix.repository;

import br.edu.ifms.bookflix.model.Obra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObraRepository extends JpaRepository<Obra, Integer>{

}
