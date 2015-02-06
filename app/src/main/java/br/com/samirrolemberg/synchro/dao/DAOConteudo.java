package br.com.samirrolemberg.synchro.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.com.samirrolemberg.synchro.conn.DatabaseManager;
import br.com.samirrolemberg.synchro.model.Conteudo;
import br.com.samirrolemberg.synchro.model.Post;

public class DAOConteudo extends DAO {

	public final static String TABLE = "conteudo";
	private SQLiteDatabase database = null;

	public DAOConteudo(Context context) {
		super(context);
		database = DatabaseManager.getInstance().openDatabase();
	}
	
	public long inserir(Conteudo conteudo, long idPost){
		ContentValues values = new ContentValues();
		values.put("modo", conteudo.getModo());
		values.put("tipo", conteudo.getTipo());
		values.put("valor", conteudo.getValor());
		values.put("idPost", idPost);
		long id = database.insert(TABLE, null, values);
		
		return id;
		
	}
	public int atualizaAcesso(Post post, int acesso){
		ContentValues values = new ContentValues();
		values.put("acesso", acesso);
		String[] args = {post.getIdPost()+""};

		return database.update(TABLE, values, "idPost = ?", args);
	}

	public long size(Post post){
		long resultado = 0;
		try {
			String[] args = {post.getIdPost()+""};
			StringBuffer sql = new StringBuffer();
			sql.append("select count(idConteudo) total from "+TABLE+" where idPost = ?");
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

	public boolean isExist(Post post){
		boolean resultado = false;
		try {
			String[] args = {post.getIdPost()+""};
			StringBuffer sql = new StringBuffer();
			sql.append("select idConteudo from "+TABLE+" where idPost = ? limit 1");
			Cursor cursor = database.rawQuery(sql.toString(), args);
			if (cursor.moveToNext()) {
				resultado = true;
			}
			cursor.close();
		} catch (Exception e) {
			Log.i("DAOs", e.getLocalizedMessage(),e);
		}
		
		return resultado;
	}

	public List<Conteudo> listarTudo(Post post){
		List<Conteudo> conteudos = new ArrayList<Conteudo>();
		try {
			String[] args = {post.getIdPost()+""};
			StringBuffer sql = new StringBuffer();
			sql.append("select * from "+TABLE+" where idPost = ?");
			Cursor cursor = database.rawQuery(sql.toString(), args);
			while (cursor.moveToNext()) {
				Conteudo conteudo = new Conteudo.Builder()
				.idConteudo(cursor.getLong(cursor.getColumnIndex("idConteudo")))
				.modo(cursor.getString(cursor.getColumnIndex("modo")))
				.post(post)
				.tipo(cursor.getString(cursor.getColumnIndex("tipo")))
				.valor(cursor.getString(cursor.getColumnIndex("valor")))
				.build();
				conteudos.add(conteudo);
			}
			cursor.close();
		} catch (Exception e) {
			Log.i("DAOs", e.getLocalizedMessage(),e);
		}
		return conteudos;
	}

	public Conteudo buscar(Post post){
		Conteudo conteudo = null;
		try {
			String[] args = {post.getIdPost()+""};
			StringBuffer sql = new StringBuffer();
			sql.append("select * from "+TABLE+" where idPost = ?");
			Cursor cursor = database.rawQuery(sql.toString(), args);
			if (cursor.moveToNext()) {
				conteudo = new Conteudo.Builder()
				.idConteudo(cursor.getLong(cursor.getColumnIndex("idConteudo")))
				.modo(cursor.getString(cursor.getColumnIndex("modo")))
				.post(post)
				.tipo(cursor.getString(cursor.getColumnIndex("tipo")))
				.valor(cursor.getString(cursor.getColumnIndex("valor")))
				.build();
			}
			cursor.close();
		} catch (Exception e) {
			Log.i("DAOs", e.getLocalizedMessage(),e);
		}
		return conteudo;
	}

	public int remover(Post post){
		String[] args = {post.getIdPost()+""};
		return database.delete(TABLE, "idPost=?", args);
	}

	public long existe(Conteudo conteudo, Post post) {
		//select idAnexo id from anexo where idPost = ? and url = ?
		long retorno = 0;
		try {
			String[] args = {post.getIdPost()+"",conteudo.getValor()};
			StringBuffer sql = new StringBuffer();
			sql.append("select idConteudo id from "+TABLE+" where idPost = ? and valor = ?");
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

	public int atualiza(Conteudo conteudo, long idConteudo) {
		ContentValues values = new ContentValues();
		values.put("modo", conteudo.getModo());
		values.put("tipo", conteudo.getTipo());
		values.put("valor", conteudo.getValor());
		String[] args = {idConteudo+""};

		return database.update(TABLE, values, "idConteudo = ?", args);
	}

}
