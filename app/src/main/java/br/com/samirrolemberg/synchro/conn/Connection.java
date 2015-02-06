package br.com.samirrolemberg.synchro.conn;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Connection extends SQLiteOpenHelper{

	protected static final int VERSAO = 1;
	protected static final String DATABASE = "db_synchro";
	
	public Connection(Context context) {
		super(context, DATABASE, null, VERSAO);
		DatabaseManager.initializeInstance(this);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i("DAOs", "onCreate");
		db.execSQL(table_001());
		db.execSQL(table_002());
		db.execSQL(table_003());
		db.execSQL(table_004());
		db.execSQL(table_005());
		db.execSQL(table_006());
		db.execSQL(table_007());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i("DAOs", "onUpgrade");
	}
	
	public String table_001(){
		StringBuffer ddl = new StringBuffer();
		ddl.append("create table feed (");
			ddl.append("idFeed INTEGER PRIMARY KEY AUTOINCREMENT,       ");
			ddl.append("autor VARCHAR(255) NULL,       ");
			ddl.append("direitoAutoral VARCHAR(255) NULL,  "     );
			ddl.append("descricao TEXT NULL,      " );
			ddl.append("codificacao VARCHAR(255) NULL,   "    );
			ddl.append("tipoFeed VARCHAR(255) NULL,     "  );
			ddl.append("idioma VARCHAR(255) NULL,      " );
			ddl.append("link VARCHAR(255) NULL,       ");
			ddl.append("data_publicacao DATETIME NULL, " );     
			ddl.append("titulo VARCHAR(255) NULL,     "  );
			ddl.append("uri VARCHAR(255) NULL,       ");
			ddl.append("data_cadastro DATETIME NULL,       ");
			ddl.append("data_sincronizacao DATETIME NULL,");
			ddl.append("rss VARCHAR(255) NULL,       ");
			ddl.append("acesso TINYINT(1) DEFAULT 0 ");
			ddl.append(");");
			return ddl.toString();
	}
	   //acesso TINYINT(1) DEFAULT 0
	public String table_002(){
		StringBuffer ddl = new StringBuffer();			
		ddl.append("create table imagem (");
			ddl.append("idImagem INTEGER PRIMARY KEY AUTOINCREMENT,  ");
			ddl.append("descricao VARCHAR(255) NULL,       ");
			ddl.append("link VARCHAR(255) NULL,       ");
			ddl.append("titulo VARCHAR(255) NULL,       ");
			ddl.append("url VARCHAR(255) NULL,");
			ddl.append("idFeed INTEGER NULL,");
			ddl.append("acesso TINYINT(1) DEFAULT 0 ");
			ddl.append(");");
			return ddl.toString();
	}
	public String table_003(){
		StringBuffer ddl = new StringBuffer();			
		ddl.append("create table post (");
			ddl.append("idPost INTEGER PRIMARY KEY AUTOINCREMENT,");
			ddl.append("autor VARCHAR(255) NULL,       ");
			ddl.append("link VARCHAR(255) NULL,       ");
			ddl.append("data_publicacao DATETIME NULL,       ");
			ddl.append("titulo VARCHAR(255) NULL,       ");
			ddl.append("data_atualizacao DATETIME NULL,       ");
			ddl.append("link_URI VARCHAR(255) NULL,       ");
			ddl.append("idFeed INTEGER NULL,");
			ddl.append("acesso TINYINT(1) DEFAULT 0 ");
			ddl.append(");");
			return ddl.toString();
	}
	public String table_004(){
		StringBuffer ddl = new StringBuffer();			
		ddl.append("create table categoria (");
			ddl.append("idCategoria INTEGER PRIMARY KEY AUTOINCREMENT,       ");
			ddl.append("nome VARCHAR(255) NULL,       ");
			ddl.append("url VARCHAR(255) NULL,       ");
			ddl.append("idFeed INTEGER NULL,       ");
			ddl.append("idPost INTEGER NULL,");
			ddl.append("acesso TINYINT(1) DEFAULT 0 ");
			ddl.append(");");
			return ddl.toString();
	}
	public String table_005(){
		StringBuffer ddl = new StringBuffer();			
		ddl.append("create table descricao (");
			ddl.append("idDescricao INTEGER PRIMARY KEY AUTOINCREMENT,       ");
			ddl.append("modo VARCHAR(255) NULL,       ");
			ddl.append("tipo VARCHAR(255) NULL,       ");
			ddl.append("valor TEXT NULL,       ");
			ddl.append("idPost INTEGER NULL,");
			ddl.append("acesso TINYINT(1) DEFAULT 0 ");
			ddl.append(");");
			return ddl.toString();
	}
	public String table_006(){
		StringBuffer ddl = new StringBuffer();			
		ddl.append("create table conteudo (");
			ddl.append("idConteudo INTEGER PRIMARY KEY AUTOINCREMENT,       ");
			ddl.append("modo VARCHAR(255) NULL,");
			ddl.append("tipo VARCHAR(255) NULL,");
			ddl.append("valor TEXT NULL,         ");
			ddl.append("idPost INTEGER NULL,");
			ddl.append("acesso TINYINT(1) DEFAULT 0 ");
			ddl.append(");");		
			return ddl.toString();
	}
	public String table_007(){
		StringBuffer ddl = new StringBuffer();			
		ddl.append("create table anexo (");
			ddl.append("idAnexo INTEGER PRIMARY KEY AUTOINCREMENT,       ");
			ddl.append("tamanho BIGINT NULL,       ");
			ddl.append("tipo VARCHAR(255) NULL,       ");
			ddl.append("url VARCHAR(255) NULL,");
			ddl.append("idPost INTEGER,");
			ddl.append("acesso TINYINT(1) DEFAULT 0 ");
			ddl.append(");");
			
		return ddl.toString();
	}

}
