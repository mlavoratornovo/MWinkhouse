package org.winkhouse.mwinkhouse.activity.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.winkhouse.mwinkhouse.activity.listeners.anim.ImageSelOnClickListener;
import org.winkhouse.mwinkhouse.activity.listeners.anim.SelAnagraficheEndAnimationListener;
import org.winkhouse.mwinkhouse.activity.listeners.anim.SelAnagraficheStartAnimationListener;
import org.winkhouse.mwinkhouse.models.AnagraficheVO;
import org.winkhouse.mwinkhouse.models.ImmobiliVO;
import org.winkhouse.mwinkhouse.models.columns.AnagraficheColumnNames;

import org.winkhouse.mwinkhouse.R;
import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListaImmobiliAdapter extends ArrayAdapter<ImmobiliVO> {

	private HashMap anagraficheColumnNames = null;
	private HashMap<Integer,Object> selected = null;
	
	public ListaImmobiliAdapter(Context context, int textViewResourceId, List<ImmobiliVO> immobili,HashMap<Integer,Object> selected) {
		super(context, textViewResourceId, immobili);
		anagraficheColumnNames = null;
		this.selected = selected;
	}
	
	protected HashMap getAnagraficheColumnNames(){
		if (anagraficheColumnNames == null){
			anagraficheColumnNames = new HashMap();
			anagraficheColumnNames.put(AnagraficheColumnNames.CODANAGRAFICA, null);
			anagraficheColumnNames.put(AnagraficheColumnNames.COGNOME, null);
			anagraficheColumnNames.put(AnagraficheColumnNames.NOME, null);
			anagraficheColumnNames.put(AnagraficheColumnNames.RAGSOC, null);
		}
		return anagraficheColumnNames;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = null;
		
		ImmobiliVO immobile = getItem(position);
		
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dettaglio_lista_immobili, null);
            viewHolder = new ViewHolder();
            viewHolder.immagine	= (ImageView)convertView.findViewById(R.id.immobile_img);
            viewHolder.immagine.setTag(immobile);
            viewHolder.propietario = (TextView)convertView.findViewById(R.id.propietario);
            viewHolder.indirizzo = (TextView)convertView.findViewById(R.id.indirizzo);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        
        
        ArrayList<AnagraficheVO> aVOs = immobile.getAnagrafichePropietarie(getContext(), getAnagraficheColumnNames());
        
        if (aVOs.size() > 0){
        	String anagrafica_des = ((aVOs.get(0).getCognome() != null)? aVOs.get(0).getCognome() : "") + " " + 
        							((aVOs.get(0).getNome() != null)? aVOs.get(0).getNome() : "") + " " + 
        							((aVOs.get(0).getRagioneSociale() != null)? aVOs.get(0).getRagioneSociale() : "");
        	
        	viewHolder.propietario.setText(anagrafica_des);
        }else{
        	viewHolder.propietario.setText("");
        }
        viewHolder.indirizzo.setText(immobile.getCitta() + " " + immobile.getZona() + " " + 
        							 immobile.getIndirizzo() );
        
		Animation animation1 = AnimationUtils.loadAnimation(convertView.getContext(), R.anim.to_middle);
		Animation animation2 = AnimationUtils.loadAnimation(convertView.getContext(), R.anim.from_middle);
		
		animation2.setAnimationListener(new SelAnagraficheEndAnimationListener(viewHolder.immagine,R.drawable.ic_check_circle_white_48dp,
																			   R.drawable.gohome));
		
		animation1.setAnimationListener(new SelAnagraficheStartAnimationListener(viewHolder.immagine,
																				 R.drawable.gohome, 
				  																 animation2));

		viewHolder.immagine.setOnClickListener(new ImageSelOnClickListener(animation1,selected));
		
    	viewHolder.immagine.setImageDrawable(getContext().getResources().getDrawable(R.drawable.gohome));

                
        return convertView;
    }

    private class ViewHolder {
    	public ImageView immagine;
        public TextView propietario;
        public TextView indirizzo;
    }

	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		super.notifyDataSetChanged();
	}
	
	public HashMap<Integer,Object> getSelected() {
		return selected;
	}

}
