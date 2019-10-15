package org.winkhouse.mwinkhouse.models;

import java.sql.SQLException;
import java.util.HashMap;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;

import org.winkhouse.mwinkhouse.models.columns.ImmagineColumnNames;

import android.content.ContentValues;
import android.database.Cursor;

public class ImmagineVO {

	private Integer codImmagine = null;
	private Integer codImmobile = null;
	private Integer ordine = null;
	private String pathImmagine = null;
	private String imgPropsStr = null;
	
	public ImmagineVO() {
		super();
		codImmagine = 0;
		codImmobile = 0;
		ordine = 0;
		pathImmagine = "";
		imgPropsStr = "";
	}
	
	public ImmagineVO(Cursor c,HashMap column_list) throws SQLException {
		super();
		
		if (column_list != null){
			if (column_list.containsKey(ImmagineColumnNames.CODIMMAGINE)){
				codImmagine = c.getInt(c.getColumnIndex(ImmagineColumnNames.CODIMMAGINE));
			}
		}else{
			codImmagine = c.getInt(c.getColumnIndex(ImmagineColumnNames.CODIMMAGINE));
		}
		if (column_list != null){
			if (column_list.containsKey(ImmagineColumnNames.CODIMMOBILE)){
				codImmobile = c.getInt(c.getColumnIndex(ImmagineColumnNames.CODIMMOBILE));
			}
		}else{
			codImmobile = c.getInt(c.getColumnIndex(ImmagineColumnNames.CODIMMOBILE));
		}
		if (column_list != null){
			if (column_list.containsKey(ImmagineColumnNames.ORDINE)){
				ordine = c.getInt(c.getColumnIndex(ImmagineColumnNames.ORDINE));
			}
		}else{
			ordine = c.getInt(c.getColumnIndex(ImmagineColumnNames.ORDINE));
		}
		if (column_list != null){
			if (column_list.containsKey(ImmagineColumnNames.PATHIMMAGINE)){
				pathImmagine = c.getString(c.getColumnIndex(ImmagineColumnNames.PATHIMMAGINE));
			}
		}else{
			pathImmagine = c.getString(c.getColumnIndex(ImmagineColumnNames.PATHIMMAGINE));
		}
		if (column_list != null){
			if (column_list.containsKey(ImmagineColumnNames.IMGPROPS)){
				imgPropsStr = c.getString(c.getColumnIndex(ImmagineColumnNames.IMGPROPS));
			}
		}else{
			imgPropsStr = c.getString(c.getColumnIndex(ImmagineColumnNames.IMGPROPS));
		}
		
	}	

	
	public Integer getCodImmagine() {
		return codImmagine;
	}
	
	public void setCodImmagine(Integer codImmagine) {
		this.codImmagine = codImmagine;
	}
	
	public Integer getCodImmobile() {
		return codImmobile;
	}
	
	public void setCodImmobile(Integer codImmobile) {
		this.codImmobile = codImmobile;
	}
	
	public Integer getOrdine() {
		return ordine;
	}
	
	public void setOrdine(Integer ordine) {
		this.ordine = ordine;
	}
	
	public String getPathImmagine() {
		return pathImmagine;
	}
	
	public void setPathImmagine(String pathImmagine) {
		this.pathImmagine = pathImmagine;
	}

	public String getImgPropsStr() {
		return imgPropsStr;
	}

	public void setImgPropsStr(String imgPropsStr) {
		this.imgPropsStr = imgPropsStr;
	}

	@Override
	public String toString() {
		return getPathImmagine();
	}
	
	protected static final XMLFormat<ImmagineVO> IMMAGINE_XML = new XMLFormat<ImmagineVO>(ImmagineVO.class){
		
        public void write(ImmagineVO i_xml, OutputElement xml)throws XMLStreamException {
        	xml.setAttribute("codImmobile", i_xml.getCodImmobile());
        	xml.setAttribute("codImmagine", i_xml.getCodImmagine());
        	xml.setAttribute("pathImmagine", i_xml.getPathImmagine());
        	xml.setAttribute("imgPropsStr", i_xml.getImgPropsStr());
        	xml.setAttribute("ordine", i_xml.getOrdine());        	        	
        }
                
        public void read(InputElement xml, ImmagineVO i_xml) throws XMLStreamException{
        	i_xml.setCodImmobile(xml.getAttribute("codImmobile", 0));
        	i_xml.setCodImmagine(xml.getAttribute("codImmagine", 0));
        	i_xml.setPathImmagine(xml.getAttribute("pathImmagine", ""));
        	i_xml.setImgPropsStr(xml.getAttribute("imgPropsStr", ""));
        	i_xml.setOrdine(xml.getAttribute("ordine", 0));
       }
        
   };

   public ContentValues getContentValue(){
	    ContentValues values = new ContentValues();
	    values.put("CODIMMAGINE", getCodImmagine());
	    values.put("ORDINE", getOrdine());
	    values.put("PATHIMMAGINE", getPathImmagine());
	    values.put("IMGPROPS", getImgPropsStr());
	    values.put("CODIMMOBILE", getCodImmobile());
	    return values;
  }
		

}
