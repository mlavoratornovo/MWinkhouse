package org.winkhouse.mwinkhouse.models;

import java.sql.ResultSet;
import java.sql.SQLException;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;
import android.content.ContentValues;

public class AbbinamentiVO {

	private Integer codAbbinamento = null;
	private Integer codImmobile = null;
	private Integer codAnagrafica = null;
	
	public AbbinamentiVO(){
		super();
		codAbbinamento = 0;
		codImmobile = 0;
		codAnagrafica = 0;
	}
	
	public AbbinamentiVO(ResultSet rs) throws SQLException {
		super();		
		codAbbinamento = rs.getInt("CODABBINAMENTO");
		codImmobile = rs.getInt("CODIMMOBILE");
		codAnagrafica = rs.getInt("CODANAGRAFICA");
	}

	public Integer getCodAbbinamento() {
		return codAbbinamento;
	}

	public void setCodAbbinamento(Integer codAbbinamento) {
		this.codAbbinamento = codAbbinamento;
	}

	public Integer getCodImmobile() {
		return codImmobile;
	}

	public void setCodImmobile(Integer codImmobile) {
		this.codImmobile = codImmobile;
	}

	public Integer getCodAnagrafica() {
		return codAnagrafica;
	}

	public void setCodAnagrafica(Integer codAnagrafica) {
		this.codAnagrafica = codAnagrafica;
	}
	
	protected static final XMLFormat<AbbinamentiVO> ABBINAMENTI_XML = new XMLFormat<AbbinamentiVO>(AbbinamentiVO.class){
		
        public void write(AbbinamentiVO afa_xml, OutputElement xml)throws XMLStreamException {
        }
                
        public void read(InputElement xml, AbbinamentiVO c_xml) throws XMLStreamException{
        	c_xml.setCodAbbinamento(xml.getAttribute("codAbbinamento", 0));
        	c_xml.setCodAnagrafica(xml.getAttribute("codAnagrafica", 0));
        	c_xml.setCodImmobile(xml.getAttribute("codImmobile",0));
       }
        
   };
   
   public ContentValues getContentValue(){
	   ContentValues values = new ContentValues();
	   values.put("CODABBINAMENTO", getCodAbbinamento());
	   values.put("CODIMMOBILE", getCodImmobile());
	   values.put("CODANAGRAFICA", getCodAnagrafica());
	   return values;
   }

	
}
