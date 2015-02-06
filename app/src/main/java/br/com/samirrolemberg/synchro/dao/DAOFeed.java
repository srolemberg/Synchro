package br.com.samirrolemberg.synchro.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.com.samirrolemberg.synchro.conn.DatabaseManager;
import br.com.samirrolemberg.synchro.model.Feed;

public class DAOFeed extends DAO{

	public final static String TABLE = "feed";
	private SQLiteDatabase database = null;

	public DAOFeed(Context context) {
		super(context);
		database = DatabaseManager.getInstance().openDatabase();
	}

	public long inserir(Feed feed){
		ContentValues values = new ContentValues();
		values.put("autor", feed.getAutor());
		values.put("direitoAutoral", feed.getDireitoAutoral());
		values.put("descricao", feed.getDescricao());
		values.put("codificacao", feed.getCodificacao());
		values.put("tipoFeed", feed.getTipoFeed());
		values.put("idioma", feed.getIdioma());
		values.put("link", feed.getLink());
		values.put("data_publicacao", feed.getData_publicacao()==null?null:feed.getData_publicacao().getTime());
		values.put("titulo", feed.getTitulo());
		values.put("uri", feed.getUri());
		values.put("data_cadastro", new Date().getTime());
		values.put("rss", feed.getRss());
		
		long id = database.insert(TABLE, null, values);
		
		return id;
	}
	
	public Feed buscar(String rss){
		Feed feed = null;
		try {
			String[] args = {rss};
			StringBuffer sql = new StringBuffer();
			sql.append("select * from "+TABLE+" where rss = ?");
			Cursor cursor = database.rawQuery(sql.toString(), args);
			if (cursor.moveToNext()) {
				feed = new Feed.Builder()
				.idFeed(cursor.getLong(cursor.getColumnIndex("idFeed")))
				.autor(cursor.getString(cursor.getColumnIndex("autor")))
				.direitoAutoral(cursor.getString(cursor.getColumnIndex("direitoAutoral")))
				.descricao(cursor.getString(cursor.getColumnIndex("descricao")))
				.codificacao(cursor.getString(cursor.getColumnIndex("codificacao")))
				.tipoFeed(cursor.getString(cursor.getColumnIndex("tipoFeed")))
				.idioma(cursor.getString(cursor.getColumnIndex("idioma")))
				.link(cursor.getString(cursor.getColumnIndex("link")))
				.data_publicacao(new Date(cursor.getLong(cursor.getColumnIndex("data_publicacao"))))
				.titulo(cursor.getString(cursor.getColumnIndex("titulo")))
				.uri(cursor.getString(cursor.getColumnIndex("uri")))
				.data_cadastro(new Date(cursor.getLong(cursor.getColumnIndex("data_cadastro"))))
				.data_sincronizacao(new Date(cursor.getLong(cursor.getColumnIndex("data_sincronizacao"))))
				.rss(cursor.getString(cursor.getColumnIndex("rss")))
				.build();
			}
			cursor.close();
		} catch (Exception e) {
			Log.i("DAOs", e.getLocalizedMessage(),e);
		}
		return feed;
	}

	public int atualiza(Feed feed, long idFeed){
		ContentValues values = new ContentValues();
		values.put("autor", feed.getAutor());
		values.put("direitoAutoral", feed.getDireitoAutoral());
		values.put("descricao", feed.getDescricao());
		values.put("codificacao", feed.getCodificacao());
		values.put("tipoFeed", feed.getTipoFeed());
		values.put("idioma", feed.getIdioma());
		values.put("link", feed.getLink());
		values.put("data_publicacao", feed.getData_publicacao()==null?null:feed.getData_publicacao().getTime());
		values.put("titulo", feed.getTitulo());
		values.put("uri", feed.getUri());
		values.put("data_cadastro", new Date().getTime());
		//values.put("rss", feed.getRss()); essa informação é do usuário
		String[] args = {idFeed+""};
		return database.update(TABLE, values, "idFeed=?", args);
	}

	public int atualizaDataPublicacao(Feed feed){
		ContentValues values = new ContentValues();
		values.putNull("data_publicacao");
		String[] args = {feed.getIdFeed()+""};
		Log.i("OUTPUT-TEST", feed.toString());
		return database.update(TABLE, values, "idFeed=?", args);
	}
	
	public int atualizaAcesso(Feed feed, int acesso){
		ContentValues values = new ContentValues();
		values.put("acesso", acesso);
		String[] args = {feed.getIdFeed()+""};
		return database.update(TABLE, values, "idFeed=?", args);
	}
	
	public List<Feed> listarTudo(){
		List<Feed> feeds = new ArrayList<Feed>();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select * from "+TABLE+" order by idFeed desc ");
			Cursor cursor = database.rawQuery(sql.toString(), null);
			while (cursor.moveToNext()) {
				
				Feed feed = new Feed.Builder()
				.idFeed(cursor.getLong(cursor.getColumnIndex("idFeed")))
				.autor(cursor.getString(cursor.getColumnIndex("autor")))
				.direitoAutoral(cursor.getString(cursor.getColumnIndex("direitoAutoral")))
				.descricao(cursor.getString(cursor.getColumnIndex("descricao")))
				.codificacao(cursor.getString(cursor.getColumnIndex("codificacao")))
				.tipoFeed(cursor.getString(cursor.getColumnIndex("tipoFeed")))
				.idioma(cursor.getString(cursor.getColumnIndex("idioma")))
				.link(cursor.getString(cursor.getColumnIndex("link")))
				.data_publicacao(new Date(cursor.getLong(cursor.getColumnIndex("data_publicacao"))))
				.titulo(cursor.getString(cursor.getColumnIndex("titulo")))
				.uri(cursor.getString(cursor.getColumnIndex("uri")))
				.data_cadastro(new Date(cursor.getLong(cursor.getColumnIndex("data_cadastro"))))
				.data_sincronizacao(new Date(cursor.getLong(cursor.getColumnIndex("data_sincronizacao"))))
				.rss(cursor.getString(cursor.getColumnIndex("rss")))
				.build();
				
				feeds.add(feed);
			}
			cursor.close();
		} catch (Exception e) {
			Log.i("DAOs", e.getLocalizedMessage(),e);
		}
		return feeds;
	}
	
	public void remover(Feed feed){
		String[] args = {feed.getIdFeed()+""};
		database.delete(TABLE, "idFeed=?", args);
	}
}
