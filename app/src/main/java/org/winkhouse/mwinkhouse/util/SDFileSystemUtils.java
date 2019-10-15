package org.winkhouse.mwinkhouse.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import android.os.Environment;

public class SDFileSystemUtils {

	public boolean createWinkFolder(){
		
		File winkdir_import = new File(Environment.getExternalStorageDirectory() + File.separator + "winkhouse/import");
		File winkdir_export = new File(Environment.getExternalStorageDirectory() + File.separator + "winkhouse/export");
		
		return winkdir_import.mkdirs() && winkdir_export.mkdirs();
		 
	}
	
	public File readDataFromImportFolder(){
		return null;
	}
	
	public boolean writeDataToExportFolder(){
		return true;
	}

	public boolean copyFile(File sourceFile, File destFile){

		try {
			if (!sourceFile.exists()) {
				return false;
			}

			if (!destFile.exists()) {
				destFile.createNewFile();
			}

			FileChannel source = null;
			FileChannel destination = null;

			source = new FileInputStream(sourceFile).getChannel();
			destination = new FileOutputStream(destFile).getChannel();
			if (destination != null && source != null) {
				destination.transferFrom(source, 0, source.size());
			}
			if (source != null) {
				source.close();
			}
			if (destination != null) {
				destination.close();
			}
		} catch (IOException e) {
			return false;
		}

		return true;
	}

	public boolean deleteFolder(File folderToDelete){

	    boolean returnValue = true;

	    if (folderToDelete.isDirectory()){
            for (File folderFile: folderToDelete.listFiles()) {
                deleteFolder(folderFile);
            }
            folderToDelete.delete();
        }else{
            folderToDelete.delete();
        }


	    return returnValue;
    }
}
