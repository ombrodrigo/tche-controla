package com.senac.utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class PartyAdapterArray extends ArrayAdapter<String> {
	  private final Context context;
	  private final String[] values;

	  
	  public PartyAdapterArray(Context context, String[] values,int layout) {
		    super(context, layout, values);
		    this.context = context;
		    this.values = values;
	  }
	  



	 

}
