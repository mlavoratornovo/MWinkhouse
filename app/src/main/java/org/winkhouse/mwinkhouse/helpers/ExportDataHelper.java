package org.winkhouse.mwinkhouse.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javolution.util.FastList;
import javolution.xml.XMLBinding;
import javolution.xml.XMLObjectWriter;
import javolution.xml.stream.XMLStreamException;

import org.winkhouse.mwinkhouse.models.AbbinamentiVO;
import org.winkhouse.mwinkhouse.models.AffittiAllegatiVO;
import org.winkhouse.mwinkhouse.models.AffittiAnagraficheVO;
import org.winkhouse.mwinkhouse.models.AffittiRateVO;
import org.winkhouse.mwinkhouse.models.AffittiSpeseVO;
import org.winkhouse.mwinkhouse.models.AffittiVO;
import org.winkhouse.mwinkhouse.models.AgentiAppuntamentiVO;
import org.winkhouse.mwinkhouse.models.AgentiVO;
import org.winkhouse.mwinkhouse.models.AllegatiColloquiVO;
import org.winkhouse.mwinkhouse.models.AllegatiImmobiliVO;
import org.winkhouse.mwinkhouse.models.AnagraficheAppuntamentiVO;
import org.winkhouse.mwinkhouse.models.AnagraficheVO;
import org.winkhouse.mwinkhouse.models.AppuntamentiVO;
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
import org.winkhouse.mwinkhouse.models.GCalendarVO;
import org.winkhouse.mwinkhouse.models.ImmagineVO;
import org.winkhouse.mwinkhouse.models.ImmobiliPropietariVO;
import org.winkhouse.mwinkhouse.models.ImmobiliVO;
import org.winkhouse.mwinkhouse.models.RicercheVO;
import org.winkhouse.mwinkhouse.models.RiscaldamentiVO;
import org.winkhouse.mwinkhouse.models.StanzeImmobiliVO;
import org.winkhouse.mwinkhouse.models.StatoConservativoVO;
import org.winkhouse.mwinkhouse.models.TipiAppuntamentiVO;
import org.winkhouse.mwinkhouse.models.TipologiaContattiVO;
import org.winkhouse.mwinkhouse.models.TipologiaStanzeVO;
import org.winkhouse.mwinkhouse.models.TipologieImmobiliVO;

import android.content.Context;
import android.os.Environment;

public class ExportDataHelper {
	

	private String importDirectory = null;	
	private String exportDirectory = null;
	
	
	public ExportDataHelper(){
		importDirectory = Environment.getExternalStorageDirectory() + File.separator + "winkhouse/import";
		exportDirectory = Environment.getExternalStorageDirectory() + File.separator + "winkhouse/export";
	}
	
	protected boolean exportSelection(Object obj,String fileName){
		
		boolean returnValue = true;
		
		FileOutputStream fos = null;
		
		File f_dir = new File(exportDirectory);
		
		if (f_dir.exists() == false){
			f_dir.mkdirs();
		}
		
		File f = new File(exportDirectory + File.separator + fileName);
		
		try {
			
			f.createNewFile();
		    fos = new FileOutputStream(f);
		    
			XMLObjectWriter xmlow = XMLObjectWriter.newInstance(fos);
			xmlow.setBinding(getBindingsObj());
			
			if (obj instanceof ArrayList){
				Iterator it = ((ArrayList)obj).iterator();
				while(it.hasNext()){
					xmlow.write(it.next());
				}
			}else{
				xmlow.write(obj);	
			}
											
			
			xmlow.close();

		} catch (FileNotFoundException e1) {
			returnValue = false;
		} catch (IOException e2) {
			returnValue = false;
		} catch (XMLStreamException e1) {
			returnValue = false;
		}
						
		return returnValue;
		
	}
	
