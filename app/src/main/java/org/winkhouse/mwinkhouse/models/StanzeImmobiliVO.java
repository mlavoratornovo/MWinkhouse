package org.winkhouse.mwinkhouse.models;

import java.sql.SQLException;
import java.util.HashMap;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;

import org.winkhouse.mwinkhouse.helpers.DataBaseHelper;
import org.winkhouse.mwinkhouse.models.columns.StanzeImmobiliColumnNames;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class StanzeImmobiliVO {

	private Integer codStanzeImmobili = null;
	private Integer codImmobile = null;
	private Integer codTipologiaStanza = null;
	private Integer mq = null;	
	private TipologiaStanzeVO tipologia = null;
	
	public StanzeImmobiliVO() {
		codStanzeImmobili = 0;
		codImmobile = 0;
		codTipologiaStanza = 0;
		mq = 0;	
	}

	public StanzeImmobiliVO(Cursor c,HashMap column_list) throws SQLException {
		
		if (column_list != null){
			if (column_list.containsKey(StanzeImmobiliColumnNames.CODSTANZEIMMOBILI)){
				codStanzeImmobili = c.getInt(c.getColumnIndex(StanzeImmobiliColumnNames.CODSTANZEIMMOBILI));
			}
		}else{
			codStanzeImmobili = c.getInt(c.getColumnIndex(StanzeImmobiliColumnNames.CODSTANZEIMMOBILI));
		}

		if (column_list != null){
			if (column_list.containsKey(StanzeImmobiliColumnNames.CODIMMOBILE)){
				codImmobile = c.getInt(c.getColumnIndex(StanzeImmobiliColumnNames.CODIMMOBILE));
			}
		}else{
			codImmobile = c.getInt(c.getColumnIndex(StanzeImmobiliColumnNames.CODIMMOBILE));
		}
		
		if (column_list != null){
			if (column_list.containsKey(StanzeImmobiliColumnNames.CODTIPOLOGIASTANZA)){
				codTipologiaStanza = c.getInt(c.getColumnIndex(StanzeImmobiliColumnNames.CODTIPOLOGIASTANZA));
			}
		}else{
			codTipologiaStanza = c.getInt(c.getColumnIndex(StanzeImmobiliColumnNames.CODTIPOLOGIASTANZA));
		}
		
		if (column_list != null){
			if (column_list.containsKey(StanzeImmobiliColumnNames.MQ)){
				mq = c.getInt(c.getColumnIndex(StanzeImmobiliColumnNames.MQ));
			}
		}else{
			mq = c.getInt(c.getColumnIndex(StanzeImmobiliColumnNames.MQ));
		}

	}

	public Integer getMq() {
		return mq;
	}

	public void setMq(Integer mq) {
		this.mq = mq;
	}

	public TipologiaStanzeVO getTipologia(Context c,HashMap column_list) {
		
		if (tipologia == null){
			if (codTipologiaStanza != null && codTipologiaStanza != 0){
				   DataBaseHelper dbh = new DataBaseHelper(c,DataBaseHelper.NONE_DB);
				   tipologia = dbh.getTiplogiaStanzeById(codTipologiaStanza, column_list);
				   dbh.close();
			   }

		}
		return tipologia;
	}

	public void setTipologia(TipologiaStanzeVO tipologia) {
		this.tipologia = tipologia;
	}

	public Integer getCodStanzeImmobili() {
		return codStanzeImmobili;
	}

	public void setCodStanzeImmobili(Integer codStanzeImmobili) {
		this.codStanzeImmobili = codStanzeImmobili;
	}

	public Integer getCodImmobile() {
		return codImmobile;
	}

	public void setCodImmobile(Integer codImmobile) {
		this.codImmobile = codImmobile;
	}

	public Integer getCodTipologiaStanza() {
		return codTipologiaStanza;
	}

	public void setCodTipologiaStanza(Integer codTipologiaStanza) {
		this.codTipologiaStanza = codTipologiaStanza;
	}
	
	protected static final XMLFormat<StanzeImmobiliVO> STANZEIMMOBILI_XML = new XMLFormat<StanzeImmobiliVO>(StanzeImmobiliVO.class){
		
        public void write(StanzeImmobiliVO a_xml, OutputElement xml)throws XMLStreamException {
        }
                
        public void read(InputElement xml, StanzeImmobiliVO a_xml) throws XMLStreamException{
        	a_xml.setCodStanzeImmobili(xml.getAttribute("codStanzeImmobili", 0));
        	a_xml.setCodTipologiaStanza(xml.getAttribute("codTipologiaStanza", 0));
        	a_xml.setCodImmobile(xml.getAttribute("codImmobile", 0));
        	a_xml.setMq(xml.getAttribute("mq", 0));
       }
        
   };

	public ContentValues getContentValue(){
	    ContentValues values = new ContentValues();
	    values.put(StanzeImmobiliColumnNames.CODSTANZEIMMOBILI, getCodStanzeImmobili());
  	    values.put(StanzeImmobiliColumnNames.CODIMMOBILE, getCodImmobile());
  	    values.put(StanzeImmobiliColumnNames.CODTIPOLOGIASTANZA, getCodTipologiaStanza());
  	    values.put(StanzeImmobiliColumnNames.MQ, getMq());
	    return values;
	}


}
