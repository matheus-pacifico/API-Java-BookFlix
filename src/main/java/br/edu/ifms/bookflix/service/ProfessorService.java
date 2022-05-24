package br.edu.ifms.bookflix.service;

import br.edu.ifms.bookflix.model.Professor;
import br.edu.ifms.bookflix.repository.ProfessorRepository;

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
	
	public Optional<Professor> findById(Integer id) {
		return professores.findById(id);
	}

	public Professor findOne(Integer id) {
		List<Professor> lista = professores.findAll();
		for (Professor professor : lista) {
			if (professor.getId()==id) {
				return professor;
			}
		}
		return null;
	}
	
	public Professor findBySiape(int siape) {		
		List<Professor> lista = professores.findAll();
		for (Professor professor : lista) {
			if (professor.getSiape() == siape) {
				return professor;
			}
		}
		return null;
	}
	
}
