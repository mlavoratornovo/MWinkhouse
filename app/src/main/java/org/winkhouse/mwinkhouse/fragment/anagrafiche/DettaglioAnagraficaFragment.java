package org.winkhouse.mwinkhouse.fragment.anagrafiche;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.winkhouse.mwinkhouse.R;
import org.winkhouse.mwinkhouse.activity.adapters.CustomSpinnerAdapter;
import org.winkhouse.mwinkhouse.activity.adapters.ListaGeoAdapter;
import org.winkhouse.mwinkhouse.activity.contatti.ListaContattiActivity;
import org.winkhouse.mwinkhouse.activity.immobili.ListaImmobiliActivity;
import org.winkhouse.mwinkhouse.activity.listeners.DialogAnagraficheAnnullaListener;
import org.winkhouse.mwinkhouse.activity.listeners.DialogAnagraficheCancellaListener;
import org.winkhouse.mwinkhouse.activity.listeners.WinkTextWatcher;
import org.winkhouse.mwinkhouse.helpers.DataBaseHelper;
import org.winkhouse.mwinkhouse.helpers.GeoFinderThread;
import org.winkhouse.mwinkhouse.models.AnagraficheVO;
import org.winkhouse.mwinkhouse.models.ClassiClientiVO;
import org.winkhouse.mwinkhouse.models.GeoVO;
import org.winkhouse.mwinkhouse.models.columns.AnagraficheColumnNames;
import org.winkhouse.mwinkhouse.models.columns.ClassiClienteColumnNames;
import org.winkhouse.mwinkhouse.util.ActivityMessages;
import org.winkhouse.mwinkhouse.util.NetworkUtils;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DettaglioAnagraficaFragment extends Fragment {

	private AnagraficheVO anagrafica = null;
	
	private EditText txtProvincia = null;
	private EditText txtCap = null;
	private EditText txtCognome = null;
	private EditText txtNome = null;
	private EditText txtCitta = null;
	private EditText txtCommento = null;
	private EditText txtIndirizzo = null;
	private EditText txtRagioneSociale = null;
	private EditText txtPiva = null;
	private EditText txtCodiceFiscale = null;
	
	private Spinner sp_classe_cliente = null;
	
	private ArrayList<ClassiClientiVO> al_classi_cliente = null;
	
	private WinkTextWatcher wtw_citta = null;
	private WinkTextWatcher wtw_commento = null;
	private WinkTextWatcher wtw_indirizzo = null;
	private WinkTextWatcher wtw_cognome = null;

	private WinkTextWatcher wtw_provincia = null;
	private WinkTextWatcher wtw_cap = null;
	private WinkTextWatcher wtw_nome = null;
	private WinkTextWatcher wtw_ragionesociale = null;
	private WinkTextWatcher wtw_piva = null;
	private WinkTextWatcher wtw_codicefiscale = null;

	private Dialog dialog = null;
	private ListView listViewGeo = null;
	private ArrayList<GeoVO> listGeo = null;
	private DisplayMetrics dm = null;
	
	private ListaGeoAdapter adapterGeo = null;
	private TextView cittafind = null;
	
	Animation animazione_rotazione_out = null;

	private View view = null;
	
	public DettaglioAnagraficaFragment() {
		
	}
	
	private boolean canSave(){
		
		boolean returnValue = false;
		
		if (anagrafica != null) {
			
			if (!anagrafica.getCognome().trim().equalsIgnoreCase("") || 
				!anagrafica.getNome().trim().equalsIgnoreCase("") ||
				!anagrafica.getRagioneSociale().trim().equalsIgnoreCase("")){
				returnValue = true;
			}else{
				returnValue = false;
			}
			
		}else{
			returnValue = false;
		}
		
		return returnValue;
				
	}
	
	public AnagraficheVO getAnagrafica() {
		return anagrafica;
	}
	
	protected int get_al_classi_cliente_index(){
		int count = 0;
		for (ClassiClientiVO classiclienti : al_classi_cliente) {
			if (classiclienti.getCodClasseCliente() == anagrafica.getCodClasseCliente()){
				return count;
			}else{
				count++;
			}
		}
		return 0;
	}

	@Override
	public void onResume() {
		super.onResume();
		onStart();
	}

	@Override
	public void onStart() {
		super.onStart();
		Integer codAnagrafica = getArguments().getInt(AnagraficheColumnNames.CODANAGRAFICA, 0);
		if (codAnagrafica != 0){
			DataBaseHelper dbh = new DataBaseHelper(getActivity().getBaseContext(),DataBaseHelper.READ_DB);
			AnagraficheVO avo = dbh.getAnagraficaById(codAnagrafica, null);
			avo = (avo != null) ? avo : new AnagraficheVO(); 
			setAnagrafica(avo);
			dbh.close();
		}else{
			setAnagrafica(new AnagraficheVO());
		}

	}


	public void setAnagrafica(AnagraficheVO anagrafica) {
		this.anagrafica = anagrafica;
		
		if (wtw_citta != null){
			txtCitta.removeTextChangedListener(wtw_citta);
		}
		if (wtw_commento != null){
			txtCommento.removeTextChangedListener(wtw_commento);
		}
		if (wtw_indirizzo != null){
			txtIndirizzo.removeTextChangedListener(wtw_indirizzo);
		}
		if (wtw_cognome != null){
			txtCognome.removeTextChangedListener(wtw_cognome);
		}
		if (wtw_provincia != null){
			txtProvincia.removeTextChangedListener(wtw_provincia);
		}
		if (wtw_cap != null){
			txtCap.removeTextChangedListener(wtw_cap);
		}
		if (wtw_nome != null){
			txtNome.removeTextChangedListener(wtw_nome);
		}
		if (wtw_ragionesociale != null){
			txtRagioneSociale.removeTextChangedListener(wtw_ragionesociale);
		}
		if (wtw_piva != null){
			txtPiva.removeTextChangedListener(wtw_piva);
		}
		if (wtw_codicefiscale != null){
			txtCodiceFiscale.removeTextChangedListener(wtw_codicefiscale);
		}
		
		txtCitta.setText(anagrafica.getCitta());
		txtCommento.setText(anagrafica.getCommento());
		txtIndirizzo.setText(anagrafica.getIndirizzo());
		txtCognome.setText(anagrafica.getCognome());
		txtProvincia.setText(anagrafica.getProvincia());
		txtCap.setText(anagrafica.getCap());
		txtNome.setText(anagrafica.getNome());
		txtRagioneSociale.setText(anagrafica.getRagioneSociale());
		txtPiva.setText(anagrafica.getPartitaIva());
		txtCodiceFiscale.setText(anagrafica.getCodiceFiscale());
		
		wtw_citta = new WinkTextWatcher(this.anagrafica, "setCitta", String.class);
		txtCitta.addTextChangedListener(wtw_citta);
		
		wtw_commento = new WinkTextWatcher(this.anagrafica, "setCommento", String.class);
		txtCommento.addTextChangedListener(wtw_commento);
		
		wtw_indirizzo = new WinkTextWatcher(this.anagrafica, "setIndirizzo", String.class);
		txtIndirizzo.addTextChangedListener(wtw_indirizzo);
		
		wtw_cognome = new WinkTextWatcher(this.anagrafica, "setCognome", String.class);
		txtCognome.addTextChangedListener(wtw_cognome);
		
		wtw_provincia = new WinkTextWatcher(this.anagrafica, "setProvincia", String.class);
		txtProvincia.addTextChangedListener(wtw_provincia);
		
		wtw_cap = new WinkTextWatcher(this.anagrafica, "setCap", String.class);
		txtCap.addTextChangedListener(wtw_cap);
		
		wtw_nome = new WinkTextWatcher(this.anagrafica, "setNome", String.class);
		txtNome.addTextChangedListener(wtw_nome);
		
		wtw_ragionesociale = new WinkTextWatcher(this.anagrafica, "setRagioneSociale", String.class);
		txtRagioneSociale.addTextChangedListener(wtw_ragionesociale);
		
		wtw_piva = new WinkTextWatcher(this.anagrafica, "setPartitaIva", String.class);
		txtPiva.addTextChangedListener(wtw_piva);
		
		wtw_codicefiscale = new WinkTextWatcher(this.anagrafica, "setCodiceFiscale", String.class);
		txtCodiceFiscale.addTextChangedListener(wtw_codicefiscale);
		
		
		sp_classe_cliente.setSelection(get_al_classi_cliente_index());
		sp_classe_cliente.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				ClassiClientiVO cevo = (ClassiClientiVO)arg0.getItemAtPosition(arg2);
				getAnagrafica().setCodClasseCliente(cevo.getCodClasseCliente());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});

	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		view = inflater.inflate(R.layout.dettaglio_anagrafica,container,false);
		
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		
		animazione_rotazione_out = AnimationUtils.loadAnimation(getActivity().getBaseContext(), R.anim.activity_close_scale);
		dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		getActivity().overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);
		
