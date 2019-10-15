package org.winkhouse.mwinkhouse.activity.stanze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.winkhouse.mwinkhouse.R;
import org.winkhouse.mwinkhouse.activity.adapters.CustomSpinnerAdapter;
import org.winkhouse.mwinkhouse.activity.adapters.ListaStanzeImmobiliAdapter;
import org.winkhouse.mwinkhouse.activity.listeners.WinkTextWatcher;
import org.winkhouse.mwinkhouse.helpers.DataBaseHelper;
import org.winkhouse.mwinkhouse.models.ImmobiliVO;
import org.winkhouse.mwinkhouse.models.StanzeImmobiliVO;
import org.winkhouse.mwinkhouse.models.TipologiaStanzeVO;
import org.winkhouse.mwinkhouse.models.columns.ImmobiliColumnNames;
import org.winkhouse.mwinkhouse.util.ActivityMessages;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class ListaStanzeActivity extends AppCompatActivity {

	private DisplayMetrics dm = null;
	private ListView listView = null;
	private ArrayList<StanzeImmobiliVO> list = null;
	private Integer codImmobile = null;
	private ListaStanzeImmobiliAdapter adapter = null;
	private Dialog dialog = null;
	private HashMap<Integer,Object> selected = new HashMap<Integer,Object>();
	private ArrayList<TipologiaStanzeVO> al_tipistanze = null;	
	private Spinner sp_tipi_stanze = null;
	private EditText et_mq = null; 

	public ListaStanzeActivity() {}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		 dm = new DisplayMetrics();
 		 getWindowManager().getDefaultDisplay().getMetrics(dm);
		 overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);
		 
//		 ActionBar ab = getActionBar();
//		 ab.setHomeButtonEnabled(true);
		 
         setContentView(R.layout.lista_stanze);
         
	     listView = findViewById(R.id.listViewStanzeImmobile);
	     listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	     list = getData();

	     adapter = new ListaStanzeImmobiliAdapter(ListaStanzeActivity.this, 
				    							  R.layout.dettaglio_lista_stanze, 
				    							  list);
	     listView.setAdapter(adapter);	
	     

	}
	
	protected ArrayList getData(){

	    HashMap columns_immobile = new HashMap();
	    columns_immobile.put(ImmobiliColumnNames.CODIMMOBILE, null);
	    columns_immobile.put(ImmobiliColumnNames.INDIRIZZO, null);

		list = new ArrayList<StanzeImmobiliVO>();
		
		if (getIntent().getExtras() != null){
			codImmobile = getIntent().getExtras().getInt(ActivityMessages.CODIMMOBILE_PROPIETARI_ACTION);
		}   
	    
	    DataBaseHelper dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.READ_DB);
		ImmobiliVO immobile = dbh.getImmobileById(codImmobile, columns_immobile);
		ListaStanzeActivity.this.setTitle(immobile.getIndirizzo());
	     			 
	    ArrayList<StanzeImmobiliVO> ivos = dbh.getStanzeImmobile(null, codImmobile);
	    
	    dbh.getSqllitedb().close();
	    
        Iterator<StanzeImmobiliVO> it = ivos.iterator();
	     while(it.hasNext()){
	    	 list.add(it.next());
	     }
	     
	     dbh.getSqllitedb().close();
	     
		return list;
				
	}
	
	Runnable run = new Runnable(){
	     public void run(){
	    	list.clear();
	 		list = getData();
	 		adapter.clear();
	 		for (StanzeImmobiliVO stanza : list) {
	 			adapter.add(stanza);	
	 		}		
	 		adapter.notifyDataSetChanged();
	 		listView.setChoiceMode(ListView.CHOICE_MODE_NONE);
	 		listView.setAdapter(adapter);
	 		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	     }
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    getMenuInflater().inflate(R.menu.menu_lista_contatti, menu);
	    return true;
	}

	@Override
	public void onBackPressed() {
		finish();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		DataBaseHelper dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.NONE_DB);
		
		int itemId = item.getItemId();
		
		if (itemId == android.R.id.home){
			NavUtils.navigateUpFromSameTask(this);
			
		}else if (itemId == R.id.nuovocontatto) {
			StanzeImmobiliVO stanza = new StanzeImmobiliVO();
			stanza.setCodImmobile(codImmobile);
			openDettaglioStanzaPopup(ListaStanzeActivity.this,stanza);
			
		} else if (itemId == R.id.cancellacontatto) {
			dbh.deleteStanza(selected.keySet().toArray(new Integer[selected.keySet().size()]));
			refreshData();
		}
		
		dbh.close();
		
		return true;

	}

	public void refreshData(){
		ListaStanzeActivity.this.runOnUiThread(run);
	}
	
	@Override
	protected void onPostResume() {
		super.onPostResume();
		refreshData();
	}

	public void onCheckboxClicked(View view) {

	    boolean checked = ((CheckBox) view).isChecked();
	    
	    Integer codstanzaimmobile = (Integer)view.getTag();
	    
	    int id = view.getId();
		if (id == R.id.chk_item) {
			if (checked){
				selected.put(codstanzaimmobile, null);
			}else{
				selected.remove(codstanzaimmobile);
			}
		}
	}
	
	public void openDettaglioStanzaPopup(Context c, StanzeImmobiliVO stanza){
		
		dialog = new Dialog(c);		
		
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_nuova_stanza);
		
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
	    lp.copyFrom(dialog.getWindow().getAttributes());
	    lp.width = WindowManager.LayoutParams.WRAP_CONTENT;	
	    LinearLayout ll = dialog.findViewById(R.id.dialog_stanza_layout);
	    ll.getLayoutParams().width = (dm.widthPixels - (dm.widthPixels/10 * 2));

		sp_tipi_stanze = dialog.findViewById(R.id.cmb_tipistanze);
		sp_tipi_stanze.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus){
					sp_tipi_stanze.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
				}else{
					sp_tipi_stanze.getBackground().setColorFilter(Color.parseColor("#f1ebeb"), PorterDuff.Mode.SRC_IN);
				}
				
			}
		});
		
		DataBaseHelper dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.NONE_DB);		
		
		 
		ArrayList<TipologiaStanzeVO> tmp3 = dbh.getAllTipiStanze(null);		
		
		al_tipistanze = new ArrayList<TipologiaStanzeVO>();
		al_tipistanze.add(new TipologiaStanzeVO());
		al_tipistanze.addAll(tmp3);
		
		CustomSpinnerAdapter icsa3 = new CustomSpinnerAdapter(ListaStanzeActivity.this, 
															  R.layout.custom_combo_detail, 
															  al_tipistanze.toArray());
		
		sp_tipi_stanze.setAdapter(icsa3);

		sp_tipi_stanze.setSelection(get_al_tipistanze_index(stanza));
		sp_tipi_stanze.setOnItemSelectedListener(new TipiStanzeComboSelectedListener(stanza));
		sp_tipi_stanze.setPopupBackgroundResource(R.drawable.spinner_bckground);
		
		et_mq = dialog.findViewById(R.id.mq);
		et_mq.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus){
					et_mq.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
				}else{
					et_mq.getBackground().setColorFilter(Color.parseColor("#f1ebeb"), PorterDuff.Mode.SRC_IN);
				}
				
			}
		});
		
		et_mq.setText(String.valueOf(stanza.getMq()));
		
		WinkTextWatcher wtw_contatto = new WinkTextWatcher(stanza, "setMq", Integer.class);
		et_mq.addTextChangedListener(wtw_contatto);

		ImageButton salva = dialog.findViewById(R.id.btn_salva);
		salva.setOnClickListener(new StanzaClickListener(stanza));
		
		ImageButton chiudi = dialog.findViewById(R.id.btn_chiudi);
		chiudi.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
