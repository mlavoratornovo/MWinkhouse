package org.winkhouse.mwinkhouse.models;

import java.sql.SQLException;
import java.util.HashMap;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;
import android.content.ContentValues;
import android.database.Cursor;

public class StatoConservativoVO {

	private Integer codStatoConservativo = null;
	private String descrizione = null;		
	
	public StatoConservativoVO() {
		codStatoConservativo = 0;
		descrizione = "";		
	}

	public StatoConservativoVO(Cursor c,HashMap column_list) throws SQLException {
		super();
		
		if (column_list != null){
			if (column_list.containsKey("CODSTATOCONSERVATIVO")){
				codStatoConservativo = c.getInt(c.getColumnIndex("CODSTATOCONSERVATIVO"));
			}
		}else{
			codStatoConservativo = c.getInt(c.getColumnIndex("CODSTATOCONSERVATIVO"));
		}
		if (column_list != null){
			if (column_list.containsKey("DESCRIZIONE")){
				descrizione = c.getString(c.getColumnIndex("DESCRIZIONE"));
			}
		}else{
			descrizione = c.getString(c.getColumnIndex("DESCRIZIONE"));
		}
	}

	public Integer getCodStatoConservativo() {
		return codStatoConservativo;
	}

	public void setCodStatoConservativo(Integer codStatoConservativo) {
		this.codStatoConservativo = codStatoConservativo;
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
	
	protected static final XMLFormat<StatoConservativoVO> STATOCONSERVATIVO_XML = new XMLFormat<StatoConservativoVO>(StatoConservativoVO.class){
		
        public void write(StatoConservativoVO sc_xml, OutputElement xml)throws XMLStreamException {
        	xml.setAttribute("codStatoConservativo", sc_xml.getCodStatoConservativo());
			xml.setAttribute("descrizione", sc_xml.getDescrizione());			        	        	
        }
        
        public void read(InputElement xml, StatoConservativoVO sc_xml) throws XMLStreamException{
        	sc_xml.setCodStatoConservativo(xml.getAttribute("codStatoConservativo", 0));
        	sc_xml.setDescrizione(xml.getAttribute("descrizione", ""));
       }
        
   };
   
   public ContentValues getContentValue(){
	    ContentValues values = new ContentValues();
	    values.put("CODSTATOCONSERVATIVO", getCodStatoConservativo());
  	    values.put("DESCRIZIONE", getDescrizione());
	    return values;
   }



}
