package org.winkhouse.mwinkhouse.activity;

import org.winkhouse.mwinkhouse.R;
import org.winkhouse.mwinkhouse.activity.anagrafiche.ListaAnagraficheActivity;
import org.winkhouse.mwinkhouse.activity.datibase.DatiBaseActivity;
import org.winkhouse.mwinkhouse.activity.immobili.ListaImmobiliActivity;
import org.winkhouse.mwinkhouse.activity.settings.SettingsActivity;
import org.winkhouse.mwinkhouse.helpers.DataBaseHelper;
import org.winkhouse.mwinkhouse.util.ActivityMessages;
import org.winkhouse.mwinkhouse.util.SDFileSystemUtils;
import org.winkhouse.mwinkhouse.util.SystemUiHider;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.api.services.drive.Drive;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class StartUpActivity extends AppCompatActivity{
	/**
	 * Whether or not the system UI should be auto-hidden after
	 * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
	 */
	//private static final boolean AUTO_HIDE = true;

	/**
	 * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
	 * user interaction before hiding the system UI.
	 */
	//private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

	/**
	 * If set, will toggle the system UI visibility upon interaction. Otherwise,
	 * will show the system UI visibility upon interaction.
	 */
	//private static final boolean TOGGLE_ON_CLICK = true;

	/**
	 * The flags to pass to {@link SystemUiHider#getInstance}.
	 */
	//private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

	/**
	 * The instance of the {@link SystemUiHider} for this activity.
	 */
	private SystemUiHider mSystemUiHider;
	
	private final Dialog dialog = null;
	
	private final static String REGEX_IPADDRES = "\\b(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b";
	
	private final EditText ipWinkhouse = null;
	private final EditText portaWinkhouse = null;
	
	private static final Animation animazione_rotazione_in = null;
	private static Animation animazione_rotazione_out = null;
	
	private static final int anim_type = 4;
	
	public final static int TYPE_IMPORT = 0;
	public final static int TYPE_EXPORT = 1;
	
	private ImageButton anagrafiche = null;
	private DisplayMetrics dm = null;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);

		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		DataBaseHelper dbh = new DataBaseHelper(this,DataBaseHelper.WRITE_DB);
		dbh.close();
		
		
//		switch (anim_type) {
//		
//		case 0: animazione_rotazione = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);
//				break;
//		case 1: animazione_rotazione = AnimationUtils.loadAnimation(this, R.anim.anim_fadeout);
//				break;
//		case 2: animazione_rotazione = AnimationUtils.loadAnimation(this, R.anim.anim_shake);
//				break;
//		case 3: animazione_rotazione = AnimationUtils.loadAnimation(this, R.anim.anim_zoomout);
//				break;
//		case 4: animazione_rotazione = AnimationUtils.loadAnimation(this, R.anim.anim_fadeout);
//		default:
//			break;
//		}
//		animazione_rotazione_in = AnimationUtils.loadAnimation(this, R.anim.anim_fadein);
		animazione_rotazione_out = AnimationUtils.loadAnimation(this, R.anim.activity_close_scale);
		overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);
		
