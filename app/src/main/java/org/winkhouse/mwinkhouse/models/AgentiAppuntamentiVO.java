package org.winkhouse.mwinkhouse.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AgentiAppuntamentiVO {

	private Integer codAgentiAppuntamenti = null;
	private Integer codAgente = null;
	private Integer codAppuntamento = null;
	private String note = null;
	
	public AgentiAppuntamentiVO() {
		codAgentiAppuntamenti = 0;
		codAgente = 0;
		codAppuntamento = 0;
		note = "";
	}

	public AgentiAppuntamentiVO(ResultSet rs) throws SQLException {
		codAgentiAppuntamenti = rs.getInt("CODAGENTIAPPUNTAMENTI");
		codAgente = rs.getInt("CODAGENTE");
		codAppuntamento = rs.getInt("CODAPPUNTAMENTO");
		note = rs.getString("NOTE");
	}

	public Integer getCodAgentiAppuntamenti() {
		return codAgentiAppuntamenti;
	}

	public void setCodAgentiAppuntamenti(Integer codAgentiAppuntamenti) {
		this.codAgentiAppuntamenti = codAgentiAppuntamenti;
	}

	public Integer getCodAgente() {
		return codAgente;
	}

	public void setCodAgente(Integer codAgente) {
		this.codAgente = codAgente;
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
