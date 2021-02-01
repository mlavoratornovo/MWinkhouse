package org.winkhouse.mwinkhouse.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javolution.xml.XMLObjectReader;
import javolution.xml.stream.XMLStreamException;

import org.winkhouse.mwinkhouse.activity.immobili.RicercaImmobiliActivity;
import org.winkhouse.mwinkhouse.models.AbbinamentiVO;
import org.winkhouse.mwinkhouse.models.AgentiVO;
import org.winkhouse.mwinkhouse.models.AnagraficheVO;
import org.winkhouse.mwinkhouse.models.AttributeVO;
import org.winkhouse.mwinkhouse.models.AttributeValueVO;
import org.winkhouse.mwinkhouse.models.ClasseEnergeticaVO;
import org.winkhouse.mwinkhouse.models.ClassiClientiVO;
import org.winkhouse.mwinkhouse.models.ColloquiAgentiVO;
import org.winkhouse.mwinkhouse.models.ColloquiAnagraficheVO;
import org.winkhouse.mwinkhouse.models.ColloquiVO;
import org.winkhouse.mwinkhouse.models.ContattiVO;
import org.winkhouse.mwinkhouse.models.DatiCatastaliVO;
import org.winkhouse.mwinkhouse.models.EntityVO;
import org.winkhouse.mwinkhouse.models.ImmagineVO;
import org.winkhouse.mwinkhouse.models.ImmobiliPropietariVO;
import org.winkhouse.mwinkhouse.models.ImmobiliVO;
import org.winkhouse.mwinkhouse.models.RiscaldamentiVO;
import org.winkhouse.mwinkhouse.models.StanzeImmobiliVO;
import org.winkhouse.mwinkhouse.models.StatoConservativoVO;
import org.winkhouse.mwinkhouse.models.TipologiaContattiVO;
import org.winkhouse.mwinkhouse.models.TipologiaStanzeVO;
import org.winkhouse.mwinkhouse.models.TipologieImmobiliVO;
import org.winkhouse.mwinkhouse.models.columns.AbbinamentiColumnNames;
import org.winkhouse.mwinkhouse.models.columns.AgentiColumnNames;
import org.winkhouse.mwinkhouse.models.columns.AnagraficheColumnNames;
import org.winkhouse.mwinkhouse.models.columns.ClasseEnergeticaColumnNames;
import org.winkhouse.mwinkhouse.models.columns.ClassiClienteColumnNames;
import org.winkhouse.mwinkhouse.models.columns.ColloquiAgentiColumnNames;
import org.winkhouse.mwinkhouse.models.columns.ColloquiAnagraficheColumnNames;
import org.winkhouse.mwinkhouse.models.columns.ColloquiColumnNames;
import org.winkhouse.mwinkhouse.models.columns.ContattiColumnNames;
import org.winkhouse.mwinkhouse.models.columns.DatiCatastaliColumnNames;
import org.winkhouse.mwinkhouse.models.columns.ImmagineColumnNames;
import org.winkhouse.mwinkhouse.models.columns.ImmobiliColumnNames;
import org.winkhouse.mwinkhouse.models.columns.ImmobiliPropietaColumnNames;
import org.winkhouse.mwinkhouse.models.columns.RiscaldamentiColumnNames;
import org.winkhouse.mwinkhouse.models.columns.StanzeImmobiliColumnNames;
import org.winkhouse.mwinkhouse.models.columns.StatoConservativoColumnNames;
import org.winkhouse.mwinkhouse.models.columns.TipologieContattiColumnNames;
import org.winkhouse.mwinkhouse.models.columns.TipologieImmobiliColumnNames;
import org.winkhouse.mwinkhouse.models.columns.TipologieStanzeColumnNames;
import org.winkhouse.mwinkhouse.util.SDFileSystemUtils;
import org.winkhouse.mwinkhouse.util.SysSettingNames;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;

public class ImportDataHelper {
	
	public final static String IMMOBILI_TAG = "immobili";
	public final static String IMMOBILI_FILENAME = "immobili.xml";
	public final static String IMMOBILI_GETCOD_METHODNAME = "getCodImmobile";
	
	public final static String ANAGRAFICHE_TAG = "anagrafiche";
	public final static String ANAGRAFICHE_FILENAME = "anagrafiche.xml";
	public final static String ANAGRAFICHE_GETCOD_METHODNAME = "getCodAnagrafica";

	public final static String AGENTI_TAG = "agenti";
	public final static String AGENTI_FILENAME = "agenti.xml";
	public final static String AGENTI_GETCOD_METHODNAME = "getCodAgente";
	
	public final static String RISCALDAMENTI_TAG = "riscaldamenti";
	public final static String RISCALDAMENTI_FILENAME = "riscaldamenti.xml";
	public final static String RISCALDAMENTI_GETCOD_METHODNAME = "getCodRiscaldamento";

	public final static String STANZEIMMOBILI_TAG = "stanzeimmobili";
	public final static String STANZEIMMOBILI_FILENAME = "stanzeimmobili.xml";
	public final static String STANZEIMMOBILI_GETCOD_METHODNAME = "getCodStanzeImmobili";

	public final static String STATOCONSERVATIVO_TAG = "statoconservativo";
	public final static String STATOCONSERVATIVO_FILENAME = "statoconservativo.xml";
	public final static String STATOCONSERVATIVO_GETCOD_METHODNAME = "getCodStatoConservativo";
	
	public final static String TIPOLOGIACONTATTO_TAG = "tipologiacontatti";
	public final static String TIPOLOGIACONTATTO_FILENAME = "tipologiacontatti.xml";
	public final static String TIPOLOGIACONTATTO_GETCOD_METHODNAME = "getCodTipologiaContatto";

	public final static String CONTATTI_TAG = "contatti";
	public final static String CONTATTI_FILENAME = "contatti.xml";
	public final static String CONTATTI_GETCOD_METHODNAME = "getCodContatto";
	
	public final static String TIPOLOGIAIMMOBILI_TAG = "tipologiaimmobili";
	public final static String TIPOLOGIAIMMOBILI_FILENAME = "tipologiaimmobili.xml";
	public final static String TIPOLOGIAIMMOBILI_GETCOD_METHODNAME = "getCodTipologiaImmobile";

	public final static String TIPOLOGIASTANZE_TAG = "tipologiastanze";
	public final static String TIPOLOGIASTANZE_FILENAME = "tipologiastanze.xml";
	public final static String TIPOLOGIASTANZE_GETCOD_METHODNAME = "getCodTipologiaStanza";

	public final static String IMMAGINE_TAG = "immagine";
	public final static String IMMAGINE_FILENAME = "immagine.xml";
	public final static String IMMAGINE_GETCOD_METHODNAME = "getCodImmagine";

	public final static String ABBINAMENTI_TAG = "abbinamenti";
	public final static String ABBINAMENTI_FILENAME = "abbinamenti.xml";
	public final static String ABBINAMENTI_GETCOD_METHODNAME = "getCodAbbinamento";

	public final static String CLASSEENERGETICA_TAG = "classeenergetica";
	public final static String CLASSEENERGETICA_FILENAME = "classeenergetica.xml";
	public final static String CLASSEENERGETICA_GETCOD_METHODNAME = "getCodClasseEnergetica";

	public final static String CLASSICLIENTI_TAG = "classiclienti";
	public final static String CLASSICLIENTI_FILENAME = "classiclienti.xml";
	public final static String CLASSICLIENTI_GETCOD_METHODNAME = "getCodClasseCliente";

	public final static String DATICATASTALI_TAG = "daticatastali";
	public final static String DATICATASTALI_FILENAME = "daticatastali.xml";
	public final static String DATICATASTALI_GETCOD_METHODNAME = "getCodDatiCatastali";

	public final static String ENTITA_TAG = "entita";
	public final static String ENTITA_FILENAME = "entita.xml";
	public final static String ENTITA_GETCOD_METHODNAME = "getIdClassEntity";

	public final static String ATTRIBUTI_TAG = "attributo";
	public final static String ATTRIBUTI_FILENAME = "attributi.xml";
	public final static String ATTRIBUTI_GETCOD_METHODNAME = "getIdAttribute";

	public final static String VALOREATTRIBUTO_TAG = "valoreattributo";
	public final static String VALOREATTRIBUTO_FILENAME = "valoriattributi.xml";
	public final static String VALOREATTRIBUTO_GETCOD_METHODNAME = "getIdValue";

	public final static String IMMOBILIPROPIETARI_TAG = "immobilipropietari";
	public final static String IMMOBILIPROPIETARI_FILENAME = "immobilipropietari.xml";
	public final static String IMMOBILIPROPIETARI_GETCOD_METHODNAME = "getKeyCode";

	public final static String COLLOQUI_TAG = "colloqui";
	public final static String COLLOQUI_FILENAME = "colloqui.xml";
	public final static String COLLOQUI_GETCOD_METHODNAME = "getCodColloquio";
	
	public final static String COLLOQUIANAGRAFICHE_TABLE_NAME = "colloquianagrafiche";
	public final static String COLLOQUIANAGRAFICHE_TAG = "colloqui_anagrafiche";
	public final static String COLLOQUIANAGRAFICHE_FILENAME = "colloqui_anagrafiche.xml";
	public final static String COLLOQUIANAGRAFICHE_GETCOD_METHODNAME = "getCodColloquioAnagrafiche";
	
	public final static String COLLOQUIAGENTI_TABLE_NAME = "colloquiagenti";
	public final static String COLLOQUIAGENTI_TAG = "colloqui_agenti";
	public final static String COLLOQUIAGENTI_FILENAME = "colloqui_agenti.xml";
	public final static String COLLOQUIAGENTI_GETCOD_METHODNAME = "getCodColloquioAgenti";

	public final static String UPDATE_METHOD_SOVRASCRIVI = "sovrascrivi";
	public final static String UPDATE_METHOD_AGGIUNGI = "aggiungi";
	public final static String UPDATE_METHOD_AGGIORNA = "aggiorna";

    private Context context = null;

    public void setContext(Context context) {
        this.context = context;
    }

    protected String importDirectory = null;
    protected String location = null;

    public static String baseImportPath = Environment.getExternalStorageDirectory() + File.separator + "winkhouse/import";
    public static String immaginiPath = Environment.getExternalStorageDirectory() + File.separator + "winkhouse/immagini";

