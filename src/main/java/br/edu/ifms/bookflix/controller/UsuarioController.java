package br.edu.ifms.bookflix.controller;
  
import br.edu.ifms.bookflix.model.Usuario;

import br.edu.ifms.bookflix.service.UsuarioService;

import br.edu.ifms.bookflix.dto.UsuarioDTO;

import java.net.URI; 
import java.util.List; 
import java.util.stream.Collectors;
  
import jakarta.validation.Valid;
  
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.PathVariable; 
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RequestMethod; 
import org.springframework.web.bind.annotation.RestController; 
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
  
@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {
  
   @Autowired
   private UsuarioService usuarioServices;
  
   @RequestMapping(value="/mostrar/{id}", method = RequestMethod.GET)
   public ResponseEntity<Usuario> find(@PathVariable Integer id) { 
	   Usuario objeto = usuarioServices.find(id); 
	   return ResponseEntity.ok().body(objeto); 
   }
   
   @RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody UsuarioDTO objetoDTO) {
		Usuario objeto = usuarioServices.fromNewDTO(objetoDTO);
		objeto = usuarioServices.insert(objeto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}").buildAndExpand(objeto.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody UsuarioDTO objetoDTO, @PathVariable Integer id) {
		Usuario objeto = usuarioServices.fromDTO(objetoDTO);
		objeto.setId(id);
		objeto = usuarioServices.update(objeto);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@RequestBody Usuario objeto, @PathVariable Integer id){
		usuarioServices.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/deletarporid/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteById(@PathVariable Integer id){
		usuarioServices.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/mostrar", method = RequestMethod.GET)
	public ResponseEntity<List<UsuarioDTO>> findAll() {		
		List<Usuario> lista = usuarioServices.findAll();
		List<UsuarioDTO> listaDTO = lista.stream().map(objeto -> new UsuarioDTO(objeto)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDTO);
	}
  
}
 