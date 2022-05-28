package br.edu.ifms.bookflix.service;

import br.edu.ifms.bookflix.model.Obra;
//import br.edu.ifms.bookflix.model.Autor;
/*import br.edu.ifms.bookflix.model.Avaliacao;
import br.edu.ifms.bookflix.model.Professor;*/

import br.edu.ifms.bookflix.repository.ObraRepository;
import br.edu.ifms.bookflix.repository.AutorRepository;

//import br.edu.ifms.bookflix.service.AutorService;

import br.edu.ifms.bookflix.dto.ObraDTO;
import br.edu.ifms.bookflix.dto.ObraNewDTO;

import br.edu.ifms.bookflix.service.exception.ObjectNotFoundException;
import br.edu.ifms.bookflix.service.exception.DataIntegrityException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ObraService {

	@Autowired
	private ObraRepository obrasRepository;
	@Autowired
	private AutorRepository autoresRepository;
	
	public Obra find(Integer id) {
		Optional<Obra> objeto = obrasRepository.findById(id); 
		return objeto.orElseThrow(() -> new ObjectNotFoundException( 
				 "Obra não encontrada! Id: " + id + ", Tipo: " + Obra.class.getName()));		
	}
	
	@Transactional
	public Obra insert (Obra objeto) {
		objeto.setId(null);
		obrasRepository.save(objeto);
		autoresRepository.saveAll(objeto.getAutor());
		return objeto;	
	}
	
	public Obra update(Obra objeto) {
		Obra novoObjeto = find(objeto.getId());
		updateData(objeto, novoObjeto);
		return obrasRepository.save(novoObjeto);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			obrasRepository.deleteById(id);	
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível remover. Verifique a integridade referencial.");
		}
	}
	
	public List<Obra> findAll() {
		return obrasRepository.findAll();
	}
	
	public void deleteById(Integer id) {
		obrasRepository.deleteById(id);
	}
	
	public void save(Obra obra) {
		obrasRepository.saveAndFlush(obra);
	}
	
	public Optional<Obra> findById(Integer id) {
		return obrasRepository.findById(id);
	}
	
	public Obra fromDTO(ObraDTO objetoDTO) {
		return new Obra(objetoDTO.getId(), objetoDTO.getIsbn(), objetoDTO.getTitulo(), 
			objetoDTO.getArea(), objetoDTO.getGenero(), objetoDTO.getDescricao(), 
			objetoDTO.getAno(), objetoDTO.getPagina(), objetoDTO.getProfessor());
	}
	
	public Obra fromNewDTO(ObraNewDTO objetoNewDTO) {
		return new Obra(null, objetoNewDTO.getIsbn(), objetoNewDTO.getTitulo(), 
				objetoNewDTO.getArea(), objetoNewDTO.getGenero(), objetoNewDTO.getDescricao(), 
				objetoNewDTO.getAno(), objetoNewDTO.getPagina(), null);
	}
	
	private void updateData(Obra objeto, Obra novoObjeto) {
		novoObjeto.setIsbn(objeto.getIsbn());
		novoObjeto.setTitulo(objeto.getTitulo());
		novoObjeto.setArea(objeto.getArea());
		novoObjeto.setGenero(objeto.getGenero());
		novoObjeto.setDescricao(objeto.getDescricao());
		novoObjeto.setAno(objeto.getAno());
		novoObjeto.setPagina(objeto.getPagina());
		novoObjeto.setProfessor(objeto.getProfessor());
	}
	
	public List<Obra> findByTitulo(String titulo) {
		List<Obra> lista = obrasRepository.findAll();
		List<Obra> obrasEncontradas = new ArrayList<>();
		for (Obra obra : lista) {
			if (obra.getTitulo().contains(titulo)) {
				obrasEncontradas.add(obra);
			}
		}
		if (!obrasEncontradas.isEmpty()) return obrasEncontradas;
		return null;
	}
	
	public Obra findByIsbn(String isbn) {
		List<Obra> lista = obrasRepository.findAll();
		for (Obra obra : lista) {
			if (obra.getIsbn().compareTo(isbn) == 0) {
				return obra;
			}
		}
		return null;
	}
	
}
