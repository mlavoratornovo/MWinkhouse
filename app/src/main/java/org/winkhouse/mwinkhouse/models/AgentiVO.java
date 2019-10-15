package org.winkhouse.mwinkhouse.models;

import java.sql.ResultSet;
import java.sql.SQLException;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;
import android.content.ContentValues;

public class AgentiVO {

	private Integer codAgente = null;
	private String nome = null;
	private String cognome = null;
	private String indirizzo = null;
	private String provincia = null;
	private String cap = null;
	private String citta = null;
	
	public AgentiVO() {
		super();
		codAgente = 0;
		nome = "";
		cognome = "";
		indirizzo = "";
		provincia = "";
		cap = "";
		citta = "";		
	}

	public AgentiVO(ResultSet rs) throws SQLException {
		super();		
		codAgente = rs.getInt("CODAGENTE");
		nome = rs.getString("NOME");
		cognome = rs.getString("COGNOME");
		indirizzo = rs.getString("INDIRIZZO");
		provincia = rs.getString("PROVINCIA");
		cap = rs.getString("CAP");
		citta = rs.getString("CITTA");		
	}
	
	public Integer getCodAgente() {
		return codAgente;
	}

	public void setCodAgente(Integer codAgente) {
		this.codAgente = codAgente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	@Override
	public String toString() {
		return getNome() + " " + getCognome() + " - " + getCitta();
	}
	
	protected static final XMLFormat<AgentiVO> AGENTI_XML = new XMLFormat<AgentiVO>(AgentiVO.class){
		
        public void write(AgentiVO a_xml, OutputElement xml)throws XMLStreamException {
        }
                
        public void read(InputElement xml, AgentiVO a_xml) throws XMLStreamException{
        	a_xml.setCodAgente(xml.getAttribute("codAgente",0));
        	a_xml.setNome(xml.getAttribute("nome",""));
        	a_xml.setCognome(xml.getAttribute("cognome",""));
        	a_xml.setProvincia(xml.getAttribute("provincia", ""));
        	a_xml.setCap(xml.getAttribute("cap",""));
        	a_xml.setCitta(xml.getAttribute("citta",""));
        	a_xml.setIndirizzo(xml.getAttribute("indirizzo",""));
       }
        
   };
	
	public ContentValues getContentValue(){
	   ContentValues values = new ContentValues();
	   values.put("CODAGENTE", getCodAgente());
	   values.put("NOME", getNome());
	   values.put("COGNOME", getCognome());
	   values.put("INDIRIZZO", getIndirizzo());
	   values.put("PROVINCIA", getProvincia());
	   values.put("CAP", getCap());
	   values.put("CITTA", getCitta());
	   return values;
	}
   
}
