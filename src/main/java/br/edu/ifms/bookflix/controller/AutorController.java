package br.edu.ifms.bookflix.controller;

import br.edu.ifms.bookflix.model.Autor;

import br.edu.ifms.bookflix.service.AutorService;

import br.edu.ifms.bookflix.dto.AutorDTO;

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
@RequestMapping(value = "/autor")
public class AutorController {
	
	@Autowired
	private AutorService autorServices;
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Autor> find(@PathVariable Integer id) {		
		Autor objeto = autorServices.find(id);
		return ResponseEntity.ok().body(objeto);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody AutorDTO objetoNewDTO) {
		Autor objeto = autorServices.fromNewDTO(objetoNewDTO);
		objeto = autorServices.insert(objeto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}").buildAndExpand(objeto.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody AutorDTO objetoDTO, @PathVariable Integer id) {
		Autor objeto = autorServices.fromDTO(objetoDTO);
		objeto.setId(id);
		objeto = autorServices.update(objeto);
		return ResponseEntity.noContent().build();
	}
		
	@RequestMapping(value="/{id}", method= RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@RequestBody Autor objeto, @PathVariable Integer id){
		autorServices.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/deletarporid/{id}", method= RequestMethod.DELETE)
	public ResponseEntity<Void> deleteById(@PathVariable Integer id){
		autorServices.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/mostrar", method = RequestMethod.GET)
	public ResponseEntity<List<AutorDTO>> findAll() {		
		List<Autor> lista = autorServices.findAll();
		List<AutorDTO> listaDTO = lista.stream().map(objeto -> new AutorDTO(objeto)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDTO);
	}	

}
