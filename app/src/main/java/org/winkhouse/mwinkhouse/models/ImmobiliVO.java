package org.winkhouse.mwinkhouse.models;

import java.sql.SQLException;
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
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Classe VO che rappresenta una riga della tabella IMMOBILI
 */

public class ImmobiliVO implements Parcelable{
	
	private Integer codImmobile = null;
	private String rif = null;
	private String indirizzo = null;
	private String provincia = null;
	private String cap = null;
	private String citta = null;
	private String zona = null;	
	private Date dataInserimento = null;
	private Date dataLibero = null;
	private String descrizione = null;
	private String mutuoDescrizione = null;
	private Double prezzo = null;
	private Double mutuo = null;
	private Double spese = null;
	private String varie = null;
	private Boolean visione = null;
	private Boolean storico = null;
	private Boolean affittabile = null;
	private Integer mq = null;
	private Integer annoCostruzione = null;
	private Integer codAgenteInseritore = null;
	private Integer codAnagrafica = null;
	private Integer codRiscaldamento = null;
	private Integer codStato = null;
	private Integer codTipologia = null;
	private Integer codClasseEnergetica = null;
	
	private ArrayList<AnagraficheVO> anagrafichePropietarie = null;
	
	
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { 
		public ImmobiliVO createFromParcel(Parcel in) { 
			return new ImmobiliVO(in); 
		}   
		public ImmobiliVO[] newArray(int size) { 
			return new ImmobiliVO[size]; 
		} 
	 };	

	public ImmobiliVO(Parcel p) {
		codImmobile = p.readInt();
		rif = p.readString();
		indirizzo = p.readString();
		provincia = p.readString();
		cap = p.readString();
		citta = p.readString();
		zona = p.readString();

		Long di = p.readLong();
		if (di == -1){
            dataInserimento = new Date();
        }else{
            dataInserimento = new Date(di);
        }

        Long dl = p.readLong();
        if (dl == -1){
            dataLibero = null;
        }else{
            dataLibero = new Date(dl);
        }

		descrizione = p.readString();
		mutuoDescrizione = p.readString();

		prezzo = p.readDouble();
		mutuo = p.readDouble();
		spese = p.readDouble();
		varie = p.readString();

		Byte v = p.readByte();
		if (v != -1){
            visione = p.readByte() != 0;
        }else{
		    visione = false;
        }

        Byte s = p.readByte();
        if (s != -1){
            storico = p.readByte() != 0;
        }else {
            storico = false;
        }

        Byte a = p.readByte();
        if (a != -1){
            affittabile = p.readByte() != 0;
        }else{
            affittabile = false;
        }

		mq = p.readInt();
		annoCostruzione = p.readInt();
		codAgenteInseritore = p.readInt();
		codAnagrafica = p.readInt();
		codRiscaldamento = p.readInt();
		codStato = p.readInt();
		codTipologia = p.readInt();		
		codClasseEnergetica = p.readInt();
		
	}
	
	public ImmobiliVO() {
		codImmobile = 0;
		rif = "";
		indirizzo = "";
		provincia = "";
		cap = "";
		citta = "";
		zona = "";	
		dataInserimento = new Date();
		dataLibero = new Date();
		descrizione = "";
		mutuoDescrizione = "";
		prezzo = 0.0;
		mutuo = 0.0;
		spese = 0.0;
		varie = "";
		visione = false;
		storico = false;
		affittabile = false;
		mq = 0;
		annoCostruzione = 0;
		codAgenteInseritore = 0;
		codAnagrafica = 0;
		codRiscaldamento = 0;
		codStato = 0;
		codTipologia = 0;		
		codClasseEnergetica = 0;
		anagrafichePropietarie = new ArrayList<AnagraficheVO>();
	}

