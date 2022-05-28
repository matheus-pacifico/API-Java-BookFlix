package br.edu.ifms.bookflix.service;

import br.edu.ifms.bookflix.model.Obra;
import br.edu.ifms.bookflix.model.Professor;
import br.edu.ifms.bookflix.repository.ProfessorRepository;
import br.edu.ifms.bookflix.service.exception.ObjectNotFoundException;
import br.edu.ifms.bookflix.dto.ProfessorDTO;

//import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfessorService {

	@Autowired
	private ProfessorRepository professoresRepository;
	
	public List<Professor> findAll() {
		return professoresRepository.findAll();
	}
	
	public Professor find(Integer id) {
		Optional<Professor> objeto = professoresRepository.findById(id); 
		return objeto.orElseThrow(() -> new ObjectNotFoundException( 
				 "Professor não encontrado! Id: " + id + ", Tipo: " + Professor.class.getName()));		
	}
	
	public void deleteById(Integer id) {
		professoresRepository.deleteById(id);
	}
	
	public void save(Professor professor) {
		professoresRepository.saveAndFlush(professor);
	}
	
	public Optional<Professor> findById(Integer id) {
		return professoresRepository.findById(id);
	}
	
	public Professor insert (Professor obj) {
		obj.setId(null);
		return professoresRepository.save(obj);
	}

	public Professor update(Professor objeto) {
		Professor novoObjeto = find(objeto.getId());
		updateData(novoObjeto, objeto);
		return professoresRepository.save(novoObjeto);
	}
	
	public Professor fromDTO(ProfessorDTO objetoDTO) {
		return new Professor(objetoDTO.getId(), objetoDTO.getSiape(), null);
	}
	
	private void updateData(Professor novoObjeto, Professor objeto) {
		novoObjeto.setSiape(objeto.getSiape());
	}
	
	public List<Obra> ListarObrasPostadasPeloProfessor(Integer id){
		Professor professor = find(id);
		return professor.getObras();
	}
	
}
