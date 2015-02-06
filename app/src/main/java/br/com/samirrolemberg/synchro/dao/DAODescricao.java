package br.com.samirrolemberg.synchro.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.com.samirrolemberg.synchro.conn.DatabaseManager;
import br.com.samirrolemberg.synchro.model.Descricao;
import br.com.samirrolemberg.synchro.model.Post;

public class DAODescricao extends DAO {

	public final static String TABLE = "descricao";
	private SQLiteDatabase database = null;

	public DAODescricao(Context context) {
		super(context);
		database = DatabaseManager.getInstance().openDatabase();
	}
	
	public long inserir(Descricao descricao, long idPost){
		ContentValues values = new ContentValues();
		values.put("modo", descricao.getModo());
		values.put("tipo", descricao.getTipo());
		values.put("valor", descricao.getValor());
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
			sql.append("select count(idDescricao) total from "+TABLE+" where idPost = ?");
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
			sql.append("select idDescricao from "+TABLE+" where idPost = ? limit 1");
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
		
	public List<Descricao> listarTudo(Post post){
		List<Descricao> descricoes = new ArrayList<Descricao>();
		try {
			String[] args = {post.getIdPost()+""};
			StringBuffer sql = new StringBuffer();
			sql.append("select * from "+TABLE+" where idPost = ?");
			Cursor cursor = database.rawQuery(sql.toString(), args);
			while (cursor.moveToNext()) {
				Descricao descricao = new Descricao.Builder()
				.idDescricao(cursor.getLong(cursor.getColumnIndex("idDescricao")))
				.modo(cursor.getString(cursor.getColumnIndex("modo")))
				.post(post)
				.tipo(cursor.getString(cursor.getColumnIndex("tipo")))
				.valor(cursor.getString(cursor.getColumnIndex("valor")))
				.build();
				descricoes.add(descricao);
			}
			cursor.close();
		} catch (Exception e) {
			Log.i("DAOs", e.getLocalizedMessage(),e);
		}
		return descricoes;
	}

	public Descricao buscar(Post post){
		Descricao descricao = null;
		try {
			String[] args = {post.getIdPost()+""};
			StringBuffer sql = new StringBuffer();
			sql.append("select * from "+TABLE+" where idPost = ?");
			Cursor cursor = database.rawQuery(sql.toString(), args);
			if (cursor.moveToNext()) {
				descricao = new Descricao.Builder()
				.idDescricao(cursor.getLong(cursor.getColumnIndex("idDescricao")))
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
		return descricao;
	}

	public long existe(Descricao descricao, long idPost) {
		//select idAnexo id from anexo where idPost = ? and url = ?
		long retorno = 0;
		try {
			String[] args = {idPost+"",descricao.getValor()};
			StringBuffer sql = new StringBuffer();
			sql.append("select idDescricao id from "+TABLE+" where idPost = ? and valor = ?");
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

	public int atualiza(Descricao descricao, long idDescricao) {
		ContentValues values = new ContentValues();
		values.put("modo", descricao.getModo());
		values.put("tipo", descricao.getTipo());
		values.put("valor", descricao.getValor());
		String[] args = {idDescricao+""};

		return database.update(TABLE, values, "idDescricao = ?", args);
	}

	
	public int remover(Post post){
		String[] args = {post.getIdPost()+""};
		return database.delete(TABLE, "idPost=?", args);
	}

}