//		ActionBar ab = getActionBar();
        
		SDFileSystemUtils SDfsu = new SDFileSystemUtils();
		SDfsu.createWinkFolder();
		
		setContentView(R.layout.activity_start_up);

		final View controlsView = findViewById(R.id.fullscreen_content_controls);
		final TextView contentView = findViewById(R.id.fullscreen_content);
		
		PackageInfo pinfo;
		try {
			pinfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
			String versionlabel = "WINKHOUSE\n v. "+ pinfo.versionName;
			contentView.setText(versionlabel);
		} catch (NameNotFoundException e) {
			contentView.setText("WINKHOUSE");			
		}
		
		
		class ListaAnagraficheRunner implements Runnable{
			
			private View v = null;
			
			public ListaAnagraficheRunner(View v){
				this.v = v;
			}
			
			@Override
			public void run() {
				Intent lista_anagrafiche = new Intent(v.getContext(), ListaAnagraficheActivity.class);
				lista_anagrafiche.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
				lista_anagrafiche.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);						
				v.getContext().startActivity(lista_anagrafiche);
				
			}

		}
		
		anagrafiche = findViewById(R.id.btn_anagrafiche);
		anagrafiche.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				v.startAnimation(animazione_rotazione_out);				
				v.postDelayed(new ListaAnagraficheRunner(v), 1200);
				
			}
			
		});
		
		
		class DatiBaseRunner implements Runnable{
			
			private View v = null;
			
			public DatiBaseRunner(View v){
				this.v = v;
			}
			
			@Override
			public void run() {
				Intent datibase = new Intent(v.getContext(), DatiBaseActivity.class);
				datibase.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
				datibase.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				v.getContext().startActivity(datibase);
				
			}

		}
								
		ImageButton base = findViewById(R.id.btn_base);
//		base.setAnimation(animazione_rotazione);
		base.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				v.startAnimation(animazione_rotazione_out);
				v.postDelayed(new DatiBaseRunner(v), 350);								
			}
			
		});		

		
		class SettingsRunner implements Runnable{
			
			private View v = null;
			
			public SettingsRunner(View v){
				this.v = v;
			}
			
			@Override
			public void run() {
				Intent settings = new Intent(v.getContext(), SettingsActivity.class);
				settings.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
				settings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				v.getContext().startActivity(settings);
				
			}

		}
		
		ImageButton settings = findViewById(R.id.btn_settings);
		settings.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				v.startAnimation(animazione_rotazione_out);
				v.postDelayed(new SettingsRunner(v), 350);								
			}
			
		});		
		
		//findViewById(R.id.btn_immobili).setOnTouchListener(mDelayHideTouchListener);
		
		class ListaImmobiliRunner implements Runnable{
			
			private View v = null;
			
			public ListaImmobiliRunner(View v){
				this.v = v;
			}
			
			@Override
			public void run() {
				Intent lista_immobili = new Intent(v.getContext(), ListaImmobiliActivity.class);
				lista_immobili.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
				lista_immobili.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				v.getContext().startActivity(lista_immobili);				
				
			}

		}
		
		
		ImageButton immobili = findViewById(R.id.btn_immobili);
//		immobili.setAnimation(animazione_rotazione);
		immobili.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				v.startAnimation(animazione_rotazione_out);
				v.postDelayed(new ListaImmobiliRunner(v), 1000);				
			}
			
		});

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int itemId = item.getItemId();
//		if (itemId == R.id.ImportsManager) {
//
//			Intent importsmanageractivity = new Intent(StartUpActivity.this, ImportsManager.class);
//			importsmanageractivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			importsmanageractivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			startActivity(importsmanageractivity);
//
//		} else if (itemId == R.id.ImportaWireless) {
//
//			Intent importactivity = new Intent(StartUpActivity.this, ImportActivity.class);
//			importactivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			importactivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			importactivity.putExtra(ImportActivity.ACTIVITY_TYPE, ImportActivity.TYPE_IMPORT);
//			startActivity(importactivity);
//
//		} else
        if (itemId == R.id.ImportaZip) {
			Intent importactivity = new Intent(StartUpActivity.this, ImportActivity.class);
			importactivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			importactivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			importactivity.putExtra(ActivityMessages.ACTIVITY_TYPE, ActivityMessages.IMPORT_ACTION);
			startActivity(importactivity);				
			
		}

		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    getMenuInflater().inflate(R.menu.menu, menu);
	    return super.onCreateOptionsMenu(menu);	    
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
	}

	@Override
	public void onBackPressed() {		
		this.finish();
		Process.killProcess(Process.myPid()); 
	}

	@Override
	protected void onPause() {
		super.onPause();
		overridePendingTransition(R.anim.activity_open_scale,R.anim.activity_close_translate);
	}

	@Override
	protected void onPostResume() {
		super.onPostResume();		
	}

	
}
