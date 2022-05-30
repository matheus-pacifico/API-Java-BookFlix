package br.edu.ifms.bookflix.dto;

import java.io.Serializable;

import br.edu.ifms.bookflix.model.Professor;

public class ObraNewDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String isbn;
	private String titulo;
	private String area;
	private String genero;
	private String autor;
	private String nome_arquivo;
	private String caminho_arquivo;
	private Professor professor;
		
	public ObraNewDTO() {
		// TODO Auto-generated constructor stub
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
			
}
