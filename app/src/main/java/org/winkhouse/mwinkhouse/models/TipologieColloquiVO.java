package org.winkhouse.mwinkhouse.models;

import java.sql.ResultSet;
import java.sql.SQLException;

import javolution.xml.XMLFormat;
import javolution.xml.XMLSerializable;
import javolution.xml.stream.XMLStreamException;

public class TipologieColloquiVO implements XMLSerializable{

	private Integer codTipologiaColloquio = null;
	private String descrizione = null;
	
	public static int TYPE_RICERCA = 1; 
	public static int TYPE_VISITA = 2;
	public static int TYPE_GENERICO = 3;
	
	public TipologieColloquiVO() {
		codTipologiaColloquio = 0;
		descrizione = "";				
	}
	
	public TipologieColloquiVO(ResultSet rs) throws SQLException {
		codTipologiaColloquio = rs.getInt("CODTIPOLOGIACOLLOQUIO");
		descrizione = rs.getString("DESCRIZIONE");		
	}

	protected static final XMLFormat<TipologieColloquiVO> XML = new XMLFormat<TipologieColloquiVO>(TipologieColloquiVO.class) {
        public void write(TipologieColloquiVO tcVO, OutputElement xml) throws XMLStreamException {
            xml.setAttribute("codtipologiacolloqui",tcVO.getCodTipologiaColloquio());
            xml.setAttribute("descrizione",tcVO.getDescrizione());            
        }
        public void read(InputElement xml, TipologieColloquiVO tcVO) throws XMLStreamException {
            tcVO.setCodTipologiaColloquio(xml.getAttribute("codtipologiacolloqui", new Integer(0)));
            tcVO.setDescrizione(xml.getAttribute("descrizione", "tipologia colloquio"));
       }
   };

	
	public Integer getCodTipologiaColloquio() {
		return codTipologiaColloquio;
	}

	public void setCodTipologiaColloquio(Integer codTipologiaColloquio) {
		this.codTipologiaColloquio = codTipologiaColloquio;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Override
	public String toString() {		
		return getDescrizione();
	}


}
