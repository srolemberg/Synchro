package br.com.samirrolemberg.synchro.model;

import java.io.Serializable;



public class Anexo implements Serializable{

	private static final long serialVersionUID = 1218675634215027870L;

	private final long idAnexo;
	private final long tamanho;
	private final String tipo;
	private final String url;
	private final Post post;
	private final int acesso;
	
	public static class Builder{
		private long idAnexo;
		private long tamanho;
		private String tipo;
		private String url;
		private Post post;
		private int acesso;
		
		public Anexo build(){
			return new Anexo(this);
		}

		public Builder idAnexo(long idAnexo){this.idAnexo = idAnexo; return this;}
		public Builder tamanho(long tamanho){this.tamanho = tamanho; return this;}
		public Builder tipo(String tipo){this.tipo = tipo; return this;}
		public Builder url(String url){this.url = url; return this;}
		public Builder post(Post post){this.post = post; return this;}
		public Builder acesso(int acesso){this.acesso = acesso; return this;}

	}
	private Anexo(Builder builder){
		super();
		idAnexo = builder.idAnexo;
		tamanho = builder.tamanho;
		tipo = builder.tipo;
		url = builder.url;
		post = builder.post;
		acesso = builder.acesso;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public long getIdAnexo() {
		return idAnexo;
	}
	public long getTamanho() {
		return tamanho;
	}
	public String getTipo() {
		return tipo;
	}
	public String getUrl() {
		return url;
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
		builder2.append("Anexo [idAnexo=");
		builder2.append(idAnexo);
		builder2.append(", tamanho=");
		builder2.append(tamanho);
		builder2.append(", tipo=");
		builder2.append(tipo);
		builder2.append(", url=");
		builder2.append(url);
		builder2.append(", post=");
		builder2.append(post);
		builder2.append(", acesso=");
		builder2.append(acesso);
		builder2.append("]");
		return builder2.toString();
	}
	

}
