package org.winkhouse.mwinkhouse.helpers;

import org.winkhouse.mwinkhouse.activity.StartUpActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.widget.EditText;
import android.widget.ProgressBar;

public class ImportDataThread extends Thread {

	private ProgressBar pd_loader = null;
	private ImportDatiHandler handle = null;
	private final StartUpActivity activity = null;
	private ImportDataHelper importhelper = null;
	private SQLiteDatabase sqldb = null;
    private EditText message = null;

	private class ImportDatiHandler extends Handler{
		
		private ProgressBar pd_loader = null;
		
		public ImportDatiHandler(ProgressBar pd_loader){
			this.pd_loader = pd_loader;			
		}
		
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
      	   	pd_loader.incrementProgressBy(1);
		}
		
	} 
	
	public ImportDataThread(ProgressBar pd_loader, EditText message, Context context) {
		
		this.pd_loader = pd_loader;
		this.message = message;
		this.handle = new ImportDatiHandler(this.pd_loader);
		this.importhelper = new ImportDataHelper(null, false);
		DataBaseHelper dbh = new DataBaseHelper(context,DataBaseHelper.NONE_DB);
		this.sqldb = dbh.getWritableDatabase();
	}

	@Override
	public void run() {
		
		try{
			while(this.pd_loader.getProgress() <= this.pd_loader.getMax()){

                if (this.pd_loader.getProgress() == 0){
                	this.message.setText("Importazione classi energetiche");
                	importhelper.importClasseEnergeticaToDB(this.sqldb);
                }
                if (this.pd_loader.getProgress() == 1){
                	this.message.setText("Importazione classi cliente");
                	importhelper.importClassiClienteToDB(this.sqldb);
                }
                if (this.pd_loader.getProgress() == 2){
                	this.message.setText("Importazione riscaldamenti");
                	importhelper.importRiscaldamentiToDB(this.sqldb);
                }
                if (this.pd_loader.getProgress() == 3){
                	this.message.setText("Importazione stato conservativo");
                	importhelper.importStatoConservativoToDB(this.sqldb);
                }
                if (this.pd_loader.getProgress() == 4){
                	this.message.setText("Importazione tipologie contatti");
                	importhelper.importTipologiaContattiToDB(this.sqldb);
                }
                if (this.pd_loader.getProgress() == 5){
                	this.message.setText("Importazione tipologie immobili");
                	importhelper.importTipologieImmobiliToDB(sqldb);
                }
                if (this.pd_loader.getProgress() == 6){
                	importhelper.importTipologiaStanzeToDB(sqldb);
                }
                if (this.pd_loader.getProgress() == 7){
                	this.message.setText("Importazione immobili");
                	importhelper.importImmobiliToDB(this.sqldb);
                }
                if (this.pd_loader.getProgress() == 8){              
                	this.message.setText("Importazione anagrafiche");
                	importhelper.importAnagraficheToDB(this.sqldb);
                }
                if (this.pd_loader.getProgress() == 9){
                	importhelper.importAgentiToDB(this.sqldb);
                }
                if (this.pd_loader.getProgress() == 10){
                	importhelper.importAbbinamentiToDB(this.sqldb);
                }
                if (this.pd_loader.getProgress() == 11){
                	this.message.setText("Importazione contatti");
                	importhelper.importContattiToDB(sqldb);
                }
                if (this.pd_loader.getProgress() == 12){
                	importhelper.importDatiCatastaliToDB(sqldb);
                }
                if (this.pd_loader.getProgress() == 13){
                	this.message.setText("Importazione immagini");
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
                	this.message.setText("Importazione propietari immobili");
                	importhelper.importImmobiliPropietariToDB(sqldb);
                }
                
                Thread.sleep(1000);
                handle.sendMessage(handle.obtainMessage());
                
                if(this.pd_loader.getProgress() == this.pd_loader.getMax()){
                	break;
                	
                }
            }
			sqldb.close();
        }catch(Exception e){}
        

	}

}
