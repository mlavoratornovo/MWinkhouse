package org.winkhouse.mwinkhouse.models;

import java.sql.SQLException;
import java.util.HashMap;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;
import android.content.ContentValues;
import android.database.Cursor;

public class ClassiClientiVO {

	private Integer codClasseCliente = null;
	private String descrizione = null;
	private Integer ordine = null;
	
	public ClassiClientiVO() {
		codClasseCliente = 0;
		descrizione = "";
		ordine = 0;				
	}

	public ClassiClientiVO(Cursor c,HashMap column_list) throws SQLException {
		super();
		
		if (column_list != null){
			if (column_list.containsKey("CODCLASSECLIENTE")){
				codClasseCliente = c.getInt(c.getColumnIndex("CODCLASSECLIENTE"));
			}
		}else{
			codClasseCliente = c.getInt(c.getColumnIndex("CODCLASSECLIENTE"));
		}
		if (column_list != null){
			if (column_list.containsKey("DESCRIZIONE")){
				descrizione = c.getString(c.getColumnIndex("DESCRIZIONE"));
			}
		}else{
			descrizione = c.getString(c.getColumnIndex("DESCRIZIONE"));
		}
		if (column_list != null){
			if (column_list.containsKey("ORDINE")){
				ordine = c.getInt(c.getColumnIndex("ORDINE"));
			}
		}else{
			ordine = c.getInt(c.getColumnIndex("ORDINE"));
		}
		
	}	

	public Integer getCodClasseCliente() {
		return codClasseCliente;
	}

	public void setCodClasseCliente(Integer codClasseCliente) {
		this.codClasseCliente = codClasseCliente;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Integer getOrdine() {
		return ordine;
	}

	public void setOrdine(Integer ordine) {
		this.ordine = ordine;
	}

	@Override
	public String toString() {
		return getDescrizione();
	}
	
	protected static final XMLFormat<ClassiClientiVO> CLASSICLIENTI_XML = new XMLFormat<ClassiClientiVO>(ClassiClientiVO.class){
		
        public void write(ClassiClientiVO ta_xml, OutputElement xml)throws XMLStreamException {
        	xml.setAttribute("codClasseCliente", ta_xml.getCodClasseCliente());
			xml.setAttribute("descrizione", ta_xml.getDescrizione());
			xml.setAttribute("ordine", ta_xml.getOrdine());        	        	
        }
        
        public void read(InputElement xml, ClassiClientiVO ta_xml) throws XMLStreamException{
        	ta_xml.setCodClasseCliente(xml.getAttribute("codClasseCliente", 0));
        	ta_xml.setDescrizione(xml.getAttribute("descrizione", ""));
        	ta_xml.setOrdine(xml.getAttribute("ordine", 0));
       }
        
   };

   
	public ContentValues getContentValue(){
	    ContentValues values = new ContentValues();
	    values.put("CODCLASSECLIENTE", getCodClasseCliente());
  	    values.put("DESCRIZIONE", getDescrizione());
	    values.put("ORDINE", getOrdine());
	    return values;
	}
   
}
