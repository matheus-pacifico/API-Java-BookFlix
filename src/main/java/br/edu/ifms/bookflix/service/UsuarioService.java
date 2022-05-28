package br.edu.ifms.bookflix.service;

import br.edu.ifms.bookflix.model.Usuario;
import br.edu.ifms.bookflix.repository.UsuarioRepository;
import br.edu.ifms.bookflix.service.exception.ObjectNotFoundException;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuariosRepository;
	
	public List<Usuario> findAll() {
		return usuariosRepository.findAll();
	}
	
	public void deleteById(Integer id) {
		usuariosRepository.deleteById(id);
	}
	
	public void save(Usuario usuario) {
		usuariosRepository.saveAndFlush(usuario);
	}
	
	public Optional<Usuario> findById(Integer id) {
		return usuariosRepository.findById(id);
	}
	
	public Usuario find(Integer id) {
		Optional<Usuario> objeto = usuariosRepository.findById(id); 
		return objeto.orElseThrow(() -> new ObjectNotFoundException( 
				 "Usuario não encontrado! Id: " + id + ", Tipo: " + Usuario.class.getName()));		
	}
	
}
