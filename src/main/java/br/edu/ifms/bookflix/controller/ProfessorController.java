package br.edu.ifms.bookflix.controller;

import br.edu.ifms.bookflix.model.Professor;

import br.edu.ifms.bookflix.service.ProfessorService;

import br.edu.ifms.bookflix.dto.ProfessorDTO;

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
@RequestMapping(value = "/api/v1/professor")
public class ProfessorController {
	
	@Autowired
	private ProfessorService professorServices;
	
	@GetMapping(value = "/mostrar/{id}")
	public ResponseEntity<Professor> find(@PathVariable String id) {
		professorServices.intParamaterValidator(id);		
		Professor objeto = professorServices.find(Integer.parseInt(id));
		return ResponseEntity.ok().body(objeto);
	}
		
	@PostMapping(value = "/adicionar")
	public ResponseEntity<Void> insert(@Valid @RequestBody ProfessorDTO objetoNewDTO) {
		Professor objeto = professorServices.fromNewDTO(objetoNewDTO);
		objeto = professorServices.insert(objeto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(objeto.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/atualizar/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody ProfessorDTO objetoDTO, @PathVariable String id) {
		professorServices.intParamaterValidator(id);
		professorServices.intParamaterValidator(objetoDTO.getId().toString());
		Professor objeto = professorServices.fromDTO(objetoDTO);
		professorServices.validateProfessorId(Integer.parseInt(id), objetoDTO.getId());
		objeto = professorServices.update(objeto);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/remover/{id}")
	public ResponseEntity<Void> delete(@RequestBody Professor objeto, @PathVariable String id) {
		professorServices.intParamaterValidator(id);
		professorServices.delete(Integer.parseInt(id), objeto);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/deletar/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable String id) {
		professorServices.intParamaterValidator(id);
		professorServices.deleteById(Integer.parseInt(id));
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value = "/exibir")
	public ResponseEntity<List<Professor>> findAll() {
		return ResponseEntity.ok().body(professorServices.findAll());
	}	

}
