package br.edu.ifms.bookflix.dto;

import br.edu.ifms.bookflix.model.Autenticacao;
import br.edu.ifms.bookflix.model.Usuario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

public class AutenticacaoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	@Email
	@NotBlank(message="Preenchimento obrigatório")
	private String email;
	@NotBlank(message="Preenchimento obrigatório")
	@Length(min=8, max=50, message="A senha deve ter no mínimo 8 caracteres")
	private String senha;
	private Usuario usuario;
	
	public AutenticacaoDTO() {
		//TODO Auto-generated constructor stub
	}

	public AutenticacaoDTO(Autenticacao objeto) {
		this.id = objeto.getId();
		this.email = objeto.getEmail();
		this.senha = objeto.getSenha();
		this.usuario = objeto.getUsuario();
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
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
	
	public Autenticacao fromDTO(AutenticacaoDTO objetoDTO) {
		return new Autenticacao(objetoDTO.getId(), objetoDTO.getEmail(), objetoDTO.getSenha(), 
			objetoDTO.getUsuario());
	}
	
	public Autenticacao fromNewDTO(AutenticacaoDTO objetoNewDTO) {
		return new Autenticacao(null, objetoNewDTO.getEmail(), objetoNewDTO.getSenha(), objetoNewDTO.getUsuario());
	}
	
	public Autenticacao autenticacaoWithoutObra(Autenticacao autenticacao) {
		if(autenticacao.getUsuario().getProfessor() != null) autenticacao.getUsuario().getProfessor().setObras(null);
		
		return autenticacao;
	}
	
	public List<Autenticacao> autenticacoesListWithoutObras(List<Autenticacao> autenticacoes) {
		List<Autenticacao> autenticacoesSemObra = new ArrayList<>();
		autenticacoes.forEach(a -> autenticacaoWithoutObra(a));
		
		autenticacoesSemObra.addAll(autenticacoes);
		
		return autenticacoesSemObra;
	}
	
}
