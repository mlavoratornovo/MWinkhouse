package org.winkhouse.mwinkhouse.helpers;

import java.io.File;
import java.util.ArrayList;

import org.winkhouse.mwinkhouse.activity.ImportActivity.ThreadSincro;
import org.winkhouse.mwinkhouse.activity.StartUpActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ImportDataWirelessThread extends Thread {
	
	
	private Context context = null;
	private StartUpActivity activity = null;
	private WirelessImportDataHelper importhelper = null;
	private SQLiteDatabase sqldb = null;
	private DataBaseHelper dbh = null; 
	private ThreadSincro status = null;	
	private ProgressBar pd_loader_down = null;
	private TextView msg_down = null;
	private Handler mHandler = null;
	private long count = 1;

	
	public ImportDataWirelessThread(Context context,ProgressBar pd_loader_down, TextView msg_down, ThreadSincro status) {
				
		this.pd_loader_down = pd_loader_down;
		this.pd_loader_down.setMax(23);
		this.pd_loader_down.setProgress(0);
		this.msg_down = msg_down;
		this.status = status;
		this.mHandler = new Handler();
		this.importhelper = new WirelessImportDataHelper(null);

		if (this.importhelper.getDataUpdateMode().equalsIgnoreCase(WirelessImportDataHelper.UPDATE_METHOD_SOVRASCRIVI)){
			this.importhelper.deleteImportDirContent(new File(this.importhelper.getImportDirectory()),new ArrayList<String>());
		}else{
			this.importhelper.deleteImportDirContent(new File(this.importhelper.getImportDirectory()),new ArrayList<String>(){{add("immagini");}});
		}

		this.dbh = new DataBaseHelper(context,DataBaseHelper.NONE_DB);
		this.sqldb = dbh.getWritableDatabase();
		

	}	

	@Override
	public void run() {
		
		while(!status.getStatus()){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
		} 	
		
		try{
			while(this.pd_loader_down.getProgress() < this.pd_loader_down.getMax()){

                if (this.pd_loader_down.getProgress() == 0){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {
        					msg_down.setText("Decompressione file scaricato");
        					pd_loader_down.setProgress(1);
        				}
        			});	                		                	
                	importhelper.unZipArchivioWireless(msg_down,this.mHandler,status.getFilename());
                }				
                if (this.pd_loader_down.getProgress() == 1){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {
        					msg_down.setText("Importazione classi energetiche");
        					pd_loader_down.setProgress(2);
        				}
        			});	                		                	
                	importhelper.importClasseEnergeticaToDB(this.sqldb);
                }
                if (this.pd_loader_down.getProgress() == 2){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {
        					msg_down.setText("Importazione classi cliente");
        					pd_loader_down.setProgress(3);
        				}
        			});	                		                	
                	
                	importhelper.importClassiClienteToDB(this.sqldb);
                }
                if (this.pd_loader_down.getProgress() == 3){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {
        					msg_down.setText("Importazione riscaldamenti");
        					pd_loader_down.setProgress(4);
        				}
        			});	                		                	
                	
                	importhelper.importRiscaldamentiToDB(this.sqldb);
                }
                if (this.pd_loader_down.getProgress() == 4){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {
        					msg_down.setText("Importazione stato conservativo");
        					pd_loader_down.setProgress(5);
        				}
        			});	                		                	
                	
                	importhelper.importStatoConservativoToDB(this.sqldb);
                }
                if (this.pd_loader_down.getProgress() == 5){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {
        					msg_down.setText("Importazione tipologie contatti");
        					pd_loader_down.setProgress(6);
        				}
        			});	                		                	
                	
                	importhelper.importTipologiaContattiToDB(this.sqldb);
                }
                if (this.pd_loader_down.getProgress() == 6){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {
        					msg_down.setText("Importazione tipologie immobili");
        					pd_loader_down.setProgress(7);
        				}
        			});	                		                	
                	
                	importhelper.importTipologieImmobiliToDB(sqldb);
                }
                if (this.pd_loader_down.getProgress() == 7){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {

        					pd_loader_down.setProgress(8);
        				}
        			});	                		                	
                	

                	importhelper.importTipologiaStanzeToDB(sqldb);
                }
                if (this.pd_loader_down.getProgress() == 8){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {
        					msg_down.setText("Importazione immobili");
        					pd_loader_down.setProgress(9);
        				}
        			});	                		                	
                	
                	importhelper.importImmobiliToDB(this.sqldb);
                }
                if (this.pd_loader_down.getProgress() == 9){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {
        					msg_down.setText("Importazione anagrafiche");
        					pd_loader_down.setProgress(10);
        				}
        			});	                		                	
                	
                	importhelper.importAnagraficheToDB(this.sqldb);
                }
                if (this.pd_loader_down.getProgress() == 10){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {
        			
        					pd_loader_down.setProgress(11);
        				}
        			});	                		                	
                	
                	importhelper.importAgentiToDB(this.sqldb);
                }
                if (this.pd_loader_down.getProgress() == 11){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {
        					pd_loader_down.setProgress(12);
        				}
        			});	                		                	

                	importhelper.importAbbinamentiToDB(this.sqldb);
                }
                if (this.pd_loader_down.getProgress() == 12){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {
        					msg_down.setText("Importazione contatti");
        					pd_loader_down.setProgress(13);
        				}
        			});	                		                	
                	
                	importhelper.importContattiToDB(sqldb);
                }
                if (this.pd_loader_down.getProgress() == 13){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {
        					pd_loader_down.setProgress(14);
        				}
        			});	                		                	
                	
                	importhelper.importDatiCatastaliToDB(sqldb);
                }
                if (this.pd_loader_down.getProgress() == 14){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {
        					msg_down.setText("Importazione immagini");
        					pd_loader_down.setProgress(15);
        				}
        			});	                		                	
                	
                	importhelper.importImmaginiToDB(sqldb);
                }
                if (this.pd_loader_down.getProgress() == 15){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {
        					pd_loader_down.setProgress(16);
        				}
        			});	                		                	
                	
                	importhelper.importStanzeImmobiliToDB(sqldb);
                }
                if (this.pd_loader_down.getProgress() == 16){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {        					
        					pd_loader_down.setProgress(17);
        				}
        			});	                		                	
                	
                	importhelper.importEntityToDB(sqldb);
                }
                if (this.pd_loader_down.getProgress() == 17){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {	        					
        					pd_loader_down.setProgress(18);
        				}
        			});	                		                	
                	
                	importhelper.importAttributeToDB(sqldb);
                }
                if (this.pd_loader_down.getProgress() == 18){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {	        					
        					pd_loader_down.setProgress(19);	        					
        				}
        			});	                		                	
                	
                	importhelper.importAttributeValueToDB(sqldb);
                }
                if (this.pd_loader_down.getProgress() == 19){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {
        					msg_down.setText("Importazione propietari immobili");
        					pd_loader_down.setProgress(20);	        					
        				}
        			});	                		                	
                	
                	importhelper.importImmobiliPropietariToDB(sqldb);
                }
                
                if (this.pd_loader_down.getProgress() == 20){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {
        					msg_down.setText("Importazione colloqui");
        					pd_loader_down.setProgress(21);	        					
        				}
        			});	                		                	
                	
                	importhelper.importColloquiToDB(sqldb);
                }

                if (this.pd_loader_down.getProgress() == 21){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {
        					msg_down.setText("Importazione colloqui");
        					pd_loader_down.setProgress(22);	        					
        				}
        			});	                		                	
                	
                	importhelper.importColloquiAnagraficheToDB(sqldb);
                }

                if (this.pd_loader_down.getProgress() == 22){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {
        					msg_down.setText("Importazione colloqui agenti");
        					pd_loader_down.setProgress(23);	        					
        				}
        			});	                		                	
                	
                	importhelper.importAgentiToDB(sqldb);
                }

                Thread.sleep(500);
                
                if(this.pd_loader_down.getProgress() == this.pd_loader_down.getMax()){
                	this.mHandler.post(new Runnable() {
        				
        				@Override
        				public void run() {
        					msg_down.setText("Importazione terminata con successo");
        					pd_loader_down.setProgress(0);
        				}
        			});	                		                	
                	
                	status.setStatus(false);	                	
                	break;
                	
                }
            }
			sqldb.close();
			dbh.close();
			status = null;
        }catch(Exception e){
        	Log.e("Error", e.getMessage());
        }
		
		
	}
	

}
