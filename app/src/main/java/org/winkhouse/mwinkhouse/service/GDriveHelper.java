package org.winkhouse.mwinkhouse.service;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import org.winkhouse.mwinkhouse.helpers.DataBaseHelper;
import org.winkhouse.mwinkhouse.helpers.ExportSearchParamsHelper;
import org.winkhouse.mwinkhouse.helpers.SearchParam;
import org.winkhouse.mwinkhouse.helpers.WirelessImportDataHelper;
import org.winkhouse.mwinkhouse.util.NotificationHelper;
import org.winkhouse.mwinkhouse.util.SDFileSystemUtils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by michele on 25/09/17.
 */

public class GDriveHelper implements EasyPermissions.PermissionCallbacks{

    private static GDriveHelper instance = null;
    private String accoutName = null;
    private GoogleAccountCredential mCredential;
    public static final String[] SCOPES = { DriveScopes.DRIVE, DriveScopes.DRIVE_READONLY };

    private Drive mService = null;
    private Activity context = null;

    private String[] currentScope = null;

    private int randomNum = 0;
    private ArrayList<SearchParam> parametri = null;

    static final int REQUEST_ACCOUNT_PICKER = 1000;


    static final int REQUEST_AUTHORIZATION = 1001;

    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;

    private String msgCriteri = null;

    private GDriveHelper(){}

    public static GDriveHelper getInstance(){

        if (instance == null){
            instance = new GDriveHelper();
        }

        return instance;

    }

    public String getMsgCriteri() {
        return msgCriteri;
    }

    public void setMsgCriteri(String msgCriteri) {
        this.msgCriteri = msgCriteri;
    }

    public int getRandomNum() {
        return randomNum;
    }

    public void setRandomNum(int randomNum) {
        this.randomNum = randomNum;
    }

    public ArrayList<SearchParam> getParametri() {
        return parametri;
    }

    public void setParametri(ArrayList<SearchParam> parametri) {
        this.parametri = parametri;
    }

    public void setCurrentScope(String[] currentScope) {
        this.currentScope = currentScope;
    }

    public String[] getCurrentScope() {
        return currentScope;
    }

    public String getAccoutName() {
        return accoutName;
    }

    public void setAccoutName(String accoutName) {
        this.accoutName = accoutName;
    }

//    public void setmCredential(GoogleAccountCredential mCredential) {
//        this.mCredential = mCredential;
//    }

    public GoogleAccountCredential getmCredential() {

        if (mCredential == null){

            mCredential = GoogleAccountCredential.usingOAuth2(getContext(),
                                                              Arrays.asList(SCOPES))
                                                              .setBackOff(new ExponentialBackOff());


        }

        return mCredential;
    }

    public Drive getmService(){

        if (mService == null) {

            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

            mService = new com.google.api.services.drive.Drive.Builder(transport,
                                                                       jsonFactory,
                                                                       getmCredential())
                                                              .setApplicationName("mwinkouse")
                                                              .build();
        }

        return mService;

    }


    public void resetmCredential() {
        this.mCredential = null;
    }

