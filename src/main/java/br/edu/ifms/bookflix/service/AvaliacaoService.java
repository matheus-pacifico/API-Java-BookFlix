package br.edu.ifms.bookflix.service;

import br.edu.ifms.bookflix.model.Avaliacao;

import br.edu.ifms.bookflix.repository.AvaliacaoRepository;

import br.edu.ifms.bookflix.dto.AvaliacaoDTO;

import br.edu.ifms.bookflix.service.exception.DataIntegrityException;
import br.edu.ifms.bookflix.service.exception.ObjectNotFoundException;

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
	private AvaliacaoDTO avaliacoesDTO;
	
	public Avaliacao find(Integer id) {
		Optional<Avaliacao> objeto = avaliacoesRepository.findById(id);
		return objeto.orElseThrow(() -> new ObjectNotFoundException( 
				 "Avaliação não encontrada! Id: " + id));		
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
	
	public List<Avaliacao> findAll() {
		return avaliacoesDTO.listOfAvaliacoesWithoutUsuariosDataExceptName(avaliacoesRepository.findAll());
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
	
	public Avaliacao fromDTO(AvaliacaoDTO objetoDTO) {
		return avaliacoesDTO.fromDTO(objetoDTO);
	}
	
	public Avaliacao fromNewDTO(AvaliacaoDTO objetoNewDTO) {
		return avaliacoesDTO.fromNewDTO(objetoNewDTO);
	}
	
	private void updateData(Avaliacao novoObjeto, Avaliacao objeto) {
		novoObjeto.setComentario(objeto.getComentario());
		novoObjeto.setNota(objeto.getNota());
		novoObjeto.setUsuario(objeto.getUsuario());
		novoObjeto.setObra(objeto.getObra());
	}
	
	public Avaliacao avaliacaoWithoutUsuariosDataExceptName(Avaliacao avaliacao) {
		return avaliacoesDTO.avaliacaoWithoutUsuariosDataExceptName(avaliacao);
	}

}
