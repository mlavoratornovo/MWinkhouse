package org.winkhouse.mwinkhouse.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AllegatiColloquiVO {

	private Integer codAllegatiColloquio = null;
	private Integer codColloquio = null;
	private String descrizione = null;
	private String nome = null;
	private String fromPath = null;
	
	public AllegatiColloquiVO() {
		codAllegatiColloquio = 0;
		codColloquio = 0;
		descrizione = "";
		nome = "";
		fromPath = "";
	}

	public AllegatiColloquiVO(ResultSet rs) throws SQLException {
		
		codAllegatiColloquio = rs.getInt("CODALLEGATICOLLOQUIO");
		codColloquio = rs.getInt("CODCOLLOQUIO");
		descrizione = rs.getString("COMMENTO");
		nome = rs.getString("NOME");
	}
	
	public Integer getCodAllegatiColloquio() {
		return codAllegatiColloquio;
	}

	public void setCodAllegatiColloquio(Integer codAllegatiColloquio) {
		this.codAllegatiColloquio = codAllegatiColloquio;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Integer getCodColloquio() {
		return codColloquio;
	}

	public void setCodColloquio(Integer codColloquio) {
		this.codColloquio = codColloquio;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getFromPath() {
		return fromPath;
	}

	public void setFromPath(String fromPath) {
		this.fromPath = fromPath;
	}

	@Override
	public String toString() {
		return getNome() + " " + getDescrizione();
	}
	
}
