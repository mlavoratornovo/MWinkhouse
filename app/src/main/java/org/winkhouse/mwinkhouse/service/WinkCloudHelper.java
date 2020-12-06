package org.winkhouse.mwinkhouse.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.preference.PreferenceManager;

import org.winkhouse.mwinkhouse.helpers.DataBaseHelper;
import org.winkhouse.mwinkhouse.helpers.ExportSearchParamsHelper;
import org.winkhouse.mwinkhouse.helpers.WirelessImportDataHelper;
import org.winkhouse.mwinkhouse.util.NotificationHelper;
import org.winkhouse.mwinkhouse.util.SDFileSystemUtils;
import org.winkhouse.mwinkhouse.util.SysSettingNames;
import org.winkhouse.mwinkhouse.models.restmsgs.ResponseByCodeQueryName;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by michele on 04/02/19.
 */

public class WinkCloudHelper {

    private Context context = null;
    private int randomNo = 0;
    private String msg_criteri = null;

    public WinkCloudHelper(Context context,int randomNo,String msg_criteri) {

        this.context        = context;
        this.randomNo       = randomNo;
        this.msg_criteri    = msg_criteri;

    }

    public boolean pollingResult(String queryFileName){

        boolean result = false;

        HTTPHelper httpHelper = new HTTPHelper();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.context);
        String code = sharedPref.getString(SysSettingNames.WINKCLOUDID, null);
        String downfilename = null;

        try {
            ResponseByCodeQueryName[] queryResponseData = httpHelper.getQueryResposesByCodeQueryName(this.context,
                                                                                                     code,
                                                                                                     queryFileName);
            if (queryResponseData != null) {

                for (int i = 0; i < queryResponseData.length; i++) {

                    downfilename = queryResponseData[i].filename;
                    ExportSearchParamsHelper esph = new ExportSearchParamsHelper(null);
                    File download = new File(esph.getCloudSearchDirectory() + File.separator + downfilename);

                    if (httpHelper.downloadResponse(this.context, code, queryResponseData[i].idfile, download)) {

                        NotificationHelper.getInstance().doNotificationBar(this.context,
                                "immobili",
                                "Ricerca cloud immobili",
                                "File risultati scaricato " + this.msg_criteri,
                                this.randomNo);

                        String unzipFolder = esph.getCloudSearchDirectory() + File.separator + downfilename.replace(".zip", "");
                        File fUnzipFolder = new File(unzipFolder);

                        WirelessImportDataHelper widh = new WirelessImportDataHelper(unzipFolder, true);
                        widh.setContext(this.context);

                        String destpath = Environment.getExternalStorageDirectory() +
                                File.separator +
                                "winkhouse" +
                                File.separator +
                                "searchC" +
                                File.separator +
                                downfilename;
                        File destination = new File(destpath);

                        if (widh.getDataUpdateMode().equalsIgnoreCase(WirelessImportDataHelper.UPDATE_METHOD_SOVRASCRIVI)) {
                            widh.deleteImportDirContent(new File(widh.getImportDirectory()),
                                    new ArrayList<String>());
                        } else {
                            widh.deleteImportDirContent(new File(widh.getImportDirectory()),
                                    new ArrayList<String>() {{
                                        add("immagini");
                                    }});
                        }

                        SDFileSystemUtils sdfsu = new SDFileSystemUtils();

//                        if (sdfsu.copyFile(download, destination)) {

                            if (widh.unZipArchivioWireless(this.context, null, null, destpath)) {

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
/*
                        } else {
                            result = true;
                        }
*/
                    }
                }

                httpHelper.deleteByQueryFileName(this.context, code, queryFileName);

            } else {
                result = false;
            }

        }catch (Exception e){

            if (downfilename != null) {

                File destination = new File(Environment.getExternalStorageDirectory() + File.separator + "winkhouse" + File.separator + downfilename);
                if (destination.exists()) {
                    destination.delete();
                }

                ExportSearchParamsHelper esph = null;
                try {
                    esph = new ExportSearchParamsHelper(null);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                if (esph != null) {
                    File download = new File(esph.getCloudSearchDirectory() + File.separator + downfilename);

                    if (download.exists()) {
                        download.delete();
                    }
                }

            }

            e.printStackTrace();

        }

        return result;

    }

}
