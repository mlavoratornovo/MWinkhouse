package org.winkhouse.mwinkhouse.models;

import java.sql.SQLException;
import java.util.HashMap;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;

import org.winkhouse.mwinkhouse.models.columns.ColloquiAgentiColumnNames;

import android.content.ContentValues;
import android.database.Cursor;

public class ColloquiAgentiVO implements ColloquiAgentiColumnNames{

	private Integer codColloquioAgenti = null;
	private Integer codColloquio = null;
	private Integer codAgente = null;
	private String commento = null;
	
	public ColloquiAgentiVO() {
		codColloquioAgenti = 0;
		codColloquio = 0;
		codAgente = 0;
		commento = "";
	}

	public ColloquiAgentiVO(Cursor c,HashMap column_list) throws SQLException {
		
		if (column_list != null){
			if (column_list.containsKey(CODCOLLOQUIOAGENTE)){
				codColloquioAgenti = c.getInt(c.getColumnIndex(CODCOLLOQUIOAGENTE));
			}
		}else{
			codColloquioAgenti = c.getInt(c.getColumnIndex(CODCOLLOQUIOAGENTE));
		}

		if (column_list != null){
			if (column_list.containsKey(CODCOLLOQUIO)){
				codColloquio = c.getInt(c.getColumnIndex(CODCOLLOQUIO));
			}
		}else{
			codColloquio = c.getInt(c.getColumnIndex(CODCOLLOQUIO));
		}

		if (column_list != null){
			if (column_list.containsKey(CODAGENTE)){
				codAgente = c.getInt(c.getColumnIndex(CODAGENTE));
			}
		}else{
			codAgente = c.getInt(c.getColumnIndex(CODAGENTE));
		}

		if (column_list != null){
			if (column_list.containsKey(COMMENTO)){
				commento = c.getString(c.getColumnIndex(COMMENTO));
			}
		}else{
			commento = c.getString(c.getColumnIndex(COMMENTO));
		}

	}	
	
	public Integer getCodColloquioAgenti() {
		return codColloquioAgenti;
	}

	public void setCodColloquioAgenti(Integer codColloquioAgenti) {
		this.codColloquioAgenti = codColloquioAgenti;
	}

	public Integer getCodColloquio() {
		return codColloquio;
	}

	public void setCodColloquio(Integer codColloquio) {
		this.codColloquio = codColloquio;
	}

	public Integer getCodAgente() {
		return codAgente;
	}

	public void setCodAgente(Integer codAgente) {
		this.codAgente = codAgente;
	}

	public String getCommento() {
		return commento;
	}

	public void setCommento(String commento) {
		this.commento = commento;
	}
	
	protected static final XMLFormat<ColloquiAgentiVO> COLLOQUIAGENTI_XML = new XMLFormat<ColloquiAgentiVO>(ColloquiAgentiVO.class){
		
        public void write(ColloquiAgentiVO i_xml, OutputElement xml)throws XMLStreamException {
        	
        	xml.setAttribute("codColloquio", i_xml.getCodColloquio());
        	xml.setAttribute("commento", i_xml.getCommento());
        	xml.setAttribute("codColloquioAgenti", i_xml.getCodColloquioAgenti());
        	xml.setAttribute("codAgente", i_xml.getCodAgente());
        	
        }
                
        public void read(InputElement xml, ColloquiAgentiVO i_xml) throws XMLStreamException{
        	
        	i_xml.setCodColloquio(xml.getAttribute("codColloquio", 0));
        	i_xml.setCommento(xml.getAttribute("commento", ""));
        	i_xml.setCodColloquioAgenti(xml.getAttribute("codColloquioAgenti", 0));
        	i_xml.setCodAgente(xml.getAttribute("codAgente", 0));
        	
        }
   };

   public ContentValues getContentValue(){
	    ContentValues values = new ContentValues();
	    values.put(CODCOLLOQUIO, getCodColloquio());
	    values.put(COMMENTO, getCommento());
	    values.put(CODCOLLOQUIOAGENTE, getCodColloquioAgenti());
	    values.put(CODAGENTE, getCodAgente());
	    return values;
   }


}
