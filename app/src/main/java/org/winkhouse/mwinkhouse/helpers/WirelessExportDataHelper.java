package org.winkhouse.mwinkhouse.helpers;

import java.io.File;
import java.util.ArrayList;

import org.winkhouse.mwinkhouse.util.ZipUtils;

import android.os.Environment;
import android.os.Handler;
import android.widget.TextView;

public class WirelessExportDataHelper extends ExportDataHelper {

	public WirelessExportDataHelper() {
	}
	
	protected boolean zipArchivio(TextView msg_field,Handler mHandler ){
		
		boolean returnValue = true;
		
		ZipUtils zu = new ZipUtils(Environment.getExternalStorageDirectory() + File.separator + "winkhouse/export", 
								   Environment.getExternalStorageDirectory() + File.separator + "winkhouse/winkhouseExport.zip");
		zu.zipDirectory();
		
		WirelessImportDataHelper importhelper = new WirelessImportDataHelper(null);

		importhelper.deleteImportDirContent(new File(Environment.getExternalStorageDirectory() + File.separator + "winkhouse/export"),new ArrayList<String>());

		File f = new File(Environment.getExternalStorageDirectory() + File.separator + "winkhouse/winkhouseExport.zip");
		
		returnValue = f.exists();
		
		return returnValue;
		
	}


}
