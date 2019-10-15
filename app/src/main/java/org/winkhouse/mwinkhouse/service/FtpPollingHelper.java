package org.winkhouse.mwinkhouse.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.winkhouse.mwinkhouse.activity.immobili.RicercaImmobiliActivity;
import org.winkhouse.mwinkhouse.helpers.DataBaseHelper;
import org.winkhouse.mwinkhouse.helpers.ExportSearchParamsHelper;
import org.winkhouse.mwinkhouse.helpers.WirelessImportDataHelper;
import org.winkhouse.mwinkhouse.util.NotificationHelper;
import org.winkhouse.mwinkhouse.util.SDFileSystemUtils;
import org.winkhouse.mwinkhouse.util.SysSettingNames;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.io.IOException;
import java.util.ArrayList;

public class FtpPollingHelper {

    private Context context = null;
    private int randomNo = 0;
    private String msg_criteri = null;

	public FtpPollingHelper(Context context,int randomNo,String msg_criteri) {

		this.context        = context;
        this.randomNo       = randomNo;
        this.msg_criteri    = msg_criteri;

	}
	
	private class SearchResponseFilter implements FTPFileFilter {
		
  		private String responseFileName = null;
  		
  		public SearchResponseFilter(String responseFileName){
  			this.responseFileName = responseFileName;
  		}
  		
  		@Override
  		public boolean accept(FTPFile arg0) {

            return arg0.isFile() && arg0.getName().equalsIgnoreCase(responseFileName);
        }
  	}
	
	public boolean uploadSearch(String filePathName){
		
		boolean returnValue = true;
		
  		FTPClient con = null;
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
          try
          {
              con = new FTPClient();
              con.connect(sharedPref.getString(SysSettingNames.FTP_URL,null),Integer.valueOf(sharedPref.getString(SysSettingNames.FTP_PORT, "21")));
  
              if (con.login(sharedPref.getString(SysSettingNames.FTP_USERNAME,null), sharedPref.getString(SysSettingNames.FTP_PASSWORD,null)))
              {
                  con.enterLocalPassiveMode();
                  con.setFileType(FTP.BINARY_FILE_TYPE);
                  
                  File up = new File(filePathName);
                  FileInputStream in = new FileInputStream(up);

                  String ftppath = "";
                  if (!sharedPref.getString(SysSettingNames.FTP_PATH,"").equalsIgnoreCase("")){
                      ftppath = File.separator +
                                sharedPref.getString(SysSettingNames.FTP_PATH,"") +
                                File.separator;
                  }

                  boolean result = con.storeFile(ftppath +
                                                 filePathName.substring(filePathName.lastIndexOf(File.separator)+1), in);
                  
                  in.close();
                  if (result) Log.v("upload result", "succeeded");
                  con.logout();
                  con.disconnect();
              }else{
              	returnValue = false;
              }
          }
          catch (Exception e)
          {
              e.printStackTrace();
              returnValue = false;
          }
  
		
		return returnValue;
		
	}
		
