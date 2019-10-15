package org.winkhouse.mwinkhouse.models;

import java.sql.SQLException;
import java.util.HashMap;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;

import org.winkhouse.mwinkhouse.helpers.DataBaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class ContattiVO {

	private Integer codContatto = null;
	private String contatto = null;
	private String descrizione = null;
	private Integer codTipologiaContatto = null;
	private Integer codAnagrafica = null;
	private Integer codAgente = null;
	private TipologiaContattiVO tipologiaContattiVO = null;
	
	public ContattiVO() {
		codContatto = 0;
		contatto = "";
		descrizione = "";
		codTipologiaContatto = 0;
		codAnagrafica = 0;		
		codAgente = 0;
	}
	
	public ContattiVO(Cursor c,HashMap column_list) throws SQLException {
		super();
		
		if (column_list != null){
			if (column_list.containsKey("CODCONTATTO")){
				codContatto = c.getInt(c.getColumnIndex("CODCONTATTO"));
			}
		}else{
			codContatto = c.getInt(c.getColumnIndex("CODCONTATTO"));
		}
		if (column_list != null){
			if (column_list.containsKey("CONTATTO")){
				contatto = c.getString(c.getColumnIndex("CONTATTO"));
			}
		}else{
			contatto = c.getString(c.getColumnIndex("CONTATTO"));
		}
		if (column_list != null){
			if (column_list.containsKey("DESCRIZIONE")){
				descrizione = c.getString(c.getColumnIndex("DESCRIZIONE"));
			}
		}else{
			descrizione = c.getString(c.getColumnIndex("DESCRIZIONE"));
		}
		if (column_list != null){
			if (column_list.containsKey("CODTIPOLOGIACONTATTO")){
				codTipologiaContatto = c.getInt(c.getColumnIndex("CODTIPOLOGIACONTATTO"));
			}
		}else{
			codTipologiaContatto = c.getInt(c.getColumnIndex("CODTIPOLOGIACONTATTO"));
		}
		if (column_list != null){
			if (column_list.containsKey("CODANAGRAFICA")){
				codAnagrafica = c.getInt(c.getColumnIndex("CODANAGRAFICA"));
			}
		}else{
			codAnagrafica = c.getInt(c.getColumnIndex("CODANAGRAFICA"));
		}
		if (column_list != null){
			if (column_list.containsKey("CODAGENTE")){
				codAgente = c.getInt(c.getColumnIndex("CODAGENTE"));
			}
		}else{
			codAgente = c.getInt(c.getColumnIndex("CODAGENTE"));
		}
		
	}	

	public Integer getCodContatto() {
		return codContatto;
	}

	public void setCodContatto(Integer codContatto) {
		this.codContatto = codContatto;
	}

	public String getContatto() {
		return contatto;
	}

	public void setContatto(String contatto) {
		this.contatto = contatto;
	}
	
	public Integer getCodAnagrafica() {
		return codAnagrafica;
	}

	public void setCodAnagrafica(Integer codAnagrafica) {
		this.codAnagrafica = codAnagrafica;
	}

	public Integer getCodTipologiaContatto() {
		return codTipologiaContatto;
	}

	public void setCodTipologiaContatto(Integer codTipologiaContatto) {
		this.codTipologiaContatto = codTipologiaContatto;
	}

	public Integer getCodAgente() {
		return codAgente;
	}

	public void setCodAgente(Integer codAgente) {
		this.codAgente = codAgente;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	protected static final XMLFormat<ContattiVO> CONTATTI_XML = new XMLFormat<ContattiVO>(ContattiVO.class){
		
        public void write(ContattiVO c_xml, OutputElement xml)throws XMLStreamException {
        	xml.setAttribute("codContatto", c_xml.getCodContatto());
			xml.setAttribute("descrizione", c_xml.getDescrizione());
			xml.setAttribute("codTipologiaContatto", c_xml.getCodTipologiaContatto());
			xml.setAttribute("codAnagrafica", c_xml.getCodAnagrafica());
			xml.setAttribute("codAgente", c_xml.getCodAgente());
			xml.setAttribute("contatto", c_xml.getContatto());
        }
                
        public void read(InputElement xml, ContattiVO c_xml) throws XMLStreamException{
        	c_xml.setCodContatto(xml.getAttribute("codContatto", 0));
        	c_xml.setCodAgente(xml.getAttribute("codAgente", 0));
        	c_xml.setCodAnagrafica(xml.getAttribute("codAnagrafica", 0));
        	c_xml.setCodTipologiaContatto(xml.getAttribute("codTipologiaContatto", 0));
        	c_xml.setDescrizione(xml.getAttribute("descrizione", ""));
        	c_xml.setContatto(xml.getAttribute("contatto", ""));
       }
        
   };

   public ContentValues getContentValue(){
	    ContentValues values = new ContentValues();
	    values.put("CODCONTATTO", getCodContatto());
  	    values.put("DESCRIZIONE", getDescrizione());
	    values.put("CONTATTO", getContatto());
	    values.put("CODTIPOLOGIACONTATTO", getCodTipologiaContatto());
  	    values.put("CODANAGRAFICA", getCodAnagrafica());
	    values.put("CODAGENTE", getCodAgente());
	    return values;
	}

   public TipologiaContattiVO getTipologiaContattiVO(Context c,HashMap column_list) {
	   if (tipologiaContattiVO == null){
		   if (codTipologiaContatto != null && codTipologiaContatto != 0){
			   DataBaseHelper dbh = new DataBaseHelper(c,DataBaseHelper.NONE_DB);
			   tipologiaContattiVO = dbh.getTiplogiaContattoById(codTipologiaContatto, null);
			   dbh.close();
		   }
					   
	   }
	   return tipologiaContattiVO;
   }

   public void setTipologiaContattiVO(TipologiaContattiVO tipologiaContattiVO) {
	   this.tipologiaContattiVO = tipologiaContattiVO;
   }

	
}
