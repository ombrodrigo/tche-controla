package com.senac.night_control.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.senac.night_control.model.Festa;
import com.senac.night_control.model.FestaConsumo;

public class FestaConsumoDao extends SQLiteOpenHelper {
	



	private static final String TABELA = "FestaConsumo";
	private static final int VERSION = 1;
	private static final String[] COLUNAS = {"id","festa_id","pub_id","consumo_limite","valor_bebida","quantidade_consumida","user_id","data","nota_festa","isActive"};
	
	
	public FestaConsumoDao(Context context) {
		super(context, TABELA, null, VERSION);
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public void onCreate(SQLiteDatabase db) {

		String sql ="CREATE TABLE IF NOT EXISTS " + TABELA +
		        "( id INTEGER PRIMARY KEY autoincrement," +
					"festa_id INTEGER NOT NULL,"+
					"pub_id INTEGER NOT NULL,"+
					"consumo_limite NUMERIC  ," +
					"valor_bebida NUMERIC  ," +
					"quantidade_consumida," +
					"user_id INTEGER NOT NULL," +
					"data TEXT, "+
					"nota_festa INTEGER,"+
					"isActive INTEGER);";
		
		
		     db.execSQL(sql);
		     
		     Log.w("Ok", "Database was created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("Drop Table " + FestaConsumoDao.TABELA);
	      this.onCreate(db);
	}
	
	public void inserir(FestaConsumo festaConsumo) throws SQLException {

		
		ContentValues valores = new ContentValues();
		
		if(festaConsumo.getId()>0){
			valores.put("id", festaConsumo.getId());
		}
		
		
		valores.put("festa_id", festaConsumo.getFesta_id());
		valores.put("consumo_limite", festaConsumo.getConsumo_limite());
		valores.put("valor_bebida", festaConsumo.getValor_bebida());
		valores.put("quantidade_consumida", festaConsumo.getQuantidade_consumida());
		valores.put("data", festaConsumo.getData());
		valores.put("nota_festa", festaConsumo.getNota_festa());
		valores.put("pub_id", festaConsumo.getPub_id());
		valores.put("user_id", festaConsumo.getUser_id());
		valores.put("isActive", festaConsumo.getIsActive());

		
 	
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

	
	public FestaConsumo VerifyIfExistActive(String userId)
	{
	

		      Cursor c = getReadableDatabase().query(TABELA, COLUNAS, null, null, null, null, null);
		      FestaConsumo consumoAtivo=new FestaConsumo();
		      
		      while(c.moveToNext()){
		    	  FestaConsumo party = new FestaConsumo();

		    	  String name=c.getColumnName(7);
		    	  if(c.getString(6).equals(userId) && c.getString(9).equals("1"))
		    	  {
		    		  
		    		  consumoAtivo.setId(Integer.parseInt(c.getString(0)));
		    		  consumoAtivo.setFesta_id(Integer.parseInt(c.getString(1)));
		    		  consumoAtivo.setPub_id(Integer.parseInt(c.getString(2)));
		    		  consumoAtivo.setConsumo_limite(Double.parseDouble(c.getString(3)));
		    		  consumoAtivo.setValor_bebida(Double.parseDouble(c.getString(4)));
		    		  consumoAtivo.setQuantidade_consumida(Integer.parseInt(c.getString(5)));
		    		  consumoAtivo.setUser_id(Integer.parseInt(c.getString(6)));
		    		  consumoAtivo.setData(c.getString(7));
		    		  consumoAtivo.setNota_festa(Integer.parseInt(c.getString(8)));
		    		  consumoAtivo.setIsActive(Integer.parseInt(c.getString(9)));
		    		  
		    		  
		    		  return consumoAtivo;
		    		  
		    	  }
		    	  
		      }
			  c.close();
			  
			  consumoAtivo.setIsActive(1);
			 return consumoAtivo;
		 
		 
		
		
	}
	
	public FestaConsumo verifyEntityInDatabase(FestaConsumo festaConsumoParam,String userId)
	{
		
		Cursor c = null;
		boolean achou = false;
		
		FestaConsumo festaConsumoEntity=new FestaConsumo();
		
		String usuarioId=userId;
		usuarioId=usuarioId.replace('s', ' ');
		usuarioId=usuarioId.trim();
		
		
		try {
			c = getReadableDatabase().query(TABELA, COLUNAS, null, null, null,
					null, null);
			
						
						
			while (c.moveToNext()) {

				String festaId=String.valueOf(festaConsumoParam.getFesta_id());
				String data=String.valueOf(festaConsumoParam.getData());
			
				String dataDb=c.getString(8);
				
				if (c.getString(1).equals(festaId) 
					&& c.getString(5).equals(usuarioId)
					&& c.getString(7).equals(data)){
					
					festaConsumoEntity=festaConsumoParam;
					festaConsumoEntity.setUser_id(Integer.parseInt(usuarioId));
					festaConsumoEntity.setId(Integer.parseInt(c.getString(0)));	
					
					
					return festaConsumoEntity;
				}

			}
		} finally {
			if (c != null)
				c.close();
		}
		
		festaConsumoEntity.setUser_id(Integer.parseInt(usuarioId));
		
		return festaConsumoEntity;
		
	}
	
	

	
	
	

}
