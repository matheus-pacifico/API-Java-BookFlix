package br.edu.ifms.bookflix.controller;

import br.edu.ifms.bookflix.dto.ObraDTO;
import br.edu.ifms.bookflix.model.Obra;
import br.edu.ifms.bookflix.projection.ObraView;
import br.edu.ifms.bookflix.service.ObraService;

import java.net.URI;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
@Validated
public class ObraController {
	
	@Autowired
	private ObraService obraService;
	
	@GetMapping(value = "/mostrar/{id}")
	public ResponseEntity<Obra> search(@PathVariable Integer id) {
		Obra objeto = obraService.find(id);
		return ResponseEntity.ok().body(objeto);
	}

	@PostMapping(value = "/adicionar")
    public ResponseEntity<Void> insert(@Valid @RequestBody ObraDTO objetoNewDTO) {
		Obra objeto = obraService.fromNewDTO(objetoNewDTO);
		objeto = obraService.insert(objeto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(objeto.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/atualizar/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody ObraDTO objetoDTO, @PathVariable Integer id) {
		obraService.validateObraId(id, objetoDTO.getId());
		Obra objeto = obraService.fromDTO(objetoDTO);
		objeto = obraService.update(objeto);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/remover/{id}")
	public ResponseEntity<Void> delete(@RequestBody Obra objeto, @PathVariable Integer id) {
		obraService.delete(id, objeto);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/deletar/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
		obraService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value = "/exibir")
	public ResponseEntity<List<Obra>> findAll() {
		return ResponseEntity.ok().body(obraService.findAll());
	}
	
	@GetMapping(value = "/mostrar/ifsn/{ifsn}")
	public ResponseEntity<Obra> findByIfsn(@NotBlank @PathVariable String ifsn) {
		return ResponseEntity.ok().body(obraService.obraWithoutSomeAtributes(obraService.findByIfsn(ifsn)));
	}
	
	@GetMapping(value = "/search")
	public ResponseEntity<Page<ObraView>> searchObra(@NotBlank @RequestParam String q,
													 @RequestParam(defaultValue = "0") int page) {
		Page<ObraView> obrasFound = obraService.searchObra(q, page);
		if(obrasFound.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(obrasFound);
	}
	
	@GetMapping(value = "/search/titulo/{titulo}")
	public ResponseEntity<Page<ObraView>> searchByTitulo(@NotBlank @PathVariable String titulo,
			 											 @RequestParam(defaultValue = "0") int page) {
		Page<ObraView> obrasFound = obraService.searchObraByTitulo(titulo, page);
		if(obrasFound.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(obrasFound);
	}
	
	@GetMapping(value = "/search/ifsn/{ifsn}")
	public ResponseEntity<Page<ObraView>> searchByIfsn(@NotBlank @PathVariable String ifsn,
			 										   @RequestParam(defaultValue = "0") int page) {
		Page<ObraView> obrasFound = obraService.searchObraByIfsn(ifsn, page);
		if(obrasFound.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(obrasFound);
	}
	
	@GetMapping(value = "/search/area/{area}")
	public ResponseEntity<Page<ObraView>> searchByArea(@NotBlank @PathVariable String area,
			 										   @RequestParam(defaultValue = "0") int page) {
		Page<ObraView> obrasFound = obraService.searchObraByArea(area, page);
		if(obrasFound.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(obrasFound);
	}
	
	@GetMapping(value = "/search/ano/{ano}")
	public ResponseEntity<Page<ObraView>> searchByAno(@PathVariable int ano,
													  @RequestParam(defaultValue = "0") int page) {
		Page<ObraView> obrasFound = obraService.searchObraByAno(ano, page);
		if(obrasFound.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(obrasFound);
	}
	
}
