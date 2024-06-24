package pacifico.mvm.bookflix.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pacifico.mvm.bookflix.model.Obra;
import pacifico.mvm.bookflix.projection.ObraView;

@Repository
public interface ObraRepository extends JpaRepository<Obra, Integer>{
	
	Optional<Obra> findByIfsn(String ifsn);
	
	@Query(value = "SELECT O FROM Obra O JOIN FETCH Autor A WHERE unaccent(O.ifsn) ILIKE %:q% "
			+ "OR unaccent(O.titulo) ILIKE %:q% OR unaccent(O.area) ILIKE %:q% OR unaccent(O.descricao) ILIKE %:q% "
			+ "OR unaccent(A.nome) ILIKE %:q% "
			+ "ORDER BY CASE WHEN unaccent(O.titulo) ILIKE %:q% THEN 0 WHEN unaccent(O.descricao) ILIKE %:q% THEN 1 "
			+ "WHEN unaccent(O.area) ILIKE %:q% THEN 2 ELSE 3 END"
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
	
	@Query(value = "SELECT O.caminho_arquivo FROM Obra O WHERE O.ifsn = :ifsn AND O.nome_arquivo = :ofn")
	String getPathToDeleteObraFile(@Param("ifsn") String ifsn, @Param("ofn") String originalFileName);
	
}
