package br.edu.ifms.bookflix.repository;

import br.edu.ifms.bookflix.model.Obra;

import br.edu.ifms.bookflix.projection.ObraView;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ObraRepository extends JpaRepository<Obra, Integer>{
	
	Optional<Obra> findByIfsn(String ifsn);
	
	@Query(value = "SELECT O FROM Obra O WHERE unaccent(O.ifsn) ILIKE %:q% OR unaccent(O.titulo) ILIKE %:q% "
			+ "OR unaccent(O.area) ILIKE %:q% OR unaccent(O.descricao) ILIKE %:q%"
	)
	List<ObraView> searchObra(@Param("q") String pesquisa);

	@Query(value = "SELECT O FROM Obra O WHERE unaccent(O.ifsn) ILIKE %:ifsn%")
	List<ObraView> searchObraByIfsn(@Param("ifsn") String ifsn);
	
	@Query(value = "SELECT O FROM Obra O WHERE unaccent(O.titulo) ILIKE %:titulo%")
	List<ObraView> searchObraByTitulo(@Param("titulo") String titulo);
		
	@Query(value = "SELECT O FROM Obra O WHERE unaccent(O.area) ILIKE %:area%")
	List<ObraView> searchObraByArea(@Param("area") String area);
	
	@Query(value = "SELECT O FROM Obra O WHERE O.ano = :ano")
	List<ObraView> searchObraByAno(@Param("ano") int ano);
	
}
