package br.edu.ifms.bookflix.service;

import br.edu.ifms.bookflix.model.Autor;
import br.edu.ifms.bookflix.model.Avaliacao;
import br.edu.ifms.bookflix.model.Obra;
import br.edu.ifms.bookflix.model.Professor;
import br.edu.ifms.bookflix.model.Usuario;
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
		Obra obraAuxiliar = new Obra();
		obraAuxiliar.setId(objetoDTO.getId());
		obraAuxiliar.setIfsn(objetoDTO.getIfsn());
		obraAuxiliar.setTitulo(objetoDTO.getTitulo());
		obraAuxiliar.setArea(objetoDTO.getArea());
		obraAuxiliar.setDescricao(objetoDTO.getDescricao());
		obraAuxiliar.setNomeArquivo(objetoDTO.getNomeArquivo());
		obraAuxiliar.setCaminhoArquivo(objetoDTO.getCaminhoArquivo());
		obraAuxiliar.setProfessor(objetoDTO.getProfessor());
		obraAuxiliar.setAutores(objetoDTO.getAutores());
		obraAuxiliar.setAvaliacoes(objetoDTO.getAvaliacoes());
		return obraAuxiliar;
	}
	
	public Obra fromNewDTO(ObraDTO objetoNewDTO) {
		Obra obra = new Obra(null, objetoNewDTO.getIfsn(), objetoNewDTO.getTitulo(), 
			objetoNewDTO.getArea(), objetoNewDTO.getDescricao(), 
			objetoNewDTO.getNomeArquivo(), objetoNewDTO.getCaminhoArquivo(), objetoNewDTO.getProfessor());
		obra.setAutores(objetoNewDTO.getAutores());
		return obra;
	}
	
	private void updateData(Obra objeto, Obra novoObjeto) {
        novoObjeto.setIfsn(objeto.getIfsn());
        novoObjeto.setTitulo(objeto.getTitulo());
        novoObjeto.setArea(objeto.getArea());
        novoObjeto.setDescricao(objeto.getDescricao());
        novoObjeto.setNomeArquivo(objeto.getNomeArquivo());
        novoObjeto.setCaminhoArquivo(objeto.getCaminhoArquivo());
        novoObjeto.setProfessor(objeto.getProfessor());
        novoObjeto.setAutores(objeto.getAutores());
        novoObjeto.setAvaliacoes(objeto.getAvaliacoes());
	}
	
	public Obra obraWithoutSomeDetails(Obra objeto) {
		Obra obraAuxiliar = objeto;
		obraAuxiliar.setId(null);
		obraAuxiliar.setIfsn(objeto.getIfsn());
		obraAuxiliar.setTitulo(objeto.getTitulo());
		obraAuxiliar.setArea(objeto.getArea());
		obraAuxiliar.setDescricao(objeto.getDescricao());
        obraAuxiliar.setProfessor(professorOnlyWithNameAndSiape(objeto.getProfessor()));
		obraAuxiliar.setAutores(listOfAutoresOnlyWithName(objeto.getAutores()));
		obraAuxiliar.setAvaliacoes(listOfAvaliacoesOnlyWithUsersNameWithoutObra(objeto.getAvaliacoes()));
		return obraAuxiliar;
	}

	private List<Obra> listOfObrasWithoutSomeDetails(List<Obra> obras){
		return obras.stream().map(o -> obraWithoutSomeDetails(o))
				.collect(Collectors.toList());
	}
	
	public List<Obra> findByTitulo(String titulo) {
		return listOfObrasWithoutSomeDetails(allObrasFound().stream()
            .filter(obra -> (isFound(obra.getTitulo(), titulo)))
			.collect(Collectors.toList()));
	}
	
	public List<Obra> findByIfsn(String ifsn) {
		return listOfObrasWithoutSomeDetails(allObrasFound().stream()
			.filter(obra -> (isFound(obra.getIfsn(), ifsn)))
			.collect(Collectors.toList()));
	}
	
	public List<Obra> findByAutor(String autor) {
		HashSet<Obra> obras = new HashSet<>();
		List<Obra> obrasEncontradas = new ArrayList<>();
		
		allObrasFound().forEach(obra -> obra.getAutores().stream()
				.filter(a -> (isFound(a.getNome(), autor)))
				.forEach(o -> obras.add(obra)));
		
		obrasEncontradas.addAll(obras);
		
		return listOfObrasWithoutSomeDetails(obrasEncontradas);
	}
	
	public List<Obra> listByArea(String area) {
		return listOfObrasWithoutSomeDetails(allObrasFound().stream()
			.filter(obra -> (isFound(obra.getArea(), area)))
			.collect(Collectors.toList()));
	}
	
	public List<Obra> findByTudo(String pesquisa) {
		HashSet<Obra> obrasNaoRepetidas = new HashSet<>();
		List<Obra> obrasEncontradas = new ArrayList<>();
		
		obrasNaoRepetidas.addAll(findByTitulo(pesquisa));
		obrasNaoRepetidas.addAll(findByIfsn(pesquisa));
		obrasNaoRepetidas.addAll(findByAutor(pesquisa));
				
		obrasEncontradas.addAll(obrasNaoRepetidas);
		
		return listOfObrasWithoutSomeDetails(obrasEncontradas);
	}  

	private List<Obra> allObrasFound() {
		return listOfObrasWithoutSomeDetails(obrasRepository.findAll());
	}
	
    private boolean isFound(String comparacao, String busca) {
        return (comparacao.toUpperCase().contains(busca.toUpperCase()));      
    }
    
    private Professor professorOnlyWithNameAndSiape(Professor professor) {
    	professor.setId(null);
    	professor.setUsuario(usuarioOnlyWithName(professor.getUsuario()));
    	professor.setObras(null);
    	return professor;
    }
    
    private Usuario usuarioOnlyWithName(Usuario usuario) {
    	return new Usuario(null, usuario.getNome(), null,null,null);
    }
	
	private Avaliacao avaliacaoWithoutUsersDataExceptName(Avaliacao avaliacao) {
		avaliacao.setUsuario(usuarioOnlyWithName(avaliacao.getUsuario()));
		avaliacao.setObra(null);
		return avaliacao;
	}
	
	private List<Avaliacao> listOfAvaliacoesOnlyWithUsersNameWithoutObra(List<Avaliacao> avaliacoes) {
		return avaliacoes.stream().map(a -> avaliacaoWithoutUsersDataExceptName(a))
				.collect(Collectors.toList());
	}
	
	private Autor autorOnlyWithName(Autor autor) {
		autor.setId(null);
		autor.setObra(null);
		return autor;
	}

	private List<Autor> listOfAutoresOnlyWithName(List<Autor> autor) {
		return autor.stream().map(a -> autorOnlyWithName(a))
				.collect(Collectors.toList());
	}
	
}
