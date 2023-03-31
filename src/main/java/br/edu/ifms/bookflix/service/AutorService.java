package br.edu.ifms.bookflix.service;

import br.edu.ifms.bookflix.model.Autor;

import br.edu.ifms.bookflix.repository.AutorRepository;

import br.edu.ifms.bookflix.dto.AutorDTO;

import br.edu.ifms.bookflix.service.exception.DataIntegrityException;
import br.edu.ifms.bookflix.service.exception.ObjectNotFoundException;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorService {
	
	@Autowired
	private AutorRepository autoresRepository;
	
	public Autor find(Integer id) {
		Optional<Autor> objeto = autoresRepository.findById(id); 
		return objeto.orElseThrow(() -> new ObjectNotFoundException( 
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Autor.class.getName()));		
	}
	
	@Transactional
	public Autor insert (Autor objeto) {
		objeto.setId(null);
		autoresRepository.save(objeto);
		return objeto;
	}
	
	public Autor update(Autor objeto) {
		Autor novoObjeto = find(objeto.getId());
		updateData(novoObjeto, objeto);
		return autoresRepository.save(novoObjeto);
	}
	
	@Transactional
	public void delete(Integer id) {
		find(id);
		try {
			autoresRepository.deleteById(id);	
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível remover. Verifique a integridade referencial.");
		}
	}	
	
	public Autor fromDTO(AutorDTO objetoDTO) {
		return new Autor(objetoDTO.getId(), objetoDTO.getNome(), objetoDTO.getObra());
	}
	
	public Autor fromNewDTO(AutorDTO objetoNewDTO) {
		return new Autor(null, objetoNewDTO.getNome(),  objetoNewDTO.getObra());
	}
	
	private void updateData(Autor novoObjeto, Autor objeto) {
		novoObjeto.setNome(objeto.getNome());
		novoObjeto.setObra(objeto.getObra());
	}
	
	public List<Autor> findAll() {
		return autoresRepository.findAll();
	}
	
	@Transactional
	public void deleteById(Integer id) {
		autoresRepository.deleteById(id);
	}
	
	public void save(Autor avaliacao) {
		autoresRepository.saveAndFlush(avaliacao);
	}
	
	public Optional<Autor> findById(Integer id) {
		return autoresRepository.findById(id);
	}

}
