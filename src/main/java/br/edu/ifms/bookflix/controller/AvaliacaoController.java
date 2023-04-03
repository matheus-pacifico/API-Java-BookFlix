package br.edu.ifms.bookflix.controller;

import br.edu.ifms.bookflix.model.Avaliacao;

import br.edu.ifms.bookflix.service.AvaliacaoService;

import br.edu.ifms.bookflix.dto.AvaliacaoDTO;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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
@RequestMapping(value = "api/v1/avaliacao")
public class AvaliacaoController {
	
	@Autowired
	private AvaliacaoService avaliacaoServices;
	
    @GetMapping(value = "/mostrar/{id}")
	public ResponseEntity<Avaliacao> find(@PathVariable Integer id) {		
		Avaliacao objeto = avaliacaoServices.avaliacaoSemDadosDoUsuarioExcetoNome(avaliacaoServices.find(id));
		return ResponseEntity.ok().body(objeto);
	}
	
    @PostMapping(value = "/adicionar")
	public ResponseEntity<Void> insert(@Valid @RequestBody AvaliacaoDTO objetoNewDTO) {
		Avaliacao objeto = avaliacaoServices.fromNewDTO(objetoNewDTO);
		objeto = avaliacaoServices.insert(objeto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}").buildAndExpand(objeto.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/atualizar/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody AvaliacaoDTO objetoDTO, @PathVariable Integer id) {
		Avaliacao objeto = avaliacaoServices.fromDTO(objetoDTO);
		objeto.setId(id);
		objeto = avaliacaoServices.update(objeto);
		return ResponseEntity.noContent().build();
	}
		
	@DeleteMapping(value = "/remover/{id}")
	public ResponseEntity<Void> delete(@RequestBody Avaliacao objeto,@PathVariable Integer id) {
		avaliacaoServices.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/deletar/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
		avaliacaoServices.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value = "/mostrar")
	public ResponseEntity<List<AvaliacaoDTO>> findAll() {		
		List<Avaliacao> lista = avaliacaoServices.findAll();
		List<AvaliacaoDTO> listaDTO = lista.stream().map(objeto -> new AvaliacaoDTO(objeto)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDTO);
	}	

}
