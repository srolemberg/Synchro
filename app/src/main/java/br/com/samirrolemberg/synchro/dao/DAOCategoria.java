package br.com.samirrolemberg.synchro.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.com.samirrolemberg.synchro.conn.DatabaseManager;
import br.com.samirrolemberg.synchro.model.Categoria;
import br.com.samirrolemberg.synchro.model.Feed;
import br.com.samirrolemberg.synchro.model.Post;

public class DAOCategoria extends DAO{

	public final static String TABLE = "categoria";
	private SQLiteDatabase database = null;

	public DAOCategoria(Context context) {
		super(context);
		database = DatabaseManager.getInstance().openDatabase();
	}
	
	public long inserir(Categoria categoria, Post idPost){
		ContentValues values = new ContentValues();
		values.put("nome", categoria.getNome());
		values.put("url", categoria.getUrl());
		values.put("idPost", idPost.getIdPost());
		long id = database.insert(TABLE, null, values);
		
		return id;
		
	}
	public long inserir(Categoria categoria, Feed idFeed){
		ContentValues values = new ContentValues();
		values.put("nome", categoria.getNome());
		values.put("url", categoria.getUrl());
		values.put("idFeed", idFeed.getIdFeed());
		long id = database.insert(TABLE, null, values);
		
		return id;
		
	}
	public int atualiza(Categoria categoria, long idCategoria){
		ContentValues values = new ContentValues();
		values.put("nome", categoria.getNome());
		values.put("url", categoria.getUrl());
		String[] args = {idCategoria+""};
		return database.update(TABLE, values, "idCategoria=?", args);
	}

	public int atualizaAcesso(Feed feed, int acesso){
		ContentValues values = new ContentValues();
		values.put("acesso", acesso);
		String[] args = {feed.getIdFeed()+""};

		return database.update(TABLE, values, "idFeed = ?", args);
	}
	public int atualizaAcesso(Post post, int acesso){
		ContentValues values = new ContentValues();
		values.put("acesso", acesso);
		String[] args = {post.getIdPost()+""};

		return database.update(TABLE, values, "idPost = ?", args);
	}

	public List<Categoria> listarTudo(Post post){
		List<Categoria> categorias = new ArrayList<Categoria>();
		try {
			String[] args = {post.getIdPost()+""};
			StringBuffer sql = new StringBuffer();
			sql.append("select * from "+TABLE+" where idPost = ?");
			Cursor cursor = database.rawQuery(sql.toString(), args);
			while (cursor.moveToNext()) {
				Categoria categoria = new Categoria.Builder()
				.idCategoria(cursor.getLong(cursor.getColumnIndex("idCategoria")))
				.post(post)
				.nome(cursor.getString(cursor.getColumnIndex("nome")))
				.url(cursor.getString(cursor.getColumnIndex("url")))
				.build();
				categorias.add(categoria);
			}
			cursor.close();
		} catch (Exception e) {
			Log.i("DAOs", e.getLocalizedMessage(),e);
		}
		return categorias;
	}
	public List<Categoria> listarTudo(Feed feed){
		List<Categoria> categorias = new ArrayList<Categoria>();
		try {
			String[] args = {feed.getIdFeed()+""};
			StringBuffer sql = new StringBuffer();
			sql.append("select * from "+TABLE+" where idFeed = ?");
			Cursor cursor = database.rawQuery(sql.toString(), args);
			while (cursor.moveToNext()) {
				Categoria categoria = new Categoria.Builder()
				.idCategoria(cursor.getLong(cursor.getColumnIndex("idCategoria")))
				.feed(feed)
				.nome(cursor.getString(cursor.getColumnIndex("nome")))
				.url(cursor.getString(cursor.getColumnIndex("url")))
				.build();
				categorias.add(categoria);
			}
			cursor.close();
		} catch (Exception e) {
			Log.i("DAOs", e.getLocalizedMessage(),e);
		}
		return categorias;
	}

