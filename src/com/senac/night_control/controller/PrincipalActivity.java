package com.senac.night_control.controller;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import android.widget.EditText;
import android.widget.TextView;

import com.senac.night_control.R;
import com.senac.night_control.database.UsuarioDao;
import com.senac.night_control.model.Usuario;
import com.senac.utilities.VisualUtilities;

public class PrincipalActivity extends ActionBarActivity {
	
	
	private Button conectar;
	private EditText login,senha;
	private TextView negado;
	private VisualUtilities visual;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//linha responsável por limitar o funcionamento da tela somente na vertical
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
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
		visual=new VisualUtilities();
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
				List<Usuario> usuarios = null;
				try {
					usuarios=daoUser.getLista();
					daoUser.close();
					
					
				} catch (Exception e) {
					// TODO: handle exception
				}
			
				
				for(Usuario usuario: usuarios)
				{  
					 if(usuario.getLogin().equals(login.getText().toString()) && usuario.getSenha().equals(senha.getText().toString()))
					 {							
 
						GeneratePopupWelcome(this, R.string.bem_vindo ,R.string.sessao_ativa,usuario.getLogin(),visual.SplitName(usuario.getNome()));
						 achou=true;
					 }
				} 
				
				if(achou==false){
					
					GeneratePopupDeniedAcess(this, R.string.acesso_negado,R.string.acesso_negado_para,login.getText().toString());
					
				}
				
		
			
				
			}
			
			
		});
		
	}
	
	//Métodos
	
	//Método responsável por gerar o pop-up de boas vindas a app quando o usuário efetua login com sucesso
	public void GeneratePopupWelcome(OnClickListener t,int title,int message,final String user,final String nome)
	{
		//criando um AlertDialog
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle(nome);
		alertDialog.setMessage(getString(message));
		
		
		//função que seta um botão ao alert criado
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
		   public void onClick(DialogInterface dialog, int which) {
			   
			   //Criando uma intent para chamar a outra tela
			   Intent intentBemVindo=new Intent(getBaseContext(),PrimeiraActivity.class);
			   
			   //put extra envia valores a intent que está sendo chamada através de um hashmap de chave e valor
			   intentBemVindo.putExtra( "nome", nome);
			   intentBemVindo.putExtra( "login", user);
			   
			   //iniciando a activity
			   startActivity(intentBemVindo);
		   }
		});
		
	
		// Set the Icon for the Dialog
		alertDialog.setIcon(R.drawable.ic_launcher);
		alertDialog.show();
		
		
		
	}
	
	public void GeneratePopupDeniedAcess(OnClickListener t,int title,int message,String username)
	{
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle(getString(title));
		alertDialog.setMessage( getString(message)+" "+username);
		
		
		
		alertDialog.setCancelable(true);
		
		// Set the Icon for the Dialog
		alertDialog.setIcon(R.drawable.ic_launcher);
		alertDialog.show();
	}

}
