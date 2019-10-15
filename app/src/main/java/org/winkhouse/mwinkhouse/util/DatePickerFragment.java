package org.winkhouse.mwinkhouse.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.winkhouse.mwinkhouse.fragment.colloqui.DettaglioColloquioFragment;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment implements OnDateSetListener {
	
	protected DettaglioColloquioFragment dettaglioColloquio = null;
	protected Date startDate = null;

	public DatePickerFragment(){}

	public void setDettaglioColloquio(DettaglioColloquioFragment dettaglioColloquio) {
		this.dettaglioColloquio = dettaglioColloquio;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStartDate() {

		return startDate;
	}

	public DettaglioColloquioFragment getDettaglioColloquio() {
		return dettaglioColloquio;
	}

//	public DatePickerFragment(DettaglioColloquioFragment dettaglioColloquio, Date startDate) throws Exception {
//
//		this.dettaglioColloquio = dettaglioColloquio;
//		this.startDate = startDate;
//
//	}
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
				
		Calendar c = Calendar.getInstance(Locale.ITALIAN);
		c.setTime(this.startDate);
		
        return new DatePickerDialog(getActivity(), this, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
    }

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		
		Calendar c = Calendar.getInstance(Locale.ITALIAN);
			
		c.setTime(startDate);
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, monthOfYear);
		c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
					
		dettaglioColloquio.updateData(c.getTime());
			
	}

}
