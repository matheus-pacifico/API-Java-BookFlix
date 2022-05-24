package br.edu.ifms.bookflix.service;

import br.edu.ifms.bookflix.model.Aluno;
import br.edu.ifms.bookflix.repository.AlunoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlunoService {

	@Autowired
	private AlunoRepository alunos;
	
	public List<Aluno> findAll() {
		return alunos.findAll();
	}
	
	public void deleteById(Integer id) {
		alunos.deleteById(id);
	}
	
	public void save(Aluno aluno) {
		alunos.saveAndFlush(aluno);
	}
	
	public Optional<Aluno> findById(Integer id) {
		return alunos.findById(id);
	}
	
	public Aluno findOne(Integer id) {
		
		List<Aluno> lista = alunos.findAll();
		for (Aluno aluno : lista) {
			if (aluno.getId()==id) {
				return aluno;
			}
		}
		return null;
	}
	
	public List<String> findAlunosTurma(int turma) {
		List<Aluno> lista = alunos.findAll();
		List<String> alunosTurma = new ArrayList<>();
		for (Aluno aluno: lista) {
			if (aluno.getTurma() == turma) {
				alunosTurma.add(aluno.getUsuario().getNome());
			}
		}
		return alunosTurma;
	}
	
	public Aluno findByRa(int ra) {		
		List<Aluno> lista = alunos.findAll();
		for (Aluno aluno : lista) {
			if (aluno.getRa() == ra) {
				return aluno;
			}
		}
		return null;
	}
	
}
