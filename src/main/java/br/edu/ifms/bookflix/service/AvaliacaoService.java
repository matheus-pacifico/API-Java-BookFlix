package br.edu.ifms.bookflix.service;

import br.edu.ifms.bookflix.model.Avaliacao;
import br.edu.ifms.bookflix.repository.AvaliacaoRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvaliacaoService {

	@Autowired
	private AvaliacaoRepository avaliacoes;
	
	public List<Avaliacao> findAll() {
		return avaliacoes.findAll();
	}
	
	public void deleteById(Integer id) {
		avaliacoes.deleteById(id);
	}
	
	public void save(Avaliacao avaliacao) {
		avaliacoes.saveAndFlush(avaliacao);
	}
	
	public Optional<Avaliacao> findById(Integer id) {
		return avaliacoes.findById(id);
	}
	
}