	public ImmobiliVO(Cursor c,HashMap column_list) throws SQLException {
		super();
		
		if (column_list != null){
			if (column_list.containsKey("CODIMMOBILE")){
				codImmobile = c.getInt(c.getColumnIndex("CODIMMOBILE"));
			}
		}else{
			codImmobile = c.getInt(c.getColumnIndex("CODIMMOBILE"));
		}
		if (column_list != null){
			if (column_list.containsKey("CODANAGRAFICA")){
				codAnagrafica = c.getInt(c.getColumnIndex("CODANAGRAFICA"));
			}
		}else{
			codAnagrafica = c.getInt(c.getColumnIndex("CODANAGRAFICA"));
		}
		if (column_list != null){
			if (column_list.containsKey("CODRISCALDAMENTO")){
				codRiscaldamento = c.getInt(c.getColumnIndex("CODRISCALDAMENTO"));
			}
		}else{
			codRiscaldamento = c.getInt(c.getColumnIndex("CODRISCALDAMENTO"));
		}
		if (column_list != null){
			if (column_list.containsKey("CODSTATO")){
				codStato = c.getInt(c.getColumnIndex("CODSTATO"));
			}
		}else{
			codStato = c.getInt(c.getColumnIndex("CODSTATO"));
		}
		if (column_list != null){
			if (column_list.containsKey("CODTIPOLOGIA")){
				codTipologia = c.getInt(c.getColumnIndex("CODTIPOLOGIA"));
			}
		}else{
			codTipologia = c.getInt(c.getColumnIndex("CODTIPOLOGIA"));
		}
		if (column_list != null){
			if (column_list.containsKey("CODCLASSEENERGETICA")){
				codClasseEnergetica = c.getInt(c.getColumnIndex("CODCLASSEENERGETICA"));
			}
		}else{
			codClasseEnergetica = c.getInt(c.getColumnIndex("CODCLASSEENERGETICA"));
		}		
		if (column_list != null){
			if (column_list.containsKey("MQ")){
				mq = c.getInt(c.getColumnIndex("MQ"));
			}
		}else{
			mq = c.getInt(c.getColumnIndex("MQ"));
		}		
		if (column_list != null){
			if (column_list.containsKey("ANNOCOSTRUZIONE")){
				annoCostruzione = c.getInt(c.getColumnIndex("ANNOCOSTRUZIONE"));
			}
		}else{
			annoCostruzione = c.getInt(c.getColumnIndex("ANNOCOSTRUZIONE"));
		}		
		if (column_list != null){
			if (column_list.containsKey("RIF")){
				rif = c.getString(c.getColumnIndex("RIF"));
			}
		}else{
			rif = c.getString(c.getColumnIndex("RIF"));
		}
		if (column_list != null){
			if (column_list.containsKey("ZONA")){
				zona = c.getString(c.getColumnIndex("ZONA"));
			}
		}else{
			zona = c.getString(c.getColumnIndex("ZONA"));
		}
		if (column_list != null){
			if (column_list.containsKey("MUTUODESCRIZIONE")){
				mutuoDescrizione = c.getString(c.getColumnIndex("MUTUODESCRIZIONE"));
			}
		}else{
			mutuoDescrizione = c.getString(c.getColumnIndex("MUTUODESCRIZIONE"));
		}
		if (column_list != null){
			if (column_list.containsKey("DESCRIZIONE")){
				descrizione = c.getString(c.getColumnIndex("DESCRIZIONE"));
			}
		}else{
			descrizione = c.getString(c.getColumnIndex("DESCRIZIONE"));
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
					dataInserimento = DateFormatUtils.getInstace().parse_xml(c.getString(c.getColumnIndex("DATAINSERIMENTO")));					
				}catch(Exception e){
					
				}
			}
		}else{			
			try{					
				dataInserimento = DateFormatUtils.getInstace().parse_xml(c.getString(c.getColumnIndex("DATAINSERIMENTO"))); 
			}catch(Exception e){
				
			}			
		}
		if (column_list != null){
			if (column_list.containsKey("DATALIBERO")){
				try{					
					dataLibero = DateFormatUtils.getInstace().parse_xml(c.getString(c.getColumnIndex("DATALIBERO"))); 
				}catch(Exception e){
					
				}
			}
		}else{
			try{					
				dataLibero = DateFormatUtils.getInstace().parse_xml(c.getString(c.getColumnIndex("DATALIBERO"))); 
			}catch(Exception e){
				
			}			
		}
		
		if (column_list != null){
			if (column_list.containsKey("PREZZO")){
				prezzo = c.getDouble(c.getColumnIndex("PREZZO"));
			}
		}else{
			prezzo = c.getDouble(c.getColumnIndex("PREZZO"));
		}
		if (column_list != null){
			if (column_list.containsKey("MUTUO")){
				mutuo = c.getDouble(c.getColumnIndex("MUTUO"));
			}
		}else{
			mutuo = c.getDouble(c.getColumnIndex("MUTUO"));
		}
		if (column_list != null){
			if (column_list.containsKey("SPESE")){
				spese = c.getDouble(c.getColumnIndex("SPESE"));
			}
		}else{
			spese = c.getDouble(c.getColumnIndex("SPESE"));
		}
		
		if (column_list != null){
			if (column_list.containsKey("STORICO")){
				int tmp = c.getInt(c.getColumnIndex("STORICO"));
				storico = tmp != 0;
			}
		}else{
			int tmp = c.getInt(c.getColumnIndex("STORICO"));
			storico = tmp != 0;
		}
		if (column_list != null){
			if (column_list.containsKey("VISIONE")){
				int tmp = c.getInt(c.getColumnIndex("VISIONE"));
				visione = tmp != 0;
			}
		}else{
			int tmp = c.getInt(c.getColumnIndex("VISIONE"));
			visione = tmp != 0;
		}
		if (column_list != null){
			if (column_list.containsKey("AFFITTO")){
				int tmp = c.getInt(c.getColumnIndex("AFFITTO"));
				affittabile = tmp != 0;
			}
		}else{
			int tmp = c.getInt(c.getColumnIndex("AFFITTO"));
			affittabile = tmp != 0;
		}	
		if (column_list != null){
			if (column_list.containsKey("CODAGENTEINSERITORE")){
				codAgenteInseritore = c.getInt(c.getColumnIndex("CODAGENTEINSERITORE"));
			}
		}else{
			codAgenteInseritore = c.getInt(c.getColumnIndex("CODAGENTEINSERITORE"));
		}
		if (column_list != null){
			if (column_list.containsKey("VARIE")){
				varie = c.getString(c.getColumnIndex("VARIE"));
			}
		}else{
			varie = c.getString(c.getColumnIndex("VARIE"));
		}
		
	}	
	
	public Integer getCodImmobile() {
		return codImmobile;
	}

	public void setCodImmobile(Integer codImmobile) {
		this.codImmobile = codImmobile;
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

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public Date getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public Date getDataLibero() {
		return dataLibero;
	}

	public void setDataLibero(Date dataLibero) {
		this.dataLibero = dataLibero;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(Double prezzo) {
		this.prezzo = prezzo;
	}

	public Double getMutuo() {
		return mutuo;
	}

	public void setMutuo(Double mutuo) {
		this.mutuo = mutuo;
	}

	public Double getSpese() {
		return spese;
	}

	public void setSpese(Double spese) {
		this.spese = spese;
	}

	public String getVarie() {
		return varie;
	}

	public void setVarie(String varie) {
		this.varie = varie;
	}

	public Boolean getVisione() {
		return visione;
	}

	public void setVisione(Boolean visione) {
		this.visione = visione;
	}

	public Boolean getStorico() {
		return storico;
	}

	public void setStorico(Boolean storico) {
		this.storico = storico;
	}

	public Integer getMq() {
		return mq;
	}

	public void setMq(Integer mq) {
		this.mq = mq;
	}


	public Integer getAnnoCostruzione() {
		return annoCostruzione;
	}

	public void setAnnoCostruzione(Integer annoCostruzione) {
		this.annoCostruzione = annoCostruzione;
	}

	public String getMutuoDescrizione() {
		return mutuoDescrizione;
	}

	public void setMutuoDescrizione(String mutuoDescrizione) {
		this.mutuoDescrizione = mutuoDescrizione;
	}

	public Integer getCodAgenteInseritore() {
		return codAgenteInseritore;
	}

	public void setCodAgenteInseritore(Integer codAgenteInseritore) {
		this.codAgenteInseritore = codAgenteInseritore;
	}

	public Integer getCodAnagrafica() {
		return codAnagrafica;
	}

	public void setCodAnagrafica(Integer codAnagrafica) {
		this.codAnagrafica = codAnagrafica;
	}

	public Integer getCodRiscaldamento() {
		return codRiscaldamento;
	}

	public void setCodRiscaldamento(Integer codRiscaldamento) {
		this.codRiscaldamento = codRiscaldamento;
	}

	public Integer getCodStato() {
		return codStato;
	}

	public void setCodStato(Integer codStato) {
		this.codStato = codStato;
	}

	public Integer getCodTipologia() {
		return codTipologia;
	}

	public void setCodTipologia(Integer codTipologia) {
		this.codTipologia = codTipologia;
	}

	public String getRif() {
		return rif;
	}

	public void setRif(String rif) {
		this.rif = rif;
	}

	public Boolean getAffittabile() {
		return affittabile;
	}

	public void setAffittabile(Boolean affittabile) {
		this.affittabile = affittabile;
	}

	public Integer getCodClasseEnergetica() {
		return codClasseEnergetica;
	}

	public void setCodClasseEnergetica(Integer codClasseEnergetica) {
		this.codClasseEnergetica = codClasseEnergetica;
	}
	
	protected static final XMLFormat<ImmobiliVO> IMMOBILI_XML = new XMLFormat<ImmobiliVO>(ImmobiliVO.class){
		
        public void write(ImmobiliVO i_xml, OutputElement xml)throws XMLStreamException {
        	xml.setAttribute("codImmobile", i_xml.getCodImmobile());
        	xml.setAttribute("rif", i_xml.getRif());
        	xml.setAttribute("annoCostruzione", i_xml.getAnnoCostruzione());
        	xml.setAttribute("indirizzo", i_xml.getIndirizzo());
        	xml.setAttribute("citta", i_xml.getCitta());
        	xml.setAttribute("zona", i_xml.getZona());
        	xml.setAttribute("provincia", i_xml.getProvincia());
        	xml.setAttribute("cap", i_xml.getCap());        	
        	xml.setAttribute("codAnagrafica", i_xml.getCodAnagrafica());
        	xml.setAttribute("storico", i_xml.getStorico());
        	xml.setAttribute("codAgenteInseritore", i_xml.getCodAgenteInseritore());
        	xml.setAttribute("codTipologia", i_xml.getCodTipologia());
        	xml.setAttribute("codStato", i_xml.getCodStato());
        	xml.setAttribute("codRiscaldamento", i_xml.getCodRiscaldamento());
        	xml.setAttribute("codClasseEnergetica", i_xml.getCodClasseEnergetica());
        	xml.setAttribute("dataInserimento", 
        					 ((i_xml.getDataInserimento() != null)
        					  ? i_xml.getDataInserimento().toString()
        					  : null));
        	xml.setAttribute("descrizione", i_xml.getDescrizione());
        	xml.setAttribute("prezzo", i_xml.getPrezzo());
        	xml.setAttribute("mutuo", i_xml.getMutuo());
        	xml.setAttribute("spese", i_xml.getSpese());
        	xml.setAttribute("varie", i_xml.getVarie());
        	xml.setAttribute("mutuoDescrizione", i_xml.getMutuoDescrizione());
        	xml.setAttribute("mq", i_xml.getMq());
        	xml.setAttribute("visione", i_xml.getVisione());
        	xml.setAttribute("affittabile", i_xml.getAffittabile());
        	xml.setAttribute("dataLibero", 
					 		 ((i_xml.getDataLibero() != null)
					 		  ? i_xml.getDataLibero().toString()
					 		  : null));

        }
                
        public void read(InputElement xml, ImmobiliVO i_xml) throws XMLStreamException{
        	i_xml.setCodImmobile(xml.getAttribute("codImmobile", 0));
        	i_xml.setRif(xml.getAttribute("rif", ""));
        	i_xml.setAnnoCostruzione(xml.getAttribute("annoCostruzione", 0));
        	i_xml.setIndirizzo(xml.getAttribute("indirizzo", ""));
        	i_xml.setCitta(xml.getAttribute("citta", ""));
        	i_xml.setZona(xml.getAttribute("zona", ""));
        	i_xml.setProvincia(xml.getAttribute("provincia", ""));
        	i_xml.setCap(xml.getAttribute("cap", ""));
        	i_xml.setCodAnagrafica(xml.getAttribute("codAnagrafica", 0));
        	i_xml.setStorico(xml.getAttribute("storico", false));
        	i_xml.setCodAgenteInseritore(xml.getAttribute("codAgenteInseritore", 0));
        	i_xml.setCodTipologia(xml.getAttribute("codTipologia", 0));
        	i_xml.setCodStato(xml.getAttribute("codStato", 0));
        	i_xml.setCodRiscaldamento(xml.getAttribute("codRiscaldamento", 0));
        	i_xml.setCodClasseEnergetica(xml.getAttribute("codClasseEnergetica", 0));
        	
        	Date datainserimento = null;
        	try {
        		datainserimento = DateFormatUtils.getInstace().parse_xml(xml.getAttribute("dataInserimento").toString());
			} catch (Exception e) {
				datainserimento = new Date();
			}        	
        	i_xml.setDataInserimento(datainserimento);
        	i_xml.setDescrizione(xml.getAttribute("descrizione", ""));
        	i_xml.setPrezzo(xml.getAttribute("prezzo", 0.0));
        	i_xml.setMutuo(xml.getAttribute("mutuo", 0.0));
        	i_xml.setSpese(xml.getAttribute("spese", 0.0));
        	i_xml.setVarie(xml.getAttribute("varie", ""));
        	i_xml.setMutuoDescrizione(xml.getAttribute("mutuoDescrizione", ""));
        	i_xml.setMq(xml.getAttribute("mq", 0));
        	i_xml.setAffittabile(xml.getAttribute("affittabile", false));
        	i_xml.setVisione(xml.getAttribute("visione", false));
        	
        	Date dataLibero = null;
        	try {
        		dataLibero = DateFormatUtils.getInstace().parse_xml(xml.getAttribute("dataLibero").toString());
			} catch (Exception e) {
				dataLibero = new Date();
			}
        	i_xml.setDataLibero(dataLibero);
        }
   };

   public ContentValues getContentValue(){
	    ContentValues values = new ContentValues();
	    values.put("CODIMMOBILE", getCodImmobile());
	    values.put("RIF", getRif());
	    values.put("INDIRIZZO", getIndirizzo());
	    values.put("PROVINCIA", getProvincia());
	    values.put("CAP", getCap());
	    values.put("CITTA", getCitta());
	    values.put("ZONA", getZona());
	    values.put("DATAINSERIMENTO", (getDataInserimento() != null)?getDataInserimento().toString():new Date().toString());
	    values.put("DATALIBERO", (getDataLibero() != null)?getDataLibero().toString():new Date().toString());
	    values.put("DESCRIZIONE", getDescrizione());
	    values.put("MUTUODESCRIZIONE", getMutuoDescrizione());
	    values.put("PREZZO", getPrezzo());
	    values.put("MUTUO", getMutuo());
	    values.put("SPESE", getSpese());
	    values.put("VARIE", getVarie());
	    values.put("VISIONE", getVisione());
	    values.put("STORICO", getStorico());
	    values.put("AFFITTO", getAffittabile());
	    values.put("MQ", getMq());
	    values.put("ANNOCOSTRUZIONE", getAnnoCostruzione());
	    values.put("CODAGENTEINSERITORE", getCodAgenteInseritore());
	    values.put("CODANAGRAFICA", getCodAnagrafica());
	    values.put("CODRISCALDAMENTO", getCodRiscaldamento());
	    values.put("CODSTATO", getCodStato());
	    values.put("CODTIPOLOGIA", getCodTipologia());
	    values.put("CODCLASSEENERGETICA", getCodClasseEnergetica());
	    return values;
   }
   
   public ArrayList<AnagraficheVO> getAnagrafichePropietarie(Context c,HashMap column_list){
	   if (anagrafichePropietarie == null){
		   if (codImmobile != null && codImmobile != 0){
			   DataBaseHelper dbh = new DataBaseHelper(c,DataBaseHelper.NONE_DB);
			   anagrafichePropietarie = dbh.getAnagrafichePropietarie(null, codImmobile);
		   }
	   }
	   return anagrafichePropietarie;
   }

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {

        if (codImmobile == null){
            dest.writeValue(null);
        }else{
            dest.writeInt(codImmobile);
        }
        if (rif == null){
            dest.writeValue(null);
        }else{
            dest.writeString(rif);
        }
        if (indirizzo == null){
            dest.writeValue(null);
        }else{
            dest.writeString(indirizzo);
        }
        if (provincia == null){
            dest.writeValue(null);
        }else{
            dest.writeString(provincia);
        }
        if (cap == null){
            dest.writeValue(null);
        }else{
            dest.writeString(cap);
        }
        if (citta == null){
            dest.writeValue(null);
        }else{
            dest.writeString(citta);
        }
        if (zona == null){
            dest.writeValue(null);
        }else{
            dest.writeString(zona);
        }
        if (dataInserimento == null){
            dest.writeValue(null);
        }else{
            dest.writeLong(dataInserimento.getTime());
        }
        if (dataLibero == null){
            dest.writeValue(null);
        }else{
            dest.writeLong(dataLibero.getTime());
        }
        if (descrizione == null){
            dest.writeValue(null);
        }else{
            dest.writeString(descrizione);
        }
        if (mutuoDescrizione == null){
            dest.writeValue(null);
        }else{
            dest.writeString(mutuoDescrizione);
        }
        if (prezzo == null){
            dest.writeValue(null);
        }else{
            dest.writeDouble(prezzo);
        }
		if (mutuo == null){
            dest.writeValue(null);
        }else{
            dest.writeDouble(mutuo);
        }
        if (spese == null){
            dest.writeValue(null);
        }else{
            dest.writeDouble(spese);
        }
        if (varie == null){
            dest.writeValue(null);
        }else {
            dest.writeString(varie);
        }
        if (visione == null){
            dest.writeValue(null);
        }else{
            dest.writeByte((byte) (visione ? 1 : 0));
        }
        if (storico == null){
            dest.writeValue(null);
        }else{
            dest.writeByte((byte) (storico ? 1 : 0));
        }
        if (affittabile == null){
            dest.writeValue(null);
        }else{
            dest.writeByte((byte) (affittabile ? 1 : 0));
        }
        if (mq == null){
            dest.writeValue(null);
        }else{
            dest.writeInt(mq);
        }
        if (annoCostruzione == null){
            dest.writeValue(null);
        }else{
            dest.writeInt(annoCostruzione);
        }
        if (codAgenteInseritore == null){
            dest.writeValue(null);
        }else{
            dest.writeInt(codAgenteInseritore);
        }
        if (codAnagrafica == null){
            dest.writeValue(null);
        }else{
            dest.writeInt(codAnagrafica);
        }
        if (codRiscaldamento == null){
            dest.writeValue(null);
        }else{
            dest.writeInt(codRiscaldamento);
        }
        if (codStato == null){
            dest.writeValue(null);
        }else{
            dest.writeInt(codStato);
        }
        if (codTipologia == null){
            dest.writeValue(null);
        }else{
            dest.writeInt(codTipologia);
        }
        if (codClasseEnergetica == null){
            dest.writeValue(null);
        }else{
            dest.writeInt(codClasseEnergetica);
        }
		
	}

}
