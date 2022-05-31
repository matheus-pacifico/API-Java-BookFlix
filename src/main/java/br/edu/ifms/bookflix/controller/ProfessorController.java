package br.edu.ifms.bookflix.controller;

import br.edu.ifms.bookflix.model.Professor;

import br.edu.ifms.bookflix.service.ProfessorService;

import br.edu.ifms.bookflix.dto.ProfessorDTO;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/professor")
public class ProfessorController {
	
	@Autowired
	private ProfessorService professorServices;
	
	@RequestMapping(value="/mostrar/{id}", method = RequestMethod.GET)
	public ResponseEntity<Professor> find(@PathVariable Integer id) {		
		Professor objeto = professorServices.find(id);
		return ResponseEntity.ok().body(objeto);
	}
		
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ProfessorDTO objetoDTO) {
		Professor objeto = professorServices.fromNewDTO(objetoDTO);
		objeto = professorServices.insert(objeto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}").buildAndExpand(objeto.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ProfessorDTO objetoDTO, @PathVariable Integer id) {
		Professor objeto = professorServices.fromDTO(objetoDTO);
		objeto.setId(id);
		objeto = professorServices.update(objeto);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@RequestBody Professor objeto, @PathVariable Integer id){
		professorServices.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/deletarporid/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteById(@PathVariable Integer id){
		professorServices.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/mostrar", method = RequestMethod.GET)
	public ResponseEntity<List<ProfessorDTO>> findAll() {		
		List<Professor> lista = professorServices.findAll();
		List<ProfessorDTO> listaDTO = lista.stream().map(objeto -> new ProfessorDTO(objeto)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDTO);
	}	

}
