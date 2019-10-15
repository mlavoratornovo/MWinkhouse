package org.winkhouse.mwinkhouse.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AffittiSpeseVO {

	private Integer CodAffittiSpese = null;
	private Integer CodAffitto = null;
	private Integer CodParent = null;
	private Date DataInizio = null;
	private Date DataFine = null;
	private Date Scadenza = null;
	private Double Importo = null;
	private Double Versato = null;
	private String Descrizione = null;
	private Date DataPagato = null;
	private Integer CodAnagrafica = null;
	
	public AffittiSpeseVO() {
		Calendar c = Calendar.getInstance(Locale.ITALIAN);
		c.setTime(new Date());
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		CodAffittiSpese = new Integer(0);
		CodAffitto = new Integer(0);
		CodParent = new Integer(0);
		DataInizio = c.getTime();
		DataFine = c.getTime();
		Scadenza = c.getTime();
		Importo = new Double(0.0);
		Versato = new Double(0.0);
		Descrizione = "";
//		DataPagato = c.getTime();
		CodAnagrafica = new Integer(0);		
	}

	public AffittiSpeseVO(AffittiSpeseVO asVO) {
		CodAffittiSpese = asVO.getCodAffittiSpese();
		CodAffitto = asVO.getCodAffitto();
		CodParent = asVO.getCodParent();
		DataInizio = asVO.getDataInizio();
		DataFine = asVO.getDataFine();
		Scadenza = asVO.getScadenza();
		Importo = asVO.getImporto();
		Versato = asVO.getVersato();
		Descrizione = asVO.getDescrizione();
		DataPagato = asVO.getDataPagato();
		CodAnagrafica = asVO.getCodAnagrafica();		
	}

	public AffittiSpeseVO(ResultSet rs) throws SQLException{
		CodAffittiSpese = new Integer(rs.getInt("CODAFFITTISPESE"));
		CodAffitto = new Integer(rs.getInt("CODAFFITTO"));
		CodParent = new Integer(rs.getInt("CODPARENT"));
		DataInizio = rs.getTimestamp("DATAINIZIO");
		DataFine = rs.getTimestamp("DATAFINE");
		Scadenza = rs.getTimestamp("SCADENZA");
		Importo = new Double(rs.getDouble("IMPORTO"));
		Versato = new Double(rs.getDouble("IMPORTO"));
		Descrizione = rs.getString("DESCRIZIONE");
		DataPagato = rs.getTimestamp("DATAPAGATO");
		CodAnagrafica = new Integer(rs.getInt("CODANAGRAFICA"));		
	}

	public Integer getCodAffittiSpese() {
		return CodAffittiSpese;
	}

	public void setCodAffittiSpese(Integer codAffittiSpese) {
		CodAffittiSpese = codAffittiSpese;
	}

	public Integer getCodAffitto() {
		return CodAffitto;
	}

	public void setCodAffitto(Integer codAffitto) {
		CodAffitto = codAffitto;
	}

	public Integer getCodParent() {
		return CodParent;
	}

	public void setCodParent(Integer codParent) {
		CodParent = codParent;
	}

	public Date getDataInizio() {
		return DataInizio;
	}

	public void setDataInizio(Date dataInizio) {
		DataInizio = dataInizio;
	}

	public Date getDataFine() {
		return DataFine;
	}

	public void setDataFine(Date dataFine) {
		DataFine = dataFine;
	}

	public Date getScadenza() {
		return Scadenza;
	}

	public void setScadenza(Date scadenza) {
		Scadenza = scadenza;
	}

	public Double getImporto() {
		return Importo;
	}

	public void setImporto(Double importo) {
		Importo = importo;
	}

	public Double getVersato() {
		return Versato;
	}

	public void setVersato(Double versato) {
		Versato = versato;
	}

	public String getDescrizione() {
		return Descrizione;
	}

	public void setDescrizione(String descrizione) {
		Descrizione = descrizione;
	}

	public Date getDataPagato() {
		return DataPagato;
	}

	public void setDataPagato(Date dataPagato) {
		DataPagato = dataPagato;
	}

	public Integer getCodAnagrafica() {
		return CodAnagrafica;
	}

	public void setCodAnagrafica(Integer codAnagrafica) {
		CodAnagrafica = codAnagrafica;
	}

}