	private HashMap<Integer,Integer> codImmobiliMap = null;
	private HashMap<Integer,Integer> codAnagraficheMap = null;
	private HashMap<Integer,Integer> codAgentiMap = null;
	private HashMap<Integer,Integer> codClasseEnergeticaMap = null;
	private HashMap<Integer,Integer> codClassiClienteMap = null;
	private HashMap<Integer,Integer> codRiscaldamentiMap = null;
	private HashMap<Integer,Integer> codStatoConservativoMap = null;
	private HashMap<Integer,Integer> codTipologiaContattoMap = null;
	private HashMap<Integer,Integer> codTipologiaImmobiliMap = null;
	private HashMap<Integer,Integer> codTipologiaStanzeMap = null;
	private HashMap<Integer,Integer> codEntityMap = null;
	private HashMap<Integer,Integer> codAttributeMap = null;
	private HashMap<Integer,Integer> codAbbinamentiMap = null;
	private HashMap<Integer,Integer> codContattiMap = null;
	private final HashMap<Integer,Integer> codDatiCatastaliMap = null;
	private HashMap<Integer,Integer> codImmaginiMap = null;
	private HashMap<Integer,Integer> codStanzeImmobiliMap = null;
    private HashMap<Integer,Integer> codCollouiMap = null;
    private HashMap<Integer,Integer> codCollouiAnagraficheMap = null;
    private HashMap<Integer,Integer> codCollouiAgentiMap = null;


	public ImportDataHelper(String extraImportPath, boolean overridebasepath){

	    if (overridebasepath == false) {
            location = (extraImportPath == null)
                    ? baseImportPath
                    : baseImportPath + File.separator + extraImportPath;

            importDirectory = (extraImportPath == null)
                    ? baseImportPath
                    : baseImportPath + File.separator + extraImportPath;
        }else{
            location = extraImportPath;
            importDirectory = extraImportPath;
        }

		codImmobiliMap = new HashMap<Integer, Integer>();
		codAnagraficheMap = new HashMap<Integer, Integer>();
		codAgentiMap = new HashMap<Integer, Integer>();
		codClasseEnergeticaMap = new HashMap<Integer, Integer>();
		codClassiClienteMap = new HashMap<Integer, Integer>();
		codRiscaldamentiMap = new HashMap<Integer, Integer>();
		codStatoConservativoMap = new HashMap<Integer, Integer>();
		codTipologiaContattoMap = new HashMap<Integer, Integer>();
		codTipologiaImmobiliMap = new HashMap<Integer, Integer>();
		codTipologiaStanzeMap = new HashMap<Integer, Integer>();
		codEntityMap = new HashMap<Integer, Integer>();
		codAttributeMap = new HashMap<Integer, Integer>();
		codAbbinamentiMap = new HashMap<Integer, Integer>();
		codContattiMap = new HashMap<Integer, Integer>();
		codImmaginiMap = new HashMap<Integer, Integer>();
		codStanzeImmobiliMap = new HashMap<Integer, Integer>();
        codCollouiMap = new HashMap<Integer, Integer>();
        codCollouiAnagraficheMap = new HashMap<Integer, Integer>();
        codCollouiAgentiMap = new HashMap<Integer, Integer>();
	}

	public String getDataUpdateMode(){

		String returnValue = null;
        if (this.context != null) {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.context);
            returnValue = sharedPref.getString(SysSettingNames.DATA_UPDATE_MODE, UPDATE_METHOD_SOVRASCRIVI);
        }else{
            returnValue = "";
        }

