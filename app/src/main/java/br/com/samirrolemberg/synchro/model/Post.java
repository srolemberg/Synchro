package br.com.samirrolemberg.synchro.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Post implements Serializable{

	private static final long serialVersionUID = -1704116670723414049L;

	private final long idPost;
	private final String autor;
	private final String link;
	private final Date data_publicacao;
	private final String titulo;
	private final Date data_atualizacao;
	private final String link_URI;
	private final Descricao descricao;
	private final List<Categoria> categorias;
	private final List<Conteudo> conteudos;
	private final List<Anexo> anexos;
	private final Feed feed;
	private final int acesso;
	
	public static class Builder{
		private long idPost;
		private String autor;
		private String link;
		private Date data_publicacao;
		private String titulo;
		private Date data_atualizacao;
		private String link_URI;
		private Descricao descricao;
		private List<Categoria> categorias;
		private List<Conteudo> conteudos;
		private List<Anexo> anexos;
		private Feed feed;
		private int acesso;

		//apenas se algum do parametros necessitar ser inicializado no construtror
		//tornar publico e colocar seus parametros em Builder
		//private Builder(){
		//	
		//}
		
		public Builder idPost(long idPost){this.idPost = idPost; return this;}
		public Builder autor(String autor){this.autor = autor; return this;}
		public Builder link(String link){this.link = link; return this;}
		public Builder data_publicacao(Date data_publicacao){this.data_publicacao = data_publicacao; return this;}
		public Builder titulo(String titulo){this.titulo = titulo; return this;}
		public Builder data_atualizacao(Date data_atualizacao){this.data_atualizacao = data_atualizacao; return this;}
		public Builder link_URI(String link_URI){this.link_URI = link_URI; return this;}
		public Builder descricao(Descricao descricao){this.descricao = descricao; return this;}
		public Builder categorias(List<Categoria> categorias){this.categorias = categorias; return this;}
		public Builder conteudos(List<Conteudo> conteudos){this.conteudos = conteudos; return this;}
		public Builder anexos(List<Anexo> anexos){this.anexos = anexos; return this;}
		public Builder feed(Feed feed){this.feed = feed; return this;}
		public Builder acesso(int acesso){this.acesso = acesso; return this;}

		public Post build(){
			return new Post(this);
		}
		
	}//end builder
	
	private Post(Builder builder){
		super();
		idPost = builder.idPost;
		autor = builder.autor;
		link = builder.link;
		data_publicacao = builder.data_publicacao;
		titulo = builder.titulo;
		data_atualizacao = builder.data_atualizacao;
		link_URI = builder.link_URI;
		descricao = builder.descricao;
		categorias = builder.categorias;
		conteudos = builder.conteudos;
		anexos = builder.anexos;
		feed = builder.feed;
		acesso = builder.acesso;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public long getIdPost() {
		return idPost;
	}

	public String getAutor() {
		return autor;
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

	public Date getData_atualizacao() {
		return data_atualizacao;
	}

	public String getLink_URI() {
		return link_URI;
	}

	public Descricao getDescricao() {
		return descricao;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public List<Conteudo> getConteudos() {
		return conteudos;
	}

	public List<Anexo> getAnexos() {
		return anexos;
	}

	public Feed getFeed() {
		return feed;
	}

	public int getAcesso() {
		return acesso;
	}

	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("Post [idPost=");
		builder2.append(idPost);
		builder2.append(", autor=");
		builder2.append(autor);
		builder2.append(", link=");
		builder2.append(link);
		builder2.append(", data_publicacao=");
		builder2.append(data_publicacao);
		builder2.append(", titulo=");
		builder2.append(titulo);
		builder2.append(", data_atualizacao=");
		builder2.append(data_atualizacao);
		builder2.append(", link_URI=");
		builder2.append(link_URI);
		builder2.append(", descricao=");
		builder2.append(descricao);
		builder2.append(", categorias=");
		builder2.append(categorias);
		builder2.append(", conteudos=");
		builder2.append(conteudos);
		builder2.append(", anexos=");
		builder2.append(anexos);
		builder2.append(", feed=");
		builder2.append(feed);
		builder2.append(", acesso=");
		builder2.append(acesso);
		builder2.append("]");
		return builder2.toString();
	}
	
}
