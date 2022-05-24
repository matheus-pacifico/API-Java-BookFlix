package br.edu.ifms.bookflix.service;

import br.edu.ifms.bookflix.model.Obra;
import br.edu.ifms.bookflix.repository.ObraRepository;
import br.edu.ifms.bookflix.model.Autor;
import br.edu.ifms.bookflix.model.Avaliacao;
import br.edu.ifms.bookflix.model.Professor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ObraService {

	@Autowired
	private ObraRepository obras;
	
	public List<Obra> findAll() {
		return obras.findAll();
	}
	
	public void deleteById(Integer id) {
		obras.deleteById(id);
	}
	
	public void save(Obra obra) {
		obras.saveAndFlush(obra);
	}
	
	public Optional<Obra> findById(Integer id) {
		return obras.findById(id);
	}
	
	public Obra findOne(Integer id) {
		List<Obra> lista = obras.findAll();
		for (Obra obra : lista) {
			if (obra.getId() == id) {
				return obra;
			}
		}
		return null;
	}
	
	public Obra findByIsbn(String isbn) {
		List<Obra> lista = obras.findAll();
		for (Obra obra : lista) {
			if (obra.getIsbn() == isbn) {
				return obra;
			}
		}
		return null;
	}
	
	public List<String> findByTitulo(String titulo) {
		List<Obra> lista = obras.findAll();
		List<String> obrasEncontradas = new ArrayList<>();
		for (Obra obra : lista) {
			if (obra.getTitulo().contains(titulo)) {
				obrasEncontradas.add(obra.getTitulo());
			}
		}
		return obrasEncontradas;
	}
	
	public List<String> ListarObrasByArea(String area) {
		List<Obra> lista = obras.findAll();
		List<String> obrasEncontradas = new ArrayList<>();
		for (Obra obra : lista) {
			if (obra.getArea().contains(area)) {
				obrasEncontradas.add(obra.getTitulo());
			}
		}
		return obrasEncontradas;
	}
	
	public List<String> ListarObrasByGenero(String genero) {
		List<Obra> lista = obras.findAll();
		List<String> obrasEncontradas = new ArrayList<>();
		for (Obra obra : lista) {
			if (obra.getArea().contains(genero)) {
				obrasEncontradas.add(obra.getTitulo());
			}
		}
		return obrasEncontradas;
	}
	
	public List<String> ListarObrasByAno(int ano) {
		List<Obra> lista = obras.findAll();
		List<String> obrasEncontradas = new ArrayList<>();
		for (Obra obra : lista) {
			if (obra.getAno() == ano) {
				obrasEncontradas.add(obra.getTitulo());
			}
		}
		return obrasEncontradas;
	}
	
	public List<Autor> obraAutor(Integer id){
		List<Autor> autores = obras.findById(id).get().getAutor();
		return autores;
	}
	
	public List<Avaliacao> obraAvaliacao(Integer id){
		List<Avaliacao> avaliacoes = obras.findById(id).get().getAvaliacoes();
		return avaliacoes;
	}
	
	public Professor obraProfessor(Integer id){
		Professor professor = obras.findById(id).get().getProfessor();
		return professor;
	}
	
	
}