//				Intent lista_stanze = new Intent(v.getContext(), ListaStanzeActivity.class);
//				lista_stanze.putExtra(StanzeImmobiliColumnNames.CODIMMOBILE, codImmobile);
//				v.getContext().startActivity(lista_stanze);
				refreshData();
				dialog.dismiss();
				
			}
		});
		
		dbh.close();
		
		dialog.show();
		dialog.getWindow().setAttributes(lp);
	}

	protected int get_al_tipistanze_index(StanzeImmobiliVO c){
		int count = 0;
		for (TipologiaStanzeVO tipistanze : al_tipistanze) {
			if (tipistanze.getCodTipologiaStanza() == c.getCodTipologiaStanza()){
				return count;
			}else{
				count++;
			}
		}
		return 0;
	}
	
	class TipiStanzeComboSelectedListener implements OnItemSelectedListener{
		
		private StanzeImmobiliVO c = null;
		
		public TipiStanzeComboSelectedListener(StanzeImmobiliVO c){
			this.c = c;
		}

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			TipologiaStanzeVO tcvo = (TipologiaStanzeVO)arg0.getItemAtPosition(arg2);
			c.setCodTipologiaStanza(tcvo.getCodTipologiaStanza());
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}

	}
	
	class StanzaClickListener implements OnClickListener{

		private StanzeImmobiliVO c = null;
		
		public StanzaClickListener(StanzeImmobiliVO c){
			this.c = c;
		}

		@Override
		public void onClick(View v) {
			
			DataBaseHelper dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.NONE_DB);
			
			if (!dbh.saveStanza(c)){
				Toast.makeText(getApplicationContext(), "Salvataggio stanza non avvenuto", Toast.LENGTH_SHORT).show();								 		        
			}
			
			dbh.close();
			dialog.dismiss();								
			
			refreshData();
			
		}
		
	}

	public void onStanzaClicked(View view){
	    Integer codstanza = (Integer)view.getTag();
	    
	    DataBaseHelper dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.NONE_DB);
	    
		StanzeImmobiliVO cvo = dbh.getStanzaById(codstanza, null);
		dbh.close();
		openDettaglioStanzaPopup(ListaStanzeActivity.this,cvo);
	}


	
}
