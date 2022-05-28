package br.edu.ifms.bookflix.dto;

import br.edu.ifms.bookflix.model.Usuario;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;


public class AutenticacaoNewDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String email;
	@NotBlank(message="Preenchimento obrigatório")
	@Length(min=8, max=50, message="A senha deve ter no mínimo 8 caracteres")
	private String senha;
	private Usuario usuario;
	
	public AutenticacaoNewDTO() {
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
