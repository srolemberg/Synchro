package br.com.samirrolemberg.synchro.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndCategoryImpl;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndContentImpl;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEnclosureImpl;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntryImpl;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndFeed;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndImageImpl;

public class SimpleFeed implements Serializable {
	
	private static final long serialVersionUID = 8848081141427890290L;

	public static Feed consumir(SyndFeed rome, String rss){
		List<Categoria> categorias = null;
		if (rome.getCategories()!=null) {
			categorias = new ArrayList<Categoria>();
			for (Object obj : rome.getCategories()) {
				SyndCategoryImpl synd = (SyndCategoryImpl) obj;
				categorias.add(new Categoria.Builder()
				.nome(synd.getName()==null?null:synd.getName())
				.url(synd.getTaxonomyUri()==null?null:synd.getTaxonomyUri())
				.build());
			}			
		}
		Imagem imagem = null;
		if (rome.getImage()!=null) {
			SyndImageImpl synd = (SyndImageImpl) rome.getImage();
			imagem = new Imagem.Builder()
			.descricao(synd.getDescription()==null?null:synd.getDescription())
			.link(synd.getLink()==null?null:synd.getLink())
			.titulo(synd.getTitle()==null?null:synd.getTitle())
			.url(synd.getUrl()==null?null:synd.getUrl())
			.build();
		}

		List<Post> posts = null;
		if (rome.getEntries()!=null) {
			posts = new ArrayList<Post>();
			for (Object obj : rome.getEntries()) {
				SyndEntryImpl synd = (SyndEntryImpl) obj;
				
				Descricao descricao = null;
				List<Categoria> categorias2 = null;
				List<Conteudo> conteudos = null;
				List<Anexo> anexos = null;
				
				if (synd.getCategories()!=null) {
					categorias2 = new ArrayList<Categoria>();
					for (Object obj2 : synd.getCategories()) {
						SyndCategoryImpl synd2 = (SyndCategoryImpl) obj2;
						categorias2.add(new Categoria.Builder()
						.nome(synd2.getName()==null?null:synd2.getName())
						.url(synd2.getTaxonomyUri()==null?null:synd2.getTaxonomyUri())
						.build());
					}			
				}
				if (synd.getContents()!=null) {
					conteudos = new ArrayList<Conteudo>();
					for (Object obj3 : synd.getContents()) {
						SyndContentImpl synd3 = (SyndContentImpl) obj3;
						conteudos.add(new Conteudo.Builder()
						.modo(synd3.getMode()==null?null:synd3.getMode())
						.tipo(synd3.getType()==null?null:synd3.getType())
						.valor(synd3.getValue()==null?null:synd3.getValue())
						.build());
					}
				}
				if (synd.getEnclosures()!=null) {
					anexos = new ArrayList<Anexo>();
					for (Object obj4 : synd.getEnclosures()) {
						SyndEnclosureImpl synd4 = (SyndEnclosureImpl) obj4;
						anexos.add(new Anexo.Builder()
						.tamanho(synd4.getLength()<=0?0:synd4.getLength())
						.tipo(synd4.getType()==null?null:synd4.getType())
						.url(synd4.getUrl()==null?null:synd4.getUrl())
						.build());
					}			
				}
				if (synd.getDescription()!=null) {
					SyndContentImpl d = (SyndContentImpl) synd.getDescription();
					descricao = new Descricao.Builder()
					.modo(d.getMode()==null?null:d.getMode())
					.tipo(d.getType()==null?null:d.getType())
					.valor(d.getValue()==null?null:d.getValue())
					.build();
				}
				Post post = new Post.Builder()
				.autor(synd.getAuthor()==null?null:synd.getAuthor())
				.link(synd.getLink()==null?null:synd.getLink())
				.data_publicacao(synd.getPublishedDate()==null?null:synd.getPublishedDate())
				.titulo(synd.getTitle()==null?null:synd.getTitle())
				.data_atualizacao(synd.getUpdatedDate()==null?null:synd.getUpdatedDate())
				.link_URI(synd.getUri()==null?null:synd.getUri())
				.descricao(descricao)
				.categorias(categorias2)
				.conteudos(conteudos)
				.anexos(anexos)
				.build();
				posts.add(post);
			}
		}
		Feed feed = new Feed.Builder()
		.autor(rome.getAuthor()==null?null:rome.getAuthor())
		.categorias(categorias)
		.direitoAutoral(rome.getCopyright()==null?null:rome.getCopyright())
		.descricao(rome.getDescription()==null?null:rome.getDescription())
		.codificacao(rome.getEncoding()==null?null:rome.getEncoding())
		.tipoFeed(rome.getFeedType()==null?null:rome.getFeedType())
		.idioma(rome.getLanguage()==null?null:rome.getLanguage())
		.link(rome.getLink()==null?null:rome.getLink())
		.data_publicacao(rome.getPublishedDate()==null?null:rome.getPublishedDate())
		.titulo(rome.getTitle()==null?null:rome.getTitle())
		.uri(rome.getUri()==null?null:rome.getUri())
		.rss(rss)
		.imagem(imagem)
		.posts(posts)
		.build();
		return feed;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
