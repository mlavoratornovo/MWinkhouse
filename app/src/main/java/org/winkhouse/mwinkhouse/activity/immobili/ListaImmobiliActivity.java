package org.winkhouse.mwinkhouse.activity.immobili;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.winkhouse.mwinkhouse.activity.ImportActivity;
import org.winkhouse.mwinkhouse.activity.adapters.ListaImmobiliAdapter;
import org.winkhouse.mwinkhouse.activity.adapters.ListaImmobiliSelAdapter;
import org.winkhouse.mwinkhouse.activity.listeners.DialogImmobiliCancellaListener;
import org.winkhouse.mwinkhouse.helpers.DataBaseHelper;
import org.winkhouse.mwinkhouse.helpers.SearchParam;
import org.winkhouse.mwinkhouse.models.AnagraficheVO;
import org.winkhouse.mwinkhouse.models.ImmobiliPropietariVO;
import org.winkhouse.mwinkhouse.models.ImmobiliVO;
import org.winkhouse.mwinkhouse.models.columns.ImmobiliColumnNames;
import org.winkhouse.mwinkhouse.util.ActivityMessages;

import org.winkhouse.mwinkhouse.R;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.SparseArray;
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

public class ListaImmobiliActivity extends AppCompatActivity {

	private ListaImmobiliAdapter adapter = null;
	private ListaImmobiliSelAdapter adapterSel = null;
	private ListView listView = null;
	private ListView listViewSel = null;
	private ArrayList<ImmobiliVO> list = null;
	private ArrayList<ImmobiliVO> listSel = null;
	private Integer codAnagraficaPropieta = null;
	private Dialog dialog = null; 		
	private final HashMap<Integer,Object> selectedSel = new HashMap<Integer,Object>();
	private final HashMap<Integer,Object> selected = new HashMap<Integer,Object>();
	private DisplayMetrics dm = null;
	
	public ListaImmobiliActivity() {

	}
	
	protected ArrayList getData(){
				
		if (list == null){
			list = new ArrayList<ImmobiliVO>();
		}else{
			list.clear();
		}
		ArrayList<ImmobiliVO> ivos = new ArrayList<ImmobiliVO>();
		DataBaseHelper dbh = null; 

	    HashMap columns = new HashMap();
	    columns.put(ImmobiliColumnNames.CODIMMOBILE, null);
	    columns.put(ImmobiliColumnNames.ZONA, null);
	    columns.put(ImmobiliColumnNames.CITTA, null);
	    columns.put(ImmobiliColumnNames.INDIRIZZO, null);
	    columns.put(ImmobiliColumnNames.CODANAGRAFICA, null);

		Parcelable[] obj_arr = null;
				
		if (getIntent().getExtras() != null){
			
			if (getIntent().getExtras().containsKey(ActivityMessages.IMMOBILI_PROPIETA_ACTION) && 
				getIntent().getExtras().getBoolean(ActivityMessages.IMMOBILI_PROPIETA_ACTION, false) &&
				getIntent().getExtras().containsKey(ActivityMessages.CODANAGRAFICA_PROPIETARI_ACTION)){
				
				codAnagraficaPropieta = getIntent().getExtras().getInt(ActivityMessages.CODANAGRAFICA_PROPIETARI_ACTION);
				dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.READ_DB);
				AnagraficheVO anagrafica = dbh.getAnagraficaById(codAnagraficaPropieta, null);
				ListaImmobiliActivity.this.setTitle(anagrafica.getCognome() + " " + anagrafica.getNome() + " " + anagrafica.getRagioneSociale());
				ivos = dbh.getImmobiliPropieta(columns, codAnagraficaPropieta);
				
			}else if (getIntent().getExtras().containsKey(ActivityMessages.SEARCH_ACTION) && 
				      getIntent().getExtras().containsKey(SearchParam.class.getName())){
				
				ListaImmobiliActivity.this.setTitle("Risultati ricerca");
				obj_arr = getIntent().getExtras().getParcelableArray(SearchParam.class.getName());
			    dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.READ_DB);
			     

				if (obj_arr != null){
								 
					getIntent().getExtras().remove(SearchParam.class.getName());
					 
					ArrayList<SearchParam> al_params = new ArrayList<SearchParam>();
					 
					for (int i = 0; i < obj_arr.length; i++) {				
						al_params.add((SearchParam)obj_arr[i]);
					}
					 
					ivos = dbh.searchImmobili(al_params, columns);
					 
				}				
				
			}else{
				ListaImmobiliActivity.this.setTitle("Lista immobili");
				dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.READ_DB);
				ivos = dbh.getAllImmobili(columns);	
			}
			
		
			
		}else{
			ListaImmobiliActivity.this.setTitle("Lista immobili");
			dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.READ_DB);
			ivos = dbh.getAllImmobili(columns);	
		}

		if (dbh != null){
			dbh.close();
			dbh.getSqllitedb().close();
		}
		
	    Iterator<ImmobiliVO> it = ivos.iterator();
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
		ListaImmobiliActivity.this.runOnUiThread(run);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		 super.onCreate(savedInstanceState);
		 dm = new DisplayMetrics();
 		 getWindowManager().getDefaultDisplay().getMetrics(dm);
		 overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);
		 
