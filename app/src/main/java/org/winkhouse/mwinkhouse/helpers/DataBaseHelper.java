package org.winkhouse.mwinkhouse.helpers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.winkhouse.mwinkhouse.models.AnagraficheVO;
import org.winkhouse.mwinkhouse.models.ClasseEnergeticaVO;
import org.winkhouse.mwinkhouse.models.ClassiClientiVO;
import org.winkhouse.mwinkhouse.models.ColloquiAnagraficheVO;
import org.winkhouse.mwinkhouse.models.ColloquiVO;
import org.winkhouse.mwinkhouse.models.ContattiVO;
import org.winkhouse.mwinkhouse.models.ImmagineVO;
import org.winkhouse.mwinkhouse.models.ImmobiliPropietariVO;
import org.winkhouse.mwinkhouse.models.ImmobiliVO;
import org.winkhouse.mwinkhouse.models.RiscaldamentiVO;
import org.winkhouse.mwinkhouse.models.StanzeImmobiliVO;
import org.winkhouse.mwinkhouse.models.StatoConservativoVO;
import org.winkhouse.mwinkhouse.models.SysSettingVO;
import org.winkhouse.mwinkhouse.models.TipologiaContattiVO;
import org.winkhouse.mwinkhouse.models.TipologiaStanzeVO;
import org.winkhouse.mwinkhouse.models.TipologieColloquiVO;
import org.winkhouse.mwinkhouse.models.TipologieImmobiliVO;
import org.winkhouse.mwinkhouse.models.columns.AnagraficheColumnNames;
import org.winkhouse.mwinkhouse.models.columns.ClasseEnergeticaColumnNames;
import org.winkhouse.mwinkhouse.models.columns.ClassiClienteColumnNames;
import org.winkhouse.mwinkhouse.models.columns.ColloquiAgentiColumnNames;
import org.winkhouse.mwinkhouse.models.columns.ColloquiAnagraficheColumnNames;
import org.winkhouse.mwinkhouse.models.columns.ColloquiColumnNames;
import org.winkhouse.mwinkhouse.models.columns.ContattiColumnNames;
import org.winkhouse.mwinkhouse.models.columns.ImmagineColumnNames;
import org.winkhouse.mwinkhouse.models.columns.ImmobiliColumnNames;
import org.winkhouse.mwinkhouse.models.columns.ImmobiliPropietaColumnNames;
import org.winkhouse.mwinkhouse.models.columns.RiscaldamentiColumnNames;
import org.winkhouse.mwinkhouse.models.columns.StanzeImmobiliColumnNames;
import org.winkhouse.mwinkhouse.models.columns.StatoConservativoColumnNames;
import org.winkhouse.mwinkhouse.models.columns.TipologieContattiColumnNames;
import org.winkhouse.mwinkhouse.models.columns.TipologieImmobiliColumnNames;
import org.winkhouse.mwinkhouse.models.columns.TipologieStanzeColumnNames;
import org.winkhouse.mwinkhouse.util.DateFormatUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {
		
	private static final String LOG = "DatabaseHelper"; 
	
	private static final int DATABASE_VERSION = 3;//ufficiale 3
	
	private static final String DATABASE_NAME = "winkhouse";
	
	private static final String CREATE_TABLE_IMMOBILI = "CREATE TABLE " +
            ImportDataHelper.IMMOBILI_TAG + "(CODIMMOBILE INTEGER PRIMARY KEY, CODAGENTEINSERITORE INTEGER," + 
            "CODANAGRAFICA INTEGER, CODCLASSEENERGETICA INTEGER, CODRISCALDAMENTO INTEGER," + 
            "CODSTATO INTEGER, CODTIPOLOGIA INTEGER, ANNOCOSTRUZIONE INTEGER, DATAINSERIMENTO TEXT, CAP TEXT, DATALIBERO TEXT," + 
            "DESCRIZIONE TEXT, INDIRIZZO TEXT, MQ INTEGER, MUTUO REAL, MUTUODESCRIZIONE TEXT," + 
            "PREZZO REAL, CITTA TEXT, PROVINCIA TEXT, RIF TEXT, SPESE REAL, STORICO INTEGER, VARIE TEXT,VISIONE INTEGER," + 
            "AFFITTO INTEGER, ZONA TEXT)";
	
	private static final String CREATE_TABLE_ANAGRAFICHE = "CREATE TABLE " +
			ImportDataHelper.ANAGRAFICHE_TAG + "(CODANAGRAFICA INTEGER PRIMARY KEY, CODAGENTEINSERITORE INTEGER," +
			"CODCLASSECLIENTE INTEGER, CAP TEXT, CITTA TEXT, CODICEFISCALE TEXT, COGNOME TEXT, NOME TEXT, RAGSOC TEXT, COMMENTO TEXT," + 
			"DATAINSERIMENTO TEXT, INDIRIZZO TEXT, PIVA TEXT, PROVINCIA TEXT, STORICO INTEGER)";
	
	private static final String CREATE_TABLE_AGENTI = "CREATE TABLE " +
			ImportDataHelper.AGENTI_TAG + "(CODAGENTE INTEGER PRIMARY KEY, CAP TEXT, CITTA TEXT, COGNOME TEXT, INDIRIZZO TEXT," +
			"NOME TEXT, PROVINCIA TEXT)";
	
	private static final String CREATE_TABLE_ABBINAMENTI = "CREATE TABLE " +
			ImportDataHelper.ABBINAMENTI_TAG + "(CODABBINAMENTO INTEGER PRIMARY KEY, CODANAGRAFICA INTEGER, CODIMMOBILE INTEGER)";
	
	private static final String CREATE_TABLE_CLASSEENERGETICA = "CREATE TABLE " +
			ImportDataHelper.CLASSEENERGETICA_TAG + "(CODCLASSEENERGETICA INTEGER PRIMARY KEY, DESCRIZIONE TEXT, NOME TEXT, ORDINE INTEGER)";
	
	
	private static final String CREATE_TABLE_CLASSECLIENTI = "CREATE TABLE " +
			ImportDataHelper.CLASSICLIENTI_TAG + "(CODCLASSECLIENTE INTEGER PRIMARY KEY, DESCRIZIONE TEXT, ORDINE INTEGER)";
	

	
	private static final String CREATE_TABLE_CONTATTI = "CREATE TABLE " +
			ImportDataHelper.CONTATTI_TAG + "(CODCONTATTO INTEGER PRIMARY KEY, DESCRIZIONE TEXT, CONTATTO TEXT, CODTIPOLOGIACONTATTO INTEGER, " +
			"CODAGENTE INTEGER, CODANAGRAFICA INTEGER)";
	
	private static final String CREATE_TABLE_DATICATASTALI = "CREATE TABLE " +
			ImportDataHelper.DATICATASTALI_TAG + "(CODDATICATASTALI INTEGER PRIMARY KEY, CODIMMOBILE INTEGER, CATEGORIA TEXT, DIMENSIONE REAL, " +
			"FOGLIO TEXT, PARTICELLA TEXT, REDDITOAGRARIO REAL, REDDITODOMENICALE REAL, RENDITA REAL, SUBALTERNO TEXT)";
	
	private static final String CREATE_TABLE_IMMAGINI = "CREATE TABLE " +
			ImportDataHelper.IMMAGINE_TAG + "(CODIMMAGINE INTEGER PRIMARY KEY, CODIMMOBILE INTEGER, IMGPROPS TEXT, ORDINE INTEGER, PATHIMMAGINE TEXT)";
	
	private static final String CREATE_TABLE_RISCALDAMENTI = "CREATE TABLE " +
			ImportDataHelper.RISCALDAMENTI_TAG + "(CODRISCALDAMENTO INTEGER PRIMARY KEY, DESCRIZIONE TEXT)";
	
	private static final String CREATE_TABLE_STATOCONSERVATIVO = "CREATE TABLE " +
			ImportDataHelper.STATOCONSERVATIVO_TAG + "(CODSTATOCONSERVATIVO INTEGER PRIMARY KEY, DESCRIZIONE TEXT)";
	
	private static final String CREATE_TABLE_TIPOLOGIACONTATTO = "CREATE TABLE " +
			ImportDataHelper.TIPOLOGIACONTATTO_TAG + "(CODTIPOLOGIACONTATTO INTEGER PRIMARY KEY, DESCRIZIONE TEXT)";

	private static final String CREATE_TABLE_TIPOLOGIASTANZE = "CREATE TABLE " +
			ImportDataHelper.TIPOLOGIASTANZE_TAG + "(CODTIPOLOGIASTANZA INTEGER PRIMARY KEY, DESCRIZIONE TEXT)";

	private static final String CREATE_TABLE_TIPOLOGIAIMMOBILI = "CREATE TABLE " +
			ImportDataHelper.TIPOLOGIAIMMOBILI_TAG + "(CODTIPOLOGIAIMMOBILE INTEGER PRIMARY KEY, DESCRIZIONE TEXT)";

	
	
	private static final String CREATE_TABLE_STANZEIMMOBILI = "CREATE TABLE " +
			ImportDataHelper.STANZEIMMOBILI_TAG + "(CODSTANZEIMMOBILI INTEGER PRIMARY KEY, CODTIPOLOGIASTANZA INTEGER, CODIMMOBILE INTEGER, MQ INTEGER)";

	private static final String CREATE_TABLE_ENTITY = "CREATE TABLE " +
			ImportDataHelper.ENTITA_TAG + "(IDCLASSENTITY INTEGER PRIMARY KEY, DESCRIPTION TEXT, CLASSNAME TEXT)";

	private static final String CREATE_TABLE_ATTRIBUTI = "CREATE TABLE " +
			ImportDataHelper.ATTRIBUTI_TAG + "(IDATTRIBUTE INTEGER PRIMARY KEY, IDCLASSENTITY INTEGER, ATTRIBUTENAME TEXT, FIELDTYPE TEXT, LINKEDIDENTITY INTEGER)";

	private static final String CREATE_TABLE_VALORIATTRIBUTI = "CREATE TABLE " +
			ImportDataHelper.VALOREATTRIBUTO_TAG + "(IDVALUE INTEGER PRIMARY KEY, IDFIELD INTEGER, IDOBJECT INTEGER, FIELDVALUE TEXT)";
	
	//v2
	private static final String CREATE_TABLE_SYSSETTINGS = "CREATE TABLE SYSSETTINGS (IDSETTING INTEGER PRIMARY KEY, SETTINGNAME TEXT, SETTINGVALUE TEXT)";
	
	private static final String CREATE_TABLE_IMMOBILIPROPIETARI = "CREATE TABLE " + ImportDataHelper.IMMOBILIPROPIETARI_TAG + "(CODIMMOBILE INTEGER, CODANAGRAFICA INTEGER)";

	private static final String FILL_IMMOBILI_PROPIETARI = "INSERT INTO " + ImportDataHelper.IMMOBILIPROPIETARI_TAG + " (CODIMMOBILE, CODANAGRAFICA) SELECT CODIMMOBILE,CODANAGRAFICA FROM IMMOBILI";
	private static final String UPDATE_IMMOBILI_ANAGRAFICA = "UPDATE " + ImportDataHelper.IMMOBILI_TAG + " SET CODANAGRAFICA = 0";
	
	//v3
	private static final String ADD_MQ_TO_STANZEIMMOBILI = "ALTER TABLE STANZEIMMOBILI ADD COLUMN MQ INTEGER";
	
	private static final String CREATE_TABLE_COLLOQUI = "CREATE TABLE " + 
								ImportDataHelper.COLLOQUI_TAG + "(CODCOLLOQUIO PRIMARY KEY, DESCRIZIONE TEXT, CODAGENTEINSERITORE INTEGER, CODIMMOBILEABBINATO INTEGER, " +
								"CODTIPOLOGIACOLLOQUIO INTEGER, DATAINSERIMENTO TEXT, DATACOLLOQUIO TEXT, LUOGO TEXT, SCADENZIERE INTEGER, " +
								"COMMENTOAGENZIA TEXT, COMMENTOCLIENTE TEXT, CODPARENT INTEGER, ICALUID TEXT, CODUSERUPDATE INTEGER, DATEUPDATE TEXT)";
	
	private static final String CREATE_TABLE_COLLOQUIANAGRAFICHE = "CREATE TABLE " + 
								ImportDataHelper.COLLOQUIANAGRAFICHE_TABLE_NAME + " (CODCOLLOQUIANAGRAFICHE PRIMARY KEY, CODCOLLOQUIO INTEGER, CODANAGRAFICA INTEGER, DESCRIZIONE TEXT, " +
								"CODUSERUPDATE INTEGER, DATEUPDATE TEXT)";
	
	private static final String CREATE_TABLE_COLLOQUIAGENTI = "CREATE TABLE " + 
								ImportDataHelper.COLLOQUIAGENTI_TABLE_NAME + " (CODCOLLOQUIOAGENTE PRIMARY KEY, CODCOLLOQUIO INTEGER, CODAGENTE INTEGER, COMMENTO TEXT, CODUSERUPDATE INTEGER, " +
								"DATEUPDATE TIMESTAMP)";
	
	private static final String CREATE_TABLE_COLLOQUICRITERIRICERCA = "CREATE TABLE COLLOQUICRITERIRICERCA (CODCRITERIORICERCA PRIMARY KEY, CODCOLLOQUIO INTEGER, GETTERMETHODNAME TEXT, FROMVALUE TEXT, TOVALUE TEXT, " +
																										   "LOGICALOPERATOR TEXT, CODRICERCA INTEGER, ISPERSONAL INTEGER, CODUSERUPDATE INTEGER, DATEUPDATE TEXT)";

	
	private SQLiteDatabase sqllitedb = null;
	
	public final static int READ_DB = 0;
	public final static int WRITE_DB = 1;
	public final static int NONE_DB = 2;
	
	public DataBaseHelper(Context context, int one_db_instace) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		if (one_db_instace == READ_DB){
			this.sqllitedb = getReadableDatabase();
		}
		if (one_db_instace == WRITE_DB){
			this.sqllitedb = getWritableDatabase();
		}
		
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		arg0.execSQL(CREATE_TABLE_ABBINAMENTI);
		arg0.execSQL(CREATE_TABLE_AGENTI);
		arg0.execSQL(CREATE_TABLE_ANAGRAFICHE);
		arg0.execSQL(CREATE_TABLE_CLASSECLIENTI);
		fillClassiCliente(arg0);
		arg0.execSQL(CREATE_TABLE_CLASSEENERGETICA);
		fillClassiEnergetiche(arg0);
		arg0.execSQL(CREATE_TABLE_CONTATTI);
		arg0.execSQL(CREATE_TABLE_DATICATASTALI);
		arg0.execSQL(CREATE_TABLE_IMMAGINI);
		arg0.execSQL(CREATE_TABLE_IMMOBILI);
		arg0.execSQL(CREATE_TABLE_RISCALDAMENTI);
		fillRiscaldamenti(arg0);
		arg0.execSQL(CREATE_TABLE_STANZEIMMOBILI);
		arg0.execSQL(CREATE_TABLE_STATOCONSERVATIVO);
		fillStatoConservativo(arg0);
		arg0.execSQL(CREATE_TABLE_TIPOLOGIACONTATTO);
		fillTipologiaContatti(arg0);
		arg0.execSQL(CREATE_TABLE_TIPOLOGIAIMMOBILI);
		fillTipologiaImmobili(arg0);
		arg0.execSQL(CREATE_TABLE_TIPOLOGIASTANZE);
		arg0.execSQL(CREATE_TABLE_ENTITY);
		arg0.execSQL(CREATE_TABLE_ATTRIBUTI);
		arg0.execSQL(CREATE_TABLE_VALORIATTRIBUTI);
		arg0.execSQL(CREATE_TABLE_SYSSETTINGS);
		arg0.execSQL(CREATE_TABLE_IMMOBILIPROPIETARI);
		arg0.execSQL(CREATE_TABLE_COLLOQUI);
		arg0.execSQL(CREATE_TABLE_COLLOQUIAGENTI);
		arg0.execSQL(CREATE_TABLE_COLLOQUIANAGRAFICHE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int old_version, int new_version) {
		
		
		if (old_version == 1 && new_version == 2){
			arg0.execSQL(CREATE_TABLE_SYSSETTINGS);
			arg0.execSQL(CREATE_TABLE_IMMOBILIPROPIETARI);
			arg0.execSQL(FILL_IMMOBILI_PROPIETARI);
			arg0.execSQL(UPDATE_IMMOBILI_ANAGRAFICA);
		}
		if (old_version == 2 && new_version == 3){
			arg0.execSQL(ADD_MQ_TO_STANZEIMMOBILI);
			arg0.execSQL(CREATE_TABLE_COLLOQUI);
			arg0.execSQL(CREATE_TABLE_COLLOQUIAGENTI);
			arg0.execSQL(CREATE_TABLE_COLLOQUIANAGRAFICHE);			
		}
		
//		if (old_version == 3 && new_version == 4){
//			arg0.execSQL(CREATE_TABLE_COLLOQUI);
//			arg0.execSQL(CREATE_TABLE_COLLOQUIAGENTI);
//			arg0.execSQL(CREATE_TABLE_COLLOQUIANAGRAFICHE);			
//		}

	}
	
	protected String[] buildColumnList(HashMap column_list){
		
		String[] returnValue = null;
		
		if (column_list != null){
			ArrayList tmp = new ArrayList<String>();
			Set keys = column_list.keySet();
			if (keys.size() > 0){
				returnValue = (String[])keys.toArray(new String[keys.size()]);
			}
		}
		
		return returnValue;
	}
	
	protected ArrayList getWhereClause(ArrayList<SearchParam> parametri){
		
		ArrayList returnValue = new ArrayList();
		
		ArrayList<String> params = new ArrayList<String>();		
		String whereClause = null;
		
		Iterator<SearchParam> it = parametri.iterator();
		int count = 1;
		
		while (it.hasNext()) {
			
			SearchParam searchParam = (SearchParam) it.next();
			if ((searchParam.getValue_a() != null) && 
				(searchParam.getValue_da() != null)){
				
				if (searchParam.getMatchType().equalsIgnoreCase(Integer.class.getName()) || 
					searchParam.getMatchType().equalsIgnoreCase(Double.class.getName()) ||
					searchParam.getMatchType().equalsIgnoreCase(Float.class.getName()) 
					){
						if (whereClause != null){
							whereClause += searchParam.getColumn_name() + " >= ? " + 
									   	   " AND " + searchParam.getColumn_name() + " <= ?";
							
						}else{
							whereClause = searchParam.getColumn_name() + " >= ? " + 
									   	  " AND " + searchParam.getColumn_name() + " <= ?";
	
						}
					
				
															
				}else if (searchParam.getMatchType().equalsIgnoreCase(String.class.getName())){
					
					if (whereClause != null){
						whereClause += searchParam.getColumn_name() + " LIKE ? AND " + searchParam.getColumn_name() + " LIKE ?";						
					}else{
						whereClause = searchParam.getColumn_name() + " LIKE ? AND " + searchParam.getColumn_name() + " LIKE ?";
					}
					

				}
				
				if (count < parametri.size()){
					if (whereClause != null){					
						whereClause += " " + searchParam.getLogic_operatore();
					}
				}
				
				params.add(searchParam.getValue_da());
				params.add(searchParam.getValue_a());

				count ++;
				
			}else if ((searchParam.getValue_a() != null) && 
					  (searchParam.getValue_da() == null)){

				if (searchParam.getMatchType().equalsIgnoreCase(Integer.class.getName()) || 
						searchParam.getMatchType().equalsIgnoreCase(Double.class.getName()) ||
						searchParam.getMatchType().equalsIgnoreCase(Float.class.getName()) 
						){
					
						if (whereClause != null){
							whereClause += searchParam.getColumn_name() + " <= ?";
						}else{
							whereClause = searchParam.getColumn_name() + " <= ?";
						}
					
					
					
				}else if (searchParam.getMatchType().equalsIgnoreCase(String.class.getName())){
					if (whereClause != null){
						whereClause += searchParam.getColumn_name() + " LIKE ? ";
					}else{
						whereClause = searchParam.getColumn_name() + " LIKE ? ";
					}
				}				 
						   
				if (count < parametri.size()){
					if (whereClause != null){
						whereClause += " " + searchParam.getLogic_operatore();
					}
				}
				params.add(searchParam.getValue_a());

				count ++;
				
			}else if ((searchParam.getValue_a() == null) && 
					  (searchParam.getValue_da() != null)){

				if (searchParam.getMatchType().equalsIgnoreCase(Integer.class.getName()) || 
						searchParam.getMatchType().equalsIgnoreCase(Double.class.getName()) ||
						searchParam.getMatchType().equalsIgnoreCase(Float.class.getName()) 
						){
					if (
							searchParam.getColumn_name().equalsIgnoreCase(ImmobiliColumnNames.CODCLASSEENERGETICA) ||
							searchParam.getColumn_name().equalsIgnoreCase(ImmobiliColumnNames.CODSTATO) ||
							searchParam.getColumn_name().equalsIgnoreCase(ImmobiliColumnNames.CODRISCALDAMENTO) ||
							searchParam.getColumn_name().equalsIgnoreCase(ImmobiliColumnNames.CODTIPOLOGIA) ||
							searchParam.getColumn_name().equalsIgnoreCase(AnagraficheColumnNames.CODCLASSECLIENTE)
							){
							if (whereClause != null){
								whereClause += searchParam.getColumn_name() + " = ? ";
							}else{
								whereClause = searchParam.getColumn_name() + " = ? ";
							}
							
					}else{

					
						if (whereClause != null){	
							whereClause += searchParam.getColumn_name() + " >= ?";
						}else{
							whereClause = searchParam.getColumn_name() + " >= ?";
						}
						
					}
					
				}else if (searchParam.getMatchType().equalsIgnoreCase(String.class.getName())){
					if (whereClause != null){
						whereClause += searchParam.getColumn_name() + " LIKE ? ";
					}else{
						whereClause = searchParam.getColumn_name() + " LIKE ? ";
					}
				}				 
						   
				if (count < parametri.size()){
					if (whereClause != null){
						whereClause += " " + searchParam.getLogic_operatore() + " ";
					}
				}

				params.add(searchParam.getValue_da());
				
				count ++;

			}
			
		}
		
		returnValue.add(whereClause);
		returnValue.add(params.toArray(new String[params.size()]));
		
		return returnValue;
		
	}
	
	public ArrayList<AnagraficheVO> getAllAnagrafiche(HashMap column_list){
		ArrayList<AnagraficheVO> returnValue = new ArrayList<AnagraficheVO>();
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		Cursor c = rdb.query(ImportDataHelper.ANAGRAFICHE_TAG, buildColumnList(column_list), null, null, null, null, null);
		while(c.moveToNext()){
			try {
				returnValue.add(new AnagraficheVO(c, column_list));
			} catch (SQLException e) {
				
			}
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}

		return returnValue;
		
	}

	public ArrayList<ColloquiVO> getAllColloqui(HashMap column_list){
		ArrayList<ColloquiVO> returnValue = new ArrayList<ColloquiVO>();
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		Cursor c = rdb.query(ImportDataHelper.COLLOQUI_TAG, buildColumnList(column_list), null, null, null, null, null);
		while(c.moveToNext()){
			try {
				returnValue.add(new ColloquiVO(c, column_list));
			} catch (SQLException e) {
				
			}
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}

		return returnValue;
		
	}
	
	public Cursor getAllAnagraficheCursor(HashMap column_list){
		ArrayList<AnagraficheVO> returnValue = new ArrayList<AnagraficheVO>();
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		Cursor c = rdb.query(ImportDataHelper.ANAGRAFICHE_TAG, buildColumnList(column_list), null, null, null, null, null);

		return c;
		
	}

	public boolean saveAnagrafica(AnagraficheVO anagrafica){
		
		boolean returnValue = true;
		SQLiteDatabase wdb = (this.sqllitedb == null)?getWritableDatabase():this.sqllitedb;
		
		if (anagrafica.getCodAnagrafica() == 0){
			
			anagrafica.setCodAnagrafica(getNextAnagraficaID());
			Long newcod = wdb.insert(ImportDataHelper.ANAGRAFICHE_TAG, null, anagrafica.getContentValue());
			if (newcod == -1){
				returnValue = false;
			}else{
				anagrafica.setCodAnagrafica(newcod.intValue());
			}
		}else{
			int rownum = wdb.update(ImportDataHelper.ANAGRAFICHE_TAG, anagrafica.getContentValue(), AnagraficheColumnNames.CODANAGRAFICA + "=?", new String[]{anagrafica.getCodAnagrafica().toString()});
			if (rownum != 1){
				returnValue = false;
			}
		}
		
		return returnValue;
	}

	public boolean saveImmobiliPropieta(ImmobiliPropietariVO immobiliPropietariVO){
		
		boolean returnValue = true;
		
		SQLiteDatabase wdb = (this.sqllitedb == null)?getWritableDatabase():this.sqllitedb;	
		wdb.insert(ImportDataHelper.IMMOBILIPROPIETARI_TAG, null, immobiliPropietariVO.getContentValue());
		if (this.sqllitedb == null){
//			wdb.close();
		}
		return returnValue;
	}
	
	public ArrayList<AnagraficheVO> searchAnagrafiche(ArrayList<SearchParam> params,HashMap column_list){
		
		ArrayList<AnagraficheVO> returnValue = new ArrayList<AnagraficheVO>();
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;

		ArrayList al = getWhereClause(params);
		Cursor c = rdb.query(ImportDataHelper.ANAGRAFICHE_TAG, buildColumnList(column_list), (String)al.get(0), (String[])al.get(1), null, null, null);
		while(c.moveToNext()){
			try {
				returnValue.add(new AnagraficheVO(c, column_list));
			} catch (SQLException e) {
				
			}
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}

		return returnValue;		
	}

	public Cursor searchAnagraficheCursor(ArrayList<SearchParam> params,HashMap column_list){
		
		ArrayList<AnagraficheVO> returnValue = new ArrayList<AnagraficheVO>();
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;

		ArrayList al = getWhereClause(params);
		Cursor c = rdb.query(ImportDataHelper.ANAGRAFICHE_TAG, buildColumnList(column_list), (String)al.get(0), (String[])al.get(1), null, null, null);

		return c;		
	}

	
	public AnagraficheVO getAnagraficaById(Integer codAnagrafica,HashMap column_list){
		AnagraficheVO returnValue = null;
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		Cursor c = rdb.query(ImportDataHelper.ANAGRAFICHE_TAG, buildColumnList(column_list), "CODANAGRAFICA=?", new String[]{codAnagrafica.toString()}, null, null, null);
		if (c.moveToFirst()){
			try {
				returnValue = new AnagraficheVO(c, column_list);
			} catch (SQLException e) {
				returnValue = null;
			}
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}

		return returnValue;
	}

	public ColloquiVO getColloquiById(Integer codColloquio,HashMap column_list){
		
		ColloquiVO returnValue = null;
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		
		String query = "SELECT * FROM COLLOQUI WHERE CODCOLLOQUIO = ?";
		query = query.replace("?", String.valueOf(codColloquio));
		
//		Cursor c = rdb.query(ImportDataHelper.COLLOQUI_TAG, 
//							 buildColumnList(column_list), 
//							 ColloquiColumnNames.CODCOLLOQUIO + "=?", 
//							 new String[]{String.valueOf(codColloquio)},
//							 null, 
//							 null, 
//							 null);
		
		Cursor c = rdb.rawQuery(query, null);		
		
		if (c.moveToFirst()){
			try {
				returnValue = new ColloquiVO(c, column_list);
			} catch (SQLException e) {
				returnValue = null;
			}
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}

		return returnValue;
		
	}
	
	public SysSettingVO getSysSettingByName(String sysSettingName,HashMap column_list){
		SysSettingVO returnValue = null;
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		Cursor c = rdb.query("SYSSETTINGS", buildColumnList(column_list), "SETTINGNAME=?", new String[]{sysSettingName}, null, null, null);
		if (c.moveToFirst()){
			try {
				returnValue = new SysSettingVO(c, column_list);
			} catch (SQLException e) {
				returnValue = null;
			}
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}

		return returnValue;
	}
	
	
	public ArrayList<ImmobiliVO> getAllImmobili(HashMap column_list){
		ArrayList<ImmobiliVO> returnValue = new ArrayList<ImmobiliVO>();
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		Cursor c = rdb.query(ImportDataHelper.IMMOBILI_TAG, buildColumnList(column_list), null, null, null, null, null);
		while(c.moveToNext()){
			try {
				returnValue.add(new ImmobiliVO(c, column_list));
			} catch (SQLException e) {
				
			}
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}

		return returnValue;
		
	}

	public ImmobiliVO getImmobileById(Integer codImmobile,HashMap column_list){
		ImmobiliVO returnValue = null;
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		Cursor c = rdb.query(ImportDataHelper.IMMOBILI_TAG, buildColumnList(column_list), ImmobiliColumnNames.CODIMMOBILE + "=?", new String[]{codImmobile.toString()}, null, null, null);
		if (c.moveToFirst()){
			try {
				returnValue = new ImmobiliVO(c, column_list);
			} catch (SQLException e) {
				returnValue = null;
			}
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}

		return returnValue;
	}
	
	public ContattiVO getContattiById(Integer codContatto,HashMap column_list){
		ContattiVO returnValue = null;
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		Cursor c = rdb.query(ImportDataHelper.CONTATTI_TAG, buildColumnList(column_list), ContattiColumnNames.CODCONTATTO + "=?", new String[]{codContatto.toString()}, null, null, null);
		if (c.moveToFirst()){
			try {
				returnValue = new ContattiVO(c, column_list);
			} catch (SQLException e) {
				returnValue = null;
			}
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}

		return returnValue;
	}

	public StanzeImmobiliVO getStanzaById(Integer codStanza,HashMap column_list){
		StanzeImmobiliVO returnValue = null;
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		Cursor c = rdb.query(ImportDataHelper.STANZEIMMOBILI_TAG, buildColumnList(column_list), StanzeImmobiliColumnNames.CODSTANZEIMMOBILI + "=?", new String[]{codStanza.toString()}, null, null, null);
		if (c.moveToFirst()){
			try {
				returnValue = new StanzeImmobiliVO(c, column_list);
			} catch (SQLException e) {
				returnValue = null;
			}
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}

		return returnValue;
	}

	public ImmagineVO getImmagineById(Integer codImmagine,HashMap column_list){
		ImmagineVO returnValue = null;
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		Cursor c = rdb.query(ImportDataHelper.IMMAGINE_TAG, buildColumnList(column_list), ImmagineColumnNames.CODIMMAGINE + "=?", new String[]{codImmagine.toString()}, null, null, null);
		if (c.moveToFirst()){
			try {
				returnValue = new ImmagineVO(c, column_list);
			} catch (SQLException e) {
				returnValue = null;
			}
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}

		return returnValue;
	}

	public TipologiaContattiVO getTiplogiaContattoById(Integer codTipologiaContatto,HashMap column_list){
		TipologiaContattiVO returnValue = null;
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		Cursor c = rdb.query(ImportDataHelper.TIPOLOGIACONTATTO_TAG, buildColumnList(column_list), TipologieContattiColumnNames.CODTIPOLOGIACONTATTO+ "=?", new String[]{codTipologiaContatto.toString()}, null, null, null);
		if (c.moveToFirst()){
			try {
				returnValue = new TipologiaContattiVO(c, column_list);
			} catch (SQLException e) {
				returnValue = null;
			}
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}
		return returnValue;
	}

	public TipologiaStanzeVO getTiplogiaStanzeById(Integer codTipologiaStanze,HashMap column_list){
		TipologiaStanzeVO returnValue = null;
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		Cursor c = rdb.query(ImportDataHelper.TIPOLOGIASTANZE_TAG, buildColumnList(column_list), TipologieStanzeColumnNames.CODTIPOLOGIASTANZA+ "=?", new String[]{codTipologiaStanze.toString()}, null, null, null);
		if (c.moveToFirst()){
			try {
				returnValue = new TipologiaStanzeVO(c, column_list);
			} catch (SQLException e) {
				returnValue = null;
			}
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}
		return returnValue;
	}

	protected int getNextImmobileID(){
		
		int returnValue = 1;
		
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		Cursor c = rdb.query(ImportDataHelper.IMMOBILI_TAG, new String[]{ImmobiliColumnNames.CODIMMOBILE}, null, null, null, null, ImmobiliColumnNames.CODIMMOBILE + " DESC");
		if (c.moveToFirst()){
			returnValue = c.getInt(c.getColumnIndex(ImmobiliColumnNames.CODIMMOBILE)) + 1;
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}
		return returnValue;
		
	}
	
	protected int getNextSettingID(){
		
		int returnValue = 1;
		
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		Cursor c = rdb.query("SYSSETTINGS", new String[]{"IDSETTING"}, null, null, null, null, "IDSETTING" + " DESC");
		if (c.moveToFirst()){
			returnValue = c.getInt(c.getColumnIndex("IDSETTING")) + 1;
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}
		return returnValue;
		
	}
	
	protected int getNextAnagraficaID(){
		
		int returnValue = 1;
		
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		Cursor c = rdb.query(ImportDataHelper.ANAGRAFICHE_TAG, new String[]{AnagraficheColumnNames.CODANAGRAFICA}, null, null, null, null, AnagraficheColumnNames.CODANAGRAFICA + " DESC");
		if (c.moveToFirst()){
			returnValue = c.getInt(c.getColumnIndex(AnagraficheColumnNames.CODANAGRAFICA)) + 1;
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}		
		return returnValue;
		
	}

	protected int getNextContattoID(){
		
		int returnValue = 1;
		
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		Cursor c = rdb.query(ImportDataHelper.CONTATTI_TAG, new String[]{ContattiColumnNames.CODCONTATTO}, null, null, null, null, ContattiColumnNames.CODCONTATTO + " DESC");
		if (c.moveToFirst()){
			returnValue = c.getInt(c.getColumnIndex(ContattiColumnNames.CODCONTATTO)) + 1;
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}
		return returnValue;
		
	}

	protected int getNextColloquiID(){
		
		int returnValue = 1;
		
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		Cursor c = rdb.query(ImportDataHelper.COLLOQUI_TAG, new String[]{ColloquiColumnNames.CODCOLLOQUIO}, null, null, null, null, ColloquiColumnNames.CODCOLLOQUIO + " DESC");
		if (c.moveToFirst()){
			returnValue = c.getInt(c.getColumnIndex(ColloquiColumnNames.CODCOLLOQUIO)) + 1;
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}
		return returnValue;
		
	}

	protected int getNextColloquiAnagraficheID(){
		
		int returnValue = 1;
		
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		Cursor c = rdb.query(ImportDataHelper.COLLOQUIANAGRAFICHE_TABLE_NAME, new String[]{ColloquiAnagraficheColumnNames.CODCOLLOQUIANAGRAFICHE}, null, null, null, null, ColloquiAnagraficheColumnNames.CODCOLLOQUIANAGRAFICHE + " DESC");
		if (c.moveToFirst()){
			returnValue = c.getInt(c.getColumnIndex(ColloquiAnagraficheColumnNames.CODCOLLOQUIANAGRAFICHE)) + 1;
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}
		return returnValue;
		
	}

	
	protected int getNextStanzaID(){
		
		int returnValue = 1;
		
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		Cursor c = rdb.query(ImportDataHelper.STANZEIMMOBILI_TAG, new String[]{StanzeImmobiliColumnNames.CODSTANZEIMMOBILI}, null, null, null, null, StanzeImmobiliColumnNames.CODSTANZEIMMOBILI + " DESC");
		if (c.moveToFirst()){
			returnValue = c.getInt(c.getColumnIndex(StanzeImmobiliColumnNames.CODSTANZEIMMOBILI)) + 1;
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}
		return returnValue;
		
	}

	protected int getNextImmagineID(){
		
		int returnValue = 1;
		
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		Cursor c = rdb.query(ImportDataHelper.IMMAGINE_TAG, new String[]{ImmagineColumnNames.CODIMMAGINE}, null, null, null, null, ImmagineColumnNames.CODIMMAGINE + " DESC");
		if (c.moveToFirst()){
			returnValue = c.getInt(c.getColumnIndex(ImmagineColumnNames.CODIMMAGINE)) + 1;
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}
		return returnValue;
		
	}

	protected int getNextClasseEnergeticaID(SQLiteDatabase arg0){
		
		int returnValue = 1;
		
		SQLiteDatabase rdb = null;
		if (arg0 != null){
			rdb = arg0;
		}else{
			if (this.sqllitedb == null){
				rdb = getReadableDatabase(); 
			}else{
				rdb = this.sqllitedb; 
			}
		}

		Cursor c = rdb.query(ImportDataHelper.CLASSEENERGETICA_TAG, new String[]{ClasseEnergeticaColumnNames.CODCLASSEENERGETICA}, null, null, null, null, ClasseEnergeticaColumnNames.CODCLASSEENERGETICA + " DESC");
		if (c.moveToFirst()){
			returnValue = c.getInt(c.getColumnIndex(ClasseEnergeticaColumnNames.CODCLASSEENERGETICA)) + 1;
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}
		return returnValue;
		
	}

	protected int getNextRiscaldamentiID(SQLiteDatabase arg0){
		
		int returnValue = 1;
		
		SQLiteDatabase rdb = null;
		if (arg0 != null){
			rdb = arg0;
		}else{
			if (this.sqllitedb == null){
				rdb = getReadableDatabase(); 
			}else{
				rdb = this.sqllitedb; 
			}
		}

		Cursor c = rdb.query(ImportDataHelper.RISCALDAMENTI_TAG, new String[]{RiscaldamentiColumnNames.CODRISCALDAMENTO}, null, null, null, null, RiscaldamentiColumnNames.CODRISCALDAMENTO + " DESC");
		if (c.moveToFirst()){
			returnValue = c.getInt(c.getColumnIndex(RiscaldamentiColumnNames.CODRISCALDAMENTO)) + 1;
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}
		return returnValue;
		
	}

	protected int getNextTipologieImmobiliID(SQLiteDatabase arg0){
		
		int returnValue = 1;
		SQLiteDatabase rdb = null;
		if (arg0 != null){
			rdb = arg0;
		}else{
			if (this.sqllitedb == null){
				rdb = getReadableDatabase(); 
			}else{
				rdb = this.sqllitedb; 
			}
		}

//		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		Cursor c = rdb.query(ImportDataHelper.TIPOLOGIAIMMOBILI_TAG, new String[]{TipologieImmobiliColumnNames.CODTIPOLOGIAIMMOBILE}, null, null, null, null, TipologieImmobiliColumnNames.CODTIPOLOGIAIMMOBILE + " DESC");
		if (c.moveToFirst()){
			returnValue = c.getInt(c.getColumnIndex(TipologieImmobiliColumnNames.CODTIPOLOGIAIMMOBILE)) + 1;
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}
		return returnValue;
		
	}

	protected int getNextStatoConservativoID(SQLiteDatabase arg0){
		
		int returnValue = 1;
		SQLiteDatabase rdb = null;
		if (arg0 != null){
			rdb = arg0;
		}else{
			if (this.sqllitedb == null){
				rdb = getReadableDatabase(); 
			}else{
				rdb = this.sqllitedb; 
			}
		}

//		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		Cursor c = rdb.query(ImportDataHelper.STATOCONSERVATIVO_TAG, new String[]{StatoConservativoColumnNames.CODSTATOCONSERVATIVO}, null, null, null, null, StatoConservativoColumnNames.CODSTATOCONSERVATIVO + " DESC");
		if (c.moveToFirst()){
			returnValue = c.getInt(c.getColumnIndex(StatoConservativoColumnNames.CODSTATOCONSERVATIVO)) + 1;
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}
		return returnValue;
		
	}
	
	protected int getNextTipologiaContattiID(SQLiteDatabase arg0){
		
		int returnValue = 1;
		SQLiteDatabase rdb = null;
		if (arg0 != null){
			rdb = arg0;
		}else{
			if (this.sqllitedb == null){
				rdb = getReadableDatabase(); 
			}else{
				rdb = this.sqllitedb; 
			}
		}

//		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		Cursor c = rdb.query(ImportDataHelper.TIPOLOGIACONTATTO_TAG, new String[]{TipologieContattiColumnNames.CODTIPOLOGIACONTATTO}, null, null, null, null, TipologieContattiColumnNames.CODTIPOLOGIACONTATTO + " DESC");
		if (c.moveToFirst()){
			returnValue = c.getInt(c.getColumnIndex(TipologieContattiColumnNames.CODTIPOLOGIACONTATTO)) + 1;
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}
		return returnValue;
		
	}


	protected int getNextClassiClientiID(SQLiteDatabase arg0){
		
		int returnValue = 1;
		
		SQLiteDatabase rdb = null;
		if (arg0 != null){
			rdb = arg0;
		}else{
			if (this.sqllitedb == null){
				rdb = getReadableDatabase(); 
			}else{
				rdb = this.sqllitedb; 
			}
		}
		Cursor c = rdb.query(ImportDataHelper.CLASSICLIENTI_TAG, new String[]{ClassiClienteColumnNames.CODCLASSECLIENTE}, null, null, null, null, ClassiClienteColumnNames.CODCLASSECLIENTE + " DESC");
		if (c.moveToFirst()){
			returnValue = c.getInt(c.getColumnIndex(ClassiClienteColumnNames.CODCLASSECLIENTE)) + 1;
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}
		return returnValue;
		
	}

	public boolean saveImmobile(ImmobiliVO immobile){
		
		boolean returnValue = true;
		SQLiteDatabase wdb = (this.sqllitedb == null)?getWritableDatabase():this.sqllitedb;
		
		if (immobile.getCodImmobile() == 0){
			
			immobile.setCodImmobile(getNextImmobileID());
			immobile.setRif("MOB_" + immobile.getCodImmobile().toString());
			
			Long newcod = wdb.insert(ImportDataHelper.IMMOBILI_TAG, null, immobile.getContentValue());
			if (newcod == -1){
				returnValue = false;
			}else{
				immobile.setCodImmobile(newcod.intValue());
			}
		}else{
			int rownum = wdb.update(ImportDataHelper.IMMOBILI_TAG, immobile.getContentValue(), ImmobiliColumnNames.CODIMMOBILE + "=?", new String[]{immobile.getCodImmobile().toString()});
			if (rownum != 1){
				returnValue = false;
			}
		}
		if (this.sqllitedb == null){
//			wdb.close();
		}

		return returnValue;
	}

	public boolean saveSysSetting(SysSettingVO setting){
		
		boolean returnValue = true;
		SQLiteDatabase wdb = (this.sqllitedb == null)?getWritableDatabase():this.sqllitedb;
		
		if (setting.getIdSetting() == 0){
			
			setting.setIdSetting(getNextSettingID());			
			
			Long newcod = wdb.insert("SYSSETTINGS", null, setting.getContentValue());
			if (newcod == -1){
				returnValue = false;
			}else{
				setting.setIdSetting(newcod.intValue());
			}
		}else{
			int rownum = wdb.update("SYSSETTINGS", setting.getContentValue(), "IDSETTING" + "=?", new String[]{setting.getIdSetting().toString()});
			if (rownum != 1){
				returnValue = false;
			}
		}
		if (this.sqllitedb == null){
//			wdb.close();
		}

		return returnValue;
	}
	
	
	public boolean saveContatto(ContattiVO contatto){
		
		boolean returnValue = true;
		
		SQLiteDatabase wdb = (this.sqllitedb == null)?getWritableDatabase():this.sqllitedb;
		
		if (contatto.getCodContatto() == 0){
			
			contatto.setCodContatto(getNextContattoID());
			Long newcod = wdb.insert(ImportDataHelper.CONTATTI_TAG, null, contatto.getContentValue());
			if (newcod == -1){
				returnValue = false;
			}else{
				contatto.setCodContatto(newcod.intValue());
			}
		}else{
			int rownum = wdb.update(ImportDataHelper.CONTATTI_TAG, contatto.getContentValue(), ContattiColumnNames.CODCONTATTO + "=?", new String[]{contatto.getCodContatto().toString()});
			if (rownum != 1){
				returnValue = false;
			}
		}
		if (this.sqllitedb == null){
//			wdb.close();
		}
		return returnValue;
	}

	public boolean saveStanza(StanzeImmobiliVO stanza){
		
		boolean returnValue = true;
		
		SQLiteDatabase wdb = (this.sqllitedb == null)?getWritableDatabase():this.sqllitedb;
		
		if (stanza.getCodStanzeImmobili() == 0){
			
			stanza.setCodStanzeImmobili(getNextStanzaID());
			Long newcod = wdb.insert(ImportDataHelper.STANZEIMMOBILI_TAG, null, stanza.getContentValue());
			if (newcod == -1){
				returnValue = false;
			}else{
				stanza.setCodStanzeImmobili(newcod.intValue());
			}
		}else{
			int rownum = wdb.update(ImportDataHelper.STANZEIMMOBILI_TAG, stanza.getContentValue(), StanzeImmobiliColumnNames.CODSTANZEIMMOBILI + "=?", new String[]{stanza.getCodStanzeImmobili().toString()});
			if (rownum != 1){
				returnValue = false;
			}
		}
		if (this.sqllitedb == null){
//			wdb.close();
		}
		return returnValue;
	}

	public boolean saveClasseEnergetica(ClasseEnergeticaVO classeEnergeticaVO,SQLiteDatabase arg0){
		
		boolean returnValue = true;
		
		SQLiteDatabase wdb = (this.sqllitedb == null && arg0==null)?getWritableDatabase():(this.sqllitedb == null)?arg0:this.sqllitedb;
		
		if (classeEnergeticaVO.getCodClasseEnergetica() == 0){
			
			classeEnergeticaVO.setCodClasseEnergetica(getNextClasseEnergeticaID(arg0));
			Long newcod = wdb.insert(ImportDataHelper.CLASSEENERGETICA_TAG, null, classeEnergeticaVO.getContentValue());
			if (newcod == -1){
				returnValue = false;
			}else{
				classeEnergeticaVO.setCodClasseEnergetica(newcod.intValue());
			}
		}else{
			int rownum = wdb.update(ImportDataHelper.CLASSEENERGETICA_TAG, classeEnergeticaVO.getContentValue(), ClasseEnergeticaColumnNames.CODCLASSEENERGETICA+ "=?", new String[]{classeEnergeticaVO.getCodClasseEnergetica().toString()});
			if (rownum != 1){
				returnValue = false;
			}
		}
		if (this.sqllitedb == null){
//			wdb.close();
		}
		return returnValue;
	}

	public boolean saveRiscaldamento(RiscaldamentiVO riscaldamentiVO,SQLiteDatabase arg0){
		
		boolean returnValue = true;
		
		SQLiteDatabase wdb = (this.sqllitedb == null && arg0==null)?getWritableDatabase():(this.sqllitedb == null)?arg0:this.sqllitedb;
		
		if (riscaldamentiVO.getCodRiscaldamento() == 0){
			
			riscaldamentiVO.setCodRiscaldamento(getNextRiscaldamentiID(arg0));
			Long newcod = wdb.insert(ImportDataHelper.RISCALDAMENTI_TAG, null, riscaldamentiVO.getContentValue());
			if (newcod == -1){
				returnValue = false;
			}else{
				riscaldamentiVO.setCodRiscaldamento(newcod.intValue());
			}
		}else{
			int rownum = wdb.update(ImportDataHelper.RISCALDAMENTI_TAG, riscaldamentiVO.getContentValue(), RiscaldamentiColumnNames.CODRISCALDAMENTO + "=?", new String[]{riscaldamentiVO.getCodRiscaldamento().toString()});
			if (rownum != 1){
				returnValue = false;
			}
		}
		if (this.sqllitedb == null){
//			wdb.close();
		}
		return returnValue;
	}

	public boolean saveTipologieImmobili(TipologieImmobiliVO tipologieImmobiliVO,SQLiteDatabase arg0){
		
		boolean returnValue = true;
		
		SQLiteDatabase wdb = (this.sqllitedb == null && arg0==null)?getWritableDatabase():(this.sqllitedb == null)?arg0:this.sqllitedb;
		
		if (tipologieImmobiliVO.getCodTipologiaImmobile() == 0){
			
			tipologieImmobiliVO.setCodTipologiaImmobile(getNextTipologieImmobiliID(arg0));
			Long newcod = wdb.insert(ImportDataHelper.TIPOLOGIAIMMOBILI_TAG, null, tipologieImmobiliVO.getContentValue());
			if (newcod == -1){
				returnValue = false;
			}else{
				tipologieImmobiliVO.setCodTipologiaImmobile(newcod.intValue());
			}
		}else{
			int rownum = wdb.update(ImportDataHelper.TIPOLOGIAIMMOBILI_TAG, tipologieImmobiliVO.getContentValue(), TipologieImmobiliColumnNames.CODTIPOLOGIAIMMOBILE + "=?", new String[]{tipologieImmobiliVO.getCodTipologiaImmobile().toString()});
			if (rownum != 1){
				returnValue = false;
			}
		}
		if (this.sqllitedb == null){
//			wdb.close();
		}
		return returnValue;
	}

	public boolean saveStatoConservativo(StatoConservativoVO statoConservativoVO,SQLiteDatabase arg0){
		
		boolean returnValue = true;
		
		SQLiteDatabase wdb = (this.sqllitedb == null && arg0==null)?getWritableDatabase():(this.sqllitedb == null)?arg0:this.sqllitedb;
		
		if (statoConservativoVO.getCodStatoConservativo() == 0){
			
			statoConservativoVO.setCodStatoConservativo(getNextStatoConservativoID(arg0));
			Long newcod = wdb.insert(ImportDataHelper.STATOCONSERVATIVO_TAG, null, statoConservativoVO.getContentValue());
			if (newcod == -1){
				returnValue = false;
			}else{
				statoConservativoVO.setCodStatoConservativo(newcod.intValue());
			}
		}else{
			int rownum = wdb.update(ImportDataHelper.STATOCONSERVATIVO_TAG, statoConservativoVO.getContentValue(), StatoConservativoColumnNames.CODSTATOCONSERVATIVO + "=?", new String[]{statoConservativoVO.getCodStatoConservativo().toString()});
			if (rownum != 1){
				returnValue = false;
			}
		}
		if (this.sqllitedb == null){
//			wdb.close();
		}
		return returnValue;
	}

	public boolean saveClassiClienti(ClassiClientiVO classiClientiVO,SQLiteDatabase arg0){
		
		boolean returnValue = true;
		
		SQLiteDatabase wdb = null;
		if (this.sqllitedb == null && arg0==null){
			wdb = getWritableDatabase(); 
		}else if (this.sqllitedb == null && arg0!=null) {
			wdb = arg0;
		}else{
			wdb = this.sqllitedb;
		}
		
		if (classiClientiVO.getCodClasseCliente() == 0){
			
			classiClientiVO.setCodClasseCliente(getNextClassiClientiID(arg0));
			Long newcod = wdb.insert(ImportDataHelper.CLASSICLIENTI_TAG, null, classiClientiVO.getContentValue());
			if (newcod == -1){
				returnValue = false;
			}else{
				classiClientiVO.setCodClasseCliente(newcod.intValue());
			}
		}else{
			int rownum = wdb.update(ImportDataHelper.CLASSICLIENTI_TAG, classiClientiVO.getContentValue(), ClassiClienteColumnNames.CODCLASSECLIENTE + "=?", new String[]{classiClientiVO.getCodClasseCliente().toString()});
			if (rownum != 1){
				returnValue = false;
			}
		}
		if (this.sqllitedb == null){
//			wdb.close();
		}
		return returnValue;
	}

	public boolean saveTipologiaContatti(TipologiaContattiVO tipologiaContattiVO,SQLiteDatabase arg0){
		
		boolean returnValue = true;
		
		SQLiteDatabase wdb = (this.sqllitedb == null && arg0==null)?getWritableDatabase():(this.sqllitedb == null)?arg0:this.sqllitedb;
		
		if (tipologiaContattiVO.getCodTipologiaContatto() == 0){
			
			tipologiaContattiVO.setCodTipologiaContatto(getNextTipologiaContattiID(arg0));
			Long newcod = wdb.insert(ImportDataHelper.TIPOLOGIACONTATTO_TAG, null, tipologiaContattiVO.getContentValue());
			if (newcod == -1){
				returnValue = false;
			}else{
				tipologiaContattiVO.setCodTipologiaContatto(newcod.intValue());
			}
		}else{
			int rownum = wdb.update(ImportDataHelper.TIPOLOGIACONTATTO_TAG, tipologiaContattiVO.getContentValue(), TipologieContattiColumnNames.CODTIPOLOGIACONTATTO + "=?", new String[]{tipologiaContattiVO.getCodTipologiaContatto().toString()});
			if (rownum != 1){
				returnValue = false;
			}
		}
		if (this.sqllitedb == null){
//			wdb.close();
		}
		return returnValue;
	}

	public boolean saveColloquio(ColloquiVO colloquio){
		
		boolean returnValue = true;
		
		SQLiteDatabase wdb = (this.sqllitedb == null)?getWritableDatabase():this.sqllitedb;
		
		if (colloquio.getCodColloquio() == 0){
			
			colloquio.setCodColloquio(getNextColloquiID());
			Long newcod = wdb.insert(ImportDataHelper.COLLOQUI_TAG, null, colloquio.getContentValue());
			if (newcod == -1){
				returnValue = false;
			}else{
				colloquio.setCodColloquio(newcod.intValue());
			}
		}else{
//			int rownum = wdb.update(ImportDataHelper.COLLOQUI_TAG, 
//									colloquio.getContentValue(), 
//									ColloquiColumnNames.CODCOLLOQUIO + "=?", 
//									new String[]{String.valueOf(colloquio.getCodColloquio())});
			String UPDATEQUERY = null;
			try {
				UPDATEQUERY = "UPDATE COLLOQUI " + 
									  "SET DESCRIZIONE = '" + colloquio.getDescrizione() + "', " + 
									  "CODAGENTEINSERITORE = " + String.valueOf(colloquio.getCodAgenteInseritore()) + ", " +
									  "CODIMMOBILEABBINATO = " + String.valueOf(colloquio.getCodImmobileAbbinato()) + ", " +
									  "CODTIPOLOGIACOLLOQUIO = " + String.valueOf(colloquio.getCodImmobileAbbinato()) + ", " +
									  "DATAINSERIMENTO = '" + DateFormatUtils.getInstace().format_xml(colloquio.getDataInserimento()) + "', " + 
									  "DATACOLLOQUIO = '" + DateFormatUtils.getInstace().format_xml(colloquio.getDataColloquio()) + "', " +
									  "LUOGO  = '" + colloquio.getLuogoIncontro() + "', " +
									  "SCADENZIERE = " + ((colloquio.getScadenziere())?"1":"0") + ", " +
									  "COMMENTOAGENZIA = '" + colloquio.getCommentoAgenzia() + "', " +
									  "COMMENTOCLIENTE = '" + colloquio.getCommentoCliente() + "', " +
									  "CODPARENT = " + String.valueOf(colloquio.getCodParent()) + ", " +
									  "ICALUID = '" + colloquio.getiCalUid() + "' " +
									  "WHERE CODCOLLOQUIO = " + String.valueOf(colloquio.getCodColloquio());
			} catch (Exception e1) {
				Log.e("WINKHOUSEUPDATECOLLOQUIO", UPDATEQUERY);
			}
			
			try {
				wdb.execSQL(UPDATEQUERY);				
			} catch (android.database.SQLException e) {
				Log.e("WINKHOUSEUPDATECOLLOQUIO", UPDATEQUERY);
				Log.e("WINKHOUSEUPDATECOLLOQUIO", e.getMessage());
				returnValue = false;
			}
			
		}
		if (this.sqllitedb == null){
//			wdb.close();
		}
		return returnValue;
	}

	public boolean saveColloquioAnagrafica(ColloquiAnagraficheVO colloqui_anagrafiche){
		
		boolean returnValue = true;
		
		SQLiteDatabase wdb = (this.sqllitedb == null)?getWritableDatabase():this.sqllitedb;
		
		if (colloqui_anagrafiche.getCodColloquioAnagrafiche() == 0){
			
			colloqui_anagrafiche.setCodColloquioAnagrafiche(getNextColloquiAnagraficheID());
			Long newcod = wdb.insert(ImportDataHelper.COLLOQUIANAGRAFICHE_TABLE_NAME, null, colloqui_anagrafiche.getContentValue());
			if (newcod == -1){
				returnValue = false;
			}else{
				colloqui_anagrafiche.setCodColloquioAnagrafiche(newcod.intValue());
			}
		}else{
			int rownum = wdb.update(ImportDataHelper.COLLOQUIANAGRAFICHE_TABLE_NAME, 
									colloqui_anagrafiche.getContentValue(), 
									ColloquiAnagraficheColumnNames.CODCOLLOQUIANAGRAFICHE + "=?", 
									new String[]{colloqui_anagrafiche.getCodColloquioAnagrafiche().toString()});
			if (rownum != 1){
				returnValue = false;
			}
		}
		if (this.sqllitedb == null){
//			wdb.close();
		}
		return returnValue;
	}

	
	public boolean saveTipologiaStanze(TipologiaStanzeVO tipologiaStanzeVO, SQLiteDatabase arg0){
		
		boolean returnValue = true;
		
		SQLiteDatabase wdb = (this.sqllitedb == null && arg0==null)?getWritableDatabase():(this.sqllitedb == null)?arg0:this.sqllitedb;
		
		if (tipologiaStanzeVO.getCodTipologiaStanza() == 0){
			
			tipologiaStanzeVO.setCodTipologiaStanza(getNextTipologiaContattiID(arg0));
			Long newcod = wdb.insert(ImportDataHelper.TIPOLOGIASTANZE_TAG, null, tipologiaStanzeVO.getContentValue());
			if (newcod == -1){
				returnValue = false;
			}else{
				tipologiaStanzeVO.setCodTipologiaStanza(newcod.intValue());
			}
		}else{
			int rownum = wdb.update(ImportDataHelper.TIPOLOGIASTANZE_TAG, tipologiaStanzeVO.getContentValue(), TipologieStanzeColumnNames.CODTIPOLOGIASTANZA + "=?", new String[]{tipologiaStanzeVO.getCodTipologiaStanza().toString()});
			if (rownum != 1){
				returnValue = false;
			}
		}
		if (this.sqllitedb == null){
//			wdb.close();
		}
		return returnValue;
	}

	public boolean saveImmagine(ImmagineVO immagine){
		
		boolean returnValue = true;
		
		SQLiteDatabase wdb = (this.sqllitedb == null)?getWritableDatabase():this.sqllitedb;
		
		if (immagine.getCodImmagine() == 0){
			
			immagine.setCodImmagine(getNextImmagineID());
			Long newcod = wdb.insert(ImportDataHelper.IMMAGINE_TAG, null, immagine.getContentValue());
			if (newcod == -1){
				returnValue = false;
			}else{
				immagine.setCodImmagine(newcod.intValue());
			}
		}else{
			int rownum = wdb.update(ImportDataHelper.IMMAGINE_TAG, immagine.getContentValue(), ImmagineColumnNames.CODIMMAGINE + "=?", new String[]{immagine.getCodImmagine().toString()});
			if (rownum != 1){
				returnValue = false;
			}
		}
		if (this.sqllitedb == null){
//			wdb.close();
		}
		return returnValue;
	}
	
	public void deleteImmobile(ImmobiliVO immobile){
		
		SQLiteDatabase wdb = (this.sqllitedb == null)?getWritableDatabase():this.sqllitedb;
		
		int result = wdb.delete(ImportDataHelper.IMMOBILI_TAG, ImmobiliColumnNames.CODIMMOBILE + "=?", new String[]{immobile.getCodImmobile().toString()});
		if (this.sqllitedb == null){
//			wdb.close();
		}
				
	}
	
	public void resetPropietario(Integer codAnagrafica){
		
		SQLiteDatabase wdb = (this.sqllitedb == null)?getWritableDatabase():this.sqllitedb;
		
		ContentValues cv = new ContentValues();
		cv.put(ImmobiliColumnNames.CODANAGRAFICA, 0);
		
		wdb.update(ImportDataHelper.IMMOBILI_TAG, cv, ImmobiliColumnNames.CODANAGRAFICA + "=?", new String[]{codAnagrafica.toString()});
		if (this.sqllitedb == null){
//			wdb.close();
		}
	}
	
	public void deleteImmaginiByImmobile(Integer codImmobile){

		SQLiteDatabase wdb = (this.sqllitedb == null)?getWritableDatabase():this.sqllitedb;
		
		wdb.delete(ImportDataHelper.IMMAGINE_TAG, ImmagineColumnNames.CODIMMOBILE + "=?", new String[]{codImmobile.toString()});
		if (this.sqllitedb == null){
//			wdb.close();
		}
		
	}
	
	public void deleteAnagrafica(AnagraficheVO anagrafica){
		
		SQLiteDatabase wdb = (this.sqllitedb == null)?getWritableDatabase():this.sqllitedb;
		
		wdb.delete(ImportDataHelper.ANAGRAFICHE_TAG, AnagraficheColumnNames.CODANAGRAFICA + "=?", new String[]{anagrafica.getCodAnagrafica().toString()});
		if (this.sqllitedb == null){
//			wdb.close();
		}
		
	}

	public void deleteAnagrafica(Integer codanagrafica){
		
		SQLiteDatabase wdb = (this.sqllitedb == null)?getWritableDatabase():this.sqllitedb;
		
		wdb.delete(ImportDataHelper.ANAGRAFICHE_TAG, AnagraficheColumnNames.CODANAGRAFICA + "=?", new String[]{codanagrafica.toString()});
		if (this.sqllitedb == null){
//			wdb.close();
		}
		
	}

	public void deleteImmobiliPropietariByCodAnagrafica(Integer codAnagrafica){
		
		SQLiteDatabase wdb = (this.sqllitedb == null)?getWritableDatabase():this.sqllitedb;
		
		wdb.delete(ImportDataHelper.IMMOBILIPROPIETARI_TAG, ImmobiliPropietaColumnNames.CODANAGRAFICA + "=?", new String[]{codAnagrafica.toString()});
		if (this.sqllitedb == null){
//			wdb.close();
		}
		
	}

	public void deleteImmobiliPropietariByCodImmobile(Integer codImmobile){
		
		SQLiteDatabase wdb = (this.sqllitedb == null)?getWritableDatabase():this.sqllitedb;
		
		wdb.delete(ImportDataHelper.IMMOBILIPROPIETARI_TAG, ImmobiliPropietaColumnNames.CODIMMOBILE + "=?", new String[]{codImmobile.toString()});
		if (this.sqllitedb == null){
//			wdb.close();
		}
		
	}

	public void deleteContattiByAnagrafica(Integer codAnagrafica){
		
		SQLiteDatabase wdb = (this.sqllitedb == null)?getWritableDatabase():this.sqllitedb;
		
		wdb.delete(ImportDataHelper.CONTATTI_TAG, AnagraficheColumnNames.CODANAGRAFICA + "=?", new String[]{codAnagrafica.toString()});
		if (this.sqllitedb == null){
//			wdb.close();
		}
		
	}

	public void deleteContatti(Integer[] codcontatti){
		
		SQLiteDatabase wdb = (this.sqllitedb == null)?getWritableDatabase():this.sqllitedb;
		for (Integer codcontatto : codcontatti) {
			wdb.delete(ImportDataHelper.CONTATTI_TAG, ContattiColumnNames.CODCONTATTO + "=?", new String[]{codcontatto.toString()});	
		}		
		if (this.sqllitedb == null){
//			wdb.close();
		}
		
	}

	public void deleteStanza(Integer[] codstanze){
		
		SQLiteDatabase wdb = (this.sqllitedb == null)?getWritableDatabase():this.sqllitedb;
		for (Integer codstanza : codstanze) {
			wdb.delete(ImportDataHelper.STANZEIMMOBILI_TAG, StanzeImmobiliColumnNames.CODSTANZEIMMOBILI + "=?", new String[]{codstanza.toString()});	
		}		
		if (this.sqllitedb == null){
//			wdb.close();
		}
		
	}

	public void deleteImmagini(Integer[] codimmagini){
		
		SQLiteDatabase wdb = (this.sqllitedb == null)?getWritableDatabase():this.sqllitedb;
		for (Integer codimmagine : codimmagini) {
			wdb.delete(ImportDataHelper.IMMAGINE_TAG, ImmagineColumnNames.CODIMMAGINE + "=?", new String[]{codimmagine.toString()});	
		}		
		if (this.sqllitedb == null){
//			wdb.close();
		}
		
	}
	
	public void deleteClasseEnergetica(ClasseEnergeticaVO classeEnergetica){
		
		SQLiteDatabase wdb = (this.sqllitedb == null)?getWritableDatabase():this.sqllitedb;
		
		wdb.delete(ImportDataHelper.CLASSEENERGETICA_TAG, ClasseEnergeticaColumnNames.CODCLASSEENERGETICA + "=?", new String[]{classeEnergetica.getCodClasseEnergetica().toString()});
		if (this.sqllitedb == null){
//			wdb.close();
		}
		
	}
	
	public void resetClasseEnergetica(Integer codClasseEnergetica){
		
		SQLiteDatabase wdb = (this.sqllitedb == null)?getWritableDatabase():this.sqllitedb;
		
		ContentValues cv = new ContentValues();
		cv.put(ImmobiliColumnNames.CODCLASSEENERGETICA, 0);
		
		wdb.update(ImportDataHelper.IMMOBILI_TAG, cv, ImmobiliColumnNames.CODCLASSEENERGETICA + "=?", new String[]{codClasseEnergetica.toString()});
		if (this.sqllitedb == null){
//			wdb.close();
		}
	}

	public void deleteRiscaldamento(RiscaldamentiVO riscaldamenti){
		
		SQLiteDatabase wdb = (this.sqllitedb == null)?getWritableDatabase():this.sqllitedb;
		
		wdb.delete(ImportDataHelper.RISCALDAMENTI_TAG, RiscaldamentiColumnNames.CODRISCALDAMENTO + "=?", new String[]{riscaldamenti.getCodRiscaldamento().toString()});
		if (this.sqllitedb == null){
//			wdb.close();
		}
		
	}
	
	public void resetRiscaldamento(Integer codRiscaldamento){
		
		SQLiteDatabase wdb = (this.sqllitedb == null)?getWritableDatabase():this.sqllitedb;
		
		ContentValues cv = new ContentValues();
		cv.put(ImmobiliColumnNames.CODRISCALDAMENTO, 0);
		
		wdb.update(ImportDataHelper.IMMOBILI_TAG, cv, ImmobiliColumnNames.CODRISCALDAMENTO + "=?", new String[]{codRiscaldamento.toString()});
		if (this.sqllitedb == null){
//			wdb.close();
		}
	}

	public void deleteTipologieImmobile(TipologieImmobiliVO tipologieImmobili){
		
		SQLiteDatabase wdb = (this.sqllitedb == null)?getWritableDatabase():this.sqllitedb;
		
		wdb.delete(ImportDataHelper.TIPOLOGIAIMMOBILI_TAG, TipologieImmobiliColumnNames.CODTIPOLOGIAIMMOBILE + "=?", new String[]{tipologieImmobili.getCodTipologiaImmobile().toString()});
		if (this.sqllitedb == null){
//			wdb.close();
		}
		
	}

	public void deleteImmobiliPropieta(ImmobiliPropietariVO immobiliPropietariVO){
		
		SQLiteDatabase wdb = (this.sqllitedb == null)?getWritableDatabase():this.sqllitedb;
		
		wdb.delete(ImportDataHelper.IMMOBILIPROPIETARI_TAG, 
				   ImmobiliPropietaColumnNames.CODANAGRAFICA + " = ? AND " + ImmobiliPropietaColumnNames.CODIMMOBILE + " = ?", 
				   new String[]{immobiliPropietariVO.getCodAnagrafica().toString(),immobiliPropietariVO.getCodImmobile().toString()});
		if (this.sqllitedb == null){
//			wdb.close();
		}
		
	}	

	public void deleteAnagraficheColloquiByCodColloquio(Integer codColloquio){
		
		SQLiteDatabase wdb = (this.sqllitedb == null)?getWritableDatabase():this.sqllitedb;
		
		wdb.delete(ImportDataHelper.COLLOQUIANAGRAFICHE_TABLE_NAME, 
				   ColloquiAnagraficheColumnNames.CODCOLLOQUIO + "=?", 
				   new String[]{codColloquio.toString()});
		if (this.sqllitedb == null){
//			wdb.close();
		}
		
	}	
	
	public void deleteAnagraficheColloquiByCodAnagrafica(Integer codAnagrafica){
		
		SQLiteDatabase wdb = (this.sqllitedb == null)?getWritableDatabase():this.sqllitedb;
		
		wdb.delete(ImportDataHelper.COLLOQUIANAGRAFICHE_TABLE_NAME, 
				   ColloquiAnagraficheColumnNames.CODANAGRAFICA + "=?", 
				   new String[]{codAnagrafica.toString()});
		if (this.sqllitedb == null){
//			wdb.close();
		}
		
	}	

	public void deleteAnagraficheColloqui(ColloquiAnagraficheVO caVO){
		
		SQLiteDatabase wdb = (this.sqllitedb == null)?getWritableDatabase():this.sqllitedb;
		
		int row = wdb.delete(ImportDataHelper.COLLOQUIANAGRAFICHE_TABLE_NAME, 
				   			 ColloquiAnagraficheColumnNames.CODANAGRAFICA + "=? AND " + ColloquiAnagraficheColumnNames.CODCOLLOQUIO + "=?", 
				   			 new String[]{caVO.getCodAnagrafica().toString(),caVO.getCodColloquio().toString()});
		
		if (this.sqllitedb == null){
//			wdb.close();
		}
		
	}	
	
	public void deleteAgentiColloquiByCodColloquio(Integer codColloquio){
		
		SQLiteDatabase wdb = (this.sqllitedb == null)?getWritableDatabase():this.sqllitedb;
		
		int num = wdb.delete(ImportDataHelper.COLLOQUIAGENTI_TABLE_NAME, 
				   			 ColloquiAgentiColumnNames.CODCOLLOQUIO + "=?", 
				   			 new String[]{codColloquio.toString()});
		if (this.sqllitedb == null){
//			wdb.close();
		}
		
	}	
	
	public void deleteAgentiColloquiByCodAgente(Integer codAgente){
		
		SQLiteDatabase wdb = (this.sqllitedb == null)?getWritableDatabase():this.sqllitedb;
		
		int num = wdb.delete(ImportDataHelper.COLLOQUIAGENTI_TABLE_NAME, 
				   			 ColloquiAgentiColumnNames.CODAGENTE + "=?", 
				   			 new String[]{codAgente.toString()});
		
		if (this.sqllitedb == null){
//			wdb.close();
		}
		
	}	

	public void deleteColloquio(Integer codColloquio){
		
		SQLiteDatabase wdb = (this.sqllitedb == null)?getWritableDatabase():this.sqllitedb;
		String DELETEQUERY = "DELETE FROM COLLOQUI WHERE CODCOLLOQUIO = " + String.valueOf(codColloquio);
		
		try {
			wdb.execSQL(DELETEQUERY);
		} catch (android.database.SQLException e) {
			Log.e("WINKHOUSE.DELETECOLLOQUIO", e.getMessage());
		}
		
//		int num = wdb.delete(ImportDataHelper.COLLOQUI_TAG, 
//				   			 ColloquiColumnNames.CODCOLLOQUIO + "=?", 
//				   			 new String[]{codColloquio.toString()});
		
		if (this.sqllitedb == null){
//			wdb.close();
		}
		
	}	

	public void deleteColloquioByCodImmobile(Integer codImmobile){
		
		SQLiteDatabase wdb = (this.sqllitedb == null)?getWritableDatabase():this.sqllitedb;
		
		wdb.delete(ImportDataHelper.COLLOQUI_TAG, 
				   ColloquiColumnNames.CODIMMOBILEABBINATO + " = ? ", 
				   new String[]{codImmobile.toString()});
		if (this.sqllitedb == null){
//			wdb.close();
		}
		
	}	

	public void deleteStanzeByCodImmobile(Integer codImmobile){
		
		SQLiteDatabase wdb = (this.sqllitedb == null)?getWritableDatabase():this.sqllitedb;
		
		wdb.delete(ImportDataHelper.STANZEIMMOBILI_TAG, 
				   StanzeImmobiliColumnNames.CODIMMOBILE + " = ? ", 
				   new String[]{codImmobile.toString()});
		
		if (this.sqllitedb == null){
//			wdb.close();
		}
		
	}	
	
	public void resetTipologieImmobile(Integer codTipologieImmobili){
		
		SQLiteDatabase wdb = (this.sqllitedb == null)?getWritableDatabase():this.sqllitedb;
		
		ContentValues cv = new ContentValues();
		cv.put(ImmobiliColumnNames.CODTIPOLOGIA, 0);
		
		wdb.update(ImportDataHelper.IMMOBILI_TAG, cv, ImmobiliColumnNames.CODTIPOLOGIA + "=?", new String[]{codTipologieImmobili.toString()});
		if (this.sqllitedb == null){
//			wdb.close();
		}
	}

	public void deleteStatoConservativo(StatoConservativoVO statoConservativo){
		
		SQLiteDatabase wdb = (this.sqllitedb == null)?getWritableDatabase():this.sqllitedb;
		
		wdb.delete(ImportDataHelper.STATOCONSERVATIVO_TAG, StatoConservativoColumnNames.CODSTATOCONSERVATIVO + "=?", new String[]{statoConservativo.getCodStatoConservativo().toString()});
		if (this.sqllitedb == null){
//			wdb.close();
		}
		
	}
	
	public void resetStatoConservativo(Integer codStatoConservativo){
		
		SQLiteDatabase wdb = (this.sqllitedb == null)?getWritableDatabase():this.sqllitedb;
		
		ContentValues cv = new ContentValues();
		cv.put(ImmobiliColumnNames.CODSTATO, 0);
		
		wdb.update(ImportDataHelper.IMMOBILI_TAG, cv, ImmobiliColumnNames.CODSTATO + "=?", new String[]{codStatoConservativo.toString()});
		if (this.sqllitedb == null){
//			wdb.close();
		}
	}

	public void deleteClassiClienti(ClassiClientiVO classiClienti){
		
		SQLiteDatabase wdb = (this.sqllitedb == null)?getWritableDatabase():this.sqllitedb;
		
		wdb.delete(ImportDataHelper.CLASSICLIENTI_TAG, ClassiClienteColumnNames.CODCLASSECLIENTE + "=?", new String[]{classiClienti.getCodClasseCliente().toString()});
		if (this.sqllitedb == null){
//			wdb.close();
		}
		
	}
	
	public void resetClassiClienti(Integer codClassiClienti){
		
		SQLiteDatabase wdb = (this.sqllitedb == null)?getWritableDatabase():this.sqllitedb;
		
		ContentValues cv = new ContentValues();
		cv.put(AnagraficheColumnNames.CODANAGRAFICA, 0);
		
		wdb.update(ImportDataHelper.IMMOBILI_TAG, cv, AnagraficheColumnNames.CODANAGRAFICA + "=?", new String[]{codClassiClienti.toString()});
		if (this.sqllitedb == null){
//			wdb.close();
		}
	}

	public void deleteTipologiaContatti(TipologiaContattiVO tipologiaContatti){
		
		SQLiteDatabase wdb = (this.sqllitedb == null)?getWritableDatabase():this.sqllitedb;
		
		wdb.delete(ImportDataHelper.TIPOLOGIACONTATTO_TAG, TipologieContattiColumnNames.CODTIPOLOGIACONTATTO + "=?", new String[]{tipologiaContatti.getCodTipologiaContatto().toString()});
		if (this.sqllitedb == null){
//			wdb.close();
		}
		
	}

	public void deleteTipologiaStanze(TipologiaStanzeVO tipologiaStanze){
		
		SQLiteDatabase wdb = (this.sqllitedb == null)?getWritableDatabase():this.sqllitedb;
		
		wdb.delete(ImportDataHelper.TIPOLOGIASTANZE_TAG, TipologieStanzeColumnNames.CODTIPOLOGIASTANZA + "=?", new String[]{tipologiaStanze.getCodTipologiaStanza().toString()});
		if (this.sqllitedb == null){
//			wdb.close();
		}
		
	}
	
	public void resetTipologiaContatti(Integer codTipologiaContatti){
		
		SQLiteDatabase wdb = (this.sqllitedb == null)?getWritableDatabase():this.sqllitedb;
		
		ContentValues cv = new ContentValues();
		cv.put(ContattiColumnNames.CODTIPOLOGIACONTATTO, 0);
		
		wdb.update(ImportDataHelper.CONTATTI_TAG, cv, ContattiColumnNames.CODTIPOLOGIACONTATTO + "=?", new String[]{codTipologiaContatti.toString()});
		if (this.sqllitedb == null){
//			wdb.close();
		}
	}

	public void resetTipologiaStanze(Integer codTipologiaStanze){
		
		SQLiteDatabase wdb = (this.sqllitedb == null)?getWritableDatabase():this.sqllitedb;
		
		ContentValues cv = new ContentValues();
		cv.put(TipologieStanzeColumnNames.CODTIPOLOGIASTANZA, 0);
		
		wdb.update(ImportDataHelper.TIPOLOGIASTANZE_TAG, cv, TipologieStanzeColumnNames.CODTIPOLOGIASTANZA + "=?", new String[]{codTipologiaStanze.toString()});
		if (this.sqllitedb == null){
//			wdb.close();
		}
	}

	public ArrayList<ImmobiliVO> searchImmobili(ArrayList<SearchParam> params,HashMap column_list){
		
		ArrayList<ImmobiliVO> returnValue = new ArrayList<ImmobiliVO>();
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		
		ArrayList al = getWhereClause(params);
		Cursor c = rdb.query(ImportDataHelper.IMMOBILI_TAG, buildColumnList(column_list), (String)al.get(0), (String[])al.get(1), null, null, null);
		while(c.moveToNext()){
			try {
				returnValue.add(new ImmobiliVO(c, column_list));
			} catch (SQLException e) {
				
			}
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}
		return returnValue;		
	}
	
	public ArrayList<ClasseEnergeticaVO> getAllClassiEnergetiche(HashMap column_list){
		ArrayList<ClasseEnergeticaVO> returnValue = new ArrayList<ClasseEnergeticaVO>();
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		Cursor c = rdb.query(ImportDataHelper.CLASSEENERGETICA_TAG, buildColumnList(column_list), null, null, null, null, "ORDINE");
		while(c.moveToNext()){
			try {
				returnValue.add(new ClasseEnergeticaVO(c, column_list));
			} catch (SQLException e) {
				
			}
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}

		return returnValue;
		
	}

	public ArrayList<AnagraficheVO> getAnagrafichePropietarie(HashMap column_list,Integer codImmobile){
		
		String query = "SELECT A.* FROM ANAGRAFICHE A JOIN IMMOBILIPROPIETARI IP ON A.CODANAGRAFICA = IP.CODANAGRAFICA WHERE IP.CODIMMOBILE = ? ORDER BY A.CODANAGRAFICA"; 
		
		ArrayList<AnagraficheVO> returnValue = new ArrayList<AnagraficheVO>();
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		Cursor c = rdb.rawQuery(query, new String[]{codImmobile.toString()});
		while(c.moveToNext()){
			try {
				returnValue.add(new AnagraficheVO(c, column_list));
			} catch (SQLException e) {
				
			}
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}
		return returnValue;
		
	}

	public Cursor getAnagrafichePropietarieCursor(HashMap column_list,Integer codImmobile){
		
		String query = "SELECT A.CODANAGRAFICA AS _id, A.* FROM ANAGRAFICHE A JOIN IMMOBILIPROPIETARI IP ON A.CODANAGRAFICA = IP.CODANAGRAFICA WHERE IP.CODIMMOBILE = ?"; 
		
		ArrayList<AnagraficheVO> returnValue = new ArrayList<AnagraficheVO>();
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		Cursor c = rdb.rawQuery(query, new String[]{codImmobile.toString()});
		return c;
		
	}

	public ArrayList<AnagraficheVO> getAnagraficheNotPropietarie(HashMap column_list,Integer codImmobile){
		
		String query = "SELECT * FROM ANAGRAFICHE WHERE CODANAGRAFICA NOT IN (SELECT CODANAGRAFICA FROM IMMOBILIPROPIETARI WHERE CODIMMOBILE = ?) ORDER BY CODANAGRAFICA"; 
		
		ArrayList<AnagraficheVO> returnValue = new ArrayList<AnagraficheVO>();
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		Cursor c = rdb.rawQuery(query, new String[]{codImmobile.toString()});
		while(c.moveToNext()){
			try {
				returnValue.add(new AnagraficheVO(c, column_list));
			} catch (SQLException e) {
				
			}
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}
		return returnValue;
		
	}

	public ArrayList<AnagraficheVO> getAnagraficheNotColloquio(HashMap column_list,Integer codColloquio){

		String query = "SELECT * FROM ANAGRAFICHE WHERE CODANAGRAFICA NOT IN (SELECT CODANAGRAFICA FROM COLLOQUIANAGRAFICHE WHERE CODCOLLOQUIO = ?) ORDER BY CODANAGRAFICA"; 
		
		ArrayList<AnagraficheVO> returnValue = new ArrayList<AnagraficheVO>();
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		Cursor c = rdb.rawQuery(query, new String[]{codColloquio.toString()});
		while(c.moveToNext()){
			try {
				returnValue.add(new AnagraficheVO(c, column_list));
			} catch (SQLException e) {
				
			}
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}
		return returnValue;
		
	}
	
	public ArrayList<ImmobiliVO> getImmobiliPropieta(HashMap column_list,Integer codAnagrafica){
		
		String query = "SELECT I.* FROM IMMOBILI I JOIN IMMOBILIPROPIETARI IP ON I.CODIMMOBILE = IP.CODIMMOBILE WHERE IP.CODANAGRAFICA = ? ORDER BY I.CODIMMOBILE"; 
		
		ArrayList<ImmobiliVO> returnValue = new ArrayList<ImmobiliVO>();
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		Cursor c = rdb.rawQuery(query, new String[]{codAnagrafica.toString()});
		while(c.moveToNext()){
			try {
				returnValue.add(new ImmobiliVO(c, column_list));
			} catch (SQLException e) {
				
			}
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}
		return returnValue;
		
	}

	public ArrayList<ImmobiliVO> getImmobiliNotPropietarie(HashMap column_list,Integer codAnagrafica){
		
		String query = "SELECT * FROM IMMOBILI WHERE CODIMMOBILE NOT IN (SELECT CODIMMOBILE FROM IMMOBILIPROPIETARI WHERE CODANAGRAFICA = ?) ORDER BY CODIMMOBILE"; 
		
		ArrayList<ImmobiliVO> returnValue = new ArrayList<ImmobiliVO>();
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		Cursor c = rdb.rawQuery(query, new String[]{codAnagrafica.toString()});
		while(c.moveToNext()){
			try {
				returnValue.add(new ImmobiliVO(c, column_list));
			} catch (SQLException e) {
				
			}
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}
		return returnValue;
		
	}
	
	public ArrayList<ClassiClientiVO> getAllClassiClienti(HashMap column_list){
		ArrayList<ClassiClientiVO> returnValue = new ArrayList<ClassiClientiVO>();
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		Cursor c = rdb.query(ImportDataHelper.CLASSICLIENTI_TAG, buildColumnList(column_list), null, null, null, null, "ORDINE");
		while(c.moveToNext()){
			try {
				returnValue.add(new ClassiClientiVO(c, column_list));
			} catch (SQLException e) {
				
			}
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}
		return returnValue;
		
	}

	public ArrayList<ContattiVO> getAllContatti(HashMap column_list){
		ArrayList<ContattiVO> returnValue = new ArrayList<ContattiVO>();
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		Cursor c = rdb.query(ImportDataHelper.CONTATTI_TAG, buildColumnList(column_list), null, null, null, null, null);
		while(c.moveToNext()){
			try {
				returnValue.add(new ContattiVO(c, column_list));
			} catch (SQLException e) {
				
			}
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}
		return returnValue;
		
	}

	public ArrayList<ImmagineVO> getAllImmagini(HashMap column_list){
		ArrayList<ImmagineVO> returnValue = new ArrayList<ImmagineVO>();
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		Cursor c = rdb.query(ImportDataHelper.IMMAGINE_TAG, buildColumnList(column_list), null, null, null, null, null);
		while(c.moveToNext()){
			try {
				returnValue.add(new ImmagineVO(c, column_list));
			} catch (SQLException e) {
				
			}
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}
		return returnValue;
		
	}

	public ArrayList<TipologieImmobiliVO> getAllTipologieImmobili(HashMap column_list){
		ArrayList<TipologieImmobiliVO> returnValue = new ArrayList<TipologieImmobiliVO>();
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		Cursor c = rdb.query(ImportDataHelper.TIPOLOGIAIMMOBILI_TAG, buildColumnList(column_list), null, null, null, null, "DESCRIZIONE");
		while(c.moveToNext()){
			try {
				returnValue.add(new TipologieImmobiliVO(c, column_list));
			} catch (SQLException e) {
				
			}
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}
		return returnValue;
		
	}

	public ArrayList<TipologieColloquiVO> getAllTipologieColloqui(HashMap column_list){
		
		ArrayList<TipologieColloquiVO> returnValue = new ArrayList<TipologieColloquiVO>();
		
		TipologieColloquiVO tcvo_generico = new TipologieColloquiVO();
		tcvo_generico.setCodTipologiaColloquio(TipologieColloquiVO.TYPE_GENERICO);
		tcvo_generico.setDescrizione("Generico");
		returnValue.add(tcvo_generico);

		TipologieColloquiVO tcvo_visita = new TipologieColloquiVO();
		tcvo_visita.setCodTipologiaColloquio(TipologieColloquiVO.TYPE_VISITA);
		tcvo_visita.setDescrizione("Visita");
		
		returnValue.add(tcvo_visita);

		return returnValue;
		
	}

	public HashMap<String,SysSettingVO> getAllSysSettings(HashMap column_list){
		HashMap<String,SysSettingVO> returnValue = new HashMap<String,SysSettingVO>();
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		Cursor c = rdb.query("SYSSETTINGS", buildColumnList(column_list), null, null, null, null, "SETTINGNAME");
		while(c.moveToNext()){
			try {
				SysSettingVO ssVO = new SysSettingVO(c, column_list);
				returnValue.put(ssVO.getSettingName(), ssVO);
			} catch (SQLException e) {
				
			}
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}
		return returnValue;
		
	}
	
	
	public ArrayList<TipologiaContattiVO> getAllTipicontatti(HashMap column_list){
		ArrayList<TipologiaContattiVO> returnValue = new ArrayList<TipologiaContattiVO>();
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		Cursor c = rdb.query(ImportDataHelper.TIPOLOGIACONTATTO_TAG, buildColumnList(column_list), null, null, null, null, "DESCRIZIONE");
		while(c.moveToNext()){
			try {
				returnValue.add(new TipologiaContattiVO(c, column_list));
			} catch (SQLException e) {
				
			}
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}
		return returnValue;
		
	}

	public ArrayList<TipologiaStanzeVO> getAllTipiStanze(HashMap column_list){
		ArrayList<TipologiaStanzeVO> returnValue = new ArrayList<TipologiaStanzeVO>();
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		Cursor c = rdb.query(ImportDataHelper.TIPOLOGIASTANZE_TAG, buildColumnList(column_list), null, null, null, null, TipologieStanzeColumnNames.DESCRIZIONE);
		while(c.moveToNext()){
			try {
				returnValue.add(new TipologiaStanzeVO(c, column_list));
			} catch (SQLException e) {
				
			}
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}
		return returnValue;
		
	}

	
	public ArrayList<RiscaldamentiVO> getAllRiscaldamenti(HashMap column_list){
		ArrayList<RiscaldamentiVO> returnValue = new ArrayList<RiscaldamentiVO>();
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		Cursor c = rdb.query(ImportDataHelper.RISCALDAMENTI_TAG, buildColumnList(column_list), null, null, null, null, "DESCRIZIONE");
		while(c.moveToNext()){
			try {
				returnValue.add(new RiscaldamentiVO(c, column_list));
			} catch (SQLException e) {
				
			}
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}
		return returnValue;
		
	}

	public ArrayList<StatoConservativoVO> getAllStatoConservativo(HashMap column_list){
		ArrayList<StatoConservativoVO> returnValue = new ArrayList<StatoConservativoVO>();
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		Cursor c = rdb.query(ImportDataHelper.STATOCONSERVATIVO_TAG, buildColumnList(column_list), null, null, null, null, "DESCRIZIONE");
		while(c.moveToNext()){
			try {
				returnValue.add(new StatoConservativoVO(c, column_list));
			} catch (SQLException e) {
				
			}
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}
		return returnValue;
		
	}
	
	public ArrayList<ImmagineVO> getImmaginiImmobile(Integer codImmobile,HashMap column_list){
		ArrayList<ImmagineVO> returnValue = new ArrayList<ImmagineVO>();
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		Cursor c = rdb.query(ImportDataHelper.IMMAGINE_TAG, buildColumnList(column_list), ImmagineColumnNames.CODIMMOBILE + "=?", new String[]{codImmobile.toString()}, null, null, ImmagineColumnNames.ORDINE);
		while(c.moveToNext()){
			try {
				returnValue.add(new ImmagineVO(c, column_list));
			} catch (SQLException e) {
				
			}
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}
		return returnValue;
		
	}
	
	public ArrayList<ContattiVO> getContattiAnagrafica(Integer codAnagrafica,HashMap column_list){
		ArrayList<ContattiVO> returnValue = new ArrayList<ContattiVO>();
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		Cursor c = rdb.query(ImportDataHelper.CONTATTI_TAG, buildColumnList(column_list), AnagraficheColumnNames.CODANAGRAFICA + "=?", new String[]{codAnagrafica.toString()}, null, null, null);
		while(c.moveToNext()){
			try {
				returnValue.add(new ContattiVO(c, column_list));
			} catch (SQLException e) {
				
			}
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}
		return returnValue;
		
	}
	
	public ArrayList<StanzeImmobiliVO> getStanzeImmobile(HashMap column_list,Integer codImmobile){
		
		ArrayList<StanzeImmobiliVO> returnValue = new ArrayList<StanzeImmobiliVO>();
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		Cursor c = rdb.query(ImportDataHelper.STANZEIMMOBILI_TAG, buildColumnList(column_list), StanzeImmobiliColumnNames.CODIMMOBILE + "=?", new String[]{codImmobile.toString()}, null, null, null);
		while(c.moveToNext()){
			try {
				returnValue.add(new StanzeImmobiliVO(c, column_list));
			} catch (SQLException e) {
				
			}
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}
		return returnValue;
		
	}
	
	public ArrayList<AnagraficheVO> getAnagraficheColloquio(Integer codColloquio,HashMap column_list){
		
		String query = "SELECT A.* FROM ANAGRAFICHE A JOIN COLLOQUIANAGRAFICHE CA ON A.CODANAGRAFICA = CA.CODANAGRAFICA WHERE CA.CODCOLLOQUIO = ? ORDER BY A.COGNOME,A.NOME"; 
		
		ArrayList<AnagraficheVO> returnValue = new ArrayList<AnagraficheVO>();
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		Cursor c = rdb.rawQuery(query, new String[]{codColloquio.toString()});
		while(c.moveToNext()){
			try {
				returnValue.add(new AnagraficheVO(c, column_list));
			} catch (SQLException e) {
				
			}
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}
		
		return returnValue;
		
	}

	public ArrayList<ColloquiVO> getColloquiAnagrafica(Integer codAnagrafica,HashMap column_list){
		
		String query = "SELECT DISTINCT C.* FROM COLLOQUI C JOIN COLLOQUIANAGRAFICHE CA ON C.CODCOLLOQUIO = CA.CODCOLLOQUIO WHERE CA.CODANAGRAFICA = ? ORDER BY C.DATACOLLOQUIO"; 
		
		ArrayList<ColloquiVO> returnValue = new ArrayList<ColloquiVO>();
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		Cursor c = rdb.rawQuery(query, new String[]{codAnagrafica.toString()});
		while(c.moveToNext()){
			try {
				returnValue.add(new ColloquiVO(c, column_list));
			} catch (SQLException e) {
				
			}
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}
		
		return returnValue;
		
	}

	public ArrayList<ColloquiVO> getColloquiImmobile(Integer codImmobile,HashMap column_list){
		
		ArrayList<ColloquiVO> returnValue = new ArrayList<ColloquiVO>();
		SQLiteDatabase rdb = (this.sqllitedb == null)?getReadableDatabase():this.sqllitedb;
		Cursor c = rdb.query(ImportDataHelper.COLLOQUI_TAG, buildColumnList(column_list), ColloquiColumnNames.CODIMMOBILEABBINATO + "=?", new String[]{codImmobile.toString()}, null, null, null);
		while(c.moveToNext()){
			try {
				returnValue.add(new ColloquiVO(c, column_list));
			} catch (SQLException e) {
				
			}
		}
		c.close();
		if (this.sqllitedb == null){
			// rdb.close();
		}
		return returnValue;
		
	}


	public SQLiteDatabase getSqllitedb() {
		return sqllitedb;
	}

	public void fillClassiEnergetiche(SQLiteDatabase arg0){
		
		ClasseEnergeticaVO ceVO_cp = new ClasseEnergeticaVO();
		ceVO_cp.setNome("casa passiva");
		ceVO_cp.setDescrizione("< 15Kwh/mq annuo = < 1,5 litri gasolio/mq annuo");
		ceVO_cp.setOrdine(1);
		saveClasseEnergetica(ceVO_cp,arg0);
		
		ClasseEnergeticaVO ceVO_A = new ClasseEnergeticaVO();
		ceVO_A.setNome("A");
		ceVO_A.setDescrizione("< 30 Kwh/mq annuo = < 3 litri gasolio/mq annuo");
		ceVO_A.setOrdine(2);
		saveClasseEnergetica(ceVO_A,arg0);

		ClasseEnergeticaVO ceVO_B = new ClasseEnergeticaVO();
		ceVO_B.setNome("B");
		ceVO_B.setDescrizione("tra 31 - 50 Kwh/mq annuo = 3,1 - 5 litri gasolio/mq annuo");
		ceVO_B.setOrdine(3);
		saveClasseEnergetica(ceVO_B,arg0);

		ClasseEnergeticaVO ceVO_C = new ClasseEnergeticaVO();
		ceVO_C.setNome("C");
		ceVO_C.setDescrizione("tra 51 - 70 kwh/mq annuo = 5,1 - 7 litri gasolio/mq annuo");
		ceVO_C.setOrdine(4);
		saveClasseEnergetica(ceVO_C,arg0);

		ClasseEnergeticaVO ceVO_D = new ClasseEnergeticaVO();
		ceVO_D.setNome("D");
		ceVO_D.setDescrizione("tra 71 - 90 Kwh/mq annuo = 7,1 - 9 litri gasolio/mq annuo");
		ceVO_D.setOrdine(5);
		saveClasseEnergetica(ceVO_D,arg0);

		ClasseEnergeticaVO ceVO_E = new ClasseEnergeticaVO();
		ceVO_E.setNome("E");
		ceVO_E.setDescrizione("tra 91 - 120 Kwh/mq annuo = 7,1 - 9 litri gasolio/mq annuo");
		ceVO_E.setOrdine(6);
		saveClasseEnergetica(ceVO_E,arg0);

		ClasseEnergeticaVO ceVO_F = new ClasseEnergeticaVO();
		ceVO_F.setNome("F");
		ceVO_F.setDescrizione("tra 121 - 160 Kwh/mq annuo = 12.1 - 16 litri gasolio/mq annuo");
		ceVO_F.setOrdine(7);
		saveClasseEnergetica(ceVO_F,arg0);

		ClasseEnergeticaVO ceVO_G = new ClasseEnergeticaVO();
		ceVO_G.setNome("G");
		ceVO_G.setDescrizione("> 160 Kwh/annuo = 16 litri gasolio/mq annuo");
		ceVO_G.setOrdine(8);
		saveClasseEnergetica(ceVO_G,arg0);

	}

	public void fillTipologiaImmobili(SQLiteDatabase arg0){
		
		TipologieImmobiliVO tiVO_1 = new TipologieImmobiliVO();
		tiVO_1.setDescrizione("Abitazione singola");
		saveTipologieImmobili(tiVO_1,arg0);

		TipologieImmobiliVO tiVO_2 = new TipologieImmobiliVO();
		tiVO_2.setDescrizione("Appartamento ingresso indipendente");
		saveTipologieImmobili(tiVO_2,arg0);

		TipologieImmobiliVO tiVO_3 = new TipologieImmobiliVO();
		tiVO_3.setDescrizione("Villetta a schiera");
		saveTipologieImmobili(tiVO_3,arg0);

		TipologieImmobiliVO tiVO_4 = new TipologieImmobiliVO();
		tiVO_4.setDescrizione("Appartamento in condominio");
		saveTipologieImmobili(tiVO_4,arg0);

		TipologieImmobiliVO tiVO_5 = new TipologieImmobiliVO();
		tiVO_5.setDescrizione("Villa con giardino");
		saveTipologieImmobili(tiVO_5,arg0);

		TipologieImmobiliVO tiVO_6 = new TipologieImmobiliVO();
		tiVO_6.setDescrizione("Attivita commerciale");
		saveTipologieImmobili(tiVO_6,arg0);

		TipologieImmobiliVO tiVO_7 = new TipologieImmobiliVO();
		tiVO_7.setDescrizione("Terreno agricolo");
		saveTipologieImmobili(tiVO_7,arg0);

		TipologieImmobiliVO tiVO_8 = new TipologieImmobiliVO();
		tiVO_8.setDescrizione("Terreno edificabile");
		saveTipologieImmobili(tiVO_8,arg0);

		TipologieImmobiliVO tiVO_9 = new TipologieImmobiliVO();
		tiVO_9.setDescrizione("Magazzino");
		saveTipologieImmobili(tiVO_9,arg0);

		TipologieImmobiliVO tiVO_10 = new TipologieImmobiliVO();
		tiVO_10.setDescrizione("Casale rustico");
		saveTipologieImmobili(tiVO_10,arg0);

		TipologieImmobiliVO tiVO_11 = new TipologieImmobiliVO();
		tiVO_11.setDescrizione("Bifamiliare");
		saveTipologieImmobili(tiVO_11,arg0);

		TipologieImmobiliVO tiVO_12 = new TipologieImmobiliVO();
		tiVO_12.setDescrizione("Deposito");
		saveTipologieImmobili(tiVO_12,arg0);

		TipologieImmobiliVO tiVO_13 = new TipologieImmobiliVO();
		tiVO_13.setDescrizione("Mansarda");
		saveTipologieImmobili(tiVO_13,arg0);

		TipologieImmobiliVO tiVO_14 = new TipologieImmobiliVO();
		tiVO_14.setDescrizione("Box auto");
		saveTipologieImmobili(tiVO_14,arg0);

		TipologieImmobiliVO tiVO_15 = new TipologieImmobiliVO();
		tiVO_15.setDescrizione("Capannone");
		saveTipologieImmobili(tiVO_15,arg0);

		TipologieImmobiliVO tiVO_16 = new TipologieImmobiliVO();
		tiVO_16.setDescrizione("Locale commerciale");
		saveTipologieImmobili(tiVO_16,arg0);

		TipologieImmobiliVO tiVO_17 = new TipologieImmobiliVO();
		tiVO_17.setDescrizione("Quadrifamiliare");
		saveTipologieImmobili(tiVO_17,arg0);

		TipologieImmobiliVO tiVO_18 = new TipologieImmobiliVO();
		tiVO_18.setDescrizione("Casa indipendente");
		saveTipologieImmobili(tiVO_18,arg0);

		TipologieImmobiliVO tiVO_19 = new TipologieImmobiliVO();
		tiVO_19.setDescrizione("Tipologia da verificare");
		saveTipologieImmobili(tiVO_19,arg0);

		TipologieImmobiliVO tiVO_20 = new TipologieImmobiliVO();
		tiVO_20.setDescrizione("Stalla");
		saveTipologieImmobili(tiVO_20,arg0);

		TipologieImmobiliVO tiVO_21 = new TipologieImmobiliVO();
		tiVO_21.setDescrizione("Palazzina");
		saveTipologieImmobili(tiVO_21,arg0);

		TipologieImmobiliVO tiVO_22 = new TipologieImmobiliVO();
		tiVO_22.setDescrizione("Altro");
		saveTipologieImmobili(tiVO_22,arg0);
		
	}
	
	public void fillClassiCliente(SQLiteDatabase arg0){
		
		ClassiClientiVO ccVO_1 = new ClassiClientiVO();
		ccVO_1.setDescrizione("Acquirente");
		ccVO_1.setOrdine(1);
		saveClassiClienti(ccVO_1,arg0);

		ClassiClientiVO ccVO_2 = new ClassiClientiVO();
		ccVO_2.setDescrizione("Affittuario");
		ccVO_2.setOrdine(2);
		saveClassiClienti(ccVO_2,arg0);

		ClassiClientiVO ccVO_3 = new ClassiClientiVO();
		ccVO_3.setDescrizione("Locatore");
		ccVO_3.setOrdine(3);
		saveClassiClienti(ccVO_3,arg0);

		ClassiClientiVO ccVO_4 = new ClassiClientiVO();
		ccVO_4.setDescrizione("Azienda edile");
		ccVO_4.setOrdine(4);
		saveClassiClienti(ccVO_4,arg0);

		ClassiClientiVO ccVO_5 = new ClassiClientiVO();
		ccVO_5.setDescrizione("Proprietario");
		ccVO_5.setOrdine(5);
		saveClassiClienti(ccVO_5,arg0);

		ClassiClientiVO ccVO_6 = new ClassiClientiVO();
		ccVO_6.setDescrizione("Incaricato");
		ccVO_6.setOrdine(6);
		saveClassiClienti(ccVO_6,arg0);

		ClassiClientiVO ccVO_7 = new ClassiClientiVO();
		ccVO_7.setDescrizione("Procuratore");
		ccVO_7.setOrdine(7);
		saveClassiClienti(ccVO_7,arg0);

		ClassiClientiVO ccVO_8 = new ClassiClientiVO();
		ccVO_8.setDescrizione("Nominativo generico");
		ccVO_8.setOrdine(8);
		saveClassiClienti(ccVO_8,arg0);

		ClassiClientiVO ccVO_9 = new ClassiClientiVO();
		ccVO_9.setDescrizione("Propietario incarico");
		ccVO_9.setOrdine(9);
		saveClassiClienti(ccVO_9,arg0);

		ClassiClientiVO ccVO_10 = new ClassiClientiVO();
		ccVO_10.setDescrizione("Geometra");
		ccVO_10.setOrdine(10);
		saveClassiClienti(ccVO_10,arg0);

		ClassiClientiVO ccVO_11 = new ClassiClientiVO();
		ccVO_11.setDescrizione("Ingegnere");
		ccVO_11.setOrdine(11);
		saveClassiClienti(ccVO_11,arg0);

		ClassiClientiVO ccVO_12 = new ClassiClientiVO();
		ccVO_12.setDescrizione("Architetto");
		ccVO_12.setOrdine(12);
		saveClassiClienti(ccVO_12,arg0);
		
		ClassiClientiVO ccVO_13 = new ClassiClientiVO();
		ccVO_13.setDescrizione("Contatto telemarketing");
		ccVO_13.setOrdine(13);
		saveClassiClienti(ccVO_13,arg0);
		
	}
	
	public void fillRiscaldamenti(SQLiteDatabase arg0){
		
		RiscaldamentiVO rVO_1 = new RiscaldamentiVO();
		rVO_1.setDescrizione("Caldaia autonoma");
		saveRiscaldamento(rVO_1,arg0);
		
		RiscaldamentiVO rVO_2 = new RiscaldamentiVO();
		rVO_2.setDescrizione("Caldaia autonoma a condensazione");
		saveRiscaldamento(rVO_2,arg0);

		RiscaldamentiVO rVO_3 = new RiscaldamentiVO();
		rVO_3.setDescrizione("Caldaia centralizzata");
		saveRiscaldamento(rVO_3,arg0);

		RiscaldamentiVO rVO_4 = new RiscaldamentiVO();
		rVO_4.setDescrizione("Pompa di calore");
		saveRiscaldamento(rVO_4,arg0);

		RiscaldamentiVO rVO_5 = new RiscaldamentiVO();
		rVO_5.setDescrizione("Solo aria condizionata");
		saveRiscaldamento(rVO_5,arg0);

		RiscaldamentiVO rVO_6 = new RiscaldamentiVO();
		rVO_6.setDescrizione("Stufa o camino");
		saveRiscaldamento(rVO_6,arg0);

	}
	
	public void fillStatoConservativo(SQLiteDatabase arg0){
		
		StatoConservativoVO scVO_1 = new StatoConservativoVO();
		scVO_1.setDescrizione("Su progetto");
		saveStatoConservativo(scVO_1,arg0);
		
		StatoConservativoVO scVO_2 = new StatoConservativoVO();
		scVO_2.setDescrizione("Nuovo");
		saveStatoConservativo(scVO_2,arg0);

		StatoConservativoVO scVO_3 = new StatoConservativoVO();
		scVO_3.setDescrizione("Da ristrutturare lievemente");
		saveStatoConservativo(scVO_3,arg0);

		StatoConservativoVO scVO_4 = new StatoConservativoVO();
		scVO_4.setDescrizione("Da ristrutturare");
		saveStatoConservativo(scVO_4,arg0);

		StatoConservativoVO scVO_5 = new StatoConservativoVO();
		scVO_5.setDescrizione("Da ristrutturare completamente");
		saveStatoConservativo(scVO_5,arg0);

		StatoConservativoVO scVO_6 = new StatoConservativoVO();
		scVO_6.setDescrizione("Mediocre");
		saveStatoConservativo(scVO_6,arg0);

		StatoConservativoVO scVO_7 = new StatoConservativoVO();
		scVO_7.setDescrizione("Buono");
		saveStatoConservativo(scVO_7,arg0);

		StatoConservativoVO scVO_8 = new StatoConservativoVO();
		scVO_8.setDescrizione("Ottimo");
		saveStatoConservativo(scVO_8,arg0);

		StatoConservativoVO scVO_9 = new StatoConservativoVO();
		scVO_9.setDescrizione("Da tinteggiare");
		saveStatoConservativo(scVO_9,arg0);

		StatoConservativoVO scVO_10 = new StatoConservativoVO();
		scVO_10.setDescrizione("Da rimodernare");
		saveStatoConservativo(scVO_10,arg0);

		StatoConservativoVO scVO_11 = new StatoConservativoVO();
		scVO_11.setDescrizione("Da riprendere");
		saveStatoConservativo(scVO_11,arg0);

		StatoConservativoVO scVO_12 = new StatoConservativoVO();
		scVO_12.setDescrizione("Da ultimare");
		saveStatoConservativo(scVO_12,arg0);

	}
	
	public void fillTipologiaContatti(SQLiteDatabase arg0){
		
		TipologiaContattiVO tcVO_1 = new TipologiaContattiVO();
		tcVO_1.setDescrizione("Telefono fisso casa");
		saveTipologiaContatti(tcVO_1,arg0);
		
		TipologiaContattiVO tcVO_2 = new TipologiaContattiVO();
		tcVO_2.setDescrizione("Telefono fisso ufficio");
		saveTipologiaContatti(tcVO_2,arg0);

		TipologiaContattiVO tcVO_3 = new TipologiaContattiVO();
		tcVO_3.setDescrizione("Cellulare personale");
		saveTipologiaContatti(tcVO_3,arg0);

		TipologiaContattiVO tcVO_4 = new TipologiaContattiVO();
		tcVO_4.setDescrizione("Cellulare lavoro");
		saveTipologiaContatti(tcVO_4,arg0);

		TipologiaContattiVO tcVO_5 = new TipologiaContattiVO();
		tcVO_5.setDescrizione("Email");
		saveTipologiaContatti(tcVO_5,arg0);

		TipologiaContattiVO tcVO_6 = new TipologiaContattiVO();
		tcVO_6.setDescrizione("Fax");
		saveTipologiaContatti(tcVO_6,arg0);

	}
}
