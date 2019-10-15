package org.winkhouse.mwinkhouse.helpers;

import java.io.File;

import org.winkhouse.mwinkhouse.activity.StartUpActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

public class ExportDataThread extends Thread {

	private ProgressDialog pd_loader = null;
	private ExportDatiHandler handle = null;
	private StartUpActivity activity = null;
	private ExportDataHelper exporthelper = null;
	private DataBaseHelper sqldb = null;
	
	private class ExportDatiHandler extends Handler{
		
		private ProgressDialog pd_loader = null;
		
		public ExportDatiHandler(ProgressDialog pd_loader){
			this.pd_loader = pd_loader;			
		}
		
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
      	   	pd_loader.incrementProgressBy(1);
		}
		
	} 
	
	public ExportDataThread(ProgressDialog pd_loader,Context context) {
		
		this.pd_loader = pd_loader; 
		this.handle = new ExportDatiHandler(this.pd_loader);
		this.exporthelper = new ExportDataHelper();
		this.exporthelper.deleteFolder(new File(this.exporthelper.getExportDirectory()));
		DataBaseHelper dbh = new DataBaseHelper(context,DataBaseHelper.NONE_DB);
		this.sqldb = dbh;		
	}

	@Override
	public void run() {
		
		try{
			while(this.pd_loader.getProgress() <= this.pd_loader.getMax()){

                if (this.pd_loader.getProgress() == 0){
                	this.pd_loader.setMessage("Esportazione classi energetiche");
                	exporthelper.exportClasseEnergeticaToXML(this.sqldb);
                }
                if (this.pd_loader.getProgress() == 1){
                	this.pd_loader.setMessage("Esportazione classi cliente");
                	exporthelper.exportClassiClienteToXML(this.sqldb);
                }
                if (this.pd_loader.getProgress() == 2){
                	this.pd_loader.setMessage("Esportazione riscaldamenti");
                	exporthelper.exportRiscaldamentiToXML(this.sqldb);
                }
                if (this.pd_loader.getProgress() == 3){
                	this.pd_loader.setMessage("Esportazione stato conservativo");
                	exporthelper.exportStatoConservativoToXML(this.sqldb);
                }
                if (this.pd_loader.getProgress() == 4){
                	this.pd_loader.setMessage("Esportazione tipologie contatti");
                	exporthelper.exportTipologiaContattiToXML(this.sqldb);
                }
                if (this.pd_loader.getProgress() == 5){
                	this.pd_loader.setMessage("Esportazione tipologie immobili");
                	exporthelper.exportTipologieImmobiliToXML(sqldb);
                }
                if (this.pd_loader.getProgress() == 6){
                	exporthelper.exportTipologiaStanzeToXML(sqldb);
                }
                if (this.pd_loader.getProgress() == 7){
                	this.pd_loader.setMessage("Esportazione immobili");
                	exporthelper.exportImmobiliToXML(this.sqldb);
                }
                if (this.pd_loader.getProgress() == 8){                
                	this.pd_loader.setMessage("Esportazione anagrafiche");
                	exporthelper.exportAnagraficheToXML(this.sqldb);
                }
                if (this.pd_loader.getProgress() == 9){
                	exporthelper.exportAgentiToXML(this.sqldb);
                }
                if (this.pd_loader.getProgress() == 10){
                	exporthelper.exportAbbinamentiToXML(this.sqldb);
                }
                if (this.pd_loader.getProgress() == 11){
                	this.pd_loader.setMessage("Esportazione contatti");
                	exporthelper.exportContattiToXML(sqldb);
                }
                if (this.pd_loader.getProgress() == 12){
                	exporthelper.exportDatiCatastaliToXML(sqldb);
                }
                if (this.pd_loader.getProgress() == 13){
                	this.pd_loader.setMessage("Esportazione immagini");
                	exporthelper.exportImmaginiToXML(sqldb);
                	exporthelper.copyImmaginiFolder();
                }
                if (this.pd_loader.getProgress() == 14){
                	exporthelper.exportStanzeImmobiliToXML(sqldb);
                }
                if (this.pd_loader.getProgress() == 15){
                	exporthelper.exportEntityToXML(sqldb);
                }
                if (this.pd_loader.getProgress() == 16){
                	exporthelper.exportAttributeToXML(sqldb);
                }
                if (this.pd_loader.getProgress() == 17){
                	exporthelper.exportAttributeValueToXML(sqldb);
                }
                if (this.pd_loader.getProgress() == 18){
                	this.pd_loader.setMessage("Esportazione propietari immobili");
                	exporthelper.exportImmobiliPropietariToXML(sqldb,this.pd_loader.getContext());
                }

                Thread.sleep(1000);
                handle.sendMessage(handle.obtainMessage());
                
                if(this.pd_loader.getProgress() == this.pd_loader.getMax()){
                	this.pd_loader.dismiss();
                	break;
                	
                }
            }
			sqldb.close();
        }catch(Exception e){}
        

	}

}
