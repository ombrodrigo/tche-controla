package com.senac.night_control.controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.senac.night_control.R;
import com.senac.night_control.database.UsuarioDao;
import com.senac.night_control.model.Usuario;
import com.senac.utilities.VisualUtilities;

public class CadastroActivity extends ActionBarActivity {
	

	private EditText nome,email,login,senha,confirmarSenha;
	private CheckBox maior;
	private Button salvar;
	private VisualUtilities visual;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastro);

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
		getMenuInflater().inflate(R.menu.cadastro, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_cadastro,
					container, false);
			return rootView;
		}
	}
	
	//Instanciar os inputs
	public void InstanciarVariaveis()
	{
		visual=new VisualUtilities();
		nome=(EditText)findViewById(R.id.et_cad_nome);
		email=(EditText)findViewById(R.id.et_cad_email);
		login=(EditText)findViewById(R.id.et_cad_login);
		senha=(EditText)findViewById(R.id.et_cad_senha);
		maior=(CheckBox)findViewById(R.id.input_check_tenho18anos);	
		salvar=(Button)findViewById(R.id.btn_cad_salvar);
		
	}
	
	public void Listeners()
	{
		
		salvar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Usuario user=new Usuario(nome.getText().toString(), email.getText().toString(), login.getText().toString(), senha.getText().toString(), true);
				UsuarioDao usuarioDao=new UsuarioDao(getBaseContext());	
				
				try
				{
					usuarioDao.inserir(user);
					
					
					
				}catch(Exception e)
				{
					
				}
	


				
	
			}
		});
	}
	
	


}