	protected XMLBinding getBindingsObj(){
		
		XMLBinding winkhouseBinding = new XMLBinding();
		
		winkhouseBinding.setAlias(AbbinamentiVO.class, "abbinamenti");
		winkhouseBinding.setAlias(AffittiAllegatiVO.class, "allegati_affitti");
		winkhouseBinding.setAlias(AffittiAnagraficheVO.class, "affitti_anagrafiche");
		winkhouseBinding.setAlias(AffittiRateVO.class, "affitti_rate");
		winkhouseBinding.setAlias(AffittiSpeseVO.class, "affitti_spese");
		winkhouseBinding.setAlias(AffittiVO.class, "affitti");
		winkhouseBinding.setAlias(AgentiAppuntamentiVO.class, "agenti_appuntamenti");
		winkhouseBinding.setAlias(AgentiVO.class, "agenti");
		winkhouseBinding.setAlias(AllegatiColloquiVO.class, "allegati_colloqui");
		winkhouseBinding.setAlias(AllegatiImmobiliVO.class, "allegati_immobili");
		winkhouseBinding.setAlias(AnagraficheAppuntamentiVO.class, "anagrafiche_appuntamenti");
		winkhouseBinding.setAlias(AnagraficheVO.class, "anagrafiche");
		winkhouseBinding.setAlias(AppuntamentiVO.class, "appuntamenti");
		winkhouseBinding.setAlias(ClasseEnergeticaVO.class, "classeenergetica");
		winkhouseBinding.setAlias(ClassiClientiVO.class, "classiclienti");
		winkhouseBinding.setAlias(ColloquiAgentiVO.class, "colloqui_agenti");
		winkhouseBinding.setAlias(ColloquiAnagraficheVO.class, "colloqui_anagrafiche");
//		winkhouseBinding.setAlias(ColloquiCriteriRicercaVO.class, "colloqui_criteriricerca");
		winkhouseBinding.setAlias(ColloquiVO.class, "colloqui");
		winkhouseBinding.setAlias(ContattiVO.class, "contatti");
//		winkhouseBinding.setAlias(CriteriRicercaVO.class, "criteriricerca");
		winkhouseBinding.setAlias(DatiCatastaliVO.class, "daticatastali");
		winkhouseBinding.setAlias(GCalendarVO.class, "gcalendar");
		winkhouseBinding.setAlias(ImmagineVO.class, "immagine");
		winkhouseBinding.setAlias(ImmobiliVO.class, "immobili");
		winkhouseBinding.setAlias(RicercheVO.class, "ricerche");
		winkhouseBinding.setAlias(RiscaldamentiVO.class, "riscaldamenti");
		winkhouseBinding.setAlias(StanzeImmobiliVO.class, "stanzeimmobili");
		winkhouseBinding.setAlias(StatoConservativoVO.class, "statoconservativo");
		winkhouseBinding.setAlias(TipiAppuntamentiVO.class, "tipologiaappuntamenti");
		winkhouseBinding.setAlias(TipologiaContattiVO.class, "tipologiacontatti");
		winkhouseBinding.setAlias(TipologiaStanzeVO.class, "tipologiastanze");
		winkhouseBinding.setAlias(TipologieImmobiliVO.class, "tipologiaimmobili");
		winkhouseBinding.setAlias(EntityVO.class, "entita");
		winkhouseBinding.setAlias(AttributeVO.class, "attributo");
		winkhouseBinding.setAlias(AttributeValueVO.class, "valoreattributo");
		winkhouseBinding.setAlias(FastList.class, "lista");
		winkhouseBinding.setAlias(EntityVO.class, "entita");
		winkhouseBinding.setAlias(AttributeVO.class, "attributo");
		winkhouseBinding.setAlias(AttributeValueVO.class, "valoreattributo");
		winkhouseBinding.setAlias(ImmobiliPropietariVO.class, "immobilipropieta");		
				
		return winkhouseBinding;
		
	}
	
	
	public void copy(File src, File dst) throws IOException {
		
		if (src.exists()){
			
		    InputStream in = new FileInputStream(src);
		    OutputStream out = new FileOutputStream(dst);
			    
		    byte[] buf = new byte[1024];
		    int len;
		    while ((len = in.read(buf)) > 0) {
		        out.write(buf, 0, len);
		    }
		    in.close();
		    out.close();
		}
		
	}
	
	public void exportImmobiliToXML(DataBaseHelper sqldb){
		ArrayList<ImmobiliVO> ivos = sqldb.getAllImmobili(null);
		exportSelection(ivos, ImportDataHelper.IMMOBILI_FILENAME);				
	} 

