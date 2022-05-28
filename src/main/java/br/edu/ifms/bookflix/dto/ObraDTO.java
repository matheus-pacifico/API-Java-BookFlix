package br.edu.ifms.bookflix.dto;

import br.edu.ifms.bookflix.model.Obra;
import br.edu.ifms.bookflix.model.Professor;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

public class ObraDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	@NotEmpty(message="Preenchimento obrigat�rio")
	private String isbn;
	private String titulo;
	private String area;
	private String genero;
	private String autor;
	private String nomeArquivo;
	private String caminhoArquivo;
	private Professor professor;
		
	public ObraDTO() {
		// TODO Auto-generated constructor stub
	}

	public ObraDTO(Obra objeto) {
		this.id = objeto.getId();
		this.isbn = objeto.getArea();
		this.titulo = objeto.getTitulo();
		this.area = objeto.getArea();
		this.genero = objeto.getGenero();
		this.autor = objeto.getAutor();
		this.nomeArquivo = objeto.getNomeArquivo();
		this.caminhoArquivo = objeto.getCaminhoArquivo();
		this.professor = objeto.getProfessor();
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
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public String getCaminhoArquivo() {
		return caminhoArquivo;
	}

	public void setCaminhoArquivo(String caminhoArquivo) {
		this.caminhoArquivo = caminhoArquivo;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}		
	
}
