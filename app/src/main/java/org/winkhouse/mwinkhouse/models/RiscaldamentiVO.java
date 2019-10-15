package org.winkhouse.mwinkhouse.models;

import java.sql.SQLException;
import java.util.HashMap;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;
import android.content.ContentValues;
import android.database.Cursor;

public class RiscaldamentiVO {

	private Integer codRiscaldamento = null;
	private String descrizione = null;		
	
	public RiscaldamentiVO() {
		codRiscaldamento = 0;
		descrizione = "";				
	}

	public RiscaldamentiVO(Cursor c,HashMap column_list) throws SQLException {
		super();
		
		if (column_list != null){
			if (column_list.containsKey("CODRISCALDAMENTO")){
				codRiscaldamento = c.getInt(c.getColumnIndex("CODRISCALDAMENTO"));
			}
		}else{
			codRiscaldamento = c.getInt(c.getColumnIndex("CODRISCALDAMENTO"));
		}
		if (column_list != null){
			if (column_list.containsKey("DESCRIZIONE")){
				descrizione = c.getString(c.getColumnIndex("DESCRIZIONE"));
			}
		}else{
			descrizione = c.getString(c.getColumnIndex("DESCRIZIONE"));
		}
	}
	
	public Integer getCodRiscaldamento() {
		return codRiscaldamento;
	}

	public void setCodRiscaldamento(Integer codRiscaldamento) {
		this.codRiscaldamento = codRiscaldamento;
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
	
	protected static final XMLFormat<RiscaldamentiVO> RISCALDAMENTI_XML = new XMLFormat<RiscaldamentiVO>(RiscaldamentiVO.class){
		
        public void write(RiscaldamentiVO ta_xml, OutputElement xml)throws XMLStreamException {
        	xml.setAttribute("codRiscaldamento", ta_xml.getCodRiscaldamento());
			xml.setAttribute("descrizione", ta_xml.getDescrizione());			        	
        }
        
        public void read(InputElement xml, RiscaldamentiVO ta_xml) throws XMLStreamException{
        	ta_xml.setCodRiscaldamento(xml.getAttribute("codRiscaldamento", 0));
        	ta_xml.setDescrizione(xml.getAttribute("descrizione", ""));
       }
        
   };
   
	public ContentValues getContentValue(){
	    ContentValues values = new ContentValues();
	    values.put("CODRISCALDAMENTO", getCodRiscaldamento());
  	    values.put("DESCRIZIONE", getDescrizione());
	    return values;
	}



}
