package org.winkhouse.mwinkhouse.models;

import java.sql.ResultSet;
import java.sql.SQLException;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;
import android.content.ContentValues;

public class DatiCatastaliVO {
	
	private Integer codDatiCatastali = null;
	private String foglio = null;
	private String particella = null;
	private String subalterno = null;
	private String categoria = null;
	private Double rendita = null;
	private Double redditoDomenicale = null;
	private Double redditoAgricolo = null;
	private Double dimensione = null;
	private Integer codImmobile = null;
	
	public DatiCatastaliVO() {
		super();
		codDatiCatastali = 0;
		foglio = "";
		particella = "";
		subalterno = "";
		categoria = "";
		rendita = 0.0;
		redditoDomenicale = 0.0;
		redditoAgricolo = 0.0;
		dimensione = 0.0;
		codImmobile = 0;
	}

	public DatiCatastaliVO(ResultSet rs) throws SQLException {
		super();
		codDatiCatastali = rs.getInt("CODDATICATASTALI");
		foglio = rs.getString("FOGLIO");
		particella = rs.getString("PARTICELLA");
		subalterno = rs.getString("SUBALTERNO");
		categoria = rs.getString("CATEGORIA");
		rendita = rs.getDouble("RENDITA");
		redditoDomenicale = rs.getDouble("REDDITODOMENICALE");
		redditoAgricolo = rs.getDouble("REDDITOAGRARIO");
		dimensione = rs.getDouble("DIMENSIONE");
		codImmobile = rs.getInt("CODIMMOBILE");
	}

	public Integer getCodDatiCatastali() {
		return codDatiCatastali;
	}

	public void setCodDatiCatastali(Integer codDatiCatastali) {
		this.codDatiCatastali = codDatiCatastali;
	}

	public String getFoglio() {
		return foglio;
	}

	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}

	public String getParticella() {
		return particella;
	}

	public void setParticella(String particella) {
		this.particella = particella;
	}

	public String getSubalterno() {
		return subalterno;
	}

	public void setSubalterno(String subalterno) {
		this.subalterno = subalterno;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Double getRendita() {
		return rendita;
	}

	public void setRendita(Double rendita) {
		this.rendita = rendita;
	}

	public Double getRedditoDomenicale() {
		return redditoDomenicale;
	}

	public void setRedditoDomenicale(Double redditoDomenicale) {
		this.redditoDomenicale = redditoDomenicale;
	}

	public Double getRedditoAgricolo() {
		return redditoAgricolo;
	}

	public void setRedditoAgricolo(Double redditoAgricolo) {
		this.redditoAgricolo = redditoAgricolo;
	}

	public Double getDimensione() {
		return dimensione;
	}

	public void setDimensione(Double dimensione) {
		this.dimensione = dimensione;
	}

	public Integer getCodImmobile() {
		return codImmobile;
	}

	public void setCodImmobile(Integer codImmobile) {
		this.codImmobile = codImmobile;
	}
	
	protected static final XMLFormat<DatiCatastaliVO> DATICATASTALI_XML = new XMLFormat<DatiCatastaliVO>(DatiCatastaliVO.class){
		
        public void write(DatiCatastaliVO dc_xml, OutputElement xml)throws XMLStreamException {
        }
                
        public void read(InputElement xml, DatiCatastaliVO dc_xml) throws XMLStreamException{
        	dc_xml.setCodDatiCatastali(xml.getAttribute("codDatiCatastali", 0));
        	dc_xml.setCodImmobile(xml.getAttribute("codImmobile", 0));
        	dc_xml.setCategoria(xml.getAttribute("categoria", ""));
        	dc_xml.setFoglio(xml.getAttribute("foglio", ""));
        	dc_xml.setParticella(xml.getAttribute("particella", ""));
        	dc_xml.setSubalterno(xml.getAttribute("subalterno", ""));
        	dc_xml.setDimensione(xml.getAttribute("dimensione", 0.0));
        	dc_xml.setRedditoAgricolo(xml.getAttribute("redditoAgricolo", 0.0));
        	dc_xml.setRedditoDomenicale(xml.getAttribute("redditoDomenicale", 0.0));
        	dc_xml.setRendita(xml.getAttribute("rendita", 0.0));
       }
        
   };

   public ContentValues getContentValue(){
	    ContentValues values = new ContentValues();
	    values.put("CODDATICATASTALI", getCodDatiCatastali());
 	    values.put("FOGLIO", getFoglio());
	    values.put("PARTICELLA", getParticella());
	    values.put("SUBALTERNO", getSubalterno());
 	    values.put("CATEGORIA", getCategoria());
	    values.put("RENDITA", getRendita());
	    values.put("REDDITODOMENICALE", getRedditoDomenicale());
	    values.put("REDDITOAGRARIO", getRedditoAgricolo());
	    values.put("DIMENSIONE", getDimensione());
	    values.put("CODIMMOBILE", getCodImmobile());
	    return values;
   }

	
}