//        ActionBar ab = getActivity().getActionBar();
//		ab.setDisplayHomeAsUpEnabled(true);

		ImageButton btn_openlmenu = (ImageButton)view.findViewById(R.id.btn_openlmenu);
		btn_openlmenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				v.startAnimation(animazione_rotazione_out);
				v.postDelayed(new Runnable() {
					@Override
					public void run() {
						DrawerLayout mDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
						mDrawerLayout.openDrawer(Gravity.LEFT);
					}
				}, 1200);

			}
		});


		ImageButton btn_salva = (ImageButton)view.findViewById(R.id.btn_salva);
		btn_salva.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				v.startAnimation(animazione_rotazione_out);
				v.postDelayed(new Runnable() {
					@Override
					public void run() {

						DataBaseHelper dbh = new DataBaseHelper(view.getContext(),DataBaseHelper.WRITE_DB);
						if (anagrafica != null && canSave()){
							if (dbh.saveAnagrafica(anagrafica)){
								AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext(),AlertDialog.THEME_HOLO_DARK);
								alertDialog.setTitle("Risultato salvataggio");
								alertDialog.setMessage("Salvataggio anagrafica avvenuto con successo");
								alertDialog.setIcon(R.drawable.adept_commit);
								alertDialog.setNegativeButton("Chiudi", null);
								alertDialog.show();
							}else{
								AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext(),AlertDialog.THEME_HOLO_DARK);
								alertDialog.setTitle("Risultato salvataggio");
								alertDialog.setMessage("Salvataggio anagrafica non avvenuto");
								alertDialog.setIcon(R.drawable.button_cancel);
								alertDialog.setNegativeButton("Chiudi", null);
								alertDialog.show();
							}

						}else{
							Toast.makeText(getActivity().getBaseContext(), "Campo Cognome o Nome o Ragione sociale obbligatorio",Toast.LENGTH_SHORT).show();
						}
						dbh.close();
					}
				}, 1200);

			}
		});
		
		ImageButton btn_cancella = (ImageButton)view.findViewById(R.id.btn_cancella);
		btn_cancella.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				v.startAnimation(animazione_rotazione_out);
				v.postDelayed(new Runnable() {
					@Override
					public void run() {

						if (anagrafica.getCodAnagrafica() != null && anagrafica.getCodAnagrafica() != 0){
							AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext(),AlertDialog.THEME_HOLO_DARK);
							alertDialog.setTitle("Conferma cancellazione ...");
							alertDialog.setMessage("Procedere con la cancellazione dell'anagrafica ?");
							alertDialog.setIcon(R.drawable.help);

							HashSet<Integer> arr_param = new HashSet<Integer>();
							arr_param.add(anagrafica.getCodAnagrafica());
							alertDialog.setPositiveButton("SI", new DialogAnagraficheCancellaListener(arr_param,view.getContext(),null,null));
							alertDialog.setNegativeButton("NO", null);
							alertDialog.show();
						}else{
							Toast.makeText(view.getContext(), "Impossibile cancellare una anagrafica non salvata",Toast.LENGTH_SHORT).show();
						}
					}
				}, 1200);
			}
		});
			
		ImageButton btn_annulla = (ImageButton)view.findViewById(R.id.btn_annulla);
		btn_annulla.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				v.startAnimation(animazione_rotazione_out);
				v.postDelayed(new Runnable() {
					@Override
					public void run() {

						AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext(), AlertDialog.THEME_HOLO_DARK);
						alertDialog.setTitle("Conferma annullamento modifiche ...");
						alertDialog.setMessage("Procedere con l'annullamento delle modifiche apportate all'anagrafica ?");
						alertDialog.setIcon(R.drawable.help);
						alertDialog.setPositiveButton("SI", new DialogAnagraficheAnnullaListener(DettaglioAnagraficaFragment.this, anagrafica, view.getContext()));
						alertDialog.setNegativeButton("NO", null);
						alertDialog.show();
					}
				},1200);
				
			}
		});
		
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		
		TextView labelProvincia = (TextView)view.findViewById(R.id.labelProvincia);
		labelProvincia.setWidth((dm.widthPixels-2)/2);
		TextView labelCap = (TextView)view.findViewById(R.id.labelCap);
		labelCap.setWidth((dm.widthPixels-2)/2);
		
		txtProvincia = (EditText)view.findViewById(R.id.txtProvincia);
		txtProvincia.addTextChangedListener(new WinkTextWatcher(this.anagrafica, "setProvincia", String.class));
		txtProvincia.setWidth((dm.widthPixels-2)/2);
		txtProvincia.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus){
					txtProvincia.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
				}else{
					txtProvincia.getBackground().setColorFilter(Color.parseColor("#f1ebeb"), PorterDuff.Mode.SRC_IN);
				}
				
			}
		});
		txtProvincia.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				openGeoRicercaPopup(v.getContext());
				return true;
			}
		});
		
		txtCap = (EditText)view.findViewById(R.id.txtCap);
		txtCap.addTextChangedListener(new WinkTextWatcher(this.anagrafica, "setCap", String.class));		
		txtCap.setWidth((dm.widthPixels-2)/2);
		txtCap.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus){
					txtCap.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
				}else{
					txtCap.getBackground().setColorFilter(Color.parseColor("#f1ebeb"), PorterDuff.Mode.SRC_IN);
				}
				
			}
		});
		txtCap.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				openGeoRicercaPopup(v.getContext());
				return true;
			}
		});

		txtCognome = (EditText)view.findViewById(R.id.txtCognome);
		txtCognome.addTextChangedListener(new WinkTextWatcher(this.anagrafica, "setCognome", String.class));
		txtCognome.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus){
					txtCognome.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
				}else{
					txtCognome.getBackground().setColorFilter(Color.parseColor("#f1ebeb"), PorterDuff.Mode.SRC_IN);
				}
				
			}
		});

		txtCitta = (EditText)view.findViewById(R.id.txtCitta);
		txtCitta.addTextChangedListener(new WinkTextWatcher(this.anagrafica, "setCitta", String.class));
		txtCitta.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus){
					txtCitta.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
				}else{
					txtCitta.getBackground().setColorFilter(Color.parseColor("#f1ebeb"), PorterDuff.Mode.SRC_IN);
				}
				
			}
		});
		txtCitta.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				openGeoRicercaPopup(v.getContext());
				return true;
			}
		});

		txtNome = (EditText)view.findViewById(R.id.txtNome);
		txtNome.addTextChangedListener(new WinkTextWatcher(this.anagrafica, "setNome", String.class));
		txtNome.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus){
					txtNome.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
				}else{
					txtNome.getBackground().setColorFilter(Color.parseColor("#f1ebeb"), PorterDuff.Mode.SRC_IN);
				}
				
			}
		});

		txtCommento = (EditText)view.findViewById(R.id.txtDescrizione);
		txtCommento.addTextChangedListener(new WinkTextWatcher(this.anagrafica, "setCommento", String.class));
		txtCommento.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus){
					txtCommento.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
				}else{
					txtCommento.getBackground().setColorFilter(Color.parseColor("#f1ebeb"), PorterDuff.Mode.SRC_IN);
				}
				
			}
		});

		txtIndirizzo = (EditText)view.findViewById(R.id.txtIndirizzo);
		txtIndirizzo.addTextChangedListener(new WinkTextWatcher(this.anagrafica, "setIndirizzo", String.class));
		txtIndirizzo.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus){
					txtIndirizzo.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
				}else{
					txtIndirizzo.getBackground().setColorFilter(Color.parseColor("#f1ebeb"), PorterDuff.Mode.SRC_IN);
				}
				
			}
		});

		txtRagioneSociale = (EditText)view.findViewById(R.id.txtRagioneSociale);
		txtRagioneSociale.addTextChangedListener(new WinkTextWatcher(this.anagrafica, "setRagioneSociale", String.class));
		txtRagioneSociale.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus){
					txtRagioneSociale.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
				}else{
					txtRagioneSociale.getBackground().setColorFilter(Color.parseColor("#f1ebeb"), PorterDuff.Mode.SRC_IN);
				}
				
			}
		});

		txtNome = (EditText)view.findViewById(R.id.txtNome);
		txtNome.addTextChangedListener(new WinkTextWatcher(this.anagrafica, "setNome", String.class));
		txtNome.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus){
					txtNome.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
				}else{
					txtNome.getBackground().setColorFilter(Color.parseColor("#f1ebeb"), PorterDuff.Mode.SRC_IN);
				}
				
			}
		});

		txtPiva = (EditText)view.findViewById(R.id.txtPiva);
		txtPiva.addTextChangedListener(new WinkTextWatcher(this.anagrafica, "setPartitaIva", String.class));
		txtPiva.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus){
					txtPiva.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
				}else{
					txtPiva.getBackground().setColorFilter(Color.parseColor("#f1ebeb"), PorterDuff.Mode.SRC_IN);
				}
				
			}
		});

		txtCodiceFiscale = (EditText)view.findViewById(R.id.txtCodiceFiscale);
		txtCodiceFiscale.addTextChangedListener(new WinkTextWatcher(this.anagrafica, "setCodiceFiscale", String.class));
		txtCodiceFiscale.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus){
					txtCodiceFiscale.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
				}else{
					txtCodiceFiscale.getBackground().setColorFilter(Color.parseColor("#f1ebeb"), PorterDuff.Mode.SRC_IN);
				}
				
			}
		});

		sp_classe_cliente = (Spinner)view.findViewById(R.id.cmb_classe);
		sp_classe_cliente.setPopupBackgroundResource(R.drawable.spinner_bckground);
		sp_classe_cliente.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus){
					sp_classe_cliente.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
				}else{
					sp_classe_cliente.getBackground().setColorFilter(Color.parseColor("#f1ebeb"), PorterDuff.Mode.SRC_IN);
				}
				
			}
		});
		
		DataBaseHelper dbh = new DataBaseHelper(getActivity().getBaseContext(),DataBaseHelper.READ_DB);
		HashMap column_list = new HashMap();
		
		column_list.put(ClassiClienteColumnNames.CODCLASSECLIENTE, null);
		column_list.put(ClassiClienteColumnNames.ORDINE, null);
		column_list.put(ClassiClienteColumnNames.DESCRIZIONE, null);
		
		ArrayList<ClassiClientiVO> tmp = dbh.getAllClassiClienti(column_list);
		
		dbh.getSqllitedb().close();
		
		al_classi_cliente = new ArrayList<ClassiClientiVO>();
		al_classi_cliente.add(new ClassiClientiVO());
		al_classi_cliente.addAll(tmp);
		
		CustomSpinnerAdapter icsa = new CustomSpinnerAdapter(getActivity().getBaseContext(), 
															 R.layout.custom_combo_detail, 
															 al_classi_cliente.toArray());
		
		sp_classe_cliente.setAdapter(icsa);
		dbh.close();
		
		return view;
	}
	
    public void openGeoRicercaPopup(Context c){
		
		dialog = new Dialog(c);		
		
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.geo_ricerca);
		
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
	    lp.copyFrom(dialog.getWindow().getAttributes());
	    lp.width = WindowManager.LayoutParams.WRAP_CONTENT;	
	    
	    
	    LinearLayout ll = (LinearLayout)dialog.findViewById(R.id.dialog_listageo_layout);
	    ll.getLayoutParams().width = (dm.widthPixels - (dm.widthPixels/10 * 2));
	    ll.getLayoutParams().height = (dm.heightPixels - (dm.heightPixels/10 * 2));

