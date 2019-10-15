package org.winkhouse.mwinkhouse.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TipiAppuntamentiVO {

	private Integer codTipoAppuntamento = null;
	private String descrizione = null;
	private String gCalColor = null;
	private Integer ordine = null;
	
	public TipiAppuntamentiVO() {
		codTipoAppuntamento = 0;
		descrizione = "";
		gCalColor = "";
		ordine = 0;
	}

	public TipiAppuntamentiVO(ResultSet rs) throws SQLException {
		codTipoAppuntamento = rs.getInt("CODTIPOAPPUNTAMENTO");
		descrizione = rs.getString("DESCRIZIONE");
		gCalColor = rs.getString("GCALCOLOR");
		ordine = rs.getInt("ORDINE");
	}	
	
	public Integer getCodTipoAppuntamento() {
		return codTipoAppuntamento;
	}

	public void setCodTipoAppuntamento(Integer codTipoAppuntamento) {
		this.codTipoAppuntamento = codTipoAppuntamento;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getgCalColor() {
		return gCalColor;
	}

	public void setgCalColor(String gCalColor) {
		this.gCalColor = gCalColor;
	}

	public Integer getOrdine() {
		return ordine;
	}

	public void setOrdine(Integer ordine) {
		this.ordine = ordine;
	}

	@Override
	public String toString() {
		return getDescrizione();
	}

}
