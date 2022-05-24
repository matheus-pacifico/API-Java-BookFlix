package br.edu.ifms.bookflix.service;

import br.edu.ifms.bookflix.model.Autenticacao;
import br.edu.ifms.bookflix.repository.AutenticacaoRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService {

	@Autowired
	private AutenticacaoRepository autenticacoes;
	
	public List<Autenticacao> findAll() {
		return autenticacoes.findAll();
	}
	
	public void deleteById(Integer id) {
		autenticacoes.deleteById(id);
	}
	
	public void save(Autenticacao autenticacao) {
		autenticacoes.saveAndFlush(autenticacao);
	}
	
	public Optional<Autenticacao> findById(Integer id) {
		return autenticacoes.findById(id);
	}
	
}
