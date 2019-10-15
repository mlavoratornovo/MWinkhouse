package org.winkhouse.mwinkhouse.service;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;

import org.winkhouse.mwinkhouse.service.GDriveHelper;
import org.winkhouse.mwinkhouse.util.ActivityMessages;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Created by michele on 19/06/17.
 */

public class CloudSearchGDriveService extends CloudSearchService {

    private Account accountName = null;

    public CloudSearchGDriveService() {

        super("CloudSearchGDriveService");

    }

    @Override
    protected boolean doUploadRequest(Context context, int randomNo, String notificationmsg, String cloudSearchDirectory, String filename) {

        GDriveHelper.getInstance().setMsgCriteri(notificationmsg);
        return  GDriveHelper.getInstance().uploadSearch(cloudSearchDirectory + File.separator + filename + ".zip");

    }

    @Override
    protected boolean doPolling(Context context, int randomNo, String notificationmsg, String cloudSearchDirectory, String filename) {

        GDriveHelper.getInstance().setMsgCriteri(notificationmsg);

        return GDriveHelper.getInstance().pollingResult(filename);

    }

}