	public boolean pollingResult(String queryFileName){

		boolean result = false;
		
  		FTPClient cli = null;
  		String responseFileName = queryFileName.replace("_Q_", "_R_");
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

        try
          {
              cli = new FTPClient();

              cli.connect(sharedPref.getString(SysSettingNames.FTP_URL,null),Integer.valueOf(sharedPref.getString(SysSettingNames.FTP_PORT, "21")));
  
              if (cli.login(sharedPref.getString(SysSettingNames.FTP_USERNAME,null), sharedPref.getString(SysSettingNames.FTP_PASSWORD,null)))
              {
                  cli.enterLocalPassiveMode();
                  cli.setFileType(FTP.BINARY_FILE_TYPE);
                  FTPFile[] risposta = cli.listFiles(File.separator + sharedPref.getString(SysSettingNames.FTP_PATH,"") + File.separator,
                                                     new SearchResponseFilter(responseFileName));
                  
                  if (risposta.length > 0){

                      ExportSearchParamsHelper esph = new ExportSearchParamsHelper();
                      File download = new File(esph.getCloudSearchDirectory()+File.separator+responseFileName);

                      OutputStream outputStream2 = new BufferedOutputStream(new FileOutputStream(download));

                      if (cli.retrieveFile(risposta[0].getName(), outputStream2)){

                          NotificationHelper.getInstance().doNotificationBar(this.context,
                                                                             "immobili",
                                                                             "Ricerca cloud immobili",
                                                                             "File risultati scaricato " + this.msg_criteri,
                                                                             this.randomNo);

                          outputStream2.flush();
                          outputStream2.close();

                          cli.deleteFile(File.separator +
                                         sharedPref.getString(SysSettingNames.FTP_PATH,null) +
                                         File.separator +risposta[0].getName());

                          if (download.length() > 0) {

                              String[] rfnArr = responseFileName.split("_");
                              String extraFolderName = "WFTPR_" + rfnArr[2];

                              WirelessImportDataHelper widh = new WirelessImportDataHelper(extraFolderName);
                              widh.setContext(this.context);

                              File destination = new File(Environment.getExternalStorageDirectory() +
                                                          File.separator +
                                                          "winkhouse" +
                                                          File.separator +
                                                          responseFileName);

                              if (widh.getDataUpdateMode().equalsIgnoreCase(WirelessImportDataHelper.UPDATE_METHOD_SOVRASCRIVI)) {
                                  widh.deleteImportDirContent(new File(widh.getImportDirectory()),
                                                              new ArrayList<String>());
                              } else {
                                  widh.deleteImportDirContent(new File(widh.getImportDirectory()),
                                                              new ArrayList<String>() {{add("immagini");}});
                              }

                              SDFileSystemUtils sdfsu = new SDFileSystemUtils();

                              if (sdfsu.copyFile(download, destination)) {

                                  if (widh.unZipArchivioWireless(null, null, responseFileName)) {

                                      DataBaseHelper dbh = new DataBaseHelper(context, DataBaseHelper.NONE_DB);
                                      SQLiteDatabase sqldb = dbh.getWritableDatabase();

                                      widh.importAgentiToDB(sqldb);
                                      widh.importClasseEnergeticaToDB(sqldb);
                                      widh.importClassiClienteToDB(sqldb);
                                      widh.importRiscaldamentiToDB(sqldb);
                                      widh.importStatoConservativoToDB(sqldb);
                                      widh.importTipologiaContattiToDB(sqldb);
                                      widh.importTipologieImmobiliToDB(sqldb);
                                      widh.importTipologiaStanzeToDB(sqldb);
                                      widh.importAgentiToDB(sqldb);
                                      widh.importAnagraficheToDB(sqldb);
                                      widh.importImmobiliToDB(sqldb);
                                      widh.importAbbinamentiToDB(sqldb);
                                      widh.importContattiToDB(sqldb);
                                      widh.importDatiCatastaliToDB(sqldb);
                                      widh.importImmaginiToDB(sqldb);
                                      widh.importStanzeImmobiliToDB(sqldb);
                                      widh.importEntityToDB(sqldb);
                                      widh.importAttributeToDB(sqldb);
                                      widh.importImmobiliPropietariToDB(sqldb);
                                      widh.importColloquiToDB(sqldb);
                                      widh.importColloquiAnagraficheToDB(sqldb);

                                      sqldb.close();
                                      dbh.close();
                                      ArrayList itemNotToDel = new ArrayList<String>();
//                                      itemNotToDel.add("immagini");
                                      widh.deleteImportDirContent(new File(widh.getImportDirectory()), itemNotToDel);
                                      destination.delete();
                                      download.delete();
                                      result = true;

                                      NotificationHelper.getInstance()
                                                        .doNotificationBar(this.context,
                                                                           "immobili",
                                                                           "Ricerca cloud immobili",
                                                                           "Dati importati " + this.msg_criteri,
                                                                           this.randomNo);


                                  } else {
                                      result = true;
                                  }

                              } else {
                                  result = true;
                              }
                          }else{
                              NotificationHelper.getInstance()
                                                .doNotificationBar(this.context,
                                                                   "immobili",
                                                                   "Ricerca cloud immobili",
                                                                   "Nessun risultato " + this.msg_criteri,
                                                                   this.randomNo);
                              result = true;

                          }
                      }else{
                          result = false;
                      }
                  	
                  }else{
                      result = false;
                  }
              }
          }
          catch (Exception e)
          {
              File destination = new File(Environment.getExternalStorageDirectory() + File.separator + "winkhouse" + File.separator + responseFileName);
              if (destination.exists()){
                  destination.delete();
              }
              ExportSearchParamsHelper esph = new ExportSearchParamsHelper();
              File download = new File(esph.getCloudSearchDirectory()+File.separator+responseFileName);
              if (download.exists()){
                  download.delete();
              }


              e.printStackTrace();
          }
        
        return result;
          
		
	}
	
}