//	    LinearLayout lltol = (LinearLayout)dialog.findViewById(R.id.winktoolbar);
//	    lltol.getLayoutParams().width = (dm.widthPixels - (dm.widthPixels/10 * 2));

	    
	    cittafind = (TextView)dialog.findViewById(R.id.txtCitta);
	    cittafind.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus){
					cittafind.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
				}else{
					cittafind.getBackground().setColorFilter(Color.parseColor("#f1ebeb"), PorterDuff.Mode.SRC_IN);
				}
				
			}
		});
	    cittafind.setText(txtCitta.getText());
	    
	    listViewGeo = (ListView)dialog.findViewById(R.id.listViewGeo);
	    listViewGeo.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				GeoVO geo = (GeoVO)arg0.getItemAtPosition(arg2);
				txtCitta.setText(geo.getCitta());
				txtProvincia.setText(geo.getProvincia());
				txtCap.setText(geo.getCap());
				dialog.dismiss();


				
			}
	    	
		});
	    
	    listGeo = new ArrayList<GeoVO>();	     
	     
	    adapterGeo = new ListaGeoAdapter(getActivity().getBaseContext(), 
	    								 R.layout.dettaglio_lista_geo, 
	    								 listGeo);
	    
	    listViewGeo.setAdapter(adapterGeo);	
	    

		ImageButton cerca = (ImageButton)dialog.findViewById(R.id.btn_cerca);
		cerca.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				v.startAnimation(animazione_rotazione_out);
				NetworkUtils nu = new NetworkUtils();
				if (nu.isOnline(getActivity().getBaseContext())){
									
					if (cittafind.getText().toString().trim() != ""){
						
						GeoFinderThread gft = new GeoFinderThread(v.getContext(), 
																  listGeo, 
																  listViewGeo, 
																  adapterGeo, 
																  cittafind.getText().toString());
						
						gft.execute("");
					}else{
						Toast.makeText(getActivity().getBaseContext(), "Inserire una città", Toast.LENGTH_SHORT).show();
					}
					
				}else{
					Toast.makeText(getActivity().getBaseContext(), "Impossibile accedere ad internet", Toast.LENGTH_SHORT).show();
				}
				
			}
				
				
		});
				
		ImageButton chiudi = (ImageButton)dialog.findViewById(R.id.btn_chiudi);
		chiudi.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				v.startAnimation(animazione_rotazione_out);
				dialog.dismiss();
			}
		});
		
		dialog.show();
		dialog.getWindow().setAttributes(lp);
				
		
	}
    
    @Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		
		inflater.inflate(R.menu.menu_dettaglio_anagrafica, menu);	    
	    super.onCreateOptionsMenu(menu,inflater);
	}
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int itemId = item.getItemId();
		
		if (itemId == android.R.id.home){
			
			NavUtils.navigateUpFromSameTask(this.getActivity());
			
		}else if (itemId == R.id.contatti) {
			
			if (anagrafica.getCodAnagrafica() != 0){
				Intent lista_contatti = new Intent(getActivity().getBaseContext(), ListaContattiActivity.class);				
				lista_contatti.putExtra(AnagraficheColumnNames.CODANAGRAFICA, anagrafica.getCodAnagrafica());
				startActivity(lista_contatti);			
			}else{
				Toast.makeText(getActivity().getBaseContext(), "Codice anagrafica mancante, salvare", Toast.LENGTH_SHORT).show();
			}
			
		}else if (itemId == R.id.immobili_anagrafica) {
			
			if (anagrafica.getCodAnagrafica() != 0){
					Intent it = new Intent(getActivity().getBaseContext(), ListaImmobiliActivity.class);	
					it.putExtra(ActivityMessages.IMMOBILI_PROPIETA_ACTION,true);
					it.putExtra(ActivityMessages.CODANAGRAFICA_PROPIETARI_ACTION,anagrafica.getCodAnagrafica());												
					startActivity(it);											
			}else{
				Toast.makeText(getActivity().getBaseContext(), "Codice anagrafica mancante, salvare", Toast.LENGTH_SHORT).show();												
			}
			
		}else if (itemId == R.id.mappa) {
			
			  Intent intent = new Intent(Intent.ACTION_VIEW);
			  
			  Uri i = Uri.parse("geo:0,0?q="+ anagrafica.getIndirizzo() + "+" + anagrafica.getCitta() + "+" + anagrafica.getProvincia());
			  
			  String tmp = i.toString();
			  intent.setData(i);
			  if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
				  startActivity(intent);
			  }else{
					Toast.makeText(getActivity().getBaseContext(), "Impossibile trovare l'applicazione Maps di Google", Toast.LENGTH_SHORT).show();																	  
			  }
			
		}
					
		return true;
	}
	


}
