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
	private static final String[] COLUNAS = {"pub_id","festa_nome","usuario_id","data_hora","consumo_limite","nota_festa"};
	
	public FestaDao(Context context) {
		super(context, TABELA, null, VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {

		String sql ="CREATE TABLE IF NOT EXISTS " + TABELA +
		        "( id INTEGER PRIMARY KEY autoincrement," +
					"festa_nome TEXT,"+
					" data_hora TEXT NOT NULL," +
					" consumo_limite TEXT," +
					" nota_festa TEXT,"+
					" pub_id INTEGER ," +
					" usuario_id INTEGER);";
		
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
		valores.put("pub_id", festa.getPub_id());
		valores.put("festa_nome", festa.getFestaNome());
		valores.put("usuario_id", festa.getUsuario_id());
    	valores.put("data_hora", festa.getData_hora());
    	valores.put("consumo_limite", festa.getConsumo_limite());
    	valores.put("nota_festa", festa.getNota_festa());
    	
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
	    	  Festa party = new Festa(0,"",0, 0, "", 0, 0);

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
	
}
