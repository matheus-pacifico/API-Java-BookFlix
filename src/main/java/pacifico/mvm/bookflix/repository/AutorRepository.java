package pacifico.mvm.bookflix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pacifico.mvm.bookflix.model.Autor;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Integer>{

}
