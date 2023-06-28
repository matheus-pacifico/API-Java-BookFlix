package br.edu.ifms.bookflix.dto;

import br.edu.ifms.bookflix.model.Autor;
import br.edu.ifms.bookflix.model.Obra;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;

public class AutorDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	@NotBlank(message="Preenchimento obrigatório")
	private String nome;
	private Obra obra;
	
	public AutorDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public AutorDTO(Autor autor) {
		this.id = autor.getId();
		this.nome = autor.getNome();
		this.obra = autor.getObra();
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

	public Obra getObra() {
		return obra;
	}

	public void setObra(Obra obra) {
		this.obra = obra;
	}	
	
	public Autor fromDTO(AutorDTO objetoDTO) {
		return new Autor(objetoDTO.getId(), objetoDTO.getNome(), objetoDTO.getObra());
	}
	
	public Autor fromNewDTO(AutorDTO objetoNewDTO) {
		return new Autor(null, objetoNewDTO.getNome(), objetoNewDTO.getObra());
	}
	
	public Autor autorWithoutAvaliacoesDaObra(Autor autor) {
		if(autor.getObra() != null) {
			autor.getObra().setAvaliacoes(null);
		}
		return autor;
	}
	
}
