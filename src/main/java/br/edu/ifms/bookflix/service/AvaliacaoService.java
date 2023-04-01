package br.edu.ifms.bookflix.service;

import br.edu.ifms.bookflix.model.Avaliacao;

import br.edu.ifms.bookflix.repository.AvaliacaoRepository;

import br.edu.ifms.bookflix.dto.AvaliacaoDTO;

import br.edu.ifms.bookflix.service.exception.DataIntegrityException;
import br.edu.ifms.bookflix.service.exception.ObjectNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AvaliacaoService {

	@Autowired
	private AvaliacaoRepository avaliacoesRepository;
	
	public Avaliacao find(Integer id) {
		Optional<Avaliacao> objeto = avaliacoesRepository.findById(id);
		return objeto.orElseThrow(() -> new ObjectNotFoundException( 
				 "Objeto não encontrado! Id: " + id /*+ ", Tipo: " + Avaliacao.class.getName()*/));		
	}
	
	@Transactional
	public Avaliacao insert (Avaliacao objeto) {
		objeto.setId(null);
		avaliacoesRepository.save(objeto);
		return objeto;
	}
	
	public Avaliacao update(Avaliacao objeto) {
		Avaliacao novoObjeto = find(objeto.getId());
		updateData(novoObjeto, objeto);
		return avaliacoesRepository.save(novoObjeto);
	}
	
	@Transactional
	public void delete(Integer id) {
		find(id);
		try {
			avaliacoesRepository.deleteById(id);	
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível remover. Verifique a integridade referencial.");
		}
	}	
	
	public Avaliacao fromDTO(AvaliacaoDTO objetoDTO) {
		return new Avaliacao(objetoDTO.getId(), objetoDTO.getComentario(), objetoDTO.getNota(),
				objetoDTO.getUsuario(), objetoDTO.getObra());
	}
	
	public Avaliacao fromNewDTO(AvaliacaoDTO objetoNewDTO) {
		return new Avaliacao(null, objetoNewDTO.getComentario(), objetoNewDTO.getNota(), objetoNewDTO.getUsuario(), objetoNewDTO.getObra());
	}
	
	private void updateData(Avaliacao novoObjeto, Avaliacao objeto) {
		novoObjeto.setComentario(objeto.getComentario());
		novoObjeto.setNota(objeto.getNota());
		novoObjeto.setUsuario(objeto.getUsuario());
		novoObjeto.setObra(objeto.getObra());
	}
	
	public List<Avaliacao> findAll() {
		return listaAvaliacoesSemDadosDoUsuarioExcetoNome(avaliacoesRepository.findAll());
	}
	
	@Transactional
	public void deleteById(Integer id) {
		avaliacoesRepository.deleteById(id);
	}
	
	public void save(Avaliacao avaliacao) {
		avaliacoesRepository.saveAndFlush(avaliacao);
	}
	
	public Optional<Avaliacao> findById(Integer id) {
		return avaliacoesRepository.findById(id);
	}
	
	public Avaliacao avaliacaoSemDadosDoUsuarioExcetoNome(Avaliacao avaliacao) {
		avaliacao.getUsuario().setAutenticacao(null);
		avaliacao.getUsuario().setProfessor(null);
		avaliacao.getUsuario().setAluno(null);
		avaliacao.getUsuario().setAvaliacoes(null);
		avaliacao.getUsuario().setId(null);
		return avaliacao;
	}
	
	public List<Avaliacao> listaAvaliacoesSemDadosDoUsuarioExcetoNome(List<Avaliacao> avaliacoes) {
		List<Avaliacao> avaliacoesSemDadosExcetoNome = new ArrayList<>();
		avaliacoes.forEach(a -> avaliacaoSemDadosDoUsuarioExcetoNome(a));
		
		avaliacoesSemDadosExcetoNome.addAll(avaliacoes);
		
		return avaliacoesSemDadosExcetoNome;
	}
	
}
