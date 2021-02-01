package org.winkhouse.mwinkhouse.activity.contatti;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.winkhouse.mwinkhouse.activity.adapters.CustomSpinnerAdapter;
import org.winkhouse.mwinkhouse.activity.adapters.ListaContattiAdapter;
import org.winkhouse.mwinkhouse.activity.listeners.WinkTextWatcher;
import org.winkhouse.mwinkhouse.helpers.DataBaseHelper;
import org.winkhouse.mwinkhouse.models.ContattiVO;
import org.winkhouse.mwinkhouse.models.TipologiaContattiVO;
import org.winkhouse.mwinkhouse.models.columns.AnagraficheColumnNames;
import org.winkhouse.mwinkhouse.models.columns.ContattiColumnNames;

import org.winkhouse.mwinkhouse.R;
import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
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
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.Spinner;
import android.widget.Toast;

public class ListaContattiActivity extends AppCompatActivity {

	private ListView listView = null;
	private ListaContattiAdapter adapter = null;
	private ArrayList<ContattiVO> list = null;
	private ArrayList<TipologiaContattiVO> al_tipicontatti = null;
	
	private Spinner sp_tipi_contatti = null;
	private EditText et_contatto = null; 
	private Dialog dialog = null;
	
	private int cod_anagrafica = 0;
	
	private final HashMap<Integer,Object> selected = new HashMap<Integer,Object>();
	private DisplayMetrics dm = null;
	
	
	Runnable run = new Runnable(){
	     public void run(){
	    	list.clear();
	 		list = getData();
	 		adapter.clear();
	 		for (ContattiVO contattiVO : list) {
	 			adapter.add(contattiVO);	
	 		}		
	 		adapter.notifyDataSetChanged();
	 		listView.setChoiceMode(ListView.CHOICE_MODE_NONE);
	 		listView.setAdapter(adapter);
	 		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

	     }
	};

	public ListaContattiActivity() {
		
	}
	
	protected ArrayList<ContattiVO> getData(){
		
		list = new ArrayList<ContattiVO>();
		
		
		if (getIntent().getExtras() != null){
			cod_anagrafica = getIntent().getExtras().getInt(AnagraficheColumnNames.CODANAGRAFICA);
//			getIntent().getExtras().remove(AnagraficheColumnNames.CODANAGRAFICA);
		}   
	    
	    DataBaseHelper dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.READ_DB);
	     			 
	    ArrayList<ContattiVO> ivos = dbh.getContattiAnagrafica(cod_anagrafica, null);
	    
	    dbh.getSqllitedb().close();
	    
        Iterator<ContattiVO> it = ivos.iterator();
	     while(it.hasNext()){
	    	 list.add(it.next());
	     }
	     
	     dbh.getSqllitedb().close();
	     
