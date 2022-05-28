package br.edu.ifms.bookflix.dto;

import br.edu.ifms.bookflix.model.Avaliacao;
import br.edu.ifms.bookflix.model.Obra;
import br.edu.ifms.bookflix.model.Usuario;

import java.io.Serializable;

public class AvaliacaoDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;	
	private String comentario;
	private int nota;
	private Usuario usuario;
	private Obra obra;
			
	public AvaliacaoDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public AvaliacaoDTO(Avaliacao avaliacao) {
		this.id = avaliacao.getId();
		this.comentario = avaliacao.getComentario();
		this.nota = avaliacao.getNota();
		this.usuario = avaliacao.getUsuario();
		this.obra = avaliacao.getObra();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public int getNota() {
		return nota;
	}

	public void setNota(int nota) {
		this.nota = nota;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Obra getObra() {
		return obra;
	}

	public void setObra(Obra obra) {
		this.obra = obra;
	}
	
}
