package org.winkhouse.mwinkhouse.activity.adapters;

import java.util.List;

import org.winkhouse.mwinkhouse.R;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomSpinnerAdapter extends ArrayAdapter {
	
	private Activity activity;
	
	public Resources res;	
	private LayoutInflater inflater;
		
	public CustomSpinnerAdapter(Context context, int resource,
										Object[] objects) {
		super(context, resource, objects);
	
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public CustomSpinnerAdapter(Context context, int resource,List objects) {
		super(context, resource, objects);
		this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	

	@Override
    public View getDropDownView(int position, View convertView,ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
 
    // This funtion called for each row ( Called data.size() times )
    public View getCustomView(int position, View convertView, ViewGroup parent) {
 
        View row = inflater.inflate(R.layout.custom_combo_detail, parent, false);
         
        Object item = getItem(position);
        
        TextView label        = (TextView)row.findViewById(R.id.comboItemDescriprion);
         
        if(position==0){            
            label.setText("Selezionare");
        }
        else
        {
          
            label.setText(item.toString().replace("null", ""));
             
        }   
 
        return row;
      }
}
