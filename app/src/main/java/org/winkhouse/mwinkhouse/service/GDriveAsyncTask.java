package org.winkhouse.mwinkhouse.service;

import android.os.AsyncTask;

import org.winkhouse.mwinkhouse.helpers.ExportSearchParamsHelper;
import org.winkhouse.mwinkhouse.helpers.SearchParam;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by michele on 02/05/17.
 */

public class GDriveAsyncTask extends AsyncTask {

    @Override
    protected Object doInBackground(Object[] params) {
//        String filename = "mwinkhouse";
//
//        filename = filename + "_Q_" + String.valueOf(new Date().getTime());
//        String xmlfilename = filename + ".xml";
//
//        ExportSearchParamsHelper esph = new ExportSearchParamsHelper();
//        if (esph.exportSearchParamToXML((ArrayList<SearchParam>)params[0], xmlfilename)) {
//
//            if (esph.zipArchivio(esph.getCloudSearchDirectory(), esph.getCloudSearchDirectory() + File.separator + filename)) {
//
//                if (GDrivePollingHelper..uploadSearch(esph.getCloudSearchDirectory() + File.separator + filename + ".zip")) {
//
//                }
//
//            }
//        }
          return null;
    }
}
