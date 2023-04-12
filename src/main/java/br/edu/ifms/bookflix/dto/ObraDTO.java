package br.edu.ifms.bookflix.dto;

import br.edu.ifms.bookflix.model.Obra;
import br.edu.ifms.bookflix.model.Professor;
import br.edu.ifms.bookflix.model.Usuario;
import br.edu.ifms.bookflix.model.Autor;
import br.edu.ifms.bookflix.model.Avaliacao;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.constraints.NotNull;

public class ObraDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	@NotNull(message="Preenchimento obrigatório")
	private String ifsn;
	private String titulo;
	private String area;
	private String descricao;
	private String nome_arquivo;
	private String caminho_arquivo;
	private int ano;
	private Professor professor;
	private List<Avaliacao> avaliacoes;
	private List<Autor> autores;
		
	public ObraDTO() {
		// TODO Auto-generated constructor stub
	}

	public ObraDTO(Obra objeto) {
		this.id = objeto.getId();
		this.ifsn = objeto.getIfsn();
		this.titulo = objeto.getTitulo();
		this.area = objeto.getArea();
		this.descricao = objeto.getDescricao();
		this.autores = objeto.getAutores();
		this.nome_arquivo = objeto.getNomeArquivo();
		this.caminho_arquivo = objeto.getCaminhoArquivo();
		this.ano = objeto.getAno();
		this.professor = objeto.getProfessor();
		this.avaliacoes = objeto.getAvaliacoes();
		this.autores = objeto.getAutores();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIfsn() {
		return ifsn;
	}

	public void setIfsn(String ifsn) {
		this.ifsn = ifsn;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getNomeArquivo() {
		return nome_arquivo;
	}

	public void setNomeArquivo(String nome_arquivo) {
		this.nome_arquivo = nome_arquivo;
	}

	public String getCaminhoArquivo() {
		return caminho_arquivo;
	}

	public void setCaminhoArquivo(String caminho_arquivo) {
		this.caminho_arquivo = caminho_arquivo;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public List<Avaliacao> getAvaliacoes() {
		return avaliacoes;
	}

	public void setAvaliacoes(List<Avaliacao> avaliacoes) {
		this.avaliacoes = avaliacoes;
	}

	public List<Autor> getAutores() {
		return autores;
	}

	public void setAutores(List<Autor> autores) {
		this.autores = autores;
	}

	public Obra fromDTO(ObraDTO objetoDTO) {
		Obra obraAuxiliar = new Obra();
		obraAuxiliar.setId(objetoDTO.getId());
		obraAuxiliar.setIfsn(objetoDTO.getIfsn());
		obraAuxiliar.setTitulo(objetoDTO.getTitulo());
		obraAuxiliar.setArea(objetoDTO.getArea());
		obraAuxiliar.setDescricao(objetoDTO.getDescricao());
		obraAuxiliar.setNomeArquivo(objetoDTO.getNomeArquivo());
		obraAuxiliar.setCaminhoArquivo(objetoDTO.getCaminhoArquivo());
		obraAuxiliar.setAno(objetoDTO.getAno());
		obraAuxiliar.setProfessor(objetoDTO.getProfessor());
		obraAuxiliar.setAutores(objetoDTO.getAutores());
		obraAuxiliar.setAvaliacoes(objetoDTO.getAvaliacoes());
		return obraAuxiliar;
	}
	
	public Obra fromNewDTO(ObraDTO objetoNewDTO) {
		Obra obra = new Obra(null, objetoNewDTO.getIfsn(), objetoNewDTO.getTitulo(), 
			objetoNewDTO.getArea(), objetoNewDTO.getDescricao(), 
			objetoNewDTO.getNomeArquivo(), objetoNewDTO.getCaminhoArquivo(), objetoNewDTO.getAno(), objetoNewDTO.getProfessor());
		obra.setAutores(objetoNewDTO.getAutores());
		return obra;
	}

	public Obra obraWithoutSomeAttributes(Obra objeto) {
		objeto.setProfessor(professorOnlyWithNameAndSiape(objeto.getProfessor()));
		objeto.setAutores(listOfAutoresOnlyWithName((objeto.getAutores())));
		objeto.setAvaliacoes(listOfAvaliacoesOnlyWithUsersNameWithoutObra(objeto.getAvaliacoes()));
		return objeto;
	}
	
	private Professor professorOnlyWithNameAndSiape(Professor professor) {
    	professor.setId(null);
    	professor.setUsuario(usuarioOnlyWithName(professor.getUsuario()));
    	professor.setObras(null);
    	return professor;
    }
    
    private Usuario usuarioOnlyWithName(Usuario usuario) {
    	return new Usuario(null, usuario.getNome(), null,null,null);
    }
	
	private Avaliacao avaliacaoWithoutUsersDataExceptName(Avaliacao avaliacao) {
		avaliacao.setUsuario(usuarioOnlyWithName(avaliacao.getUsuario()));
		avaliacao.setObra(null);
		return avaliacao;
	}
	
	private List<Avaliacao> listOfAvaliacoesOnlyWithUsersNameWithoutObra(List<Avaliacao> avaliacoes) {
		return avaliacoes.stream().map(a -> avaliacaoWithoutUsersDataExceptName(a))
				.collect(Collectors.toList());
	}
	
	private Autor autorOnlyWithName(Autor autor) {
		return new Autor(null, autor.getNome(), null);
	}

	private List<Autor> listOfAutoresOnlyWithName(List<Autor> autor) {
		return autor.stream().map(a -> autorOnlyWithName(a))
				.collect(Collectors.toList());
	}		
	
}
