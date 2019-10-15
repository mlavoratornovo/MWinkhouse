package org.winkhouse.mwinkhouse.models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;

import org.winkhouse.mwinkhouse.helpers.DataBaseHelper;
import org.winkhouse.mwinkhouse.models.columns.ColloquiColumnNames;
import org.winkhouse.mwinkhouse.util.DateFormatUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class ColloquiVO implements ColloquiColumnNames{

	private Integer codColloquio = null;
	private String descrizione = null;
	private Integer codAgenteInseritore = null;
	private Integer codImmobileAbbinato = null;
	private Integer codTipologiaColloquio = null;
	private Date dataInserimento = null;
	private Date dataColloquio = null;
	
	private String luogoIncontro = null;
	private Boolean scadenziere = null;
	private String commentoAgenzia = null;
	private String commentoCliente = null;
	private Integer codParent = null;
	private String iCalUid = null;
	private ArrayList<AnagraficheVO> anagrafiche = null;
	private String des_tipo_colloquio = null;
	
	
	public String getDes_tipo_colloquio() {
		if (getCodTipologiaColloquio() == TipologieColloquiVO.TYPE_GENERICO)
			return "Generico";
		if (getCodTipologiaColloquio() == TipologieColloquiVO.TYPE_RICERCA)
			return "Ricerca";
		if (getCodTipologiaColloquio() == TipologieColloquiVO.TYPE_VISITA)
			return "Visita";

		return "";
	}

	public ColloquiVO() {
		codColloquio = 0;
		descrizione = "";
		codAgenteInseritore = 0;
		codImmobileAbbinato = 0;
		codTipologiaColloquio = 0;
		dataInserimento = new Date();
		dataColloquio = new Date();
		luogoIncontro = "";
		scadenziere = false;
		commentoAgenzia = "";
		commentoCliente = "";
		codParent = 0;
		iCalUid = "";
	}

	public ColloquiVO(Cursor c,HashMap column_list) throws SQLException {
		
		if (column_list != null){
			if (column_list.containsKey(CODCOLLOQUIO)){
				codColloquio = c.getInt(c.getColumnIndex(CODCOLLOQUIO));
			}
		}else{
			codColloquio = c.getInt(c.getColumnIndex(CODCOLLOQUIO));
		}
		
		if (column_list != null){
			if (column_list.containsKey(DESCRIZIONE)){
				descrizione = c.getString(c.getColumnIndex(DESCRIZIONE));
			}
		}else{
			descrizione = c.getString(c.getColumnIndex(DESCRIZIONE));
		}
		
		if (column_list != null){
			if (column_list.containsKey(CODAGENTEINSERITORE)){
				codAgenteInseritore = c.getInt(c.getColumnIndex(CODAGENTEINSERITORE));
			}
		}else{
			codAgenteInseritore = c.getInt(c.getColumnIndex(CODAGENTEINSERITORE));
		}

		if (column_list != null){
			if (column_list.containsKey(CODIMMOBILEABBINATO)){
				codImmobileAbbinato = c.getInt(c.getColumnIndex(CODIMMOBILEABBINATO));
			}
		}else{
			codImmobileAbbinato = c.getInt(c.getColumnIndex(CODIMMOBILEABBINATO));
		}

		if (column_list != null){
			if (column_list.containsKey(CODTIPOLOGIACOLLOQUIO)){
				codTipologiaColloquio = c.getInt(c.getColumnIndex(CODTIPOLOGIACOLLOQUIO));
			}
		}else{
			codTipologiaColloquio = c.getInt(c.getColumnIndex(CODTIPOLOGIACOLLOQUIO));
		}

		if (column_list != null){
			if (column_list.containsKey(DATAINSERIMENTO)){
				try{				
					dataInserimento = DateFormatUtils.getInstace().parse_xml(c.getString(c.getColumnIndex(DATAINSERIMENTO)));					
				}catch(Exception e){
					
				}
			}
		}else{			
			try{					
				dataInserimento = DateFormatUtils.getInstace().parse_xml(c.getString(c.getColumnIndex(DATAINSERIMENTO))); 
			}catch(Exception e){
				
			}			
		}

		if (column_list != null){
			if (column_list.containsKey(DATACOLLOQUIO)){
				try{				
					dataColloquio = DateFormatUtils.getInstace().parse_xml(c.getString(c.getColumnIndex(DATACOLLOQUIO)));					
				}catch(Exception e){
					Log.e("WINKHOUSE", e.getMessage());
				}
			}
		}else{			
			try{					
				dataColloquio = DateFormatUtils.getInstace().parse_xml(c.getString(c.getColumnIndex(DATACOLLOQUIO))); 
			}catch(Exception e){
				
			}			
		}

		if (column_list != null){
			if (column_list.containsKey(LUOGO)){
				luogoIncontro = c.getString(c.getColumnIndex(LUOGO));
			}
		}else{
			luogoIncontro = c.getString(c.getColumnIndex(LUOGO));
		}

		if (column_list != null){
			if (column_list.containsKey(SCADENZIERE)){
				int tmp = c.getInt(c.getColumnIndex(SCADENZIERE));
				scadenziere = (tmp == 0)?false:true;
			}
		}else{
			int tmp = c.getInt(c.getColumnIndex(SCADENZIERE));
			scadenziere = (tmp == 0)?false:true;
		}
		
		if (column_list != null){
			if (column_list.containsKey(COMMENTOAGENZIA)){
				commentoAgenzia = c.getString(c.getColumnIndex(COMMENTOAGENZIA));
			}
		}else{
			commentoAgenzia = c.getString(c.getColumnIndex(COMMENTOAGENZIA));
		}

		if (column_list != null){
			if (column_list.containsKey(COMMENTOCLIENTE)){
				commentoCliente = c.getString(c.getColumnIndex(COMMENTOCLIENTE));
			}
		}else{
			commentoCliente = c.getString(c.getColumnIndex(COMMENTOCLIENTE));
		}

		if (column_list != null){
			if (column_list.containsKey(CODPARENT)){
				codParent = c.getInt(c.getColumnIndex(CODPARENT));
			}
		}else{
			codParent = c.getInt(c.getColumnIndex(CODPARENT));
		}

		if (column_list != null){
			if (column_list.containsKey(ICALUID)){
				iCalUid = c.getString(c.getColumnIndex(ICALUID));
			}
		}else{
			iCalUid = c.getString(c.getColumnIndex(ICALUID));
		}

	}
	
	public Integer getCodColloquio() {
		return codColloquio;
	}

	public void setCodColloquio(Integer codColloquio) {
		this.codColloquio = codColloquio;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Date getDataColloquio() {
		return dataColloquio;
	}

	public void setDataColloquio(Date dataColloquio) {
		this.dataColloquio = dataColloquio;
	}

	public String getLuogoIncontro() {
		return luogoIncontro;
	}

	public void setLuogoIncontro(String luogoIncontro) {
		this.luogoIncontro = luogoIncontro;
	}

	public Boolean getScadenziere() {
		return scadenziere;
	}

	public void setScadenziere(Boolean scadenziere) {
		this.scadenziere = scadenziere;
	}

	public String getCommentoAgenzia() {
		return commentoAgenzia;
	}

	public void setCommentoAgenzia(String commentoAgenzia) {
		this.commentoAgenzia = commentoAgenzia;
	}

	public String getCommentoCliente() {
		return commentoCliente;
	}

	public void setCommentoCliente(String commentoCliente) {
		this.commentoCliente = commentoCliente;
	}

	public Date getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public Integer getCodAgenteInseritore() {
		return codAgenteInseritore;
	}

	public void setCodAgenteInseritore(Integer codAgenteInseritore) {
		this.codAgenteInseritore = codAgenteInseritore;
	}

	public Integer getCodImmobileAbbinato() {
		return codImmobileAbbinato;
	}

	public void setCodImmobileAbbinato(Integer codImmobileAbbinato) {
		this.codImmobileAbbinato = codImmobileAbbinato;
	}

	public Integer getCodTipologiaColloquio() {
		return codTipologiaColloquio;
	}

	public void setCodTipologiaColloquio(Integer codTipologiaColloquio) {
		this.codTipologiaColloquio = codTipologiaColloquio;
	}

	public Integer getCodParent() {
		return codParent;
	}

	public void setCodParent(Integer codParent) {
		this.codParent = codParent;
	}

	public String getiCalUid() {
		return iCalUid;
	}

	public void setiCalUid(String iCalUid) {
		this.iCalUid = iCalUid;
	}
	
	public ArrayList<AnagraficheVO> getAnagrafiche(Context c,HashMap column_list) {
		
		if (anagrafiche == null){
			DataBaseHelper dbh = new DataBaseHelper(c,DataBaseHelper.NONE_DB);
			anagrafiche = dbh.getAnagraficheColloquio(this.codColloquio,column_list);
			dbh.close();
		}
		
		return anagrafiche;
		
	}
	
	
	protected static final XMLFormat<ColloquiVO> COLLOQUI_XML = new XMLFormat<ColloquiVO>(ColloquiVO.class){
		
        public void write(ColloquiVO i_xml, OutputElement xml)throws XMLStreamException {
        	
        	xml.setAttribute("codColloquio", i_xml.getCodColloquio());
        	xml.setAttribute("descrizione", i_xml.getDescrizione());
        	xml.setAttribute("codAgenteInseritore", i_xml.getCodAgenteInseritore());
        	xml.setAttribute("codImmobileAbbinato", i_xml.getCodImmobileAbbinato());
        	xml.setAttribute("codTipologiaColloquio", i_xml.getCodTipologiaColloquio());
        	try {
				xml.setAttribute("dataInserimento",DateFormatUtils.getInstace().format_xml(i_xml.getDataInserimento()));
			} catch (Exception e) {
				try {
					xml.setAttribute("dataInserimento",DateFormatUtils.getInstace().format_xml(new Date()));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
        	try {
				xml.setAttribute("dataColloquio",DateFormatUtils.getInstace().format_xml(i_xml.getDataColloquio()));
			} catch (Exception e) {
				try {
					xml.setAttribute("dataColloquio",DateFormatUtils.getInstace().format_xml(new Date()));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}      	
        	xml.setAttribute("luogoIncontro", i_xml.getLuogoIncontro());
        	xml.setAttribute("scadenziere", i_xml.getScadenziere());
        	xml.setAttribute("commentoAgenzia", i_xml.getCommentoAgenzia());
        	xml.setAttribute("commentoCliente", i_xml.getCommentoCliente());
        	xml.setAttribute("codParent", i_xml.getCodParent());
        	xml.setAttribute("iCalUid", i_xml.getiCalUid());
        	
        }
                
        public void read(InputElement xml, ColloquiVO i_xml) throws XMLStreamException{
        	
        	i_xml.setCodColloquio(xml.getAttribute("codColloquio", 0));
        	i_xml.setDescrizione(xml.getAttribute("descrizione", ""));
        	i_xml.setCodAgenteInseritore(xml.getAttribute("codAgenteInseritore", 0));
        	i_xml.setCodImmobileAbbinato(xml.getAttribute("codImmobileAbbinato", 0));
        	i_xml.setCodTipologiaColloquio(xml.getAttribute("codTipologiaColloquio", 0));
        	Date datainserimento = null;
        	try {
        		datainserimento = DateFormatUtils.getInstace().parse_xml(xml.getAttribute("dataInserimento").toString());
			} catch (Exception e) {
				datainserimento = new Date();
			}        	
        	Date datacolloquio = null;
        	try {
        		datacolloquio = DateFormatUtils.getInstace().parse_xml(xml.getAttribute("dataColloquio").toString());
			} catch (Exception e) {
				datainserimento = new Date();
			}        	
        	i_xml.setLuogoIncontro(xml.getAttribute("luogoIncontro", ""));
        	i_xml.setScadenziere(xml.getAttribute("scadenziere", false));
        	i_xml.setCommentoAgenzia(xml.getAttribute("commentoAgenzia", ""));
        	i_xml.setCommentoCliente(xml.getAttribute("commentoCliente", ""));
        	i_xml.setCodParent(xml.getAttribute("codParent", 0));
        	i_xml.setiCalUid(xml.getAttribute("iCalUid", ""));
        	
        }
   };

   public ContentValues getContentValue(){
	   
	    ContentValues values = new ContentValues();
	    
	    values.put(CODCOLLOQUIO, getCodColloquio());
	    values.put(DESCRIZIONE, getDescrizione());
	    values.put(CODAGENTEINSERITORE, getCodAgenteInseritore());
	    values.put(CODIMMOBILEABBINATO, getCodImmobileAbbinato());
	    values.put(CODTIPOLOGIACOLLOQUIO, getCodTipologiaColloquio());
	    
	    try {
			values.put(DATAINSERIMENTO, DateFormatUtils.getInstace().format_xml(getDataInserimento()));
		} catch (Exception e) {
			Log.e("WINKHOUSE.ColloquiVO.getContentValue",e.getCause().getMessage());
		}
	    
	    try {
			values.put(DATACOLLOQUIO, DateFormatUtils.getInstace().format_xml(getDataColloquio()));
		} catch (Exception e) {
			Log.e("WINKHOUSE.ColloquiVO.getContentValue",e.getCause().getMessage());
		}
	    
	    values.put(LUOGO, getLuogoIncontro());
	    values.put(SCADENZIERE, getScadenziere());
	    values.put(COMMENTOAGENZIA, getCommentoAgenzia());
	    values.put(COMMENTOCLIENTE, getCommentoCliente());
	    values.put(CODPARENT, getCodParent());
	    values.put(ICALUID, getiCalUid());
	   
	    return values;
   }


	
}