	public void exportAnagraficheToXML(DataBaseHelper sqldb){
		ArrayList<AnagraficheVO> ivos = sqldb.getAllAnagrafiche(null);
		exportSelection(ivos, ImportDataHelper.ANAGRAFICHE_FILENAME);				
	}
	
	public void exportAgentiToXML(DataBaseHelper sqldb){
		
		File f_agenti = new File(importDirectory + File.separator + ImportDataHelper.AGENTI_FILENAME);
		File f_agenti_export = new File(exportDirectory + File.separator + ImportDataHelper.AGENTI_FILENAME);
		
		try {
			copy(f_agenti, f_agenti_export);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
	} 

	public void exportClasseEnergeticaToXML(DataBaseHelper sqldb){

		ArrayList<ClasseEnergeticaVO> ceVOs = sqldb.getAllClassiEnergetiche(null);
		exportSelection(ceVOs, ImportDataHelper.CLASSEENERGETICA_FILENAME);				
	} 

	public void exportAbbinamentiToXML(DataBaseHelper sqldb){
		
		File f_abbinamenti = new File(importDirectory + File.separator + ImportDataHelper.ABBINAMENTI_FILENAME);
		File f_abbinamenti_export = new File(exportDirectory + File.separator + ImportDataHelper.ABBINAMENTI_FILENAME);
		
		try {
			copy(f_abbinamenti, f_abbinamenti_export);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	} 

	public void exportClassiClienteToXML(DataBaseHelper sqldb){
		
		ArrayList<ClassiClientiVO> clVOs = sqldb.getAllClassiClienti(null);
		exportSelection(clVOs, ImportDataHelper.CLASSICLIENTI_FILENAME);		
				
	} 

	public void exportRiscaldamentiToXML(DataBaseHelper sqldb){
		
		ArrayList<RiscaldamentiVO> rVOs = sqldb.getAllRiscaldamenti(null);
		exportSelection(rVOs, ImportDataHelper.RISCALDAMENTI_FILENAME);				
	} 

	public void exportStatoConservativoToXML(DataBaseHelper sqldb){
		
		ArrayList<StatoConservativoVO> scVOs = sqldb.getAllStatoConservativo(null);
		exportSelection(scVOs, ImportDataHelper.STATOCONSERVATIVO_FILENAME);				
	} 

	public void exportTipologiaContattiToXML(DataBaseHelper sqldb){
		
		ArrayList<TipologiaContattiVO> tcVOs = sqldb.getAllTipicontatti(null);
		exportSelection(tcVOs, ImportDataHelper.TIPOLOGIACONTATTO_FILENAME);
		
	}
		
	public void exportTipologieImmobiliToXML(DataBaseHelper sqldb){
			
		ArrayList<TipologieImmobiliVO> tiVOs = sqldb.getAllTipologieImmobili(null);
		exportSelection(tiVOs, ImportDataHelper.TIPOLOGIAIMMOBILI_FILENAME);
	} 
	
	public void exportTipologiaStanzeToXML(DataBaseHelper sqldb){
		
		File f_tipistanze = new File(importDirectory + File.separator + ImportDataHelper.TIPOLOGIASTANZE_FILENAME);
		File f_tipistanze_export = new File(exportDirectory + File.separator + ImportDataHelper.TIPOLOGIASTANZE_FILENAME);
		
		try {
			copy(f_tipistanze, f_tipistanze_export);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	} 

	public void exportContattiToXML(DataBaseHelper sqldb){
		ArrayList<ContattiVO> cVOs = sqldb.getAllContatti(null);
		exportSelection(cVOs, ImportDataHelper.CONTATTI_FILENAME);				
	} 

	public void exportDatiCatastaliToXML(DataBaseHelper sqldb){

		File f_daticatastali = new File(importDirectory + File.separator + ImportDataHelper.DATICATASTALI_FILENAME);
		File f_daticatastali_export = new File(exportDirectory + File.separator + ImportDataHelper.DATICATASTALI_FILENAME);
		
		try {
			copy(f_daticatastali, f_daticatastali_export);
		} catch (IOException e) {
			e.printStackTrace();
		}
				
	} 

	public void exportImmaginiToXML(DataBaseHelper sqldb){
		ArrayList<ImmagineVO> iVOs = sqldb.getAllImmagini(null);
		exportSelection(iVOs, ImportDataHelper.IMMAGINE_FILENAME);				
	}
	
	public void copyImmaginiFolder(){

		File f_immagini 		= new File(importDirectory + File.separator + "immagini");
		File f_immagini_export 	= new File(exportDirectory + File.separator + "immagini");
		
		if (f_immagini.exists()){
			copyFolder(f_immagini, f_immagini_export);
		}
		
	}
	
	protected void copyFolder(File folderOrigin, File folderDest){
		
		File[] f_ori = folderOrigin.listFiles();
		
		if (!folderDest.exists()){
			folderDest.mkdirs();
		}
		
		for (int i = 0; i < f_ori.length; i++) {
			
			if (f_ori[i].isFile()){
				
				try {
					copy(f_ori[i], new File(folderDest.getAbsolutePath() + File.separator + f_ori[i].getName()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}else{
				
				copyFolder(f_ori[i],new File(folderDest.getAbsolutePath() + File.separator + f_ori[i].getName()));
				
			}
		}		
				
	}
	
	protected void deleteFolder(File folder){
		
		File[] items = folder.listFiles();
		if (items != null){
			for (int i = 0; i < items.length; i++) {
				if (items[i].isFile()){
					items[i].delete();
				}else{
					deleteFolder(items[i]);
				}
			}
		}
		
	}
	
	public void exportStanzeImmobiliToXML(DataBaseHelper sqldb){
		
		File f_stanze = new File(importDirectory + File.separator + ImportDataHelper.STANZEIMMOBILI_FILENAME);
		File f_stanze_export = new File(exportDirectory + File.separator + ImportDataHelper.STANZEIMMOBILI_FILENAME);
		
		try {
			copy(f_stanze, f_stanze_export);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	} 
	
	public void exportEntityToXML(DataBaseHelper sqldb){
		
		File f_entity = new File(importDirectory + File.separator + ImportDataHelper.ENTITA_FILENAME);
		File f_entity_export = new File(exportDirectory + File.separator + ImportDataHelper.ENTITA_FILENAME);
		
		try {
			copy(f_entity, f_entity_export);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	} 

	public void exportAttributeToXML(DataBaseHelper sqldb){

		File f_attribute = new File(importDirectory + File.separator + ImportDataHelper.ATTRIBUTI_FILENAME);
		File f_attribute_export = new File(exportDirectory + File.separator + ImportDataHelper.ATTRIBUTI_FILENAME);
		
		try {
			copy(f_attribute, f_attribute_export);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	} 

	public void exportAttributeValueToXML(DataBaseHelper sqldb){

		File f_attributevalue = new File(importDirectory + File.separator + ImportDataHelper.VALOREATTRIBUTO_FILENAME);
		File f_attributevalue_export = new File(exportDirectory + File.separator + ImportDataHelper.VALOREATTRIBUTO_FILENAME);
		
		try {
			copy(f_attributevalue, f_attributevalue_export);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void exportImmobiliPropietariToXML(DataBaseHelper sqldb,Context c){
		
		ArrayList<ImmobiliPropietariVO> al_immobiliPropietari = new ArrayList<ImmobiliPropietariVO>();
		
		ArrayList<ImmobiliVO> alimvos = sqldb.getAllImmobili(null);
		
		for (ImmobiliVO immobiliVO : alimvos) {
			
			ArrayList<AnagraficheVO> alavos = immobiliVO.getAnagrafichePropietarie(c, null);
			
			for (AnagraficheVO anagraficheVO : alavos) {
				
				ImmobiliPropietariVO ipvo = new ImmobiliPropietariVO();
				
				ipvo.setCodImmobile(immobiliVO.getCodImmobile());
				ipvo.setCodAnagrafica(anagraficheVO.getCodAnagrafica());
				
				al_immobiliPropietari.add(ipvo);
				
			}
			
		}
		
		exportSelection(al_immobiliPropietari, ImportDataHelper.IMMOBILIPROPIETARI_FILENAME);
		
	}	

	public String getImportDirectory() {
		return importDirectory;
	}

	public String getExportDirectory() {
		return exportDirectory;
	}
	

}
