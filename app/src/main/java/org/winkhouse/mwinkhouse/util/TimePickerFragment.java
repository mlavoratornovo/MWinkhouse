package org.winkhouse.mwinkhouse.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.winkhouse.mwinkhouse.fragment.colloqui.DettaglioColloquioFragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.widget.TimePicker;

public class TimePickerFragment extends DatePickerFragment implements OnTimeSetListener {

	public TimePickerFragment(){}

//	public TimePickerFragment(DettaglioColloquioFragment dettaglioColloquio, Date startDate) throws Exception {
//		super(dettaglioColloquio, startDate);
//	}

	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
				
		Calendar c = Calendar.getInstance(Locale.ITALIAN);
		c.setTime(this.startDate);
		
        return new TimePickerDialog(getActivity(), this, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);
    }

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		
		Calendar c = Calendar.getInstance(Locale.ITALIAN);
		
		c.setTime(startDate);
		c.set(Calendar.HOUR_OF_DAY, hourOfDay);
		c.set(Calendar.MINUTE, minute);		
					
		dettaglioColloquio.updateTime(c.getTime());		

	}

}
