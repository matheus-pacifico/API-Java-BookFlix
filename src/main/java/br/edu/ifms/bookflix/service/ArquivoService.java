package br.edu.ifms.bookflix.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class ArquivoService {
	private String newFileName;
	
	public void validateArquivo(boolean fileIsEmpty, String fileName) {
		if(fileIsEmpty) throw new IllegalArgumentException("O arquivo está vazio");
		
		int fromDotIndex = fileName.lastIndexOf('.');
		fromDotIndex = fromDotIndex == -1 ? fileName.length() : fromDotIndex;
		String extensao = fileName.substring(fromDotIndex).toLowerCase();
		if(!extensao.equals(".pdf") && !extensao.equals(".doc") && !extensao.equals(".docx")) {
			throw new IllegalArgumentException("O arquivo tem que ser do tipo pdf, doc ou docx");
		}
		
		setNewFileName(extensao);
	}
	
	public void setNewFileName(String extensao) {
		newFileName = UUID.randomUUID().toString().replace("-", "").substring(0,16) + extensao;
	}
	
	public String getNewFileName() {
		return newFileName;
	}
	
}
