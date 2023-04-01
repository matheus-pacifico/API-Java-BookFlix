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
				 "Obra não encontrada! Id: " + id /*+ ", Tipo: " + Obra.class.getName()*/));		
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
		return new Obra(null, objetoNewDTO.getIfsn(), objetoNewDTO.getTitulo(), 
			objetoNewDTO.getArea(), objetoNewDTO.getDescricao(), 
			objetoNewDTO.getNomeArquivo(), objetoNewDTO.getCaminhoArquivo(), objetoNewDTO.getProfessor());
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
	
	public List<Obra> findByTitulo(String titulo) {
		List<Obra> obrasEncontradas = new ArrayList<>();
        allObrasFound().stream()
            .filter(obra -> (isFound(obra.getTitulo(), titulo)))
            .forEach(obra -> obrasEncontradas.add(obra));
		return obrasEncontradas;
	}
	
	public List<Obra> findByIfsn(String ifsn) {
		List<Obra> obrasEncontradas = new ArrayList<>();
		allObrasFound().stream()
			.filter(obra -> (isFound(obra.getIfsn(), ifsn)))
        	.forEach(obra -> obrasEncontradas.add(obra));
		return obrasEncontradas;
	}
	
	public List<Obra> findByAutor(String autor) {
		List<Obra> obrasEncontradas = new ArrayList<>();
		allObrasFound().stream()
			.filter(obra -> (isFound(obra.getAutores().stream().toString(), autor)))
			.forEach(obra -> obrasEncontradas.add(obra));
		return obrasEncontradas;
	}
	
	public List<Obra> listByArea(String area) {
		List<Obra> obrasEncontradas = new ArrayList<>();
		allObrasFound().stream()
			.filter(obra -> (isFound(obra.getArea(), area)))
        	.forEach(obra -> obrasEncontradas.add(obra));
		return obrasEncontradas;
	}
	
	public List<Obra> findByTudo(String pesquisa) {
		List<Obra> obrasEncontradas = new ArrayList<>();
		
		obrasEncontradas.addAll(findByTitulo(pesquisa));
		obrasEncontradas.addAll(findByAutor(pesquisa));
		obrasEncontradas.addAll(findByIfsn(pesquisa));
				
		obrasEncontradas = removeRepeatedObras(obrasEncontradas);
		
		return obrasEncontradas;
	}
	
	private List<Obra> removeRepeatedObras(List<Obra> obrasEncontradas) {
		HashSet<Obra> obrasNaoRepetidas = new HashSet<>();
		List<Obra> obrasUnicas = new ArrayList<>();
		
		obrasNaoRepetidas.addAll(obrasEncontradas);
		
		obrasUnicas.addAll(obrasNaoRepetidas);
		
		return obrasUnicas;
	}

	private List<Obra> allObrasFound(){
		return listaObrasApenasComNomeDoUsuarioESemObraDoProfessor(obrasRepository.findAll());
	}
	
    private boolean isFound(String comparacao, String busca) {
        return (comparacao.toUpperCase().contains(busca.toUpperCase()));      
    }
	
	public Obra obrasApenasComNomeDoUsuarioESemObraDoProfessor(Obra obra) {
		obra.getAvaliacoes().forEach(a -> {
			a.getUsuario().setAutenticacao(null);
			a.getUsuario().setProfessor(null);
			a.getUsuario().setAluno(null);
			a.getUsuario().setAvaliacoes(null);
			a.getUsuario().setId(null);
		});
		obra.getProfessor().setObras(null);
		
		return obra;
	}
	
	public List<Obra> listaObrasApenasComNomeDoUsuarioESemObraDoProfessor(List<Obra> obras) {
		List<Obra> obrasSemDadosExcetoNome = new ArrayList<>();
		obras.forEach(o -> obrasApenasComNomeDoUsuarioESemObraDoProfessor(o));
		
		obrasSemDadosExcetoNome.addAll(obras);
		
		return obrasSemDadosExcetoNome;
	}

}
