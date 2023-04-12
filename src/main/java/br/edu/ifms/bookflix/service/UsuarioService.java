package br.edu.ifms.bookflix.service;

import br.edu.ifms.bookflix.model.Usuario;

import br.edu.ifms.bookflix.repository.UsuarioRepository;

import br.edu.ifms.bookflix.dto.UsuarioDTO;

import br.edu.ifms.bookflix.service.exception.DataIntegrityException;
import br.edu.ifms.bookflix.service.exception.ObjectNotFoundException;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuariosRepository;
	private UsuarioDTO usuariosDTO = new UsuarioDTO();
	
	public Usuario find(Integer id) {
		Optional<Usuario> objeto = usuariosRepository.findById(id); 
		return objeto.orElseThrow(() -> new ObjectNotFoundException( 
				 "Usuário não encontrado! Id: " + id));		
	}
	
	@Transactional
	public Usuario insert (Usuario obj) {
		obj.setId(null);
		return usuariosRepository.save(obj);
	}
	
	public List<Usuario> findAll() {
		return usuariosRepository.findAll();
	}
	
	@Transactional
	public void deleteById(Integer id) {
		usuariosRepository.deleteById(id);
	}
	
	public void save(Usuario usuario) {
		usuariosRepository.saveAndFlush(usuario);
	}
	
	public Optional<Usuario> findById(Integer id) {
		return usuariosRepository.findById(id);
	}
	
	@Transactional
	public void delete(Integer id) {
		find(id);
		try {
			usuariosRepository.deleteById(id);	
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível remover. Verifique a integridade referencial.");
		}
	}
	
	public Usuario fromDTO(UsuarioDTO objetoDTO) {
		return usuariosDTO.fromDTO(objetoDTO);
	}
	
	public Usuario fromNewDTO(UsuarioDTO objetoNewDTO) {
		return usuariosDTO.fromNewDTO(objetoNewDTO);
	}

	public Usuario update(Usuario objeto) {
		Usuario novoObjeto = find(objeto.getId());
		updateData(objeto, novoObjeto);
		return usuariosRepository.save(novoObjeto);
	}
	
	private void updateData(Usuario objeto, Usuario novoObjeto) {
		novoObjeto.setAutenticacao(objeto.getAutenticacao());
		novoObjeto.setNome(objeto.getNome());
		novoObjeto.setProfessor(objeto.getProfessor());
		novoObjeto.setAluno(objeto.getAluno());
		novoObjeto.setAvaliacoes(objeto.getAvaliacoes());
	}
	
	public Usuario usuarioWithoutAvaliacaoDasObras(Usuario usuario) {
		return usuariosDTO.usuarioWithoutAvaliacaoDasObras(usuario);
	}
	
	public List<Usuario> usuariosListWithoutAvaliacoesDasObras(List<Usuario> usuarios) {
		return usuariosDTO.usuariosListWithoutAvaliacoesDasObras(usuarios);
	}
	
}
