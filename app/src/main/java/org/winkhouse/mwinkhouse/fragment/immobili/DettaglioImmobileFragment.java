package org.winkhouse.mwinkhouse.fragment.immobili;

import java.util.ArrayList;
import java.util.HashMap;

import org.winkhouse.mwinkhouse.R;
import org.winkhouse.mwinkhouse.activity.adapters.CustomSpinnerAdapter;
import org.winkhouse.mwinkhouse.activity.adapters.ListaGeoAdapter;
import org.winkhouse.mwinkhouse.activity.anagrafiche.ListaAnagraficheActivity;
import org.winkhouse.mwinkhouse.activity.immagini.ImmobiliImmaginiGalleryActivity;
import org.winkhouse.mwinkhouse.activity.immobili.DettaglioImmobileActivity;
import org.winkhouse.mwinkhouse.activity.immobili.RicercaImmobiliActivity;
import org.winkhouse.mwinkhouse.activity.listeners.DialogImmobiliAnnullaListener;
import org.winkhouse.mwinkhouse.activity.listeners.DialogImmobiliCancellaListener;
import org.winkhouse.mwinkhouse.activity.listeners.WinkTextWatcher;
import org.winkhouse.mwinkhouse.helpers.DataBaseHelper;
import org.winkhouse.mwinkhouse.helpers.GeoFinderThread;
import org.winkhouse.mwinkhouse.models.AnagraficheVO;
import org.winkhouse.mwinkhouse.models.ClasseEnergeticaVO;
import org.winkhouse.mwinkhouse.models.GeoVO;
import org.winkhouse.mwinkhouse.models.ImmobiliVO;
import org.winkhouse.mwinkhouse.models.RiscaldamentiVO;
import org.winkhouse.mwinkhouse.models.StatoConservativoVO;
import org.winkhouse.mwinkhouse.models.TipologieImmobiliVO;
import org.winkhouse.mwinkhouse.models.columns.ClasseEnergeticaColumnNames;
import org.winkhouse.mwinkhouse.models.columns.ImmobiliColumnNames;
import org.winkhouse.mwinkhouse.models.columns.RiscaldamentiColumnNames;
import org.winkhouse.mwinkhouse.models.columns.StatoConservativoColumnNames;
import org.winkhouse.mwinkhouse.models.columns.TipologieImmobiliColumnNames;
import org.winkhouse.mwinkhouse.util.ActivityMessages;
import org.winkhouse.mwinkhouse.util.NetworkUtils;

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
import android.text.InputType;
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
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.googleapis.testing.json.GoogleJsonResponseExceptionFactoryTesting;

public class DettaglioImmobileFragment extends Fragment {

	private EditText txtProvincia = null;
	private EditText txtCap = null;
	private EditText txtMq = null;
	private EditText txtAnnoCostruzione = null;
	private EditText txtCitta = null;
	private EditText txtDescrizione = null;
	private EditText txtIndirizzo = null;
	private EditText txtZona = null;
	private EditText txtPrezzo = null;
	private Spinner sp_classe_energetica = null;
	private Spinner sp_riscaldamenti = null;
	private Spinner sp_tipo_immobile = null;
	private Spinner sp_statoconservativo = null;
	private final Spinner sp_anagrafiche = null;
	
	private ImmobiliVO immobile = null;
	
	private ArrayList<StatoConservativoVO> al_statoconservativo = null;
	private ArrayList<ClasseEnergeticaVO> al_classi_energetiche = null;
	private ArrayList<RiscaldamentiVO> al_riscaldamenti = null;
	private ArrayList<TipologieImmobiliVO> al_tipi_immobili = null;
	private final ArrayList<AnagraficheVO> al_anagrafiche = null;
	
	private WinkTextWatcher wtw_citta = null;
	private WinkTextWatcher wtw_descrizione = null;
	private WinkTextWatcher wtw_indirizzo = null;
	private WinkTextWatcher wtw_zona = null;
	private WinkTextWatcher wtw_prezzo = null;
	private WinkTextWatcher wtw_provincia = null;
	private WinkTextWatcher wtw_cap = null;
	private WinkTextWatcher wtw_mq = null;
	private WinkTextWatcher wtw_annocostruzione = null;

	Animation animazione_rotazione_out = null;

	private Dialog dialog = null;
	private ListView listViewGeo = null;
	private ArrayList<GeoVO> listGeo = null;
	private DisplayMetrics dm = null;
	
