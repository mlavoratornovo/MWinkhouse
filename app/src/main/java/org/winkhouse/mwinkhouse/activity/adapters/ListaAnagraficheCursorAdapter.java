package org.winkhouse.mwinkhouse.activity.adapters;

import java.util.ArrayList;
import java.util.HashMap;

import org.winkhouse.mwinkhouse.activity.listeners.anim.ImageSelOnClickListener;
import org.winkhouse.mwinkhouse.activity.listeners.anim.SelAnagraficheEndAnimationListener;
import org.winkhouse.mwinkhouse.activity.listeners.anim.SelAnagraficheStartAnimationListener;
import org.winkhouse.mwinkhouse.helpers.DataBaseHelper;
import org.winkhouse.mwinkhouse.models.ImmobiliVO;
import org.winkhouse.mwinkhouse.models.columns.AnagraficheColumnNames;

import org.winkhouse.mwinkhouse.R;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListaAnagraficheCursorAdapter extends CursorAdapter {
	
	private LayoutInflater inflater;
	private HashMap<Integer,Object> selected = null;
	
	public ListaAnagraficheCursorAdapter(Context context, Cursor c,boolean autoRequery) {
		super(context, c, autoRequery);
		inflater = LayoutInflater.from(context);
	}

	public ListaAnagraficheCursorAdapter(Context context, Cursor c, int flags) {
		super(context, c, flags);
		inflater = LayoutInflater.from(context);
	}
	
	@Override
	public void bindView(View arg0, Context arg1, Cursor arg2) {
		
		ImageView immagine	= (ImageView)arg0.findViewById(R.id.anagrafica_img);
        immagine.setTag(arg2.getInt(arg2.getColumnIndex(AnagraficheColumnNames.CODANAGRAFICA)));
        
        TextView anagrafica = (TextView)arg0.findViewById(R.id.anagrafica);
        anagrafica.setText(arg2.getString(arg2.getColumnIndex(AnagraficheColumnNames.NOME)) + " " + 
        				   arg2.getString(arg2.getColumnIndex(AnagraficheColumnNames.COGNOME)) + " " +
        				   arg2.getString(arg2.getColumnIndex(AnagraficheColumnNames.RAGSOC)));
        
        TextView anagrafica_indirizzo = (TextView)arg0.findViewById(R.id.anagrafica_indirizzo);
        anagrafica_indirizzo.setText(arg2.getString(arg2.getColumnIndex(AnagraficheColumnNames.PROVINCIA)) + " " + 
        							 arg2.getString(arg2.getColumnIndex(AnagraficheColumnNames.CITTA)) + " " + 
        							 arg2.getString(arg2.getColumnIndex(AnagraficheColumnNames.INDIRIZZO)));
        

		Animation animation1 = AnimationUtils.loadAnimation(arg0.getContext(), R.anim.to_middle);
		Animation animation2 = AnimationUtils.loadAnimation(arg0.getContext(), R.anim.from_middle);
		
		DataBaseHelper dbh = new DataBaseHelper(arg1,DataBaseHelper.NONE_DB);
		ArrayList<ImmobiliVO>immobiliPropietari = dbh.getImmobiliPropieta(null, 
																		  arg2.getInt(arg2.getColumnIndex(AnagraficheColumnNames.CODANAGRAFICA)));
//		dbh.close();

		
		animation2.setAnimationListener(new SelAnagraficheEndAnimationListener(immagine,R.drawable.ic_check_circle_white_48dp,
				 															   (immobiliPropietari.size() == 0)
				 															    ? R.drawable.anagrafica
				 															    : R.drawable.anagrafica_propietario));
		
		animation1.setAnimationListener(new SelAnagraficheStartAnimationListener(immagine,
																				 (immobiliPropietari.size() == 0)
				  																  ? R.drawable.anagrafica
				  																  : R.drawable.anagrafica_propietario, 
				  																  animation2));

		immagine.setOnClickListener(new ImageSelOnClickListener(animation1,selected));
		
    	immagine.setImageDrawable((immobiliPropietari.size() == 0)
    							  ? arg1.getResources().getDrawable(R.drawable.anagrafica)
    							  : arg1.getResources().getDrawable(R.drawable.anagrafica_propietario));

	}

	@Override
	public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
		return inflater.inflate(R.layout.dettaglio_lista_anagrafiche, null);
	}

	public HashMap<Integer, Object> getSelected() {
		return selected;
	}

	public void setSelected(HashMap<Integer, Object> selected) {
		this.selected = selected;
	}

}
