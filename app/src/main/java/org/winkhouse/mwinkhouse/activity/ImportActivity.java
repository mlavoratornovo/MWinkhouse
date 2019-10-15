package org.winkhouse.mwinkhouse.activity;

import org.winkhouse.mwinkhouse.R;
import org.winkhouse.mwinkhouse.activity.listeners.WinkTextWatcher;
import org.winkhouse.mwinkhouse.helpers.DataBaseHelper;
import org.winkhouse.mwinkhouse.helpers.DownloadDataWirelessThread;
import org.winkhouse.mwinkhouse.helpers.ExportDataWirelessThread;
import org.winkhouse.mwinkhouse.helpers.ImportDataWirelessThread;
import org.winkhouse.mwinkhouse.helpers.UploadDataWirelessThread;
import org.winkhouse.mwinkhouse.models.SysSettingVO;
import org.winkhouse.mwinkhouse.util.SysSettingNames;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ImportActivity extends Activity {

	private DisplayMetrics dm = null;
	private final static String REGEX_IPADDRES = "\\b(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b";
	
	private EditText ipWinkhouse = null;
	private EditText portaWinkhouse = null;
	
	private SysSettingVO ssWinkhouseIp = null;
	private SysSettingVO ssWinkhousePort = null;

	
	private TextView top_text = null;
	private TextView bottom_text = null;
	private TextView top_label = null;
	private TextView bottom_label = null;
	private ImageButton salva = null; 
	private ProgressBar top_progress = null;
	private ProgressBar bottom_progress = null;
	private AlertDialog.Builder alertDialog = null;
	
	public final static int TYPE_IMPORT = 0;
	public final static int TYPE_EXPORT = 1;
	public final static String ACTIVITY_TYPE = "Atype";
	
	private int current_type = -1;
	
	Animation animazione_rotazione_out = null;
	
	public ImportActivity() {
		
	}
	
	public class ThreadSincro{
		
		private Boolean status = null;
		private String filename = null;
		
		public ThreadSincro(Boolean status,String filename){
			this.status = status;
			this.filename = filename;
		}
		
		public synchronized Boolean getStatus(){
			return this.status;
		}
		
		public synchronized void setStatus(Boolean status){
			this.status = status;
		}

		public synchronized String getFilename() {
			return filename;
		}

		public synchronized void setFilename(String filename) {
			this.filename = filename;
		}
		
		
	}
	
	class SalvaExportListener extends SalvaImportListener implements OnClickListener{
				
		
		public SalvaExportListener(SysSettingVO winkhouseip,
				SysSettingVO winkhouseport) {
			super(winkhouseip, winkhouseport);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onClick(View arg0) {
			
			DataBaseHelper dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.NONE_DB);
			
			if(!ssWinkhouseIp.getSettingValue().matches(REGEX_IPADDRES)) {
				Toast.makeText(ImportActivity.this, "Inserire un ip valido", Toast.LENGTH_SHORT).show();
			}else{
				if (checkPortNumber(ssWinkhousePort.getSettingValue())){
					if (dbh.saveSysSetting(ssWinkhouseIp)){
						
						if (dbh.saveSysSetting(ssWinkhousePort)){
//							try{
								android.net.wifi.WifiManager m = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
								android.net.wifi.SupplicantState s = m.getConnectionInfo().getSupplicantState();
								NetworkInfo.DetailedState state = WifiInfo.getDetailedStateOf(s);
								
								if( state != NetworkInfo.DetailedState.CONNECTED ){
									m.setWifiEnabled(true);
//									Thread.sleep(2000);
								}
																	
//								Socket sock = new Socket(ssWinkhouseIp.getSettingValue(), Integer.valueOf(ssWinkhousePort.getSettingValue()));
							
								ThreadSincro ts = new ThreadSincro(false,null);
				    		
								ExportDataWirelessThread edwt = new ExportDataWirelessThread(ImportActivity.this,top_progress,top_text,ts);
								edwt.start();
							
								UploadDataWirelessThread udwt = new UploadDataWirelessThread(ImportActivity.this,bottom_progress,bottom_text,ts);
								udwt.start();
								
//							}catch(Exception e){
//								Toast.makeText(ImportActivity.this, "Impossibile contattare winkhouse", Toast.LENGTH_SHORT).show();
//							}

		
						}else{
							Toast.makeText(ImportActivity.this, "Errore durante il salvataggio della porta", Toast.LENGTH_SHORT).show();
						}
					}else{
						Toast.makeText(ImportActivity.this, "Numero di porta non valido", Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.makeText(ImportActivity.this, "Errore durante il salvataggio del'ip", Toast.LENGTH_SHORT).show();
				}
				
			}
			    		    		
		}
		
		
		
	}
	
	
	class SalvaImportListener implements OnClickListener{
		
		SysSettingVO ssWinkhouseIp = null;
		SysSettingVO ssWinkhousePort = null;
		
		private int type_call		= 0;
		
		public SalvaImportListener(SysSettingVO winkhouseip, SysSettingVO winkhouseport){
			ssWinkhouseIp = winkhouseip;
			ssWinkhousePort = winkhouseport;			
		}
		
		protected boolean checkPortNumber(String portNumber){
			try {
				if (Integer.valueOf(portNumber) > 0 && Integer.valueOf(portNumber) <= 65535){
					return true;
				}else{
					return false;
				}
			} catch (NumberFormatException e) {
				return false;
			}
		}
		
		@Override
		public void onClick(View arg0) {
			arg0.startAnimation(animazione_rotazione_out);
			DataBaseHelper dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.NONE_DB);
			
			if(!ssWinkhouseIp.getSettingValue().matches(REGEX_IPADDRES)) {
				Toast.makeText(ImportActivity.this, "Inserire un ip valido", Toast.LENGTH_SHORT).show();
			}else{
				if (checkPortNumber(ssWinkhousePort.getSettingValue())){
					if (dbh.saveSysSetting(ssWinkhouseIp)){
						
						if (dbh.saveSysSetting(ssWinkhousePort)){
							try{
//								Socket sock = new Socket(ssWinkhouseIp.getSettingValue(), Integer.valueOf(ssWinkhousePort.getSettingValue()));
								alertDialog = new AlertDialog.Builder(arg0.getContext(),AlertDialog.THEME_HOLO_DARK);
				 		        alertDialog.setTitle("Importazione dati da winkhouse");	 
				 		        alertDialog.setMessage("L'importazione sostituisce tutti i dati in archivio, procedere ?");
				 		        alertDialog.setIcon(R.drawable.adept_commit);
				 		        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {

										android.net.wifi.WifiManager m = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
										android.net.wifi.SupplicantState s = m.getConnectionInfo().getSupplicantState();
										NetworkInfo.DetailedState state = WifiInfo.getDetailedStateOf(s);
										
										if( state != NetworkInfo.DetailedState.CONNECTED ){
											m.setWifiEnabled(true);
											try {
												Thread.sleep(2000);
											} catch (InterruptedException e) {
												Log.e("WINK",e.getMessage());
											}
										}
										
										ThreadSincro ts = new ThreadSincro(false,null);
										
										DownloadDataWirelessThread ddwt = new DownloadDataWirelessThread(ImportActivity.this,top_progress,top_text,ts);
										ddwt.start();
										
							    		ImportDataWirelessThread idwt = new ImportDataWirelessThread(ImportActivity.this,bottom_progress,bottom_text,ts);
										idwt.start();
										
									}
								});
				 		       alertDialog.setNegativeButton("Annulla", null);
				 		       alertDialog.show();

							}catch(Exception e){
								Log.d("WINKHOUSE", e.toString());
								Toast.makeText(ImportActivity.this, "Impossibile contattare winkhouse", Toast.LENGTH_SHORT).show();
							}
				 		    
							
		
						}else{
							Toast.makeText(ImportActivity.this, "Errore durante il salvataggio della porta", Toast.LENGTH_SHORT).show();
						}
					}else{
						Toast.makeText(ImportActivity.this, "Numero di porta non valido", Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.makeText(ImportActivity.this, "Errore durante il salvataggio del'ip", Toast.LENGTH_SHORT).show();
				}
				
			}
			    		    		
		}
		
		
		
	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		 super.onCreate(savedInstanceState);
		 
		 animazione_rotazione_out = AnimationUtils.loadAnimation(this, R.anim.activity_close_scale);
		 
		 dm = new DisplayMetrics();
 		 getWindowManager().getDefaultDisplay().getMetrics(dm);
		 overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);
		 
//		 ActionBar ab = getActionBar();
//		 ab.setHomeButtonEnabled(true);
		 
         setContentView(R.layout.import_activity);
         
         top_label = (TextView)findViewById(R.id.top_label);
         bottom_label = (TextView)findViewById(R.id.bottom_label);
         
         
 		DataBaseHelper dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.NONE_DB);
		
 		SysSettingVO ssWinkhouseIp = dbh.getSysSettingByName(SysSettingNames.WINKHOUSE_IP, null);
 		SysSettingVO ssWinkhousePort = dbh.getSysSettingByName(SysSettingNames.WINKHOUSE_PORT, null);
 		
 		ssWinkhouseIp = (ssWinkhouseIp == null)?new SysSettingVO():ssWinkhouseIp;
 		ssWinkhouseIp.setSettingName(SysSettingNames.WINKHOUSE_IP);
 		ssWinkhousePort = (ssWinkhousePort == null)?new SysSettingVO():ssWinkhousePort;
 		ssWinkhousePort.setSettingName(SysSettingNames.WINKHOUSE_PORT);
 			  
 		ipWinkhouse = (EditText)findViewById(R.id.ipWinkhouse);
 		ipWinkhouse.setMaxWidth(300);
 		ipWinkhouse.setText(ssWinkhouseIp.getSettingValue());
 		ipWinkhouse.setOnFocusChangeListener(new OnFocusChangeListener() {
 			
 			@Override
 			public void onFocusChange(View v, boolean hasFocus) {
 				if (hasFocus){
 					ipWinkhouse.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
 				}else{
 					ipWinkhouse.getBackground().setColorFilter(Color.parseColor("#f1ebeb"), PorterDuff.Mode.SRC_IN);
 				}
 				
 			}
 		});

 		WinkTextWatcher wtw_ssWinkhouseIp = new WinkTextWatcher(ssWinkhouseIp, "setSettingValue", String.class);
 		ipWinkhouse.addTextChangedListener(wtw_ssWinkhouseIp);		
 		
 		portaWinkhouse = (EditText)findViewById(R.id.porta);
 		portaWinkhouse.setText(ssWinkhousePort.getSettingValue());
 		WinkTextWatcher wtw_ssWinkhousePort = new WinkTextWatcher(ssWinkhousePort, "setSettingValue", String.class);
 		portaWinkhouse.addTextChangedListener(wtw_ssWinkhousePort);
 		portaWinkhouse.setOnFocusChangeListener(new OnFocusChangeListener() {
 			
 			@Override
 			public void onFocusChange(View v, boolean hasFocus) {
 				if (hasFocus){
 					portaWinkhouse.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
 				}else{
 					portaWinkhouse.getBackground().setColorFilter(Color.parseColor("#f1ebeb"), PorterDuff.Mode.SRC_IN);
 				}
 				
 			}
 		});
 		
 		salva = (ImageButton)findViewById(R.id.btn_salva);
 		if (current_type == TYPE_IMPORT){
 			salva.setOnClickListener(new SalvaImportListener(ssWinkhouseIp, ssWinkhousePort));
 		}else{
 			salva.setOnClickListener(new SalvaExportListener(ssWinkhouseIp, ssWinkhousePort));
 		}
         
  		 ImageButton chiudi = (ImageButton)findViewById(R.id.btn_chiudi);
 		 chiudi.setOnClickListener(new OnClickListener() {
 			
 			@Override
 			public void onClick(View v) {
 				v.startAnimation(animazione_rotazione_out);
 				finish();
 				
 			}
 		});
 		 
 		top_progress = (ProgressBar)findViewById(R.id.top_progress);
 		top_text = (TextView)findViewById(R.id.top_text); 		
 		bottom_progress = (ProgressBar)findViewById(R.id.bottom_progress);
 		bottom_text = (TextView)findViewById(R.id.bottom_text);
		 
	}


	@Override
	protected void onStart() {
		
		super.onStart();
		
		Integer activityType = getIntent().getIntExtra(ACTIVITY_TYPE, -1);
		current_type = activityType;
 		DataBaseHelper dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.NONE_DB);
		
 		ssWinkhouseIp = dbh.getSysSettingByName(SysSettingNames.WINKHOUSE_IP, null);
 		ssWinkhousePort = dbh.getSysSettingByName(SysSettingNames.WINKHOUSE_PORT, null);		
 		ssWinkhouseIp = (ssWinkhouseIp == null)?new SysSettingVO():ssWinkhouseIp;
 		ssWinkhouseIp.setSettingName(SysSettingNames.WINKHOUSE_IP);
 		WinkTextWatcher wtw_ssWinkhouseIp = new WinkTextWatcher(ssWinkhouseIp, "setSettingValue", String.class);
 		ipWinkhouse.addTextChangedListener(wtw_ssWinkhouseIp);		

 		ssWinkhousePort = (ssWinkhousePort == null)?new SysSettingVO():ssWinkhousePort;
 		ssWinkhousePort.setSettingName(SysSettingNames.WINKHOUSE_PORT);
 		WinkTextWatcher wtw_ssWinkhousePort = new WinkTextWatcher(ssWinkhousePort, "setSettingValue", String.class);
 		portaWinkhouse.addTextChangedListener(wtw_ssWinkhousePort);

		if (activityType == TYPE_IMPORT){
	        top_label.setText("Avanzamento download file");
	        bottom_label.setText("Avanzamento importazione dati");	
 			salva.setOnClickListener(new SalvaImportListener(ssWinkhouseIp, ssWinkhousePort));
 			setTitle("Importazione");
	        
		}else{
	        top_label.setText("Avanzamento esportazione dati");
	        bottom_label.setText("Avanzamento upload file");
	        salva.setOnClickListener(new SalvaExportListener(ssWinkhouseIp, ssWinkhousePort));
	        setTitle("Esportazione");
		}

	}

}
