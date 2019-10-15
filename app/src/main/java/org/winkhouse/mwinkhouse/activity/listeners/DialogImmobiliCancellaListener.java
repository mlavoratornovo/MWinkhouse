package org.winkhouse.mwinkhouse.activity.listeners;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.winkhouse.mwinkhouse.activity.immobili.ListaImmobiliActivity;
import org.winkhouse.mwinkhouse.helpers.DataBaseHelper;
import org.winkhouse.mwinkhouse.helpers.ImmaginiHelper;
import org.winkhouse.mwinkhouse.models.ImmagineVO;
import org.winkhouse.mwinkhouse.models.ImmobiliVO;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class DialogImmobiliCancellaListener implements DialogInterface.OnClickListener {
	
	
	private List<Integer> immobili_ids = null;
	private Context c = null;
	
	public DialogImmobiliCancellaListener(List<Integer> immobili_ids, Context c) {
		
		this.immobili_ids = immobili_ids;
		this.c = c;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		
		if (immobili_ids != null){
			
			Iterator<Integer> it = immobili_ids.iterator();
			
			DataBaseHelper dbh = new DataBaseHelper(c,DataBaseHelper.WRITE_DB);
			
			while(it.hasNext()){
			
				Integer codImmobile = it.next();
				
			    ArrayList<ImmagineVO> lista_immagini = dbh.getImmaginiImmobile(codImmobile, null);
			    Integer[] cod_immagini = new Integer[lista_immagini.size()];
			    int count = 0;
			    for (Iterator iterator = lista_immagini.iterator(); iterator.hasNext();) {
					ImmagineVO immagineVO = (ImmagineVO) iterator.next();
					cod_immagini[count] = immagineVO.getCodImmagine();
					count ++;
				}
				
			    ImmaginiHelper ih = new ImmaginiHelper();
			    ih.cancellaImmagini(c, dbh, codImmobile, cod_immagini);
			    
			    dbh.deleteImmobiliPropietariByCodImmobile(codImmobile);
			    dbh.deleteColloquioByCodImmobile(codImmobile);
			    dbh.deleteStanzeByCodImmobile(codImmobile);
			    
			    ImmobiliVO ivo = new ImmobiliVO();
			    ivo.setCodImmobile(codImmobile);
				dbh.deleteImmobile(ivo);
				
			}
			dbh.getSqllitedb().close();
			
			Intent lista_immobili =new Intent(c, ListaImmobiliActivity.class);
			lista_immobili.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
			c.startActivity(lista_immobili);
		}
		
	}

}
