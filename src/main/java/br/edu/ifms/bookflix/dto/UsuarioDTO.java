package br.edu.ifms.bookflix.dto;

import br.edu.ifms.bookflix.model.Usuario;
import br.edu.ifms.bookflix.model.Professor;
import br.edu.ifms.bookflix.model.Aluno;
import br.edu.ifms.bookflix.model.Avaliacao;

import java.io.Serializable;
import java.util.List;

public class UsuarioDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nome;
	private Professor professor;
	private Aluno aluno;
	private List<Avaliacao> avaliacoes;
	
	public UsuarioDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public UsuarioDTO(Usuario objeto) {
		this.id = objeto.getId();
		this.nome = objeto.getNome();
		this.professor = objeto.getProfessor();
		this.aluno = objeto.getAluno();
		this.avaliacoes = objeto.getAvaliacoes();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public List<Avaliacao> getAvaliacoes() {
		return avaliacoes;
	}

	public void setAvaliacoes(List<Avaliacao> avaliacoes) {
		this.avaliacoes = avaliacoes;
	}	

}
