package org.winkhouse.mwinkhouse.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AllegatiImmobiliVO {

	private Integer codAllegatiImmobili = null;
	private Integer codImmobile = null;
	private String commento = null;
	private String nome = null;
	private String fromPath = null;
	
	public AllegatiImmobiliVO() {
		codAllegatiImmobili = 0;
		codImmobile = 0;
		commento = "";
		nome = "";
		fromPath = "";
	}

	public AllegatiImmobiliVO(ResultSet rs) throws SQLException{
		codAllegatiImmobili = rs.getInt("CODALLEGATIIMMOBILI");
		codImmobile = rs.getInt("CODIMMOBILE");;
		commento = rs.getString("COMMENTO");
		nome = rs.getString("NOME");
	}

	public Integer getCodAllegatiImmobili() {
		return codAllegatiImmobili;
	}

	public void setCodAllegatiImmobili(Integer codAllegatiImmobili) {
		this.codAllegatiImmobili = codAllegatiImmobili;
	}

	public Integer getCodImmobile() {
		return codImmobile;
	}

	public void setCodImmobile(Integer codImmobile) {
		this.codImmobile = codImmobile;
	}

	public String getCommento() {
		return commento;
	}

	public void setCommento(String commento) {
		this.commento = commento;
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
		return getNome();
	}

}
