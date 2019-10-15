package org.winkhouse.mwinkhouse.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AffittiAnagraficheVO {

	private Integer codAffittiAnagrafiche = null;
	private Integer codAffitto = null;
	private Integer codAnagrafica = null;
	private String nota = null;
	
	public AffittiAnagraficheVO(){
		codAffittiAnagrafiche = 0;
		codAffitto = 0;
		codAnagrafica = 0;
		nota = "";		
	}
	
	public AffittiAnagraficheVO(ResultSet rs) throws SQLException{
		codAffittiAnagrafiche = rs.getInt("CODAFFITTIANAGRAFICHE");
		codAffitto = rs.getInt("CODAFFITTO");
		codAnagrafica = rs.getInt("CODANAGRAFICA");
		nota = rs.getString("NOTA");		
	}

	public Integer getCodAffittiAnagrafiche() {
		return codAffittiAnagrafiche;
	}

	public void setCodAffittiAnagrafiche(Integer codAffittiAnagrafiche) {
		this.codAffittiAnagrafiche = codAffittiAnagrafiche;
	}

	public Integer getCodAffitto() {
		return codAffitto;
	}

	public void setCodAffitto(Integer codAffitto) {
		this.codAffitto = codAffitto;
	}

	public Integer getCodAnagrafica() {
		return codAnagrafica;
	}

	public void setCodAnagrafica(Integer codAnagrafica) {
		this.codAnagrafica = codAnagrafica;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	

}
