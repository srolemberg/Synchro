package br.com.samirrolemberg.synchro.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.com.samirrolemberg.synchro.conn.DatabaseManager;
import br.com.samirrolemberg.synchro.model.Feed;
import br.com.samirrolemberg.synchro.model.Imagem;

public class DAOImagem extends DAO{

	public final static String TABLE = "imagem";
	private SQLiteDatabase database = null;

	public DAOImagem(Context context) {
		super(context);
		database = DatabaseManager.getInstance().openDatabase();
	}
	
	public long inserir(Imagem imagem, long idFeed){
		ContentValues values = new ContentValues();
		values.put("descricao", imagem.getDescricao());
		values.put("link", imagem.getLink());
		values.put("titulo", imagem.getTitulo());
		values.put("url", imagem.getUrl());
		values.put("idFeed", idFeed);
		long id = database.insert(TABLE, null, values);
		
		return id;
		
	}
	public int atualiza(Imagem imagem, long idFeed){
		ContentValues values = new ContentValues();
		values.put("descricao", imagem.getDescricao());
		values.put("link", imagem.getLink());
		values.put("titulo", imagem.getTitulo());
		values.put("url", imagem.getUrl());
		String[] args = {idFeed+""};
		return database.update(TABLE, values, "idFeed=?", args);
	}

	public int atualizaAcesso(Feed feed, int acesso){
		ContentValues values = new ContentValues();
		values.put("acesso", acesso);
		String[] args = {feed.getIdFeed()+""};

		return database.update(TABLE, values, "idFeed = ?", args);
	}

	public List<Imagem> listarTudo(Feed feed){
		List<Imagem> imagens = new ArrayList<Imagem>();
		try {
			String[] args = {feed.getIdFeed()+""};
			StringBuffer sql = new StringBuffer();
			sql.append("select * from "+TABLE+" where idFeed = ?");
			Cursor cursor = database.rawQuery(sql.toString(), args);
			while (cursor.moveToNext()) {
				Imagem imagem = new Imagem.Builder()
				.idImagem(cursor.getLong(cursor.getColumnIndex("idImagem")))
				.feed(feed)
				.descricao(cursor.getString(cursor.getColumnIndex("descricao")))
				.link(cursor.getString(cursor.getColumnIndex("link")))
				.titulo(cursor.getString(cursor.getColumnIndex("titulo")))
				.url(cursor.getString(cursor.getColumnIndex("url")))
				.build();
				imagens.add(imagem);
			}
			cursor.close();
		} catch (Exception e) {
			Log.i("DAOs", e.getLocalizedMessage(),e);
		}
		return imagens;
	}

	public Imagem buscar(Feed feed){
		Imagem imagem = null;
		try {
			String[] args = {feed.getIdFeed()+""};
			StringBuffer sql = new StringBuffer();
			sql.append("select * from "+TABLE+" where idFeed = ?");
			Cursor cursor = database.rawQuery(sql.toString(), args);
			if (cursor.moveToNext()) {
				imagem = new Imagem.Builder()
				.idImagem(cursor.getLong(cursor.getColumnIndex("idImagem")))
				.feed(feed)
				.descricao(cursor.getString(cursor.getColumnIndex("descricao")))
				.link(cursor.getString(cursor.getColumnIndex("link")))
				.titulo(cursor.getString(cursor.getColumnIndex("titulo")))
				.url(cursor.getString(cursor.getColumnIndex("url")))
				.build();
			}
			cursor.close();
		} catch (Exception e) {
			Log.i("DAOs", e.getLocalizedMessage(),e);
		}
		return imagem;
	}

	public void remover(Feed feed){
		String[] args = {feed.getIdFeed()+""};
		database.delete(TABLE, "idFeed=?", args);
	}

	public long existe(Imagem imagem, Feed feed) {
		//select idAnexo id from anexo where idPost = ? and url = ?
		long retorno = 0;
		try {
			String[] args = {feed.getIdFeed()+"",imagem.getUrl()};//TODO VER NO BANCO SE PRECISA MUDAR DE URI PARA URL
			StringBuffer sql = new StringBuffer();
			sql.append("select idImagem id from "+TABLE+" where idFeed = ? and url = ?");
			Cursor cursor = database.rawQuery(sql.toString(), args);
			if (cursor.moveToNext()) {
				retorno = cursor.getLong(cursor.getColumnIndex("id"));
			}
			cursor.close();
		} catch (Exception e) {
			Log.i("DAOs", e.getLocalizedMessage(),e);
		}

		return retorno;
	}

}
