package br.edu.ifms.bookflix.service;

import br.edu.ifms.bookflix.model.Autenticacao;

import br.edu.ifms.bookflix.repository.AutenticacaoRepository;

import br.edu.ifms.bookflix.dto.AutenticacaoDTO;

import br.edu.ifms.bookflix.service.exception.DataIntegrityException;
import br.edu.ifms.bookflix.service.exception.ObjectNotFoundException;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutenticacaoService {

	@Autowired
	private AutenticacaoRepository autenticacoesRepository;
	
	public Autenticacao find(Integer id) {
		Optional<Autenticacao> objeto = autenticacoesRepository.findById(id); 
		return objeto.orElseThrow(() -> new ObjectNotFoundException( 
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Autenticacao.class.getName()));		
	}
	
	@Transactional
	public Autenticacao insert (Autenticacao objeto) {
		objeto.setId(null);
		autenticacoesRepository.save(objeto);
		return objeto;
		
	}
	
	public Autenticacao update(Autenticacao objeto) {
		Autenticacao novoObjeto = find(objeto.getId());
		updateData(objeto, novoObjeto);
		return autenticacoesRepository.save(novoObjeto);
	}
	
	@Transactional
	public void delete(Integer id) {
		find(id);
		try {
			autenticacoesRepository.deleteById(id);	
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível remover. Verifique a integridade referencial.");
		}
	}
	
	public Autenticacao fromDTO(AutenticacaoDTO objetoDTO) {
		return new Autenticacao(objetoDTO.getId(), objetoDTO.getEmail(), objetoDTO.getSenha(), 
			objetoDTO.getUsuario());
	}
	
	public Autenticacao fromNewDTO(AutenticacaoDTO objetoNewDTO) {
		return new Autenticacao(null, objetoNewDTO.getEmail(), objetoNewDTO.getSenha(), objetoNewDTO.getUsuario());
	}
	
	private void updateData(Autenticacao objeto, Autenticacao novoObjeto) {
		novoObjeto.setEmail(objeto.getEmail());
		novoObjeto.setSenha(objeto.getSenha());
		novoObjeto.setUsuario(objeto.getUsuario());
	}
	
	public List<Autenticacao> findAll() {
		return autenticacoesRepository.findAll();
	}
	
	@Transactional
	public void deleteById(Integer id) {
		autenticacoesRepository.deleteById(id);
	}
	
	public void save(Autenticacao autenticacao) {
		autenticacoesRepository.saveAndFlush(autenticacao);
	}
	
	public Optional<Autenticacao> findById(Integer id) {
		return autenticacoesRepository.findById(id);
	}
	
}
