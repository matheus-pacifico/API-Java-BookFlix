package br.edu.ifms.bookflix.service;

import br.edu.ifms.bookflix.model.Obra;

import br.edu.ifms.bookflix.repository.ObraRepository;

import br.edu.ifms.bookflix.dto.ObraDTO;

import br.edu.ifms.bookflix.service.exception.ObjectNotFoundException;
import br.edu.ifms.bookflix.service.exception.DataIntegrityException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ObraService {

	@Autowired
	private ObraRepository obrasRepository;
	
	public Obra find(Integer id) {
		Optional<Obra> objeto = obrasRepository.findById(id); 
		return objeto.orElseThrow(() -> new ObjectNotFoundException( 
				 "Obra não encontrada! Id: " + id + ", Tipo: " + Obra.class.getName()));		
	}
	
	@Transactional
	public Obra insert (Obra objeto) {
		objeto.setId(null);
		obrasRepository.save(objeto);
		return objeto;	
	}
	
	public Obra update(Obra objeto) {
		Obra novoObjeto = find(objeto.getId());
		updateData(objeto, novoObjeto);
		return obrasRepository.save(novoObjeto);
	}
	
	@Transactional
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
	
	@Transactional
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
			objetoDTO.getArea(), objetoDTO.getGenero(), objetoDTO.getAutor(), 
			objetoDTO.getNomeArquivo(), objetoDTO.getCaminhoArquivo(), objetoDTO.getProfessor());
	}
	
	public Obra fromNewDTO(ObraDTO objetoNewDTO) {
		return new Obra(null, objetoNewDTO.getIsbn(), objetoNewDTO.getTitulo(), 
				objetoNewDTO.getArea(), objetoNewDTO.getGenero(), objetoNewDTO.getAutor(), 
				objetoNewDTO.getNomeArquivo(), objetoNewDTO.getCaminhoArquivo(), null);
	}
	
	private void updateData(Obra objeto, Obra novoObjeto) {
		novoObjeto.setIsbn(objeto.getIsbn());
		novoObjeto.setTitulo(objeto.getTitulo());
		novoObjeto.setArea(objeto.getArea());
		novoObjeto.setGenero(objeto.getGenero());
		novoObjeto.setAutor(objeto.getAutor());
		novoObjeto.setNomeArquivo(objeto.getNomeArquivo());
		novoObjeto.setCaminhoArquivo(objeto.getCaminhoArquivo());
		novoObjeto.setProfessor(objeto.getProfessor());
		novoObjeto.setAvaliacoes(objeto.getAvaliacoes());
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
	
	public List<Obra> findByAutor(String autor) {
		List<Obra> lista = obrasRepository.findAll();
		List<Obra> obrasEncontradas = new ArrayList<>();
		for (Obra obra : lista) {
			if (obra.getAutor().contains(autor)) {
				obrasEncontradas.add(obra);
			}
		}
		if (!obrasEncontradas.isEmpty()) return obrasEncontradas;
		return null;
	}
	
}
