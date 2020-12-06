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

    private ArrayList itemsToExport = null;
    public final static int EXPORT_TYPE_IMMOBILI = 0;
    public final static int EXPORT_TYPE_ANAGRAFICHE = 1;
    private int exportType = -1;

	private String importDirectory = null;

    public void setImportDirectory(String importDirectory) {
        this.importDirectory = importDirectory;
    }

    public void setExportDirectory(String exportDirectory) {
        this.exportDirectory = exportDirectory;
    }

    private String exportDirectory = null;
	
	public ExportDataHelper(ArrayList itemsToExport) throws  Exception{
        this.itemsToExport = itemsToExport;
        if (itemsToExport != null && itemsToExport.size() > 0){
            if (itemsToExport.get(0) instanceof ImmobiliVO){
                exportType = EXPORT_TYPE_IMMOBILI;
            }else if (itemsToExport.get(0) instanceof AnagraficheVO){
                exportType = EXPORT_TYPE_ANAGRAFICHE;
            }else{
                throw new Exception("Elementi da esportare sconosciuti");
            }
        }
//        else{
//            throw new Exception("Nessun elemento da esportare");
//        }

//		importDirectory = Environment.getExternalStorageDirectory() + File.separator + "winkhouse/import";
//		exportDirectory = Environment.getExternalStorageDirectory() + File.separator + "winkhouse/export" + File.separator + "tmp";
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
	
	public void exportImmobiliToXML(DataBaseHelper sqldb, Context c){
        if (exportType == EXPORT_TYPE_IMMOBILI) {
            exportSelection(itemsToExport, ImportDataHelper.IMMOBILI_FILENAME);
        }

        if (exportType == EXPORT_TYPE_ANAGRAFICHE) {

            ArrayList itemsToExport = new ArrayList();

            for (Object anagrafica : itemsToExport){

                itemsToExport.addAll(((AnagraficheVO)anagrafica).getImmobiliPropietari(c, null));

            }

            exportSelection(itemsToExport, ImportDataHelper.IMMOBILI_FILENAME);
        }
	} 

	public void exportAnagraficheToXML(DataBaseHelper sqldb, Context c){

        if (exportType == EXPORT_TYPE_ANAGRAFICHE) {
            exportSelection(itemsToExport, ImportDataHelper.IMMOBILI_FILENAME);
        }

        if (exportType == EXPORT_TYPE_IMMOBILI) {

            ArrayList itemsToExport = new ArrayList();

            for (Object immobile : itemsToExport){

                itemsToExport.addAll(((ImmobiliVO)immobile).getAnagrafichePropietarie(c, null));

            }

            exportSelection(itemsToExport, ImportDataHelper.IMMOBILI_FILENAME);
        }

	}
	
	public void exportAgentiToXML(DataBaseHelper sqldb){

        exportSelection(new ArrayList<AgentiVO>(), ImportDataHelper.AGENTI_FILENAME);

	} 

	public void exportClasseEnergeticaToXML(DataBaseHelper sqldb){

		ArrayList<ClasseEnergeticaVO> ceVOs = sqldb.getAllClassiEnergetiche(null);
		exportSelection(ceVOs, ImportDataHelper.CLASSEENERGETICA_FILENAME);				
	} 

	public void exportAbbinamentiToXML(DataBaseHelper sqldb){

	    exportSelection(new ArrayList<AbbinamentiVO>(), ImportDataHelper.ABBINAMENTI_FILENAME);

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

		ArrayList<TipologiaStanzeVO> alts = sqldb.getAllTipiStanze(null);
        exportSelection(alts, ImportDataHelper.TIPOLOGIASTANZE_FILENAME);

	} 

	public void exportContattiToXML(DataBaseHelper sqldb, Context c){

        ArrayList<ContattiVO> cVOs = new ArrayList<ContattiVO>();

        if (exportType == EXPORT_TYPE_IMMOBILI) {

            for (Object immobile : itemsToExport){

                ArrayList<AnagraficheVO> ala = ((ImmobiliVO)immobile).getAnagrafichePropietarie(c, null);
                for (AnagraficheVO avo : ala){
                    cVOs.addAll(sqldb.getContattiAnagrafica(avo.getCodAnagrafica(),null));
                }

            }

        }

        if (exportType == EXPORT_TYPE_ANAGRAFICHE) {

            for (Object anagrafica : itemsToExport){

                cVOs.addAll(sqldb.getContattiAnagrafica(((AnagraficheVO)anagrafica).getCodAnagrafica(),null));

            }

        }

		exportSelection(cVOs, ImportDataHelper.CONTATTI_FILENAME);				
	} 

	public void exportDatiCatastaliToXML(DataBaseHelper sqldb){

        exportSelection(new ArrayList<DatiCatastaliVO>(), ImportDataHelper.DATICATASTALI_FILENAME);

	} 

	public void exportImmaginiToXML(DataBaseHelper sqldb, Context c){

        ArrayList<ImmagineVO> iVOs = new ArrayList<ImmagineVO>();

        if (exportType == EXPORT_TYPE_IMMOBILI) {
            for (Object ivo : itemsToExport){
                iVOs.addAll(sqldb.getImmaginiImmobile(((ImmobiliVO)ivo).getCodImmobile(), null));
            }
        }
        if (exportType == EXPORT_TYPE_ANAGRAFICHE) {
            for (Object ivo : itemsToExport){
                ArrayList<ImmobiliVO> alimms = ((AnagraficheVO)ivo).getImmobiliPropietari(c,null);
                for (ImmobiliVO iVO : alimms){
                    iVOs.addAll(sqldb.getImmaginiImmobile(iVO.getCodImmobile(), null));
                }
            }
        }

		exportSelection(iVOs, ImportDataHelper.IMMAGINE_FILENAME);
	}

    public void exportColloquiAgentiVOToXML(DataBaseHelper sqldb, Context c){

	    ArrayList<ColloquiAgentiVO> alca = new ArrayList<ColloquiAgentiVO>();
	    exportSelection(alca, ImportDataHelper.COLLOQUIAGENTI_FILENAME);

    }

    public void exportColloquiAnagraficheVOToXML(DataBaseHelper sqldb, Context c){

        ArrayList<ColloquiAnagraficheVO> caVOs = new ArrayList<ColloquiAnagraficheVO>();

        if (exportType == EXPORT_TYPE_IMMOBILI) {
            for (Object ivo : itemsToExport){
                ArrayList<ColloquiVO> alc = sqldb.getColloquiImmobile(((ImmobiliVO)ivo).getCodImmobile(), null);
                for (ColloquiVO cVO : alc){
                    caVOs.addAll(sqldb.getColloquiAnagraficheByCodColloquio(cVO.getCodColloquio(), null));
                }
            }
        }
        if (exportType == EXPORT_TYPE_ANAGRAFICHE) {
            for (Object ivo : itemsToExport){
                ArrayList<ColloquiVO> alc = sqldb.getColloquiAnagrafica(((AnagraficheVO)ivo).getCodAnagrafica(), null);
                for (ColloquiVO cVO : alc){
                    caVOs.addAll(sqldb.getColloquiAnagraficheByCodColloquio(cVO.getCodColloquio(), null));
                }
            }
        }

        exportSelection(caVOs, ImportDataHelper.COLLOQUIANAGRAFICHE_FILENAME);

    }

    public void exportColloquiVOToXML(DataBaseHelper sqldb, Context c){

        ArrayList<ColloquiVO> cVOs = new ArrayList<ColloquiVO>();

        if (exportType == EXPORT_TYPE_IMMOBILI) {
            for (Object ivo : itemsToExport){
                cVOs.addAll(sqldb.getColloquiImmobile(((ImmobiliVO)ivo).getCodImmobile(), null));
            }
        }
        if (exportType == EXPORT_TYPE_ANAGRAFICHE) {
            for (Object ivo : itemsToExport){
                cVOs.addAll(sqldb.getColloquiAnagrafica(((AnagraficheVO)ivo).getCodAnagrafica(), null));
            }
        }

        exportSelection(cVOs, ImportDataHelper.COLLOQUIANAGRAFICHE_FILENAME);

    }
	
	public void copyImmaginiFolder(DataBaseHelper sqldb, Context c){

        ArrayList<ImmagineVO> iVOs = new ArrayList<ImmagineVO>();

        if (exportType == EXPORT_TYPE_IMMOBILI) {
            for (Object ivo : itemsToExport){
                iVOs.addAll(sqldb.getImmaginiImmobile(((ImmobiliVO)ivo).getCodImmobile(), null));
            }
        }
        if (exportType == EXPORT_TYPE_ANAGRAFICHE) {
            for (Object ivo : itemsToExport){
                ArrayList<ImmobiliVO> alimms = ((AnagraficheVO)ivo).getImmobiliPropietari(c,null);
                for (ImmobiliVO iVO : alimms){
                    iVOs.addAll(sqldb.getImmaginiImmobile(iVO.getCodImmobile(), null));
                }
            }
        }

        String rootImgPath = Environment.getExternalStorageDirectory() + File.separator + "winkhouse/immagini";
        String rootImgExportPath = this.exportDirectory + File.separator + "immagini";

        for (ImmagineVO iVO : iVOs){

            File fexpFolder = new File(rootImgExportPath + File.separator + iVO.getCodImmobile().toString());
            if (!fexpFolder.exists()) {
                fexpFolder.mkdirs();
                fexpFolder = null;
            }
            try {
                copy(new File(rootImgPath + File.separator + iVO.getCodImmobile().toString() + File.separator + iVO.getCodImmagine()),
                     new File(rootImgExportPath + File.separator + iVO.getCodImmobile().toString() + File.separator + iVO.getCodImmagine()));
            }catch (Exception ex){}

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
		
		String[] items = folder.list();
		if (items != null){
			for (int i = 0; i < items.length; i++) {
			    File f = new File(folder.toPath() + File.separator +items[i]);
				if (f.isFile()){
					f.delete();
				}else if (f.listFiles().length == 0) {
				    f.delete();
                }else{
					deleteFolder(f);
				}
			}
		}
		folder.delete();
		
	}
	
	public void exportStanzeImmobiliToXML(DataBaseHelper sqldb, Context c){

        ArrayList<StanzeImmobiliVO> alst = new ArrayList<StanzeImmobiliVO>();
        if (exportType == EXPORT_TYPE_IMMOBILI){

            for (Object o : itemsToExport){
                alst.addAll(sqldb.getStanzeImmobile(null,((ImmobiliVO)o).getCodImmobile()));
            }

        }
        if (exportType == EXPORT_TYPE_ANAGRAFICHE){

            for (Object o : itemsToExport){

                ArrayList<ImmobiliVO> alivo = ((AnagraficheVO)o).getImmobiliPropietari(c, null);
                for (ImmobiliVO ivo : alivo){
                    alst.addAll(sqldb.getStanzeImmobile(null,ivo.getCodImmobile()));
                }

            }

        }

        exportSelection(alst, ImportDataHelper.STANZEIMMOBILI_FILENAME);

	} 
	
	public void exportEntityToXML(DataBaseHelper sqldb){

        exportSelection(new ArrayList<EntityVO>(), ImportDataHelper.ENTITA_FILENAME);

	} 

	public void exportAttributeToXML(DataBaseHelper sqldb){

        exportSelection(new ArrayList<AttributeVO>(), ImportDataHelper.ATTRIBUTI_FILENAME);

	} 

	public void exportAttributeValueToXML(DataBaseHelper sqldb){

        exportSelection(new ArrayList<AttributeValueVO>(), ImportDataHelper.VALOREATTRIBUTO_FILENAME);

	}
	
	public void exportImmobiliPropietariToXML(DataBaseHelper sqldb,Context c){
		
		ArrayList<ImmobiliPropietariVO> al_immobiliPropietari = new ArrayList<ImmobiliPropietariVO>();

		if (exportType == EXPORT_TYPE_IMMOBILI){

		    for (Object immobile : itemsToExport){

                ArrayList<AnagraficheVO> alavos = sqldb.getAnagrafichePropietarie(null, ((ImmobiliVO)immobile).getCodImmobile());

                for (AnagraficheVO anagraficheVO : alavos) {

                    ImmobiliPropietariVO ipvo = new ImmobiliPropietariVO();

                    ipvo.setCodImmobile(((ImmobiliVO)immobile).getCodImmobile());
                    ipvo.setCodAnagrafica(anagraficheVO.getCodAnagrafica());

                    al_immobiliPropietari.add(ipvo);

                }

            }
        }
        if (exportType == EXPORT_TYPE_ANAGRAFICHE){

            for (Object anagrafica : itemsToExport){

                ArrayList<ImmobiliVO> alavos = ((AnagraficheVO)anagrafica).getImmobiliPropietari(c, null);

                for (ImmobiliVO iVO : alavos) {

                    ImmobiliPropietariVO ipvo = new ImmobiliPropietariVO();

                    ipvo.setCodImmobile(iVO.getCodImmobile());
                    ipvo.setCodAnagrafica(((AnagraficheVO)anagrafica).getCodAnagrafica());

                    al_immobiliPropietari.add(ipvo);

                }

            }

        }

//		ArrayList<ImmobiliVO> alimvos = sqldb.getAllImmobili(null);
//
//		for (ImmobiliVO immobiliVO : alimvos) {
//
//			ArrayList<AnagraficheVO> alavos = immobiliVO.getAnagrafichePropietarie(c, null);
//
//			for (AnagraficheVO anagraficheVO : alavos) {
//
//				ImmobiliPropietariVO ipvo = new ImmobiliPropietariVO();
//
//				ipvo.setCodImmobile(immobiliVO.getCodImmobile());
//				ipvo.setCodAnagrafica(anagraficheVO.getCodAnagrafica());
//
//				al_immobiliPropietari.add(ipvo);
//
//			}
//
//		}
		
		exportSelection(al_immobiliPropietari, ImportDataHelper.IMMOBILIPROPIETARI_FILENAME);
		
	}	

	public String getImportDirectory() {
		return importDirectory;
	}

	public String getExportDirectory() {
		return exportDirectory;
	}
	

}
