package org.winkhouse.mwinkhouse.helpers;

import org.winkhouse.mwinkhouse.activity.StartUpActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;

public class ImportDataThread extends Thread {

	private ProgressDialog pd_loader = null;
	private ImportDatiHandler handle = null;
	private StartUpActivity activity = null;
	private ImportDataHelper importhelper = null;
	private SQLiteDatabase sqldb = null;
	
	private class ImportDatiHandler extends Handler{
		
		private ProgressDialog pd_loader = null;
		
		public ImportDatiHandler(ProgressDialog pd_loader){
			this.pd_loader = pd_loader;			
		}
		
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
      	   	pd_loader.incrementProgressBy(1);
		}
		
	} 
	
	public ImportDataThread(ProgressDialog pd_loader,Context context) {
		
		this.pd_loader = pd_loader; 
		this.handle = new ImportDatiHandler(this.pd_loader);
		this.importhelper = new ImportDataHelper(null);
		DataBaseHelper dbh = new DataBaseHelper(context,DataBaseHelper.NONE_DB);
		this.sqldb = dbh.getWritableDatabase();
	}

	@Override
	public void run() {
		
		try{
			while(this.pd_loader.getProgress() <= this.pd_loader.getMax()){

                if (this.pd_loader.getProgress() == 0){
                	this.pd_loader.setMessage("Importazione classi energetiche");
                	importhelper.importClasseEnergeticaToDB(this.sqldb);
                }
                if (this.pd_loader.getProgress() == 1){
                	this.pd_loader.setMessage("Importazione classi cliente");
                	importhelper.importClassiClienteToDB(this.sqldb);
                }
                if (this.pd_loader.getProgress() == 2){
                	this.pd_loader.setMessage("Importazione riscaldamenti");
                	importhelper.importRiscaldamentiToDB(this.sqldb);
                }
                if (this.pd_loader.getProgress() == 3){
                	this.pd_loader.setMessage("Importazione stato conservativo");
                	importhelper.importStatoConservativoToDB(this.sqldb);
                }
                if (this.pd_loader.getProgress() == 4){
                	this.pd_loader.setMessage("Importazione tipologie contatti");
                	importhelper.importTipologiaContattiToDB(this.sqldb);
                }
                if (this.pd_loader.getProgress() == 5){
                	this.pd_loader.setMessage("Importazione tipologie immobili");
                	importhelper.importTipologieImmobiliToDB(sqldb);
                }
                if (this.pd_loader.getProgress() == 6){
                	importhelper.importTipologiaStanzeToDB(sqldb);
                }
                if (this.pd_loader.getProgress() == 7){
                	this.pd_loader.setMessage("Importazione immobili");
                	importhelper.importImmobiliToDB(this.sqldb);
                }
                if (this.pd_loader.getProgress() == 8){              
                	this.pd_loader.setMessage("Importazione anagrafiche");
                	importhelper.importAnagraficheToDB(this.sqldb);
                }
                if (this.pd_loader.getProgress() == 9){
                	importhelper.importAgentiToDB(this.sqldb);
                }
                if (this.pd_loader.getProgress() == 10){
                	importhelper.importAbbinamentiToDB(this.sqldb);
                }
                if (this.pd_loader.getProgress() == 11){
                	this.pd_loader.setMessage("Importazione contatti");
                	importhelper.importContattiToDB(sqldb);
                }
                if (this.pd_loader.getProgress() == 12){
                	importhelper.importDatiCatastaliToDB(sqldb);
                }
                if (this.pd_loader.getProgress() == 13){
                	this.pd_loader.setMessage("Importazione immagini");
                	importhelper.importImmaginiToDB(sqldb);
                }
                if (this.pd_loader.getProgress() == 14){
                	importhelper.importStanzeImmobiliToDB(sqldb);
                }
                if (this.pd_loader.getProgress() == 15){
                	importhelper.importEntityToDB(sqldb);
                }
                if (this.pd_loader.getProgress() == 16){
                	importhelper.importAttributeToDB(sqldb);
                }
                if (this.pd_loader.getProgress() == 17){
                	importhelper.importAttributeValueToDB(sqldb);
                }
                if (this.pd_loader.getProgress() == 18){
                	this.pd_loader.setMessage("Importazione propietari immobili");
                	importhelper.importImmobiliPropietariToDB(sqldb);
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
