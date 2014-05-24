package com.senac.night_control.database;

import com.senac.night_control.model.Bebida;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class BebidaDao extends SQLiteOpenHelper {
	
	private static final String TABELA = "Bebida";
	private static final int VERSION = 1;
	private static final String[] COLUNAS = {"id","bebida_nome"};
	
	public BebidaDao(Context context) {
		super(context, TABELA, null, VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {

		String sql ="CREATE TABLE IF NOT EXISTS " + TABELA +
		        "( id INTEGER PRIMARY KEY autoincrement," +
					" nome_bebida TEXT NOT NULL)";
		
		     db.execSQL(sql);
		     
		     Log.w("Ok", "Database was created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("Drop Table " + BebidaDao.TABELA);
	      this.onCreate(db);
	}
	
	public void inserir(Bebida bebida) throws SQLException {

		ContentValues valores = new ContentValues();
	    	valores.put("nome_bebida", bebida.getBebida_nome());
	    	
	    	try {
	    		getWritableDatabase().insert(TABELA, null, valores);
	    	} catch (SQLException e) {
	    		e.printStackTrace();
	    	} 
	 }

}
