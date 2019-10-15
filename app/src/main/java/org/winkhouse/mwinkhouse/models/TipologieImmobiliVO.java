package org.winkhouse.mwinkhouse.models;

import java.sql.SQLException;
import java.util.HashMap;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;
import android.content.ContentValues;
import android.database.Cursor;


public class TipologieImmobiliVO {

	private Integer codTipologiaImmobile = null;
	private String descrizione = null;	
	
	public TipologieImmobiliVO() {
		codTipologiaImmobile = 0;
		descrizione = "";		
	}
	
	public TipologieImmobiliVO(Cursor c,HashMap column_list) throws SQLException {
		super();
		
		if (column_list != null){
			if (column_list.containsKey("CODTIPOLOGIAIMMOBILE")){
				codTipologiaImmobile = c.getInt(c.getColumnIndex("CODTIPOLOGIAIMMOBILE"));
			}
		}else{
			codTipologiaImmobile = c.getInt(c.getColumnIndex("CODTIPOLOGIAIMMOBILE"));
		}
		if (column_list != null){
			if (column_list.containsKey("DESCRIZIONE")){
				descrizione = c.getString(c.getColumnIndex("DESCRIZIONE"));
			}
		}else{
			descrizione = c.getString(c.getColumnIndex("DESCRIZIONE"));
		}
	}
	
	public Integer getCodTipologiaImmobile() {
		return codTipologiaImmobile;
	}

	public void setCodTipologiaImmobile(Integer codTipologiaImmobile) {
		this.codTipologiaImmobile = codTipologiaImmobile;
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

	protected static final XMLFormat<TipologieImmobiliVO> TIPIIMMOBILI_XML = new XMLFormat<TipologieImmobiliVO>(TipologieImmobiliVO.class){
		
        public void write(TipologieImmobiliVO ti_xml, OutputElement xml)throws XMLStreamException {
        	xml.setAttribute("codTipologiaImmobile", ti_xml.getCodTipologiaImmobile());
			xml.setAttribute("descrizione", ti_xml.getDescrizione());			        	
        }
        
        public void read(InputElement xml, TipologieImmobiliVO ti_xml) throws XMLStreamException{
        	ti_xml.setCodTipologiaImmobile(xml.getAttribute("codTipologiaImmobile", 0));
        	ti_xml.setDescrizione(xml.getAttribute("descrizione", ""));        	
       }
        
   };
   
   public ContentValues getContentValue(){
	    ContentValues values = new ContentValues();
	    values.put("CODTIPOLOGIAIMMOBILE", getCodTipologiaImmobile());
	    values.put("DESCRIZIONE", getDescrizione());
	    return values;
  }


}
