package br.edu.ifms.bookflix.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class ArquivoService {
	
	@Autowired
	private ObraService obraServices;
	
	public void validateArquivo(boolean fileIsEmpty, String fileName) {
		if(fileIsEmpty) throw new IllegalArgumentException("O arquivo está vazio");
		
		String extensao = getExtensaoFrom(fileName);
		if(!extensao.equals(".pdf") && !extensao.equals(".doc") && !extensao.equals(".docx")) {
			throw new IllegalArgumentException("O arquivo tem que ser do tipo pdf, doc ou docx");
		}
	}
	
	public String getExtensaoFrom(String fileName) {
		int fromDotIndex = fileName.lastIndexOf('.');
		fromDotIndex = fromDotIndex == -1 ? fileName.length() : fromDotIndex;
		return fileName.substring(fromDotIndex).toLowerCase();
	}
	
	public String getNewFileName(String fileName) {
		return UUID.randomUUID().toString().replace("-", "").substring(0, 16) + getExtensaoFrom(fileName);
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
	
}
