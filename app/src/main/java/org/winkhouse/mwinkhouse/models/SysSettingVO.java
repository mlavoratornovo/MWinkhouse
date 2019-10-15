package org.winkhouse.mwinkhouse.models;

import java.sql.SQLException;
import java.util.HashMap;

import android.content.ContentValues;
import android.database.Cursor;

public class SysSettingVO {
	
	private Integer idSetting = null; 
	private String settingName = null;
	private String settingValue = null;
	
	public SysSettingVO(){
		idSetting = 0; 
		settingName = "";
		settingValue = "";		
	}
	
	public SysSettingVO(Cursor c,HashMap column_list) throws SQLException {
		
		if (column_list != null){
			if (column_list.containsKey("IDSETTING")){
				idSetting = c.getInt(c.getColumnIndex("IDSETTING"));
			}
		}else{
			idSetting = c.getInt(c.getColumnIndex("IDSETTING"));
		}
		
		if (column_list != null){
			if (column_list.containsKey("SETTINGNAME")){
				settingName = c.getString(c.getColumnIndex("SETTINGNAME"));
			}
		}else{
			settingName = c.getString(c.getColumnIndex("SETTINGNAME"));
		}

		if (column_list != null){
			if (column_list.containsKey("SETTINGVALUE")){
				settingValue = c.getString(c.getColumnIndex("SETTINGVALUE"));
			}
		}else{
			settingValue = c.getString(c.getColumnIndex("SETTINGVALUE"));
		}
		 
	}

	public Integer getIdSetting() {
		return idSetting;
	}

	public void setIdSetting(Integer idSetting) {
		this.idSetting = idSetting;
	}

	public String getSettingName() {
		return settingName;
	}

	public void setSettingName(String settingName) {
		this.settingName = settingName;
	}

	public String getSettingValue() {
		return settingValue;
	}

	public void setSettingValue(String settingValue) {
		this.settingValue = settingValue;
	}
	
	public ContentValues getContentValue(){
	    ContentValues values = new ContentValues();
	    values.put("IDSETTING", getIdSetting());
	    values.put("SETTINGNAME", getSettingName());
	    values.put("SETTINGVALUE", getSettingValue());
	    return values;
   }

}
