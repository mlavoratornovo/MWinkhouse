package org.winkhouse.mwinkhouse.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class AppuntamentiVO {

	private Integer codAppuntamento = null;
	private Date dataInserimento = null;
	private Date dataAppuntamento = null;
	private Date dataFineAppuntamento = null;
	private Integer codTipoAppuntamento = null;
	private Integer codPadre = null;	
	private String iCalUID = null;
	private String descrizione = null;
	private String luogo = null;
	
	public AppuntamentiVO(){
		codAppuntamento = 0;
		dataInserimento = new Date();
		dataAppuntamento = new Date();
		dataFineAppuntamento = new Date();
		codTipoAppuntamento = 0;
		codPadre = 0;		
		descrizione = "";
		luogo = "";
		iCalUID = "";
	}

	public AppuntamentiVO(ResultSet rs) throws SQLException{
		codAppuntamento = rs.getInt("CODAPPUNTAMENTO");
		dataInserimento = rs.getTimestamp("DATAINSERIMENTO");
		dataAppuntamento = rs.getTimestamp("DATAAPPUNTAMENTO");
		descrizione = rs.getString("DESCRIZIONE");
		luogo = rs.getString("LUOGO");
		dataFineAppuntamento = (rs.getTimestamp("DATAFINEAPPUNTAMENTO")==null)
								? rs.getTimestamp("DATAAPPUNTAMENTO")
								: rs.getTimestamp("DATAFINEAPPUNTAMENTO");
								
		codTipoAppuntamento = rs.getInt("CODTIPOAPPUNTAMENTO");
		
		codPadre = rs.getInt("CODPADRE");		
		iCalUID = (rs.getString("ICALUID") == null)
				  ? ""
				  : rs.getString("ICALUID");
	}	
	
	public Integer getCodAppuntamento() {
		return codAppuntamento;
	}

	public void setCodAppuntamento(Integer codAppuntamento) {
		this.codAppuntamento = codAppuntamento;
	}

	public Date getDataInserimento() {		
		return dataInserimento;
	}

	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public Date getDataAppuntamento() {
		return dataAppuntamento;
	}

	public void setDataAppuntamento(Date dataAppuntamento) {
		this.dataAppuntamento = dataAppuntamento;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getLuogo() {
		return luogo;
	}

	public void setLuogo(String luogo) {
		this.luogo = luogo;
	}
	
	public Date getDataFineAppuntamento() {
		return dataFineAppuntamento;
	}
	
	public void setDataFineAppuntamento(Date dataFineAppuntamento) {
		this.dataFineAppuntamento = dataFineAppuntamento;
	}
	
	public Integer getCodTipoAppuntamento() {
		return codTipoAppuntamento;
	}
	
	public void setCodTipoAppuntamento(Integer codTipoAppuntamento) {
		this.codTipoAppuntamento = codTipoAppuntamento;
	}
	
	public Integer getCodPadre() {
		return codPadre;
	}
	
	public void setCodPadre(Integer codPadre) {
		this.codPadre = codPadre;
	}
	
	public String getiCalUID() {
		return iCalUID;
	}
	
	public void setiCalUID(String iCalUID) {
		this.iCalUID = iCalUID;
	}
	
	
}
