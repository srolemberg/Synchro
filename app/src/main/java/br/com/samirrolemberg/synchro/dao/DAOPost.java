package br.com.samirrolemberg.synchro.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.com.samirrolemberg.synchro.conn.DatabaseManager;
import br.com.samirrolemberg.synchro.model.Feed;
import br.com.samirrolemberg.synchro.model.Post;

public class DAOPost extends DAO{

	public final static String TABLE = "post";
	private SQLiteDatabase database = null;

	public DAOPost(Context context) {
		super(context);
		database = DatabaseManager.getInstance().openDatabase();
	}
	
	public long inserir(Post post, long idFeed){
		ContentValues values = new ContentValues();
		values.put("autor", post.getAutor());
		values.put("link", post.getLink());
		values.put("data_publicacao", post.getData_publicacao()==null?null:post.getData_publicacao().getTime());
		values.put("titulo", post.getTitulo());
		values.put("data_atualizacao", post.getData_atualizacao()==null?null:post.getData_atualizacao().getTime());
		values.put("link_URI", post.getLink_URI());
		values.put("idFeed", idFeed);
		long id = database.insert(TABLE, null, values);
		
		return id;
		
	}
	public int atualizaAcesso(Post post, int acesso){
		ContentValues values = new ContentValues();
		values.put("acesso", acesso);
		String[] args = {post.getIdPost()+""};

		return database.update(TABLE, values, "idPost = ?", args);
	}

	public List<Post> listarTudo(Feed feed){
		List<Post> posts = new ArrayList<Post>();
		try {
			String[] args = {feed.getIdFeed()+""};
			StringBuffer sql = new StringBuffer();
			sql.append("select * from "+TABLE+" where idFeed = ? order by data_publicacao desc");
			Cursor cursor = database.rawQuery(sql.toString(), args);
			while (cursor.moveToNext()) {
				Post post = new Post.Builder()
				.idPost(cursor.getLong(cursor.getColumnIndex("idPost")))
				.autor(cursor.getString(cursor.getColumnIndex("autor")))
				.link(cursor.getString(cursor.getColumnIndex("link")))
				.data_publicacao(new Date(cursor.getLong(cursor.getColumnIndex("data_publicacao"))))
				.titulo(cursor.getString(cursor.getColumnIndex("titulo")))
				.data_atualizacao(new Date(cursor.getLong(cursor.getColumnIndex("data_atualizacao"))))
				.link_URI(cursor.getString(cursor.getColumnIndex("link_URI")))
				.feed(feed)
				.build();
				posts.add(post);
			}
			cursor.close();
		} catch (Exception e) {
			Log.i("DAOs", e.getLocalizedMessage(),e);
		}
		return posts;
	}

	public long size(Feed feed){
		long resultado = 0;
		try {
			String[] args = {feed.getIdFeed()+""};
			StringBuffer sql = new StringBuffer();
			sql.append("select count(idPost) total from "+TABLE+" where idFeed = ?");
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

	public int remover(Post post){
		String[] args = {post.getIdPost()+""};
		return database.delete(TABLE, "idPost=?", args);
	}

	public long existe(Post post) {
		long resultado = 0;		
		try {
			String where = "";
			List<String> lista = new LinkedList<String>();
			
			if (post.getLink_URI()!=null) {
				if (where.trim().isEmpty()) {					
					where += " where link_URI = ? ";
				}				
				lista.add(post.getLink_URI());
			}else
			if (post.getLink()!=null) {
				if (where.trim().isEmpty()) {					
					where += " where link = ? ";
				}
				lista.add(post.getLink());
			}else
			if (post.getTitulo()!=null) {
				if (where.trim().isEmpty()) {					
					where += " where titulo = ? ";
				}
				lista.add(post.getTitulo());
			}
			if (!where.trim().isEmpty()) {
				//String[] args = (String[])lista.toArray();
				String[] args = new String[lista.size()];
				args = lista.toArray(args);
				
				StringBuffer sql = new StringBuffer();
				sql.append("select idPost id from "+TABLE+where);
				Cursor cursor = database.rawQuery(sql.toString(), args);
				if (cursor.moveToNext()) {
					resultado = cursor.getLong(cursor.getColumnIndex("id"));
				}
				cursor.close();
			}
		} catch (Exception e) {
			Log.i("DAOs", e.getLocalizedMessage(),e);
		}

		return resultado;
	}

	public int atualiza(Post post, long idPost) {
		ContentValues values = new ContentValues();
		values.put("autor", post.getAutor());
		values.put("link", post.getLink());
		values.put("data_publicacao", post.getData_publicacao()==null?null:post.getData_publicacao().getTime());
		values.put("titulo", post.getTitulo());
		values.put("data_atualizacao", post.getData_atualizacao()==null?null:post.getData_atualizacao().getTime());
		values.put("link_URI", post.getLink_URI());
		String[] args = {idPost+""};

		return database.update(TABLE, values, "idPost = ?", args);
	}

}
