package br.edu.ifms.bookflix.controller;

import br.edu.ifms.bookflix.service.ArquivoService;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dropbox.core.DbxDownloader;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.DeleteErrorException;
import com.dropbox.core.v2.files.FileMetadata;

@RestController
@RequestMapping(value = "/api/v1/obra/arquivo")
public class ArquivoController {

	@Autowired
	private ArquivoService arquivoService;

	@PostMapping(value = "/upload")
	public ResponseEntity<?> uploadArquivo(@RequestParam MultipartFile file) throws URISyntaxException {
		arquivoService.validateArquivo(file.isEmpty(), file.getOriginalFilename());

		try {
			arquivoService.upload(file.getInputStream());
			URI uri = new URI(arquivoService.getURI());
			return ResponseEntity.created(uri).build();
		} catch(IOException e) {
			return ResponseEntity.internalServerError().body("Erro no processamento do arquivo");
		} catch(DbxException e) {
			return ResponseEntity.internalServerError().body("Erro ao salvar o arquivo");
		}
	}

	@GetMapping(value = "/download/{ifsn}")
	public ResponseEntity<Resource> downloadArquivo(@PathVariable String ifsn) throws DbxException {
		DbxDownloader<FileMetadata> fileMetaData = arquivoService.getFile(ifsn);
		long fileLength = fileMetaData.getResult().getSize();
		Resource file = new InputStreamResource(fileMetaData.getInputStream());
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(fileMetaData.getContentType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + ifsn 
						+ arquivoService.getExtensao() + "\"")
				.contentLength(fileLength)
				.body(file);
	}

	@DeleteMapping(value = "/delete/{ifsn}/{originalFileName}")
	public ResponseEntity<?> deleteArquivo(@PathVariable String ifsn, @PathVariable String originalFileName) throws DbxException {
		try {
			arquivoService.deleteFile(ifsn, originalFileName);
			return ResponseEntity.ok().build();
		} catch (DeleteErrorException e) {
			return ResponseEntity.internalServerError().body("Erro ao excluir o arquivo");
		}
	}

}
