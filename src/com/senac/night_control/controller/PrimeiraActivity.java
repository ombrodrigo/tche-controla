package com.senac.night_control.controller;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.senac.night_control.R;
import com.senac.night_control.R.string;
import com.senac.night_control.model.Usuario;

public class PrimeiraActivity extends ActionBarActivity {

	ImageView cadastro,consumo;
	TextView bemVindo;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_primeira);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		InstanciarVariaveis();
		Listeners();
		
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.primeira, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_primeira,
					container, false);
			return rootView;
		}
	}
	
	public void InstanciarVariaveis()
	{

		
		 Bundle extras = getIntent().getExtras();
		 cadastro=(ImageView)findViewById(R.id.dash_cadastro);
		 consumo=(ImageView)findViewById(R.id.dash_consumo);
		 bemVindo=(TextView)findViewById(R.id.labelBemVindo);
		 
		 GetUser();
				 

	} 

	public void Listeners()
	{
		consumo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intentConsumo=new Intent(getBaseContext(),Consumo.class);
				
				List<String> dataAboutUser=GetUser();
				
				
				intentConsumo.putExtra( "nome", dataAboutUser.get(0));
				intentConsumo.putExtra( "login", dataAboutUser.get(1));
				intentConsumo.putExtra( "id", dataAboutUser.get(2));
				   
				
				startActivity(intentConsumo);
				
			}
		});
		
		
	}

	public List<String> GetUser()
	{
		
	List<String> dadosUsuario=new ArrayList<String>();
		
		Intent intent=getIntent();
		
	
		String nome=intent.getStringExtra("nome");
		String login=intent.getStringExtra("login");
		String idUser=intent.getStringExtra("id");

		
		dadosUsuario.add(nome);
		dadosUsuario.add(login);
		dadosUsuario.add(idUser);
		
		
		
		return dadosUsuario;
	}

}
