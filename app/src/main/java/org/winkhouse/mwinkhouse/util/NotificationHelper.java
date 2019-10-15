package org.winkhouse.mwinkhouse.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import org.winkhouse.mwinkhouse.R;
import org.winkhouse.mwinkhouse.activity.anagrafiche.ListaAnagraficheActivity;
import org.winkhouse.mwinkhouse.activity.immobili.ListaImmobiliActivity;
import org.winkhouse.mwinkhouse.helpers.SearchParam;

/**
 * Created by michele on 13/06/17.
 */

public class NotificationHelper {

    private static NotificationHelper instance = null;

    private NotificationHelper(){

    }

    public static NotificationHelper getInstance(){


        if (instance == null) {
            instance = new NotificationHelper();
        }
        return instance;
    }

    public void doNotificationBar(Context context,String tipoRicerca,String titolo,String messaggio,int msgid){

        String CHANNEL_ID = "Wink_channel_01";
        CharSequence name = "Wink_channel";
        String Description = "Winkhouse notification channel";

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableVibration(true);
            mChannel.setShowBadge(true);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(mChannel);
            }
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context.getApplicationContext(), CHANNEL_ID);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText(messaggio);
        bigText.setBigContentTitle("Ricerca immobili remota");
        bigText.setSummaryText(messaggio);

        mBuilder.setSmallIcon(R.drawable.winkicona);
        mBuilder.setContentTitle("Ricerca immobili remota");
        mBuilder.setContentText(messaggio);
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);

/*        }else{
            Notification.Builder mBuilder = new Notification.Builder(context.getApplicationContext())
                                                            .setSmallIcon(R.drawable.winkicona)
                                                            .setContentTitle(titolo)
                                                            .setStyle(new Notification.BigTextStyle().bigText(messaggio))
                                                            .setContentText(messaggio)
                                                            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                                                            .setTicker(titolo);

        }*/



        Intent resultIntent = null;
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

        if (tipoRicerca.equalsIgnoreCase(SearchParam.IMMOBILI)) {

            resultIntent = new Intent(context, ListaImmobiliActivity.class);
            stackBuilder.addParentStack(ListaImmobiliActivity.class);

        }else{
            resultIntent = new Intent(context, ListaAnagraficheActivity.class);
            stackBuilder.addParentStack(ListaAnagraficheActivity.class);
        }

        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        //NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(msgid, mBuilder.build());

    }
}
