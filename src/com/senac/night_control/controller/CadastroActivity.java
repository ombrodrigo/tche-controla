package com.senac.night_control.controller;

import android.app.AlertDialog;
import android.content.Context;
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
import android.widget.CheckBox;
import android.widget.EditText;

import com.senac.night_control.R;
import com.senac.night_control.database.UsuarioDao;
import com.senac.night_control.model.Usuario;
import com.senac.utilities.VisualUtilities;

public class CadastroActivity extends ActionBarActivity {
	

	private EditText nome,email,login,senha,confirmarSenha,ajuda_Nome,ajuda_Telefone;
	private CheckBox maior;
	private Button salvar;
	private VisualUtilities visual;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastro);
		
		//linha responsável por limitar o funcionamento da tela somente na vertical
				this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

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
		confirmarSenha=(EditText)findViewById(R.id.et_cad_confirmarSenha);
		ajuda_Nome=(EditText)findViewById(R.id.et_cad_ajudanome);
		ajuda_Telefone=(EditText)findViewById(R.id.et_cad_telefone);
		maior=(CheckBox)findViewById(R.id.input_check_tenho18anos);	
		salvar=(Button)findViewById(R.id.btn_cad_salvar);
		
	}
	
	public void Listeners()
	{
		
		salvar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Usuario user=new Usuario(nome.getText().toString(), email.getText().toString(), login.getText().toString(), senha.getText().toString(),ajuda_Nome.getText().toString(),ajuda_Telefone.getText().toString(),false);

				UsuarioDao usuarioDao=new UsuarioDao(getBaseContext());	
				
				
				if(email.getText().toString().length()<=0)
				{
					int[] msg=new int[2];
					msg[0]=R.string.label_email;
					msg[1]=R.string.label_obrigatorio;
					
					GenerateMessage(this, R.string.label_email, msg );						
						
					
				}else
				{
					if(visual.EmailIsValid(email.getText().toString()))
					{
						if(login.getText().toString().length()<=0)
						{
							int[] msg2=new int[2];
							msg2[0]=R.string.input_login;
							msg2[1]=R.string.label_obrigatorio;
							
							GenerateMessage(this, R.string.input_login,msg2);
						}else
						{
							//verificando se o login já existe no banco de dados
							if(!usuarioDao.verifyUniqueLogin(login.getText().toString()))
							{
								if(senha.getText().toString().length()<=0 || confirmarSenha.getText().toString().length()<=0)
								{
									int[] msg3=new int[4];
									msg3[0]=R.string.input_senha;
									msg3[1]=R.string.label_e;
									msg3[2]=R.string.label_confirmarSenha;
									msg3[3]=R.string.label_obrigatorio;
									
									GenerateMessage(this, R.string.input_senha,msg3);
								}else
								{
									
									//verificando inicialmente se a senha e a confirmação estão iguais
									if(senha.getText().toString().equals(confirmarSenha.getText().toString()))
									{
										
										try
										{
											
											usuarioDao.inserir(user);
											
											int[] msg4=new int[1];
											msg4[0]=R.string.label_cadastro_sucess;
											
											GenerateMessage(this, R.string.label_cadastro, msg4);											
											
										}catch(Exception e)
										{
											int[] msg9=new int[1];
											msg9[0]=R.string.error_insert;
										
											GenerateMessage(this, R.string.input_senha,msg9);
										}
										
										
										
									}else
									{
										int[] msg5=new int[1];
										msg5[0]=R.string.error_confirm_password;
										
										GenerateMessage(this, R.string.input_senha,msg5);
									}
									
								}
								
							}else
							{
								int[] msg6=new int[1];
								msg6[0]=R.string.error_login_utilizado;
								
								GenerateMessage(this, R.string.input_login, msg6);	
							}
						}
					
					}else
					{
						int[] msg7=new int[1];
						msg7[0]=R.string.error_email;
						
						GenerateMessage(this, R.string.label_email, msg7);
					}
					
				}
				
				
	


				
	
			}
		});
		
		maior.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {			
								
				if(maior.isChecked())
				{
					salvar.setEnabled(true);
				}else
				{
					salvar.setEnabled(false);
				}
				
			}
		});
	}
	
	
	//Método responsável por informar que a senha não foi confirmada corretamento
	public void GenerateMessage(OnClickListener t,int title,int [] message)
	{
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle(getString(title));
		
		String mensagem="";
		
		for (int i : message) {
	
			mensagem=mensagem+" "+getString(i);
			
		}		
		alertDialog.setMessage(mensagem);	
		
		
		alertDialog.setCanceledOnTouchOutside(true );
		
		// Set the Icon for the Dialog
		alertDialog.setIcon(R.drawable.ic_launcher);
		alertDialog.show();
	}
	
	


}
