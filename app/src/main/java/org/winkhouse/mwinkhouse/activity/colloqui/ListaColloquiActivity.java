package org.winkhouse.mwinkhouse.activity.colloqui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.winkhouse.mwinkhouse.activity.adapters.ListaColloquiAdapter;
import org.winkhouse.mwinkhouse.activity.adapters.ListaColloquiSelAdapter;
import org.winkhouse.mwinkhouse.activity.listeners.DialogColloquioCancellaListener;
import org.winkhouse.mwinkhouse.helpers.DataBaseHelper;
import org.winkhouse.mwinkhouse.models.AnagraficheVO;
import org.winkhouse.mwinkhouse.models.ColloquiVO;
import org.winkhouse.mwinkhouse.models.ContattiVO;
import org.winkhouse.mwinkhouse.models.GeoVO;
import org.winkhouse.mwinkhouse.models.ImmobiliPropietariVO;
import org.winkhouse.mwinkhouse.models.ImmobiliVO;
import org.winkhouse.mwinkhouse.models.TipologiaContattiVO;
import org.winkhouse.mwinkhouse.models.columns.ColloquiColumnNames;
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

public class ListaColloquiActivity extends AppCompatActivity {

	private ListaColloquiAdapter adapter = null;	
	private ListaColloquiSelAdapter adapterSel = null;
	
	private ListView listView = null;
	private ListView listViewSel = null;
	private ListView listViewGeo = null;
	private ArrayList<ColloquiVO> list = null;
	private ArrayList<ColloquiVO> listsel = null;
	private ArrayList<GeoVO> listGeo = null;
	private Integer codimmobile = null;
	private Integer codanagrafica = null;
	private Dialog dialog = null;
	private Dialog dialogCancellazione = null;
	private HashMap<Integer,Object> selectedSel = new HashMap<Integer,Object>();
	private HashMap<Integer,Object> selected = new HashMap<Integer,Object>();
	private DisplayMetrics dm = null;
	
	public ListaColloquiActivity() {

	}
	
	protected ArrayList<ColloquiVO> getData(){
		
		if (list == null){
			list = new ArrayList<ColloquiVO>();
		}else{
			list.clear();
		}
		ArrayList<ColloquiVO> avos = new ArrayList<ColloquiVO>();
		DataBaseHelper dbh = null; 

	    HashMap columns = new HashMap();
	    columns.put(ColloquiColumnNames.CODCOLLOQUIO, null);
	    columns.put(ColloquiColumnNames.DATACOLLOQUIO, null);
	    columns.put(ColloquiColumnNames.CODTIPOLOGIACOLLOQUIO, null);
	    
		Parcelable[] obj_arr = null;
				
		if (getIntent().getExtras() != null){
			
			if (getIntent().getExtras().containsKey(ActivityMessages.CODIMMOBILE_ACTION)){
				
				codimmobile = getIntent().getExtras().getInt(ActivityMessages.CODIMMOBILE_ACTION);
				dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.READ_DB);				
				ImmobiliVO immobile = dbh.getImmobileById(codimmobile, null);
				ListaColloquiActivity.this.setTitle(immobile.getIndirizzo());
				avos = dbh.getColloquiImmobile(codimmobile, columns);
				
				
			}else if (getIntent().getExtras().containsKey(ActivityMessages.CODANAGRAFICA_ACTION)){
				
				codanagrafica = getIntent().getExtras().getInt(ActivityMessages.CODANAGRAFICA_ACTION);				
				dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.READ_DB);
				AnagraficheVO anagrafica = dbh.getAnagraficaById(codanagrafica, null);
				ListaColloquiActivity.this.setTitle(anagrafica.getCognome() + " " + anagrafica.getNome() + " " + anagrafica.getRagioneSociale());
				avos = dbh.getColloquiAnagrafica(anagrafica.getCodAnagrafica(), columns);
			}
			
			if (dbh != null){
				dbh.close();
				dbh.getSqllitedb().close();
			}
			
		}else{
			
			codimmobile = null;
			codanagrafica = null;
			
			ListaColloquiActivity.this.setTitle("Lista colloqui");
			dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.READ_DB);
			avos = dbh.getAllColloqui(columns);	
			if (dbh != null){
				dbh.close();
				dbh.getSqllitedb().close();
			}

		}
	     
	     Iterator<ColloquiVO> it = avos.iterator();
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
		ListaColloquiActivity.this.runOnUiThread(run);
	}
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		 super.onCreate(savedInstanceState);
		 dm = new DisplayMetrics();
 		 getWindowManager().getDefaultDisplay().getMetrics(dm);
		 overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);
		 		 
