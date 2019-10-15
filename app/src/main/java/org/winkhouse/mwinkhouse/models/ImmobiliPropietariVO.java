package org.winkhouse.mwinkhouse.models;

import java.sql.ResultSet;
import java.sql.SQLException;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;
import android.content.ContentValues;

public class ImmobiliPropietariVO {

	private Integer codImmobile = null;
	private Integer codAnagrafica = null;
	private Integer keyCode = null;
	
	public ImmobiliPropietariVO() {
		// TODO Auto-generated constructor stub
	}

	public ImmobiliPropietariVO(ResultSet rs) throws SQLException{
		codImmobile = rs.getInt("CODIMMOBILE");
		codAnagrafica = rs.getInt("CODANAGRAFICA");
	}
	
	public Integer getKeyCode(){
		if (keyCode == null){
			String tmp = String.valueOf(codImmobile) + String.valueOf(codAnagrafica);
			keyCode = Integer.valueOf(tmp);
		}
		return keyCode;
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
	
	protected static final XMLFormat<ImmobiliPropietariVO> IMMOBILIPROPIETARI_XML = new XMLFormat<ImmobiliPropietariVO>(ImmobiliPropietariVO.class){
		
        public void write(ImmobiliPropietariVO afa_xml, OutputElement xml)throws XMLStreamException {
        	xml.setAttribute("codAnagrafica", afa_xml.getCodAnagrafica());
        	xml.setAttribute("codImmobile", afa_xml.getCodImmobile());
        }
                
        public void read(InputElement xml, ImmobiliPropietariVO c_xml) throws XMLStreamException{        	
        	c_xml.setCodAnagrafica(xml.getAttribute("codAnagrafica", 0));
        	c_xml.setCodImmobile(xml.getAttribute("codImmobile",0));
       }
        
   };
   
   public ContentValues getContentValue(){
	   ContentValues values = new ContentValues();	   
	   values.put("CODIMMOBILE", getCodImmobile());
	   values.put("CODANAGRAFICA", getCodAnagrafica());
	   return values;
   }


}
