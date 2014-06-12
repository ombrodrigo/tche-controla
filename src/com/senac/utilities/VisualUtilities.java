package com.senac.utilities;

import android.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;

import com.senac.night_control.controller.PrincipalActivity;

public class VisualUtilities extends ActionBarActivity {
	
	
	public void GeneratePopupWelcome(final Context context,String title,String message)
	{
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setTitle(title);
		alertDialog.setMessage(message);
		
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
		   public void onClick(DialogInterface dialog, int which) {
			   Intent intentBemVindo=new Intent(context,PrincipalActivity.class);
			   
			   startActivity(intentBemVindo);
		   }
		});
		// Set the Icon for the Dialog
		alertDialog.setIcon(R.drawable.ic_dialog_alert);
		alertDialog.show();
	}
	
	//função que recebe um nome e retorna somente o primeiro e segundo nome
	public String SplitName(String name)
	{
		String[] word=name.split(" ");
		
		if(word.length>1)
		{
			return word[0]+" "+word[1];
		}
		
		return name;
	}
	
	//função que recebe um nome e retorna somente o primeiro e segundo nome
		public boolean EmailIsValid(String email)
		{
			if(email.contains("@"))
			{
				return true;
			}
			
			return false;
		}

}
