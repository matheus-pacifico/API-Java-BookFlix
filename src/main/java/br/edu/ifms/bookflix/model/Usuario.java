package br.edu.ifms.bookflix.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Usuario implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String nome;
	private String telefone;
	
	@JsonIgnore
	@OneToOne(mappedBy ="usuario")	
	private Autenticacao autenticacao;
	
	@OneToOne(mappedBy = "usuario")
	private Professor professores;
	
	@OneToOne(mappedBy = "usuario")
	private Aluno alunos;

	@JsonIgnore
	@OneToMany(mappedBy = "usuario")
	private List<Avaliacao> avaliacoes = new ArrayList<Avaliacao>();
	
	public Usuario() {
		// TODO Auto-generated constructor stub
	}

	public Usuario(Integer id, String nome, String telefone, Autenticacao autenticacao, Professor professores,
			Aluno alunos) {
		super();
		this.id = id;
		this.nome = nome;
		this.telefone = telefone;
		this.autenticacao = autenticacao;
		this.professores = professores;
		this.alunos = alunos;
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

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Autenticacao getAutenticacao() {
		return autenticacao;
	}

	public void setAutenticacao(Autenticacao autenticacao) {
		this.autenticacao = autenticacao;
	}

	public Aluno getAlunos() {
		return alunos;
	}

	public void setAlunos(Aluno alunos) {
		this.alunos = alunos;
	}

	public Professor getProfessores() {
		return professores;
	}

	public void setProfessores(Professor professores) {
		this.professores = professores;
	}

	public List<Avaliacao> getAvaliacoes() {
		return avaliacoes;
	}

	public void setAvaliacoes(List<Avaliacao> avaliacoes) {
		this.avaliacoes = avaliacoes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}	

}
