package br.edu.ifms.bookflix.controller;

import br.edu.ifms.bookflix.model.Obra;

import br.edu.ifms.bookflix.service.ObraService;

import br.edu.ifms.bookflix.dto.ObraDTO;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/obra")
public class ObraController {
	
	@Autowired
	private ObraService obraServices;
	
	@RequestMapping(value="/mostrar/{id}", method = RequestMethod.GET)
	public ResponseEntity<Obra> find(@PathVariable Integer id) {		
		Obra objeto = obraServices.find(id);
		return ResponseEntity.ok().body(objeto);
	}

	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ObraDTO objetoNewDTO) {
		Obra objeto = obraServices.fromNewDTO(objetoNewDTO);
		objeto = obraServices.insert(objeto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}").buildAndExpand(objeto.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ObraDTO objetoDTO, @PathVariable Integer id) {
		Obra objeto = obraServices.fromDTO(objetoDTO);
		objeto.setId(id);
		objeto = obraServices.update(objeto);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method= RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@RequestBody Obra objeto,@PathVariable Integer id){
		obraServices.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/deletarporid/{id}", method= RequestMethod.DELETE)
	public ResponseEntity<Void> deleteById(@PathVariable Integer id){
		obraServices.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/mostrar", method = RequestMethod.GET)
	public ResponseEntity<List<ObraDTO>> findAll() {		
		List<Obra> lista = obraServices.findAll();
		List<ObraDTO> listaDTO = lista.stream().map(objeto -> new ObraDTO(objeto)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDTO);
	}
	
	@RequestMapping(value = "/mostrar/titulo", method = RequestMethod.GET)
	public ResponseEntity<List<ObraDTO>> findByTitle(@RequestParam(value = "titulo") String titulo) {		
		List<Obra> lista = obraServices.findByTitulo(titulo);
		List<ObraDTO> listaDTO = lista.stream().map(objeto -> new ObraDTO(objeto)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDTO);
	}
	
	@RequestMapping(value = "/mostrar/autor", method = RequestMethod.GET)
	public ResponseEntity<List<ObraDTO>> findByAutor(@RequestParam(value = "autor") String autor) {		
		List<Obra> lista = obraServices.findByAutor(autor);
		List<ObraDTO> listaDTO = lista.stream().map(objeto -> new ObraDTO(objeto)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDTO);
	}
	
	@RequestMapping(value = "/mostrar/isbn", method = RequestMethod.GET)
	public ResponseEntity<List<ObraDTO>> findByIsbn(@RequestParam(value = "isbn") String isbn) {		
		List<Obra> lista = obraServices.findByIsbn(isbn);
		List<ObraDTO> listaDTO = lista.stream().map(objeto -> new ObraDTO(objeto)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDTO);
	}
	
}
