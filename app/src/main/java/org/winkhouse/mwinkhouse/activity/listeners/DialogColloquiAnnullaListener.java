package org.winkhouse.mwinkhouse.activity.listeners;

import org.winkhouse.mwinkhouse.fragment.colloqui.DettaglioColloquioFragment;
import org.winkhouse.mwinkhouse.helpers.DataBaseHelper;
import org.winkhouse.mwinkhouse.models.ColloquiVO;

import android.content.Context;
import android.content.DialogInterface;

public class DialogColloquiAnnullaListener implements DialogInterface.OnClickListener {
	
	private DettaglioColloquioFragment v = null;
	private ColloquiVO colloquio = null;
	private Context c = null;
	
	public DialogColloquiAnnullaListener(DettaglioColloquioFragment v, ColloquiVO colloquio, Context c) {
		this.v = v;
		this.colloquio = colloquio;
		this.c = c; 
	}

//	public DialogImmobiliAnnullaListener(DettaglioImmobileExtraInfoActivity diei, ImmobiliVO immobile, Context c) {
//		this.diei = diei;
//		this.immobile = immobile;
//		this.c = c; 
//	}

	
	@Override
	public void onClick(DialogInterface dialog, int which) {
		
		DataBaseHelper dbh = new DataBaseHelper(c,DataBaseHelper.NONE_DB);
		
		if (colloquio != null && colloquio.getCodColloquio() != 0){			
			v.setColloquio(dbh.getColloquiById(colloquio.getCodColloquio(), null));
		}
		
		dbh.close();
		
	}

}
