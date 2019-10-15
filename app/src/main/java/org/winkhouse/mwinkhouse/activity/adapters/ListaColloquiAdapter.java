package org.winkhouse.mwinkhouse.activity.adapters;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import org.winkhouse.mwinkhouse.activity.listeners.anim.ImageSelOnClickListener;
import org.winkhouse.mwinkhouse.activity.listeners.anim.SelAnagraficheEndAnimationListener;
import org.winkhouse.mwinkhouse.activity.listeners.anim.SelAnagraficheStartAnimationListener;
import org.winkhouse.mwinkhouse.helpers.DataBaseHelper;
import org.winkhouse.mwinkhouse.models.ColloquiVO;

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

public class ListaColloquiAdapter extends ArrayAdapter<ColloquiVO> {

	private HashMap colloquiColumnNames = null;
	private DataBaseHelper db = null;
	private HashMap<Integer,Object> selected = null;
	private int count = 0;
	private SimpleDateFormat sdf = null;
	
	public ListaColloquiAdapter(Context context, int textViewResourceId, List<ColloquiVO> anagrafiche,HashMap<Integer,Object> selected) {
		super(context, textViewResourceId, anagrafiche);
		colloquiColumnNames = null;
		this.selected = selected;
		this.db = db;
		this.sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	}
		
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = null;
//		this.db = new DataBaseHelper(getContext(), DataBaseHelper.NONE_DB);
//		this.db.close();
		ColloquiVO colloquio = getItem(position);
		
        if (convertView == null) {
        	
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dettaglio_lista_colloqui, null);
            
            viewHolder = new ViewHolder();
            viewHolder.immagine	= (ImageView)convertView.findViewById(R.id.colloqui_img);
            viewHolder.immagine.setTag(colloquio);
            viewHolder.anagrafica = (TextView)convertView.findViewById(R.id.anagrafica);
            viewHolder.dataora_tipo = (TextView)convertView.findViewById(R.id.dataora_tipo);
            
            convertView.setTag(viewHolder);
            
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        
		Animation animation1 = AnimationUtils.loadAnimation(convertView.getContext(), R.anim.to_middle);
		Animation animation2 = AnimationUtils.loadAnimation(convertView.getContext(), R.anim.from_middle);
		
		animation2.setAnimationListener(new SelAnagraficheEndAnimationListener(viewHolder.immagine,R.drawable.ic_check_circle_white_48dp,
				 															   R.drawable.colloqui));
		
		animation1.setAnimationListener(new SelAnagraficheStartAnimationListener(viewHolder.immagine,
																				 R.drawable.colloqui, 
				  																 animation2));

		viewHolder.immagine.setOnClickListener(new ImageSelOnClickListener(animation1,selected));

        		
    	viewHolder.immagine.setImageDrawable(getContext().getResources().getDrawable(R.drawable.colloqui));
    	
    	String desanagrafica = "";
    	
        if (colloquio.getAnagrafiche(getContext(), null).size() > 0){
        	desanagrafica += ((colloquio.getAnagrafiche(getContext(), null).get(0).getNome() != null) ? colloquio.getAnagrafiche(getContext(), null).get(0).getNome() : "") + " " + 
					  		 ((colloquio.getAnagrafiche(getContext(), null).get(0).getCognome() != null) ? colloquio.getAnagrafiche(getContext(), null).get(0).getCognome() : "") + " " +
					  		 ((colloquio.getAnagrafiche(getContext(), null).get(0).getRagioneSociale() != null) ? colloquio.getAnagrafiche(getContext(), null).get(0).getRagioneSociale() : ""); 
        }
        
        viewHolder.anagrafica.setText(desanagrafica);        
        viewHolder.dataora_tipo.setText(sdf.format(colloquio.getDataColloquio()) + " " + colloquio.getDes_tipo_colloquio());
        
        return convertView;
    }

    private class ViewHolder {
    	public ImageView immagine;
        public TextView anagrafica;
        public TextView dataora_tipo;
    }

	public HashMap<Integer, Object> getSelected() {
		return selected;
	}



}
