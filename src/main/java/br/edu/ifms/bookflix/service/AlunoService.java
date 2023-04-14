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

	public Aluno update(Aluno objetoEditado) {
		Aluno objetoAtualizado = find(objetoEditado.getId());
		objetoAtualizado = objetoEditado;
		return alunosRepository.save(objetoAtualizado);
	}
	
	@Transactional
	public void delete(Integer id, Aluno objeto) {
		if(!objeto.equals(find(id))) {
			throw new IllegalArgumentException("O aluno a ser removido é diferente do aluno cadastrado no banco de dados");
		}
		deleteById(id);
	}
		
	public List<Aluno> findAll() {
		return alunosRepository.findAll();
	}
	
	@Transactional
	public void deleteById(Integer id) {
		find(id);
		try {
			alunosRepository.deleteById(id);	
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível remover. Verifique a integridade referencial.");
		}
	}
	
	public void save(Aluno aluno) {
		alunosRepository.saveAndFlush(aluno);
	}
	
	public Aluno fromDTO(AlunoDTO objetoDTO) {
		return alunosDTO.fromDTO(objetoDTO);
	}
	
	public Aluno fromNewDTO(AlunoDTO objetoNewDTO) {
		return alunosDTO.fromNewDTO(objetoNewDTO);
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
    
    public void intParamaterValidator(String param) {
    	if(!param.matches("[0-9]+")) {
    		throw new IllegalArgumentException("O parâmetro tem que ser um número inteiro");
    	}
    }
    
    public void validateAlunoId(Integer paramPathId, Integer alunoBodyId) {
    	if(!paramPathId.equals(alunoBodyId)) {
    		throw new IllegalArgumentException("O id da URL é diferente do id do aluno informado no corpo da solicitação");
    	}
    }
	
}
