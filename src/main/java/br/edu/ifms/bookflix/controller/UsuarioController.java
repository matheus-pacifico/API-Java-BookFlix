package br.edu.ifms.bookflix.controller;
  
import br.edu.ifms.bookflix.model.Usuario;

import br.edu.ifms.bookflix.service.UsuarioService;

import br.edu.ifms.bookflix.dto.UsuarioDTO;

import java.net.URI; 
import java.util.List; 
  
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RestController; 
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
  
@RestController
@RequestMapping(value = "/api/v1/usuario")
public class UsuarioController {
  
    @Autowired
    private UsuarioService usuarioServices;
  
    @GetMapping(value = "/mostrar/{id}")
    public ResponseEntity<Usuario> find(@PathVariable String id) { 
    	usuarioServices.intParamaterValidator(id);
    	Usuario objeto = usuarioServices.usuarioWithoutAvaliacaoDasObras(usuarioServices.find(Integer.parseInt(id))); 
    	return ResponseEntity.ok().body(objeto); 
    }
   
    @PostMapping(value = "/adicionar")
    public ResponseEntity<Void> insert(@Valid @RequestBody UsuarioDTO objetoNewDTO) {
		Usuario objeto = usuarioServices.fromNewDTO(objetoNewDTO);
		objeto = usuarioServices.insert(objeto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(objeto.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/atualizar/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody UsuarioDTO objetoDTO, @PathVariable String id) {
		usuarioServices.intParamaterValidator(id);
		usuarioServices.intParamaterValidator(objetoDTO.getId().toString());
		Usuario objeto = usuarioServices.fromDTO(objetoDTO);
		usuarioServices.validateUsuarioId(Integer.parseInt(id), objetoDTO.getId());
		objeto = usuarioServices.update(objeto);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/remover/{id}")
	public ResponseEntity<Void> delete(@RequestBody Usuario objeto, @PathVariable String id) {
		usuarioServices.intParamaterValidator(id);
		usuarioServices.delete(Integer.parseInt(id), objeto);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/deletar/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable String id) {
		usuarioServices.intParamaterValidator(id);
		usuarioServices.deleteById(Integer.parseInt(id));
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value = "/exibir")
	public ResponseEntity<List<Usuario>> findAll() {
		return ResponseEntity.ok().body(usuarioServices.findAll());
	}
	
}
 