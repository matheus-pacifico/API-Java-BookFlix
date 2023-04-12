package br.edu.ifms.bookflix.service;

import br.edu.ifms.bookflix.model.Aluno;

import br.edu.ifms.bookflix.repository.AlunoRepository;

import br.edu.ifms.bookflix.dto.AlunoDTO;

import br.edu.ifms.bookflix.service.exception.DataIntegrityException;
import br.edu.ifms.bookflix.service.exception.ObjectNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlunoService {

	@Autowired
	private AlunoRepository alunosRepository;
	private AlunoDTO alunosDTO = new AlunoDTO();
	
	public Aluno find(Integer id) {
		Optional<Aluno> objeto = alunosRepository.findById(id); 
		return objeto.orElseThrow(() -> new ObjectNotFoundException( 
				 "Aluno não encontrado! Id: " + id));		
	}
	
	@Transactional
	public Aluno insert (Aluno objeto) {
		objeto.setId(null);
		return alunosRepository.save(objeto);
	}

	public Aluno update(Aluno objeto) {
		Aluno novoObjeto = find(objeto.getId());
		updateData(objeto, novoObjeto);
		return alunosRepository.save(novoObjeto);
	}
	
	@Transactional
	public void delete(Integer id) {
		find(id);
		try {
			alunosRepository.deleteById(id);	
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível remover. Verifique a integridade referencial.");
		}
	}
	
	public Aluno fromDTO(AlunoDTO objetoDTO) {
		return alunosDTO.fromDTO(objetoDTO);
	}
	
	public Aluno fromNewDTO(AlunoDTO objetoNewDTO) {
		return alunosDTO.fromNewDTO(objetoNewDTO);
	}
	
	private void updateData(Aluno objeto, Aluno novoObjeto) {
		novoObjeto.setRa(objeto.getRa());
		novoObjeto.setTurma(objeto.getTurma());
		novoObjeto.setUsuario(objeto.getUsuario());
	}
	
	public List<Aluno> findAll() {
		return alunosRepository.findAll();
	}
	
	@Transactional
	public void deleteById(Integer id) {
		alunosRepository.deleteById(id);
	}
	
	public void save(Aluno aluno) {
		alunosRepository.saveAndFlush(aluno);
	}
	
	public Optional<Aluno> findById(Integer id) {
		return alunosRepository.findById(id);
	}
	
	public List<Aluno> findAlunosByTurma(int turma) {		
		return findAll().stream()
				.filter(a -> a.getTurma() == turma)
				.collect(Collectors.toList());
	}
	
	public Optional<Aluno> findByRa(String ra) {
		return findAll().stream()
				.filter(a -> a.getRa().compareTo(ra) == 0).findFirst();
	}
	
}
