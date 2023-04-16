package br.edu.ifms.bookflix.controller;

import br.edu.ifms.bookflix.service.ArquivoService;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api/v1/obra/arquivo")
public class ArquivoController {

	@Autowired
	ArquivoService arquivoServices;	

	@PostMapping(value = "/upload")
	ResponseEntity<?> uploadArquivo(@RequestParam MultipartFile file) throws URISyntaxException {
		arquivoServices.validateArquivo(file.isEmpty(), file.getOriginalFilename());

	    String path = "/folder/example/";
		String newFileName = arquivoServices.getNewFileName();
		URI uri = new URI(path + newFileName);

		try {
			//Desativado para não ocorrer problemas com a plataforma de hospedagem da API
			/*file.transferTo(Path.of(uri));*/
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body("Erro ao salvar o arquivo");
		}
		return ResponseEntity.created(uri).build();
	}

}
