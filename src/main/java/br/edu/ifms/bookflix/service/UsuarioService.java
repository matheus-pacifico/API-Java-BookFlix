package br.edu.ifms.bookflix.service;

import br.edu.ifms.bookflix.model.Usuario;
import br.edu.ifms.bookflix.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarios;
	
	public List<Usuario> findAll() {
		return usuarios.findAll();
	}
	
	public void deleteById(Integer id) {
		usuarios.deleteById(id);
	}
	
	public void save(Usuario usuario) {
		usuarios.saveAndFlush(usuario);
	}
	
	public Optional<Usuario> findById(Integer id) {
		return usuarios.findById(id);
	}
	
	public Usuario findOne(Integer id) {
		List<Usuario> lista = usuarios.findAll();
		for (Usuario usuario : lista) {
			if (usuario.getId() == id) {
				return usuario;
			}
		}
		return null;
	}
	
}
