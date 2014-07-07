package com.senac.night_control.controller;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.senac.night_control.R;
import com.senac.night_control.database.FestaConsumoDao;
import com.senac.night_control.database.FestaDao;
import com.senac.night_control.database.PubDao;
import com.senac.night_control.model.Festa;
import com.senac.night_control.model.FestaConsumo;
import com.senac.night_control.model.Pub;
import com.senac.night_control.model.Usuario;
import com.senac.utilities.DataMananger;
import com.senac.utilities.DataManipulation;
import com.senac.utilities.PartyAdapterArray;

@SuppressLint("NewApi")
public class Consumo extends ActionBarActivity {

	private TextView qtGastei, maximo, valorBebida,tituloGastoAtual;
	private ImageButton add;
	private DataMananger data;
	private DataManipulation db;
	private EditText partyName, spendMaximum, drinkValue;
	private AlertDialog alertDialogParty, alertDialogSpend, alertDialogDrink,
			alertDialogPub;
	private ListView listContentParty, listContentPlace;
	private SeekBar seekContentSpend, seekContentDrink;

	private Pub pub;
	private Festa party;
	private FestaConsumo consumo;
	private Usuario u;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consumo);

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
		getMenuInflater().inflate(R.menu.consumo, menu);
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
			
			
			View rootView = inflater.inflate(R.layout.fragment_consumo,
					container, false);
			return rootView;
		}
	}

	public void InstanciarVariaveis() {

/*		quantidade = (TextView) findViewById(R.id.qtsFalta);

		maximo = (TextView) findViewById(R.id.maximo);

		tst=(TextView)findViewById(R.id.tst);*/
		
		add = (ImageButton) findViewById(R.id.imageView1);
		maximo=(TextView) findViewById(R.id.lblCoastMax);
		valorBebida=(TextView)findViewById(R.id.et_drinkSetValueS);
		tituloGastoAtual=(TextView)findViewById(R.id.textView1);
		consumo=new FestaConsumo();
		
		qtGastei = (TextView) findViewById(R.id.textView2);

		// alert's
		alertDialogParty = new AlertDialog.Builder(this).create();
		alertDialogSpend = new AlertDialog.Builder(this).create();
		alertDialogDrink = new AlertDialog.Builder(this).create();
		alertDialogPub = new AlertDialog.Builder(this).create();

		data = new DataMananger();
		db = new DataManipulation();

		data.setPrecoBebida(0);
		data.setQtQueroGastar(0);
		data.setQuantidadeQuePossoBeber((int) (data.getQtQueroGastar() / data
				.getPrecoBebida()));

		List<String> dadosUsuario=GetUser();
		
		/*tst.setText(dadosUsuario.get(0));*/
		
		
		alertDialogParty.setCancelable(false);
		alertDialogPub.setCancelable(false);
		alertDialogDrink.setCancelable(false);
		alertDialogSpend.setCancelable(false);
		
		ShowValues();


	}

	public void Listeners() {
	
		String usId=GetUser().get(2);
		usId=usId.replace('s', ' ');
		usId=usId.trim();
		db=new DataManipulation();
		db.festaConsumoDao=new FestaConsumoDao(getBaseContext());
		consumo=db.festaConsumoDao.VerifyIfExistActive(usId);
		
		if(consumo.getFesta_id()<=0){
		
		GenerateMessagePlace();
		}else
		{
			ShowValues();
			
		}

		add.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
			String aviso="Chega por hoje!";
			double tempValue=consumo.getValor_bebida()*consumo.getQuantidade_consumida();
			
				data.CalcularQuantasAindaPossoBeber(consumo.getConsumo_limite(), consumo.getQuantidade_consumida()*consumo.getValor_bebida(), consumo.getValor_bebida());
				
				
				if(data.AddBebida(consumo))
				{
					db.festaConsumoDao.inserir(consumo);
					ShowValues();
					
				}else{
					
					tituloGastoAtual.setText(aviso);
				}
			}
		});

	}

	public void ShowValues() {

		if(consumo!=null){
			qtGastei.setText(""+consumo.getQuantidade_consumida()*consumo.getValor_bebida());
			valorBebida.setText(""+consumo.getValor_bebida());
			maximo.setText(""+consumo.getConsumo_limite());
		}
		
	}

	public void ScreenRotation() {

		/*// Get current screen orientation
		Display display = ((WindowManager) getSystemService(WINDOW_SERVICE))
				.getDefaultDisplay();
		int orientation = display.getOrientation();
		switch (orientation) {
		case Configuration.ORIENTATION_PORTRAIT:

			data.AddBebida();
			ShowValues();
			break;
		case Configuration.ORIENTATION_LANDSCAPE:

			data.AddBebida();
			ShowValues();
			break;
		}*/
	}

	public void GenerateMessagePlace() {
		// setando o layout da viem para ser carregado n o dialog
		LayoutInflater li = LayoutInflater.from(this);
		final View viewPartyPlace = li.inflate(R.layout.alert_place_question,
				null);

		GenerateListViewPub(viewPartyPlace);

		alertDialogPub.setView(viewPartyPlace);

		alertDialogPub.setButton2("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface alertDialogParty, int id) {

				TextView textoPub = (TextView) alertDialogPub
						.findViewById(R.id.et_perg_place);

				// inserindo dados no bd funcionando

				pub = new Pub(textoPub.getText().toString());

				db.GenerateSession(pub, null, null);

				/*
				 * PubDao pubDao=new PubDao(getBaseContext());
				 * 
				 * 
				 * //verifica se o pub já existe no banco de dados, se não
				 * existe insere
				 * if(!pubDao.verifyUniquePubInBd(pub.getPub_nome())) {
				 * 
				 * pubDao.inserir(pub); }
				 */

				// Nesse momento deve ser inserido o pub na variável de sessão
				// para registrar somente no fim do questionário

				GenerateMessageParty();
				alertDialogPub.cancel();
			}
		});

		// Set the Icon for the Dialog
		alertDialogPub.setIcon(R.drawable.ic_launcher);
		alertDialogPub.show();

	}

	public void GenerateMessageParty() {
		// setando o layout da viem para ser carregado n o dialog
		LayoutInflater li = LayoutInflater.from(this);
		final View viewParty = li.inflate(R.layout.alert_party_question, null);

		GenerateListViewParty(viewParty);

		alertDialogParty.setView(viewParty);

		alertDialogParty.setButton2("Ok",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface alertDialogPartyy,
							int id) {

						TextView textoParty = (TextView) alertDialogParty
								.findViewById(R.id.et_perg_festa);

						// capturando a data atual
						Date date = new Date(0);
						String dataAtual = date.getDate() + "/" + date.getDay()
								+ "/" + date.getYear();

						/* FestaDao partyDao=new FestaDao(getBaseContext()); */
						party = new Festa(textoParty.getText().toString());

						db.GenerateSession(pub, party, null);

						/* partyDao.inserir(party); */

						GenerateMessageSpend();
						alertDialogParty.cancel();
					}
				});

		// Set the Icon for the Dialog
		alertDialogParty.setIcon(R.drawable.ic_launcher);
		alertDialogParty.show();

	}

	public void GenerateListViewParty(final View v) {

		// instanciando o objeto listeViem que não pode ser instanciando no
		// método instanciar variáveis pois ainda não está no contexto
		listContentParty = (ListView) v.findViewById(R.id.listViewContentParty);

		// criando uma lista fake para testes, aqui iremos buscar os dados do
		// banco de dados, GetAllParty()
		DataManipulation fake = new DataManipulation();

		PartyAdapterArray party = new PartyAdapterArray(this,
				fake.ReturnDataParty(getBaseContext()),
				android.R.layout.simple_list_item_1);
		listContentParty.setAdapter(party);

		// criando o evento de click ao selecionar uma festa na lista
		listContentParty.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {

				TextView item = ((TextView) view);
				TextView festa = (TextView) v.findViewById(R.id.et_perg_festa);
				festa.setText(item.getText());

			}
		});
	}

	public void GenerateListViewSpend(final View v, final TextView content) {

	}

	public void GenerateListViewPub(final View v) {

		// instanciando o objeto listeViem que não pode ser instanciando no
		// método instanciar variáveis pois ainda não está no contexto
		listContentPlace = (ListView) v.findViewById(R.id.listViewContentPlace);

		// criando uma lista fake para testes, aqui iremos buscar os dados do
		// banco de dados, GetAllParty()
		DataManipulation fake = new DataManipulation();

		PartyAdapterArray place = new PartyAdapterArray(this,
				fake.ReturnDataPlace(getBaseContext()),
				android.R.layout.simple_list_item_1);
		listContentPlace.setAdapter(place);

		// criando o evento de click ao selecionar uma festa na lista
		listContentPlace.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {

				TextView itemPlace = ((TextView) view);
				TextView place = (TextView) v.findViewById(R.id.et_perg_place);
				place.setText(itemPlace.getText());

			}
		});

	}

	public void GenerateMessageSpend() {
		// setando o layout da viem para ser carregado n o dialog
		LayoutInflater li2 = LayoutInflater.from(this);
		final View viewSpend = li2.inflate(
				R.layout.alert_maximum_spending_question, null);

		alertDialogSpend.setView(viewSpend);

		alertDialogSpend.setButton2("Ok",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface alertDialogSpend, int id) {

						// captando o valor do alert e colocando na classe data
						spendMaximum = (EditText) viewSpend
								.findViewById(R.id.et_perg_gasto);

						double valor = 0;
						String temp = spendMaximum.getText().toString();
						valor = Double.parseDouble(temp);

					/*	party.setConsumo_limite(valor);*/
						
						consumo=new FestaConsumo();
						consumo.setConsumo_limite(valor);

						db.GenerateSession(pub, party, consumo);

						/* data.setQtQueroGastar(valor); */

						GenerateMessageDrink();
						alertDialogSpend.cancel();
					}
				});

		// Set the Icon for the Dialog
		alertDialogSpend.setIcon(R.drawable.ic_launcher);
		alertDialogSpend.show();

		Button btnAdd, btnDecremet;

		btnAdd = (Button) alertDialogSpend.findViewById(R.id.btn_AddOneInSPend);
		btnDecremet = (Button) alertDialogSpend
				.findViewById(R.id.btn_DecrementOneInSPend);

		final TextView text = (TextView) alertDialogSpend
				.findViewById(R.id.et_perg_gasto);

		// actions
		btnAdd.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				double value = 0;

				if (text.getText().toString().length() > 0) {
					value = Double.parseDouble(text.getText().toString());

				}

				value += 1;

				if (value > 0 && value < 1000) {
					String valueString = String.valueOf(value);
					text.setText(valueString);

				}

			}
		});
		;

		btnDecremet.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				double value = 0;

				if (text.getText().toString().length() > 0) {
					value = Double.parseDouble(text.getText().toString());

				}

				value -= 1;

				if (value > 0) {
					String valueString = String.valueOf(value);
					text.setText(valueString);

				}

			}
		});
		;

		// instanciando o objeto listeViem que não pode ser instanciando no
		// método instanciar variáveis pois ainda não está no contexto
		seekContentSpend = (SeekBar) alertDialogSpend
				.findViewById(R.id.seekViewContentSpend);

		// criando o evento de click ao selecionar uma festa na lista
		seekContentSpend
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {

					}

					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {

						double variavel = progress;

						String var = String.valueOf(variavel);
						text.setText(var);

					}
				});

	}

	public void GenerateMessageDrink() {
		// setando o layout da viem para ser carregado n o dialog
		LayoutInflater li3 = LayoutInflater.from(this);
		final View viewDrink = li3.inflate(R.layout.alert_drink_question, null);

		alertDialogDrink.setView(viewDrink);

		alertDialogDrink.setButton2("Ok",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface alertDialogDrink, int id) {

						double value = 0;
						drinkValue = (EditText) viewDrink
								.findViewById(R.id.et_perg_bebida);
						String temp2 = drinkValue.getText().toString();
						value = Double.parseDouble(temp2);

						
						consumo.setValor_bebida(value);
						
						VerifyObjectsForSave(party, pub, consumo,
								getBaseContext());

						/*
						 * data.setPrecoBebida(Double.parseDouble(drinkValue.getText
						 * ().toString()));
						 * data.CalcularQuantasAindaPossoBeber(data
						 * .getQtQueroGastar(), data.getGastos(),
						 * data.getPrecoBebida());
						 */
						
						

						ShowValues();
						db.GenerateSession(pub, party, consumo);
						alertDialogDrink.cancel();
					}
				});

		// Set the Icon for the Dialog
		alertDialogDrink.setIcon(R.drawable.ic_launcher);
		alertDialogDrink.show();

		Button btnAdd, btnDecremet;

		btnAdd = (Button) alertDialogDrink.findViewById(R.id.btn_AddOneInDrink);
		btnDecremet = (Button) alertDialogDrink
				.findViewById(R.id.btn_DecrementOneInDrink);

		final TextView text = (TextView) alertDialogDrink
				.findViewById(R.id.et_perg_bebida);

		// actions
		btnAdd.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				double value = 0;

				if (text.getText().toString().length() > 0) {

					value = Double.parseDouble(text.getText().toString());
				}

				value += 1;

				if (value > 0 && value < 1000) {

					String valueString = String.valueOf(value);
					text.setText(valueString);

				}

			}
		});
		;

		btnDecremet.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				double value = 0;

				if (text.getText().toString().length() > 0) {

					value = Double.parseDouble(text.getText().toString());
				}

				value -= 1;

				if (value > 0) {
					String valueString = String.valueOf(value);
					text.setText(valueString);
				}

			}
		});
		;

		// instanciando o objeto listeViem que não pode ser instanciando no
		// método instanciar variáveis pois ainda não está no contexto
		seekContentDrink = (SeekBar) alertDialogDrink
				.findViewById(R.id.seekViewContentDrink);

		// criando o evento de click ao selecionar uma festa na lista
		seekContentDrink
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {

					}

					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {

						double variavel = progress;

						String var = String.valueOf(variavel);
						text.setText(var);

					}
				});

	}

	public void VerifyObjectsForSave(Festa f, Pub p, FestaConsumo fc,
			Context context) {

		if (f != null) {
			if (p != null) {

				

				if ((p=SavePubInDatabase(p, context)) != null) {
					if((f=SaveFestaInDatabase(f, context))!=null)
					{
						
						p=db.pubDao.verifyEntityInDatabase(p.getPub_nome());
						f=db.festaDao.verifyEntityInDatabase(f.getFestaNome());
						
				
						SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
						Date hoje = new Date();
						
						consumo.setQuantidade_consumida(0);
						consumo.setData(df.format(hoje.getTime()));
						consumo.setFesta_id(f.getId());
						consumo.setPub_id(p.getId());
						consumo.setId(0);
						consumo.setValor_bebida(fc.getValor_bebida());
						consumo.setConsumo_limite(fc.getConsumo_limite());
						consumo.setIsActive(0);
						
						
						SaveConsumoInDAtabase(consumo,context);
					}
				}

				

				

			} else {
				// informa que o pub esta nulo
			}

		} else {
			// informa que a festa está nula
		}

	}


	// /Método responsável por salvar o pub no banco de dados
	private Pub SavePubInDatabase(Pub p, Context context) {

		try {

			db.pubDao = new PubDao(context);

			p = db.pubDao.verifyEntityInDatabase(p.getPub_nome());

			if (p.getId() <= 0) {

				db.pubDao.inserir(p);
				p = db.pubDao.verifyEntityInDatabase(p.getPub_nome());

				Log.e("SaveLogSucess",
						"Pub gravado com sucesso: " + p.getPub_nome());

			} else {
			Log.e("SaveLogSucess", "O Pub: (" + p.getPub_nome()
						+ ") já existia no banco de dados Id:" + p.getId());

			}

		} catch (Exception ex) {
			p = null;
			Log.w("SaveLogFailed", "SavePubInDatabase erro: " + ex.getMessage());
		}

		return p;

	}

	//Método responsável por salvar a festa no banco de dados
	private Festa SaveFestaInDatabase(Festa f, Context context) 
	{
		
		try {

			db.festaDao = new FestaDao(context);

			f=db.festaDao.verifyEntityInDatabase(f.getFestaNome());
			
			
			if (f.getId() <= 0) {
				
				db.festaDao.inserir(f);
				f = db.festaDao.verifyEntityInDatabase(f.getFestaNome());

				Log.e("SaveLogSucess",
						"Festa gravada com sucesso: " + f.getFestaNome());
			}else
			{
				Log.e("SaveLogSucess", "A Festa: (" + f.getFestaNome()
						+ ") já existia no banco de dados Id:" + f.getId());
			}
			
			
			

		} catch (Exception ex) {
			f = null;
			Log.w("SaveLogFailed", "SaveFestaInDatabase erro: " + ex.getMessage());
		}

		return f;
		

	}
	
	//Método responsável por 
	private FestaConsumo SaveConsumoInDAtabase(FestaConsumo fc, Context context) 
	{
		
		
		try {

			db.festaConsumoDao=new FestaConsumoDao(context);
			
			FestaConsumo fcTemp=db.festaConsumoDao.verifyEntityInDatabase(fc, ""+GetUser().get(2));
			
			if(fcTemp.getId()>0)
			{				
				fc.setId(fcTemp.getId());
				fc.setIsActive(1);
				db.festaConsumoDao.inserir(fc);
				
				Log.e("SaveLogSucess",
						"Consumo gravado com sucesso: (Existia no banco)" + fcTemp.getId());
			}else{			
				

				db.festaConsumoDao.inserir(fc);

				fc=db.festaConsumoDao.verifyEntityInDatabase(fc, ""+GetUser().get(2));
				
				Log.e("SaveLogSucess",
						"Consumo gravado com sucesso: " + fc.getId());
			}
			
	
			
			
			

		} catch (Exception ex) {
			fc = null;
			Log.w("SaveLogFailed", "SaveFestaInDatabase erro: " + ex.getMessage());
		}

		return fc;
		
		
		
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
