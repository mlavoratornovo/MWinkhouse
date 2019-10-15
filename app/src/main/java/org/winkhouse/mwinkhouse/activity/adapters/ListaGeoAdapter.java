package org.winkhouse.mwinkhouse.activity.adapters;

import java.util.List;

import org.winkhouse.mwinkhouse.models.GeoVO;

import org.winkhouse.mwinkhouse.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListaGeoAdapter extends ArrayAdapter<GeoVO> {

	public ListaGeoAdapter(Context context, int textViewResourceId, List<GeoVO> anagrafiche) {
		super(context, textViewResourceId, anagrafiche);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = null;
//		this.db = new DataBaseHelper(getContext(), DataBaseHelper.NONE_DB);
//		this.db.close();
		GeoVO geo = getItem(position);
		
        if (convertView == null) {
        	
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dettaglio_lista_geo, null);
            
            viewHolder = new ViewHolder();
            viewHolder.immagine	= (ImageView)convertView.findViewById(R.id.img_loc);
            viewHolder.immagine.setTag(geo);
            viewHolder.citta = (TextView)convertView.findViewById(R.id.citta);
            viewHolder.des_citta = (TextView)convertView.findViewById(R.id.des_citta);
            
            convertView.setTag(viewHolder);
            
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        
        viewHolder.citta.setText(geo.getCitta());
        viewHolder.des_citta.setText(geo.getProvincia() + " " + geo.getCap() + " " + geo.getRegione());
        
        return convertView;

	}
	
    private class ViewHolder {
    	public ImageView immagine;
        public TextView citta;
        public TextView des_citta;
    }

}
