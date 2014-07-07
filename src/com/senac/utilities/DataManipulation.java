package com.senac.utilities;

import java.sql.Date;
import java.util.List;

import android.content.Context;

import com.senac.night_control.database.FestaConsumoDao;
import com.senac.night_control.database.FestaDao;
import com.senac.night_control.database.PubDao;
import com.senac.night_control.model.Festa;
import com.senac.night_control.model.FestaConsumo;
import com.senac.night_control.model.Pub;

public class DataManipulation {
	
		Pub pub;
		Festa festa;
		FestaConsumo festaConsumo;
		
		public PubDao pubDao;
		public FestaDao festaDao;
		public FestaConsumoDao festaConsumoDao;
		
	
	
	
	  public String[] ReturnFakeData()
	  {
		  
		  String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
			        "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
			        "Linux", "OS/2","Android", "iPhone", "WindowsMobile",
			        "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
			        "Linux", "OS/2","Android", "iPhone", "WindowsMobile",
			        "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
			        "Linux", "OS/2","Android", "iPhone", "WindowsMobile",
			        "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
			        "Linux", "OS/2","Android", "iPhone", "WindowsMobile",
			        "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
			        "Linux", "OS/2" };
		  
		  return values;
	  }
	  
	  public String[] ReturnDataPlace(Context context)
	  {
		  //Recuperando os dados do banco de dados e concatenando em uma lista
		  PubDao pub=new PubDao(context);
		  List<Pub> listaPub=pub.getLista();
		  
		  String[] values = new String[listaPub.size()];
		  
		  for (int i=0;i<listaPub.size();i++) {
			  
			  values[i]=listaPub.get(i).getPub_nome();
			
		}  
		  

		  return values;
	  }
	  
	  public String[] ReturnDataParty(Context context)
	  {
		  //Recuperando os dados do banco de dados e concatenando em uma lista
		  FestaDao party=new FestaDao(context);
		  List<Festa> listaParty=party.getLista();
		  
		  String[] values = new String[listaParty.size()];
		  
		  for (int i=0;i<listaParty.size();i++) {
			  
			  values[i]=listaParty.get(i).getFestaNome();
			
		}  
		  

		  return values;
	  }
	  
	  
	  public void GenerateSession(Pub bar,Festa party,FestaConsumo consumo)
	  {
	
		  
		  this.pub=bar!=null?bar:new Pub("empty");
		  this.festa=party!=null?party:new Festa("");
		  this.festaConsumo=consumo!=null?consumo:new FestaConsumo();
		  
	  }
	  
	  
	  

}
