package org.winkhouse.mwinkhouse.models;

import java.sql.SQLException;
import java.util.HashMap;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;

import org.winkhouse.mwinkhouse.models.columns.TipologieStanzeColumnNames;

import android.content.ContentValues;
import android.database.Cursor;

public class TipologiaStanzeVO {

	private Integer codTipologiaStanza = null;
	private String descrizione = null;
	
	public TipologiaStanzeVO() {
		codTipologiaStanza = 0;
		descrizione = "";		
	}
	
	public TipologiaStanzeVO(Cursor c,HashMap column_list) throws SQLException {
		
		if (column_list != null){
			if (column_list.containsKey(TipologieStanzeColumnNames.CODTIPOLOGIASTANZA)){
				codTipologiaStanza = c.getInt(c.getColumnIndex(TipologieStanzeColumnNames.CODTIPOLOGIASTANZA));
			}
		}else{
			codTipologiaStanza = c.getInt(c.getColumnIndex(TipologieStanzeColumnNames.CODTIPOLOGIASTANZA));
		}		

		if (column_list != null){
			if (column_list.containsKey(TipologieStanzeColumnNames.DESCRIZIONE)){
				descrizione = c.getString(c.getColumnIndex(TipologieStanzeColumnNames.DESCRIZIONE));
			}
		}else{
			descrizione = c.getString(c.getColumnIndex(TipologieStanzeColumnNames.DESCRIZIONE));
		}		

				
	}

	public Integer getCodTipologiaStanza() {
		return codTipologiaStanza;
	}

	public void setCodTipologiaStanza(Integer codTipologiaStanza) {
		this.codTipologiaStanza = codTipologiaStanza;
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

	protected static final XMLFormat<TipologiaStanzeVO> TIPISTANZE_XML = new XMLFormat<TipologiaStanzeVO>(TipologiaStanzeVO.class){
		
        public void write(TipologiaStanzeVO ts_xml, OutputElement xml)throws XMLStreamException {
        }
        
        public void read(InputElement xml, TipologiaStanzeVO ts_xml) throws XMLStreamException{
        	ts_xml.setCodTipologiaStanza(xml.getAttribute("codTipologiaStanza", 0));
        	ts_xml.setDescrizione(xml.getAttribute("descrizione", ""));        	
       }
        
   };

   public ContentValues getContentValue(){
	    ContentValues values = new ContentValues();
	    values.put("CODTIPOLOGIASTANZA", getCodTipologiaStanza());
	    values.put("DESCRIZIONE", getDescrizione());
	    return values;
   }


}