    public boolean uploadSearch(String filePathName){

        File fileMetadata = new File();
        String fileName = filePathName.substring(filePathName.lastIndexOf(java.io.File.separator)+1);

        fileMetadata.setName(fileName);

        java.io.File uploadfilePath = new java.io.File(filePathName);

        FileContent mediaContent = new FileContent("application/zip", uploadfilePath);

        File file = null;
        try {
            file = getmService().files().create(fileMetadata, mediaContent)
                                        .setFields("id")
                                        .execute();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }

    public boolean pollingResult(String queryFileName){

        boolean returnValue = false;

        String responseFileName = queryFileName.replace("_Q_", "_R_");
        java.util.List<File> files = new ArrayList<File>();

        Drive.Files.List result = null;

        FileList result2 = null;
        String pageToken = null;

        try {
            do {
                result = getmService().files().list();

                result2 = result.setPageSize(20)
                                .setFields("nextPageToken, files(id, name)")
                                .execute();
                result2.setNextPageToken(result.getPageToken());

                files.addAll(result2.getFiles());
            }while(result.getPageToken() != null && result.getPageToken().length() > 0);

            ExportSearchParamsHelper esph = new ExportSearchParamsHelper(null);

            if (files != null) {

                for (File file : files) {

                    if (file.getName().equalsIgnoreCase(responseFileName)) {

                        java.io.File download = new java.io.File(esph.getCloudSearchDirectory() + java.io.File.separator + responseFileName);

                        OutputStream outputStream2 = new BufferedOutputStream(new FileOutputStream(download));

                        try {
                            getmService().files().get(file.getId()).executeMediaAndDownloadTo(outputStream2);
                            NotificationHelper.getInstance()
                                              .doNotificationBar(this.context,
                                                                 "immobili",
                                                                 "Ricerca cloud immobili",
                                                                 "File risultati scaricato " + this.getMsgCriteri(),
                                                                 this.getRandomNum());
                            outputStream2.flush();
                            outputStream2.close();

                            String[] rfnArr = responseFileName.split("_");
                            String extraFolderName = "WFTPR_" + rfnArr[2];

                            WirelessImportDataHelper widh = new WirelessImportDataHelper(extraFolderName, false);
                            widh.setContext(this.context);

                            java.io.File destination = new java.io.File(Environment.getExternalStorageDirectory() +
                                    java.io.File.separator + "winkhouse" +
                                    java.io.File.separator + responseFileName);

                            if (widh.getDataUpdateMode().equalsIgnoreCase(WirelessImportDataHelper.UPDATE_METHOD_SOVRASCRIVI)) {
                                widh.deleteImportDirContent(new java.io.File(widh.getImportDirectory()), new ArrayList<String>());
                            } else {
                                widh.deleteImportDirContent(new java.io.File(widh.getImportDirectory()), new ArrayList<String>() {{
                                    add("immagini");
                                }});
                            }

                            SDFileSystemUtils sdfsu = new SDFileSystemUtils();

                            if (sdfsu.copyFile(download, destination)) {

                                if (widh.unZipArchivioWireless(this.context, null, null, responseFileName)) {

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
//                                    itemNotToDel.add("immagini");
                                    widh.deleteImportDirContent(new java.io.File(widh.getImportDirectory()), itemNotToDel);
                                    destination.delete();
                                    download.delete();
                                    returnValue = true;

                                    NotificationHelper.getInstance()
                                                      .doNotificationBar(this.context,
                                                                         "immobili",
                                                                         "Ricerca cloud immobili",
                                                                         "Dati importati " + getMsgCriteri(),
                                                                         this.getRandomNum());


                                } else {
                                    returnValue = true;
                                }

                            } else {
                                returnValue = true;
                            }


                        }catch(Exception e){
                            NotificationHelper.getInstance()
                                              .doNotificationBar(this.context,
                                                                 "immobili",
                                                                 "Ricerca cloud immobili",
                                                                 "Nessun risultato " + getMsgCriteri(),
                                                                 this.getRandomNum());
                            returnValue = true;

                        }


                    }

                }

            }else{
                    returnValue = false;

            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnValue;
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        NotificationHelper.getInstance()
                .doNotificationBar(this.context,
                        "immobili",
                        "Ricerca cloud immobili",
                        "Permesso utilizzo gdrive concesso " + getMsgCriteri(),
                        this.getRandomNum());

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        NotificationHelper.getInstance()
                          .doNotificationBar(this.context,
                                             "immobili",
                                             "Ricerca cloud immobili",
                                             "Permesso utilizzo gdrive negato " + getMsgCriteri(),
                                             this.getRandomNum());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);

    }

    @AfterPermissionGranted(REQUEST_PERMISSION_GET_ACCOUNTS)
    public void chooseAccount() {
        if (EasyPermissions.hasPermissions(getContext(), Manifest.permission.GET_ACCOUNTS)) {
            if (getAccoutName() != null) {
                getmCredential().setSelectedAccountName(GDriveHelper.getInstance().getAccoutName());
//                doAndUploadRequest();

            } else {
                // Start a dialog from which the user can choose an account
                if (getContext() != null) {
                    context.startActivityForResult(getmCredential().newChooseAccountIntent(),
                                                   REQUEST_ACCOUNT_PICKER);
                }
            }
        } else {
            // Request the GET_ACCOUNTS permission via a user dialog
            if (context != null) {
                EasyPermissions.requestPermissions(context,
                                                   "Seleziona un account google",
                                                   REQUEST_PERMISSION_GET_ACCOUNTS,
                                                   Manifest.permission.GET_ACCOUNTS);
            }
        }
    }

    private class UploadRequest extends AsyncTask<Void,Void,Boolean>{

        private String fileName = null;
        private String filePath = null;
        private GoogleAccountCredential credential = null;

        public UploadRequest(GoogleAccountCredential credential, String filePath, String fileName){

            this.credential = credential;
            this.fileName   = fileName;
            this.filePath   = filePath;

        }

        @Override
        protected Boolean doInBackground(Void... voids) {

//            File fileMetadata = new File();
//            fileMetadata.setName(fileName);
//
//            java.io.File uploadfilePath = new java.io.File(filePath + java.io.File.separator + fileName);
//
//            FileContent mediaContent = new FileContent("application/zip", uploadfilePath);
//
//            File file = null;
//            try {
//                file = getmService().files().create(fileMetadata, mediaContent)
//                                            .setFields("id")
//                                            .execute();
//            } catch (IOException e) {
//                e.printStackTrace();
//                return false;
//            }
//
//            Log.d("DRIVE File ID: ", file.getId());

            FileList result = null;
            try {
                result = getmService().files().list()
                        .setPageSize(10)
                        .setFields("nextPageToken, files(id, name)")
                        .execute();
                List<File> files = result.getFiles();

                if (files != null) {

                    for (File file : files) {

                        java.io.File download = new java.io.File(filePath + java.io.File.separator + "test.txt");

                        OutputStream outputStream2 = new BufferedOutputStream(new FileOutputStream(download));


                        getmService().files().get(file.getId())
                                             .executeMediaAndDownloadTo(outputStream2);


                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
        }

    }

    public Context getContext() {
        return context;
    }

    public void setContext(Activity context) {
        this.context = context;
    }


}
