package org.winkhouse.mwinkhouse.activity.listeners;

import java.util.Iterator;
import java.util.List;

import org.winkhouse.mwinkhouse.activity.colloqui.ListaColloquiActivity;
import org.winkhouse.mwinkhouse.helpers.DataBaseHelper;
import org.winkhouse.mwinkhouse.util.ActivityMessages;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class DialogColloquioCancellaListener implements DialogInterface.OnClickListener {
	
	
	private List<Integer> colloqui_ids = null;
	private Context c = null;
	private Integer codImmobile = null;
	private Integer codAnagrafica = null;
	
	public DialogColloquioCancellaListener(List<Integer> colloqui_ids, Context c,Integer codImmobile, Integer codAnagrafica) {		
		this.colloqui_ids = colloqui_ids;
		this.c = c;
		this.codImmobile = codImmobile;
		this.codAnagrafica = codAnagrafica;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		
		if (colloqui_ids != null){
			
			DataBaseHelper dbh = new DataBaseHelper(c,DataBaseHelper.WRITE_DB);
			
			Iterator<Integer> it = colloqui_ids.iterator();
			while (it.hasNext()) {
				
				Integer codColloquio = it.next();
				dbh.deleteAgentiColloquiByCodColloquio(codColloquio);
				dbh.deleteAnagraficheColloquiByCodColloquio(codColloquio);
				dbh.deleteColloquio(codColloquio);
				
			}
			
			dbh.getSqllitedb().close();
			
	        Intent intent = new Intent(c, ListaColloquiActivity.class);
	        
	        if ((codImmobile != null) && (codImmobile != 0)){        
	        	
	        	intent.putExtra(ActivityMessages.CODIMMOBILE_ACTION,codImmobile);
	        }
			
			if ((codAnagrafica != null) && (codAnagrafica != 0)){
				
				intent.putExtra(ActivityMessages.CODANAGRAFICA_ACTION,codAnagrafica);
				
			}
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
			c.startActivity(intent);
			
		}
	}

}
