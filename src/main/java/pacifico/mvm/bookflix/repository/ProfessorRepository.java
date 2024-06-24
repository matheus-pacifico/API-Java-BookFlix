package pacifico.mvm.bookflix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pacifico.mvm.bookflix.model.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Integer>{

}
