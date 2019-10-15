package org.winkhouse.mwinkhouse.helpers;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.winkhouse.mwinkhouse.activity.ImportActivity.ThreadSincro;
import org.winkhouse.mwinkhouse.models.SysSettingVO;
import org.winkhouse.mwinkhouse.util.SysSettingNames;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

public class UploadDataWirelessThread extends Thread {

	private WirelessExportDataHelper exportHelper = null;
	private SQLiteDatabase sqldb = null;
	private DataBaseHelper dbh = null; 
	private ThreadSincro status = null;
	private ProgressBar pd_loader_up = null;
	private TextView msg_up = null;
	private Handler mHandler = null;
	private Context context = null;
	private int count = 0;
	
	public UploadDataWirelessThread(Context context,ProgressBar pd_loader_up, TextView msg_up, ThreadSincro status) {
		
		this.context = context;
		this.pd_loader_up = pd_loader_up;		
		this.msg_up = msg_up;
		this.status = status;
		this.mHandler = new Handler();		
		count = 0;
		
		this.exportHelper = new WirelessExportDataHelper();
		this.dbh = new DataBaseHelper(context,DataBaseHelper.NONE_DB);
		this.sqldb = dbh.getReadableDatabase();
		
	}	

	@Override
	public void run() {
		
		BufferedInputStream bis = null;
		FileInputStream fis = null;
		File f = null;
		DataOutputStream os = null;
		

		while(!status.getStatus()){
			try {
					Thread.sleep(500);
				} catch (InterruptedException e) {}
					
		}

			
		SysSettingVO ssWinkhouseIp = dbh.getSysSettingByName(SysSettingNames.WINKHOUSE_IP, null);
		SysSettingVO ssWinkhousePort = dbh.getSysSettingByName(SysSettingNames.WINKHOUSE_PORT, null);
		Socket sock = null;
		
		try {
			
		      sock = new Socket(ssWinkhouseIp.getSettingValue(), Integer.valueOf(ssWinkhousePort.getSettingValue()));
		      os = new DataOutputStream(sock.getOutputStream());
		      
		      f = new File(Environment.getExternalStorageDirectory() + File.separator + "winkhouse/winkhouseExport.zip");
		      
		      bis = new BufferedInputStream(new FileInputStream(f));				      
			  pd_loader_up.setMax(Long.valueOf(f.length()/1024).intValue());
			  
		      byte[] zipfilearr = new byte[1024];
		      int bytesRead = 0;
		    		  
		      while ((bytesRead = bis.read(zipfilearr)) != -1) {
		    	  
		          os.write(zipfilearr, 0, bytesRead);
	              os.flush();
	              count += bytesRead;
		    	  this.mHandler.post(new Runnable() {
							
							@Override
							public void run() {
								msg_up.setText("Inviati " + String.valueOf(count/1024) + "/" + String.valueOf(pd_loader_up.getMax()) + " kB");									
								pd_loader_up.setProgress(count);
								status.setStatus(false);
								return;
							}
			      });
		            
		      }
		      bis.close();
		      os.close();
//		      int x = fis.read();
//		      while (x != -1){
//		    	  os.write(x);
//		    	  os.flush();
//		    	  count ++;
//		    	  this.mHandler.post(new Runnable() {
//						
//						@Override
//						public void run() {
//							msg_up.setText("Inviati " + String.valueOf(count/1024) + "/" + String.valueOf(pd_loader_up.getMax()) + " kB");									
//							pd_loader_up.setProgress(count);
//							status.setStatus(false);
//							return;
//						}
//		    	  });
//		      }
		      				      
		      
		 } catch (NumberFormatException e) {
			 this.mHandler.post(new Runnable() {
					
					@Override
					public void run() {
						msg_up.setText("Impossibile contattare winkhouse");
						pd_loader_up.setIndeterminate(false);
						pd_loader_up.setProgress(0);
						status.setStatus(false);
					}
				});
				return;
		 } catch (UnknownHostException e) {
			 this.mHandler.post(new Runnable() {
					
					@Override
					public void run() {
						msg_up.setText("Impossibile contattare winkhouse");
						pd_loader_up.setIndeterminate(false);
						pd_loader_up.setProgress(0);
						status.setStatus(false);
					}
				});
				return;							
		 } catch (IOException e) {
			 this.mHandler.post(new Runnable() {
					
					@Override
					public void run() {
						msg_up.setText("Impossibile contattare winkhouse");
						pd_loader_up.setIndeterminate(false);
						pd_loader_up.setProgress(0);
						status.setStatus(false);
					}
				});
				return;
		 } catch (Exception e) {
			 this.mHandler.post(new Runnable() {
					
					@Override
					public void run() {
						msg_up.setText("Impossibile contattare winkhouse");
						pd_loader_up.setIndeterminate(false);
						pd_loader_up.setProgress(0);
						status.setStatus(false);
					}
				});
				return;
		 }
		 finally {
//			      try {
//					bis.close();
//				} catch (IOException e) {
//					Log.e("Error", e.getMessage());
//				}
//			      try {
//					fis.close();
//				} catch (IOException e) {
//					Log.e("Error", e.getMessage());
//				}
//			      try {
//					os.close();
//				} catch (IOException e) {
//					Log.e("Error", e.getMessage());
//				}
			    status.setStatus(true);
		 }
							 
	}		

}
