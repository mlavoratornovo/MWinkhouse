package org.winkhouse.mwinkhouse.activity.listeners;

import java.util.HashMap;
import java.util.Iterator;

import org.winkhouse.mwinkhouse.helpers.DataBaseHelper;
import org.winkhouse.mwinkhouse.models.ImmobiliPropietariVO;

import android.content.Context;
import android.content.DialogInterface;

public class DialogAnagraficheDissociaListener implements DialogInterface.OnClickListener {

	private Integer codImmobilePropieta = null;
	private HashMap<Integer,Object> selected = null;
	private Context c = null;
	
	public DialogAnagraficheDissociaListener(Integer codImmobilePropieta,HashMap<Integer,Object> selected,Context c) {
		this.codImmobilePropieta = codImmobilePropieta;
		this.selected = selected;
		this.c = c;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		DataBaseHelper dbh = new DataBaseHelper(c,DataBaseHelper.READ_DB);
		Iterator<Integer> it = selected.keySet().iterator();
		while (it.hasNext()) {
			ImmobiliPropietariVO ipVO = new ImmobiliPropietariVO();
			ipVO.setCodImmobile(codImmobilePropieta);
			ipVO.setCodAnagrafica(it.next());
			dbh.deleteImmobiliPropieta(ipVO);	
		}
		dbh.close();


	}

}
