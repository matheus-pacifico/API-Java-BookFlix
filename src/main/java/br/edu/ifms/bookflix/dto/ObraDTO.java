package br.edu.ifms.bookflix.dto;

import br.edu.ifms.bookflix.model.Obra;
import br.edu.ifms.bookflix.model.Professor;
import br.edu.ifms.bookflix.model.Avaliacao;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;

public class ObraDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	@NotEmpty(message="Preenchimento obrigat�rio")
	private String ifsn;
	private String titulo;
	private String area;
	private String descricao;
	private String autor;
	private String nome_arquivo;
	private String caminho_arquivo;
	private Professor professor;
	private List<Avaliacao> avaliacoes;
		
	public ObraDTO() {
		// TODO Auto-generated constructor stub
	}

	public ObraDTO(Obra objeto) {
		this.id = objeto.getId();
		this.ifsn = objeto.getIfsn();
		this.titulo = objeto.getTitulo();
		this.area = objeto.getArea();
		this.descricao = objeto.getDescricao();
		this.autor = objeto.getAutor();
		this.nome_arquivo = objeto.getNomeArquivo();
		this.caminho_arquivo = objeto.getCaminhoArquivo();
		this.professor = objeto.getProfessor();
		this.avaliacoes = objeto.getAvaliacoes();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIfsn() {
		return ifsn;
	}

	public void setIfsn(String ifsn) {
		this.ifsn = ifsn;
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
	
}
