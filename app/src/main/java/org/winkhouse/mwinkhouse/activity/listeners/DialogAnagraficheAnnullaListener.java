package org.winkhouse.mwinkhouse.activity.listeners;

import org.winkhouse.mwinkhouse.fragment.anagrafiche.DettaglioAnagraficaFragment;
import org.winkhouse.mwinkhouse.helpers.DataBaseHelper;
import org.winkhouse.mwinkhouse.models.AnagraficheVO;

import android.content.Context;
import android.content.DialogInterface;

public class DialogAnagraficheAnnullaListener implements DialogInterface.OnClickListener {
	
	private DettaglioAnagraficaFragment v = null;
	private AnagraficheVO anagrafica = null;
	private Context c = null;
	
	public DialogAnagraficheAnnullaListener(DettaglioAnagraficaFragment v, AnagraficheVO anagrafica, Context c) {
		this.v = v;
		this.anagrafica = anagrafica;
		this.c = c;		
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		DataBaseHelper dbh = new DataBaseHelper(c,DataBaseHelper.NONE_DB);				
		if (anagrafica != null && anagrafica.getCodAnagrafica() != 0){
			v.setAnagrafica(dbh.getAnagraficaById(anagrafica.getCodAnagrafica(), null));
		}
		dbh.close();
	}

}
