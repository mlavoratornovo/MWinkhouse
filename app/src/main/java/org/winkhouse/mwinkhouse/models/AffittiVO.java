package org.winkhouse.mwinkhouse.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AffittiVO {

    private Integer codAffitti = null;
    private Integer codImmobile = null;
    private Integer codAgenteIns = null;
    private Date dataInizio = null;
    private Date dataFine = null;
    private Double cauzione = null;
    private Double rata = null;
    private String descrizione = null;

	public AffittiVO() {
		Calendar c = Calendar.getInstance(Locale.ITALIAN);
		c.setTime(new Date());
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
	    codAffitti = 0;
	    codImmobile = 0;
	    codAgenteIns = 0;
	    dataInizio = c.getTime();
//	    dataFine = c.getTime();
	    cauzione = 0.0;
	    rata = 0.0;
	    descrizione = "";
	}

	public AffittiVO(ResultSet rs)throws SQLException {
	    codAffitti = rs.getInt("CODAFFITTI");
	    codImmobile = rs.getInt("CODIMMOBILE");
	    codAgenteIns = rs.getInt("CODAGENTEINS");
	    dataInizio = rs.getTimestamp("DATAINIZIO");
	    dataFine = rs.getTimestamp("DATAFINE");
	    cauzione = rs.getDouble("CAUZIONE");
	    rata = rs.getDouble("RATA");
	    descrizione = rs.getString("DESCRIZIONE");
	}

	public Integer getCodAffitti() {
		return codAffitti;
	}

	public void setCodAffitti(Integer codAffitti) {
		this.codAffitti = codAffitti;
	}

	public Integer getCodImmobile() {
		return codImmobile;
	}

	public void setCodImmobile(Integer codImmobile) {
		this.codImmobile = codImmobile;
	}

	public Date getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}

	public Date getDataFine() {
		return dataFine;
	}

	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}

	public Double getCauzione() {
		return cauzione;
	}

	public void setCauzione(Double cauzione) {
		this.cauzione = cauzione;
	}

	public Double getRata() {
		return rata;
	}

	public void setRata(Double rata) {
		this.rata = rata;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Integer getCodAgenteIns() {
		return codAgenteIns;
	}

	public void setCodAgenteIns(Integer codAgenteIns) {
		this.codAgenteIns = codAgenteIns;
	}

	
}
