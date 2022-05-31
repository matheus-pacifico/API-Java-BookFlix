package br.edu.ifms.bookflix.dto;

import br.edu.ifms.bookflix.model.Aluno;

import br.edu.ifms.bookflix.model.Usuario;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

public class AlunoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	@NotEmpty
	private String ra;
	private int turma;
	private Usuario usuario;
				
	public AlunoDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public AlunoDTO(Aluno objeto) {
		this.id = objeto.getId();
		this.ra = objeto.getRa();
		this.turma = objeto.getTurma();
		this.usuario = objeto.getUsuario();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRa() {
		return ra;
	}

	public void setRa(String ra) {
		this.ra = ra;
	}

	public int getTurma() {
		return turma;
	}

	public void setTurma(int turma) {
		this.turma = turma;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	} 
	
}
