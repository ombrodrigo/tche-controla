package com.senac.night_control.controller;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
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
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.senac.night_control.R;
import com.senac.night_control.database.FestaDao;
import com.senac.night_control.database.PubDao;
import com.senac.night_control.model.Festa;
import com.senac.night_control.model.Pub;
import com.senac.utilities.DataMananger;
import com.senac.utilities.FakeData;
import com.senac.utilities.PartyAdapterArray;

@SuppressLint("NewApi")
public class Consumo extends ActionBarActivity {
	
	private TextView qtGastei,maximo,quantidade;
	private Button add;
	private DataMananger data;
	private EditText partyName,spendMaximum,drinkValue;
	private AlertDialog alertDialogParty,alertDialogSpend,alertDialogDrink,alertDialogPub;
	private ListView listContentParty,listContentPlace;
	private SeekBar seekContentSpend;


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
	
	public void InstanciarVariaveis()
	{
		
		quantidade=(TextView)findViewById(R.id.qtsFalta);
		qtGastei=(TextView)findViewById(R.id.gasto);
		maximo=(TextView)findViewById(R.id.maximo);
		add=(Button)findViewById(R.id.addBira);
		
		//alert's
		alertDialogParty = new AlertDialog.Builder(this).create();	
		alertDialogSpend=new AlertDialog.Builder(this).create();
		alertDialogDrink=new AlertDialog.Builder(this).create();
		alertDialogPub = new AlertDialog.Builder(this).create();
		
		data=new DataMananger();
		
	
		data.setPrecoBebida(0);
		data.setQtQueroGastar(0);
		data.setQuantidadeQuePossoBeber((int) (data.getQtQueroGastar()/data.getPrecoBebida()));
		
		ShowValues();
		
		ScreenRotation();
		
	
		
	
		
		
	}
	
