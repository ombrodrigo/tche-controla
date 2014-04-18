package com.senac.night_control.controller;

import java.util.List;

import com.senac.night_control.R;
import com.senac.night_control.database.UsuarioDao;
import com.senac.night_control.model.Usuario;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Build;

public class PrincipalActivity extends ActionBarActivity {
	
	
	private Button conectar;
	private EditText login,senha;
	private TextView negado;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		UsuarioDao user=new UsuarioDao(this);

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
		getMenuInflater().inflate(R.menu.principal, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_principal,
					container, false);
			return rootView;
		}
	}
	
	
	//Método responsável por chamar a tela de cadastro
	public void onClickCadastro(View v) {	
		
		Intent intentCadastro=new Intent(getBaseContext(),CadastroActivity.class);		
		startActivity(intentCadastro);
        
      }
	
	public void InstanciarVariaveis()
	{
		conectar=(Button)findViewById(R.id.btn_conectar);
		login=(EditText)findViewById(R.id.et_login);
		senha=(EditText)findViewById(R.id.et_senha);
		negado=(TextView)findViewById(R.id.tv_acess_negado);
		
	}
	
	public void Listeners()
	{
		conectar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UsuarioDao daoUser=new UsuarioDao(getBaseContext());
				Boolean achou=false;
				
				List<Usuario> usuarios=daoUser.getLista();
				daoUser.close();
				
				
				for(Usuario usuario: usuarios)
				{  
					 if(usuario.getLogin().equals(login.getText().toString()) && usuario.getSenha().equals(senha.getText().toString()))
					 {
						 Intent intentBemVindo=new Intent(getBaseContext(),PrimeiraActivity.class);
		
						 intentBemVindo.putExtra( "nome", usuario.getNome());
						 intentBemVindo.putExtra( "login", usuario.getLogin());
						 
						 startActivity(intentBemVindo);
						 achou=true;
					 }
				} 
				
				if(achou==false){
					negado.setText("Acesso negado para: "+login.getText());
				}
				
			}
		});
		
	}

}
