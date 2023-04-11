package br.edu.ifms.bookflix.projection;

import br.edu.ifms.bookflix.model.Autor;

import java.util.List;

public interface ObraView {
		
	String getIfsn();
	String getTitulo();
	String getArea();
	int getAno();
	String getDescricao();	
	List<Autor> getAutores();
	
}
