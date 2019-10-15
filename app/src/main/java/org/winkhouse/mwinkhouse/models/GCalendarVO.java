package org.winkhouse.mwinkhouse.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GCalendarVO {
	
	private Integer codGCalendar = null;
	private String privateUrl = null;
	private String allUrl = null;
	private Integer codGData = null;	
	
	public GCalendarVO(){
		codGCalendar = 0;
		privateUrl = "";
		allUrl = "";
		codGData = 0;		
	}
	
	public GCalendarVO(ResultSet rs) throws SQLException{
		codGCalendar = rs.getInt("CODGCALENDAR");
		privateUrl = rs.getString("PRIVATEURL");
		allUrl = rs.getString("ALLURL");
		codGData = rs.getInt("CODGDATA");
		
	}

	public Integer getCodGCalendar() {
		return codGCalendar;
	}

	public void setCodGCalendar(Integer codGCalendar) {
		this.codGCalendar = codGCalendar;
	}

	public String getPrivateUrl() {
		return privateUrl;
	}

	public void setPrivateUrl(String privateUrl) {
		this.privateUrl = privateUrl;
	}

	public String getAllUrl() {
		return allUrl;
	}

	public void setAllUrl(String allUrl) {
		this.allUrl = allUrl;
	}

	public Integer getCodGData() {
		return codGData;
	}

	public void setCodGData(Integer codGData) {
		this.codGData = codGData;
	}

}
