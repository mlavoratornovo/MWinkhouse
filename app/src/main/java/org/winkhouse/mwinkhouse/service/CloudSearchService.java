package org.winkhouse.mwinkhouse.service;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.winkhouse.mwinkhouse.R;
import org.winkhouse.mwinkhouse.activity.anagrafiche.ListaAnagraficheActivity;
import org.winkhouse.mwinkhouse.activity.anagrafiche.RicercaAnagraficheActivity;
import org.winkhouse.mwinkhouse.activity.immobili.ListaImmobiliActivity;
import org.winkhouse.mwinkhouse.activity.immobili.RicercaImmobiliActivity;
import org.winkhouse.mwinkhouse.helpers.ExportSearchParamsHelper;
import org.winkhouse.mwinkhouse.helpers.SearchParam;
import org.winkhouse.mwinkhouse.helpers.WirelessExportDataHelper;
import org.winkhouse.mwinkhouse.util.ActivityMessages;
import org.winkhouse.mwinkhouse.util.NotificationHelper;
import org.winkhouse.mwinkhouse.util.SysSettingNames;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.widget.Toast;

public class CloudSearchService extends IntentService {

    private Toast t = null;

	public CloudSearchService() {

		super("CloudSearchService");

	}

	public CloudSearchService(String name) {
		super(name);
	}


	@Override
	protected void onHandleIntent(Intent arg0) {

		if (arg0.getExtras() != null){

            String filename = "mwinkhouse_Q_";
            boolean doPolling = true;
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            int polling = Integer.valueOf(sharedPref.getString(SysSettingNames.CLOUD_POLLING,"30"));

			if (
                (arg0.getExtras().containsKey(ActivityMessages.SEARCH_ACTION) &&
				 arg0.getExtras().containsKey(SearchParam.class.getName()))
                ){
						
				Parcelable[] obj_arr = arg0.getExtras().getParcelableArray(SearchParam.class.getName());
				if (obj_arr != null){

                    int randomNo = arg0.getExtras().getInt("randomNo");

                    arg0.getExtras().remove(SearchParam.class.getName());
					 
					ArrayList<SearchParam> al_params = new ArrayList<SearchParam>();
					 
					for (int i = 0; i < obj_arr.length; i++) {				
						al_params.add((SearchParam)obj_arr[i]);
					}
                    if (al_params.size() > 0) {

                        String notificationmsg = "";
                        for (SearchParam sp : al_params){
                            notificationmsg += sp.toString();
                        }

                        try {

                            filename += randomNo + "_";
                            filename += String.valueOf(new Date().getTime());
                            String xmlfilename = filename + ".xml";

                            ExportSearchParamsHelper esph = new ExportSearchParamsHelper(null);

                            if (esph.exportSearchParamToXML(al_params, xmlfilename)) {

                                if (esph.zipArchivio(this, esph.getCloudSearchDirectory(), esph.getCloudSearchDirectory() + File.separator + filename)) {

                                    if (doUploadRequest(getApplicationContext(),randomNo,notificationmsg,esph.getCloudSearchDirectory(),filename)){

                                        NotificationHelper.getInstance().doNotificationBar(getApplicationContext(),
                                                                                           al_params.get(0).getSearchType(),
                                                                                           "Ricerca cloud " + al_params.get(0).getSearchType(),
                                                                                           "Inviata ricerca " + notificationmsg,
                                                                                           randomNo);

                                    } else {
                                        NotificationHelper.getInstance().doNotificationBar(getApplicationContext(),
                                                                                           al_params.get(0).getSearchType(),
                                                                                           "Ricerca cloud " + al_params.get(0).getSearchType(),
                                                                                           "Impossibile inviare ricerca " + notificationmsg,
                                                                                           randomNo);
                                        doPolling = false;

                                    }

                                    File fxml = new File(esph.getCloudSearchDirectory() + File.separator + filename + ".xml");
                                    try {
                                        fxml.delete();
                                    } catch (Exception e) {
                                        fxml = null;
                                    }

                                    File fzip = new File(esph.getCloudSearchDirectory() + File.separator + filename + ".zip");

                                    try {
                                        fzip.delete();
                                    } catch (Exception e) {
                                        fzip = null;
                                    }


                                    //								filename ="mwinkhouse_R_1489435815181";
                                    if (doPolling) {
                                        boolean condition = true;
                                        while (condition == true) {
                                            condition = !doPolling(getApplicationContext(),randomNo,notificationmsg,esph.getCloudSearchDirectory(),filename + ".zip");
                                            SystemClock.sleep(polling*1000);
                                        }

                                        NotificationHelper.getInstance().doNotificationBar(getApplicationContext(),
                                                                                           al_params.get(0).getSearchType(),
                                                                                           "Ricerca cloud " + al_params.get(0).getSearchType(),
                                                                                           "Ricerca cloud conclusa " + notificationmsg,
                                                                                           randomNo);
                                    }



                                } else {
                                    NotificationHelper.getInstance().doNotificationBar(getApplicationContext(),
                                                                                       al_params.get(0).getSearchType(),
                                                                                       "Ricerca cloud " + al_params.get(0).getSearchType(),
                                                                                       "Impossibile generare la richiesta",
                                                                                       randomNo);

                                }

                            } else {
                                NotificationHelper.getInstance().doNotificationBar(getApplicationContext(),
                                                                                   al_params.get(0).getSearchType(),
                                                                                   "Ricerca cloud " + al_params.get(0).getSearchType(),
                                                                                   "Impossibile generare la richiesta",
                                                                                   randomNo);
                            }


                        } catch (Exception e) {

                            e.printStackTrace();
                        }

                    }else{

                        NotificationHelper.getInstance().doNotificationBar(getApplicationContext(),
                                                                           al_params.get(0).getSearchType(),
                                                                           "Ricerca cloud " + al_params.get(0).getSearchType(),
                                                                           "Impossibile generare la richiesta, nessun parametro inserito",
                                                                           randomNo);
                    }

				}

			}

		}
		
	}

	protected boolean doUploadRequest(Context context,int randomNo,String notificationmsg,String cloudSearchDirectory, String filename){

        boolean returnValue = false;

        FtpPollingHelper fph = new FtpPollingHelper(getApplicationContext(),randomNo,notificationmsg);

        return fph.uploadSearch(cloudSearchDirectory + File.separator + filename + ".zip");

    }

    protected boolean doPolling(Context context,int randomNo,String notificationmsg,String cloudSearchDirectory, String filename){

        boolean returnValue = false;

        FtpPollingHelper fph = new FtpPollingHelper(getApplicationContext(),randomNo,notificationmsg);

        return fph.pollingResult(filename);

    }

}
