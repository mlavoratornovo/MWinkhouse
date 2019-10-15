package org.winkhouse.mwinkhouse.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.winkhouse.mwinkhouse.helpers.SearchParam;
import org.winkhouse.mwinkhouse.models.restmsgs.ErrorMessage;
import org.winkhouse.mwinkhouse.models.restmsgs.MessageDeleteQueryOk;
import org.winkhouse.mwinkhouse.models.restmsgs.ResponseByCodeQueryName;
import org.winkhouse.mwinkhouse.models.restmsgs.StandardRestMessage;
import org.winkhouse.mwinkhouse.util.NotificationHelper;
import org.winkhouse.mwinkhouse.util.SysSettingNames;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

/**
 * Created by michele on 21/01/19.
 */

public class HTTPHelper {

    private final static String CHKSTATUS_CODICE_ACTION = "wink_get_query_code_controller.php?action=checkstatus";
    private final static String NUOVO_CODICE_ACTION = "wink_get_query_code_controller.php?action=nuovo_codice";
    private final static String POST_QUERYREQUEST_ACTION = "wink_get_query_code_controller.php?action=upload_file";
    private final static String POST_QUERY_FILE_EXIST_ACTION = "wink_get_query_code_controller.php?action=query_file_exist";
    private final static String POST_DOWNLOAD_FILE_BY_ID = "wink_get_query_code_controller.php?action=download_file_by_id";
    private final static String POST_LIST_RESPOSEQUERY_BY_CODE_QUERY_FILENAME = "wink_get_query_code_controller.php?action=list_response_by_code";
    private final static String POST_DELETE_BY_QUERY_FILENAME = "wink_get_query_code_controller.php?action=delete_search_query_file";

    public boolean uploadQueryRequest(Context context, File queryRequestZipFile, String code){

        boolean returnValue = false;

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

        String urlwinkcloud = sharedPref.getString(SysSettingNames.WINKCLOUDURL,"http://www.winkcloudquery.org");
        String urlwinkid = sharedPref.getString(SysSettingNames.WINKCLOUDID,null);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                                                   .setType(MultipartBody.FORM)
                                                   .addFormDataPart("winkcode", code)
                                                   .addFormDataPart("winkcloudid", urlwinkid)
                                                   .addFormDataPart("qfileToUpload", queryRequestZipFile.getName(),
                                                                    RequestBody.create(MediaType.parse("application/octet-stream"),
                                                                                       queryRequestZipFile))
                                                   .build();
        String url = (urlwinkcloud.endsWith("/"))
                      ? urlwinkcloud + POST_QUERYREQUEST_ACTION
                      : urlwinkcloud + "/" + POST_QUERYREQUEST_ACTION;

        Request request = new Request.Builder()
                                     .url(url)
                                     .post(requestBody)
                                     .build();

        Call call = client.newCall(request);
        try {

            Response response = call.execute();

            if (response.code() == 200) {

                GsonBuilder jsonBuilder = new GsonBuilder();
                Gson gson = jsonBuilder.create();

                try {
                    String bodyMessage = response.body().string();
                    StandardRestMessage muOk = gson.fromJson(bodyMessage, StandardRestMessage.class);
                    if (muOk != null && muOk.message != null){
                        if (muOk.code == 200){
                            return true;
                        }else{
                            Random rand = new Random();
                            int randomNum = rand.nextInt(1000);

                            ErrorMessage merr = gson.fromJson(bodyMessage, ErrorMessage.class);
                            NotificationHelper.getInstance()
                                    .doNotificationBar(context,
                                            "immobili",
                                            "Ricerca cloud immobili",
                                            merr.errorMessage,
                                            randomNum);

                            return false;

                        }
                    }else{
                        Random rand = new Random();
                        int randomNum = rand.nextInt(1000);

                        ErrorMessage merr = gson.fromJson(bodyMessage, ErrorMessage.class);
                        NotificationHelper.getInstance()
                                .doNotificationBar(context,
                                        "immobili",
                                        "Ricerca cloud immobili",
                                        merr.errorMessage,
                                        randomNum);

                        return false;

                    }

                } catch (Exception e) {

                    Random rand = new Random();
                    int randomNum = rand.nextInt(1000);

                    ErrorMessage merr = gson.fromJson(response.body().string(), ErrorMessage.class);
                    NotificationHelper.getInstance()
                                      .doNotificationBar(context,
                                             "immobili",
                                                  "Ricerca cloud immobili",
                                                         merr.errorMessage,
                                                         randomNum);

                    return false;
                }
            }
        }catch(IOException ex){
            returnValue = false;
        }

        return returnValue;

    }
/*
    public boolean checkStatus(Context context){

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String urlwinkcloud = sharedPref.getString(SysSettingNames.WINKCLOUDURL,"http://www.winkcloudquery.org");

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet((urlwinkcloud.endsWith("/"))
                                       ? urlwinkcloud + CHKSTATUS_CODICE_ACTION
                                       : urlwinkcloud + "/" + CHKSTATUS_CODICE_ACTION);
        try {
            HttpResponse response = client.execute(request);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            MessageStatusCheck rcm = gson.fromJson(result.toString(), MessageStatusCheck.class);
            return rcm.message.startsWith("WinkCloudQuerySystem_v_1");
        } catch (ClientProtocolException e) {
            return false;
        } catch (IOException e) {
            return false;
        }

    }
*/
    public boolean downloadResponse(Context context, String code, int idfile, File downloadFile){

        boolean returnValue = false;

        int DOWNLOAD_CHUNK_SIZE = 2048;

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String urlwinkcloud = sharedPref.getString(SysSettingNames.WINKCLOUDURL,"http://www.winkcloudquery.org");

        OkHttpClient client = new OkHttpClient();
        String url = (urlwinkcloud.endsWith("/"))
                      ? urlwinkcloud + POST_DOWNLOAD_FILE_BY_ID
                      : urlwinkcloud + "/" + POST_DOWNLOAD_FILE_BY_ID;

        RequestBody formBody = new FormBody.Builder()
                                           .add("idfile", String.valueOf(idfile))
                                           .add("winkcode", code)
                                           .build();

        Request request = new Request.Builder()
                                     .url(url)
                                     .post(formBody)
                                     .build();

        try {
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            BufferedSource source = body.source();
            BufferedSink sink = Okio.buffer(Okio.sink(downloadFile));
            long read = 0;
            source.readAll(sink);
            sink.writeAll(source);
            sink.flush();
            sink.close();
            returnValue=true;
        }catch (FileNotFoundException f){
            returnValue=false;
        }catch (IOException f){
            returnValue=false;
        }

        return returnValue;
    }

