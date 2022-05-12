package br.edu.ifms.bookflix.repository;

import br.edu.ifms.bookflix.model.Aluno;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepository extends JpaRepository <Aluno, Integer>{

}