//		 ActionBar ab = getActionBar();
//		 ab.setHomeButtonEnabled(true);
		 
         setContentView(R.layout.lista_immobili);
         
	     listView = findViewById(R.id.listViewImmobili);
	     
	     list = getData();

	     adapter = new ListaImmobiliAdapter(ListaImmobiliActivity.this,
	    								    R.layout.dettaglio_lista_immobili, 
	    		 							list,
	    		 							selected);
	     listView.setAdapter(adapter);	
	     listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				ImmobiliVO ivo = (ImmobiliVO)arg0.getItemAtPosition(arg2);
				Intent dettaglio_immobile = new Intent(arg1.getContext(), DettaglioImmobileActivity.class);
				dettaglio_immobile.putExtra(ImmobiliColumnNames.CODIMMOBILE, ivo.getCodImmobile());
				arg1.getContext().startActivity(dettaglio_immobile);
				
			}
	    	 
	     });
	}
		
	
	public void openListaImmobiliPopup(Context c){
		
		dialog = new Dialog(c);		
		
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.lista_anagrafiche_sel);
		
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
	    lp.copyFrom(dialog.getWindow().getAttributes());
	    lp.width = WindowManager.LayoutParams.WRAP_CONTENT;	
	    LinearLayout ll = dialog.findViewById(R.id.dialog_listaanagrafiche_layout);
	    ll.getLayoutParams().width = (dm.widthPixels - (dm.widthPixels/10 * 2));
	    
	    listViewSel = dialog.findViewById(R.id.listViewAnagraficheSel);
	     
	    listSel = getDataImmobiliSel();	     
	     
	    adapterSel = new ListaImmobiliSelAdapter(ListaImmobiliActivity.this, 
	    								     	 R.layout.dettaglio_lista_immobili, 
	    								       	 listSel,
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
						ipVO.setCodImmobile(it.next());
						ipVO.setCodAnagrafica(codAnagraficaPropieta);
						dbh.saveImmobiliPropieta(ipVO);	
					}
					dbh.close();
					refreshData();
					dialog.dismiss();
				}else{
					Toast.makeText(ListaImmobiliActivity.this, "Selezionare almeno un immobile",Toast.LENGTH_SHORT).show();					
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

	
	protected ArrayList<ImmobiliVO> getDataImmobiliSel(){
		
		listSel = new ArrayList();
	    DataBaseHelper dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.READ_DB);
	    ArrayList<ImmobiliVO> avos = dbh.getImmobiliNotPropietarie(null, codAnagraficaPropieta);
	    dbh.getSqllitedb().close();
        Iterator<ImmobiliVO> it = avos.iterator();
			
	     while(it.hasNext()){
	    	 listSel.add(it.next());
	     }
	     dbh.getSqllitedb().close();
	     
		return listSel;
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    getMenuInflater().inflate(R.menu.menu_lista_immobili, menu);
	    return true;

	}
	
	@Override
	public void onBackPressed() {
		finish();

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int itemId = item.getItemId();
		
		if (itemId == android.R.id.home){
			NavUtils.navigateUpFromSameTask(this);
		}else if (itemId == R.id.nuovo_immobile) {
			if ((codAnagraficaPropieta == null) || (codAnagraficaPropieta == 0)){ 			
				ImmobiliVO ivo = new ImmobiliVO();
				Intent dettaglio_immobile = new Intent(ListaImmobiliActivity.this, DettaglioImmobileActivity.class);
				dettaglio_immobile.putExtra(ImmobiliColumnNames.CODIMMOBILE, ivo.getCodImmobile());
				ListaImmobiliActivity.this.startActivity(dettaglio_immobile);
			}else{
				openListaImmobiliPopup(ListaImmobiliActivity.this);
			}
		}else if (itemId == R.id.cancella_immobile){
			
			if (selected.keySet().size() > 0){
				
				if (codAnagraficaPropieta != null && codAnagraficaPropieta != 0){
					
					DataBaseHelper dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.READ_DB);
					Iterator<Integer> it = selected.keySet().iterator();
					while (it.hasNext()) {
						Integer key = it.next();
						ImmobiliPropietariVO ipVO = new ImmobiliPropietariVO();
						ipVO.setCodImmobile(key);
						ipVO.setCodAnagrafica(codAnagraficaPropieta);
						dbh.deleteImmobiliPropieta(ipVO);
						adapter.remove((ImmobiliVO)selected.get(key));
					}
					dbh.close();
					//refreshData();
					adapter.notifyDataSetChanged();
					Intent itent = new Intent(ListaImmobiliActivity.this, ListaImmobiliActivity.class);	
					itent.putExtra(ActivityMessages.PROPIETARI_ACTION,true);
					itent.putExtra(ActivityMessages.CODIMMOBILE_PROPIETARI_ACTION,codAnagraficaPropieta);												
					startActivity(itent);
				
					
				}else{
					AlertDialog.Builder alertDialog = new AlertDialog.Builder(ListaImmobiliActivity.this,AlertDialog.THEME_HOLO_DARK);
			        alertDialog.setTitle("Conferma cancellazione ...");
			        alertDialog.setMessage("Procedere con la cancellazione degli immobili ?");
			        alertDialog.setIcon(R.drawable.help);
			        
			        ArrayList<Integer> list = new ArrayList<Integer>();
			        Iterator<Integer> it = selected.keySet().iterator();
			        while(it.hasNext()){
			        	list.add(it.next());
			        }
			        
			        alertDialog.setPositiveButton("SI", new DialogImmobiliCancellaListener(list,getApplicationContext()));
			        alertDialog.setNegativeButton("NO", null);
			        alertDialog.show();				
			        
//					Intent itent = new Intent(ListaImmobiliActivity.this, ListaImmobiliActivity.class);	
//					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//					itent.putExtra(ActivityMessages.PROPIETARI_ACTION,true);
//					itent.putExtra(ActivityMessages.CODANAGRAFICA_PROPIETARI_ACTION,codAnagraficaPropieta);												
//					startActivity(itent);
			        	
				}
			}else{
				Toast.makeText(ListaImmobiliActivity.this, "Selezionare almeno un immobile",Toast.LENGTH_SHORT).show();
			}

		} else if (itemId == R.id.ricerca_immobile) {
			Intent ricerca_immobile = new Intent(ListaImmobiliActivity.this, RicercaImmobiliActivity.class);
//			Intent ricerca_immobile = new Intent(ListaImmobiliActivity.this, DummyGDriveActivity.class);
			ListaImmobiliActivity.this.startActivity(ricerca_immobile);
		}else if (itemId == R.id.EsportaZip){
            if (selected.keySet().size() > 0){
                Intent intent = new Intent(ListaImmobiliActivity.this, ImportActivity.class);
                intent.putIntegerArrayListExtra(ActivityMessages.IMMOBILI_LIST, new ArrayList(selected.keySet()));
//                intent.putExtra(ActivityMessages.ACTIVITY_TYPE, ActivityMessages.EXPORT_ACTION);
                startActivity(intent);
            }else{
                Toast.makeText(ListaImmobiliActivity.this, "Selezionare almeno un immobile",Toast.LENGTH_SHORT).show();
            }
        }

		return true;
	}



//	@Override
//	protected void onNewIntent(Intent intent) {
//		super.onNewIntent(intent);
// 		list.clear();
//		list = getData();
//		adapter.clear();
//		adapter.addAll(list);
//		adapter.notifyDataSetChanged();
//		
////		Runnable run = new Runnable(){
////		     public void run(){
////		 		list.clear();
////				list = getData();
////				adapter.clear();
////				adapter.addAll(list);
////				adapter.notifyDataSetChanged();
////		     }
////		};
////		this.runOnUiThread(run);
//		
//	}

}
