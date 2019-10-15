package org.winkhouse.mwinkhouse.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;
import android.content.ContentValues;



public class EntityVO {

	private Integer idClassEntity = null;
	private String className = null;
	private String description = null;
	public static final String[] fieldTypes = {String.class.getName(),
											   Integer.class.getName(),
											   Double.class.getName(),
											   Date.class.getName()}; 
	

	public EntityVO() {

	}

	public EntityVO(ResultSet rs) throws SQLException{
		idClassEntity = rs.getInt("IDCLASSENTITY");
		className = rs.getString("CLASSNAME");
		description = rs.getString("DESCRIPTION");
	}
	
	public Integer getIdClassEntity() {
		return idClassEntity;
	}

	public void setIdClassEntity(Integer idClassEntity) {
		this.idClassEntity = idClassEntity;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	protected static final XMLFormat<EntityVO> ENTITY_XML = new XMLFormat<EntityVO>(EntityVO.class){
		
        public void write(EntityVO e_xml, OutputElement xml)throws XMLStreamException {
        }
                
        public void read(InputElement xml, EntityVO c_xml) throws XMLStreamException{
        	c_xml.setIdClassEntity(xml.getAttribute("idClassEntity", 0));
        	c_xml.setDescription(xml.getAttribute("description", ""));
        	c_xml.setClassName(xml.getAttribute("className", ""));
        }
        
   };
   
   public ContentValues getContentValue(){
	    ContentValues values = new ContentValues();
	    values.put("IDCLASSENTITY", getIdClassEntity());
	    values.put("CLASSNAME", getClassName());
	    values.put("DESCRIPTION", getDescription());
	    return values;
  }



}
