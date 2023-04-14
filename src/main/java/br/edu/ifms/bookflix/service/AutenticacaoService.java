package br.edu.ifms.bookflix.service;

import br.edu.ifms.bookflix.model.Autenticacao;

import br.edu.ifms.bookflix.repository.AutenticacaoRepository;

import br.edu.ifms.bookflix.dto.AutenticacaoDTO;

import br.edu.ifms.bookflix.service.exception.DataIntegrityException;
import br.edu.ifms.bookflix.service.exception.ObjectNotFoundException;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutenticacaoService {

	@Autowired
	private AutenticacaoRepository autenticacoesRepository;
	private AutenticacaoDTO autenticacoesDTO = new AutenticacaoDTO();
	
	public Autenticacao find(Integer id) {
		Optional<Autenticacao> objeto = autenticacoesRepository.findById(id); 
		return objeto.orElseThrow(() -> new ObjectNotFoundException( 
				 "Autenticação não encontrada! Id: " + id));		
	}
	
	@Transactional
	public Autenticacao insert (Autenticacao objeto) {
		objeto.setId(null);
		return autenticacoesRepository.save(objeto);
		
	}
	
	public Autenticacao update(Autenticacao objetoEditado) {
		Autenticacao objetoAtualizado = find(objetoEditado.getId());
		objetoAtualizado = objetoEditado;
		return autenticacoesRepository.save(objetoAtualizado);
	}
	
	@Transactional
	public void delete(Integer id, Autenticacao objeto) {
		if(!objeto.equals(find(id))) {
			throw new IllegalArgumentException("A autenticação a ser removida é diferente da autenticação cadastrada no banco de dados");
		}
		deleteById(id);
	}
	
	public List<Autenticacao> findAll() {
		return autenticacoesRepository.findAll();
	}
	
	@Transactional
	public void deleteById(Integer id) {
		find(id);
		try {
			autenticacoesRepository.deleteById(id);	
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível remover. Verifique a integridade referencial.");
		}
	}
	
	public void save(Autenticacao autenticacao) {
		autenticacoesRepository.saveAndFlush(autenticacao);
	}
	
	public Autenticacao fromDTO(AutenticacaoDTO objetoDTO) {
		return autenticacoesDTO.fromDTO(objetoDTO);
	}
	
	public Autenticacao fromNewDTO(AutenticacaoDTO objetoNewDTO) {
		return autenticacoesDTO.fromNewDTO(objetoNewDTO);
	}
	
	public Autenticacao autenticacaoWithoutObra(Autenticacao autenticacao) {
		return autenticacoesDTO.autenticacaoWithoutObra(autenticacao);
	}
    
    public void intParamaterValidator(String param) {
    	if(!param.matches("[0-9]+")) {
    		throw new IllegalArgumentException("O parâmetro tem que ser um número inteiro");
    	}
    }
    
    public void validateAvaliacaoId(Integer paramPathId, Integer obraBodyId) {
    	if(!paramPathId.equals(obraBodyId)) {
    		throw new IllegalArgumentException("O id da URL é diferente do id da avaliação informada no corpo da solicitação");
    	}
    }
	
	
}
