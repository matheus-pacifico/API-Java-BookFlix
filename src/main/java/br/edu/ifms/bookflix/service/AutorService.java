package br.edu.ifms.bookflix.service;

import br.edu.ifms.bookflix.dto.AutorDTO;
import br.edu.ifms.bookflix.model.Autor;
import br.edu.ifms.bookflix.repository.AutorRepository;
import br.edu.ifms.bookflix.service.exception.DataIntegrityException;
import br.edu.ifms.bookflix.service.exception.ObjectNotFoundException;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorService {
	
	@Autowired
	private AutorRepository autorRepository;
	private final AutorDTO autorDTO = new AutorDTO();
	
	public Autor find(Integer id) {
		Optional<Autor> objeto = autorRepository.findById(id); 
		return objeto.orElseThrow(() -> new ObjectNotFoundException( 
				 "Autor não encontrado! Id: " + id));		
	}
	
	@Transactional
	public Autor insert (Autor objeto) {
		objeto.setId(null);
		return autorRepository.save(objeto);
	}
	
	public Autor update(Autor objetoEditado) {
		Autor objetoAtualizado = find(objetoEditado.getId());
		objetoAtualizado = objetoEditado;
		return autorRepository.save(objetoAtualizado);
	}
	
	@Transactional
	public void delete(Integer id, Autor objeto) {
		if(!objeto.equals(find(id))) {
			throw new IllegalArgumentException("O autor a ser removido é diferente do autor cadastrado no banco de dados");
		}
		deleteById(id);		
	}
	
	public List<Autor> findAll() {
		return autorRepository.findAll();
	}
	
	public void deleteById(Integer id) {
		find(id);
		try {
			autorRepository.deleteById(id);	
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível remover. Verifique a integridade referencial.");
		}		
	}
	
	public void save(Autor avaliacao) {
		autorRepository.saveAndFlush(avaliacao);
	}
	
	public Autor fromDTO(AutorDTO objetoDTO) {
		return autorDTO.fromDTO(objetoDTO);
	}
	
	public Autor fromNewDTO(AutorDTO objetoNewDTO) {
		return autorDTO.fromNewDTO(objetoNewDTO);
	}
	
	public Autor autorWithoutAvaliacoesDaObra(Autor autor) {
		return autorDTO.autorWithoutAvaliacoesDaObra(autor);
	}
    
    public void validateAutorId(Integer paramPathId, Integer autorBodyId) {
    	if(!paramPathId.equals(autorBodyId)) {
    		throw new IllegalArgumentException("O id da URL é diferente do id do autor informado no corpo da solicitação");
    	}
    }

}
