package org.winkhouse.mwinkhouse.activity.anagrafiche;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.winkhouse.mwinkhouse.activity.ImportActivity;
import org.winkhouse.mwinkhouse.activity.adapters.ListaAnagraficheAdapter;
import org.winkhouse.mwinkhouse.activity.adapters.ListaAnagraficheSelAdapter;
import org.winkhouse.mwinkhouse.activity.immobili.ListaImmobiliActivity;
import org.winkhouse.mwinkhouse.activity.listeners.DialogAnagraficheCancellaListener;
import org.winkhouse.mwinkhouse.helpers.DataBaseHelper;
import org.winkhouse.mwinkhouse.helpers.SearchParam;
import org.winkhouse.mwinkhouse.models.AnagraficheVO;
import org.winkhouse.mwinkhouse.models.ColloquiAnagraficheVO;
import org.winkhouse.mwinkhouse.models.ContattiVO;
import org.winkhouse.mwinkhouse.models.GeoVO;
import org.winkhouse.mwinkhouse.models.ImmobiliPropietariVO;
import org.winkhouse.mwinkhouse.models.ImmobiliVO;
import org.winkhouse.mwinkhouse.models.TipologiaContattiVO;
import org.winkhouse.mwinkhouse.models.columns.AnagraficheColumnNames;
import org.winkhouse.mwinkhouse.util.ActivityMessages;

import org.winkhouse.mwinkhouse.R;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal;
import android.provider.ContactsContract.Data;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class ListaAnagraficheActivity extends AppCompatActivity {

	private ListaAnagraficheAdapter adapter = null;	
	private ListaAnagraficheSelAdapter adapterSel = null;
	
	private ListView listView = null;
	private ListView listViewSel = null;
	private ListView listViewGeo = null;
	private ArrayList<AnagraficheVO> list = null;
	private ArrayList<AnagraficheVO> listsel = null;
	private ArrayList<GeoVO> listGeo = null;
	private Integer codImmobilePropieta = null; 
	private Integer codColloquio = null;
	private Dialog dialog = null;
	private Dialog dialogCancellazione = null;
	private HashMap<Integer,Object> selectedSel = new HashMap<Integer,Object>();
	private HashMap<Integer,Object> selected = new HashMap<Integer,Object>();
	private DisplayMetrics dm = null;
	
	public ListaAnagraficheActivity() {

	}
	
	protected ArrayList<AnagraficheVO> getData(){
		
		if (list == null){
			list = new ArrayList<AnagraficheVO>();
		}else{
			list.clear();
		}
		ArrayList<AnagraficheVO> avos = new ArrayList<AnagraficheVO>();
		DataBaseHelper dbh = null; 

	    HashMap columns = new HashMap();
	    columns.put(AnagraficheColumnNames.CODANAGRAFICA, null);
	    columns.put(AnagraficheColumnNames.NOME, null);
	    columns.put(AnagraficheColumnNames.COGNOME, null);
	    columns.put(AnagraficheColumnNames.PROVINCIA, null);
	    columns.put(AnagraficheColumnNames.CITTA, null);
	    columns.put(AnagraficheColumnNames.INDIRIZZO, null);

		Parcelable[] obj_arr = null;
				
		if (getIntent().getExtras() != null){
			
			if (getIntent().getExtras().containsKey(ActivityMessages.PROPIETARI_ACTION) && 
				getIntent().getExtras().getBoolean(ActivityMessages.PROPIETARI_ACTION, false) &&
				getIntent().getExtras().containsKey(ActivityMessages.CODIMMOBILE_PROPIETARI_ACTION)){
				
				codImmobilePropieta = getIntent().getExtras().getInt(ActivityMessages.CODIMMOBILE_PROPIETARI_ACTION);
				dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.READ_DB);
				ImmobiliVO immobile = dbh.getImmobileById(codImmobilePropieta, null);
				ListaAnagraficheActivity.this.setTitle(immobile.getIndirizzo());
				avos = dbh.getAnagrafichePropietarie(columns, codImmobilePropieta);
				
			}else if (getIntent().getExtras().containsKey(ActivityMessages.SEARCH_ACTION) && 
				      getIntent().getExtras().containsKey(SearchParam.class.getName())){
				
				codImmobilePropieta = null;
				ListaAnagraficheActivity.this.setTitle("Risultati ricerca");
				obj_arr = getIntent().getExtras().getParcelableArray(SearchParam.class.getName());
			    dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.READ_DB);
			     

				if (obj_arr != null){
								 
					getIntent().getExtras().remove(SearchParam.class.getName());
					 
					ArrayList<SearchParam> al_params = new ArrayList<SearchParam>();
					 
					for (int i = 0; i < obj_arr.length; i++) {				
						al_params.add((SearchParam)obj_arr[i]);
					}
					 
					avos = dbh.searchAnagrafiche(al_params, columns);
					 
				}				
				
			}else if (getIntent().getExtras().containsKey(ActivityMessages.CODCOLLOQUIO_ACTION)){
				codColloquio = getIntent().getExtras().getInt(ActivityMessages.CODCOLLOQUIO_ACTION);
				dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.READ_DB);
				avos = dbh.getAnagraficheColloquio(codColloquio, null);
			}else{
				codImmobilePropieta = null;
				ListaAnagraficheActivity.this.setTitle("Lista anagrafiche");
				dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.READ_DB);
				avos = dbh.getAllAnagrafiche(columns);	
			}
			
			if (dbh != null){
				dbh.close();
				dbh.getSqllitedb().close();
			}
			
		}else{
			codImmobilePropieta = null;
			ListaAnagraficheActivity.this.setTitle("Lista anagrafiche");
			dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.READ_DB);
			avos = dbh.getAllAnagrafiche(columns);	
			if (dbh != null){
				dbh.close();
				dbh.getSqllitedb().close();
			}

		}
	     
	     Iterator<AnagraficheVO> it = avos.iterator();
	     while(it.hasNext()){
	    	 list.add(it.next());
	     }
	     

	    
		return list;
	}



	@Override
	protected void onPostResume() {
		super.onPostResume();
		refreshData();
	}
	
	Runnable run = new Runnable(){
		
	     public void run(){
	 		list.clear();
	 		adapter.clear();
			list = getData();			
			listView.invalidateViews();
			adapter.notifyDataSetChanged();		
		}
	};
	
	public void refreshData(){
		ListaAnagraficheActivity.this.runOnUiThread(run);
	}
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		 super.onCreate(savedInstanceState);
		 dm = new DisplayMetrics();
 		 getWindowManager().getDefaultDisplay().getMetrics(dm);
		 overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);
		 		 
