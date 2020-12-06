package org.winkhouse.mwinkhouse.helpers;

import java.io.File;
import java.util.ArrayList;
import org.winkhouse.mwinkhouse.util.ZipUtils;

import android.content.Context;
import android.os.Handler;
import android.widget.TextView;

public class WirelessExportDataHelper extends ExportDataHelper {

	public WirelessExportDataHelper(ArrayList itemsToExport) throws Exception{
        super(itemsToExport);
	}
	
	protected boolean zipArchivio(Context context,
                                  TextView msg_field, Handler mHandler, String folderToExport, String pathZipFilename){
		
		boolean returnValue = true;

        ZipUtils zu = new ZipUtils(context);
        zu.zip4jArchivio(folderToExport,pathZipFilename);

		//WirelessImportDataHelper importhelper = new WirelessImportDataHelper(null);

		//importhelper.deleteImportDirContent(new File(Environment.getExternalStorageDirectory() + File.separator + "winkhouse/export"),new ArrayList<String>());

		File f = new File(pathZipFilename);
		
		returnValue = f.exists();
		
		return returnValue;
		
	}


}
