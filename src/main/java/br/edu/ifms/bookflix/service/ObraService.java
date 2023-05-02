package br.edu.ifms.bookflix.service;

import br.edu.ifms.bookflix.dto.ObraDTO;
import br.edu.ifms.bookflix.model.Obra;
import br.edu.ifms.bookflix.projection.ObraView;
import br.edu.ifms.bookflix.repository.ObraRepository;
import br.edu.ifms.bookflix.service.exception.DataIntegrityException;
import br.edu.ifms.bookflix.service.exception.ObjectNotFoundException;

import java.text.Normalizer;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ObraService {

	@Autowired
	private ObraRepository obraRepository;
	private final ObraDTO obraDTO = new ObraDTO();
	
	public Obra find(Integer id) {
		Optional<Obra> objeto = obraRepository.findById(id); 
		return objeto.orElseThrow(() -> new ObjectNotFoundException( 
				 "Obra não encontrada! Id: " + id));		
	}
	
	@Transactional
	public Obra insert (Obra objeto) {
		objeto.setId(null);
		return obraRepository.save(objeto);	
	}
	
	public Obra update(Obra objetoEditado) {
		Obra objetoAtualizado = find(objetoEditado.getId());
		objetoAtualizado = objetoEditado;
		return obraRepository.save(objetoAtualizado);
	}
	
	@Transactional
	public void delete(Integer id, Obra objeto) {
		if(!objeto.equals(find(id))) {
			throw new IllegalArgumentException("A obra a ser removida é diferente da obra cadastrada no banco de dados");
		}
		deleteById(id);
	}
	
	public List<Obra> findAll() {
		return obraRepository.findAll(); 
	}
	
	@Transactional
	public void deleteById(Integer id) {
		find(id);
		try {
			obraRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível remover. Verifique a integridade referencial.");
		}
	}
	
	public void save(Obra obra) {
		obraRepository.saveAndFlush(obra);
	}
	
	public Obra findByIfsn(String ifsn) {
		Optional<Obra> objeto = obraRepository.findByIfsn(ifsn); 
		return objeto.orElseThrow(() -> new ObjectNotFoundException( 
				 "Obra não encontrada! IFSN: " + ifsn));		
	}
    
    public Obra fromDTO(ObraDTO objetoDTO) {
    	return obraDTO.fromDTO(objetoDTO);
    }
	
    public Obra fromNewDTO(ObraDTO objetoNewDTO) {
    	return obraDTO.fromNewDTO(objetoNewDTO);
    }
	
	public Page<ObraView> searchObra(String pesquisa, int page) {
		Pageable pageable = PageRequest.of(page, 10);
		return obraRepository.searchObra(unaccentedParam(pesquisa), pageable);
	}
	
	public Page<ObraView> searchObraByIfsn(String ifsn, int page) {
		Pageable pageable = PageRequest.of(page, 10);
		return obraRepository.searchObraByIfsn(unaccentedParam(ifsn), pageable);
	}
	
	public Page<ObraView> searchObraByTitulo(String titulo, int page) {
		Pageable pageable = PageRequest.of(page, 10);
		return obraRepository.searchObraByTitulo(unaccentedParam(titulo), pageable);
	}
	
	public Page<ObraView> searchObraByArea(String area, int page) {
		Pageable pageable = PageRequest.of(page, 10);
		return obraRepository.searchObraByArea(unaccentedParam(area), pageable);
	}
	
	public Page<ObraView> searchObraByAno(int ano, int page) {
		Pageable pageable = PageRequest.of(page, 10);
		return obraRepository.searchObraByAno(ano, pageable);
	}
    
    public Obra obraWithoutSomeAtributes(Obra objeto) {
    	return obraDTO.obraWithoutSomeAttributes(objeto);
    }
    
    private String unaccentedParam(String parameter) {
    	return Normalizer.normalize(parameter, Normalizer.Form.NFD)
    			.replaceAll("[^\\p{ASCII}]",  "");
    }
    
    public void validateObraId(Integer paramPathId, Integer obraBodyId) {
    	if(!paramPathId.equals(obraBodyId)) {
    		throw new IllegalArgumentException("O id da URL é diferente do id da obra informada no corpo da solicitação");
    	}
    }
    
    public String getObraFilePath(String ifsn) {
    	return obraRepository.getCaminhoArquivoByIfsn(ifsn);
    }
    
    public String getPathToDeleteObraFile(String ifsn, String originalFileName) {
    	return obraRepository.getPathToDeleteObraFile(ifsn, originalFileName);
    }
    
}
