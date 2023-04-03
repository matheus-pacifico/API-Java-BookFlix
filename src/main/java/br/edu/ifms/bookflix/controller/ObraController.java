package br.edu.ifms.bookflix.controller;

import br.edu.ifms.bookflix.model.Obra;

import br.edu.ifms.bookflix.service.ObraService;

import br.edu.ifms.bookflix.dto.ObraDTO;

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
@RequestMapping(value = "/api/v1/obra")
public class ObraController {
	
	@Autowired
	private ObraService obraServices;
	
	@GetMapping(value = "/mostrar/{id}")
	public ResponseEntity<Obra> find(@PathVariable Integer id) {		
		Obra objeto = obraServices.obrasApenasComNomeDoUsuarioESemObraDoProfessor(obraServices.find(id));
		return ResponseEntity.ok().body(objeto);
	}

	@PostMapping(value = "/adicionar")
    public ResponseEntity<Void> insert(@Valid @RequestBody ObraDTO objetoNewDTO) {
		Obra objeto = obraServices.fromNewDTO(objetoNewDTO);
		objeto = obraServices.insert(objeto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(objeto.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/atualizar/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody ObraDTO objetoDTO, @PathVariable Integer id) {
		Obra objeto = obraServices.fromDTO(objetoDTO);
		objeto.setId(id);
		objeto = obraServices.update(objeto);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/remover/{id}")
	public ResponseEntity<Void> delete(@RequestBody Obra objeto, @PathVariable Integer id) {
		obraServices.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/deletar/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
		obraServices.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value = "/mostrar")
	public ResponseEntity<List<ObraDTO>> findAll() {		
		List<Obra> lista = obraServices.findAll();
		List<ObraDTO> listaDTO = lista.stream().map(objeto -> new ObraDTO(objeto)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDTO);
	}
	
	@GetMapping(value = "/mostrar/titulo/{titulo}")
	public ResponseEntity<List<ObraDTO>> findByTitle(@PathVariable String titulo) {		
		List<Obra> lista = obraServices.findByTitulo(titulo);
		List<ObraDTO> listaDTO = lista.stream().map(objeto -> new ObraDTO(objeto)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDTO);
	}
	
	@GetMapping(value = "/mostrar/autor/{autor}")
	public ResponseEntity<List<ObraDTO>> findByAuthor(@PathVariable String autor) {		
		List<Obra> lista = obraServices.findByAutor(autor);
		List<ObraDTO> listaDTO = lista.stream().map(objeto -> new ObraDTO(objeto)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDTO);
	}
	
	@GetMapping(value = "/mostrar/ifsn/{ifsn}")
	public ResponseEntity<List<ObraDTO>> findByIfsn(@PathVariable String ifsn) {		
		List<Obra> lista = obraServices.findByIfsn(ifsn);
		List<ObraDTO> listaDTO = lista.stream().map(objeto -> new ObraDTO(objeto)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDTO);
	}
	
	@GetMapping(value = "/mostrar/free/{free}")
	public ResponseEntity<List<ObraDTO>> findByAll(@PathVariable String free) {
		List<Obra> lista = obraServices.findByTudo(free);
		List<ObraDTO> listaDTO = lista.stream().map(objeto -> new ObraDTO(objeto)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDTO);
	}
	
	@GetMapping(value = "/mostrar/area/{area}")
	public ResponseEntity<List<ObraDTO>> listByArea(@PathVariable String area) {
		List<Obra> lista = obraServices.listByArea(area);
		List<ObraDTO> listaDTO = lista.stream().map(objeto -> new ObraDTO(objeto)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDTO);
	}
	
}
