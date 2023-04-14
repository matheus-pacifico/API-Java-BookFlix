package br.edu.ifms.bookflix.controller;

import br.edu.ifms.bookflix.model.Obra;

import br.edu.ifms.bookflix.service.ObraService;

import br.edu.ifms.bookflix.dto.ObraDTO;

import br.edu.ifms.bookflix.projection.ObraView;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/api/v1/obra")
public class ObraController {
	
	@Autowired
	private ObraService obraServices;
	
	@GetMapping(value = "/mostrar/{id}")
	public ResponseEntity<Obra> search(@PathVariable String id) {
		obraServices.intParamaterValidator(id);
		Obra objeto = obraServices.find(Integer.parseInt(id));
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
	public ResponseEntity<Void> update(@Valid @RequestBody ObraDTO objetoDTO, @PathVariable String id) {
		obraServices.intParamaterValidator(id);
		obraServices.intParamaterValidator(objetoDTO.getId().toString());
		Obra objeto = obraServices.fromDTO(objetoDTO);
		obraServices.validateObraId(Integer.parseInt(id), objetoDTO.getId());
		objeto = obraServices.update(objeto);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/remover/{id}")
	public ResponseEntity<Void> delete(@RequestBody Obra objeto, @PathVariable String id) {
		obraServices.intParamaterValidator(id);
		obraServices.delete(Integer.parseInt(id), objeto);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/deletar/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable String id) {
		obraServices.intParamaterValidator(id);
		obraServices.deleteById(Integer.parseInt(id));
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value = "/exibir")
	public ResponseEntity<List<Obra>> findAll() {
		return ResponseEntity.ok().body(obraServices.findAll());
	}
	
	@GetMapping(value = "/mostrar/ifsn/{ifsn}")
	public ResponseEntity<Obra> findByIfsn(@PathVariable String ifsn) {
		obraServices.stringParameterValidator(ifsn);
		return ResponseEntity.ok().body(obraServices.obraWithoutSomeAtributes(obraServices.findByIfsn(ifsn)));
	}
	
	@GetMapping(value = "/search")
	public ResponseEntity<List<ObraView>> searchObra(@RequestParam String q) {
		obraServices.stringParameterValidator(q);
		List<ObraView> obrasFound = obraServices.searchObra(q);
		if(obrasFound.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(obrasFound);
	}
	
	@GetMapping(value = "/search/titulo/{titulo}")
	public ResponseEntity<List<ObraView>> searchByTitulo(@PathVariable String titulo) {
		obraServices.stringParameterValidator(titulo);
		List<ObraView> obrasFound = obraServices.searchObraByTitulo(titulo);
		if(obrasFound.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(obrasFound);
	}
	
	@GetMapping(value = "/search/ifsn/{ifsn}")
	public ResponseEntity<List<ObraView>> searchByIfsn(@PathVariable String ifsn) {	
		obraServices.stringParameterValidator(ifsn);
		List<ObraView> obrasFound = obraServices.searchObraByIfsn(ifsn);
		if(obrasFound.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(obrasFound);
	}
	
	@GetMapping(value = "/search/area/{area}")
	public ResponseEntity<List<ObraView>> searchByArea(@PathVariable String area) {
		obraServices.stringParameterValidator(area);
		List<ObraView> obrasFound = obraServices.searchObraByArea(area);
		if(obrasFound.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(obrasFound);
	}
	
	@GetMapping(value = "/search/ano/{ano}")
	public ResponseEntity<List<ObraView>> searchByAno(@PathVariable String ano) {
		obraServices.intAnoParamaterValidator(ano);
		List<ObraView> obrasFound = obraServices.searchObraByAno(ano);
		if(obrasFound.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(obrasFound);
	}
	
}
