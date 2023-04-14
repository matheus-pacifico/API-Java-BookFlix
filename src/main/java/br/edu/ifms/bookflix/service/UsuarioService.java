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

	public Usuario update(Usuario objetoEditado) {
		Usuario objetoAtualizado = find(objetoEditado.getId());
		objetoAtualizado = objetoEditado;
		return usuariosRepository.save(objetoAtualizado);
	}
	
	@Transactional
	public void delete(Integer id, Usuario objeto) {
		if(!objeto.equals(find(id))) {
			throw new IllegalArgumentException("O usuário a ser removido é diferente do usuário cadastrado no banco de dados");
		}
		deleteById(id);
	}
	
	public List<Usuario> findAll() {
		return usuariosRepository.findAll();
	}
	
	@Transactional
	public void deleteById(Integer id) {
		find(id);
		try {
			usuariosRepository.deleteById(id);	
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível remover. Verifique a integridade referencial.");
		}
	}
	
	public void save(Usuario usuario) {
		usuariosRepository.saveAndFlush(usuario);
	}
	
	public Optional<Usuario> findById(Integer id) {
		return usuariosRepository.findById(id);
	}
	
	public Usuario fromDTO(UsuarioDTO objetoDTO) {
		return usuariosDTO.fromDTO(objetoDTO);
	}
	
	public Usuario fromNewDTO(UsuarioDTO objetoNewDTO) {
		return usuariosDTO.fromNewDTO(objetoNewDTO);
	}
	
	public Usuario usuarioWithoutAvaliacaoDasObras(Usuario usuario) {
		return usuariosDTO.usuarioWithoutAvaliacaoDasObras(usuario);
	}
    
    public void intParamaterValidator(String param) {
    	if(!param.matches("[0-9]+")) {
    		throw new IllegalArgumentException("O parâmetro tem que ser um número inteiro");
    	}
    }
    
    public void validateUsuarioId(Integer paramPathId, Integer usuarioBodyId) {
    	if(!paramPathId.equals(usuarioBodyId)) {
    		throw new IllegalArgumentException("O id da URL é diferente do id do usuário informado no corpo da solicitação");
    	}
    }
	
}
