package org.winkhouse.mwinkhouse.activity.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.winkhouse.mwinkhouse.activity.listeners.anim.ImageSelOnClickListener;
import org.winkhouse.mwinkhouse.activity.listeners.anim.SelAnagraficheEndAnimationListener;
import org.winkhouse.mwinkhouse.activity.listeners.anim.SelAnagraficheStartAnimationListener;
import org.winkhouse.mwinkhouse.helpers.DataBaseHelper;
import org.winkhouse.mwinkhouse.helpers.SearchParam;
import org.winkhouse.mwinkhouse.models.AnagraficheVO;
import org.winkhouse.mwinkhouse.models.ImmobiliVO;
import org.winkhouse.mwinkhouse.models.columns.ImmobiliColumnNames;

import org.winkhouse.mwinkhouse.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListaAnagraficheSelAdapter extends ArrayAdapter<AnagraficheVO> {

	private HashMap anagraficheColumnNames = null;
	private DataBaseHelper db = null;
	private HashMap<Integer,Object> selected = null;
	
	public ListaAnagraficheSelAdapter(Context context, int textViewResourceId, List<AnagraficheVO> anagrafiche,HashMap<Integer,Object> selected) {
		super(context, textViewResourceId, anagrafiche);
		anagraficheColumnNames = null;
		this.selected = selected;
	}
	
	private int getImmobiliSize(Integer codAnagrafica){
		
		ArrayList<SearchParam> parametri = new ArrayList<SearchParam>();
		SearchParam sp = new SearchParam(ImmobiliColumnNames.CODANAGRAFICA, codAnagrafica.toString(), null, null, Integer.class.getName(),SearchParam.ANAGRAFICHE);
		parametri.add(sp);
		HashMap columns = new HashMap();
		columns.put(ImmobiliColumnNames.CODIMMOBILE, null);
		ArrayList<ImmobiliVO>ivos = db.searchImmobili(parametri, columns);
		return ivos.size();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = null;
		this.db = new DataBaseHelper(getContext(), DataBaseHelper.NONE_DB);
		AnagraficheVO anagrafica = getItem(position);
		
        if (convertView == null) {
        	
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dettaglio_lista_anagrafiche, null);
            viewHolder = new ViewHolder();
            viewHolder.immagine	= (ImageView)convertView.findViewById(R.id.anagrafica_img);
            viewHolder.immagine.setTag(anagrafica);
            viewHolder.anagrafica = (TextView)convertView.findViewById(R.id.anagrafica);
            viewHolder.anagrafica_indirizzo = (TextView)convertView.findViewById(R.id.anagrafica_indirizzo);
            convertView.setTag(viewHolder);
            
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        
                    	

		Animation animation1 = AnimationUtils.loadAnimation(convertView.getContext(), R.anim.to_middle);
		Animation animation2 = AnimationUtils.loadAnimation(convertView.getContext(), R.anim.from_middle);
		
		animation2.setAnimationListener(new SelAnagraficheEndAnimationListener(viewHolder.immagine,R.drawable.ic_check_circle_white_48dp,
																				(anagrafica.getImmobiliPropietari(getContext(),null).size() == 0)
				 															    ? R.drawable.anagrafica
				 															    : R.drawable.anagrafica_propietario));
		
		animation1.setAnimationListener(new SelAnagraficheStartAnimationListener(viewHolder.immagine,
																				 ((anagrafica.getImmobiliPropietari(getContext(),null).size() == 0)
				  																  ? R.drawable.anagrafica
				  																  : R.drawable.anagrafica_propietario), 
				  																  animation2));

		viewHolder.immagine.setOnClickListener(new ImageSelOnClickListener(animation1,selected));
        
        
        viewHolder.anagrafica.setText(((anagrafica.getNome() != null) ? anagrafica.getNome() : "") + " " + 
        							  ((anagrafica.getCognome() != null) ? anagrafica.getCognome() : "") + " " +
        							  ((anagrafica.getRagioneSociale() != null) ? anagrafica.getRagioneSociale() : ""));
        
        viewHolder.anagrafica_indirizzo.setText(anagrafica.getProvincia() + " " + anagrafica.getCitta() + " " + anagrafica.getIndirizzo() );
        
        this.db.close();
        
        return convertView;
    }

    private class ViewHolder {
    	public ImageView immagine;
        public TextView anagrafica;
        public TextView anagrafica_indirizzo;
    }


}
