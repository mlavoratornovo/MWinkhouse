package org.winkhouse.mwinkhouse.activity.listeners;

import java.util.Iterator;
import java.util.Set;

import org.winkhouse.mwinkhouse.activity.anagrafiche.ListaAnagraficheActivity;
import org.winkhouse.mwinkhouse.helpers.DataBaseHelper;
import org.winkhouse.mwinkhouse.util.ActivityMessages;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class DialogAnagraficheCancellaListener implements DialogInterface.OnClickListener {
	
	
	private Set<Integer> anagrafiche_ids = null;
	private Context c = null;
	private Integer codImmobile = null;
	private Integer codColloquio = null;
	
	public DialogAnagraficheCancellaListener(Set<Integer> anagrafiche_ids, Context c, Integer codImmobile, Integer codColloquio) {		
		this.anagrafiche_ids = anagrafiche_ids;
		this.c = c;
		this.codImmobile = codImmobile;
		this.codColloquio = codColloquio;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		
		if (anagrafiche_ids != null){
			
			DataBaseHelper dbh = new DataBaseHelper(c,DataBaseHelper.WRITE_DB);
			
			Iterator<Integer> it = anagrafiche_ids.iterator();
			while (it.hasNext()) {
				
				Integer codanagrafica = it.next();
				dbh.deleteContattiByAnagrafica(codanagrafica);
				dbh.deleteImmobiliPropietariByCodAnagrafica(codanagrafica);
				dbh.deleteAnagraficheColloquiByCodAnagrafica(codanagrafica);
				dbh.deleteAnagrafica(codanagrafica);
				dbh.resetPropietario(codanagrafica);
				
			}
			
			dbh.getSqllitedb().close();
			
			Intent lista_anagrafiche = new Intent(c, ListaAnagraficheActivity.class);			
			lista_anagrafiche.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
			
			if (codImmobile != null && codImmobile != 0){
				lista_anagrafiche.putExtra(ActivityMessages.PROPIETARI_ACTION, false);
				lista_anagrafiche.putExtra(ActivityMessages.CODIMMOBILE_PROPIETARI_ACTION,codImmobile);				
			}
			if (codColloquio != null && codColloquio != 0){				
				lista_anagrafiche.putExtra(ActivityMessages.CODCOLLOQUIO_ACTION,codColloquio);				
			}
			
			c.startActivity(lista_anagrafiche);
			
		}
	}

}
