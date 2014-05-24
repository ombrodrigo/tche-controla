package com.senac.night_control.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.senac.night_control.R;
import com.senac.utilities.DataMananger;

public class Consumo extends ActionBarActivity {
	
	private TextView qtGastei,maximo,quantidade,avisoMaximoGastos;
	private ImageView add;
	private DataMananger data;
	private EditText partyName,spendMaximum,drinkValue;
	private AlertDialog alertDialogParty,alertDialogSpend,alertDialogDrink;

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
		add=(ImageView)findViewById(R.id.addBira);
		
		//alert's
		alertDialogParty = new AlertDialog.Builder(this).create();	
		alertDialogSpend=new AlertDialog.Builder(this).create();
		alertDialogDrink=new AlertDialog.Builder(this).create();
		
		data=new DataMananger();
		
	
		data.setPrecoBebida(0);
		data.setQtQueroGastar(0);
		data.setQuantidadeQuePossoBeber((int) (data.getQtQueroGastar()/data.getPrecoBebida()));
		
		ShowValues();
		
			
	
		
	
		
		
	}
	
	public void Listeners()
	{
		
		GenerateMessageParty();
		
		
		add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				data.AddBebida();
				data.CalcularQuantasAindaPossoBeber(data.getQtQueroGastar(), data.getGastos(), data.getPrecoBebida());
				
				
				
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
	
	public void GenerateMessageParty()
		{
			// setando o layout da viem para ser carregado n o dialog
			LayoutInflater li = LayoutInflater.from(this);
			View viewParty = li.inflate(R.layout.alert_party_question, null);
			
			 
			
			 alertDialogParty.setView(viewParty);
			 
			alertDialogParty.setButton2("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface alertDialogParty, int id) {
            	GenerateMessageSpend();
            	alertDialogParty.cancel();
            }
        });
			
			
			// Set the Icon for the Dialog
			 alertDialogParty.setIcon(R.drawable.ic_launcher);
			 alertDialogParty.show();
			 
			 
			 
		}
		
	public void GenerateMessageSpend()
	{
			// setando o layout da viem para ser carregado n o dialog
			LayoutInflater li2 = LayoutInflater.from(this);
			final View viewSpend = li2.inflate(R.layout.alert_maximum_spending_question, null);
			
			 
			
			 alertDialogSpend.setView(viewSpend);
			 
			 alertDialogSpend.setButton2("Ok", new DialogInterface.OnClickListener() {
            
				 public void onClick(DialogInterface alert, int id) {
            	
            	//captando o valor do alert e colocando na classe data
            	spendMaximum=(EditText)viewSpend.findViewById(R.id.et_perg_gasto);
            	avisoMaximoGastos=(TextView)viewSpend.findViewById(R.id.label_aviso_maximum);
            	
            	
	            	double valor=0;
	            	String temp=spendMaximum.getText().toString();
	            	
	            	if(temp.length()>0){
	            		
		            	valor=Double.parseDouble(temp);
		            	
		            	data.setQtQueroGastar(valor);
		            	
		            	GenerateMessageDrink();
		            	alertDialogSpend.dismiss();
	            	}else{
	            	
	            		alertDialogSpend.show();
	            	}
            	
            }
        });
			
			
			// Set the Icon for the Dialog
			 alertDialogSpend.setIcon(R.drawable.ic_launcher);
			 alertDialogSpend.show();
			 
			 
			 
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
