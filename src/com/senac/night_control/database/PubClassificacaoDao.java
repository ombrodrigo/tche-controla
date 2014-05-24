package com.senac.night_control.database;

import com.senac.night_control.model.PubClassificacao;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PubClassificacaoDao extends SQLiteOpenHelper {
	
	private static final String TABELA = "Festa";
	private static final int VERSION = 1;
	private static final String[] COLUNAS = {"pub_id","usuario_id", "classificação"};
	
	public PubClassificacaoDao(Context context) {
		super(context, TABELA, null, VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {

		String sql ="CREATE TABLE IF NOT EXISTS " + TABELA +
		        "( id INTEGER PRIMARY KEY autoincrement," +
					" classificacao TEXT," +
					" FOREIGN KEY (pub_id) REFERENCES Pub (id))," +
					" FOREIGN KEY (usuario_id) REFERENCES Usuario (id))";
		
		     db.execSQL(sql);
		     
		     Log.w("Ok", "Database was created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("Drop Table " + PubClassificacaoDao.TABELA);
	      this.onCreate(db);
	}
	
	public void inserir(PubClassificacao pubc) throws SQLException {

		ContentValues valores = new ContentValues();
			valores.put("pub_id", pubc.getPub_id());
			valores.put("usuario_id", pubc.getUsuario_id());
	    	valores.put("consumo_limite", pubc.getClassificacao());
	    	
	    	try {
	    		getWritableDatabase().insert(TABELA, null, valores);
	    	} catch (SQLException e) {
	    		e.printStackTrace();
	    	} 
	 }

}
