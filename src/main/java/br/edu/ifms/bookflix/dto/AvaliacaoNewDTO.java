package br.edu.ifms.bookflix.dto;

import br.edu.ifms.bookflix.model.Obra;
import br.edu.ifms.bookflix.model.Usuario;

import java.io.Serializable;

public class AvaliacaoNewDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String comentario;
	private int nota;
	private Usuario usuario;
	private Obra obra;
	
	public AvaliacaoNewDTO() {
		// TODO Auto-generated constructor stub
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
