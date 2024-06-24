package pacifico.mvm.bookflix.projection;

import java.util.List;

import pacifico.mvm.bookflix.model.Autor;

public interface ObraView {
		
	String getIfsn();
	String getTitulo();
	String getArea();
	int getAno();
	String getDescricao();	
	List<Autor> getAutores();
	
}
