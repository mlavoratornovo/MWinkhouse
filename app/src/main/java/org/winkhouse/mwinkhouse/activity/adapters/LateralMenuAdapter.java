package org.winkhouse.mwinkhouse.activity.adapters;

import org.winkhouse.mwinkhouse.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LateralMenuAdapter extends BaseAdapter {
	
	private Context context = null;
	private String[] navItems = null;
	
	public LateralMenuAdapter(Context context, String[] navItems) {
		this.context = context;
		this.navItems = navItems; 
	}

	@Override
	public int getCount() {		
		return navItems.length;
	}

	@Override
	public Object getItem(int arg0) {
		
		return navItems[0];
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view;
		 
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.lateral_menu_item, null);
        }
        else {
            view = convertView;
        }
 
        TextView titleView = (TextView) view.findViewById(R.id.title);        
        ImageView iconView = (ImageView) view.findViewById(R.id.icon);
 
        titleView.setText( navItems[position] );
        
        if (navItems[position].equalsIgnoreCase("Stanze")){
        	iconView.setImageResource(R.drawable.stanze);
        }
        if (navItems[position].equalsIgnoreCase("Dati catastali")){
        	iconView.setImageResource(R.drawable.daticatastali);
        }
        if (navItems[position].equalsIgnoreCase("Colloqui")){
        	iconView.setImageResource(R.drawable.colloqui);
        }
        if (navItems[position].equalsIgnoreCase("Extra info")){
        	iconView.setImageResource(R.drawable.extrainfo);
        }
        if (navItems[position].equalsIgnoreCase("Custom info")){
        	iconView.setImageResource(R.drawable.mie_info);
        }
        if (navItems[position].equalsIgnoreCase("Anagrafiche")){
        	iconView.setImageResource(R.drawable.anagrafica);
        }
        
        
 
        return view;
	}

}
