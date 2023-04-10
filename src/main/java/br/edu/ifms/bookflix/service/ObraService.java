package br.edu.ifms.bookflix.service;

import br.edu.ifms.bookflix.model.Obra;

import br.edu.ifms.bookflix.repository.ObraRepository;

import br.edu.ifms.bookflix.dto.ObraDTO;

import br.edu.ifms.bookflix.service.exception.ObjectNotFoundException;
import br.edu.ifms.bookflix.service.exception.DataIntegrityException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ObraService {

	@Autowired
	private ObraRepository obrasRepository;
	private ObraDTO obrasDTO = new ObraDTO(); 
	
	public Obra find(Integer id) {
		Optional<Obra> objeto = obrasRepository.findById(id); 
		return objeto.orElseThrow(() -> new ObjectNotFoundException( 
				 "Obra não encontrada! Id: " + id));		
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
		return allObrasFound();
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
    	return obrasDTO.fromDTO(objetoDTO);
    }
	
    public Obra fromNewDTO(ObraDTO objetoNewDTO) {
    	return obrasDTO.fromNewDTO(objetoNewDTO);
    }
	
	private void updateData(Obra objeto, Obra novoObjeto) {
        novoObjeto.setIfsn(objeto.getIfsn());
        novoObjeto.setTitulo(objeto.getTitulo());
        novoObjeto.setArea(objeto.getArea());
        novoObjeto.setDescricao(objeto.getDescricao());
        novoObjeto.setNomeArquivo(objeto.getNomeArquivo());
        novoObjeto.setCaminhoArquivo(objeto.getCaminhoArquivo());
        novoObjeto.setAno(objeto.getAno());
        novoObjeto.setProfessor(objeto.getProfessor());
        novoObjeto.setAutores(objeto.getAutores());
        novoObjeto.setAvaliacoes(objeto.getAvaliacoes());
	}
	
	public List<Obra> findByTitulo(String titulo) {
		return allObrasFound().stream()
            .filter(obra -> (isFound(obra.getTitulo(), titulo)))
			.collect(Collectors.toList());
	}
	
	public List<Obra> findByIfsn(String ifsn) {
		return allObrasFound().stream()
			.filter(obra -> (isFound(obra.getIfsn(), ifsn)))
			.collect(Collectors.toList());
	}
	
	public List<Obra> findByAutor(String autor) {
		HashSet<Obra> obras = new HashSet<>();
		List<Obra> obrasEncontradas = new ArrayList<>();
		
		allObrasFound().forEach(obra -> obra.getAutores().stream()
				.filter(a -> (isFound(a.getNome(), autor)))
				.forEach(o -> obras.add(obra)));
		
		obrasEncontradas.addAll(obras);
		
		return obrasEncontradas;
	}
	
	public List<Obra> listByArea(String area) {
		return allObrasFound().stream()
			.filter(obra -> (isFound(obra.getArea(), area)))
			.collect(Collectors.toList());
	}
	
	public List<Obra> findByTudo(String pesquisa) {
		HashSet<Obra> obrasNaoRepetidas = new HashSet<>();
		List<Obra> obrasEncontradas = new ArrayList<>();
		
		obrasNaoRepetidas.addAll(findByTitulo(pesquisa));
		obrasNaoRepetidas.addAll(findByIfsn(pesquisa));
		obrasNaoRepetidas.addAll(findByAutor(pesquisa));
				
		obrasEncontradas.addAll(obrasNaoRepetidas);
		
		return obrasEncontradas;
	}  

	private List<Obra> allObrasFound() {
		return obrasDTO.listOfObrasWithoutSomeDetails(obrasRepository.findAll());
	}
	
    private boolean isFound(String comparacao, String busca) {
        return (comparacao.toUpperCase().contains(busca.toUpperCase()));      
    }
    
    public Obra obraWithoutSomeDetails(Obra objeto) {
    	return obrasDTO.obraWithoutSomeDetails(objeto);
    }
    
}
