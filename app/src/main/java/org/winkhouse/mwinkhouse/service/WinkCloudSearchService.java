package org.winkhouse.mwinkhouse.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcelable;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import org.winkhouse.mwinkhouse.helpers.DataBaseHelper;
import org.winkhouse.mwinkhouse.helpers.ExportSearchParamsHelper;
import org.winkhouse.mwinkhouse.helpers.SearchParam;
import org.winkhouse.mwinkhouse.helpers.WirelessImportDataHelper;
import org.winkhouse.mwinkhouse.util.ActivityMessages;
import org.winkhouse.mwinkhouse.util.NotificationHelper;
import org.winkhouse.mwinkhouse.util.SDFileSystemUtils;
import org.winkhouse.mwinkhouse.util.SysSettingNames;
import org.winkhouse.mwinkhouse.util.ZipUtils;


/**
 * Created by michele on 21/01/19.
 */

public class WinkCloudSearchService extends IntentService {

    public WinkCloudSearchService() {

        super("WinkCloudSearchService");

    }

    public WinkCloudSearchService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        SDFileSystemUtils SDUtils = new SDFileSystemUtils();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        int polling = Integer.valueOf(sharedPref.getString(SysSettingNames.CLOUD_POLLING, "30"));
        String code = sharedPref.getString(SysSettingNames.WINKCLOUDID, null);
        if (code == null) {
            NotificationHelper.getInstance().doNotificationBar(this,
                    "ricerca cloud",
                    "errore",
                    "Inserire il WinkCloud ID su cui ricercare nelle impostazioni",
                    123);

        }else{
            boolean doPolling = true;
            ExportSearchParamsHelper esph = null;
            try {
                esph = new ExportSearchParamsHelper(null);
            }catch (Exception e){

            }
            String name = String.valueOf(new Date().getTime());

            String pathWorkingFolder = "cloudsearch" + File.separator + name;
            if (esph != null) {
                File dummyF = new File(esph.getCloudSearchDirectory() + File.separator + pathWorkingFolder);
                if (!dummyF.exists()) {
                    dummyF.mkdirs();
                }
                String pathrequestzipfile = null;

                HTTPHelper httpHelper = new HTTPHelper();
                String basefilename = String.valueOf(new Date().getTime());
                String zipfilename = basefilename;
                pathrequestzipfile = pathWorkingFolder.split(File.separator)[0] + File.separator + zipfilename;

                try {
                    if (!new File(pathrequestzipfile + ".zip").exists()) {


                        if ((intent.getExtras().containsKey(ActivityMessages.SEARCH_ACTION) &&
                                intent.getExtras().containsKey(SearchParam.class.getName()))) {

                            Parcelable[] obj_arr = intent.getExtras().getParcelableArray(SearchParam.class.getName());
                            if (obj_arr != null) {
                                int randomNo = intent.getExtras().getInt("randomNo");

                                intent.getExtras().remove(SearchParam.class.getName());

                                ArrayList<SearchParam> al_params = new ArrayList<SearchParam>();

                                for (int i = 0; i < obj_arr.length; i++) {
                                    al_params.add((SearchParam) obj_arr[i]);
                                }
                                if (al_params.size() > 0) {

                                    String notificationmsg = "";
                                    for (SearchParam sp : al_params) {
                                        notificationmsg += sp.toString() + " ";
                                    }

                                    String xmlfilename = pathWorkingFolder + File.separator + "RicercheXMLModel.xml";
                                    if (esph.exportSearchParamToXML(al_params, xmlfilename)) {

                                        File f = new File(esph.getCloudSearchDirectory() + File.separator + pathWorkingFolder.split(File.separator)[0] + File.separator + zipfilename + ".zip");
                                        if (f.exists()) {
                                            f.delete();
                                        }

                                        if (esph.zipArchivio(this,esph.getCloudSearchDirectory() + File.separator + pathWorkingFolder,
                                                esph.getCloudSearchDirectory() + File.separator + pathWorkingFolder.split(File.separator)[0] + File.separator + zipfilename)) {

                                            if (httpHelper.uploadQueryRequest(this,
                                                    new File(esph.getCloudSearchDirectory() + File.separator + pathWorkingFolder.split(File.separator)[0] + File.separator + zipfilename + ".zip"),
                                                    code)) {
                                                doPolling = true;
                                                NotificationHelper.getInstance().doNotificationBar(this,
                                                        al_params.get(0).getSearchType(),
                                                        "Ricerca cloud " + al_params.get(0).getSearchType(),
                                                        "Ricerca inviata " + notificationmsg,
                                                        randomNo);

                                            } else {
                                                NotificationHelper.getInstance().doNotificationBar(this,
                                                        al_params.get(0).getSearchType(),
                                                        "Ricerca cloud " + al_params.get(0).getSearchType(),
                                                        "Impossibile inviare ricerca " + notificationmsg,
                                                        randomNo);

                                                doPolling = false;
                                            }

                                            File fzip = new File(esph.getCloudSearchDirectory() + File.separator + pathWorkingFolder.split(File.separator)[0] + File.separator + zipfilename + ".zip");
                                            try {
                                                fzip.delete();
                                            } catch (Exception e) {
                                                fzip = null;
                                            }

                                            SDUtils.deleteFolder(new File(esph.getCloudSearchDirectory() + File.separator + pathWorkingFolder));

                                            if (doPolling) {
                                                boolean condition = true;
                                                while (condition == true) {

                                                    //condition = !doPolling(this, randomNo, notificationmsg, esph.getCloudSearchDirectory(), zipfilename + ".zip");
                                                    condition = !doPolling(this, randomNo, notificationmsg, esph.getCloudSearchDirectory(), "1594449861198.zip");
                                                    SystemClock.sleep(polling * 1000);
                                                }

                                                NotificationHelper.getInstance().doNotificationBar(this,
                                                        al_params.get(0).getSearchType(),
                                                        "Ricerca cloud " + al_params.get(0).getSearchType(),
                                                        "Ricerca cloud conclusa " + notificationmsg,
                                                        randomNo);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                NotificationHelper.getInstance().doNotificationBar(this,
                        "ricerca cloud",
                        "errore",
                        "Ricerca cloud conclusa errore sconosciuto",
                         123);
            }
        }
    }

    protected boolean doPolling(Context context, int randomNo, String notificationmsg, String cloudSearchDirectory, String filename){

        boolean returnValue = false;

        WinkCloudHelper fph = new WinkCloudHelper(this,randomNo,notificationmsg);

        return fph.pollingResult(filename);

    }

}
