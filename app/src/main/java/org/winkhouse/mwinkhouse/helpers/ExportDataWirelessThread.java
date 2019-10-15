package org.winkhouse.mwinkhouse.helpers;

import java.io.File;

import org.winkhouse.mwinkhouse.activity.ImportActivity.ThreadSincro;
import org.winkhouse.mwinkhouse.activity.StartUpActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ExportDataWirelessThread extends Thread {

	private StartUpActivity activity = null;
	private WirelessExportDataHelper exporthelper = null;
	private DataBaseHelper sqldb = null;
	private Context context = null;
	private ThreadSincro status = null;
	private ProgressBar pd_loader_up = null;
	private TextView msg_up = null;
	private Handler mHandler = null;

	private boolean upstatus = false;
	
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
	
	public ExportDataWirelessThread(Context context,ProgressBar pd_loader_up, TextView msg_up, ThreadSincro status) {
		this.context = context;
		this.pd_loader_up = pd_loader_up;
		this.pd_loader_up.setMax(20);
		this.msg_up = msg_up;
		this.status = status;
		this.mHandler = new Handler();		
		this.exporthelper = new WirelessExportDataHelper();
		this.exporthelper.deleteFolder(new File(this.exporthelper.getExportDirectory()));
		File f = new File(Environment.getExternalStorageDirectory() + File.separator + "winkhouse/winkhouseExport.zip");
		if (f.exists()){
			f.delete();
		}
		DataBaseHelper dbh = new DataBaseHelper(context,DataBaseHelper.NONE_DB);
		this.sqldb = dbh;
				
	}

	@Override
	public void run() {
				
		try{
			while(this.pd_loader_up.getProgress() <= this.pd_loader_up.getMax()){

                if (this.pd_loader_up.getProgress() == 0){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {
        					msg_up.setText("Esportazione classi energetiche");
        					pd_loader_up.setProgress(1);
        				}
        			});	                		                	
                	exporthelper.exportClasseEnergeticaToXML(this.sqldb);
                }
                if (this.pd_loader_up.getProgress() == 1){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {
        					msg_up.setText("Esportazione classi clienti");
        					pd_loader_up.setProgress(2);
        				}
        			});	                		                	
                	exporthelper.exportClassiClienteToXML(this.sqldb);
                }
                if (this.pd_loader_up.getProgress() == 2){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {
        					msg_up.setText("Esportazione riscaldamenti");
        					pd_loader_up.setProgress(3);
        				}
        			});	                		                	
                	exporthelper.exportRiscaldamentiToXML(this.sqldb);
                }
                if (this.pd_loader_up.getProgress() == 3){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {
        					msg_up.setText("Esportazione stato conservativo");
        					pd_loader_up.setProgress(4);
        				}
        			});	                		                	
                	exporthelper.exportStatoConservativoToXML(this.sqldb);
                }
                if (this.pd_loader_up.getProgress() == 4){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {
        					msg_up.setText("Esportazione tipologie contatti");
        					pd_loader_up.setProgress(5);
        				}
        			});	                		                	
                	exporthelper.exportTipologiaContattiToXML(this.sqldb);
                }
                if (this.pd_loader_up.getProgress() == 5){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {
        					msg_up.setText("Esportazione tipologie immobili");
        					pd_loader_up.setProgress(6);
        				}
        			});	                		                	
                	exporthelper.exportTipologieImmobiliToXML(sqldb);
                }
                if (this.pd_loader_up.getProgress() == 6){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {
        					msg_up.setText("Esportazione tipologie stanze");
        					pd_loader_up.setProgress(7);
        				}
        			});	                		                	
                	
                	exporthelper.exportTipologiaStanzeToXML(sqldb);
                }
                if (this.pd_loader_up.getProgress() == 7){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {
        					msg_up.setText("Esportazione immobili");
        					pd_loader_up.setProgress(8);
        				}
        			});	                		                	                	
                	exporthelper.exportImmobiliToXML(this.sqldb);
                }
                if (this.pd_loader_up.getProgress() == 8){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {
        					msg_up.setText("Esportazione anagrafiche");
        					pd_loader_up.setProgress(9);
        				}
        			});	                		                	
                	exporthelper.exportAnagraficheToXML(this.sqldb);
                }
                if (this.pd_loader_up.getProgress() == 9){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {
        					pd_loader_up.setProgress(10);
        				}
        			});	                		                	
                	
                	exporthelper.exportAgentiToXML(this.sqldb);
                }
                if (this.pd_loader_up.getProgress() == 10){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {        					
        					pd_loader_up.setProgress(11);
        				}
        			});	                		                	
                	
                	exporthelper.exportAbbinamentiToXML(this.sqldb);
                }
                if (this.pd_loader_up.getProgress() == 11){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {
        					msg_up.setText("Esportazione contatti");
        					pd_loader_up.setProgress(12);
        				}
        			});	                		                	
                	exporthelper.exportContattiToXML(sqldb);
                }
                if (this.pd_loader_up.getProgress() == 12){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {
        					pd_loader_up.setProgress(13);
        				}
        			});	                		                	
                	exporthelper.exportDatiCatastaliToXML(sqldb);
                }
                if (this.pd_loader_up.getProgress() == 13){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {
        					msg_up.setText("Esportazione immagini");
        					pd_loader_up.setProgress(14);
        				}
        			});	                		                	
                	exporthelper.exportImmaginiToXML(sqldb);
                	exporthelper.copyImmaginiFolder();
                }
                if (this.pd_loader_up.getProgress() == 14){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {
        					pd_loader_up.setProgress(15);
        				}
        			});	                		                	
                	
                	exporthelper.exportStanzeImmobiliToXML(sqldb);
                }
                if (this.pd_loader_up.getProgress() == 15){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {
        					pd_loader_up.setProgress(16);
        				}
        			});	                		                	
                	
                	exporthelper.exportEntityToXML(sqldb);
                }
                if (this.pd_loader_up.getProgress() == 16){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {
        					pd_loader_up.setProgress(17);
        				}
        			});	                		                	
                	
                	exporthelper.exportAttributeToXML(sqldb);
                }
                if (this.pd_loader_up.getProgress() == 17){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {
        					msg_up.setText("Esportazione classi energetiche");
        					pd_loader_up.setProgress(18);
        				}
        			});	                		                	
                	
                	exporthelper.exportAttributeValueToXML(sqldb);
                }
                if (this.pd_loader_up.getProgress() == 18){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {
        					msg_up.setText("Esportazione propietari immobili");
        					pd_loader_up.setProgress(19);
        				}
        			});	                		                	
                	
                	exporthelper.exportImmobiliPropietariToXML(sqldb,this.pd_loader_up.getContext());
                }
                if (this.pd_loader_up.getProgress() == 19){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {
        					msg_up.setText("Creazione archivio esportazione");
        					pd_loader_up.setProgress(20);
        				}
        			});	                		                	
                	
                	exporthelper.zipArchivio(msg_up,mHandler);
                }
                
                Thread.sleep(500);
                
                if(this.pd_loader_up.getProgress() == this.pd_loader_up.getMax()){
                	status.setStatus(true);                	
                	break;
                	
                }
            }
			sqldb.close();
			
        }catch(Exception e){        	
			sqldb.close();
        	this.mHandler.post(new Runnable() {
				
				@Override
				public void run() {
					msg_up.setText("Errore creazione archivio");
					pd_loader_up.setProgress(0);
					status.setStatus(false);    
				}
			});	                		                	
			
			        	
        }
        

	}

}