    public ResponseByCodeQueryName[] getQueryResposesByCodeQueryName(Context context, String code, String queryName){

        ResponseByCodeQueryName[] returnValue = null;

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String urlwinkcloud = sharedPref.getString(SysSettingNames.WINKCLOUDURL,"http://www.winkcloudquery.org");

        OkHttpClient client = new OkHttpClient();
        String url = (urlwinkcloud.endsWith("/"))
                      ? urlwinkcloud + POST_LIST_RESPOSEQUERY_BY_CODE_QUERY_FILENAME
                      : urlwinkcloud + "/" + POST_LIST_RESPOSEQUERY_BY_CODE_QUERY_FILENAME;

        RequestBody formBody = new FormBody.Builder()
                                           .add("qfilename", queryName)
                                           .add("winkcode", code)
                                           .build();

        Request request = new Request.Builder()
                                     .url(url)
                                     .post(formBody)
                                     .build();

        Call call = client.newCall(request);
        try {

            Response response = call.execute();

            if (response.code() == 200) {
                GsonBuilder jsonBuilder = new GsonBuilder();
                Gson gson = jsonBuilder.create();
                try{
                    returnValue = gson.fromJson(response.body().string(), ResponseByCodeQueryName[].class);
                }catch(Exception e){
                    returnValue = null;
                }

            }
        }catch(IOException ex){
            returnValue = null;
        }

        return returnValue;

    }

    public boolean deleteByQueryFileName(Context context, String code, String queryName){

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String urlwinkcloud = sharedPref.getString(SysSettingNames.WINKCLOUDURL,"http://www.winkcloudquery.org");

        OkHttpClient client = new OkHttpClient();
        String url = (urlwinkcloud.endsWith("/"))
                ? urlwinkcloud + POST_DELETE_BY_QUERY_FILENAME
                : urlwinkcloud + "/" + POST_DELETE_BY_QUERY_FILENAME;

        RequestBody formBody = new FormBody.Builder()
                .add("qfilename", queryName)
                .add("winkcode", code)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        Call call = client.newCall(request);
        try {

            Response response = call.execute();

            if (response.code() == 200) {
                GsonBuilder jsonBuilder = new GsonBuilder();
                Gson gson = jsonBuilder.create();
                try{
                    MessageDeleteQueryOk returnValue = gson.fromJson(response.body().string(), MessageDeleteQueryOk.class);
                    return (returnValue.code == 200) && (returnValue.message != null);
                }catch(Exception e){
                    return false;
                }

            }
        }catch(IOException ex){
            return false;
        }

        return true;

    }
/*
    public ResponseByCodeQueryName[] getQueryResposesByCodeQueryName(Context context, String code, String queryName){

        ResponseByCodeQueryName[] returnValue = null;

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String urlwinkcloud = sharedPref.getString(SysSettingNames.WINKCLOUDURL,"http://www.winkcloudquery.org");

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost((urlwinkcloud.endsWith("/"))
                                     ? urlwinkcloud + POST_LIST_RESPOSEQUERY_BY_CODE_QUERY_FILENAME
                                     : urlwinkcloud + "/" + POST_LIST_RESPOSEQUERY_BY_CODE_QUERY_FILENAME);

        StringBody sbCode = new StringBody(code, ContentType.TEXT_PLAIN);
        StringBody sbQfilename = new StringBody(queryName, ContentType.TEXT_PLAIN);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addPart("winkcode", sbCode);
        builder.addPart("qfilename", sbQfilename);

        HttpEntity entity = builder.build();

        post.setEntity(entity);

        HttpEntity resEntity = null;

        try {

            CloseableHttpResponse response = client.execute(post);
            resEntity = response.getEntity();
            System.out.println(response.getStatusLine());

            if (resEntity != null) {
                System.out.println("Response content length: " + resEntity.getContentLength());
            }

            BufferedReader rd = new BufferedReader(new InputStreamReader(resEntity.getContent()));
            StringBuffer result = new StringBuffer();
            String line = "";

            while ((line = rd.readLine()) != null) {
                result.append(line);
            }


            GsonBuilder jsonBuilder = new GsonBuilder();
            Gson gson = jsonBuilder.create();
            try{
                returnValue = gson.fromJson(result.toString(), ResponseByCodeQueryName[].class);
            }catch(Exception e){
                returnValue = null;
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                EntityUtils.consume(resEntity);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return returnValue;

    }
*/

}