	public void Listeners()
	{
		
		GenerateMessagePlace();
		
		
		add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				data.AddBebida();
				data.CalcularQuantasAindaPossoBeber(data.getQtQueroGastar(), data.getGastos(), data.getPrecoBebida());
				data.setQuantidadeQuePossoBeber((int) (data.getQtQueroGastar()/data.getPrecoBebida()));
				
				
				ShowValues();
			}
		});
		
		
		
	
		
	}
	
	public void ShowValues()
	{
		
		quantidade.setText(Integer.toString(data.getQuantidadeQuePossoBeber()));
		qtGastei.setText(Double.toString(data.getGastos()));
		maximo.setText(Double.toString(data.getQtQueroGastar()));
	}

	public void ScreenRotation()
	{

		//Get current screen orientation
        Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        int orientation = display.getOrientation(); 
         switch(orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                
                data.AddBebida();
                ShowValues();
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                
                data.AddBebida();
                ShowValues();
                break;                   
        }
	}
	
	public void GenerateMessagePlace()
	{
		// setando o layout da viem para ser carregado n o dialog
			LayoutInflater li = LayoutInflater.from(this);
			final View viewPartyPlace = li.inflate(R.layout.alert_place_question, null);
			
			GenerateListViewPub(viewPartyPlace);
			
			alertDialogPub.setView(viewPartyPlace);
			 
			alertDialogPub.setButton2("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface alertDialogParty, int id) {
            	
            	TextView textoPub=(TextView)alertDialogPub.findViewById(R.id.et_perg_place);
            	 
            	//inserindo dados no bd funcionando
            	
            	
            	Pub pub=new Pub(textoPub.getText().toString());
            	 PubDao pubDao=new PubDao(getBaseContext());
            	 
            	 
            	 //verifica se o pub já existe no banco de dados, se não existe insere
            	 if(!pubDao.verifyUniquePubInBd(pub.getPub_nome()))
            	 {
            		 
            		 pubDao.inserir(pub);
            	 }
            	 
            	
            	//Nesse momento deve ser inserido o pub na variável de sessão para registrar somente no fim do questionário
            	
            	GenerateMessageParty();
            	alertDialogPub.cancel();
            }
        });
			
			
			// Set the Icon for the Dialog
			alertDialogPub.setIcon(R.drawable.ic_launcher);
			alertDialogPub.show();
		
	}
	
	public void GenerateMessageParty()
		{
			// setando o layout da viem para ser carregado n o dialog
			LayoutInflater li = LayoutInflater.from(this);
			final View viewParty = li.inflate(R.layout.alert_party_question, null);
			
		
			GenerateListViewParty(viewParty);
			
			alertDialogParty.setView(viewParty);
			 
			alertDialogParty.setButton2("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface alertDialogPartyy, int id) {
            	
            	
            	TextView textoParty=(TextView)alertDialogParty.findViewById(R.id.et_perg_festa);
            	
            	
            	FestaDao partyDao=new FestaDao(getBaseContext());
            	Festa party=new Festa(0, textoParty.getText().toString(), 0, 0, "", 0, 0);
            	
            	partyDao.inserir(party);
            	
            	GenerateMessageSpend();
            	alertDialogParty.cancel();
            }
        });
			
			
			// Set the Icon for the Dialog
			 alertDialogParty.setIcon(R.drawable.ic_launcher);
			 alertDialogParty.show();
			 
			 
			 
		}
	
	public void GenerateListViewParty(final View v)	{
		
		//instanciando o objeto listeViem que não pode ser instanciando no método instanciar variáveis pois ainda não está no contexto
		listContentParty=(ListView)v.findViewById(R.id.listViewContentParty);
		
		//criando uma lista fake para testes, aqui iremos buscar os dados do banco de dados, GetAllParty()
		FakeData fake=new FakeData();
		
		PartyAdapterArray party=new PartyAdapterArray(this, fake.ReturnDataParty(getBaseContext()), android.R.layout.simple_list_item_1);
		listContentParty.setAdapter(party);
		
		//criando o evento de click ao selecionar uma festa na lista
		listContentParty.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int arg2, long arg3) {
				
				
				TextView item = ((TextView)view);
				TextView festa=(TextView)v.findViewById(R.id.et_perg_festa);
				festa.setText(item.getText());
				
			}
		});
	}

	public void GenerateListViewSpend(final View v, final TextView content)	{
		
	
	}
		
	public void GenerateListViewPub(final View v)
	{
		
		//instanciando o objeto listeViem que não pode ser instanciando no método instanciar variáveis pois ainda não está no contexto
		listContentPlace=(ListView)v.findViewById(R.id.listViewContentPlace);
		
		//criando uma lista fake para testes, aqui iremos buscar os dados do banco de dados, GetAllParty()
		FakeData fake=new FakeData();
		
		PartyAdapterArray place=new PartyAdapterArray(this, fake.ReturnDataPlace(getBaseContext()), android.R.layout.simple_list_item_1);
		listContentPlace.setAdapter(place);
		
		
		//criando o evento de click ao selecionar uma festa na lista
		listContentPlace.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int arg2, long arg3) {
				
				
				TextView itemPlace = ((TextView)view);
				TextView place=(TextView)v.findViewById(R.id.et_perg_place);
				place.setText(itemPlace.getText());
				
			}
		});
		
		
		
	}
	
	public void GenerateMessageSpend()
		{
			// setando o layout da viem para ser carregado n o dialog
			LayoutInflater li2 = LayoutInflater.from(this);
			final View viewSpend = li2.inflate(R.layout.alert_maximum_spending_question, null);
			

			
			 alertDialogSpend.setView(viewSpend);

			 
			 alertDialogSpend.setButton2("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface alertDialogSpend, int id) {
   
            	//captando o valor do alert e colocando na classe data
            	spendMaximum=(EditText)viewSpend.findViewById(R.id.et_perg_gasto);
            	
            	double valor=0;
            	String temp=spendMaximum.getText().toString();
            	valor=Double.parseDouble(temp);
            	
            	data.setQtQueroGastar(valor);
            	
            	GenerateMessageDrink();
            	alertDialogSpend.cancel();
            }
        });
			
			
			// Set the Icon for the Dialog
			 alertDialogSpend.setIcon(R.drawable.ic_launcher);
			 alertDialogSpend.show();
			 
			 
			 final TextView text=(TextView)alertDialogSpend.findViewById(R.id.et_perg_gasto);
			 
				//instanciando o objeto listeViem que não pode ser instanciando no método instanciar variáveis pois ainda não está no contexto
				seekContentSpend=(SeekBar)alertDialogSpend.findViewById(R.id.seekViewContentSpend);
				
				//criando o evento de click ao selecionar uma festa na lista
				seekContentSpend.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
					
					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
						
						
					}
					
					@Override
					public void onProgressChanged(SeekBar seekBar, int progress,
							boolean fromUser) {
						
						int variavel=progress;
						
						if(text.getText().length()>0){
							int valor=Integer.parseInt(text.getText().toString());
							variavel+=valor;
						}
						String var=String.valueOf(variavel) ;
						text.setText(var);
		
						
					}
				} );
			 
			 
			 
		}
		
	public void GenerateMessageDrink()
		{
			// setando o layout da viem para ser carregado n o dialog
			LayoutInflater li3 = LayoutInflater.from(this);
			final View viewDrink = li3.inflate(R.layout.alert_drink_question, null);
			

			
			 alertDialogDrink.setView(viewDrink);
			 
			 alertDialogDrink.setButton2("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface alertDialogDrink, int id) {
            	
            	
   			 double value=0;
   			 drinkValue=(EditText)viewDrink.findViewById(R.id.et_perg_bebida);
   			 String temp2=drinkValue.getText().toString();
   			 value=Double.parseDouble(temp2);
   			 
   			 
   			 data.setPrecoBebida(Double.parseDouble(drinkValue.getText().toString()));
   			 data.CalcularQuantasAindaPossoBeber(data.getQtQueroGastar(), data.getGastos(), data.getPrecoBebida());
   			 
   			 ShowValues();
            	
            	alertDialogDrink.cancel();
            }
        });
			
			
			// Set the Icon for the Dialog
			 alertDialogDrink.setIcon(R.drawable.ic_launcher);
			 alertDialogDrink.show();
			 
			 
			 
		}
		
}

