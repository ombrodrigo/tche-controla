package com.senac.night_control.database;

import java.util.ArrayList;
import java.util.List;

import com.senac.night_control.model.Usuario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UsuarioDao extends SQLiteOpenHelper {

	private static final String TABELA = "Usuario";
	 private static final int VERSION = 1;
	private static final String[] COLUNAS = {"nome","email","login","senha","maior"};
	
	
	public UsuarioDao(Context context) {
		super(context, TABELA, null, VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		String sql = "CREATE TABLE IF NOT EXISTS " + TABELA +
		        "( id INTEGER PRIMARY KEY," +
		        " nome TEXT UNIQUE NOT NULL," +
		        " email TEXT," +
		        " login TEXT," +
		        " senha TEXT, " +
		        " maior Boolean" +
		        ");";
		
		     db.execSQL(sql);
		     

		     Log.w("Ok", "Database was created");
		    
		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("DROP TABLE IF EXISTS " + UsuarioDao.TABELA);
	      this.onCreate(db);
		
	}
	
	public void inserir(Usuario usuario){
	      
		ContentValues valores = new ContentValues();
	      valores.put("nome", usuario.getNome());
	      valores.put("email", usuario.getEmail());
	      valores.put("login", usuario.getLogin());
	      valores.put("senha", usuario.getSenha());
	      valores.put("maior", usuario.getMaiorDeIdade());
	 
	      getWritableDatabase().insert(TABELA, null, valores);
	 }
	
	
	public List<Usuario> getLista(){
	      Cursor c = getWritableDatabase().query(TABELA, COLUNAS, null, null, null, null, null);
	      List<Usuario> lista = new ArrayList<Usuario>();
	      
	      while(c.moveToNext()){
	    	  Usuario usuario = new Usuario(null, null, null, null, null);
	    	  usuario.setId(c.getInt(0));
	    	  usuario.setNome(c.getString(1));
	    	  usuario.setEmail(c.getString(2));
	    	  usuario.setLogin(c.getString(3));
	    	  usuario.setSenha(c.getString(4));
	    	  usuario.setMaiorDeIdade(true);
	 
	          lista .add(usuario);
	      }
	  c.close();
	 
	 return lista;
	 
	 }

}
