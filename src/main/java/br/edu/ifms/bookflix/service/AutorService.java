package br.edu.ifms.bookflix.service;

import br.edu.ifms.bookflix.model.Autor;
import br.edu.ifms.bookflix.repository.AutorRepository;
import br.edu.ifms.bookflix.model.Obra;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorService {

	@Autowired
	private AutorRepository autores;
	
	public List<Autor> findAll() {
		return autores.findAll();
	}
	
	public void deleteById(Integer id) {
		autores.deleteById(id);
	}
	
	public void save(Autor autor) {
		autores.saveAndFlush(autor);
	}
	
	public Optional<Autor> findById(Integer id) {
		return autores.findById(id);
	}
	
	public Autor findOne(Integer id) {		
		List<Autor> lista = autores.findAll();
		for (Autor autor : lista) {
			if (autor.getId() == id) {
				return autor;
			}
		}
		return null;
	}
	
	public List<String> findByName(String nome) {
		List<Autor> lista = autores.findAll();
		List<String> autoresEncontrados = new ArrayList<>();
		for (Autor autor : lista) {
			if (autor.getNome().contains(nome)) {
				autoresEncontrados.add(autor.getNome());
			}
		}
		return autoresEncontrados;
	}
	
	public List<Obra> autorObra(Integer id){
		List<Obra> obras = autores.findById(id).get().getObras();
		return obras;
	}
	
}
