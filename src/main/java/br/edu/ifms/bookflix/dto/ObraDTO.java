package br.edu.ifms.bookflix.dto;

import br.edu.ifms.bookflix.model.Obra;
import br.edu.ifms.bookflix.model.Professor;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

public class ObraDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	@NotEmpty(message="Preenchimento obrigatório")
	private String isbn;
	private String titulo;
	private String area;
	private String genero;
	private String descricao;
	private int ano;
	private int pagina;
	private Professor professor;
		
	public ObraDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public ObraDTO(Obra objeto) {
		this.id = objeto.getId();
		this.isbn = objeto.getIsbn();
		this.titulo = objeto.getTitulo();
		this.area = objeto.getArea();
		this.genero = objeto.getGenero();
		this.descricao = objeto.getDescricao();
		this.ano = objeto.getAno();
		this.pagina = objeto.getPagina();
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public int getPagina() {
		return pagina;
	}

	public void setPagina(int pagina) {
		this.pagina = pagina;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}		
	
		
}
