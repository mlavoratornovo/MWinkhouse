package org.winkhouse.mwinkhouse.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GDataVO {

	private Integer codGData = null;
	private Integer codContatto = null;
	private String pwsKey = null;
	private String descrizione = null;
	
	public GDataVO() {
		codGData = 0;
		codContatto = 0;
		pwsKey = "";
		descrizione = "";		
	}
	
	public GDataVO (ResultSet rs) throws SQLException{
		codGData = rs.getInt("CODGDATA");
		codContatto = rs.getInt("CODCONTATTO");
		pwsKey = rs.getString("PWSKEY");
		descrizione = rs.getString("DESCRIZIONE");				
	}

	public Integer getCodGData() {
		return codGData;
	}

	public void setCodGData(Integer codGData) {
		this.codGData = codGData;
	}

	public Integer getCodContatto() {
		return codContatto;
	}

	public void setCodContatto(Integer codContatto) {
		this.codContatto = codContatto;
	}

	public String getPwsKey() {
		return pwsKey;
	}

	public void setPwsKey(String pwsKey) {
		this.pwsKey = pwsKey;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}
