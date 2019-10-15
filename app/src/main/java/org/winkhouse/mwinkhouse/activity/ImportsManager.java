package org.winkhouse.mwinkhouse.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.winkhouse.mwinkhouse.R;
import org.winkhouse.mwinkhouse.activity.adapters.ImportsManagerAdapter;
import org.winkhouse.mwinkhouse.helpers.ImportDataWirelessThread;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ImportsManager extends Activity {
	
	private DisplayMetrics dm = null;
	private ListView listView = null;
	private ArrayList<File> list = null;
	private ImportsManagerAdapter adapter = null;
	private ArrayList<File> selected = null;
	private ImageButton btn_importa = null;
	private ImageButton btn_cancella = null;
	private ProgressBar top_progress = null;
	private TextView top_text = null;
	Animation animazione_rotazione_out = null;
	
	public ImportsManager() {
	}
	
	Runnable run = new Runnable(){
	     public void run(){
	    	list.clear();
	 		list = getData();
	 		adapter.clear();
	 		for (File file : list) {
	 			adapter.add(file);	
	 		}		
	 		adapter.notifyDataSetChanged();
	     }
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.imports_manager_activity);
		animazione_rotazione_out = AnimationUtils.loadAnimation(this, R.anim.activity_close_scale);
        dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		 
        overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);
        
//        ActionBar ab = getActionBar();
//		ab.setDisplayHomeAsUpEnabled(true);
        
	    listView = (ListView)findViewById(R.id.listaImports);
	    listView.setChoiceMode(2);
	    list = getData();
	    
	    top_progress = (ProgressBar)findViewById(R.id.top_progress);;
	    top_text = (TextView)findViewById(R.id.top_text);
	    		
	    btn_importa = (ImageButton)findViewById(R.id.btn_importa);
	    btn_importa.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				v.startAnimation(animazione_rotazione_out);
				if (selected.size() != 1){
					Toast.makeText(ImportsManager.this, "Selezionare un solo file", Toast.LENGTH_SHORT).show();
				}else{
					ImportActivity ia = new ImportActivity();
					ImportActivity.ThreadSincro ts = ia.new ThreadSincro(true,selected.get(0).getName());
		    		ImportDataWirelessThread idwt = new ImportDataWirelessThread(ImportsManager.this,top_progress,top_text,ts);
					idwt.start();

				}
				
			}
			
		});
	    
	    btn_cancella = (ImageButton)findViewById(R.id.btn_cancella);
	    btn_cancella.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				v.startAnimation(animazione_rotazione_out);
				if (selected.size() == 0){
					Toast.makeText(ImportsManager.this, "Selezionare almeno un file", Toast.LENGTH_SHORT).show();
				}else{
					for (File sel : selected) {
						sel.delete();
					} 
					refreshData();
				}
				
			}
			
		});

	    adapter = new ImportsManagerAdapter(ImportsManager.this, 
	    								    R.layout.dettaglio_lista_imports, 
	    		 							list);
	    listView.setAdapter(adapter);			
		
	}

	protected ArrayList<File> getData(){
		
		selected = new ArrayList<File>();
		list = new ArrayList<File>();
		
		String importDirectory = Environment.getExternalStorageDirectory() + File.separator + "winkhouse";
		
		File f = new File(importDirectory);
		File[] files =  f.listFiles();
		
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()){
				if (files[i].getName().endsWith(".zip") && files[i].getName().startsWith("winkhouse_wireless_import")){
					list.add(files[i]);					
				}
			}
		} 		
	    
		Collections.sort(list, new Comparator<File>() {

			@Override
			public int compare(File lhs, File rhs) {
				if (lhs.lastModified() > rhs.lastModified()){
					return -1;
				}
				if (lhs.lastModified() < rhs.lastModified()){
					return 1;
				}
				return 0;
			}
			
		});
		
		return list;
	}
	
	public void refreshData(){
		ImportsManager.this.runOnUiThread(run);
	}
	
	public void onCheckboxClicked(View view) {

	    boolean checked = ((CheckBox) view).isChecked();
	    
	    File f = (File)view.getTag();
	    boolean find = false;
	    int id = view.getId();
	    
		if (id == R.id.chk_item) {
			
			for (File sel:selected) {
				if (sel == f){
					find = true;
					break;
				}
				
			}
			if (checked){
				if (!find){
					selected.add(f);
				}
			}else{				
				selected.remove(f);
			}						
			
		}
	}
}
