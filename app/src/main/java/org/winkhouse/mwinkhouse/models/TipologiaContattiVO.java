package org.winkhouse.mwinkhouse.models;

import java.sql.SQLException;
import java.util.HashMap;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;
import android.content.ContentValues;
import android.database.Cursor;

public class TipologiaContattiVO {

	private Integer codTipologiaContatto = null;
	private String descrizione = null;
	
	public TipologiaContattiVO() {
		codTipologiaContatto = 0;
		descrizione = "";				
	}

	public TipologiaContattiVO(Cursor c,HashMap column_list) throws SQLException {
		if (column_list != null){
			if (column_list.containsKey("CODTIPOLOGIACONTATTO")){
				codTipologiaContatto = c.getInt(c.getColumnIndex("CODTIPOLOGIACONTATTO"));
			}
		}else{
			codTipologiaContatto = c.getInt(c.getColumnIndex("CODTIPOLOGIACONTATTO"));
		}
		if (column_list != null){
			if (column_list.containsKey("DESCRIZIONE")){
				descrizione = c.getString(c.getColumnIndex("DESCRIZIONE"));
			}
		}else{
			descrizione = c.getString(c.getColumnIndex("DESCRIZIONE"));
		}
	}
	
	public Integer getCodTipologiaContatto() {
		return codTipologiaContatto;
	}

	public void setCodTipologiaContatto(Integer codTipologiaContatto) {
		this.codTipologiaContatto = codTipologiaContatto;
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

	protected static final XMLFormat<TipologiaContattiVO> TIPOCONTATTI_XML = new XMLFormat<TipologiaContattiVO>(TipologiaContattiVO.class){
		
        public void write(TipologiaContattiVO tc_xml, OutputElement xml)throws XMLStreamException {
        	xml.setAttribute("codTipologiaContatto", tc_xml.getCodTipologiaContatto());
			xml.setAttribute("descrizione", tc_xml.getDescrizione());			        	        	
        }
        
        public void read(InputElement xml, TipologiaContattiVO tc_xml) throws XMLStreamException{
        	tc_xml.setCodTipologiaContatto(xml.getAttribute("codTipologiaContatto", 0));
        	tc_xml.setDescrizione(xml.getAttribute("descrizione", ""));
       }
        
   };
   
   public ContentValues getContentValue(){
	    ContentValues values = new ContentValues();
	    values.put("CODTIPOLOGIACONTATTO", getCodTipologiaContatto());
 	    values.put("DESCRIZIONE", getDescrizione());
	    return values;
  }
   

}