		return returnValue;

	}
	
	private HashMap loadFromFile(File exportFile,String xmlTag,Class classToDeserialize,String primaryKeyGetMethodName){
		
		HashMap returnValue = null;
		
		try {
			
			XMLObjectReader xmlor = XMLObjectReader.newInstance(new FileInputStream(exportFile));
			returnValue = new HashMap<Integer, Object>();
			while (xmlor.hasNext()){
				
				Object o = xmlor.read(xmlTag, classToDeserialize);
				try {
					Method m = o.getClass().getMethod(primaryKeyGetMethodName, (Class<?>[]) null);
					try {

						Integer codPrimarykey = (Integer)m.invoke(o);
						returnValue.put(codPrimarykey, o);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
			
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
				
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return returnValue;
		
	}

	private HashMap loadImmobiliObjs(){
		
		File immobiliFile = new File(this.importDirectory + File.separator + IMMOBILI_FILENAME);
		
		if (immobiliFile.exists()){
			return loadFromFile(immobiliFile, 
						 		IMMOBILI_TAG,
						 		ImmobiliVO.class,
						 		IMMOBILI_GETCOD_METHODNAME);
		}
		
		return null;
		
	}
	
	private HashMap loadAnagraficheObjs(){
		
		File anagraficheFile = new File(this.importDirectory + File.separator + ANAGRAFICHE_FILENAME);
		
		if (anagraficheFile.exists()){
			return loadFromFile(anagraficheFile, 
						 		ANAGRAFICHE_TAG,
						 		AnagraficheVO.class,
						 		ANAGRAFICHE_GETCOD_METHODNAME);
		}
		
		return null;
		
	}
	
	private HashMap loadAgentiObjs(){
		
		File agentiFile = new File(this.importDirectory + File.separator + AGENTI_FILENAME);
		
		if (agentiFile.exists()){
			return loadFromFile(agentiFile, 
						 		AGENTI_TAG,
						 		AgentiVO.class,
						 		AGENTI_GETCOD_METHODNAME);
		}
		
		return null;
		
	}

	private HashMap loadAbbinamentiObjs(){
		
		File abbinamentiFile = new File(this.importDirectory + File.separator + ABBINAMENTI_FILENAME);
		
		if (abbinamentiFile.exists()){
			return loadFromFile(abbinamentiFile, 
						 		ABBINAMENTI_TAG,
						 		AbbinamentiVO.class,
						 		ABBINAMENTI_GETCOD_METHODNAME);
		}
		
		return null;
		
	}

	private HashMap loadImmobiliPropietariObjs(){
		
		File immobilipropietariFile = new File(this.importDirectory + File.separator + IMMOBILIPROPIETARI_FILENAME);
		
		if (immobilipropietariFile.exists()){
			return loadFromFile(immobilipropietariFile, 
						 		IMMOBILIPROPIETARI_TAG,
						 		ImmobiliPropietariVO.class,
						 		IMMOBILIPROPIETARI_GETCOD_METHODNAME);
		}
		
		return null;
		
	}

	private HashMap loadClasseEnergeticaObjs(){
		
		File classeEnergeticaFile = new File(this.importDirectory + File.separator + CLASSEENERGETICA_FILENAME);
		
		if (classeEnergeticaFile.exists()){
			return loadFromFile(classeEnergeticaFile, 
						 		CLASSEENERGETICA_TAG,
						 		ClasseEnergeticaVO.class,
						 		CLASSEENERGETICA_GETCOD_METHODNAME);
		}
		
		return null;
		
	}
	
	private HashMap loadClassiClientiObjs(){
		
		File classiClientiFile = new File(this.importDirectory + File.separator + CLASSICLIENTI_FILENAME);
		
		if (classiClientiFile.exists()){
			return loadFromFile(classiClientiFile, 
						 		CLASSICLIENTI_TAG,
						 		ClassiClientiVO.class,
						 		CLASSICLIENTI_GETCOD_METHODNAME);
		}
		
		return null;
		
	}
	
	private HashMap loadContattiObjs(){
		
		File contattiFile = new File(this.importDirectory + File.separator + CONTATTI_FILENAME);
		
		if (contattiFile.exists()){
			return loadFromFile(contattiFile, 
						 		CONTATTI_TAG,
						 		ContattiVO.class,
						 		CONTATTI_GETCOD_METHODNAME);
		}
		
		return null;
		
	}

	private HashMap loadDatiCatastaliObjs(){
		
		File datiCatastaliFile = new File(this.importDirectory + File.separator + DATICATASTALI_FILENAME);
		
		if (datiCatastaliFile.exists()){
			return loadFromFile(datiCatastaliFile, 
						 		DATICATASTALI_TAG,
						 		DatiCatastaliVO.class,
						 		DATICATASTALI_GETCOD_METHODNAME);
		}
		
		return null;
		
	}

	private HashMap loadImmaginiObjs(){
		
		File immaginiFile = new File(this.importDirectory + File.separator + IMMAGINE_FILENAME);
		
		if (immaginiFile.exists()){
			return loadFromFile(immaginiFile, 
						 		IMMAGINE_TAG,
						 		ImmagineVO.class,
						 		IMMAGINE_GETCOD_METHODNAME);
		}
		
		return null;
		
	}

	private HashMap loadRiscaldamentiObjs(){
		
		File riscaldamentiFile = new File(this.importDirectory + File.separator + RISCALDAMENTI_FILENAME);
		
		if (riscaldamentiFile.exists()){
			return loadFromFile(riscaldamentiFile, 
						 		RISCALDAMENTI_TAG,
						 		RiscaldamentiVO.class,
						 		RISCALDAMENTI_GETCOD_METHODNAME);
		}
		
		return null;
		
	}

	private HashMap loadStanzeImmobiliObjs(){
		
		File stanzeImmobiliFile = new File(this.importDirectory + File.separator + STANZEIMMOBILI_FILENAME);
		
		if (stanzeImmobiliFile.exists()){
			return loadFromFile(stanzeImmobiliFile, 
						 		STANZEIMMOBILI_TAG,
						 		StanzeImmobiliVO.class,
						 		STANZEIMMOBILI_GETCOD_METHODNAME);
		}
		
		return null;
		
	}

	private HashMap loadStatoConservativoObjs(){
		
		File statoConservativoFile = new File(this.importDirectory + File.separator + STATOCONSERVATIVO_FILENAME);
		
		if (statoConservativoFile.exists()){
			return loadFromFile(statoConservativoFile, 
						 		STATOCONSERVATIVO_TAG,
						 		StatoConservativoVO.class,
						 		STATOCONSERVATIVO_GETCOD_METHODNAME);
		}
		
		return null;
		
	}

	private HashMap loadTipologiaContattoObjs(){
		
		File tipologiaContattoFile = new File(this.importDirectory + File.separator + TIPOLOGIACONTATTO_FILENAME);
		
		if (tipologiaContattoFile.exists()){
			return loadFromFile(tipologiaContattoFile, 
						 		TIPOLOGIACONTATTO_TAG,
						 		TipologiaContattiVO.class,
						 		TIPOLOGIACONTATTO_GETCOD_METHODNAME);
		}
		
		return null;
		
	}

	private HashMap loadTipologiaImmobiliObjs(){
		
		File tipologiaImmobiliFile = new File(this.importDirectory + File.separator + TIPOLOGIAIMMOBILI_FILENAME);
		
		if (tipologiaImmobiliFile.exists()){
			return loadFromFile(tipologiaImmobiliFile, 
						 		TIPOLOGIAIMMOBILI_TAG,
						 		TipologieImmobiliVO.class,
						 		TIPOLOGIAIMMOBILI_GETCOD_METHODNAME);
		}
		
		return null;
		
	}
	
	private HashMap loadTipologiaStanzeObjs(){
		
		File tipologiaStanzeFile = new File(this.importDirectory + File.separator + TIPOLOGIASTANZE_FILENAME);
		
		if (tipologiaStanzeFile.exists()){
			return loadFromFile(tipologiaStanzeFile, 
						 		TIPOLOGIASTANZE_TAG,
						 		TipologiaStanzeVO.class,
						 		TIPOLOGIASTANZE_GETCOD_METHODNAME);
		}
		
		return null;
		
	}

	private HashMap loadEntityObjs(){
		
		File entityFile = new File(this.importDirectory + File.separator + ENTITA_FILENAME);
		
		if (entityFile.exists()){
			return loadFromFile(entityFile, 
						 		ENTITA_TAG,
						 		EntityVO.class,
						 		ENTITA_GETCOD_METHODNAME);
		}
		
		return null;
		
	}
	
	private HashMap loadAttributeObjs(){
		
		File attributeFile = new File(this.importDirectory + File.separator + ATTRIBUTI_FILENAME);
		
		if (attributeFile.exists()){
			return loadFromFile(attributeFile, 
						 		ATTRIBUTI_TAG,
						 		TipologiaStanzeVO.class,
						 		ATTRIBUTI_GETCOD_METHODNAME);
		}
		
		return null;
		
	}
	
	protected HashMap loadAttributeValueObjs(){
		
		File attributeValueFile = new File(this.importDirectory + File.separator + VALOREATTRIBUTO_FILENAME);
		
		if (attributeValueFile.exists()){
			return loadFromFile(attributeValueFile, 
						 		VALOREATTRIBUTO_TAG,
						 		TipologiaStanzeVO.class,
						 		VALOREATTRIBUTO_GETCOD_METHODNAME);
		}
		
		return null;
		
	}

	protected HashMap loadColloquiObjs(){
		
		File colloquiFile = new File(this.importDirectory + File.separator + COLLOQUI_FILENAME);
		
		if (colloquiFile.exists()){
			return loadFromFile(colloquiFile, 
						 		COLLOQUI_TAG,
						 		ColloquiVO.class,
						 		COLLOQUI_GETCOD_METHODNAME);
		}
		
		return null;
		
	}

	protected HashMap loadColloquiAnagraficheObjs(){
		
		File colloquiAnagraficheFile = new File(this.importDirectory + File.separator + COLLOQUIANAGRAFICHE_FILENAME);
		
		if (colloquiAnagraficheFile.exists()){
			return loadFromFile(colloquiAnagraficheFile, 
						 		COLLOQUIANAGRAFICHE_TAG,
						 		ColloquiAnagraficheVO.class,
						 		COLLOQUIANAGRAFICHE_GETCOD_METHODNAME);
		}
		
		return null;
		
	}

	protected HashMap loadColloquiAgentiObjs(){
		
		File colloquiAgentiFile = new File(this.importDirectory + File.separator + COLLOQUIAGENTI_FILENAME);
		
		if (colloquiAgentiFile.exists()){
			return loadFromFile(colloquiAgentiFile, 
						 		COLLOQUIAGENTI_TAG,
						 		ColloquiAgentiVO.class,
						 		COLLOQUIAGENTI_GETCOD_METHODNAME);
		}
		
		return null;
		
	}

	protected String buildSearchQuery(String codeFieldName, String tableName, String codeMatchValue){

        String query = null;

        if (codeMatchValue != null) {
            query = "SELECT " + codeFieldName +
                    " FROM " + tableName.toUpperCase() +
                    " WHERE " + codeFieldName + " = " + codeMatchValue;
        }else{
            query = "SELECT " + codeFieldName +
                    " FROM " + tableName.toUpperCase() +
                    " ORDER BY " + codeFieldName + " DESC";
        }

        return query;
    }

	public void importImmobiliToDB(SQLiteDatabase sqldb){
				
		HashMap immobiliFromXML = loadImmobiliObjs();
		if (immobiliFromXML != null){

			Collection<ImmobiliVO> cImmobiliVOs = immobiliFromXML.values();
			Iterator<ImmobiliVO> it_immobili = cImmobiliVOs.iterator();

			if (getDataUpdateMode().equalsIgnoreCase("sovrascrivi") || getDataUpdateMode().equalsIgnoreCase("")){
				int result = sqldb.delete(IMMOBILI_TAG, null, null);
			}

			while(it_immobili.hasNext()){

				ImmobiliVO iVO = it_immobili.next();

				if (getDataUpdateMode().equalsIgnoreCase("sovrascrivi") || getDataUpdateMode().equalsIgnoreCase("aggiungi")) {

					if (getDataUpdateMode().equalsIgnoreCase("aggiungi")) {

                        String query = buildSearchQuery(ImmobiliColumnNames.CODIMMOBILE,
                                                        ImportDataHelper.IMMOBILI_TAG,
                                                        null);

						Cursor c = sqldb.rawQuery(query,null);

						if (c.moveToFirst()){

                            iVO.setCodClasseEnergetica(this.codClasseEnergeticaMap.get(iVO.getCodClasseEnergetica()));
                            iVO.setCodRiscaldamento(this.codRiscaldamentiMap.get(iVO.getCodRiscaldamento()));
                            iVO.setCodStato(this.codStatoConservativoMap.get(iVO.getCodStato()));
                            iVO.setCodTipologia(this.codTipologiaImmobiliMap.get(iVO.getCodTipologia()));

                            Integer oldcod = iVO.getCodImmobile();
                            Integer newcod = c.getInt(c.getColumnIndex(ImmobiliColumnNames.CODIMMOBILE)) + 1;
							iVO.setCodImmobile(newcod);
							iVO.setRif("MOB_" + iVO.getCodImmobile().toString());

                            this.codImmobiliMap.put(oldcod,iVO.getCodImmobile());
						}
						c.close();
					}
                    long new_id = sqldb.insert(IMMOBILI_TAG, null, iVO.getContentValue());

				}else if(getDataUpdateMode().equalsIgnoreCase("aggiorna")){

                    String query = buildSearchQuery(ImmobiliColumnNames.CODIMMOBILE,
                                                    ImportDataHelper.IMMOBILI_TAG,
                                                    iVO.getCodImmobile().toString());

                    Cursor c = sqldb.rawQuery(query,null);

                    if (c.moveToFirst()){

                        int codImmobile = c.getInt(c.getColumnIndex(ImmobiliColumnNames.CODIMMOBILE));
                        int rownum = sqldb.update(ImportDataHelper.IMMOBILI_TAG, iVO.getContentValue(), ImmobiliColumnNames.CODIMMOBILE + "=?", new String[]{String.valueOf(codImmobile)});

                    }else{
                        long new_id = sqldb.insert(IMMOBILI_TAG, null, iVO.getContentValue());
                    }
                    c.close();

                }else{
                    long new_id = sqldb.insert(IMMOBILI_TAG, null, iVO.getContentValue());
                }

			}
		}
				
	} 

	public void importAnagraficheToDB(SQLiteDatabase sqldb){
		
		HashMap anagraficheFromXML = loadAnagraficheObjs();
		if (anagraficheFromXML != null){
			Collection<AnagraficheVO> cAnagraficheVOs = anagraficheFromXML.values();
			Iterator<AnagraficheVO> it_anagrafiche = cAnagraficheVOs.iterator();

			if (getDataUpdateMode().equalsIgnoreCase("sovrascrivi") || getDataUpdateMode().equalsIgnoreCase("")){
                int result = sqldb.delete(ANAGRAFICHE_TAG, null, null);
            }
			
			while(it_anagrafiche.hasNext()){

				AnagraficheVO aVO = it_anagrafiche.next();

				if (getDataUpdateMode().equalsIgnoreCase("sovrascrivi") || getDataUpdateMode().equalsIgnoreCase("aggiungi")) {

					if (getDataUpdateMode().equalsIgnoreCase("aggiungi")) {

                        String query = buildSearchQuery(AnagraficheColumnNames.CODANAGRAFICA,
                                                        ImportDataHelper.ANAGRAFICHE_TAG,
                                                        null);

                        Cursor c = sqldb.rawQuery(query,null);

						if (c.moveToFirst()){

                            Integer oldcod = aVO.getCodAnagrafica();
                            Integer newcod = c.getInt(c.getColumnIndex(AnagraficheColumnNames.CODANAGRAFICA)) + 1;
							aVO.setCodAnagrafica(newcod);
							aVO.setCodClasseCliente(this.codClassiClienteMap.get(aVO.getCodClasseCliente()));
                            this.codAnagraficheMap.put(oldcod,aVO.getCodAnagrafica());

						}

						c.close();

					}

					long new_id = sqldb.insert(ANAGRAFICHE_TAG, null, aVO.getContentValue());

				}else if(getDataUpdateMode().equalsIgnoreCase("aggiorna")){

                    String query = buildSearchQuery(AnagraficheColumnNames.CODANAGRAFICA,
                                                    ImportDataHelper.ANAGRAFICHE_TAG,
                                                    aVO.getCodAnagrafica().toString());

                    Cursor c = sqldb.rawQuery(query,null);

					if (c.moveToFirst()){

						int codIAnagrafica = c.getInt(c.getColumnIndex(AnagraficheColumnNames.CODANAGRAFICA));
						int rownum = sqldb.update(ImportDataHelper.ANAGRAFICHE_TAG,
                                                  aVO.getContentValue(),
                                                  AnagraficheColumnNames.CODANAGRAFICA + "=?",
                                                  new String[]{String.valueOf(codIAnagrafica)});

					}else{
						long new_id = sqldb.insert(ANAGRAFICHE_TAG, null, aVO.getContentValue());
					}
					c.close();

				}else{

					long new_id = sqldb.insert(ANAGRAFICHE_TAG, null, aVO.getContentValue());

				}

			}
		}
				
	}
	
	public void importAgentiToDB(SQLiteDatabase sqldb){
		
		HashMap agentiFromXML = loadAgentiObjs();

		if (agentiFromXML != null){

			Collection<AgentiVO> cAgentiVOs = agentiFromXML.values();
			Iterator<AgentiVO> it_agenti = cAgentiVOs.iterator();

            if (getDataUpdateMode().equalsIgnoreCase("sovrascrivi") || getDataUpdateMode().equalsIgnoreCase("")) {
                sqldb.delete(AGENTI_TAG, null, null);
            }

			while(it_agenti.hasNext()){

				AgentiVO aVO = it_agenti.next();

				if (getDataUpdateMode().equalsIgnoreCase("sovrascrivi") || getDataUpdateMode().equalsIgnoreCase("aggiungi")) {

					if (getDataUpdateMode().equalsIgnoreCase("aggiungi")) {

                        String query = buildSearchQuery(AgentiColumnNames.CODAGENTE,
                                                        ImportDataHelper.AGENTI_TAG,
                                                        null);

                        Cursor c = sqldb.rawQuery(query,null);

						if (c.moveToFirst()){

                            Integer oldcod = aVO.getCodAgente();
                            Integer newcod = c.getInt(c.getColumnIndex(AgentiColumnNames.CODAGENTE)) + 1;
							aVO.setCodAgente(newcod);

							this.codAgentiMap.put(oldcod,aVO.getCodAgente());
						}

						c.close();

					}

					long new_id = sqldb.insert(AGENTI_TAG, null, aVO.getContentValue());

				}else if(getDataUpdateMode().equalsIgnoreCase("aggiorna")){

                    String query = buildSearchQuery(AgentiColumnNames.CODAGENTE,
                                                    ImportDataHelper.AGENTI_TAG,
                                                    aVO.getCodAgente().toString());

                    Cursor c = sqldb.rawQuery(query,null);

					if (c.moveToFirst()){

						int codIAgente = c.getInt(c.getColumnIndex(AgentiColumnNames.CODAGENTE));
						int rownum = sqldb.update(ImportDataHelper.AGENTI_TAG,
												  aVO.getContentValue(),
												  AgentiColumnNames.CODAGENTE + "=?",
												  new String[]{String.valueOf(codIAgente)});

					}else{
						long new_id = sqldb.insert(AGENTI_TAG, null, aVO.getContentValue());
					}
					c.close();

				}else{

					long new_id = sqldb.insert(AGENTI_TAG, null, aVO.getContentValue());

				}

			}
		}
	} 

	public void importClasseEnergeticaToDB(SQLiteDatabase sqldb){
		
		HashMap classeEnergeticaFromXML = loadClasseEnergeticaObjs();
		if (classeEnergeticaFromXML != null){

			Collection<ClasseEnergeticaVO> cClasseEnergeticaVOs = classeEnergeticaFromXML.values();
			Iterator<ClasseEnergeticaVO> it_ClasseEnergetica = cClasseEnergeticaVOs.iterator();

			if (getDataUpdateMode().equalsIgnoreCase("sovrascrivi") || getDataUpdateMode().equalsIgnoreCase("")) {
				sqldb.delete(CLASSEENERGETICA_TAG, null, null);
			}
			
			while(it_ClasseEnergetica.hasNext()){

				ClasseEnergeticaVO cVO = it_ClasseEnergetica.next();

				if (getDataUpdateMode().equalsIgnoreCase("sovrascrivi") || getDataUpdateMode().equalsIgnoreCase("aggiungi")) {

					if (getDataUpdateMode().equalsIgnoreCase("aggiungi")) {

                        String query = buildSearchQuery(ClasseEnergeticaColumnNames.CODCLASSEENERGETICA,
                                                        ImportDataHelper.CLASSEENERGETICA_TAG,
                                                        null);

                        Cursor c = sqldb.rawQuery(query,null);

						if (c.moveToFirst()){

                            Integer oldcod = cVO.getCodClasseEnergetica();
                            Integer newcod = c.getInt(c.getColumnIndex(ClasseEnergeticaColumnNames.CODCLASSEENERGETICA)) + 1;
							cVO.setCodClasseEnergetica(newcod);

							this.codClasseEnergeticaMap.put(oldcod,cVO.getCodClasseEnergetica());
						}

						c.close();

					}

					long new_id = sqldb.insert(CLASSEENERGETICA_TAG, null, cVO.getContentValue());

				}else if(getDataUpdateMode().equalsIgnoreCase("aggiorna")){

                    String query = buildSearchQuery(ClasseEnergeticaColumnNames.CODCLASSEENERGETICA,
                                                    ImportDataHelper.CLASSEENERGETICA_TAG,
                                                    cVO.getCodClasseEnergetica().toString());

                    Cursor c = sqldb.rawQuery(query,null);

					if (c.moveToFirst()){

						int codClasseEnergetica = c.getInt(c.getColumnIndex(ClasseEnergeticaColumnNames.CODCLASSEENERGETICA));
						int rownum = sqldb.update(ImportDataHelper.CLASSEENERGETICA_TAG,
												  cVO.getContentValue(),
												  ClasseEnergeticaColumnNames.CODCLASSEENERGETICA + "=?",
												  new String[]{String.valueOf(codClasseEnergetica)});

					}else{
						long new_id = sqldb.insert(CLASSEENERGETICA_TAG, null, cVO.getContentValue());
					}
					c.close();

				}else{

					long new_id = sqldb.insert(CLASSEENERGETICA_TAG, null, cVO.getContentValue());

				}

			}
		}
				
	} 

	public void importAbbinamentiToDB(SQLiteDatabase sqldb){
		
		HashMap abbinamentiFromXML = loadAbbinamentiObjs();

		if (abbinamentiFromXML != null){

			Collection<AbbinamentiVO> cAbbinamentiVOs = abbinamentiFromXML.values();
			Iterator<AbbinamentiVO> it_Abbinamenti = cAbbinamentiVOs.iterator();

			if (getDataUpdateMode().equalsIgnoreCase("sovrascrivi") || getDataUpdateMode().equalsIgnoreCase("")) {
				sqldb.delete(ABBINAMENTI_TAG, null, null);
			}
			
			while(it_Abbinamenti.hasNext()){
				
				AbbinamentiVO aVO = it_Abbinamenti.next();

				if (getDataUpdateMode().equalsIgnoreCase("sovrascrivi") || getDataUpdateMode().equalsIgnoreCase("aggiungi")) {

					if (getDataUpdateMode().equalsIgnoreCase("aggiungi")) {

                        String query = buildSearchQuery(AbbinamentiColumnNames.CODABBINAMENTO,
                                                        ImportDataHelper.ABBINAMENTI_TAG,
                                                        null);

                        Cursor c = sqldb.rawQuery(query,null);

						if (c.moveToFirst()){

                            Integer oldcod = aVO.getCodAbbinamento();
                            Integer newcod = c.getInt(c.getColumnIndex(AbbinamentiColumnNames.CODABBINAMENTO)) + 1;

							aVO.setCodAbbinamento(newcod);
							aVO.setCodAnagrafica(this.codAnagraficheMap.get(aVO.getCodAnagrafica()));
							aVO.setCodImmobile(this.codImmobiliMap.get(aVO.getCodImmobile()));

							this.codAbbinamentiMap.put(oldcod,aVO.getCodAbbinamento());
						}

						c.close();

					}

					long new_id = sqldb.insert(ABBINAMENTI_TAG, null, aVO.getContentValue());

				}else if(getDataUpdateMode().equalsIgnoreCase("aggiorna")){

                    String query = buildSearchQuery(AbbinamentiColumnNames.CODABBINAMENTO,
                                                    ImportDataHelper.ABBINAMENTI_TAG,
                                                    aVO.getCodAbbinamento().toString());

                    Cursor c = sqldb.rawQuery(query,null);

					if (c.moveToFirst()){

						int codAbbinamento = c.getInt(c.getColumnIndex(AbbinamentiColumnNames.CODABBINAMENTO));
						int rownum = sqldb.update(ImportDataHelper.ABBINAMENTI_TAG,
												  aVO.getContentValue(),
												  AbbinamentiColumnNames.CODABBINAMENTO + "=?",
												  new String[]{String.valueOf(codAbbinamento)});

					}else{
						long new_id = sqldb.insert(ABBINAMENTI_TAG, null, aVO.getContentValue());
					}
					c.close();

				}else{

					long new_id = sqldb.insert(ABBINAMENTI_TAG, null, aVO.getContentValue());

				}

			}
		}
				
	} 

	public void importClassiClienteToDB(SQLiteDatabase sqldb){
		
		HashMap classiClienteFromXML = loadClassiClientiObjs();
		if (classiClienteFromXML != null){
			Collection<ClassiClientiVO> cClassiClienteFromXMLVOs = classiClienteFromXML.values();
			Iterator<ClassiClientiVO> it_ClassiCliente = cClassiClienteFromXMLVOs.iterator();

			if (getDataUpdateMode().equalsIgnoreCase("sovrascrivi") || getDataUpdateMode().equalsIgnoreCase("")) {
				sqldb.delete(CLASSICLIENTI_TAG, null, null);
			}
			
			while(it_ClassiCliente.hasNext()){
				
				ClassiClientiVO ccVO = it_ClassiCliente.next();

				if (getDataUpdateMode().equalsIgnoreCase("sovrascrivi") || getDataUpdateMode().equalsIgnoreCase("aggiungi")) {

					if (getDataUpdateMode().equalsIgnoreCase("aggiungi")) {

                        String query = buildSearchQuery(ClassiClienteColumnNames.CODCLASSECLIENTE,
                                                        ImportDataHelper.CLASSICLIENTI_TAG,
                                                        null);

                        Cursor c = sqldb.rawQuery(query,null);

						if (c.moveToFirst()){

                            Integer oldcod = ccVO.getCodClasseCliente();
                            Integer newcod = c.getInt(c.getColumnIndex(ClassiClienteColumnNames.CODCLASSECLIENTE)) + 1;
							ccVO.setCodClasseCliente(newcod);
							this.codClassiClienteMap.put(oldcod,ccVO.getCodClasseCliente());
						}

						c.close();

					}

					long new_id = sqldb.insert(CLASSICLIENTI_TAG, null, ccVO.getContentValue());

				}else if(getDataUpdateMode().equalsIgnoreCase("aggiorna")){

                    String query = buildSearchQuery(ClassiClienteColumnNames.CODCLASSECLIENTE,
                                                    ImportDataHelper.CLASSICLIENTI_TAG,
                                                    ccVO.getCodClasseCliente().toString());

                    Cursor c = sqldb.rawQuery(query,null);

					if (c.moveToFirst()){

						int codClasseCliente = c.getInt(c.getColumnIndex(ClassiClienteColumnNames.CODCLASSECLIENTE));
						int rownum = sqldb.update(ImportDataHelper.CLASSICLIENTI_TAG,
												  ccVO.getContentValue(),
												  ClassiClienteColumnNames.CODCLASSECLIENTE + "=?",
												  new String[]{String.valueOf(codClasseCliente)});

					}else{
						long new_id = sqldb.insert(CLASSICLIENTI_TAG, null, ccVO.getContentValue());
					}
					c.close();

				}else{

					long new_id = sqldb.insert(CLASSICLIENTI_TAG, null, ccVO.getContentValue());

				}

			}
		}
	} 

	public void importRiscaldamentiToDB(SQLiteDatabase sqldb){
		
		HashMap riscaldamentiFromXML = loadRiscaldamentiObjs();
		if (riscaldamentiFromXML != null){
			Collection<RiscaldamentiVO> cRiscaldamentiFromXMLVOs = riscaldamentiFromXML.values();
			Iterator<RiscaldamentiVO> it_Riscaldamenti = cRiscaldamentiFromXMLVOs.iterator();

			if (getDataUpdateMode().equalsIgnoreCase("sovrascrivi") || getDataUpdateMode().equalsIgnoreCase("")) {
				sqldb.delete(RISCALDAMENTI_TAG, null, null);
			}
			
			while(it_Riscaldamenti.hasNext()){
				
				RiscaldamentiVO rVO = it_Riscaldamenti.next();

				if (getDataUpdateMode().equalsIgnoreCase("sovrascrivi") || getDataUpdateMode().equalsIgnoreCase("aggiungi")) {

					if (getDataUpdateMode().equalsIgnoreCase("aggiungi")) {

                        String query = buildSearchQuery(RiscaldamentiColumnNames.CODRISCALDAMENTO,
                                                        ImportDataHelper.RISCALDAMENTI_TAG,
                                                        null);

                        Cursor c = sqldb.rawQuery(query,null);

						if (c.moveToFirst()){

                            Integer oldcod = rVO.getCodRiscaldamento();
                            Integer newcod = c.getInt(c.getColumnIndex(RiscaldamentiColumnNames.CODRISCALDAMENTO)) + 1;
							rVO.setCodRiscaldamento(newcod);
							this.codRiscaldamentiMap.put(oldcod,rVO.getCodRiscaldamento());
						}

						c.close();

					}

					long new_id = sqldb.insert(RISCALDAMENTI_TAG, null, rVO.getContentValue());

				}else if(getDataUpdateMode().equalsIgnoreCase("aggiorna")){

                    String query = buildSearchQuery(RiscaldamentiColumnNames.CODRISCALDAMENTO,
                                                    ImportDataHelper.RISCALDAMENTI_TAG,
                                                    rVO.getCodRiscaldamento().toString());

                    Cursor c = sqldb.rawQuery(query,null);

					if (c.moveToFirst()){

						int codRiscaldamento = c.getInt(c.getColumnIndex(RiscaldamentiColumnNames.CODRISCALDAMENTO));
						int rownum = sqldb.update(ImportDataHelper.RISCALDAMENTI_TAG,
												  rVO.getContentValue(),
												  RiscaldamentiColumnNames.CODRISCALDAMENTO + "=?",
												  new String[]{String.valueOf(codRiscaldamento)});

					}else{
						long new_id = sqldb.insert(RISCALDAMENTI_TAG, null, rVO.getContentValue());
					}
					c.close();

				}else{

					long new_id = sqldb.insert(RISCALDAMENTI_TAG, null, rVO.getContentValue());

				}

			}
		}
	}

	public void importStatoConservativoToDB(SQLiteDatabase sqldb){
		
		HashMap statoConservativoFromXML = loadStatoConservativoObjs();

		if (statoConservativoFromXML != null){

			Collection<StatoConservativoVO> cStatoConservativoFromXMLVOs = statoConservativoFromXML.values();
			Iterator<StatoConservativoVO> it_StatoConservativo = cStatoConservativoFromXMLVOs.iterator();

			if (getDataUpdateMode().equalsIgnoreCase("sovrascrivi") || getDataUpdateMode().equalsIgnoreCase("")) {
				sqldb.delete(STATOCONSERVATIVO_TAG, null, null);
			}
			
			while(it_StatoConservativo.hasNext()){
				
				StatoConservativoVO scVO = it_StatoConservativo.next();

				if (getDataUpdateMode().equalsIgnoreCase("sovrascrivi") || getDataUpdateMode().equalsIgnoreCase("aggiungi")) {

					if (getDataUpdateMode().equalsIgnoreCase("aggiungi")) {

                        String query = buildSearchQuery(StatoConservativoColumnNames.CODSTATOCONSERVATIVO,
                                                        ImportDataHelper.STATOCONSERVATIVO_TAG,
                                                        null);

                        Cursor c = sqldb.rawQuery(query,null);

						if (c.moveToFirst()){

                            Integer oldcod = scVO.getCodStatoConservativo();
                            Integer newcod = c.getInt(c.getColumnIndex(StatoConservativoColumnNames.CODSTATOCONSERVATIVO)) + 1;
							scVO.setCodStatoConservativo(newcod);
							this.codStatoConservativoMap.put(oldcod,scVO.getCodStatoConservativo());
						}

						c.close();

					}

					long new_id = sqldb.insert(STATOCONSERVATIVO_TAG, null, scVO.getContentValue());

				}else if(getDataUpdateMode().equalsIgnoreCase("aggiorna")){

                    String query = buildSearchQuery(StatoConservativoColumnNames.CODSTATOCONSERVATIVO,
                                                    ImportDataHelper.STATOCONSERVATIVO_TAG,
                                                    scVO.getCodStatoConservativo().toString());

                    Cursor c = sqldb.rawQuery(query,null);

					if (c.moveToFirst()){

						int codStatoConservativo = c.getInt(c.getColumnIndex(StatoConservativoColumnNames.CODSTATOCONSERVATIVO));
						int rownum = sqldb.update(ImportDataHelper.STATOCONSERVATIVO_TAG,
												  scVO.getContentValue(),
												  StatoConservativoColumnNames.CODSTATOCONSERVATIVO + "=?",
												  new String[]{String.valueOf(codStatoConservativo)});

					}else{
						long new_id = sqldb.insert(STATOCONSERVATIVO_TAG, null, scVO.getContentValue());
					}
					c.close();

				}else{

					long new_id = sqldb.insert(STATOCONSERVATIVO_TAG, null, scVO.getContentValue());

				}


			}

		}
				
	} 

	public void importTipologiaContattiToDB(SQLiteDatabase sqldb){
		
		HashMap tipologiaContattiFromXML = loadTipologiaContattoObjs();

		if (tipologiaContattiFromXML != null){

			Collection<TipologiaContattiVO> cTipologiaContattiFromXMLVOs = tipologiaContattiFromXML.values();
			Iterator<TipologiaContattiVO> it_TipologiaContatti = cTipologiaContattiFromXMLVOs.iterator();

			if (getDataUpdateMode().equalsIgnoreCase("sovrascrivi") || getDataUpdateMode().equalsIgnoreCase("")) {
				sqldb.delete(TIPOLOGIACONTATTO_TAG, null, null);
			}

			while(it_TipologiaContatti.hasNext()){
				
				TipologiaContattiVO tcVO = it_TipologiaContatti.next();

				if (getDataUpdateMode().equalsIgnoreCase("sovrascrivi") || getDataUpdateMode().equalsIgnoreCase("aggiungi")) {

					if (getDataUpdateMode().equalsIgnoreCase("aggiungi")) {

                        String query = buildSearchQuery(TipologieContattiColumnNames.CODTIPOLOGIACONTATTO,
                                                        ImportDataHelper.TIPOLOGIACONTATTO_TAG,
                                                        null);

                        Cursor c = sqldb.rawQuery(query,null);

						if (c.moveToFirst()){

                            Integer oldcod = tcVO.getCodTipologiaContatto();
                            Integer newcod = c.getInt(c.getColumnIndex(TipologieContattiColumnNames.CODTIPOLOGIACONTATTO)) + 1;
							tcVO.setCodTipologiaContatto(newcod);
							this.codTipologiaContattoMap.put(oldcod,tcVO.getCodTipologiaContatto());
						}

						c.close();

					}

					long new_id = sqldb.insert(TIPOLOGIACONTATTO_TAG, null, tcVO.getContentValue());

				}else if(getDataUpdateMode().equalsIgnoreCase("aggiorna")){

                    String query = buildSearchQuery(TipologieContattiColumnNames.CODTIPOLOGIACONTATTO,
                                                    ImportDataHelper.TIPOLOGIACONTATTO_TAG,
                                                    tcVO.getCodTipologiaContatto().toString());

                    Cursor c = sqldb.rawQuery(query,null);

					if (c.moveToFirst()){

						int codTipologiaContatto = c.getInt(c.getColumnIndex(TipologieContattiColumnNames.CODTIPOLOGIACONTATTO));
						int rownum = sqldb.update(ImportDataHelper.TIPOLOGIACONTATTO_TAG,
												  tcVO.getContentValue(),
												  TipologieContattiColumnNames.CODTIPOLOGIACONTATTO + "=?",
												  new String[]{String.valueOf(codTipologiaContatto)});

					}else{
						long new_id = sqldb.insert(TIPOLOGIACONTATTO_TAG, null, tcVO.getContentValue());
					}
					c.close();

				}else{

					long new_id = sqldb.insert(TIPOLOGIACONTATTO_TAG, null, tcVO.getContentValue());

				}

			}

		}
				
	}
	
	public void importTipologieImmobiliToDB(SQLiteDatabase sqldb){
		
		HashMap tipologieImmobiliFromXML = loadTipologiaImmobiliObjs();

		if (tipologieImmobiliFromXML != null){

			Collection<TipologieImmobiliVO> cTipologieImmobiliFromXMLVOs = tipologieImmobiliFromXML.values();
			Iterator<TipologieImmobiliVO> it_TipologieImmobili = cTipologieImmobiliFromXMLVOs.iterator();

			if (getDataUpdateMode().equalsIgnoreCase("sovrascrivi") || getDataUpdateMode().equalsIgnoreCase("")) {
				sqldb.delete(TIPOLOGIAIMMOBILI_TAG, null, null);
			}

			while(it_TipologieImmobili.hasNext()){
				
				TipologieImmobiliVO tiVO = it_TipologieImmobili.next();

				if (getDataUpdateMode().equalsIgnoreCase("sovrascrivi") || getDataUpdateMode().equalsIgnoreCase("aggiungi")) {

					if (getDataUpdateMode().equalsIgnoreCase("aggiungi")) {

                        String query = buildSearchQuery(TipologieImmobiliColumnNames.CODTIPOLOGIAIMMOBILE,
                                                        ImportDataHelper.TIPOLOGIAIMMOBILI_TAG,
                                                        null);

                        Cursor c = sqldb.rawQuery(query,null);

						if (c.moveToFirst()){

                            Integer oldcod = tiVO.getCodTipologiaImmobile();
                            Integer newcod = c.getInt(c.getColumnIndex(TipologieImmobiliColumnNames.CODTIPOLOGIAIMMOBILE)) + 1;
							tiVO.setCodTipologiaImmobile(newcod);
							this.codTipologiaImmobiliMap.put(oldcod,tiVO.getCodTipologiaImmobile());
						}

						c.close();

					}

					long new_id = sqldb.insert(TIPOLOGIAIMMOBILI_TAG, null, tiVO.getContentValue());

				}else if(getDataUpdateMode().equalsIgnoreCase("aggiorna")){

                    String query = buildSearchQuery(TipologieImmobiliColumnNames.CODTIPOLOGIAIMMOBILE,
                                                    ImportDataHelper.TIPOLOGIAIMMOBILI_TAG,
                                                    tiVO.getCodTipologiaImmobile().toString());

                    Cursor c = sqldb.rawQuery(query,null);

					if (c.moveToFirst()){

						int codTipologiaImmobile = c.getInt(c.getColumnIndex(TipologieImmobiliColumnNames.CODTIPOLOGIAIMMOBILE));
						int rownum = sqldb.update(ImportDataHelper.TIPOLOGIAIMMOBILI_TAG,
												  tiVO.getContentValue(),
												  TipologieImmobiliColumnNames.CODTIPOLOGIAIMMOBILE + "=?",
												  new String[]{String.valueOf(codTipologiaImmobile)});

					}else{
						long new_id = sqldb.insert(TIPOLOGIAIMMOBILI_TAG, null, tiVO.getContentValue());
					}
					c.close();

				}else{

					long new_id = sqldb.insert(TIPOLOGIAIMMOBILI_TAG, null, tiVO.getContentValue());

				}

			}
		}
	} 
	
	public void importTipologiaStanzeToDB(SQLiteDatabase sqldb){
		
		HashMap tipologiaStanzeFromXML = loadTipologiaStanzeObjs();
		
		if (tipologiaStanzeFromXML != null){
			
			Collection<TipologiaStanzeVO> cTipologiaStanzeFromXMLVOs = tipologiaStanzeFromXML.values();
			Iterator<TipologiaStanzeVO> it_TipologiaStanze = cTipologiaStanzeFromXMLVOs.iterator();

			if (getDataUpdateMode().equalsIgnoreCase("sovrascrivi") || getDataUpdateMode().equalsIgnoreCase("")) {
				sqldb.delete(TIPOLOGIASTANZE_TAG, null, null);
			}
			
			while(it_TipologiaStanze.hasNext()){
				
				TipologiaStanzeVO tsVO = it_TipologiaStanze.next();

				if (getDataUpdateMode().equalsIgnoreCase("sovrascrivi") || getDataUpdateMode().equalsIgnoreCase("aggiungi")) {

					if (getDataUpdateMode().equalsIgnoreCase("aggiungi")) {

                        String query = buildSearchQuery(TipologieStanzeColumnNames.CODTIPOLOGIASTANZA,
                                                        ImportDataHelper.TIPOLOGIASTANZE_TAG,
                                                        null);

                        Cursor c = sqldb.rawQuery(query,null);

						if (c.moveToFirst()){

                            Integer oldcod = tsVO.getCodTipologiaStanza();
                            Integer newcod = c.getInt(c.getColumnIndex(TipologieStanzeColumnNames.CODTIPOLOGIASTANZA)) + 1;
							tsVO.setCodTipologiaStanza(newcod);
							this.codTipologiaImmobiliMap.put(oldcod,tsVO.getCodTipologiaStanza());
						}

						c.close();

					}

					long new_id = sqldb.insert(TIPOLOGIASTANZE_TAG, null, tsVO.getContentValue());

				}else if(getDataUpdateMode().equalsIgnoreCase("aggiorna")){

                    String query = buildSearchQuery(TipologieStanzeColumnNames.CODTIPOLOGIASTANZA,
                                                    ImportDataHelper.TIPOLOGIASTANZE_TAG,
                                                    tsVO.getCodTipologiaStanza().toString());

                    Cursor c = sqldb.rawQuery(query,null);

					if (c.moveToFirst()){

						int codTipologiaImmobile = c.getInt(c.getColumnIndex(TipologieStanzeColumnNames.CODTIPOLOGIASTANZA));
						int rownum = sqldb.update(ImportDataHelper.TIPOLOGIASTANZE_TAG,
												  tsVO.getContentValue(),
												  TipologieStanzeColumnNames.CODTIPOLOGIASTANZA + "=?",
												  new String[]{String.valueOf(codTipologiaImmobile)});

					}else{
						long new_id = sqldb.insert(TIPOLOGIASTANZE_TAG, null, tsVO.getContentValue());
					}
					c.close();

				}else{

					long new_id = sqldb.insert(TIPOLOGIASTANZE_TAG, null, tsVO.getContentValue());

				}
				
			}
		}
	} 

	public void importContattiToDB(SQLiteDatabase sqldb){
		
		HashMap contattiFromXML = loadContattiObjs();

		if (contattiFromXML != null){

			Collection<ContattiVO> cContattiFromXMLVOs = contattiFromXML.values();
			Iterator<ContattiVO> it_Contatti = cContattiFromXMLVOs.iterator();

			if (getDataUpdateMode().equalsIgnoreCase("sovrascrivi") || getDataUpdateMode().equalsIgnoreCase("")) {
				sqldb.delete(CONTATTI_TAG, null, null);
			}
			
			while(it_Contatti.hasNext()){
				
				ContattiVO cVO = it_Contatti.next();

				if (getDataUpdateMode().equalsIgnoreCase("sovrascrivi") || getDataUpdateMode().equalsIgnoreCase("aggiungi")) {

					if (getDataUpdateMode().equalsIgnoreCase("aggiungi")) {

                        String query = buildSearchQuery(ContattiColumnNames.CODCONTATTO,
                                                        ImportDataHelper.CONTATTI_TAG,
                                                        null);

                        Cursor c = sqldb.rawQuery(query,null);

						if (c.moveToFirst()){

                            Integer oldcod = cVO.getCodContatto();
                            Integer newcod = c.getInt(c.getColumnIndex(ContattiColumnNames.CODCONTATTO)) + 1;
							cVO.setCodContatto(newcod);
							cVO.setCodTipologiaContatto(codTipologiaContattoMap.get(cVO.getCodTipologiaContatto()));
							cVO.setCodAgente(codAgentiMap.get(cVO.getCodAgente()));
							cVO.setCodAnagrafica(codAnagraficheMap.get(cVO.getCodAnagrafica()));

							this.codContattiMap.put(oldcod,cVO.getCodContatto());
						}

						c.close();

					}

					long new_id = sqldb.insert(CONTATTI_TAG, null, cVO.getContentValue());

				}else if(getDataUpdateMode().equalsIgnoreCase("aggiorna")){

                    String query = buildSearchQuery(ContattiColumnNames.CODCONTATTO,
                                                    ImportDataHelper.CONTATTI_TAG,
                                                    cVO.getCodContatto().toString());

                    Cursor c = sqldb.rawQuery(query,null);

					if (c.moveToFirst()){

						int codContatto = c.getInt(c.getColumnIndex(ContattiColumnNames.CODCONTATTO));
						int rownum = sqldb.update(ImportDataHelper.CONTATTI_TAG,
												  cVO.getContentValue(),
											      ContattiColumnNames.CODCONTATTO+ "=?",
												  new String[]{String.valueOf(codContatto)});

					}else{
						long new_id = sqldb.insert(CONTATTI_TAG, null, cVO.getContentValue());
					}
					c.close();

				}else{

					long new_id = sqldb.insert(CONTATTI_TAG, null, cVO.getContentValue());

				}

			}

		}
				
	} 

	public void importDatiCatastaliToDB(SQLiteDatabase sqldb){
		
		HashMap datiCatastaliFromXML = loadDatiCatastaliObjs();

		if (datiCatastaliFromXML != null){

			Collection<DatiCatastaliVO> cDatiCatastaliFromXMLVOs = datiCatastaliFromXML.values();
			Iterator<DatiCatastaliVO> it_DatiCatastali = cDatiCatastaliFromXMLVOs.iterator();

			if (getDataUpdateMode().equalsIgnoreCase("sovrascrivi") || getDataUpdateMode().equalsIgnoreCase("")) {
				sqldb.delete(DATICATASTALI_TAG, null, null);
			}
			
			while(it_DatiCatastali.hasNext()){
				
				DatiCatastaliVO dcVO = it_DatiCatastali.next();

				if (getDataUpdateMode().equalsIgnoreCase("sovrascrivi") || getDataUpdateMode().equalsIgnoreCase("aggiungi")) {

					if (getDataUpdateMode().equalsIgnoreCase("aggiungi")) {

                        String query = buildSearchQuery(DatiCatastaliColumnNames.CODDATICATASTALI,
                                                        ImportDataHelper.DATICATASTALI_TAG,
                                                        null);

                        Cursor c = sqldb.rawQuery(query,null);

						if (c.moveToFirst()){

                            Integer oldcod = dcVO.getCodDatiCatastali();
                            Integer newcod = c.getInt(c.getColumnIndex(DatiCatastaliColumnNames.CODDATICATASTALI)) + 1;
							dcVO.setCodDatiCatastali(newcod);
							dcVO.setCodImmobile(codImmobiliMap.get(dcVO.getCodImmobile()));
							this.codDatiCatastaliMap.put(oldcod,dcVO.getCodDatiCatastali());
						}

						c.close();

					}

					long new_id = sqldb.insert(DATICATASTALI_TAG, null, dcVO.getContentValue());

				}else if(getDataUpdateMode().equalsIgnoreCase("aggiorna")){

                    String query = buildSearchQuery(DatiCatastaliColumnNames.CODDATICATASTALI,
                                                    ImportDataHelper.DATICATASTALI_TAG,
                                                    dcVO.getCodDatiCatastali().toString());

                    Cursor c = sqldb.rawQuery(query,null);

					if (c.moveToFirst()){

						int codContatto = c.getInt(c.getColumnIndex(DatiCatastaliColumnNames.CODDATICATASTALI));
						int rownum = sqldb.update(ImportDataHelper.DATICATASTALI_TAG,
												  dcVO.getContentValue(),
												  DatiCatastaliColumnNames.CODDATICATASTALI + "=?",
												  new String[]{String.valueOf(codContatto)});

					}else{
						long new_id = sqldb.insert(DATICATASTALI_TAG, null, dcVO.getContentValue());
					}
					c.close();

				}else{

					long new_id = sqldb.insert(DATICATASTALI_TAG, null, dcVO.getContentValue());

				}

			}
		}
				
	} 

	public void importImmaginiToDB(SQLiteDatabase sqldb){

        File fimmaginiDir = new File(immaginiPath);
        fimmaginiDir.mkdirs();

		HashMap immaginiFromXML = loadImmaginiObjs();
		if (immaginiFromXML != null){
			Collection<ImmagineVO> cimmaginiFromXMLVOs = immaginiFromXML.values();
			Iterator<ImmagineVO> it_immagine = cimmaginiFromXMLVOs.iterator();

			if (getDataUpdateMode().equalsIgnoreCase("sovrascrivi") || getDataUpdateMode().equalsIgnoreCase("")) {
				sqldb.delete(IMMAGINE_TAG, null, null);
			}

			while(it_immagine.hasNext()){
				
				ImmagineVO iVO = it_immagine.next();

				if (getDataUpdateMode().equalsIgnoreCase("sovrascrivi") || getDataUpdateMode().equalsIgnoreCase("aggiungi")) {

					if (getDataUpdateMode().equalsIgnoreCase("aggiungi")) {

                        String query = buildSearchQuery(ImmagineColumnNames.CODIMMAGINE,
                                                        ImportDataHelper.IMMAGINE_TAG,
                                                        null);

                        Cursor c = sqldb.rawQuery(query,null);

						if (c.moveToFirst()){

                            Integer oldcod = iVO.getCodImmagine();
                            Integer newcod = c.getInt(c.getColumnIndex(ImmagineColumnNames.CODIMMAGINE)) + 1;
							iVO.setCodImmagine(newcod);
							iVO.setCodImmobile(codImmobiliMap.get(iVO.getCodImmobile()));

							this.codImmaginiMap.put(oldcod,iVO.getCodImmagine());

							File img = new File(location + File.separator + "immagini" + File.separator + oldcod + File.separator + iVO.getPathImmagine());

							if (img.exists()){
								SDFileSystemUtils sdfsu = new SDFileSystemUtils();

								File newimg = new File(immaginiPath + File.separator + codImmobiliMap.get(oldcod) + File.separator + iVO.getPathImmagine());
								sdfsu.copyFile(img,newimg);
							}

						}

						c.close();

					}else {

                        File img = new File(location + File.separator + "immagini" + File.separator + iVO.getCodImmobile() + File.separator + iVO.getPathImmagine());

                        if (img.exists()){

                            SDFileSystemUtils sdfsu = new SDFileSystemUtils();

                            File newimgdir = new File(immaginiPath + File.separator + iVO.getCodImmobile());
                            newimgdir.mkdirs();

                            File newimg = new File(immaginiPath + File.separator + iVO.getCodImmobile() + File.separator + iVO.getPathImmagine());
                            sdfsu.copyFile(img,newimg);

                        }

                        long new_id = sqldb.insert(IMMAGINE_TAG, null, iVO.getContentValue());

                    }

				}else if(getDataUpdateMode().equalsIgnoreCase("aggiorna")){

                    String query = buildSearchQuery(ImmagineColumnNames.CODIMMAGINE,
                                                    ImportDataHelper.IMMAGINE_TAG,
                                                    iVO.getCodImmagine().toString());

                    Cursor c = sqldb.rawQuery(query,null);
					if (c.moveToFirst()){

                        File img = new File(location + File.separator + "immagini" + File.separator + iVO.getCodImmobile() + File.separator + iVO.getPathImmagine());

                        if (img.exists()){
                            SDFileSystemUtils sdfsu = new SDFileSystemUtils();

                            File newimg = new File(immaginiPath + File.separator + iVO.getCodImmobile() + File.separator + iVO.getPathImmagine());
                            sdfsu.copyFile(img,newimg);
                        }

						int codImmagine = c.getInt(c.getColumnIndex(ImmagineColumnNames.CODIMMAGINE));
						int rownum = sqldb.update(ImportDataHelper.IMMAGINE_TAG,
												  iVO.getContentValue(),
												  ImmagineColumnNames.CODIMMAGINE + "=?",
												  new String[]{String.valueOf(codImmagine)});

					}else{
						long new_id = sqldb.insert(IMMAGINE_TAG, null, iVO.getContentValue());
					}
					c.close();

				}else {

					long new_id = sqldb.insert(IMMAGINE_TAG, null, iVO.getContentValue());

				}
	
			}

		}
				
	}

	public void importImmobiliPropietariToDB(SQLiteDatabase sqldb){
		
		HashMap immobiliPropietariFromXML = loadImmobiliPropietariObjs();

		if (immobiliPropietariFromXML != null){

			Collection<ImmobiliPropietariVO> cimmobiliPropietariFromXMLVOs = immobiliPropietariFromXML.values();
			Iterator<ImmobiliPropietariVO> it_immobiliPropietari = cimmobiliPropietariFromXMLVOs.iterator();

			if (getDataUpdateMode().equalsIgnoreCase("sovrascrivi") || getDataUpdateMode().equalsIgnoreCase("")) {
				sqldb.delete(IMMOBILIPROPIETARI_TAG, null, null);
			}

			while(it_immobiliPropietari.hasNext()){
				
				ImmobiliPropietariVO iVO = it_immobiliPropietari.next();

				if (getDataUpdateMode().equalsIgnoreCase("sovrascrivi") || getDataUpdateMode().equalsIgnoreCase("aggiungi")) {

					if (getDataUpdateMode().equalsIgnoreCase("aggiungi")) {

						iVO.setCodAnagrafica(codAnagraficheMap.get(iVO.getCodAnagrafica()));
						iVO.setCodImmobile(codImmobiliMap.get(iVO.getCodImmobile()));

					}

					long new_id = sqldb.insert(IMMOBILIPROPIETARI_TAG, null, iVO.getContentValue());

				}else if(getDataUpdateMode().equalsIgnoreCase("aggiorna")){

                    String query = "SELECT " + ImmobiliPropietaColumnNames.CODIMMOBILE +","+ImmobiliPropietaColumnNames.CODANAGRAFICA +
                                   " FROM " +  ImportDataHelper.IMMOBILIPROPIETARI_TAG.toUpperCase() +
                                   " WHERE " + ImmobiliPropietaColumnNames.CODIMMOBILE + " = " + iVO.getCodImmobile().toString() +
                                   " AND " + ImmobiliPropietaColumnNames.CODANAGRAFICA + " = " + iVO.getCodAnagrafica().toString();

                    Cursor c = sqldb.rawQuery(query,null);

					if (!c.moveToFirst()){

						long new_id = sqldb.insert(IMMOBILIPROPIETARI_TAG, null, iVO.getContentValue());

					}
					c.close();

				}else {

					long new_id = sqldb.insert(IMMOBILIPROPIETARI_TAG, null, iVO.getContentValue());

				}

			}
		}
				
	}	
	
	public void importStanzeImmobiliToDB(SQLiteDatabase sqldb){
		
		HashMap stanzeImmobiliFromXML = loadStanzeImmobiliObjs();
		if (stanzeImmobiliFromXML != null){

			Collection<StanzeImmobiliVO> cstanzeImmobiliFromXMLVOs = stanzeImmobiliFromXML.values();
			Iterator<StanzeImmobiliVO> it_stanzeImmobili = cstanzeImmobiliFromXMLVOs.iterator();

			if (getDataUpdateMode().equalsIgnoreCase("sovrascrivi") || getDataUpdateMode().equalsIgnoreCase("")) {
				sqldb.delete(STANZEIMMOBILI_TAG, null, null);
			}

			while(it_stanzeImmobili.hasNext()){
				
				StanzeImmobiliVO siVO = it_stanzeImmobili.next();

				if (getDataUpdateMode().equalsIgnoreCase("sovrascrivi") || getDataUpdateMode().equalsIgnoreCase("aggiungi")) {

					if (getDataUpdateMode().equalsIgnoreCase("aggiungi")) {

                        String query = buildSearchQuery(StanzeImmobiliColumnNames.CODSTANZEIMMOBILI,
                                                        ImportDataHelper.STANZEIMMOBILI_TAG,
                                                        null);

                        Cursor c = sqldb.rawQuery(query,null);

						if (c.moveToFirst()){

							Integer oldcod = siVO.getCodStanzeImmobili();
                            Integer newcod = c.getInt(c.getColumnIndex(StanzeImmobiliColumnNames.CODSTANZEIMMOBILI)) + 1;
							siVO.setCodStanzeImmobili(newcod);
							siVO.setCodImmobile(codImmobiliMap.get(siVO.getCodImmobile()));

							this.codStanzeImmobiliMap.put(oldcod,siVO.getCodStanzeImmobili());
						}

						c.close();

					}

					long new_id = sqldb.insert(STANZEIMMOBILI_TAG, null, siVO.getContentValue());

				}else if(getDataUpdateMode().equalsIgnoreCase("aggiorna")){

                    String query = buildSearchQuery(StanzeImmobiliColumnNames.CODSTANZEIMMOBILI,
                                                    ImportDataHelper.STANZEIMMOBILI_TAG,
                                                    siVO.getCodStanzeImmobili().toString());

                    Cursor c = sqldb.rawQuery(query,null);

					if (c.moveToFirst()){

						int codstanza = c.getInt(c.getColumnIndex(StanzeImmobiliColumnNames.CODSTANZEIMMOBILI));
						int rownum = sqldb.update(ImportDataHelper.STANZEIMMOBILI_TAG,
												  siVO.getContentValue(),
												  StanzeImmobiliColumnNames.CODSTANZEIMMOBILI + "=?",
												  new String[]{String.valueOf(codstanza)});

					}else{
						long new_id = sqldb.insert(STANZEIMMOBILI_TAG, null, siVO.getContentValue());
					}
					c.close();

				}else {

					long new_id = sqldb.insert(STANZEIMMOBILI_TAG, null, siVO.getContentValue());

				}

			}

		}
				
	} 
	
	public void importEntityToDB(SQLiteDatabase sqldb){
		
		HashMap entityFromXML = loadEntityObjs();
		if (entityFromXML != null){
			Collection<EntityVO> centityFromXMLVOs = entityFromXML.values();
			Iterator<EntityVO> it_entity = centityFromXMLVOs.iterator();
			
			sqldb.delete(ENTITA_TAG, null, null);
			
			while(it_entity.hasNext()){
				
				EntityVO eVO = it_entity.next();					
					
				long new_id = sqldb.insert(ENTITA_TAG, null, eVO.getContentValue());
	
			}
		}
				
	} 

	public void importAttributeToDB(SQLiteDatabase sqldb){
		
		HashMap attributeFromXML = loadAttributeObjs();
		if (attributeFromXML != null){
			Collection<AttributeVO> cattributeFromXMLVOs = attributeFromXML.values();
			Iterator<AttributeVO> it_attribute = cattributeFromXMLVOs.iterator();
			
			sqldb.delete(ATTRIBUTI_TAG, null, null);
			
			while(it_attribute.hasNext()){
				
				AttributeVO aVO = it_attribute.next();					
				long new_id = sqldb.insert(ATTRIBUTI_TAG, null, aVO.getContentValue());
	
			}
		}
	} 

	public void importAttributeValueToDB(SQLiteDatabase sqldb){
		
		HashMap attributeValueFromXML = loadAttributeValueObjs();
		if (attributeValueFromXML != null){
			Collection<AttributeValueVO> cattributeValueFromXMLVOs = attributeValueFromXML.values();
			Iterator<AttributeValueVO> it_attributeValue = cattributeValueFromXMLVOs.iterator();
			
			sqldb.delete(VALOREATTRIBUTO_TAG, null, null);
			
			while(it_attributeValue.hasNext()){
				
				AttributeValueVO aVO = it_attributeValue.next();					
				long new_id = sqldb.insert(VALOREATTRIBUTO_TAG, null, aVO.getContentValue());
				
			}
		}				
	}

	public void importColloquiToDB(SQLiteDatabase sqldb){
		
		HashMap colloquiFromXML = loadColloquiObjs();
		if (colloquiFromXML != null){
			Collection<ColloquiVO> ccolloquiFromXMLVOs = colloquiFromXML.values();
			Iterator<ColloquiVO> it_colloqui = ccolloquiFromXMLVOs.iterator();

			if (getDataUpdateMode().equalsIgnoreCase("sovrascrivi") || getDataUpdateMode().equalsIgnoreCase("")) {
				sqldb.delete(COLLOQUI_TAG, null, null);
			}

			while(it_colloqui.hasNext()){
				
				ColloquiVO cVO = it_colloqui.next();

				if (getDataUpdateMode().equalsIgnoreCase("sovrascrivi") || getDataUpdateMode().equalsIgnoreCase("aggiungi")) {

					if (getDataUpdateMode().equalsIgnoreCase("aggiungi")) {

                        String query = buildSearchQuery(ColloquiColumnNames.CODCOLLOQUIO,
                                                        ImportDataHelper.COLLOQUI_TAG,
                                                        null);

                        Cursor c = sqldb.rawQuery(query,null);

						if (c.moveToFirst()){

							Integer oldcod = cVO.getCodColloquio();
                            Integer newcod = c.getInt(c.getColumnIndex(ColloquiColumnNames.CODCOLLOQUIO)) + 1;
                            cVO.setCodColloquio(newcod);
							cVO.setCodImmobileAbbinato(codImmobiliMap.get(cVO.getCodImmobileAbbinato()));

							this.codCollouiMap.put(oldcod,cVO.getCodColloquio());
						}

						c.close();

					}

					long new_id = sqldb.insert(COLLOQUI_TAG, null, cVO.getContentValue());

				}else if(getDataUpdateMode().equalsIgnoreCase("aggiorna")){

                    String query = buildSearchQuery(ColloquiColumnNames.CODCOLLOQUIO,
                                                    ImportDataHelper.COLLOQUI_TAG,
                                                    cVO.getCodColloquio().toString());

                    Cursor c = sqldb.rawQuery(query,null);

					if (c.moveToFirst()){

						int codcolloquio = c.getInt(c.getColumnIndex(ColloquiColumnNames.CODCOLLOQUIO));
						int rownum = sqldb.update(ImportDataHelper.COLLOQUI_TAG,
								                  cVO.getContentValue(),
                                                  ColloquiColumnNames.CODCOLLOQUIO + "=?",
								                  new String[]{String.valueOf(codcolloquio)});

					}else{
						long new_id = sqldb.insert(COLLOQUI_TAG, null, cVO.getContentValue());
					}
					c.close();

				}else {

					long new_id = sqldb.insert(COLLOQUI_TAG, null, cVO.getContentValue());

				}

			}
		}				
	}

	public void importColloquiAnagraficheToDB(SQLiteDatabase sqldb){
		
		HashMap colloquiAnagraficheFromXML = loadColloquiAnagraficheObjs();
		if (colloquiAnagraficheFromXML != null){
			Collection<ColloquiAnagraficheVO> ccolloquiAnagraficheFromXMLVOs = colloquiAnagraficheFromXML.values();
			Iterator<ColloquiAnagraficheVO> it_colloquianagrafiche = ccolloquiAnagraficheFromXMLVOs.iterator();

            if (getDataUpdateMode().equalsIgnoreCase("sovrascrivi") || getDataUpdateMode().equalsIgnoreCase("")) {
                sqldb.delete(COLLOQUIANAGRAFICHE_TABLE_NAME, null, null);
            }

			while(it_colloquianagrafiche.hasNext()){
				
				ColloquiAnagraficheVO caVO = it_colloquianagrafiche.next();

                if (getDataUpdateMode().equalsIgnoreCase("sovrascrivi") || getDataUpdateMode().equalsIgnoreCase("aggiungi")) {

                    if (getDataUpdateMode().equalsIgnoreCase("aggiungi")) {

                        String query = buildSearchQuery(ColloquiAnagraficheColumnNames.CODCOLLOQUIANAGRAFICHE,
                                                        ImportDataHelper.COLLOQUIANAGRAFICHE_TABLE_NAME,
                                                        null);
                        Cursor c = sqldb.rawQuery(query,null);
                        if (c.moveToFirst()){

                            Integer oldcod = caVO.getCodColloquioAnagrafiche();
                            Integer newcod = c.getInt(c.getColumnIndex(ColloquiAnagraficheColumnNames.CODCOLLOQUIANAGRAFICHE)) + 1;
                            caVO.setCodColloquioAnagrafiche(newcod);
                            caVO.setCodColloquio(codCollouiMap.get(caVO.getCodColloquio()));
                            caVO.setCodAnagrafica(codAnagraficheMap.get(caVO.getCodAnagrafica()));

                            this.codCollouiAnagraficheMap.put(oldcod,caVO.getCodColloquioAnagrafiche());
                        }

                        c.close();

                    }

                    long new_id = sqldb.insert(COLLOQUIANAGRAFICHE_TABLE_NAME, null, caVO.getContentValue());

                }else if(getDataUpdateMode().equalsIgnoreCase("aggiorna")){

                    String query = buildSearchQuery(ColloquiAnagraficheColumnNames.CODCOLLOQUIANAGRAFICHE,
                                                    ImportDataHelper.COLLOQUIANAGRAFICHE_TABLE_NAME,
                                                    caVO.getCodColloquioAnagrafiche().toString());

                    Cursor c = sqldb.rawQuery(query,null);

                    if (c.moveToFirst()){

                        int codcolloquio = c.getInt(c.getColumnIndex(ColloquiAnagraficheColumnNames.CODCOLLOQUIANAGRAFICHE));
                        int rownum = sqldb.update(ImportDataHelper.COLLOQUIANAGRAFICHE_TABLE_NAME,
                                                  caVO.getContentValue(),
                                                  ColloquiAnagraficheColumnNames.CODCOLLOQUIANAGRAFICHE + "=?",
                                                  new String[]{String.valueOf(codcolloquio)});

                    }else{
                        long new_id = sqldb.insert(COLLOQUIANAGRAFICHE_TABLE_NAME, null, caVO.getContentValue());
                    }
                    c.close();

                }else {

                    long new_id = sqldb.insert(COLLOQUIANAGRAFICHE_TABLE_NAME, null, caVO.getContentValue());

                }
				
			}
		}				
	}

	public void importColloquiAgentiToDB(SQLiteDatabase sqldb){
		
		HashMap colloquiagentiFromXML = loadColloquiAgentiObjs();

		if (colloquiagentiFromXML != null){

			Collection<ColloquiAgentiVO> ccolloquiagentiFromXMLVOs = colloquiagentiFromXML.values();
			Iterator<ColloquiAgentiVO> it_colloquiagenti = ccolloquiagentiFromXMLVOs.iterator();

            if (getDataUpdateMode().equalsIgnoreCase("sovrascrivi") || getDataUpdateMode().equalsIgnoreCase("")) {
                sqldb.delete(COLLOQUIAGENTI_TABLE_NAME, null, null);
            }
			
			while(it_colloquiagenti.hasNext()){
				
				ColloquiAgentiVO aVO = it_colloquiagenti.next();

                if (getDataUpdateMode().equalsIgnoreCase("sovrascrivi") || getDataUpdateMode().equalsIgnoreCase("aggiungi")) {

                    if (getDataUpdateMode().equalsIgnoreCase("aggiungi")) {

                        String query = buildSearchQuery(ColloquiAgentiColumnNames.CODCOLLOQUIOAGENTE,
                                                        ImportDataHelper.COLLOQUIAGENTI_TABLE_NAME,
                                                        null);

                        Cursor c = sqldb.rawQuery(query,null);

                        if (c.moveToFirst()){

                            Integer oldcod = aVO.getCodColloquioAgenti();
                            Integer newcod = c.getInt(c.getColumnIndex(ColloquiAgentiColumnNames.CODCOLLOQUIOAGENTE)) + 1;
                            aVO.setCodColloquioAgenti(newcod);
                            aVO.setCodColloquio(codCollouiMap.get(aVO.getCodColloquio()));
                            aVO.setCodAgente(codAnagraficheMap.get(aVO.getCodAgente()));

                            this.codCollouiAgentiMap.put(oldcod,aVO.getCodColloquioAgenti());
                        }

                        c.close();

                    }

                    long new_id = sqldb.insert(COLLOQUIAGENTI_TABLE_NAME, null, aVO.getContentValue());

                }else if(getDataUpdateMode().equalsIgnoreCase("aggiorna")){

                    String query = buildSearchQuery(ColloquiAgentiColumnNames.CODCOLLOQUIOAGENTE,
                                                    ImportDataHelper.COLLOQUIAGENTI_TABLE_NAME,
                                                    aVO.getCodColloquioAgenti().toString());

                    Cursor c = sqldb.rawQuery(query,null);

                    if (c.moveToFirst()){

                        int codcolloquio = c.getInt(c.getColumnIndex(ColloquiAgentiColumnNames.CODCOLLOQUIOAGENTE));
                        int rownum = sqldb.update(ImportDataHelper.COLLOQUIAGENTI_TABLE_NAME,
                                                  aVO.getContentValue(),
                                                  ColloquiAgentiColumnNames.CODCOLLOQUIOAGENTE + "=?",
                                                  new String[]{String.valueOf(codcolloquio)});

                    }else{
                        long new_id = sqldb.insert(COLLOQUIAGENTI_TABLE_NAME, null, aVO.getContentValue());
                    }
                    c.close();

                }else {

                    long new_id = sqldb.insert(COLLOQUIAGENTI_TABLE_NAME, null, aVO.getContentValue());

                }


				
			}
		}				
	}

	public String getImportDirectory() {
		return importDirectory;
	}

	public void setImportDirectory(String importDirectory) {
		this.importDirectory = importDirectory;
	} 

}
