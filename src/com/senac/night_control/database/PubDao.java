package com.senac.night_control.database;

import java.util.ArrayList;
import java.util.List;

import com.senac.night_control.model.Bebida;
import com.senac.night_control.model.Pub;
import com.senac.night_control.model.Usuario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class PubDao extends SQLiteOpenHelper {
	
	private static final String TABELA = "Pub";
	private static final int VERSION = 1;
	private static final String[] COLUNAS = {"id","pub_nome"};
	
	public PubDao(Context context) {
		super(context, TABELA, null, VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {

		String sql ="CREATE TABLE IF NOT EXISTS " + TABELA +
		        "( id INTEGER PRIMARY KEY autoincrement," +
					" pub_nome TEXT NOT NULL)";
		
		     db.execSQL(sql);
		     
		     Log.w("Ok", "Database"+TABELA+" was created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("Drop Table " + PubDao.TABELA);
	      this.onCreate(db);
	}
	
	public void inserir(Pub pub) throws SQLException {

		ContentValues valores = new ContentValues();
	    	valores.put("pub_nome", pub.getPub_nome());
	    	

	    	try {
	    		getWritableDatabase().insert(TABELA, null, valores);
	    	} catch (SQLException e) {
	    		e.printStackTrace();
	    	} 
	 }
	
	
	public List<Pub> getLista(){

	      Cursor c = getWritableDatabase().query(TABELA, COLUNAS, null, null, null, null, null);
	      List<Pub> lista = new ArrayList<Pub>();
	      
	      while(c.moveToNext()){
	    	  Pub pub =new Pub(null);

	    	  pub.setPub_nome(c.getString(1));
	    	
	    	 /* usuario.setMaiorDeIdade(true);*/
	    	  
	 
	          lista .add(pub);
	      }
	  c.close();
	 
	 return lista;
	 
	 }

	
	public Boolean verifyUniquePubInBd(String pubName) {
		Cursor c = null;
		boolean achou = false;
		try {
			c = getReadableDatabase().query(TABELA, COLUNAS, null, null, null,
					null, null);

			while (c.moveToNext()) {

				if (c.getString(1).equals(pubName)) {
					achou = true;
					break;
				}

			}
		} finally {
			if (c != null)
				c.close();
		}

		return achou;

	}

	public Pub verifyEntityInDatabase(String pubName)
	{
		
		Cursor c = null;
		boolean achou = false;
		
		Pub pubEntity=new Pub(pubName);
		
		try {
			c = getReadableDatabase().query(TABELA, COLUNAS, null, null, null,
					null, null);
			
						
			while (c.moveToNext()) {

				if (c.getString(1).equals(pubName)) {
					
					
					pubEntity.setId(Integer.parseInt(c.getString(0)));					
					
					return pubEntity;
				}

			}
		} finally {
			if (c != null)
				c.close();
		}

		return pubEntity;
		
	}
	
}
