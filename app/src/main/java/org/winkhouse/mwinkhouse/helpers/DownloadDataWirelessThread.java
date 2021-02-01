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

import org.winkhouse.mwinkhouse.activity.ImportActivity.ThreadSincro;
import org.winkhouse.mwinkhouse.activity.StartUpActivity;
import org.winkhouse.mwinkhouse.models.SysSettingVO;
import org.winkhouse.mwinkhouse.util.SysSettingNames;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DownloadDataWirelessThread extends Thread {
	
	private final StartUpActivity activity = null;
	private WirelessImportDataHelper importhelper = null;
	private Context context = null;
	private ThreadSincro status = null;
	private SQLiteDatabase sqldb = null;
	private DataBaseHelper dbh = null; 
	private ProgressBar pd_loader_down = null;
	private TextView msg_down = null;
	private Handler mHandler = null;
	private long count = 1;
	
	public DownloadDataWirelessThread(Context context,ProgressBar pd_loader_down, TextView msg_down, ThreadSincro status) {
		
		this.context = context;
		this.pd_loader_down = pd_loader_down;
		this.pd_loader_down.setIndeterminate(true);
		this.msg_down = msg_down;
		this.status = status;
		this.mHandler = new Handler();
		this.importhelper = new WirelessImportDataHelper(null, false);

		if (this.importhelper.getDataUpdateMode().equalsIgnoreCase(WirelessImportDataHelper.UPDATE_METHOD_SOVRASCRIVI)) {
			this.importhelper.deleteImportDirContent(new File(this.importhelper.getImportDirectory()),new ArrayList<String>());
		}else{
			this.importhelper.deleteImportDirContent(new File(this.importhelper.getImportDirectory()),new ArrayList<String>(){{add("immagini");}});
		}

		this.dbh = new DataBaseHelper(context,DataBaseHelper.NONE_DB);
		this.sqldb = dbh.getReadableDatabase();
		
		
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
	
	@Override
	public void run() {
				
		SysSettingVO ssWinkhouseIp = dbh.getSysSettingByName(SysSettingNames.WINKHOUSE_IP, null);
		SysSettingVO ssWinkhousePort = dbh.getSysSettingByName(SysSettingNames.WINKHOUSE_PORT, null);

		
		int bytesRead;
	    int current = 0;
	    
	    FileOutputStream fos = null;
	    BufferedOutputStream bos = null;
	    Socket sock = null;

		try {
			sock = new Socket(ssWinkhouseIp.getSettingValue(), Integer.valueOf(ssWinkhousePort.getSettingValue()));
		} catch (NumberFormatException e1) {			
			this.mHandler.post(new Runnable() {
				
				@Override
				public void run() {
					msg_down.setText("Impossibile contattare winkhouse");
					pd_loader_down.setIndeterminate(false);
					pd_loader_down.setProgress(0);
					status.setStatus(false);
					return;
				}
			});
			return;
		} catch (UnknownHostException e1) {
			this.mHandler.post(new Runnable() {
				
				@Override
				public void run() {
					msg_down.setText("Impossibile contattare winkhouse");
					pd_loader_down.setIndeterminate(false);
					pd_loader_down.setProgress(0);
					status.setStatus(false);
				}
			});
			return;
		} catch (IOException e1) {
			this.mHandler.post(new Runnable() {
				
				@Override
				public void run() {
					msg_down.setText("Impossibile contattare winkhouse");
					pd_loader_down.setIndeterminate(false);
					pd_loader_down.setProgress(0);
					status.setStatus(false);					
				}
			});
			return;
		}
	    
	    ArrayList<Integer> myArList = new ArrayList<Integer>();
	    InputStream is = null;
		try {
			is = sock.getInputStream();
		} catch (IOException e1) {
			this.mHandler.post(new Runnable() {
				
				@Override
				public void run() {
					msg_down.setText("Impossibile contattare winkhouse");
					pd_loader_down.setIndeterminate(false);
					pd_loader_down.setProgress(0);
					status.setStatus(false);
				}
			});
		}
	    		
		try{
			while(!status.getStatus() && is != null && status.getFilename() == null){
				
			    
			    try {
			      
			      int tmp_read = is.read();

	              Thread.sleep(10);
	              this.mHandler.post(new Runnable() {
					
					@Override
						public void run() {
							msg_down.setText("inizio ricezione dati");							
							
						}
	              });

	              
			      while(tmp_read != -1){
			    	 
			    	  myArList.add(tmp_read);
			    	  tmp_read = is.read();
			    	  count ++;
		              this.mHandler.post(new Runnable() {
							
							@Override
								public void run() {
									msg_down.setText(count / 1024 + " kB");
									
								}
			              });
		              
			      }
			      
			      String filenumber = getFileNumber().toString();
			      fos = new FileOutputStream(Environment.getExternalStorageDirectory() + File.separator + 
			    		  					 "winkhouse/winkhouse_wireless_import_" + filenumber +".zip");
			      
			      bos = new BufferedOutputStream(fos);
			      
			      byte[] bytesToRead = convertIntegers(myArList);
			      this.mHandler.post(new Runnable() {
						
						@Override
							public void run() {
								msg_down.setText("Inizio salvataggio file");								
								
							}
		              });
			      bos.write(bytesToRead);
			      bos.flush();
			      status.setFilename("winkhouse_wireless_import_" + filenumber +".zip");
			      this.mHandler.post(new Runnable() {
						
						@Override
							public void run() {
								msg_down.setText("Fine salvataggio file");								
								pd_loader_down.setIndeterminate(false);
								pd_loader_down.setProgress(0);
								status.setStatus(true);
								
							}
		              });
			      
			      
			    } catch (NumberFormatException e) {
					this.mHandler.post(new Runnable() {
						
						@Override
						public void run() {
							msg_down.setText("Impossibile completare il download da winkhouse");
							pd_loader_down.setIndeterminate(false);
							pd_loader_down.setProgress(0);
							status.setStatus(false);
						}
					});
    	
			    } catch (UnknownHostException e) {
					this.mHandler.post(new Runnable() {
						
						@Override
						public void run() {
							msg_down.setText("Impossibile completare il download da winkhouse");
							pd_loader_down.setIndeterminate(false);
							pd_loader_down.setProgress(0);
							status.setStatus(false);
						}
					});
				} catch (IOException e) {
					this.mHandler.post(new Runnable() {
						
						@Override
						public void run() {
							msg_down.setText("Impossibile completare il download da winkhouse");
							pd_loader_down.setIndeterminate(false);
							pd_loader_down.setProgress(0);
							status.setStatus(false);
						}
					});
					
				} catch (Exception e) {
					this.mHandler.post(new Runnable() {
						
						@Override
						public void run() {
							msg_down.setText("Impossibile completare il download da winkhouse");
							pd_loader_down.setIndeterminate(false);
							pd_loader_down.setProgress(0);
							status.setStatus(false);
						}
					});
					
				}
			    
			    finally {
			      if (fos != null)
					try {
						fos.close();
					} catch (IOException e) {
						Log.e("Error", e.getMessage());
					}
			      if (bos != null)
					try {
						bos.close();
					} catch (IOException e) {
						Log.e("Error", e.getMessage());
					}
			      if (sock != null)
					try {
						sock.close();
					} catch (IOException e) {
						Log.e("Error", e.getMessage());
					}
			    }				
			    	                        		            
	            	
	        }
							
        }catch(Exception e){
        	Log.e("Error", e.getMessage());
        }
	}
	
	protected Integer getFileNumber(){
		
		Integer num = 0;
		File f = new File(Environment.getExternalStorageDirectory() + File.separator + "winkhouse");
		File[] files = f.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].getName().endsWith(".zip") && files[i].getName().startsWith("winkhouse_wireless_import")){
				num++;
			}
		}
		
		return num;
		
	}
	
			
}