//		 ActionBar ab = getActionBar();
//		 ab.setDisplayHomeAsUpEnabled(true);
		 
         setContentView(R.layout.lista_colloqui);
         
	     listView = findViewById(R.id.listViewColloqui);
	     list = getData();
	     adapter = new ListaColloquiAdapter(ListaColloquiActivity.this,R.layout.dettaglio_lista_colloqui,list,selected); 


         listView.setAdapter(adapter);

	     listView.setOnItemClickListener(new OnItemClickListener() {

	        	@Override
	        	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	        		
	        		ColloquiVO avo = (ColloquiVO)arg0.getItemAtPosition(arg2);
	        		
	        		Intent dettaglio_colloquio = new Intent(arg1.getContext(), DettaglioColloquiActivity.class);
	        		dettaglio_colloquio.putExtra(ActivityMessages.CODCOLLOQUIO_ACTION, avo.getCodColloquio());
	        		
	        		arg1.getContext().startActivity(dettaglio_colloquio);

	        	}				
	     });
         
	     refreshData();	     	     
	     
	     
	}
		
	
	protected ArrayList<ColloquiVO> getDataColloquiSel(){
		
		listsel = new ArrayList();
		
	    DataBaseHelper dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.READ_DB);
	    ArrayList<ColloquiVO> avos = null;
	    
	    dbh.getSqllitedb().close();
        Iterator<ColloquiVO> it = avos.iterator();
			
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
	     
	    listsel = getDataColloquiSel();	     
	     
	    adapterSel = new ListaColloquiSelAdapter(ListaColloquiActivity.this, 
	    								   	     R.layout.dettaglio_lista_colloqui, 
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
						ImmobiliPropietariVO ipVO = new ImmobiliPropietariVO();
						ipVO.setCodImmobile(codimmobile);
						ipVO.setCodAnagrafica(it.next());
						dbh.saveImmobiliPropieta(ipVO);	
					}
					dbh.close();
					refreshData();
					dialog.dismiss();
				}else{
					Toast.makeText(ListaColloquiActivity.this, "Selezionare almeno un colloquio",Toast.LENGTH_SHORT).show();					
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
	    getMenuInflater().inflate(R.menu.menu_lista_colloqui, menu);
	    return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int itemId = item.getItemId();
		
		if (itemId == android.R.id.home){
			NavUtils.navigateUpFromSameTask(this);
			
		}else if (itemId == R.id.nuovo_colloquio) {
			
			Intent dettaglio_colloquio = null;
			
			if ((codimmobile != null) && (codimmobile != 0)){
								
				dettaglio_colloquio = new Intent(ListaColloquiActivity.this, DettaglioColloquiActivity.class);
				dettaglio_colloquio.putExtra(ActivityMessages.CODIMMOBILE_ACTION, codimmobile);
				
				ListaColloquiActivity.this.startActivity(dettaglio_colloquio);
				
			}else if ((codanagrafica != null) && (codanagrafica != 0)){
				
				dettaglio_colloquio = new Intent(ListaColloquiActivity.this, DettaglioColloquiActivity.class);
				dettaglio_colloquio.putExtra(ActivityMessages.CODANAGRAFICA_ACTION, codanagrafica);
				ListaColloquiActivity.this.startActivity(dettaglio_colloquio);
											

			}else{
				openListaAnagrafichePopup(ListaColloquiActivity.this);
			}
			
		
		} else if (itemId == R.id.cancella_colloquio) {
			
			
			if (selected.keySet().size() > 0){
				
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(ListaColloquiActivity.this,AlertDialog.THEME_HOLO_DARK);
		        alertDialog.setTitle("Conferma cancellazione ...");
		        alertDialog.setMessage("Procedere con la cancellazione dei colloqui ?");
		        alertDialog.setIcon(R.drawable.help);
		        
		        ArrayList<Integer> al = new ArrayList<Integer>();
		        Iterator it = selected.keySet().iterator();
		        while (it.hasNext()) {
					al.add((Integer)it.next());
				}
		        
		        alertDialog.setPositiveButton("SI", new DialogColloquioCancellaListener(al,getApplicationContext(),codimmobile,codanagrafica));
		        alertDialog.setNegativeButton("NO", null);
		        alertDialog.show();	
		        			        	
			}else{
				Toast.makeText(ListaColloquiActivity.this, "Selezionare almeno una anagrafica",Toast.LENGTH_SHORT).show();
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
		
		if (requestCode == 1) {

			if (resultCode == RESULT_OK) {
	            
				AnagraficheVO aVO = new AnagraficheVO();
				ArrayList<ContattiVO> al_contatti = new ArrayList<ContattiVO>();
				DataBaseHelper dbh = new DataBaseHelper(ListaColloquiActivity.this, DataBaseHelper.WRITE_DB);
				ArrayList<TipologiaContattiVO> al_tipicontatti = dbh.getAllTipicontatti(null);
				
				Uri datauri = data.getData();
	            String[] c = {Data.CONTACT_ID};

	            Cursor cursor = getContentResolver().query(CommonDataKinds.StructuredPostal.CONTENT_URI, c, null, null, null);
	            cursor.moveToFirst();
	            String c_id = cursor.getString(cursor.getColumnIndex(Data.CONTACT_ID));
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
	            
	            dbh.close();
	            refreshData();
	        }
	    }
		
	}


}
