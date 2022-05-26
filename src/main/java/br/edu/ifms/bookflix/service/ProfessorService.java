package br.edu.ifms.bookflix.service;

import br.edu.ifms.bookflix.model.Professor;
import br.edu.ifms.bookflix.repository.ProfessorRepository;
import br.edu.ifms.bookflix.dto.ProfessorDTO;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfessorService {

	@Autowired
	private ProfessorRepository professores;
	
	public List<Professor> findAll() {
		return professores.findAll();
	}
	
	public void deleteById(Integer id) {
		professores.deleteById(id);
	}
	
	public void save(Professor professor) {
		professores.saveAndFlush(professor);
	}
	
	public Professor findById(Integer id) {
		Optional<Professor> objeto = professores.findById(id);
		Professor objetoaux = objeto.get();
		return objetoaux;
	}
	
	public Professor insert (Professor obj) {
		obj.setId(null);
		return professores.save(obj);
	}

	public Professor update(Professor objeto) {
		Professor novoObjeto = findById(objeto.getId());
		updateData(novoObjeto, objeto);
		return professores.save(novoObjeto);
	}
	
	public Professor fromDTO(ProfessorDTO objetoDTO) {
		return new Professor(objetoDTO.getId(), objetoDTO.getSiape(), null);
	}
	
	private void updateData(Professor novoObjeto, Professor objeto) {
		novoObjeto.setSiape(objeto.getSiape());
	}
}
