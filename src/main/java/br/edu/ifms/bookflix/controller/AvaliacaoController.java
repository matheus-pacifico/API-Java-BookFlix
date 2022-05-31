package br.edu.ifms.bookflix.controller;

import br.edu.ifms.bookflix.model.Avaliacao;

import br.edu.ifms.bookflix.service.AvaliacaoService;

import br.edu.ifms.bookflix.dto.AvaliacaoDTO;

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
@RequestMapping(value = "/avaliacao")
public class AvaliacaoController {
	
	@Autowired
	private AvaliacaoService avaliacaoServices;
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Avaliacao> find(@PathVariable Integer id) {		
		Avaliacao objeto = avaliacaoServices.find(id);
		return ResponseEntity.ok().body(objeto);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody AvaliacaoDTO objetoNewDTO) {
		Avaliacao objeto = avaliacaoServices.fromNewDTO(objetoNewDTO);
		objeto = avaliacaoServices.insert(objeto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}").buildAndExpand(objeto.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody AvaliacaoDTO objetoDTO, @PathVariable Integer id) {
		Avaliacao objeto = avaliacaoServices.fromDTO(objetoDTO);
		objeto.setId(id);
		objeto = avaliacaoServices.update(objeto);
		return ResponseEntity.noContent().build();
	}
		
	@RequestMapping(value="/{id}", method= RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@RequestBody Avaliacao objeto,@PathVariable Integer id){
		avaliacaoServices.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/deletarporid/{id}", method= RequestMethod.DELETE)
	public ResponseEntity<Void> deleteById(@PathVariable Integer id){
		avaliacaoServices.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/mostrar", method = RequestMethod.GET)
	public ResponseEntity<List<AvaliacaoDTO>> findAll() {		
		List<Avaliacao> lista = avaliacaoServices.findAll();
		List<AvaliacaoDTO> listaDTO = lista.stream().map(objeto -> new AvaliacaoDTO(objeto)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDTO);
	}	

}
