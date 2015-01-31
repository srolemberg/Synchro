package br.com.samirrolemberg.synchro.model;

import java.io.Serializable;



public class Descricao implements Serializable {

	private static final long serialVersionUID = 1938406296895480420L;

	private final long idDescricao;
	private final String modo;
	private final String tipo;
	private final String valor;
	private final Post post;
	private final int acesso;

	public static class Builder{
		private long idDescricao;
		private String modo;
		private String tipo;
		private String valor;
		private Post post;
		private int acesso;

		public Descricao build(){
			return new Descricao(this);
		}
		public Builder idDescricao(long idDescricao){this.idDescricao = idDescricao; return this;}
		public Builder modo(String modo){this.modo = modo; return this;}
		public Builder tipo(String tipo){this.tipo = tipo; return this;}
		public Builder valor(String valor){this.valor = valor; return this;}
		public Builder post(Post post){this.post = post; return this;}
		public Builder acesso(int acesso){this.acesso = acesso; return this;}

	}
	private Descricao(Builder builder){
		super();
		idDescricao = builder.idDescricao;
		modo = builder.modo;
		tipo = builder.tipo;
		valor = builder.valor;
		post = builder.post;
		acesso = builder.acesso;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public long getIdDescricao() {
		return idDescricao;
	}
	public String getModo() {
		return modo;
	}
	public String getTipo() {
		return tipo;
	}
	public String getValor() {
		return valor;
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
		builder2.append("Descricao [idDescricao=");
		builder2.append(idDescricao);
		builder2.append(", modo=");
		builder2.append(modo);
		builder2.append(", tipo=");
		builder2.append(tipo);
		builder2.append(", valor=");
		builder2.append(valor);
		builder2.append(", post=");
		builder2.append(post);
		builder2.append(", acesso=");
		builder2.append(acesso);
		builder2.append("]");
		return builder2.toString();
	}
}
