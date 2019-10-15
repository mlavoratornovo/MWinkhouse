package org.winkhouse.mwinkhouse.activity.adapters;

import java.util.List;

import org.winkhouse.mwinkhouse.models.ContattiVO;
import org.winkhouse.mwinkhouse.models.TipologiaContattiVO;

import org.winkhouse.mwinkhouse.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class ListaContattiAdapter extends ArrayAdapter<ContattiVO> {

	public ListaContattiAdapter(Context context, int textViewResourceId, List<ContattiVO> contatti) {
		super(context, textViewResourceId, contatti);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = null;
		
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dettaglio_lista_contatti, null);
            viewHolder = new ViewHolder();
            viewHolder.selezione  = (CheckBox)convertView.findViewById(R.id.chk_item);            
            viewHolder.tipocontatto = (TextView)convertView.findViewById(R.id.tipo_contatto);
            viewHolder.contatto = (TextView)convertView.findViewById(R.id.contatto);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        
        ContattiVO contatto = getItem(position);
        TipologiaContattiVO tcvo = contatto.getTipologiaContattiVO(getContext(), null);
        viewHolder.selezione.setTag(contatto.getCodContatto());
       	viewHolder.tipocontatto.setText((tcvo != null) ? tcvo.getDescrizione(): "");
       	viewHolder.tipocontatto.setTag(contatto.getCodContatto());
      	viewHolder.contatto.setText(contatto.getContatto());
      	viewHolder.contatto.setTag(contatto.getCodContatto());
        	
      	
        return convertView;
    }

    private class ViewHolder {
    	public CheckBox selezione;
        public TextView tipocontatto;
        public TextView contatto;
    }

	
}
