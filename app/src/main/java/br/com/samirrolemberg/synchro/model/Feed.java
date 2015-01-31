package br.com.samirrolemberg.synchro.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Feed implements Serializable{

	private static final long serialVersionUID = 43578249293738642L;

	private final long idFeed;
	private final String autor;
	private final List<Categoria> categorias;
	private final String direitoAutoral;
	private final String descricao;
	private final String codificacao;
	private final String tipoFeed;
	private final String idioma;
	private final String link;
	private final Date data_publicacao;
	private final String titulo;
	private final String uri;
	private final Imagem imagem;
	private final List<Post> posts;
	private final Date data_cadastro;
	private final Date data_sincronizacao;
	private final String rss;
	private final int acesso;

	public static class Builder{
		private long idFeed;
		private String autor;
		private List<Categoria> categorias;
		private String direitoAutoral;
		private String descricao;
		private String codificacao;
		private String tipoFeed;
		private String idioma;
		private String link;
		private Date data_publicacao;
		private String titulo;
		private String uri;
		private Imagem imagem;
		private List<Post> posts;
		private Date data_cadastro;
		private Date data_sincronizacao;
		private String rss;
		private int acesso;

		//apenas se algum do parametros necessitar ser inicializado no construtror
		//tornar publico e colocar seus parametros em Builder
		//private Builder(){
		//	
		//}
		
		public Builder idFeed(long idFeed){this.idFeed = idFeed; return this;}
		public Builder autor(String autor){this.autor = autor; return this;}
		public Builder categorias(List<Categoria> categorias){this.categorias = categorias; return this;}
		public Builder direitoAutoral(String direitoAutoral){this.direitoAutoral = direitoAutoral; return this;}
		public Builder descricao(String descricao){this.descricao = descricao; return this;}
		public Builder codificacao(String codificacao){this.codificacao = codificacao; return this;}
		public Builder tipoFeed(String tipoFeed){this.tipoFeed = tipoFeed; return this;}
		public Builder idioma(String idioma){this.idioma = idioma; return this;}
		public Builder link(String link){this.link = link; return this;}
		public Builder data_publicacao(Date data_publicacao){this.data_publicacao = data_publicacao; return this;}
		public Builder titulo(String titulo){this.titulo = titulo; return this;}
		public Builder uri(String uri){this.uri = uri; return this;}
		public Builder imagem(Imagem imagem){this.imagem = imagem; return this;}
		public Builder posts(List<Post> posts){this.posts = posts; return this;}
		public Builder data_cadastro(Date data_cadastro){this.data_cadastro = data_cadastro; return this;}
		public Builder data_sincronizacao(Date data_sincronizacao){this.data_sincronizacao = data_sincronizacao; return this;}
		public Builder rss(String rss){this.rss = rss; return this;}
		public Builder acesso(int acesso){this.acesso = acesso; return this;}

		public Feed build(){
			return new Feed(this);
		}
	}//end builder
	
	private Feed(Builder builder){
		super();
		idFeed = builder.idFeed;
		autor = builder.autor;
		categorias = builder.categorias;
		direitoAutoral = builder.direitoAutoral;
		descricao = builder.descricao;
		codificacao = builder.codificacao;
		tipoFeed = builder.tipoFeed;
		idioma = builder.idioma;
		link = builder.link;
		data_publicacao = builder.data_publicacao;
		titulo = builder.titulo;
		uri = builder.uri;
		imagem = builder.imagem;
		posts = builder.posts;
		data_cadastro = builder.data_cadastro;
		data_sincronizacao = builder.data_sincronizacao;
		rss = builder.rss;
		acesso = builder.acesso;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public long getIdFeed() {
		return idFeed;
	}

	public String getAutor() {
		return autor;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public String getDireitoAutoral() {
		return direitoAutoral;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getCodificacao() {
		return codificacao;
	}

	public String getTipoFeed() {
		return tipoFeed;
	}

	public String getIdioma() {
		return idioma;
	}

	public String getLink() {
		return link;
	}

	public Date getData_publicacao() {
		return data_publicacao;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getUri() {
		return uri;
	}

	public Imagem getImagem() {
		return imagem;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public Date getData_cadastro() {
		return data_cadastro;
	}

	public Date getData_sincronizacao() {
		return data_sincronizacao;
	}

	public String getRss() {
		return rss;
	}

	public int getAcesso() {
		return acesso;
	}

	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("Feed [idFeed=");
		builder2.append(idFeed);
		builder2.append(", autor=");
		builder2.append(autor);
		builder2.append(", categorias=");
		builder2.append(categorias);
		builder2.append(", direitoAutoral=");
		builder2.append(direitoAutoral);
		builder2.append(", descricao=");
		builder2.append(descricao);
		builder2.append(", codificacao=");
		builder2.append(codificacao);
		builder2.append(", tipoFeed=");
		builder2.append(tipoFeed);
		builder2.append(", idioma=");
		builder2.append(idioma);
		builder2.append(", link=");
		builder2.append(link);
		builder2.append(", data_publicacao=");
		builder2.append(data_publicacao);
		builder2.append(", titulo=");
		builder2.append(titulo);
		builder2.append(", uri=");
		builder2.append(uri);
		builder2.append(", imagem=");
		builder2.append(imagem);
		builder2.append(", posts=");
		builder2.append(posts);
		builder2.append(", data_cadastro=");
		builder2.append(data_cadastro);
		builder2.append(", data_sincronizacao=");
		builder2.append(data_sincronizacao);
		builder2.append(", rss=");
		builder2.append(rss);
		builder2.append(", acesso=");
		builder2.append(acesso);
		builder2.append(", getIdFeed()=");
		builder2.append(getIdFeed());
		builder2.append(", getAutor()=");
		builder2.append(getAutor());
		builder2.append(", getCategorias()=");
		builder2.append(getCategorias());
		builder2.append(", getDireitoAutoral()=");
		builder2.append(getDireitoAutoral());
		builder2.append(", getDescricao()=");
		builder2.append(getDescricao());
		builder2.append(", getCodificacao()=");
		builder2.append(getCodificacao());
		builder2.append(", getTipoFeed()=");
		builder2.append(getTipoFeed());
		builder2.append(", getIdioma()=");
		builder2.append(getIdioma());
		builder2.append(", getLink()=");
		builder2.append(getLink());
		builder2.append(", getData_publicacao()=");
		builder2.append(getData_publicacao());
		builder2.append(", getTitulo()=");
		builder2.append(getTitulo());
		builder2.append(", getUri()=");
		builder2.append(getUri());
		builder2.append(", getImagem()=");
		builder2.append(getImagem());
		builder2.append(", getPosts()=");
		builder2.append(getPosts());
		builder2.append(", getData_cadastro()=");
		builder2.append(getData_cadastro());
		builder2.append(", getData_sincronizacao()=");
		builder2.append(getData_sincronizacao());
		builder2.append(", getRss()=");
		builder2.append(getRss());
		builder2.append(", getAcesso()=");
		builder2.append(getAcesso());
		builder2.append(", getClass()=");
		builder2.append(getClass());
		builder2.append(", hashCode()=");
		builder2.append(hashCode());
		builder2.append(", toString()=");
		builder2.append(super.toString());
		builder2.append("]");
		return builder2.toString();
	}
	
}
