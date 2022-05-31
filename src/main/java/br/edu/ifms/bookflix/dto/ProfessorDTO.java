package br.edu.ifms.bookflix.dto;

import br.edu.ifms.bookflix.model.Professor;
import br.edu.ifms.bookflix.model.Usuario;
import br.edu.ifms.bookflix.model.Obra;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;

public class ProfessorDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	@NotEmpty
	private String siape;
	private Usuario usuario;
	private List<Obra> obras;
				
	public ProfessorDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public ProfessorDTO(Professor objeto) {
		this.id = objeto.getId();
		this.siape = objeto.getSiape();
		this.usuario = objeto.getUsuario();
		this.obras = objeto.getObras();
	}    

	public Integer getId() {
		return id;
	}
		public void setId(Integer id) {
		this.id = id;
	}

	public String getSiape() {
		return siape;
	}

	public void setSiape(String siape) {
		this.siape = siape;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Obra> getObras() {
		return obras;
	}

	public void setObras(List<Obra> obras) {
		this.obras = obras;
	}
	
}
