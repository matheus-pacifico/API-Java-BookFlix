package br.edu.ifms.bookflix.service;

import br.edu.ifms.bookflix.dto.ProfessorDTO;
import br.edu.ifms.bookflix.model.Obra;
import br.edu.ifms.bookflix.model.Professor;
import br.edu.ifms.bookflix.repository.ProfessorRepository;
import br.edu.ifms.bookflix.service.exception.DataIntegrityException;
import br.edu.ifms.bookflix.service.exception.ObjectNotFoundException;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfessorService {

	@Autowired
	private ProfessorRepository professorRepository;
	private final ProfessorDTO professorDTO = new ProfessorDTO();
	
	public Professor find(Integer id) {
		Optional<Professor> objeto = professorRepository.findById(id); 
		return objeto.orElseThrow(() -> new ObjectNotFoundException( 
				 "Professor não encontrado! Id: " + id));		
	}
	
	@Transactional
	public Professor insert (Professor objeto) {
		objeto.setId(null);
		return professorRepository.save(objeto);
	}

	public Professor update(Professor objetoEditado) {
		Professor objetoAtualizado = find(objetoEditado.getId());
		objetoAtualizado = objetoEditado;
		return professorRepository.save(objetoAtualizado);
	}
	
	@Transactional
	public void delete(Integer id, Professor objeto) {
		if(!objeto.equals(find(id))) {
			throw new IllegalArgumentException("O professor a ser removido é diferente da professor cadastrado no banco de dados");
		}
		deleteById(id);
	}
	
	public List<Professor> findAll() {
		return professorRepository.findAll();
	}
	
	@Transactional
	public void deleteById(Integer id) {
		find(id);
		try {
			professorRepository.deleteById(id);	
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível remover. Verifique a integridade referencial.");
		}
	}
	
	public void save(Professor professor) {
		professorRepository.saveAndFlush(professor);
	}
	
	public Optional<Professor> findById(Integer id) {
		return professorRepository.findById(id);
	}
	
	public Professor fromDTO(ProfessorDTO objetoDTO) {
		return professorDTO.fromDTO(objetoDTO);
	}
	
	public Professor fromNewDTO(ProfessorDTO objetoNewDTO) {
		return professorDTO.fromNewDTO(objetoNewDTO);
	}
	
	public List<Obra> listObrasPostedByProfessor(Integer id){
		Professor professor = find(id);
		return professor.getObras();
	}
    
    public void validateProfessorId(Integer paramPathId, Integer professorBodyId) {
    	if(!paramPathId.equals(professorBodyId)) {
    		throw new IllegalArgumentException("O id da URL é diferente do id do professor informado no corpo da solicitação");
    	}
    }
	
}
