package br.edu.ifms.bookflix.controller;

import br.edu.ifms.bookflix.model.Autenticacao;

import br.edu.ifms.bookflix.service.AutenticacaoService;

import br.edu.ifms.bookflix.dto.AutenticacaoDTO;

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
@RequestMapping(value = "/autenticacao")
public class AutenticacaoController {
	
	@Autowired
	private AutenticacaoService autenticacaoServices;
	
	@RequestMapping(value="/mostrar/{id}", method = RequestMethod.GET)
	public ResponseEntity<Autenticacao> find(@PathVariable Integer id) {		
		Autenticacao objeto = autenticacaoServices.find(id);
		return ResponseEntity.ok().body(objeto);
	}

	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody AutenticacaoDTO objetoNewDTO) {
		Autenticacao objeto = autenticacaoServices.fromNewDTO(objetoNewDTO);
		objeto = autenticacaoServices.insert(objeto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}").buildAndExpand(objeto.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody AutenticacaoDTO objetoDTO, @PathVariable Integer id) {
		Autenticacao objeto = autenticacaoServices.fromDTO(objetoDTO);
		objeto.setId(id);
		objeto = autenticacaoServices.update(objeto);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method= RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@RequestBody Autenticacao objeto, @PathVariable Integer id){
		autenticacaoServices.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/deletarporid/{id}", method= RequestMethod.DELETE)
	public ResponseEntity<Void> deleteById(@PathVariable Integer id){
		autenticacaoServices.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/mostrar", method = RequestMethod.GET)
	public ResponseEntity<List<AutenticacaoDTO>> findAll() {		
		List<Autenticacao> lista = autenticacaoServices.findAll();
		List<AutenticacaoDTO> listaDTO = lista.stream().map(objeto -> new AutenticacaoDTO(objeto)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDTO);
	}
	
}
