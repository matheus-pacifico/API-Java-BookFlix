package br.edu.ifms.bookflix.controller;

import br.edu.ifms.bookflix.model.Autenticacao;

import br.edu.ifms.bookflix.service.AutenticacaoService;

import br.edu.ifms.bookflix.dto.AutenticacaoDTO;

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
@RequestMapping(value = "/api/v1/autenticacao")
public class AutenticacaoController {
	
	@Autowired
	private AutenticacaoService autenticacaoServices;
	
    @GetMapping(value = "/mostrar/{id}")
	public ResponseEntity<Autenticacao> find(@PathVariable String id) {
    	autenticacaoServices.intParamaterValidator(id);
		Autenticacao objeto = autenticacaoServices.autenticacaoWithoutObra(autenticacaoServices.find(Integer.parseInt(id)));
		return ResponseEntity.ok().body(objeto);
	}

    @PostMapping(value = "/adicionar")
	public ResponseEntity<Void> insert(@Valid @RequestBody AutenticacaoDTO objetoNewDTO) {
		Autenticacao objeto = autenticacaoServices.fromNewDTO(objetoNewDTO);
		objeto = autenticacaoServices.insert(objeto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(objeto.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/atualizar/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody AutenticacaoDTO objetoDTO, @PathVariable String id) {
		autenticacaoServices.intParamaterValidator(id);
		autenticacaoServices.intParamaterValidator(objetoDTO.getId().toString());
		Autenticacao objeto = autenticacaoServices.fromDTO(objetoDTO);
		autenticacaoServices.validateAvaliacaoId(Integer.parseInt(id), objetoDTO.getId());
		objeto = autenticacaoServices.update(objeto);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/remover/{id}")
	public ResponseEntity<Void> delete(@RequestBody Autenticacao objeto, @PathVariable String id) {
		autenticacaoServices.intParamaterValidator(id);
		autenticacaoServices.delete(Integer.parseInt(id), objeto);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/deletar/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable String id) {
		autenticacaoServices.intParamaterValidator(id);
		autenticacaoServices.deleteById(Integer.parseInt(id));
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value = "/exibir")
	public ResponseEntity<List<Autenticacao>> findAll() {
		return ResponseEntity.ok().body(autenticacaoServices.findAll());
	}
	
}
