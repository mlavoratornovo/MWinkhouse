package org.winkhouse.mwinkhouse.models;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;

import org.winkhouse.mwinkhouse.helpers.DataBaseHelper;
import org.winkhouse.mwinkhouse.util.DateFormatUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class AnagraficheVO {

	private Integer codAnagrafica = null;
	private String nome = null;
	private String cognome = null;
	private String ragioneSociale = null;
	private String indirizzo = null;
	private String provincia = null;
	private String cap = null;
	private String citta = null;
	private Date dataInserimento = null;
	private String commento = null;
	private Boolean storico = null;
	private Integer codClasseCliente = null;
	private Integer codAgenteInseritore = null;	
	private String codiceFiscale = null;
	private String partitaIva = null;
	private ArrayList<ImmobiliVO> immobiliPropietari = null;

	public AnagraficheVO() {
		super();
		codAnagrafica = 0;
		nome = "";
		cognome = "";
		ragioneSociale = "";
		indirizzo = "";
		provincia = "";
		cap = "";
		citta = "";
		dataInserimento = new Date();
		commento = "";
		storico = false;
		codClasseCliente = 0;
		codAgenteInseritore = 0;
		codiceFiscale = "";
		partitaIva = "";		
	}	

	public AnagraficheVO(Cursor c,HashMap column_list) throws SQLException {
		super();
		
		if (column_list != null){
			if (column_list.containsKey("CODANAGRAFICA")){
				codAnagrafica = c.getInt(c.getColumnIndex("CODANAGRAFICA"));
			}
		}else{
			codAnagrafica = c.getInt(c.getColumnIndex("CODANAGRAFICA"));
		}
		if (column_list != null){
			if (column_list.containsKey("NOME")){
				nome = c.getString(c.getColumnIndex("NOME"));
			}
		}else{
			nome = c.getString(c.getColumnIndex("NOME"));
		}
		if (column_list != null){
			if (column_list.containsKey("COGNOME")){
				cognome = c.getString(c.getColumnIndex("COGNOME"));
			}
		}else{
			cognome = c.getString(c.getColumnIndex("COGNOME"));
		}
		if (column_list != null){
			if (column_list.containsKey("RAGSOC")){
				ragioneSociale = c.getString(c.getColumnIndex("RAGSOC"));
			}
		}else{
			ragioneSociale = c.getString(c.getColumnIndex("RAGSOC"));
		}
		if (column_list != null){
			if (column_list.containsKey("INDIRIZZO")){
				indirizzo = c.getString(c.getColumnIndex("INDIRIZZO"));
			}
		}else{
			indirizzo = c.getString(c.getColumnIndex("INDIRIZZO"));
		}
		if (column_list != null){
			if (column_list.containsKey("PROVINCIA")){
				provincia = c.getString(c.getColumnIndex("PROVINCIA"));
			}
		}else{
			provincia = c.getString(c.getColumnIndex("PROVINCIA"));
		}
		if (column_list != null){
			if (column_list.containsKey("CITTA")){
				citta = c.getString(c.getColumnIndex("CITTA"));
			}
		}else{
			citta = c.getString(c.getColumnIndex("CITTA"));
		}
		if (column_list != null){
			if (column_list.containsKey("CAP")){
				cap = c.getString(c.getColumnIndex("CAP"));
			}
		}else{
			cap = c.getString(c.getColumnIndex("CAP"));
		}
		if (column_list != null){
			if (column_list.containsKey("DATAINSERIMENTO")){
				try{					
					dataInserimento = DateFormat.getDateInstance().parse(c.getString(c.getColumnIndex("DATAINSERIMENTO"))); 
				}catch(Exception e){
					
				}
			}
		}else{
			try{					
				dataInserimento = DateFormat.getDateInstance().parse(c.getString(c.getColumnIndex("DATAINSERIMENTO"))); 
			}catch(Exception e){
				
			}			
		}
		if (column_list != null){
			if (column_list.containsKey("COMMENTO")){
				commento = c.getString(c.getColumnIndex("COMMENTO"));
			}
		}else{
			commento = c.getString(c.getColumnIndex("COMMENTO"));
		}
		if (column_list != null){
			if (column_list.containsKey("STORICO")){
				int tmp = c.getInt(c.getColumnIndex("STORICO"));
				storico = (tmp == 0)?false:true;
			}
		}else{
			int tmp = c.getInt(c.getColumnIndex("STORICO"));
			storico = (tmp == 0)?false:true;
		}
		if (column_list != null){
			if (column_list.containsKey("CODCLASSECLIENTE")){
				codClasseCliente = c.getInt(c.getColumnIndex("CODCLASSECLIENTE"));
			}
		}else{
			codClasseCliente = c.getInt(c.getColumnIndex("CODCLASSECLIENTE"));
		}
		if (column_list != null){
			if (column_list.containsKey("CODAGENTEINSERITORE")){
				codAgenteInseritore = c.getInt(c.getColumnIndex("CODAGENTEINSERITORE"));
			}
		}else{
			codAgenteInseritore = c.getInt(c.getColumnIndex("CODAGENTEINSERITORE"));
		}
		if (column_list != null){
			if (column_list.containsKey("CODICEFISCALE")){
				codiceFiscale = c.getString(c.getColumnIndex("CODICEFISCALE"));
			}
		}else{
			codiceFiscale = c.getString(c.getColumnIndex("CODICEFISCALE"));
		}
		if (column_list != null){
			if (column_list.containsKey("PIVA")){
				partitaIva = c.getString(c.getColumnIndex("PIVA"));
			}					
		}else{
			partitaIva = c.getString(c.getColumnIndex("PIVA"));
		}
		
	}	
	
	public Integer getCodAnagrafica() {
		return codAnagrafica;
	}

	public void setCodAnagrafica(Integer codAnagrafica) {
		this.codAnagrafica = codAnagrafica;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public Date getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public String getCommento() {
		return commento;
	}

	public void setCommento(String commento) {
		this.commento = commento;
	}

	public Boolean getStorico() {
		return storico;
	}

	public void setStorico(Boolean storico) {
		this.storico = storico;
	}
	public Integer getCodClasseCliente() {
		return codClasseCliente;
	}

	public void setCodClasseCliente(Integer codClasseCliente) {
		this.codClasseCliente = codClasseCliente;
	}

	public Integer getCodAgenteInseritore() {
		return codAgenteInseritore;
	}

	public void setCodAgenteInseritore(Integer codAgenteInseritore) {
		this.codAgenteInseritore = codAgenteInseritore;
	}

	@Override
	public String toString() {		
		return getNome() + " " + getCognome() + " - " + getCitta() + " " + getIndirizzo();
	}

	public String getRagioneSociale() {
		return this.ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}
	
	protected static final XMLFormat<AnagraficheVO> ANAGRAFICHE_XML = new XMLFormat<AnagraficheVO>(AnagraficheVO.class){
		
        public void write(AnagraficheVO a_xml, OutputElement xml)throws XMLStreamException {
        	
        	xml.setAttribute("codAnagrafica", a_xml.getCodAnagrafica());
        	xml.setAttribute("cognome", a_xml.getCognome());
        	xml.setAttribute("nome", a_xml.getNome());
        	xml.setAttribute("ragioneSociale", a_xml.getRagioneSociale());
        	xml.setAttribute("partitaIva", a_xml.getPartitaIva());
        	xml.setAttribute("codiceFiscale", a_xml.getCodiceFiscale());
        	xml.setAttribute("provincia", a_xml.getProvincia());
        	xml.setAttribute("cap", a_xml.getCap());
        	xml.setAttribute("citta", a_xml.getCitta());
        	xml.setAttribute("indirizzo", a_xml.getIndirizzo());
        	xml.setAttribute("commento", a_xml.getCommento());
        	xml.setAttribute("storico", a_xml.getStorico());
        	xml.setAttribute("codAgenteInseritore", a_xml.getCodAgenteInseritore());
        	xml.setAttribute("codClasseCliente", a_xml.getCodClasseCliente());
        	xml.setAttribute("dataInserimento", 
        					 ((a_xml.getDataInserimento() != null)
        					 ? a_xml.getDataInserimento().toString()
        					 : null));
        	        	
        }
                
        public void read(InputElement xml, AnagraficheVO a_xml) throws XMLStreamException{
        	a_xml.setCodAnagrafica(xml.getAttribute("codAnagrafica", 0));
        	a_xml.setCognome(xml.getAttribute("cognome", ""));
        	a_xml.setNome(xml.getAttribute("nome", ""));
        	a_xml.setRagioneSociale(xml.getAttribute("ragioneSociale", ""));
        	a_xml.setPartitaIva(xml.getAttribute("partitaIva", ""));
        	a_xml.setCodiceFiscale(xml.getAttribute("codiceFiscale", ""));
        	a_xml.setProvincia(xml.getAttribute("provincia", ""));
        	a_xml.setCap(xml.getAttribute("cap", ""));
        	a_xml.setCitta(xml.getAttribute("citta", ""));
        	a_xml.setIndirizzo(xml.getAttribute("indirizzo", ""));
        	a_xml.setCommento(xml.getAttribute("commento", ""));
        	a_xml.setStorico(xml.getAttribute("storico", false));
        	a_xml.setCodAgenteInseritore(xml.getAttribute("codAgenteInseritore", 0));
        	a_xml.setCodClasseCliente(xml.getAttribute("codClasseCliente", 0));
        	
        	Date datainserimento = null;
        	try {
        		datainserimento = DateFormatUtils.getInstace().parse_xml(xml.getAttribute("dataInserimento").toString());
			} catch (Exception e) {
				datainserimento = new Date();
			}
        	
        	a_xml.setDataInserimento(datainserimento);
       }
        
   };
	

	public ContentValues getContentValue(){
	   ContentValues values = new ContentValues();
	   values.put("CODANAGRAFICA", getCodAnagrafica());
	   values.put("NOME", getNome());
	   values.put("COGNOME", getCognome());
	   values.put("INDIRIZZO", getIndirizzo());
	   values.put("PROVINCIA", getProvincia());
	   values.put("RAGSOC", getRagioneSociale());
	   values.put("CAP", getCap());
	   values.put("CITTA", getCitta());
	   values.put("DATAINSERIMENTO", (getDataInserimento() != null)?getDataInserimento().toString():new Date().toString());
	   values.put("COMMENTO", getCommento());
	   values.put("STORICO", getStorico());
	   values.put("CODCLASSECLIENTE", getCodClasseCliente());
	   values.put("CODAGENTEINSERITORE", getCodAgenteInseritore());
	   values.put("CODICEFISCALE", getCodiceFiscale());
	   values.put("PIVA", getPartitaIva());	   
	   return values;
	}


	public ArrayList<ImmobiliVO> getImmobiliPropietari(Context c,HashMap column_list) {
		if (immobiliPropietari == null){
			DataBaseHelper dbh = new DataBaseHelper(c,DataBaseHelper.NONE_DB);
			immobiliPropietari = dbh.getImmobiliPropieta(column_list, this.codAnagrafica);
			dbh.close();
		}
		return immobiliPropietari;
	}

}
