package org.winkhouse.mwinkhouse.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateFormatUtils {

	private static DateFormatUtils instance = null;
	
	public static DateFormatUtils getInstace(){
		
		if (instance == null){
			instance = new DateFormatUtils();
		}
		return instance;
		
	}
	
	public Date parse_xml(String dateToParse) throws Exception{
		
		Date returnValue = null;
		
		try {
			String[] tmp = dateToParse.split(" ");
			
			String[] tmp1 = tmp[0].split("-");
			
			String[] tmp2 = tmp[1].split(":");
			String[] tmp3 = tmp2[2].split("\\.");
			
			Integer secondi = Double.valueOf(tmp2[2]).intValue();

			Calendar c = Calendar.getInstance(Locale.ITALIAN);
			
			c.set(Calendar.YEAR, Integer.valueOf(tmp1[0]));
			c.set(Calendar.MONTH, Integer.valueOf(tmp1[1])-1);
			c.set(Calendar.DAY_OF_MONTH, Integer.valueOf(tmp1[2]));
			c.set(Calendar.HOUR_OF_DAY, Integer.valueOf(tmp2[0]));
			c.set(Calendar.MINUTE, Integer.valueOf(tmp2[1]));
			c.set(Calendar.SECOND, Integer.valueOf(tmp3[0]));
			c.set(Calendar.MILLISECOND, Integer.valueOf(tmp3[1]));
			
			returnValue = c.getTime();
		} catch (Exception e) {
			throw e;
		}
		
		return returnValue;
		
	}
	
	public String format_xml(Date dateToFormat) throws Exception{
		
		String returnValue = null;
		
		Calendar c = Calendar.getInstance(Locale.ITALIAN);
		c.setTime(dateToFormat);
		
		returnValue = "";
		returnValue += String.valueOf(c.get(Calendar.YEAR)) + "-";
		returnValue += String.valueOf(c.get(Calendar.MONTH)) + "-";
		returnValue += String.valueOf(c.get(Calendar.DAY_OF_MONTH)) + " ";
		returnValue += String.valueOf(c.get(Calendar.HOUR_OF_DAY)) + ":";
		returnValue += String.valueOf(c.get(Calendar.MINUTE)) + ":";
		returnValue += String.valueOf(c.get(Calendar.SECOND)) + ".";
		returnValue += String.valueOf(c.get(Calendar.MILLISECOND)) + " ";
		
		return returnValue;
		
	}
			
	
}
