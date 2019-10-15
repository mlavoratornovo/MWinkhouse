package org.winkhouse.mwinkhouse.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportMarkersVO {

	private Integer codMarker = null;
	private Integer codReport = null;
	private String tipo = null;
	private String nome = null;
	private String getMethodName = null;
	private String params = null;
	private String paramsDesc = null;
		
	public ReportMarkersVO() {
		codMarker = 0;
		codReport = 0;
		tipo = "";
		nome = "";
		getMethodName = "";
		params = "";		
		paramsDesc = "";
	}
	
	public ReportMarkersVO(ResultSet rs) throws SQLException {
		codMarker = rs.getInt("CODMARKER");
		codReport = rs.getInt("CODREPORT");
		tipo = rs.getString("TIPO");
		nome = rs.getString("NOME");
		getMethodName = rs.getString("GETMETHODNAME");
		params = rs.getString("PARAMS");
		paramsDesc = rs.getString("PARAMSDESC");
	}

	public Integer getCodMarker() {
		return codMarker;
	}

	public void setCodMarker(Integer codMarker) {
		this.codMarker = codMarker;
	}

	public Integer getCodReport() {
		return codReport;
	}

	public void setCodReport(Integer codReport) {
		this.codReport = codReport;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getGetMethodName() {
		return getMethodName;
	}

	public void setGetMethodName(String getMethodName) {
		this.getMethodName = getMethodName;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String template) {
		this.params = template;
	}

	public String getParamsDesc() {
		return paramsDesc;
	}

	public void setParamsDesc(String paramsDesc) {
		this.paramsDesc = paramsDesc;
	}
	

}
