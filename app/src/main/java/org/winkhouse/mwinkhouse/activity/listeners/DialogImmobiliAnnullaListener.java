package org.winkhouse.mwinkhouse.activity.listeners;

import org.winkhouse.mwinkhouse.activity.immobili.DettaglioImmobileExtraInfoActivity;
import org.winkhouse.mwinkhouse.fragment.immobili.DettaglioImmobileFragment;
import org.winkhouse.mwinkhouse.helpers.DataBaseHelper;
import org.winkhouse.mwinkhouse.models.ImmobiliVO;

import android.content.Context;
import android.content.DialogInterface;

public class DialogImmobiliAnnullaListener implements DialogInterface.OnClickListener {
	
	private DettaglioImmobileFragment v = null;
	private DettaglioImmobileExtraInfoActivity diei = null;
	private ImmobiliVO immobile = null;
	private Context c = null;
	
	public DialogImmobiliAnnullaListener(DettaglioImmobileFragment v, ImmobiliVO immobile, Context c) {
		this.v = v;
		this.immobile = immobile;
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
		if (immobile != null && immobile.getCodImmobile() != 0){			
			v.setImmobileVO(dbh.getImmobileById(immobile.getCodImmobile(), null));
		}
		dbh.close();
	}

}
