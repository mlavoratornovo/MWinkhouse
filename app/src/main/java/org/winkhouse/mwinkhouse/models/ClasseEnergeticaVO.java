package org.winkhouse.mwinkhouse.models;

import java.sql.SQLException;
import java.util.HashMap;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;
import android.content.ContentValues;
import android.database.Cursor;

public class ClasseEnergeticaVO {

	private Integer codClasseEnergetica = null;
	private String nome = null; 
	private String descrizione = null;
	private Integer ordine = null;
	
	public ClasseEnergeticaVO() {
		codClasseEnergetica = 0;
		nome = "";
		descrizione = "";
		ordine = 0;
	}

	public ClasseEnergeticaVO(Cursor c,HashMap column_list) throws SQLException {
		super();
		
		if (column_list != null){
			if (column_list.containsKey("CODCLASSEENERGETICA")){
				codClasseEnergetica = c.getInt(c.getColumnIndex("CODCLASSEENERGETICA"));
			}
		}else{
			codClasseEnergetica = c.getInt(c.getColumnIndex("CODCLASSEENERGETICA"));
		}
		if (column_list != null){
			if (column_list.containsKey("NOME")){
				nome = c.getString(c.getColumnIndex("NOME"));
			}
		}else{
			nome = c.getString(c.getColumnIndex("NOME"));
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

	
	public Integer getCodClasseEnergetica() {
		return codClasseEnergetica;
	}

	public void setCodClasseEnergetica(Integer codClasseEnergetica) {
		this.codClasseEnergetica = codClasseEnergetica;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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
		return getNome() + " - " + getDescrizione();
	}
	
	protected static final XMLFormat<ClasseEnergeticaVO> CLASSEENERGETICA_XML = new XMLFormat<ClasseEnergeticaVO>(ClasseEnergeticaVO.class){
		
        public void write(ClasseEnergeticaVO ce_xml, OutputElement xml)throws XMLStreamException {
        	xml.setAttribute("codClasseEnergetica", ce_xml.getCodClasseEnergetica());
        	xml.setAttribute("descrizione", ce_xml.getDescrizione());
        	xml.setAttribute("ordine", ce_xml.getOrdine());        	
        	xml.setAttribute("nome", ce_xml.getNome());        	
        }
        
        public void read(InputElement xml, ClasseEnergeticaVO ce_xml) throws XMLStreamException{
        	ce_xml.setCodClasseEnergetica(xml.getAttribute("codClasseEnergetica", 0));
        	ce_xml.setDescrizione(xml.getAttribute("descrizione", ""));
        	ce_xml.setOrdine(xml.getAttribute("ordine", 0));
        	ce_xml.setNome(xml.getAttribute("nome", ""));
       }
        
   };

	public ContentValues getContentValue(){
	    ContentValues values = new ContentValues();
	    values.put("CODCLASSEENERGETICA", getCodClasseEnergetica());
	    values.put("NOME", getNome());
  	    values.put("DESCRIZIONE", getDescrizione());
	    values.put("ORDINE", getOrdine());
	    return values;
	}

}
