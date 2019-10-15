package org.winkhouse.mwinkhouse.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AnagraficheAppuntamentiVO {

	private Integer codAnagraficheAppuntamenti = null;
	private Integer codAnagrafica = null;
	private Integer codAppuntamento = null;
	private String note = null;
	
	public AnagraficheAppuntamentiVO() {}
	
	public AnagraficheAppuntamentiVO(ResultSet rs) throws SQLException {
		codAnagraficheAppuntamenti = rs.getInt("CODANAGRAFICHEAPPUNTAMENTI");
		codAnagrafica = rs.getInt("CODANAGRAFICA");
		codAppuntamento = rs.getInt("CODAPPUNTAMENTO");
		note = rs.getString("NOTE");		
	}

	public Integer getCodAnagraficheAppuntamenti() {
		return codAnagraficheAppuntamenti;
	}

	public void setCodAnagraficheAppuntamenti(Integer codAnagraficheAppuntamenti) {
		this.codAnagraficheAppuntamenti = codAnagraficheAppuntamenti;
	}

	public Integer getCodAnagrafica() {
		return codAnagrafica;
	}

	public void setCodAnagrafica(Integer codAnagrafica) {
		this.codAnagrafica = codAnagrafica;
	}

	public Integer getCodAppuntamento() {
		return codAppuntamento;
	}

	public void setCodAppuntamento(Integer codAppuntamento) {
		this.codAppuntamento = codAppuntamento;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
