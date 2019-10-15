package org.winkhouse.mwinkhouse.activity.adapters;

import java.util.List;

import org.winkhouse.mwinkhouse.models.StanzeImmobiliVO;
import org.winkhouse.mwinkhouse.models.TipologiaStanzeVO;

import org.winkhouse.mwinkhouse.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class ListaStanzeImmobiliAdapter extends ArrayAdapter<StanzeImmobiliVO> {

	public ListaStanzeImmobiliAdapter(Context context, int textViewResourceId, List<StanzeImmobiliVO> stanze) {
		super(context, textViewResourceId, stanze);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = null;
		
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dettaglio_lista_stanze, null);
            viewHolder = new ViewHolder();
            viewHolder.selezione  = (CheckBox)convertView.findViewById(R.id.chk_item);            
            viewHolder.tipostanza = (TextView)convertView.findViewById(R.id.tipo_stanza);
            viewHolder.stanza = (TextView)convertView.findViewById(R.id.stanza);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        
        StanzeImmobiliVO stanza = getItem(position);
        TipologiaStanzeVO tsvo = stanza.getTipologia(getContext(), null);
        viewHolder.selezione.setTag(stanza.getCodStanzeImmobili());
       	viewHolder.tipostanza.setText((tsvo != null) ? tsvo.getDescrizione(): "");
       	viewHolder.tipostanza.setTag(stanza.getCodStanzeImmobili());
      	viewHolder.stanza.setText(stanza.getMq().toString());
      	viewHolder.stanza.setTag(stanza.getCodStanzeImmobili());
        	
      	
        return convertView;
    }

    private class ViewHolder {
    	public CheckBox selezione;
        public TextView tipostanza;
        public TextView stanza;
    }

	
}
