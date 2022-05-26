package br.edu.ifms.bookflix.dto;

import br.edu.ifms.bookflix.model.Professor;
import br.edu.ifms.bookflix.model.Usuario;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

public class ProfessorDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer id;
	@NotEmpty(message="Preenchimento obrigatório")
	private int siape;
	private Usuario usuario;
				
	public ProfessorDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public ProfessorDTO(Professor objeto) {
		this.id = objeto.getId();
		this.siape = objeto.getSiape();
		this.usuario = objeto.getUsuario();
	}    

	public Integer getId() {
		return id;
	}
		public void setId(Integer id) {
		this.id = id;
	}

	public int getSiape() {
		return siape;
	}

	public void setSiape(int siape) {
		this.siape = siape;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
