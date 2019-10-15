package org.winkhouse.mwinkhouse.activity.listeners;

import org.winkhouse.mwinkhouse.activity.immobili.DettaglioImmobileExtraInfoActivity;
import org.winkhouse.mwinkhouse.helpers.DataBaseHelper;
import org.winkhouse.mwinkhouse.models.ImmobiliVO;

import android.content.DialogInterface;


public class DialogDettaglioImmobileExtraInfoSaveListener implements DialogInterface.OnClickListener {

	private DettaglioImmobileExtraInfoActivity v = null;
	private ImmobiliVO immobile = null;

	public DialogDettaglioImmobileExtraInfoSaveListener(DettaglioImmobileExtraInfoActivity v, ImmobiliVO immobile) {
		this.v = v;
		this.immobile = immobile;
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which) {
		DataBaseHelper dbh = new DataBaseHelper(v.getApplicationContext(),DataBaseHelper.NONE_DB);				
		if (immobile != null && immobile.getCodImmobile() != 0){			
			dbh.saveImmobile(immobile);
		}
		dbh.close();
		v.finish();
	}

}
