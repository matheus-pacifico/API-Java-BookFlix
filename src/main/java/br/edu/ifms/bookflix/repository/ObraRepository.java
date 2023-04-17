package br.edu.ifms.bookflix.repository;

import br.edu.ifms.bookflix.model.Obra;

import br.edu.ifms.bookflix.projection.ObraView;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	Page<ObraView> searchObra(@Param("q") String pesquisa, Pageable pageable);

	@Query(value = "SELECT O FROM Obra O WHERE unaccent(O.ifsn) ILIKE %:ifsn%")
	Page<ObraView> searchObraByIfsn(@Param("ifsn") String ifsn, Pageable pageable);
	
	@Query(value = "SELECT O FROM Obra O WHERE unaccent(O.titulo) ILIKE %:titulo%")
	Page<ObraView> searchObraByTitulo(@Param("titulo") String titulo, Pageable pageable);
		
	@Query(value = "SELECT O FROM Obra O WHERE unaccent(O.area) ILIKE %:area%")
	Page<ObraView> searchObraByArea(@Param("area") String area, Pageable pageable);
	
	@Query(value = "SELECT O FROM Obra O WHERE O.ano = :ano")
	Page<ObraView> searchObraByAno(@Param("ano") int ano, Pageable pageable);
	
	@Query(value = "SELECT O.caminho_arquivo FROM Obra O WHERE O.ifsn = :ifsn")
	String getCaminhoArquivoByIfsn(@Param("ifsn") String ifsn);
	
}
