package br.edu.ifms.bookflix.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Obra implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String isbn;
	private String titulo;
	private String area;
	private String genero;
	private String autor;
	private String nome_arquivo;
	private String caminho_arquivo;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="professor_id")
	private Professor professor;
	
	@OneToMany(mappedBy = "obra")
	private List<Avaliacao> avaliacoes = new ArrayList<Avaliacao>();
	
	public Obra() {
		// TODO Auto-generated constructor stub
	}

	public Obra(Integer id, String isbn, String titulo, String area, String genero, String autor, String nome_arquivo,
			String caminho_arquivo, Professor professor) {
		super();
		this.id = id;
		this.isbn = isbn;
		this.titulo = titulo;
		this.area = area;
		this.genero = genero;
		this.autor = autor;
		this.nome_arquivo = nome_arquivo;
		this.caminho_arquivo = caminho_arquivo;
		this.professor = professor;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getNomeArquivo() {
		return nome_arquivo;
	}

	public void setNomeArquivo(String nome_arquivo) {
		this.nome_arquivo = nome_arquivo;
	}

	public String getCaminhoArquivo() {
		return caminho_arquivo;
	}

	public void setCaminhoArquivo(String caminho_arquivo) {
		this.caminho_arquivo = caminho_arquivo;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
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
		Obra other = (Obra) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}	
   
}
