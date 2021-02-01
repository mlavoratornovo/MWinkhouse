package org.winkhouse.mwinkhouse.helpers;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

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

	private final StartUpActivity activity = null;
	private WirelessExportDataHelper exporthelper = null;
	private DataBaseHelper sqldb = null;
	private Context context = null;
	private ThreadSincro status = null;
	private ProgressBar pd_loader_up = null;
	private TextView msg_up = null;
	private Handler mHandler = null;
    private final ArrayList itemsToExport = null;
	private final boolean upstatus = false;
	
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
	
	public ExportDataWirelessThread(Context context, ProgressBar pd_loader_up, TextView msg_up, ThreadSincro status, ArrayList itemsToExport, String path2Export) throws Exception{
		this.context = context;
		this.pd_loader_up = pd_loader_up;
		this.pd_loader_up.setMax(20);
		this.msg_up = msg_up;
		this.status = status;
		this.mHandler = new Handler();		
		this.exporthelper = new WirelessExportDataHelper(itemsToExport);
		this.exporthelper.setExportDirectory(path2Export+File.separator+"tmp");

		File fexporttmp = new File(this.exporthelper.getExportDirectory());
		if (fexporttmp.exists()){
            this.exporthelper.deleteFolder(fexporttmp);
        }
		fexporttmp.mkdirs();
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
                	exporthelper.exportImmobiliToXML(this.sqldb, context);
                }
                if (this.pd_loader_up.getProgress() == 8){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {
        					msg_up.setText("Esportazione anagrafiche");
        					pd_loader_up.setProgress(9);
        				}
        			});	                		                	
                	exporthelper.exportAnagraficheToXML(this.sqldb, context);
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
                	exporthelper.exportContattiToXML(sqldb, context);
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
                	exporthelper.exportImmaginiToXML(sqldb,context);
                	exporthelper.copyImmaginiFolder(sqldb,context);
                }
                if (this.pd_loader_up.getProgress() == 14){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {
        					pd_loader_up.setProgress(15);
        				}
        			});	                		                	
                	
                	exporthelper.exportStanzeImmobiliToXML(sqldb, context);
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
                	
                	exporthelper.zipArchivio(context,
                                             msg_up,
                                             mHandler,
                               exporthelper.getExportDirectory(),
                            exporthelper.getExportDirectory().replace("/tmp","") + File.separator + "winkhouseExport" + new Long(new Date().getTime()).toString() + ".zip");

                	exporthelper.deleteFolder(new File(exporthelper.getExportDirectory()));
					this.mHandler.post(new Runnable() {

						@Override
						public void run() {
							msg_up.setText("Esportazione completata");
							pd_loader_up.setProgress(20);
						}
					});

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
