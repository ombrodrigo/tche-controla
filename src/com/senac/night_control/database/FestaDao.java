package com.senac.night_control.database;

import java.util.ArrayList;
import java.util.List;

import com.senac.night_control.model.Festa;
import com.senac.night_control.model.Pub;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FestaDao extends SQLiteOpenHelper {
	
	private static final String TABELA = "Festa";
	private static final int VERSION = 1;
	private static final String[] COLUNAS = {"id","festa_nome"};
	
	public FestaDao(Context context) {
		super(context, TABELA, null, VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {

		String sql ="CREATE TABLE IF NOT EXISTS " + TABELA +
		        "( id INTEGER PRIMARY KEY autoincrement," +
					"festa_nome TEXT);";
/*					" data_hora TEXT NOT NULL," +*/
/*					" consumo_limite TEXT," +*/
/*					" nota_festa TEXT,"+" 
 * 					usuario_id INTEGER);";*/
					
		
		     db.execSQL(sql);
		     
		     Log.w("Ok", "Database was created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("Drop Table " + FestaDao.TABELA);
	      this.onCreate(db);
	}
	
	public void inserir(Festa festa) throws SQLException {

		if (verifyUniqueSpendInBd(festa.getFestaNome()))
			return;
		
		ContentValues valores = new ContentValues();
		valores.put("festa_nome", festa.getFestaNome());
		

    	
    	try {
    		getWritableDatabase().insert(TABELA, null, valores);
    	} catch (SQLException e) {
    		e.printStackTrace();
    	} 
	 }

	public List<Festa> getLista(){

	      Cursor c = getWritableDatabase().query(TABELA, COLUNAS, null, null, null, null, null);
	      List<Festa> lista = new ArrayList<Festa>();
	      
	      while(c.moveToNext()){
	    	  Festa party = new Festa("");

	    	  party.setFestaNome(c.getString(1));
	    	
	    	 /* usuario.setMaiorDeIdade(true);*/
	    	  
	 
	          lista .add(party);
	      }
		  c.close();
		  
		  
		 return lista;
	 
	 }

	public boolean verifyUniqueSpendInBd(String festa) {
		Cursor c = getReadableDatabase().query(TABELA, COLUNAS, null, null,
				null, null, null);
		boolean achou = false;

		while (c.moveToNext()) {
			if (c.getString(1).equals(festa)) {
				achou = true;
			}
		}

		c.close();
		return achou;
	}

	
	public Festa verifyEntityInDatabase(String festaName)
	{
		
		Cursor c = null;
		boolean achou = false;
		
		Festa festaEntity=new Festa(festaName);
		
		try {
			c = getReadableDatabase().query(TABELA, COLUNAS, null, null, null,
					null, null);
			
						
			while (c.moveToNext()) {

				if (c.getString(1).equals(festaName)) {
					
					
					festaEntity.setId(Integer.parseInt(c.getString(0)));					
					
					return festaEntity;
				}

			}
		} finally {
			if (c != null)
				c.close();
		}

		return festaEntity;
		
	}
	
	
}
