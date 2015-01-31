package br.com.samirrolemberg.synchro.model;

import java.io.Serializable;



public class Conteudo implements Serializable{

	private static final long serialVersionUID = -4356582009446830314L;

	private final long idConteudo;
	private final String modo;
	private final String tipo;
	private final String valor;
	private final Post post;
	private final int acesso;

	public static class Builder{
		private long idConteudo;
		private String modo;
		private String tipo;
		private String valor;
		private Post post;
		private int acesso;
		
		public Conteudo build(){
			return new Conteudo(this);
		}

		public Builder idConteudo(long idConteudo){this.idConteudo = idConteudo; return this;}
		public Builder modo(String modo){this.modo = modo; return this;}
		public Builder tipo(String tipo){this.tipo = tipo; return this;}
		public Builder valor(String valor){this.valor = valor; return this;}
		public Builder post(Post post){this.post = post; return this;}
		public Builder acesso(int acesso){this.acesso = acesso; return this;}

	}
	private Conteudo(Builder builder){
		super();
		idConteudo = builder.idConteudo;
		modo = builder.modo;
		tipo = builder.tipo;
		valor = builder.valor;
		post = builder.post;
		acesso = builder.acesso;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public long getIdConteudo() {
		return idConteudo;
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
		builder2.append("Conteudo [idConteudo=");
		builder2.append(idConteudo);
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
