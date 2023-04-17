package br.edu.ifms.bookflix.controller;

import br.edu.ifms.bookflix.service.ArquivoService;
import br.edu.ifms.bookflix.service.exception.FileNotFoundException;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
		String newFileName = arquivoServices.getNewFileName(file.getOriginalFilename());
		URI uri = new URI(path + newFileName);

		try {
			//Desativado para não ocorrer problemas com a plataforma de hospedagem da API
			/*file.transferTo(Path.of(uri));*/
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body("Erro ao salvar o arquivo");
		}
		return ResponseEntity.created(uri).build();
	}

	@CrossOrigin(origins = "")
	@GetMapping(value = "/download/{ifsn}")
	ResponseEntity<Resource> downloadArquivo(@PathVariable String ifsn) {
		/*Resource file = arquivoServices.getArquivo(ifsn);
		if(!file.exists()) {
			throw new FileNotFoundException("O arquivo da obra de IFSN: " + ifsn + " não foi encontrado");
		}

		String fileType = arquivoServices.getFileType(file.getFilename());
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(fileType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + ifsn 
						+ file.getFilename().substring(16) + "\"")
				.body(file);*/
		//Método desativado para não ocorrer problemas com a plataforma de hospedagem da API. Quando reativar 
		//o método, deixar apenas o código dentro do /**/ da linha 50 à 59. E retirar o @CrossOrigin.
		return ResponseEntity.ok().body(null);
	}

}
