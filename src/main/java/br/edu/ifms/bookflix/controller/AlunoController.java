package br.edu.ifms.bookflix.controller;

import br.edu.ifms.bookflix.model.Aluno;

import br.edu.ifms.bookflix.dto.AlunoDTO;

import br.edu.ifms.bookflix.service.AlunoService;
  
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
@RequestMapping(value = "/api/v1/aluno")
public class AlunoController {
  
    @Autowired
    private AlunoService alunoServices;
  
    @GetMapping(value = "/mostrar/{id}")
    public ResponseEntity<Aluno> find(@PathVariable String id) {
    	alunoServices.intParamaterValidator(id);
	    Aluno objeto = alunoServices.find(Integer.parseInt(id)); 
	    return ResponseEntity.ok().body(objeto); 
    }
   
    @PostMapping(value = "/adicionar")
    public ResponseEntity<Void> insert(@Valid @RequestBody AlunoDTO objetoNewDTO) {
	    Aluno objeto = alunoServices.fromNewDTO(objetoNewDTO);
	    objeto = alunoServices.insert(objeto);
	    URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
			   .path("/{id}").buildAndExpand(objeto.getId()).toUri();
	    return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/atualizar/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody AlunoDTO objetoDTO, @PathVariable String id) {
		alunoServices.intParamaterValidator(id);
		alunoServices.intParamaterValidator(objetoDTO.getId().toString());
		Aluno objeto = alunoServices.fromDTO(objetoDTO);
		alunoServices.validateAlunoId(Integer.parseInt(id), objetoDTO.getId());
		objeto = alunoServices.update(objeto);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/remover/{id}")
	public ResponseEntity<Void> delete(@RequestBody Aluno objeto, @PathVariable String id) {
		alunoServices.intParamaterValidator(id);
		alunoServices.delete(Integer.parseInt(id), objeto);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/deletar/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable String id) {
		alunoServices.intParamaterValidator(id);
		alunoServices.deleteById(Integer.parseInt(id));
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value = "/exibir")
	public ResponseEntity<List<Aluno>> findAll() {
		return ResponseEntity.ok().body(alunoServices.findAll());
	}
  
}
 