	private ListaGeoAdapter adapterGeo = null;
	private TextView cittafind = null;	
	
	private View view = null;
	
	public DettaglioImmobileFragment() {}

    public void openGeoRicercaPopup(Context c){
		
		dialog = new Dialog(c);		
		
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.geo_ricerca);
		
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
	    lp.copyFrom(dialog.getWindow().getAttributes());
	    lp.width = WindowManager.LayoutParams.WRAP_CONTENT;	
	    
	    
	    LinearLayout ll = dialog.findViewById(R.id.dialog_listageo_layout);
	    ll.getLayoutParams().width = (dm.widthPixels - (dm.widthPixels/10 * 2));
	    ll.getLayoutParams().height = (dm.heightPixels - (dm.heightPixels/10 * 2));

//	    LinearLayout lltol = (LinearLayout)dialog.findViewById(R.id.winktoolbar);
//	    lltol.getLayoutParams().width = (dm.widthPixels - (dm.widthPixels/10 * 2));

	    
	    cittafind = dialog.findViewById(R.id.txtCitta);
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
	    
	    listViewGeo = dialog.findViewById(R.id.listViewGeo);
	    
	    listViewGeo.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				GeoVO geo = (GeoVO)arg0.getItemAtPosition(arg2);
				txtCitta.setText(geo.getCitta());
				txtProvincia.setText(geo.getProvincia());
				txtCap.setText(geo.getCap());
				dialog.dismiss();
				return false;
			}
	    	
	    	
	    	
		});
	    
	    listGeo = new ArrayList<GeoVO>();	     
	     
	    adapterGeo = new ListaGeoAdapter(getActivity().getBaseContext(), 
	    								 R.layout.dettaglio_lista_geo, 
	    								 listGeo);
	    
	    listViewGeo.setAdapter(adapterGeo);	
	    

		ImageButton cerca = dialog.findViewById(R.id.btn_cerca);
		cerca.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				v.startAnimation(animazione_rotazione_out);
				NetworkUtils nu = new NetworkUtils();
				if (nu.isOnline(getActivity().getBaseContext())){
									
					if (cittafind.getText().toString().trim() != ""){
						
						GeoFinderThread gft = new GeoFinderThread(dialog.getContext(), 
																  listGeo, 
																  listViewGeo, 
																  adapterGeo, 
																  cittafind.getText().toString());
						
						gft.execute();
					}else{
						Toast.makeText(getActivity().getBaseContext(), "Inserire una città", Toast.LENGTH_SHORT).show();
					}
					
				}else{
					Toast.makeText(getActivity().getBaseContext(), "Impossibile accedere ad internet", Toast.LENGTH_SHORT).show();
				}
				
			}
				
				
		});
				
		ImageButton chiudi = dialog.findViewById(R.id.btn_chiudi);
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
	
	private void getData(){
		Integer codImmobile = getArguments().getInt(ImmobiliColumnNames.CODIMMOBILE, 0);
		if (codImmobile != 0){
			DataBaseHelper dbh = new DataBaseHelper(view.getContext(),DataBaseHelper.NONE_DB);
			ImmobiliVO ivo = dbh.getImmobileById(codImmobile, null);
			ivo = (ivo != null) ? ivo : new ImmobiliVO(); 
			setImmobileVO(ivo);
			dbh.close();
		}else{
			setImmobileVO(new ImmobiliVO());
		}
	}
	
	private boolean canSave(){
		
		boolean returnValue = false;
		
		if (immobile != null){
            returnValue = !immobile.getIndirizzo().trim().equalsIgnoreCase("");
		}else{
			returnValue = false;
		}
		
		return returnValue;
		
	}

	public void setImmobileVO(ImmobiliVO immobile){
		this.immobile = immobile;

		if (wtw_citta != null){
			txtCitta.removeTextChangedListener(wtw_citta);
		}

		if (wtw_prezzo != null){
			txtPrezzo.removeTextChangedListener(wtw_prezzo);
		}

		if (wtw_descrizione != null){
			txtDescrizione.removeTextChangedListener(wtw_descrizione);
		}
		
		if (wtw_indirizzo != null){
			txtIndirizzo.removeTextChangedListener(wtw_indirizzo);
		}
		
		if (wtw_zona != null){
			txtZona.removeTextChangedListener(wtw_zona);
		}
		
		if (wtw_provincia != null){
			txtProvincia.removeTextChangedListener(wtw_provincia);
		}
		
		if (wtw_cap != null){
			txtCap.removeTextChangedListener(wtw_cap);
		}
		
		if (wtw_mq != null){
			txtMq.removeTextChangedListener(wtw_mq);
		}
		
		if (wtw_annocostruzione != null){
			txtAnnoCostruzione.removeTextChangedListener(wtw_annocostruzione);
		}

		
		txtProvincia.setText(this.immobile.getProvincia());
		txtCap.setText(this.immobile.getCap());
		txtMq.setText(String.valueOf(this.immobile.getMq()));
		txtAnnoCostruzione.setText(String.valueOf(this.immobile.getAnnoCostruzione()));
		txtCitta.setText(this.immobile.getCitta());
		txtDescrizione.setText(this.immobile.getDescrizione());
		txtIndirizzo.setText(this.immobile.getIndirizzo());
		txtZona.setText(this.immobile.getZona());
		txtPrezzo.setText(this.immobile.getPrezzo().toString());
		
		wtw_citta = new WinkTextWatcher(this.immobile, "setCitta", String.class);
		txtCitta.addTextChangedListener(wtw_citta);
		
		wtw_descrizione = new WinkTextWatcher(this.immobile, "setDescrizione", String.class);
		txtDescrizione.addTextChangedListener(wtw_descrizione);
		
		wtw_indirizzo = new WinkTextWatcher(this.immobile, "setIndirizzo", String.class);		
		txtIndirizzo.addTextChangedListener(wtw_indirizzo);
				
		wtw_zona = new WinkTextWatcher(this.immobile, "setZona", String.class);
		txtZona.addTextChangedListener(wtw_zona);
		
		wtw_provincia = new WinkTextWatcher(this.immobile, "setProvincia", String.class);
		txtProvincia.addTextChangedListener(wtw_provincia);
		
		wtw_cap = new WinkTextWatcher(this.immobile, "setCap", String.class);
		txtCap.addTextChangedListener(wtw_cap);
		
		wtw_mq = new WinkTextWatcher(this.immobile, "setMq", Integer.class);
		txtMq.addTextChangedListener(wtw_mq);
		
		wtw_prezzo = new WinkTextWatcher(this.immobile, "setPrezzo", Double.class);
		txtPrezzo.addTextChangedListener(wtw_prezzo);
		
		wtw_annocostruzione = new WinkTextWatcher(this.immobile, "setAnnoCostruzione", Integer.class);
		txtAnnoCostruzione.addTextChangedListener(wtw_annocostruzione);

		
		sp_classe_energetica.setSelection(get_al_classi_energetiche_index());
		sp_classe_energetica.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				ClasseEnergeticaVO cevo = (ClasseEnergeticaVO)arg0.getItemAtPosition(arg2);
				getImmobileVO().setCodClasseEnergetica(cevo.getCodClasseEnergetica());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		sp_statoconservativo.setSelection(get_al_statoconservativo_index());
		sp_statoconservativo.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				StatoConservativoVO scvo = (StatoConservativoVO)arg0.getItemAtPosition(arg2);
				getImmobileVO().setCodStato(scvo.getCodStatoConservativo());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		sp_riscaldamenti.setSelection(get_al_riscaldamenti_index());
		sp_riscaldamenti.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				RiscaldamentiVO rvo = (RiscaldamentiVO)arg0.getItemAtPosition(arg2);
				getImmobileVO().setCodRiscaldamento(rvo.getCodRiscaldamento());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		sp_tipo_immobile.setSelection(get_al_tipi_immobili_index());
		sp_tipo_immobile.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				TipologieImmobiliVO tivo = (TipologieImmobiliVO)arg0.getItemAtPosition(arg2);
				getImmobileVO().setCodTipologia(tivo.getCodTipologiaImmobile());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
//		sp_anagrafiche.setSelection(get_al_anagrafiche_index());
//		sp_anagrafiche.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//			@Override
//			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//				AnagraficheVO avo = (AnagraficheVO)arg0.getItemAtPosition(arg2);
//				((DettaglioImmobileActivity)arg0.getContext()).getImmobileVO().setCodAnagrafica(avo.getCodAnagrafica());
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//		});
		
		
	}
	
	public ImmobiliVO getImmobileVO() {
		return immobile;
	}

	protected int get_al_statoconservativo_index(){
		int count = 0;
		for (StatoConservativoVO statoconservativo : al_statoconservativo) {
			if (statoconservativo.getCodStatoConservativo() == immobile.getCodStato()){
				return count;
			}else{
				count++;
			}
		}
		return 0;
	}

	protected int get_al_classi_energetiche_index(){
		int count = 0;
		for (ClasseEnergeticaVO classeenergetica : al_classi_energetiche) {
			if (classeenergetica.getCodClasseEnergetica() == immobile.getCodClasseEnergetica()){
				return count;
			}else{
				count++;
			}
		}
		return 0;
	}

	protected int get_al_riscaldamenti_index(){
		int count = 0;
		for (RiscaldamentiVO riscaldamenti : al_riscaldamenti) {
			if (riscaldamenti.getCodRiscaldamento() == immobile.getCodRiscaldamento()){
				return count;
			}else{
				count++;
			}
		}
		return 0;
	}

	protected int get_al_tipi_immobili_index(){
		int count = 0;
		for (TipologieImmobiliVO tipologieimmobili : al_tipi_immobili) {
			if (tipologieimmobili.getCodTipologiaImmobile() == immobile.getCodTipologia()){
				return count;
			}else{
				count++;
			}
		}
		return 0;
	}
	
