package com.senac.night_control.database;

import com.senac.night_control.model.Festa;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FestaDao extends SQLiteOpenHelper {
	
	private static final String TABELA = "Festa";
	private static final int VERSION = 1;
	private static final String[] COLUNAS = {"pub_id","usuario_id","data_hora","consumo_limite","nota_festa"};
	
	public FestaDao(Context context) {
		super(context, TABELA, null, VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {

		String sql ="CREATE TABLE IF NOT EXISTS " + TABELA +
		        "( id INTEGER PRIMARY KEY autoincrement," +
					" data_hora TEXT NOT NULL," +
					" consumo_limite TEXT," +
					" nota_festa TEXT,"+
					" FOREIGN KEY (pub_id) REFERENCES Pub (id))," +
					" FOREIGN KEY (usuario_id) REFERENCES Usuario (id))";
		
		     db.execSQL(sql);
		     
		     Log.w("Ok", "Database was created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("Drop Table " + FestaDao.TABELA);
	      this.onCreate(db);
	}
	
	public void inserir(Festa festa) throws SQLException {

		ContentValues valores = new ContentValues();
			valores.put("pub_id", festa.getPub_id());
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

}
