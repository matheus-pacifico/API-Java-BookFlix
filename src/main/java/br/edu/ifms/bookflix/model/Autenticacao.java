package br.edu.ifms.bookflix.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Autenticacao implements Serializable{
   
    private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String email;
	private String senha;
	
	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;
	
	public Autenticacao() {
		// TODO Auto-generated constructor stub
	}

    public Autenticacao(Integer id, String email, String senha, Usuario usuario) {
		super();
		this.id = id;
		this.email = email;
		this.senha = senha;
		this.usuario = usuario;
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

    public void setSenha(String senha){
        this.senha = senha;
    }

    public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuarios(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Autenticacao other = (Autenticacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
