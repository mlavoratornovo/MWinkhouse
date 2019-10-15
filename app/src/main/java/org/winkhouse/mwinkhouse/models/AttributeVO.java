package org.winkhouse.mwinkhouse.models;

import java.sql.ResultSet;
import java.sql.SQLException;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;
import android.content.ContentValues;

public class AttributeVO {

	private Integer idAttribute = null; 
	private Integer idClassEntity = null; 
	private String attributeName = null; 
	private String fieldType = null; 
	private Integer linkedIdEntity = null;
	
	public AttributeVO() {
		
	}

	public AttributeVO(ResultSet rs) throws SQLException {
		
		idAttribute = rs.getInt("IDATTRIBUTE"); 
		idClassEntity = rs.getInt("IDCLASSENTITY"); 
		attributeName = rs.getString("ATTRIBUTENAME"); 
		fieldType = rs.getString("FIELDTYPE"); 
		linkedIdEntity = rs.getInt("LINKEDIDENTITY");		
		
	}

	public Integer getIdAttribute() {
		return idAttribute;
	}

	public void setIdAttribute(Integer idAttribute) {
		this.idAttribute = idAttribute;
	}

	public Integer getIdClassEntity() {
		return idClassEntity;
	}

	public void setIdClassEntity(Integer idClassEntity) {
		this.idClassEntity = idClassEntity;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public Integer getLinkedIdEntity() {
		return linkedIdEntity;
	}

	public void setLinkedIdEntity(Integer linkedIdEntity) {
		this.linkedIdEntity = linkedIdEntity;
	}
	
	protected static final XMLFormat<AttributeVO> ATTRIBUTE_XML = new XMLFormat<AttributeVO>(AttributeVO.class){
		
        public void write(AttributeVO a_xml, OutputElement xml)throws XMLStreamException {
        }
                
        public void read(InputElement xml, AttributeVO c_xml) throws XMLStreamException{
        	c_xml.setAttributeName(xml.getAttribute("attributeName", ""));
        	c_xml.setFieldType(xml.getAttribute("fieldType", ""));
        	c_xml.setIdAttribute(xml.getAttribute("idAttribute", 0));
        	c_xml.setIdClassEntity(xml.getAttribute("idClassEntity", 0));
        	c_xml.setLinkedIdEntity(xml.getAttribute("linkedIdEntity", 0));
       }
        
   };
   
   public ContentValues getContentValue(){
	    ContentValues values = new ContentValues();
	    values.put("IDATTRIBUTE", getIdAttribute());
	    values.put("IDCLASSENTITY", getIdClassEntity());
	    values.put("ATTRIBUTENAME", getAttributeName());
	    values.put("FIELDTYPE", getFieldType());
	    values.put("LINKEDIDENTITY", getFieldType());
	    return values;
   }

}