//		 ActionBar ab = getActionBar();
//		 ab.setDisplayHomeAsUpEnabled(true);
		 
         setContentView(R.layout.lista_anagrafiche);
         
	     listView = findViewById(R.id.listViewAnagrafiche);
	     list = getData();
	     adapter = new ListaAnagraficheAdapter(ListaAnagraficheActivity.this,R.layout.dettaglio_lista_anagrafiche,list,selected); 


         listView.setAdapter(adapter);

	     listView.setOnItemClickListener(new OnItemClickListener() {

	        	@Override
	        	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	        		AnagraficheVO avo = (AnagraficheVO)arg0.getItemAtPosition(arg2);
	        		Intent dettaglio_anagrafiche = new Intent(arg1.getContext(), DettaglioAnagraficaActivity.class);

	        		dettaglio_anagrafiche.putExtra(AnagraficheColumnNames.CODANAGRAFICA, avo.getCodAnagrafica());
	        		arg1.getContext().startActivity(dettaglio_anagrafiche);				

	        	}				
	     });
         
	     refreshData();	     	     
	     
	     
	}
		
	
	protected ArrayList<AnagraficheVO> getDataAnagraficheSel(){
		
		listsel = new ArrayList();
		
	    DataBaseHelper dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.READ_DB);
	    ArrayList<AnagraficheVO> avos = new ArrayList<AnagraficheVO>();
	    
	    if ((codImmobilePropieta != null) && (codImmobilePropieta != 0)){
	    	avos = dbh.getAnagraficheNotPropietarie(null, codImmobilePropieta);
	    }else if ((codColloquio != null) && (codColloquio != 0)){
	    	avos = dbh.getAnagraficheNotColloquio(null, codColloquio);
	    }
	    
	    dbh.getSqllitedb().close();
	    
        Iterator<AnagraficheVO> it = avos.iterator();
			
	    while(it.hasNext()){
	    	listsel.add(it.next());
	    }
	    dbh.getSqllitedb().close();
	     
		return listsel;
	}
	
	
	public void openListaAnagrafichePopup(Context c){
		
		dialog = new Dialog(c);		
		
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.lista_anagrafiche_sel);
		
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
	    lp.copyFrom(dialog.getWindow().getAttributes());
	    lp.width = WindowManager.LayoutParams.WRAP_CONTENT;	
	    
	    LinearLayout ll = dialog.findViewById(R.id.dialog_listaanagrafiche_layout);
	    ll.getLayoutParams().width = (dm.widthPixels - (dm.widthPixels/10 * 2));
	    
	    listViewSel = dialog.findViewById(R.id.listViewAnagraficheSel);
	     
	    listsel = getDataAnagraficheSel();	     
	     
	    adapterSel = new ListaAnagraficheSelAdapter(ListaAnagraficheActivity.this, 
	    								     	    R.layout.dettaglio_lista_anagrafiche, 
	    								       	    listsel,
	    								       	    selectedSel);
        listViewSel.setAdapter(adapterSel);	
	    

		ImageButton salva = dialog.findViewById(R.id.btn_salva);
		salva.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (selectedSel.keySet().size() > 0){
					
					DataBaseHelper dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.READ_DB);					
					Iterator<Integer> it = selectedSel.keySet().iterator();
					
					while (it.hasNext()) {
						
						if ((codImmobilePropieta != null) && (codImmobilePropieta != 0)){
							
							ImmobiliPropietariVO ipVO = new ImmobiliPropietariVO();
							ipVO.setCodImmobile(codImmobilePropieta);
							ipVO.setCodAnagrafica(it.next());
							dbh.saveImmobiliPropieta(ipVO);
							
					    }else if ((codColloquio != null) && (codColloquio != 0)){
					    	
					    	ColloquiAnagraficheVO caVO = new ColloquiAnagraficheVO();
					    	caVO.setCodAnagrafica(it.next());
					    	caVO.setCodColloquio(codColloquio);
					    	dbh.saveColloquioAnagrafica(caVO);
					    	
					    }
						
					}
					
					dbh.close();
					refreshData();
					dialog.dismiss();
				}else{
					Toast.makeText(ListaAnagraficheActivity.this, "Selezionare almeno una anagrafica",Toast.LENGTH_SHORT).show();					
				}
			}
				
				
		});
				
		ImageButton chiudi = dialog.findViewById(R.id.btn_chiudi);
		chiudi.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		
		dialog.show();
		dialog.getWindow().setAttributes(lp);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    getMenuInflater().inflate(R.menu.menu_lista_anagrafiche, menu);
	    return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int itemId = item.getItemId();
		
		if (itemId == android.R.id.home){
			
			NavUtils.navigateUpFromSameTask(this);
			
		}else if (itemId == R.id.nuova_anagrafica) {
			
			if (((codImmobilePropieta == null) || (codImmobilePropieta == 0)) && ((codColloquio == null) || (codColloquio == 0))){
				
				AnagraficheVO avo = new AnagraficheVO();
				Intent dettaglio_anagrafica = new Intent(ListaAnagraficheActivity.this, DettaglioAnagraficaActivity.class);
				dettaglio_anagrafica.putExtra(ActivityMessages.CODIMMOBILE_PROPIETARI_ACTION, codImmobilePropieta);
				dettaglio_anagrafica.putExtra(AnagraficheColumnNames.CODANAGRAFICA, avo.getCodAnagrafica());
				ListaAnagraficheActivity.this.startActivity(dettaglio_anagrafica);
				
			}else{
				
				openListaAnagrafichePopup(ListaAnagraficheActivity.this);
				
			}
			
		} else if (itemId == R.id.ricerca_anagrafica) {
			
			Intent ricerca_anagrafica = new Intent(ListaAnagraficheActivity.this, RicercaAnagraficheActivity.class);
			ListaAnagraficheActivity.this.startActivity(ricerca_anagrafica);
			
		}else if (itemId == R.id.import_anagrafica){
			
			Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, 1);
		
		} else if (itemId == R.id.cancella_anagrafica) {
			
			
			if (selected.keySet().size() > 0){
				
				if ((codImmobilePropieta != null && codImmobilePropieta != 0) && 
					(codColloquio == null || codColloquio == 0)){
					
//					AlertDialog.Builder alertDialog = new AlertDialog.Builder(ListaAnagraficheActivity.this,AlertDialog.THEME_HOLO_DARK);
//			        alertDialog.setTitle("Conferma dissociazione ...");
//			        alertDialog.setMessage("Procedere con la dissociazione delle anagrafiche ?");
//			        alertDialog.setIcon(R.drawable.help);
//			        alertDialog.setPositiveButton("SI", new DialogAnagraficheDissociaListener(codImmobilePropieta,selected,getApplicationContext()));
//			        alertDialog.setNegativeButton("NO", null);
//			        alertDialog.show();				
//			        
//			        refreshData();		
					
//					openDissociazioneAnagrafichePopup(ListaAnagraficheActivity.this);

					DataBaseHelper dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.READ_DB);
					Iterator<Integer> it = selected.keySet().iterator();
					while (it.hasNext()) {
						Integer key = it.next();
						ImmobiliPropietariVO ipVO = new ImmobiliPropietariVO();
						ipVO.setCodImmobile(codImmobilePropieta);
						ipVO.setCodAnagrafica(key);
						dbh.deleteImmobiliPropieta(ipVO);
						adapter.remove((AnagraficheVO)selected.get(key));
					}
					dbh.close();
					//refreshData();
					adapter.notifyDataSetChanged();
					Intent itent = new Intent(ListaAnagraficheActivity.this, ListaAnagraficheActivity.class);	
					itent.putExtra(ActivityMessages.PROPIETARI_ACTION,true);
					itent.putExtra(ActivityMessages.CODIMMOBILE_PROPIETARI_ACTION,codImmobilePropieta);												
					startActivity(itent);
				
					
				}else if (codColloquio != null && codColloquio != 0){
					
					DataBaseHelper dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.READ_DB);
					Iterator<Integer> it = selected.keySet().iterator();
					
					while (it.hasNext()) {
						
						Integer key = it.next();
						
						ColloquiAnagraficheVO caVO = new ColloquiAnagraficheVO();
						caVO.setCodColloquio(codColloquio);
						caVO.setCodAnagrafica(key);
						dbh.deleteAnagraficheColloqui(caVO);
						
						adapter.remove((AnagraficheVO)selected.get(key));
					}
					dbh.close();
					//refreshData();
					adapter.notifyDataSetChanged();
					Intent itent = new Intent(ListaAnagraficheActivity.this, ListaAnagraficheActivity.class);	
					itent.putExtra(ActivityMessages.CODCOLLOQUIO_ACTION,codColloquio);																
					startActivity(itent);
					
					
				}else{
					AlertDialog.Builder alertDialog = new AlertDialog.Builder(ListaAnagraficheActivity.this,AlertDialog.THEME_HOLO_DARK);
			        alertDialog.setTitle("Conferma cancellazione ...");
			        alertDialog.setMessage("Procedere con la cancellazione delle anagrafiche ?");
			        alertDialog.setIcon(R.drawable.help);
			        alertDialog.setPositiveButton("SI", new DialogAnagraficheCancellaListener(selected.keySet(),getApplicationContext(),codImmobilePropieta,codColloquio));
			        alertDialog.setNegativeButton("NO", null);
			        alertDialog.show();				
			        
				}
			}		}else if (itemId == R.id.EsportaZip){
            if (selected.keySet().size() > 0){
                Intent intent = new Intent(ListaAnagraficheActivity.this, ImportActivity.class);
                intent.putIntegerArrayListExtra(ActivityMessages.ANAGRAFICHE_LIST, new ArrayList(selected.keySet()));
                startActivity(intent);
            }else{
				Toast.makeText(ListaAnagraficheActivity.this, "Selezionare almeno una anagrafica",Toast.LENGTH_SHORT).show();
			}

		}

		return true;
	}
	
	@Override
	public void onBackPressed() {
//		Intent home =new Intent(ListaAnagraficheActivity.this, StartUpActivity.class);
//		startActivity(home);				
		finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == 1) {

			if (resultCode == RESULT_OK) {
	            
				AnagraficheVO aVO = new AnagraficheVO();
				ArrayList<ContattiVO> al_contatti = new ArrayList<ContattiVO>();
				DataBaseHelper dbh = new DataBaseHelper(ListaAnagraficheActivity.this, DataBaseHelper.WRITE_DB);
				ArrayList<TipologiaContattiVO> al_tipicontatti = dbh.getAllTipicontatti(null);
				
				Uri datauri = data.getData();
	            String[] c = {ContactsContract.CommonDataKinds.StructuredPostal._ID};

	            Cursor cursor = getContentResolver().query(datauri, c, null, null, null);
	            
	            cursor.moveToFirst();
	            String c_id = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal._ID));
	            cursor.close();
         
	            String[] sp = {StructuredPostal.POSTCODE,
	                    	   StructuredPostal.STREET,
	                    	   StructuredPostal.CITY,
	                    	   StructuredPostal.REGION};

	            cursor = getContentResolver().query(CommonDataKinds.StructuredPostal.CONTENT_URI, sp, ContactsContract.CommonDataKinds.StructuredPostal.CONTACT_ID + " = ?", 
	                    							new String[]{c_id}, null);
	            while (cursor.moveToNext()){		            
		            
		            String data_pc = cursor.getString(cursor.getColumnIndex(StructuredPostal.POSTCODE));
		            String data_street = cursor.getString(cursor.getColumnIndex(StructuredPostal.STREET));
		            String data_city = cursor.getString(cursor.getColumnIndex(StructuredPostal.CITY));
		            String data_region = cursor.getString(cursor.getColumnIndex(StructuredPostal.REGION));
		            
		            aVO.setCap((data_pc == null)?"":data_pc);
		            aVO.setIndirizzo((data_street == null)?"":data_street);
		            aVO.setCitta((data_city == null)?"":data_city);
		            
		            break;
		            
	            }
	            cursor.close();
	            
	            String[] datap = {Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                        		  Data.DISPLAY_NAME_PRIMARY :
                        		  Data.DISPLAY_NAME};

	            cursor = getContentResolver().query(ContactsContract.Data.CONTENT_URI, datap, ContactsContract.Data.CONTACT_ID + " = ?", new String[]{c_id}, null);	                      
	            
	            while (cursor.moveToNext()){
		            String data_name = cursor.getString(cursor.getColumnIndex(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
							Data.DISPLAY_NAME_PRIMARY :
							Data.DISPLAY_NAME));
		            aVO.setCognome((data_name == null)?"":data_name);
		            break;
		            
	            }
	            cursor.close();	
	            
	            String[] dataem = {Email.ADDRESS};

	            cursor = getContentResolver().query(CommonDataKinds.Email.CONTENT_URI, dataem, CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{c_id}, null);
	            while (cursor.moveToNext()){
	            	
	            	String data_address = cursor.getString(cursor.getColumnIndex(Email.ADDRESS));
	            	ContattiVO cVO = new ContattiVO();
	            	cVO.setContatto((data_address == null)?"":data_address);
	            	
	            	for (TipologiaContattiVO tcVO : al_tipicontatti) {
						if (tcVO.getDescrizione().equalsIgnoreCase("email") || tcVO.getDescrizione().equalsIgnoreCase("e-mail")){
							cVO.setCodTipologiaContatto(tcVO.getCodTipologiaContatto());
							break;
						}
					}
	            	
	            	al_contatti.add(cVO);
	            	
	            	
	            	
	            	
	            }
	            cursor.close();
	            
	            String[] dataph = {Phone.NUMBER};

	            cursor = getContentResolver().query(CommonDataKinds.Phone.CONTENT_URI, dataph, CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{c_id}, null);
	            while (cursor.moveToNext()){	            
	            
	            	String data_number = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
	            	ContattiVO cVO = new ContattiVO();
	            	cVO.setContatto((data_number == null)?"":data_number);
	            	
	            	for (TipologiaContattiVO tcVO : al_tipicontatti) {
						if (tcVO.getDescrizione().contains("cellulare") || 
							tcVO.getDescrizione().contains("Cellulare") || 
							tcVO.getDescrizione().contains("Cel") || 
							tcVO.getDescrizione().contains("cel")){
							cVO.setCodTipologiaContatto(tcVO.getCodTipologiaContatto());
							break;
						}
					}
	            	
	            	al_contatti.add(cVO);
	            	
	            }
	            cursor.close();
	            
	            dbh.saveAnagrafica(aVO);
	            for (ContattiVO cVO : al_contatti) {
					cVO.setCodAnagrafica(aVO.getCodAnagrafica());
					dbh.saveContatto(cVO);
				}
	            
	            if ((codImmobilePropieta != null) && (codImmobilePropieta != 0)){
	            	ImmobiliPropietariVO ipVO = new ImmobiliPropietariVO();
	            	ipVO.setCodAnagrafica(aVO.getCodAnagrafica());
	            	ipVO.setCodImmobile(codImmobilePropieta);
	            	dbh.saveImmobiliPropieta(ipVO);
	            }

	            if ((codColloquio != null) && (codColloquio != 0)){
	            	ColloquiAnagraficheVO caVO = new ColloquiAnagraficheVO();
	            	caVO.setCodAnagrafica(aVO.getCodAnagrafica());
	            	caVO.setCodColloquio(codColloquio);
	            	dbh.saveColloquioAnagrafica(caVO);
	            }
	            
	            dbh.close();
	            refreshData();
	        }
	    }
		
	}


}
