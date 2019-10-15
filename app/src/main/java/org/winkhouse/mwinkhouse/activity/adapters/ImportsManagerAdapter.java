package org.winkhouse.mwinkhouse.activity.adapters;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.winkhouse.mwinkhouse.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class ImportsManagerAdapter extends ArrayAdapter<File> {
	
	private SimpleDateFormat sdf = null;
	
	public ImportsManagerAdapter(Context context, int textViewResourceId, List<File> files) {
		super(context, textViewResourceId, files);
		sdf = new SimpleDateFormat();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = null;
		
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dettaglio_lista_imports, null);
            viewHolder = new ViewHolder();
            viewHolder.selezione  = (CheckBox)convertView.findViewById(R.id.chk_item);
            viewHolder.selezione.setChecked(false);
            viewHolder.nome_import = (TextView)convertView.findViewById(R.id.nome_import);
            viewHolder.data_import = (TextView)convertView.findViewById(R.id.data_import);
            viewHolder.size_import = (TextView)convertView.findViewById(R.id.size_import);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        
        File importfile = getItem(position);
        
        viewHolder.selezione.setTag(importfile);
       	viewHolder.nome_import.setText(importfile.getName());
       	
        String dataStr = sdf.format(new Date(importfile.lastModified()));
        sdf.applyPattern("dd/MM/yyyy HH.mm.ss");       	
       	
      	viewHolder.data_import.setText(dataStr);
      	viewHolder.size_import.setText(String.valueOf((importfile.length()/1024))+ " kB");
      	
        	
      	
        return convertView;
    }

    private class ViewHolder {
    	public CheckBox selezione;
        public TextView nome_import;
        public TextView data_import;
        public TextView size_import;
    }


}
