package br.edu.ifms.bookflix.repository;

import br.edu.ifms.bookflix.model.Aluno;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepository extends JpaRepository <Aluno, Integer>{
	
	Optional<Aluno> findByRa(String ra);

	@Query("SELECT A FROM Aluno A WHERE A.turma = :turma")
	List<Aluno> findAlunosByTurma(@Param("turma") int turma);
	
}
