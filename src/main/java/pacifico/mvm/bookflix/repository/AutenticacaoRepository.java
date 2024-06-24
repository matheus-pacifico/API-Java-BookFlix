package pacifico.mvm.bookflix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pacifico.mvm.bookflix.model.Autenticacao;


@Repository
public interface AutenticacaoRepository extends JpaRepository<Autenticacao, Integer>{

}
