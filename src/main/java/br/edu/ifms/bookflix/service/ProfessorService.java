package br.edu.ifms.bookflix.service;

import br.edu.ifms.bookflix.model.Professor;

import br.edu.ifms.bookflix.repository.ProfessorRepository;

import br.edu.ifms.bookflix.dto.ProfessorDTO;

import br.edu.ifms.bookflix.model.Obra;
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
	private ProfessorRepository professoresRepository;
	
	public List<Professor> findAll() {
		return professoresRepository.findAll();
	}
	
	public Professor find(Integer id) {
		Optional<Professor> objeto = professoresRepository.findById(id); 
		return objeto.orElseThrow(() -> new ObjectNotFoundException( 
				 "Professor não encontrado! Id: " + id + ", Tipo: " + Professor.class.getName()));		
	}
	
	@Transactional
	public void deleteById(Integer id) {
		professoresRepository.deleteById(id);
	}
	
	public void save(Professor professor) {
		professoresRepository.saveAndFlush(professor);
	}
	
	public Optional<Professor> findById(Integer id) {
		return professoresRepository.findById(id);
	}
	
	@Transactional
	public Professor insert (Professor objeto) {
		objeto.setId(null);
		return professoresRepository.save(objeto);
	}
	
	@Transactional
	public void delete(Integer id) {
		find(id);
		try {
			professoresRepository.deleteById(id);	
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível remover. Verifique a integridade referencial.");
		}
	}

	public Professor update(Professor objeto) {
		Professor novoObjeto = find(objeto.getId());
		updateData(objeto, novoObjeto);
		return professoresRepository.save(novoObjeto);
	}
	
	private void updateData(Professor objeto, Professor novoObjeto) {
		novoObjeto.setSiape(objeto.getSiape());
	}
	
	public Professor fromDTO(ProfessorDTO objetoDTO) {
		Professor professorAuxiliar = new Professor();
		professorAuxiliar.setId(objetoDTO.getId());
		professorAuxiliar.setSiape(objetoDTO.getSiape());
		professorAuxiliar.setUsuario(objetoDTO.getUsuario());
		professorAuxiliar.setObras(objetoDTO.getObras());
		return professorAuxiliar;
	}
	
	public Professor fromNewDTO(ProfessorDTO objetoDTO) {
		return new Professor(null, objetoDTO.getSiape(), objetoDTO.getUsuario());
	}
	
	public List<Obra> ListarObrasPostadasPeloProfessor(Integer id){
		Professor professor = find(id);
		return professor.getObras();
	}
	
}
