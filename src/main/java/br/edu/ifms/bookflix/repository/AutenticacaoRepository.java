package br.edu.ifms.bookflix.repository;

import br.edu.ifms.bookflix.model.Autenticacao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AutenticacaoRepository extends JpaRepository<Autenticacao, Integer>{

}
