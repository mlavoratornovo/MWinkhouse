package org.winkhouse.mwinkhouse.helpers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.winkhouse.mwinkhouse.models.SysSettingVO;
import org.winkhouse.mwinkhouse.util.SDFileSystemUtils;
import org.winkhouse.mwinkhouse.util.SysSettingNames;
import org.winkhouse.mwinkhouse.util.ZipUtils;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

public class WirelessImportDataHelper extends ImportDataHelper {
		
	
	class FieldMsgUpdate implements Runnable {
		
		private TextView msg_field = null; 
		private String message = null;
		
		public FieldMsgUpdate(TextView msg_field, String message){
			this.msg_field = msg_field; 
			this.message = message;			
		}
		
		public void run() {
			msg_field.setText(message);
		}
		
	}	
	
	public WirelessImportDataHelper(String extraImportPath, boolean overridebasepath) {
	    super(extraImportPath, overridebasepath);
	}
		
	public byte[] convertIntegers(List<Integer> integers)	{
	    byte[] ret = new byte[integers.size()];
	    Iterator<Integer> iterator = integers.iterator();
	    for (int i = 0; i < ret.length; i++)
	    {
	        ret[i] = iterator.next().byteValue();
	    }
	    return ret;
	}
	
	public boolean downloadData(DataBaseHelper dbh){
				
		boolean returnValue = true;
		
		SysSettingVO ssWinkhouseIp = dbh.getSysSettingByName(SysSettingNames.WINKHOUSE_IP, null);
		SysSettingVO ssWinkhousePort = dbh.getSysSettingByName(SysSettingNames.WINKHOUSE_PORT, null);
		
		int bytesRead;
	    int current = 0;
	    
	    FileOutputStream fos = null;
	    BufferedOutputStream bos = null;
	    Socket sock = null;
	    
	    try {
	      sock = new Socket(ssWinkhouseIp.getSettingValue(), Integer.valueOf(ssWinkhousePort.getSettingValue()));
	      // receive file
	      ArrayList<Integer> myArList = new ArrayList<Integer>();
	      InputStream is = sock.getInputStream();
	      
	      int tmp_read = is.read();
	      
	      while(tmp_read != -1){
	    	  myArList.add(tmp_read);
	    	  tmp_read = is.read();
	      }
	      
	      fos = new FileOutputStream(Environment.getExternalStorageDirectory() + File.separator + "winkhouse/winkhouse_wireless_import.zip");
	      bos = new BufferedOutputStream(fos);
	      
	      byte[] bytesToRead = convertIntegers(myArList);
	      
	      bos.write(bytesToRead);
	      bos.flush();

	    } catch (NumberFormatException e) {
	    	e.printStackTrace();
	    	returnValue = false;
	    } catch (UnknownHostException e) {
	    	e.printStackTrace();
			returnValue = false;		
		} catch (IOException e) {
			e.printStackTrace();
			returnValue = false;
		} catch (Exception e) {
			e.printStackTrace();
			returnValue = false;
		}
	    
	    finally {
	      if (fos != null)
			try {
				fos.close();
			} catch (IOException e) {
				returnValue = false;
			}
	      if (bos != null)
			try {
				bos.close();
			} catch (IOException e) {
				returnValue = false;
			}
	      if (sock != null)
			try {
				sock.close();
			} catch (IOException e) {
				returnValue = false;
			}
	    }
	    
	    return returnValue;	    		
		
	}
	

	public boolean unZipArchivioWireless(Context context, TextView msg_field, Handler mHandler, String zipfilename) {

        boolean returnValue = true;

        String zipFile = zipfilename;
        File fz = new File(zipFile);
        ZipUtils zu = new ZipUtils(context);
        File f = new File(location);
        if (!f.isDirectory()) {
            f.mkdirs();
        }
        //+ File.separator + fz.getName().replace(".zip", "")
        // verificare calls per modificare path copia immagini verificare se ci passa l'unzip non cloud
        if (zu.unZip4jArchivio(zipFile, location)) {
            SDFileSystemUtils sd = new SDFileSystemUtils();
//            returnValue = sd.copyFolder(location + File.separator + "immagini",
//                                        location.replace("import","immagini"));
            returnValue = sd.copyFolder(location + File.separator + "immagini",
                    Environment.getExternalStorageDirectory() +
                            File.separator +
                            "winkhouse" +
                            File.separator +
                            "immagini");
        }else{
			returnValue = false;
        }

        return returnValue;

	}


	public void deleteImportDirContent(File dirLocation, ArrayList<String> namesNotToDel){
		
		File[] array = dirLocation.listFiles();
		
		if (array != null){ 
			for (int i = 0; i < array.length; i++) {
				if (array[i].isDirectory()){
					if (!namesNotToDel.contains(array[i].getName())) {
						deleteImportDirContent(array[i],namesNotToDel);
						array[i].delete();
					}

				}else{
					if (!namesNotToDel.contains(array[i].getName())) {
						array[i].delete();
					}
				}
			}
		}
		
		dirLocation.delete();
		
	}
	
	public boolean uploadData(DataBaseHelper dbh){
		
		boolean returnValue = true;
		
		return returnValue;
	}

}
