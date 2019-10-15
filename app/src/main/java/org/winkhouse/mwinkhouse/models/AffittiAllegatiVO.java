package org.winkhouse.mwinkhouse.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AffittiAllegatiVO {
	
	private Integer codAffittiAllegati = null;
	private Integer codAffitto = null;
	private String nome = null;
	private String descrizione = null;
	private String fromPath = null;

	public AffittiAllegatiVO() {
		codAffittiAllegati = 0;
		codAffitto = 0;
		nome = "";
		descrizione = "";
		fromPath = "";
	}

	public AffittiAllegatiVO(ResultSet rs) throws SQLException {
		codAffittiAllegati = rs.getInt("CODAFFITTIALLEGATI");
		codAffitto = rs.getInt("CODAFFITTO");
		nome = rs.getString("NOME");
		descrizione = rs.getString("DESCRIZIONE");
	}

	public Integer getCodAffittiAllegati() {
		return codAffittiAllegati;
	}

	public void setCodAffittiAllegati(Integer codAffittiAllegati) {
		this.codAffittiAllegati = codAffittiAllegati;
	}

	public Integer getCodAffitto() {
		return codAffitto;
	}

	public void setCodAffitto(Integer codAffitto) {
		this.codAffitto = codAffitto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getFromPath() {
		return fromPath;
	}

	public void setFromPath(String fromPath) {
		this.fromPath = fromPath;
	}

}
