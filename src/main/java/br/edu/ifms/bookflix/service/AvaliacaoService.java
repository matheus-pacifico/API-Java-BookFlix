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
	private AvaliacaoDTO avaliacoesDTO = new AvaliacaoDTO();
	
	public Avaliacao find(Integer id) {
		Optional<Avaliacao> objeto = avaliacoesRepository.findById(id);
		return objeto.orElseThrow(() -> new ObjectNotFoundException( 
				 "Avaliação não encontrada! Id: " + id));		
	}
	
	@Transactional
	public Avaliacao insert (Avaliacao objeto) {
		objeto.setId(null);
		return avaliacoesRepository.save(objeto);
	}
	
	public Avaliacao update(Avaliacao objetoEditado) {
		Avaliacao objetoAtualizado = find(objetoEditado.getId());
		objetoAtualizado = objetoEditado;
		return avaliacoesRepository.save(objetoAtualizado);
	}
	
	@Transactional
	public void delete(Integer id, Avaliacao objeto) {
		if(!objeto.equals(find(id))) {
			throw new IllegalArgumentException("A avaliação a ser removida é diferente da avaliação cadastrada no banco de dados");
		}
		deleteById(id);
		
	}
	
	public List<Avaliacao> findAll() {
		return avaliacoesDTO.listOfAvaliacoesWithoutUsuariosDataExceptName(avaliacoesRepository.findAll());
	}
	
	@Transactional
	public void deleteById(Integer id) {
		find(id);
		try {
			avaliacoesRepository.deleteById(id);	
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível remover. Verifique a integridade referencial.");
		}
	}
	
	public void save(Avaliacao avaliacao) {
		avaliacoesRepository.saveAndFlush(avaliacao);
	}
	
	public Avaliacao fromDTO(AvaliacaoDTO objetoDTO) {
		return avaliacoesDTO.fromDTO(objetoDTO);
	}
	
	public Avaliacao fromNewDTO(AvaliacaoDTO objetoNewDTO) {
		return avaliacoesDTO.fromNewDTO(objetoNewDTO);
	}
	
	public Avaliacao avaliacaoWithoutUsuariosDataExceptName(Avaliacao avaliacao) {
		return avaliacoesDTO.avaliacaoWithoutUsuariosDataExceptName(avaliacao);
	}
    
    public void intParamaterValidator(String param) {
    	if(!param.matches("[0-9]+")) {
    		throw new IllegalArgumentException("O parâmetro tem que ser um número inteiro");
    	}
    }
    
    public void validateAvaliacaoId(Integer paramPathId, Integer avaliacaoBodyId) {
    	if(!paramPathId.equals(avaliacaoBodyId)) {
    		throw new IllegalArgumentException("O id da URL é diferente do id da avaliação informada no corpo da solicitação");
    	}
    }

}