//	protected int get_al_anagrafiche_index(){
//		int count = 0;
//		for (AnagraficheVO anagrafica : al_anagrafiche) {
//			if (anagrafica.getCodAnagrafica() == immobile.getCodAnagrafica()){
//				return count;
//			}else{
//				count++;
//			}
//		}
//		return 0;
//	}
	class SalvaRunner implements Runnable{

		Context c = null;
		public SalvaRunner(Context c){
			this.c = c;
		}

		@Override
		public void run() {

			DataBaseHelper dbh = new DataBaseHelper(c, DataBaseHelper.NONE_DB);
			if (immobile != null && canSave()) {
				if (dbh.saveImmobile(immobile)) {
					((DettaglioImmobileActivity) getActivity()).setCodImmobile(immobile.getCodImmobile());
					AlertDialog.Builder alertDialog = new AlertDialog.Builder(c, AlertDialog.THEME_HOLO_DARK);
					alertDialog.setTitle("Risultato salvataggio");
					alertDialog.setMessage("Salvataggio immobile avvenuto con successo");
					alertDialog.setIcon(R.drawable.adept_commit);
					alertDialog.setPositiveButton("Chiudi", null);
					alertDialog.show();
				} else {
					AlertDialog.Builder alertDialog = new AlertDialog.Builder(c, AlertDialog.THEME_HOLO_DARK);
					alertDialog.setTitle("Risultato salvataggio");
					alertDialog.setMessage("Salvataggio immobile non avvenuto");
					alertDialog.setIcon(R.drawable.button_cancel);
					alertDialog.setPositiveButton("Chiudi", null);
					alertDialog.show();

				}

			} else {
				Toast.makeText(c, "Campo indirizzo obbligatorio", Toast.LENGTH_SHORT).show();
			}
			dbh.close();

		}

	}

    class CancellaRunner implements Runnable{

        Context c = null;
        public CancellaRunner(Context c){
            this.c = c;
        }

        @Override
        public void run() {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(c,AlertDialog.THEME_HOLO_DARK);
            alertDialog.setTitle("Conferma cancellazione ...");
            alertDialog.setMessage("Procedere con la cancellazione dell'immobile ?");
            alertDialog.setIcon(R.drawable.help);
            ArrayList<Integer> alcod = new ArrayList<Integer>();
            alcod.add(immobile.getCodImmobile());

            alertDialog.setPositiveButton("SI", new DialogImmobiliCancellaListener(alcod,c));
            alertDialog.setNegativeButton("NO", null);
            alertDialog.show();

        }

    }

    class AnnullaRunner implements Runnable{

        Context c = null;
        public AnnullaRunner(Context c){
            this.c = c;
        }

        @Override
        public void run() {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(c,AlertDialog.THEME_HOLO_DARK);
            alertDialog.setTitle("Conferma annullamento modifiche ...");
            alertDialog.setMessage("Procedere con l'annullamento delle modifiche apportate all'immobile ?");
            alertDialog.setIcon(R.drawable.help);
            alertDialog.setPositiveButton("SI", new DialogImmobiliAnnullaListener(DettaglioImmobileFragment.this,immobile,c));
            alertDialog.setNegativeButton("NO", null);
            alertDialog.show();

        }

    }

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,	Bundle savedInstanceState) {
		
		view = inflater.inflate(R.layout.dettaglio_immobile, container, false);
		
		super.onCreate(savedInstanceState);
		dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		setHasOptionsMenu(true);
		
		animazione_rotazione_out = AnimationUtils.loadAnimation(getActivity().getBaseContext(), R.anim.activity_close_scale);
		getActivity().overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);
		
