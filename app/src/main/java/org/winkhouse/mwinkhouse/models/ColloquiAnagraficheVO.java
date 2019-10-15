package org.winkhouse.mwinkhouse.models;
import java.sql.SQLException;
import java.util.HashMap;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;

import org.winkhouse.mwinkhouse.models.columns.ColloquiAnagraficheColumnNames;

import android.content.ContentValues;
import android.database.Cursor;

public class ColloquiAnagraficheVO implements ColloquiAnagraficheColumnNames{

	private Integer codColloquioAnagrafiche = null;
	private Integer codColloquio = null;
	private Integer codAnagrafica = null;
	private String commento = null;
	
	public ColloquiAnagraficheVO() {
		codColloquioAnagrafiche = 0;
		codColloquio = 0;
		codAnagrafica = 0;
		commento = "";
	}

	public ColloquiAnagraficheVO(Cursor c,HashMap column_list) throws SQLException {
		
		if (column_list != null){
			if (column_list.containsKey(CODCOLLOQUIANAGRAFICHE)){
				codColloquioAnagrafiche = c.getInt(c.getColumnIndex(CODCOLLOQUIANAGRAFICHE));
			}
		}else{
			codColloquioAnagrafiche = c.getInt(c.getColumnIndex(CODCOLLOQUIANAGRAFICHE));
		}
		if (column_list != null){
			if (column_list.containsKey(CODCOLLOQUIO)){
				codColloquio = c.getInt(c.getColumnIndex(CODCOLLOQUIO));
			}
		}else{
			codColloquio = c.getInt(c.getColumnIndex(CODCOLLOQUIO));
		}
		if (column_list != null){
			if (column_list.containsKey(CODANAGRAFICA)){
				codAnagrafica = c.getInt(c.getColumnIndex(CODANAGRAFICA));
			}
		}else{
			codAnagrafica = c.getInt(c.getColumnIndex(CODANAGRAFICA));
		}

		if (column_list != null){
			if (column_list.containsKey(DESCRIZIONE)){
				commento = c.getString(c.getColumnIndex(DESCRIZIONE));
			}
		}else{
			commento = c.getString(c.getColumnIndex(DESCRIZIONE));
		}

	}
	
	public Integer getCodColloquioAnagrafiche() {
		return codColloquioAnagrafiche;
	}

	public void setCodColloquioAnagrafiche(Integer codColloquioAnagrafiche) {
		this.codColloquioAnagrafiche = codColloquioAnagrafiche;
	}

	public String getCommento() {
		return commento;
	}

	public void setCommento(String commento) {
		this.commento = commento;
	}

	public Integer getCodColloquio() {
		return codColloquio;
	}

	public void setCodColloquio(Integer codColloquio) {
		this.codColloquio = codColloquio;
	}

	public Integer getCodAnagrafica() {
		return codAnagrafica;
	}

	public void setCodAnagrafica(Integer codAnagrafica) {
		this.codAnagrafica = codAnagrafica;
	}

	protected static final XMLFormat<ColloquiAnagraficheVO> COLLOQUIANAGRAFICHE_XML = new XMLFormat<ColloquiAnagraficheVO>(ColloquiAnagraficheVO.class){
		
        public void write(ColloquiAnagraficheVO i_xml, OutputElement xml)throws XMLStreamException {
        	
        	xml.setAttribute("codColloquio", i_xml.getCodColloquio());
        	xml.setAttribute("commento", i_xml.getCommento());
        	xml.setAttribute("codColloquioAnagrafiche", i_xml.getCodColloquioAnagrafiche());
        	xml.setAttribute("codAnagrafica", i_xml.getCodAnagrafica());
        	
        }
                
        public void read(InputElement xml, ColloquiAnagraficheVO i_xml) throws XMLStreamException{
        	
        	i_xml.setCodColloquio(xml.getAttribute("codColloquio", 0));
        	i_xml.setCommento(xml.getAttribute("commento", ""));
        	i_xml.setCodColloquioAnagrafiche(xml.getAttribute("codColloquioAnagrafiche", 0));
        	i_xml.setCodAnagrafica(xml.getAttribute("codAnagrafica", 0));
        	
        }
   };

   public ContentValues getContentValue(){
	    ContentValues values = new ContentValues();
	    values.put(CODCOLLOQUIO, getCodColloquio());
	    values.put(DESCRIZIONE, getCommento());
	    values.put(CODCOLLOQUIANAGRAFICHE, getCodColloquioAnagrafiche());
	    values.put(CODANAGRAFICA, getCodAnagrafica());
	    return values;
   }


}
