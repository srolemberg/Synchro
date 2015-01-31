package br.com.samirrolemberg.synchro.model;

import java.io.Serializable;



public class Categoria implements Serializable{

	private static final long serialVersionUID = 6532019828485474770L;

	private final long idCategoria;
	private final String nome;
	private final String url;
	private final Feed feed;
	private final Post post;
	private final int acesso;
	
	public static class Builder{
		
		private long idCategoria;
		private String nome;
		private String url;
		private Feed feed;
		private Post post;
		private int acesso;
		
		public Categoria build(){
			return new Categoria(this);
		}
		
		public Builder idCategoria(long idCategoria){this.idCategoria = idCategoria; return this;}
		public Builder nome(String nome){this.nome = nome; return this;}
		public Builder url(String url){this.url = url; return this;}
		public Builder feed(Feed feed){this.feed = feed; return this;}
		public Builder post(Post post){this.post = post; return this;}
		public Builder acesso(int acesso){this.acesso = acesso; return this;}

	}
	
	private Categoria(Builder builder){
		super();
		idCategoria = builder.idCategoria;
		nome = builder.nome;
		url = builder.url;
		feed = builder.feed;
		post = builder.post;
		acesso = builder.acesso;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public long getIdCategoria() {
		return idCategoria;
	}

	public String getNome() {
		return nome;
	}

	public String getUrl() {
		return url;
	}

	public Feed getFeed() {
		return feed;
	}

	public Post getPost() {
		return post;
	}

	public int getAcesso() {
		return acesso;
	}

	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("Categoria [idCategoria=");
		builder2.append(idCategoria);
		builder2.append(", nome=");
		builder2.append(nome);
		builder2.append(", url=");
		builder2.append(url);
		builder2.append(", feed=");
		builder2.append(feed);
		builder2.append(", post=");
		builder2.append(post);
		builder2.append(", acesso=");
		builder2.append(acesso);
		builder2.append("]");
		return builder2.toString();
	}

}