//		ActionBar ab = getActivity().getActionBar();
//		ab.setDisplayHomeAsUpEnabled(true);
		
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

        ImageButton btn_openlmenu = view.findViewById(R.id.btn_openlmenu);
        btn_openlmenu.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                v.startAnimation(animazione_rotazione_out);
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        DrawerLayout mDrawerLayout = getActivity().findViewById(R.id.drawer_layout);
                        mDrawerLayout.openDrawer(Gravity.LEFT);
                    }
                }, 1200);

            }
        });

        ImageButton btn_salva = view.findViewById(R.id.btn_salva);
		btn_salva.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				v.startAnimation(animazione_rotazione_out);
                v.postDelayed(new SalvaRunner(v.getContext()),1200);
            }
        });



		ImageButton btn_cancella = view.findViewById(R.id.btn_cancella);
		btn_cancella.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				v.startAnimation(animazione_rotazione_out);
                v.postDelayed(new CancellaRunner(v.getContext()),1200);
            }
        });
		
		ImageButton btn_annulla = view.findViewById(R.id.btn_annulla);
		btn_annulla.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				v.startAnimation(animazione_rotazione_out);
                v.postDelayed(new AnnullaRunner(v.getContext()),1200);
			}
		});
		
		
		txtCitta = view.findViewById(R.id.txtCitta);
		txtCitta.addTextChangedListener(new WinkTextWatcher(this.immobile, "setCitta", String.class));
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
		
		
		txtDescrizione = view.findViewById(R.id.txtDescrizione);
		txtDescrizione.addTextChangedListener(new WinkTextWatcher(this.immobile, "setDescrizione", String.class));
		txtDescrizione.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus){
					txtDescrizione.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
				}else{
					txtDescrizione.getBackground().setColorFilter(Color.parseColor("#f1ebeb"), PorterDuff.Mode.SRC_IN);
				}
				
			}
		});
		
		txtIndirizzo = view.findViewById(R.id.txtIndirizzo);
		txtIndirizzo.addTextChangedListener(new WinkTextWatcher(this.immobile, "setIndirizzo", String.class));
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
		
		txtZona = view.findViewById(R.id.txtZona);
		txtZona.addTextChangedListener(new WinkTextWatcher(this.immobile, "setZona", String.class));
		txtZona.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus){
					txtZona.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
				}else{
					txtZona.getBackground().setColorFilter(Color.parseColor("#f1ebeb"), PorterDuff.Mode.SRC_IN);
				}
				
			}
		});
		
		txtPrezzo = view.findViewById(R.id.txtPrezzo);
        txtPrezzo.setInputType( InputType.TYPE_CLASS_NUMBER );
		txtPrezzo.addTextChangedListener(new WinkTextWatcher(this.immobile, "setPrezzo", Double.class));
		txtPrezzo.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus){
					txtPrezzo.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
				}else{
					txtPrezzo.getBackground().setColorFilter(Color.parseColor("#f1ebeb"), PorterDuff.Mode.SRC_IN);
				}
				
			}
		});
		
		TextView labelProvincia = view.findViewById(R.id.labelProvincia);
		labelProvincia.setWidth((dm.widthPixels/2));
		TextView labelCap = view.findViewById(R.id.labelCap);
		labelCap.setWidth((dm.widthPixels/2));

		txtProvincia = view.findViewById(R.id.txtProvincia);
		txtProvincia.setWidth((dm.widthPixels/2));
		txtProvincia.addTextChangedListener(new WinkTextWatcher(this.immobile, "setProvincia", String.class));
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

		
		txtCap = view.findViewById(R.id.txtCap);
		txtCap.setWidth((dm.widthPixels/2));
		txtCap.addTextChangedListener(new WinkTextWatcher(this.immobile, "setCap", String.class));
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
		
		TextView labelMq = view.findViewById(R.id.labelMq);
		labelMq.setWidth((dm.widthPixels/2));
		TextView labelAnnoCostruzione = view.findViewById(R.id.labelAnnoCostruzione);
		labelAnnoCostruzione.setWidth((dm.widthPixels/2));

		txtMq = view.findViewById(R.id.txtMq);
        txtMq.setInputType( InputType.TYPE_CLASS_NUMBER );
		txtMq.setWidth((dm.widthPixels/2));
		txtMq.addTextChangedListener(new WinkTextWatcher(this.immobile, "setMq", Integer.class));
		txtMq.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus){
					txtMq.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
				}else{
					txtMq.getBackground().setColorFilter(Color.parseColor("#f1ebeb"), PorterDuff.Mode.SRC_IN);
				}
				
			}
		});
		
		txtAnnoCostruzione = view.findViewById(R.id.txtAnnoCostruzione);
        txtAnnoCostruzione.setInputType( InputType.TYPE_CLASS_NUMBER );
		txtAnnoCostruzione.setWidth((dm.widthPixels/2));
		txtAnnoCostruzione.addTextChangedListener(new WinkTextWatcher(this.immobile, "setAnnoCostruzione", Integer.class));
		txtAnnoCostruzione.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus){
					txtAnnoCostruzione.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
				}else{
					txtAnnoCostruzione.getBackground().setColorFilter(Color.parseColor("#f1ebeb"), PorterDuff.Mode.SRC_IN);
				}
				
			}
		});
		
		sp_classe_energetica = view.findViewById(R.id.cmb_classe_energetica);
		sp_classe_energetica.setPopupBackgroundResource(R.drawable.spinner_bckground);
		sp_classe_energetica.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus){
					sp_classe_energetica.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
				}else{
					sp_classe_energetica.getBackground().setColorFilter(Color.parseColor("#f1ebeb"), PorterDuff.Mode.SRC_IN);
				}
				
			}
		});
		
		DataBaseHelper dbh = new DataBaseHelper(view.getContext(),DataBaseHelper.NONE_DB);
		HashMap column_list = new HashMap();
		
		column_list.put(ClasseEnergeticaColumnNames.CODCLASSEENERGETICA, null);
		column_list.put(ClasseEnergeticaColumnNames.NOME, null);
		column_list.put(ClasseEnergeticaColumnNames.DESCRIZIONE, null);
		
		ArrayList<ClasseEnergeticaVO> tmp = dbh.getAllClassiEnergetiche(column_list);
		al_classi_energetiche = new ArrayList<ClasseEnergeticaVO>();
		al_classi_energetiche.add(new ClasseEnergeticaVO());
		al_classi_energetiche.addAll(tmp);
		
		CustomSpinnerAdapter icsa = new CustomSpinnerAdapter(view.getContext(), 
															 R.layout.custom_combo_detail, 
															 al_classi_energetiche.toArray());
		
		sp_classe_energetica.setAdapter(icsa);
		
		sp_riscaldamenti = view.findViewById(R.id.cmb_riscaldamento);
		sp_riscaldamenti.setPopupBackgroundResource(R.drawable.spinner_bckground);
		sp_riscaldamenti.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus){
					sp_riscaldamenti.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
				}else{
					sp_riscaldamenti.getBackground().setColorFilter(Color.parseColor("#f1ebeb"), PorterDuff.Mode.SRC_IN);
				}
				
			}
		});
		
		column_list = new HashMap();
		
		column_list.put(RiscaldamentiColumnNames.CODRISCALDAMENTO, null);
		column_list.put(RiscaldamentiColumnNames.DESCRIZIONE, null);
		
		ArrayList<RiscaldamentiVO> tmp2 = dbh.getAllRiscaldamenti(column_list);
		al_riscaldamenti = new ArrayList<RiscaldamentiVO>();
		al_riscaldamenti.add(new RiscaldamentiVO());
		al_riscaldamenti.addAll(tmp2);
		
		CustomSpinnerAdapter icsa2 = new CustomSpinnerAdapter(view.getContext(), 
															  R.layout.custom_combo_detail, 
															  al_riscaldamenti.toArray());
		
		sp_riscaldamenti.setAdapter(icsa2);
		
		sp_tipo_immobile = view.findViewById(R.id.cmb_tipologia);
		sp_tipo_immobile.setPopupBackgroundResource(R.drawable.spinner_bckground);
		sp_tipo_immobile.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus){
					sp_tipo_immobile.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
				}else{
					sp_tipo_immobile.getBackground().setColorFilter(Color.parseColor("#f1ebeb"), PorterDuff.Mode.SRC_IN);
				}
				
			}
		});

		column_list = new HashMap();
		
		column_list.put(TipologieImmobiliColumnNames.CODTIPOLOGIAIMMOBILE, null);
		column_list.put(TipologieImmobiliColumnNames.DESCRIZIONE, null);
		
		ArrayList<TipologieImmobiliVO> tmp1 = dbh.getAllTipologieImmobili(column_list);
		al_tipi_immobili = new ArrayList<TipologieImmobiliVO>();
		al_tipi_immobili.add(new TipologieImmobiliVO());
		al_tipi_immobili.addAll(tmp1);
		
		CustomSpinnerAdapter icsa1 = new CustomSpinnerAdapter(view.getContext(), 
															  R.layout.custom_combo_detail, 
															  al_tipi_immobili.toArray());
		
		sp_tipo_immobile.setAdapter(icsa1);

		sp_statoconservativo = view.findViewById(R.id.cmb_stato);
		sp_statoconservativo.setPopupBackgroundResource(R.drawable.spinner_bckground);
		sp_statoconservativo.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus){
					sp_statoconservativo.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
				}else{
					sp_statoconservativo.getBackground().setColorFilter(Color.parseColor("#f1ebeb"), PorterDuff.Mode.SRC_IN);
				}
				
			}
		});
		
		column_list = new HashMap();
		
		column_list.put(StatoConservativoColumnNames.CODSTATOCONSERVATIVO, null);
		column_list.put(StatoConservativoColumnNames.DESCRIZIONE, null);
		
		ArrayList<StatoConservativoVO> tmp3 = dbh.getAllStatoConservativo(column_list);
		al_statoconservativo = new ArrayList<StatoConservativoVO>();
		al_statoconservativo.add(new StatoConservativoVO());
		al_statoconservativo.addAll(tmp3);
		
		CustomSpinnerAdapter icsa3 = new CustomSpinnerAdapter(view.getContext(), 
															  R.layout.custom_combo_detail, 
															  al_statoconservativo.toArray());
		
		sp_statoconservativo.setAdapter(icsa3);

		dbh.close();
		dbh = null;
		
		
		return view;
		
		
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		
		inflater.inflate(R.menu.menu_dettaglio_immobile, menu);	    
	    super.onCreateOptionsMenu(menu,inflater);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int itemId = item.getItemId();
		
		if (itemId == android.R.id.home){
			NavUtils.navigateUpFromSameTask(this.getActivity());
		}else if (itemId == R.id.immagini) {
            if (immobile.getCodImmobile() != 0){
                Intent gallery_immobile = new Intent(view.getContext(), ImmobiliImmaginiGalleryActivity.class);

                gallery_immobile.putExtra(ImmobiliColumnNames.CODIMMOBILE, immobile.getCodImmobile());
                getActivity().startActivity(gallery_immobile);
            }else{
                Toast.makeText(view.getContext(), "Codice immobile mancante, salvare", Toast.LENGTH_SHORT).show();

            }
		}else if (itemId == R.id.propietario) {
			if (immobile.getCodImmobile() != 0){
				Intent it = new Intent(view.getContext(), ListaAnagraficheActivity.class);	
				it.putExtra(ActivityMessages.PROPIETARI_ACTION,true);
				it.putExtra(ActivityMessages.CODIMMOBILE_PROPIETARI_ACTION,immobile.getCodImmobile());												
				startActivity(it);											
			}else{
				Toast.makeText(view.getContext(), "Codice immobile mancante, salvare", Toast.LENGTH_SHORT).show();
			}			
		}else if (itemId == R.id.mappa) {
			  Intent intent = new Intent(Intent.ACTION_VIEW);
			  
			  Uri i = Uri.parse("geo:0,0?q="+ immobile.getIndirizzo() + "+" + immobile.getCitta() + "+" + immobile.getProvincia());
			  
			  String tmp = i.toString();
			  intent.setData(i);
			  if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
				  getActivity().startActivity(intent);
			  }else{
					Toast.makeText(view.getContext(), "Impossibile trovare l'applicazione Maps di Google", Toast.LENGTH_SHORT).show();																	  
			  }
		}
									
		return true;
	}


	@Override
	public void onResume() {
		super.onResume();
		getData();
	}

	@Override
	public void onStart() {
		super.onStart();
		getData();
	}

//	@Override
//	public void onBackPressed() {
//		finish();
//	}

	
}
