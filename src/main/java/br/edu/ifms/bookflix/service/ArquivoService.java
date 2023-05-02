package br.edu.ifms.bookflix.service;

import br.edu.ifms.bookflix.service.exception.FileNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dropbox.core.DbxDownloader;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.DeleteErrorException;
import com.dropbox.core.v2.files.FileMetadata;

@Service
public class ArquivoService {
	
	@Autowired
	private ObraService obraService;
	@Autowired
	private DbxClientV2 cloudFileManager;
	private String uri;
	private String extensao;
	
	public void validateArquivo(boolean fileIsEmpty, String fileName) {
		if(fileIsEmpty) throw new IllegalArgumentException("O arquivo está vazio");
		setExtensaoFrom(fileName);
		if(!extensao.equals(".pdf") && !extensao.equals(".doc") && !extensao.equals(".docx")) {
			throw new IllegalArgumentException("O arquivo tem que ser do tipo pdf, doc ou docx");
		}
	}
	
	private void setExtensaoFrom(String fileName) {
		int fromDotIndex = fileName.lastIndexOf('.');
		fromDotIndex = fromDotIndex == -1 ? fileName.length() : fromDotIndex;
		extensao = fileName.substring(fromDotIndex).toLowerCase();
	}
	
	public String getExtensao() {
		return extensao;
	}
	
	public void upload(InputStream inputStream) throws IOException, DbxException {
		String newFileName = UUID.randomUUID().toString().replace("-", "").substring(0, 16) + extensao;
		uri = '/' + newFileName;
		cloudFileManager.files().upload(uri).uploadAndFinish(inputStream);
	}
		
	public String getURI() {
		return uri;
	}
	
	public DbxDownloader<FileMetadata> getFile(String ifsn) throws DbxException {
		String filePath = obraService.getObraFilePath(ifsn);
		if(filePath == null) {
			throw new FileNotFoundException("O arquivo da obra de IFSN: " + ifsn + ", não foi encontrado");
		}
		setExtensaoFrom(filePath);
		return cloudFileManager.files().download(filePath);	
	}
	
	public void deleteFile(String ifsn, String originalFileName) throws DeleteErrorException, DbxException {
		String filePath = obraService.getPathToDeleteObraFile(ifsn, originalFileName);
		if(filePath == null) {
			throw new IllegalArgumentException("Não foi possível verificar o arquivo a ser excluído");
		}
		cloudFileManager.files().deleteV2(filePath);
	}
	
}
