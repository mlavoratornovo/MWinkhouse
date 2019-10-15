package org.winkhouse.mwinkhouse.helpers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.winkhouse.mwinkhouse.models.SysSettingVO;
import org.winkhouse.mwinkhouse.util.SysSettingNames;

import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

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
	
	public WirelessImportDataHelper(String extraImportPath) {
	    super(extraImportPath);
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
	
	public boolean unZipArchivioWireless(TextView msg_field,Handler mHandler,String zipfilename){
		
//		Handler mHandler = new Handler();
		boolean returnValue = true;

		String zipFile = Environment.getExternalStorageDirectory() + File.separator + "winkhouse/"+zipfilename;
//		String location = Environment.getExternalStorageDirectory() + File.separator + "winkhouse/import";
		
	    try {
	        File f = new File(location);
	        if(!f.isDirectory()) {
	            f.mkdirs();
	        }
	        File f1 = new File(zipFile);
			boolean x = f.exists();


	        ZipInputStream zin = new ZipInputStream(new FileInputStream(zipFile));

	        try {
	            ZipEntry ze = null;
	            while ((ze = zin.getNextEntry()) != null) {
	            		            	
	            	String filename = ze.getName().substring(ze.getName().lastIndexOf("\\")+1);

	            	if ((mHandler != null) && (msg_field != null)) {
						mHandler.post(new FieldMsgUpdate(msg_field, "Decopressione file " + filename));
					}

	                String path = location + File.separator + filename;
	                	                	            	            
	                if (!ze.getName().endsWith("xml")){
	                	
	                	String imagefilename = ze.getName().substring(ze.getName().lastIndexOf("\\")+1);
	                	String tmp = ze.getName().substring(0,ze.getName().lastIndexOf("\\"));
	                	String codiceimmobile = tmp.substring(tmp.lastIndexOf("\\")+1);
	                	
	                	String dirpath = location + File.separator + "immagini" + File.separator + codiceimmobile;
	                	File dirpathfile = new File(dirpath);
	                	dirpathfile.mkdirs();
						if (getDataUpdateMode().equalsIgnoreCase("aggiungi")){
							imagefilename = "Copy_" + imagefilename;
						}
	                	path = dirpath + File.separator + imagefilename;

	                }
	            	
                    FileOutputStream fout = new FileOutputStream(path, false);
                    
                    try {
                    	long bytecount = 0;
                        for (int c = zin.read(); c != -1; c = zin.read()) {
                            fout.write(c);
                            bytecount ++;
                            if ((mHandler != null) && (msg_field != null)) {
                                mHandler.post(new FieldMsgUpdate(msg_field, "Decopressione file " + filename + " kB " + String.valueOf(bytecount / 1024)));
                            }
                        }
                        zin.closeEntry();
                    }catch(Exception e){
                        Log.getStackTraceString(e);
                        zin.closeEntry();
					}finally {
						try {
							fout.close();
						}catch (Exception e){
							fout = null;
						}
                    }
                    
	            }
	            
	        }catch(Exception e){
	        	
	        	Log.d("WINK", e.getMessage());				
	        	
	        }
	        finally {
	            zin.close();
	        }
	    }
	    catch (Exception e) {
        	
        	Log.d("WINK", e.getMessage());
			
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
