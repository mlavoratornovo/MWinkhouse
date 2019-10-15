package org.winkhouse.mwinkhouse.models;

import java.sql.ResultSet;
import java.sql.SQLException;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;
import android.content.ContentValues;

public class AttributeValueVO {

	private Integer idValue = null;
	private Integer idField = null;
	private Integer idObject = null;
	private String fieldValue = null;
	  
	public AttributeValueVO() {

	}
	
	public AttributeValueVO(ResultSet rs) throws SQLException {
		
		idValue = rs.getInt("IDVALUE");
		idField = rs.getInt("IDFIELD");;
		idObject = rs.getInt("IDOBJECT");;
		fieldValue = rs.getString("FIELDVALUE");
		
	}

	public Integer getIdValue() {
		return idValue;
	}

	public void setIdValue(Integer idValue) {
		this.idValue = idValue;
	}

	public Integer getIdField() {
		return idField;
	}

	public void setIdField(Integer idField) {
		this.idField = idField;
	}

	public Integer getIdObject() {
		return idObject;
	}

	public void setIdObject(Integer idObject) {
		this.idObject = idObject;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	
	protected static final XMLFormat<AttributeValueVO> ATTRIBUTEVALUE_XML = new XMLFormat<AttributeValueVO>(AttributeValueVO.class){
		
        public void write(AttributeValueVO av_xml, OutputElement xml)throws XMLStreamException {
        	xml.setAttribute("fieldValue", av_xml.getFieldValue());
        	xml.setAttribute("idField", av_xml.getIdField());
        	xml.setAttribute("idObject", av_xml.getIdObject());
        	xml.setAttribute("idValue", av_xml.getIdValue());
        }
                
        public void read(InputElement xml, AttributeValueVO c_xml) throws XMLStreamException{
        	c_xml.setFieldValue(xml.getAttribute("fieldValue", ""));
        	c_xml.setIdField(xml.getAttribute("idField", 0));
        	c_xml.setIdObject(xml.getAttribute("idObject", 0));
        	c_xml.setIdValue(xml.getAttribute("idValue", 0));
       }
        
   };
   
   public ContentValues getContentValue(){
	    ContentValues values = new ContentValues();
	    values.put("IDVALUE", getIdValue());	    
	    values.put("IDFIELD", getIdField());
	    values.put("IDOBJECT", getIdObject());
	    values.put("FIELDVALUE", getFieldValue());
	    return values;
  }


}
