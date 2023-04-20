package br.edu.ifms.bookflix.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;

@Service
public class ArquivoService {
	
	@Autowired
	private ObraService obraServices;
	@Autowired
	private DbxClientV2 cloudFileManager;
	private String uri;
	private String newFileName;
	
	public void validateArquivo(boolean fileIsEmpty, String fileName) {
		if(fileIsEmpty) throw new IllegalArgumentException("O arquivo está vazio");
		
		String extensao = getExtensaoFrom(fileName);
		if(!extensao.equals(".pdf") && !extensao.equals(".doc") && !extensao.equals(".docx")) {
			throw new IllegalArgumentException("O arquivo tem que ser do tipo pdf, doc ou docx");
		}
		
		setNewFileName(extensao);
	}
	
	public String getExtensaoFrom(String fileName) {
		int fromDotIndex = fileName.lastIndexOf('.');
		fromDotIndex = fromDotIndex == -1 ? fileName.length() : fromDotIndex;
		return fileName.substring(fromDotIndex).toLowerCase();
	}
	
	private void setNewFileName(String extensao) {
		newFileName = UUID.randomUUID().toString().replace("-", "").substring(0, 16) + extensao;
	}
	
	public Resource getArquivo(String ifsn) {
		String filePath = obraServices.getObraFilePath(ifsn);
		return new FileSystemResource(filePath);
	}
	
	public String getFileType(String fileName) {
		String extensao = getExtensaoFrom(fileName);
		if(extensao.equals(".pdf")) return "application/pdf";
		if(extensao.equals(".doc")) return "application/msword"; 
		return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
	}
	
	public void deleteFile(String ifsn, String originalFileName) throws IOException {
		String filePath = obraServices.getPathToDeleteObraFile(ifsn, originalFileName);
		if(filePath.equals(null)) {
			throw new IllegalArgumentException("Não foi possível verificar o arquivo a ser excluído");
		}
		Path file = Paths.get(filePath);
		Files.delete(file);
	}
	
	public void upload(InputStream inputStream) throws IOException, DbxException {
		uri = '/' + newFileName;
		cloudFileManager.files().upload(uri).uploadAndFinish(inputStream);
	}
		
	public String getURI() {
		return uri;
	}
	
}