		return list;
	}
	
	public void refreshData(){
		ListaContattiActivity.this.runOnUiThread(run);
	}
	
	@Override
	protected void onPostResume() {
		super.onPostResume();
		refreshData();
	}

	public void onCheckboxClicked(View view) {

	    boolean checked = ((CheckBox) view).isChecked();
	    
	    Integer codcontatto = (Integer)view.getTag();
	    
	    int id = view.getId();
		if (id == R.id.chk_item) {
			if (checked){
				selected.put(codcontatto, null);
			}else{
				selected.remove(codcontatto);
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		 super.onCreate(savedInstanceState);		 
		 
         setContentView(R.layout.lista_contatti);
         
         dm = new DisplayMetrics();
 		 getWindowManager().getDefaultDisplay().getMetrics(dm);
 		 
         overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);
         
//         ActionBar ab = getActionBar();
// 		 ab.setDisplayHomeAsUpEnabled(true);
         
	     listView = findViewById(R.id.listViewContatti);
	     listView.setChoiceMode(2);
	     list = getData();
	     
	     adapter = new ListaContattiAdapter(ListaContattiActivity.this, 
	    								    R.layout.dettaglio_lista_contatti, 
	    		 							list);
	     listView.setAdapter(adapter);	
		
	
	}
	
	class MyItemClickListener implements OnMenuItemClickListener{
		
		private ContattiVO cvo = null;
		
		public MyItemClickListener(ContattiVO cvo){
			this.cvo = cvo;
		}
        @Override
        public boolean onMenuItemClick(MenuItem item) {
        	if (item.getTitle().toString().equalsIgnoreCase("Telefonata")){
				String uri = "tel:" + cvo.getContatto().trim() ;
				Intent intent = new Intent(Intent.ACTION_DIAL);
				intent.setData(Uri.parse(uri));
				startActivity(intent);	                    		
        	}
        	if (item.getTitle().toString().equalsIgnoreCase("SMS")){
        		Intent sendIntent = new Intent(Intent.ACTION_VIEW);         
        		sendIntent.setData(Uri.parse("sms:"+cvo.getContatto().trim()));        		
				startActivity(sendIntent);	                    		
        	}
            return true;
        }
		
	}
	
	public void onContattoClicked(View view){
	    Integer codcontatto = (Integer)view.getTag();
	    
	    DataBaseHelper dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.NONE_DB);
	    
		ContattiVO cvo = dbh.getContattiById(codcontatto, null);
		dbh.close();
		openDettaglioContattoPopup(ListaContattiActivity.this,cvo);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		DataBaseHelper dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.NONE_DB);
		
		int itemId = item.getItemId();
		
		if (itemId == android.R.id.home){
			NavUtils.navigateUpFromSameTask(this);
		}else if (itemId == R.id.nuovocontatto) {
			ContattiVO contatto = new ContattiVO();
			contatto.setCodAnagrafica(cod_anagrafica);
			openDettaglioContattoPopup(ListaContattiActivity.this,contatto);
		} else if (itemId == R.id.cancellacontatto) {
			dbh.deleteContatti(selected.keySet().toArray(new Integer[selected.keySet().size()]));			
			refreshData();
//			Intent lista_contatti = new Intent(ListaContattiActivity.this, ListaContattiActivity.class);
//			lista_contatti.putExtra(ContattiColumnNames.CODANAGRAFICA, cod_anagrafica);
//			ListaContattiActivity.this.startActivity(lista_contatti);
		}
		
		dbh.close();
		
		return true;

	}
	
	public void openDettaglioContattoPopup(Context c, ContattiVO contatto){
		
		dialog = new Dialog(c);		
		
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_nuovo_contatto);
		
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
	    lp.copyFrom(dialog.getWindow().getAttributes());
	    lp.width = WindowManager.LayoutParams.WRAP_CONTENT;	
	    LinearLayout ll = dialog.findViewById(R.id.dialog_contatto_layout);
	    ll.getLayoutParams().width = (dm.widthPixels - (dm.widthPixels/10 * 2));

		sp_tipi_contatti = dialog.findViewById(R.id.cmb_tipicontatti);
		sp_tipi_contatti.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus){
					sp_tipi_contatti.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
				}else{
					sp_tipi_contatti.getBackground().setColorFilter(Color.parseColor("#f1ebeb"), PorterDuff.Mode.SRC_IN);
				}
				
			}
		});
		
		DataBaseHelper dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.NONE_DB);		
		
		 
		ArrayList<TipologiaContattiVO> tmp3 = dbh.getAllTipicontatti(null);		
		
		al_tipicontatti = new ArrayList<TipologiaContattiVO>();
		al_tipicontatti.add(new TipologiaContattiVO());
		al_tipicontatti.addAll(tmp3);
		
		CustomSpinnerAdapter icsa3 = new CustomSpinnerAdapter(ListaContattiActivity.this, 
															  R.layout.custom_combo_detail, 
															  al_tipicontatti.toArray());
		
		sp_tipi_contatti.setAdapter(icsa3);

		sp_tipi_contatti.setSelection(get_al_tipicontatti_index(contatto));
		sp_tipi_contatti.setOnItemSelectedListener(new TipiContattiComboSelectedListener(contatto));
		sp_tipi_contatti.setPopupBackgroundResource(R.drawable.spinner_bckground);
		
		et_contatto = dialog.findViewById(R.id.contatto);
		et_contatto.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus){
					et_contatto.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
				}else{
					et_contatto.getBackground().setColorFilter(Color.parseColor("#f1ebeb"), PorterDuff.Mode.SRC_IN);
				}
				
			}
		});
		
		et_contatto.setText(contatto.getContatto());
		
		WinkTextWatcher wtw_contatto = new WinkTextWatcher(contatto, "setContatto", String.class);
		et_contatto.addTextChangedListener(wtw_contatto);

		ImageButton salva = dialog.findViewById(R.id.btn_salva);
		salva.setOnClickListener(new ContattiClickListener(contatto));
		
		ImageButton usa = dialog.findViewById(R.id.btn_usa);
		usa.setOnClickListener(new UsaContattoClickListener(contatto));		
		ImageButton chiudi = dialog.findViewById(R.id.btn_chiudi);
		
		chiudi.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent lista_contatti = new Intent(v.getContext(), ListaContattiActivity.class);
				lista_contatti.putExtra(ContattiColumnNames.CODANAGRAFICA, cod_anagrafica);
				v.getContext().startActivity(lista_contatti);

				dialog.dismiss();
				
			}
		});
		
		dbh.close();
		
		dialog.show();
		dialog.getWindow().setAttributes(lp);
	}
	
	class TipiContattiComboSelectedListener implements OnItemSelectedListener{
		
		private ContattiVO c = null;
		
		public TipiContattiComboSelectedListener(ContattiVO c){
			this.c = c;
		}

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			TipologiaContattiVO tcvo = (TipologiaContattiVO)arg0.getItemAtPosition(arg2);
			c.setCodTipologiaContatto(tcvo.getCodTipologiaContatto());
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}

	}
	
	class ContattiClickListener implements OnClickListener{

		private ContattiVO c = null;
		
		public ContattiClickListener(ContattiVO c){
			this.c = c;
		}

		@Override
		public void onClick(View v) {
			
			DataBaseHelper dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.NONE_DB);
			
			if (!dbh.saveContatto(c)){
				Toast.makeText(getApplicationContext(), "Salvataggio contatto non avvenuto", Toast.LENGTH_SHORT).show();
								
//				AlertDialog alertDialog = new AlertDialog.Builder(ListaContattiActivity.this).create();
// 		        alertDialog.setTitle("Risultato salvataggio");	 
// 		        alertDialog.setMessage("Salvataggio contatto non avvenuto");
// 		        alertDialog.setIcon(R.drawable.button_cancel);
// 		        alertDialog.show();
 		        
			}
			
			dbh.close();
			
//			Intent lista_contatti = new Intent(v.getContext(), ListaContattiActivity.class);
//			lista_contatti.putExtra(ContattiColumnNames.CODANAGRAFICA, cod_anagrafica);
//			v.getContext().startActivity(lista_contatti);
			refreshData();
			dialog.dismiss();								
			
		}
		
	}

	class UsaContattoClickListener implements OnClickListener{

		private ContattiVO c = null;
		
		public UsaContattoClickListener(ContattiVO c){
			this.c = c;
		}

		@Override
		public void onClick(View v) {
			
			if (c.getContatto().contains("@")){
				Intent sendIntent = new Intent(Intent.ACTION_VIEW);
				sendIntent.setType("plain/text");
				sendIntent.setData(Uri.parse(c.getContatto()));
				sendIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
				sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { c.getContatto() });
				startActivity(sendIntent);
			}else{
				
				PopupMenu popup = new PopupMenu(getBaseContext(), v);
				                 
                popup.getMenuInflater().inflate(R.menu.menu_azione_contatti, popup.getMenu());
                                
                popup.setOnMenuItemClickListener(new MyItemClickListener(c));
                
                popup.show();					
				
			}
			
			
		}
		
	}
	
	
	protected int get_al_tipicontatti_index(ContattiVO c){
		int count = 0;
		for (TipologiaContattiVO tipicontatti : al_tipicontatti) {
			if (tipicontatti.getCodTipologiaContatto() == c.getCodTipologiaContatto()){
				return count;
			}else{
				count++;
			}
		}
		return 0;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    getMenuInflater().inflate(R.menu.menu_lista_contatti, menu);
	    return true;
	}

	@Override
	public void onBackPressed() {
		finish();
	}
	

}