	public Categoria buscar(Post post){
		Categoria categoria = null;
		try {
			String[] args = {post.getIdPost()+""};
			StringBuffer sql = new StringBuffer();
			sql.append("select * from "+TABLE+" where idPost = ?");
			Cursor cursor = database.rawQuery(sql.toString(), args);
			if (cursor.moveToNext()) {
				categoria = new Categoria.Builder()
				.idCategoria(cursor.getLong(cursor.getColumnIndex("idCategoria")))
				.post(post)
				.nome(cursor.getString(cursor.getColumnIndex("nome")))
				.url(cursor.getString(cursor.getColumnIndex("url")))
				.build();
			}
			cursor.close();
		} catch (Exception e) {
			Log.i("DAOs", e.getLocalizedMessage(),e);
		}
		return categoria;
	}
	public Categoria buscar(Feed feed){
		Categoria categoria = null;
		try {
			String[] args = {feed.getIdFeed()+""};
			StringBuffer sql = new StringBuffer();
			sql.append("select * from "+TABLE+" where idFeed = ?");
			Cursor cursor = database.rawQuery(sql.toString(), args);
			if (cursor.moveToNext()) {
				categoria = new Categoria.Builder()
				.idCategoria(cursor.getLong(cursor.getColumnIndex("idCategoria")))
				.feed(feed)
				.nome(cursor.getString(cursor.getColumnIndex("nome")))
				.url(cursor.getString(cursor.getColumnIndex("url")))
				.build();
			}
			cursor.close();
		} catch (Exception e) {
			Log.i("DAOs", e.getLocalizedMessage(),e);
		}
		return categoria;
	}
	public long size(Feed feed){
		long resultado = 0;
		try {
			String[] args = {feed.getIdFeed()+""};
			StringBuffer sql = new StringBuffer();
			sql.append("select count(idCategoria) total from "+TABLE+" where idFeed = ?");
			Cursor cursor = database.rawQuery(sql.toString(), args);
			if (cursor.moveToNext()) {
				resultado = cursor.getLong(cursor.getColumnIndex("total"));
			}
			cursor.close();
		} catch (Exception e) {
			Log.i("DAOs", e.getLocalizedMessage(),e);
		}
		
		return resultado;
	}
	public long size(Post post){
		long resultado = 0;
		try {
			String[] args = {post.getIdPost()+""};
			StringBuffer sql = new StringBuffer();
			sql.append("select count(idCategoria) total from "+TABLE+" where idPost = ?");
			Cursor cursor = database.rawQuery(sql.toString(), args);
			if (cursor.moveToNext()) {
				resultado = cursor.getLong(cursor.getColumnIndex("total"));
			}
			cursor.close();
		} catch (Exception e) {
			Log.i("DAOs", e.getLocalizedMessage(),e);
		}
		
		return resultado;
	}

	public long existe(Categoria categoria, Post post) {
		//select idAnexo id from anexo where idPost = ? and url = ?
		long retorno = 0;
		try {
			String[] args = {post.getIdPost()+"",categoria.getNome()};
			StringBuffer sql = new StringBuffer();
			sql.append("select idCategoria id from "+TABLE+" where idPost = ? and nome = ?");
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
	public long existe(Categoria categoria, Feed feed) {
		//select idAnexo id from anexo where idPost = ? and url = ?
		long retorno = 0;
		try {
			String[] args = {feed.getIdFeed()+"",categoria.getNome()};
			StringBuffer sql = new StringBuffer();
			sql.append("select idCategoria id from "+TABLE+" where idFeed = ? and nome = ?");
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

	public int remover(Post post){
		String[] args = {post.getIdPost()+""};
		return database.delete(TABLE, "idPost=?", args);
	}
	public int remover(Feed feed){
		String[] args = {feed.getIdFeed()+""};
		return database.delete(TABLE, "idFeed=?", args);
	}

}
