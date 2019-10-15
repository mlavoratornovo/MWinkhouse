package org.winkhouse.mwinkhouse.fragment.colloqui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import org.winkhouse.mwinkhouse.R;
import org.winkhouse.mwinkhouse.activity.adapters.CustomSpinnerAdapter;
import org.winkhouse.mwinkhouse.activity.colloqui.DettaglioColloquiActivity;
import org.winkhouse.mwinkhouse.activity.listeners.DialogColloquiAnnullaListener;
import org.winkhouse.mwinkhouse.activity.listeners.DialogColloquioCancellaListener;
import org.winkhouse.mwinkhouse.activity.listeners.WinkTextWatcher;
import org.winkhouse.mwinkhouse.helpers.DataBaseHelper;
import org.winkhouse.mwinkhouse.models.AnagraficheVO;
import org.winkhouse.mwinkhouse.models.ColloquiAnagraficheVO;
import org.winkhouse.mwinkhouse.models.ColloquiVO;
import org.winkhouse.mwinkhouse.models.TipologieColloquiVO;
import org.winkhouse.mwinkhouse.util.ActivityMessages;
import org.winkhouse.mwinkhouse.util.DatePickerFragment;
import org.winkhouse.mwinkhouse.util.TimePickerFragment;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class DettaglioColloquioFragment extends Fragment {
	
	private DisplayMetrics dm = null;
	private View view = null;
	private Animation animazione_rotazione_out = null;
	private ColloquiVO colloquio = null;
	
	private Spinner sp_tipicolloqui = null;
	private EditText txtData = null;
	private WinkTextWatcher w_txtData = null;
	private EditText txtOra = null;
	private WinkTextWatcher w_txtOra = null;
	private EditText txtDescrizione = null;
	private WinkTextWatcher w_txtDescrizione = null;
	private EditText txtAgenzia = null;
	private WinkTextWatcher w_txtAgenzia = null;
	private EditText txtCliente = null;
	private WinkTextWatcher w_txtCliente = null;
	
	private String SET_DATACOLLOQUIO_METODO = "setDataColloquio";
	private String GET_DATACOLLOQUIO_METODO = "getDataColloquio";
	
	private ArrayList<TipologieColloquiVO> al_sp_tipicolloqui = null;
	
	private Integer codAnagrafica 	= null;
	private Integer codColloquio 	= null;
	private Integer codImmobile 	= null;

	public DettaglioColloquioFragment() {}
	
	private boolean canSave(){
		if (txtData.getText().toString().equalsIgnoreCase("")){
			return false;
		}
		if (txtOra.getText().toString().equalsIgnoreCase("")){
			return false;
		}
		if ((colloquio.getCodTipologiaColloquio() == null) || (colloquio.getCodTipologiaColloquio() == 0)){
			return false;
		}		
		return true;
	}
	
	public ColloquiVO getColloquio() {
		return colloquio;
	}
	
	class txtDateClickListener implements OnClickListener{
		
		private ColloquiVO colloquio = null;
		
		public txtDateClickListener(ColloquiVO colloquio){
			this.colloquio = colloquio;
		}

		@Override
		public void onClick(View v) {
			try {
				DatePickerFragment dpf = new DatePickerFragment();
				dpf.setDettaglioColloquio(DettaglioColloquioFragment.this);
				dpf.setStartDate(colloquio.getDataColloquio());
				dpf.show(DettaglioColloquioFragment.this.getActivity().getFragmentManager(), "datePicker");
			} catch (Exception e) {
				Log.e("Dettaglio colloquio", e.getMessage());
			}
			
		}
		
	}

	class txtOraClickListener implements OnClickListener{
		
		private ColloquiVO colloquio = null;
		
		public txtOraClickListener(ColloquiVO colloquio){
			this.colloquio = colloquio;
		}

		@Override
		public void onClick(View v) {
			try {
				DatePickerFragment dpf = new TimePickerFragment();
                dpf.setDettaglioColloquio(DettaglioColloquioFragment.this);
                dpf.setStartDate(colloquio.getDataColloquio());
				dpf.show(DettaglioColloquioFragment.this.getActivity().getFragmentManager(), "timePicker");
			} catch (Exception e) {
				Log.e("Dettaglio colloquio", e.getMessage());
			}
			
		}
		
	}

	public void setColloquio(ColloquiVO colloquio) {
		
		this.colloquio = colloquio;
				

//		if (w_txtData != null){
//			txtData.removeTextChangedListener(w_txtData);
//		}
//
//		if (w_txtOra != null){
//			txtOra.removeTextChangedListener(w_txtOra);
//		}
		
		txtData.setOnClickListener(new txtDateClickListener(colloquio));
		txtOra.setOnClickListener(new txtOraClickListener(colloquio));


		if (w_txtDescrizione != null){
			txtDescrizione.removeTextChangedListener(w_txtDescrizione);
		}
		
		if (w_txtAgenzia != null){
			txtAgenzia.removeTextChangedListener(w_txtAgenzia);
		}
		
		if (w_txtCliente != null){
			txtCliente.removeTextChangedListener(w_txtCliente);
		}
		
		if (this.colloquio.getDataColloquio() != null){
			SimpleDateFormat sdf_data = new SimpleDateFormat("dd/MM/yyyy");
			txtData.setText(sdf_data.format(this.colloquio.getDataColloquio()));
			SimpleDateFormat sdf_ora = new SimpleDateFormat("HH:mm");
			txtOra.setText(sdf_ora.format(this.colloquio.getDataColloquio()));
		}
		
		txtDescrizione.setText(this.colloquio.getDescrizione());
		txtAgenzia.setText(this.colloquio.getCommentoAgenzia());
		txtCliente.setText(this.colloquio.getCommentoCliente());
		
		w_txtDescrizione = new WinkTextWatcher(this.colloquio, "setDescrizione", String.class);
		txtDescrizione.addTextChangedListener(w_txtDescrizione);

		w_txtAgenzia = new WinkTextWatcher(this.colloquio, "setCommentoAgenzia", String.class);
		txtAgenzia.addTextChangedListener(w_txtAgenzia);

		w_txtCliente = new WinkTextWatcher(this.colloquio, "setCommentoCliente", String.class);
		txtCliente.addTextChangedListener(w_txtCliente);		
		
		sp_tipicolloqui.setSelection(get_al_tipi_colloqui_index());
		
		sp_tipicolloqui.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				
				TipologieColloquiVO tipo = (TipologieColloquiVO)arg0.getItemAtPosition(arg2);
				getColloquio().setCodTipologiaColloquio(tipo.getCodTipologiaColloquio());
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
	}

	protected int get_al_tipi_colloqui_index(){
		int count = 0;
		for (TipologieColloquiVO tipocolloquio : al_sp_tipicolloqui) {
			if (tipocolloquio.getCodTipologiaColloquio() == colloquio.getCodTipologiaColloquio()){
				return count;
			}else{
				count++;
			}
		}
		return 0;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,	Bundle savedInstanceState) {
		
		view = inflater.inflate(R.layout.dettaglio_colloquio, container, false);
		
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

                        boolean is_new = (colloquio.getCodColloquio() == null || colloquio.getCodColloquio() == 0);


                        DataBaseHelper dbh = new DataBaseHelper(view.getContext(),DataBaseHelper.NONE_DB);

                        if (colloquio != null && canSave()){

                            if (dbh.saveColloquio(colloquio)){

                                if (is_new){
                                    Iterator<AnagraficheVO> it = colloquio.getAnagrafiche(view.getContext(), null).iterator();
                                    while (it.hasNext()) {
                                        AnagraficheVO aVO = (AnagraficheVO) it.next();
                                        ColloquiAnagraficheVO caVO = new ColloquiAnagraficheVO();
                                        caVO.setCodAnagrafica(aVO.getCodAnagrafica());
                                        caVO.setCodColloquio(colloquio.getCodColloquio());
                                        dbh.saveColloquioAnagrafica(caVO);
                                    }


                                }

                                ((DettaglioColloquiActivity)getActivity()).setCodColloquio(colloquio.getCodColloquio());
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext(),AlertDialog.THEME_HOLO_DARK);
                                alertDialog.setTitle("Risultato salvataggio");
                                alertDialog.setMessage("Salvataggio colloquio avvenuto con successo");
                                alertDialog.setIcon(R.drawable.adept_commit);
                                alertDialog.setPositiveButton("Chiudi", null);
                                alertDialog.show();

                            }else{

                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext(),AlertDialog.THEME_HOLO_DARK);
                                alertDialog.setTitle("Risultato salvataggio");
                                alertDialog.setMessage("Salvataggio colloquio non avvenuto");
                                alertDialog.setIcon(R.drawable.button_cancel);
                                alertDialog.setPositiveButton("Chiudi", null);
                                alertDialog.show();

                            }

                        }else{
                            Toast.makeText(view.getContext(), "Campi tipo,data ed ora obbligatori",Toast.LENGTH_SHORT).show();
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

                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext(),AlertDialog.THEME_HOLO_DARK);
                        alertDialog.setTitle("Conferma cancellazione ...");
                        alertDialog.setMessage("Procedere con la cancellazione del colloquio?");
                        alertDialog.setIcon(R.drawable.help);

                        ArrayList<Integer> alcod = new ArrayList<Integer>();
                        alcod.add(colloquio.getCodColloquio());

                        alertDialog.setPositiveButton("SI", new DialogColloquioCancellaListener(alcod,view.getContext(),codImmobile,codAnagrafica));
                        alertDialog.setNegativeButton("NO", null);
                        alertDialog.show();
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

                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext(),AlertDialog.THEME_HOLO_DARK);
        		        alertDialog.setTitle("Conferma annullamento modifiche ...");
		                alertDialog.setMessage("Procedere con l'annullamento delle modifiche apportate al colloquio ?");
		                alertDialog.setIcon(R.drawable.help);
        		        alertDialog.setPositiveButton("SI", new DialogColloquiAnnullaListener(DettaglioColloquioFragment.this,colloquio,view.getContext()));
		                alertDialog.setNegativeButton("NO", null);
		                alertDialog.show();
                    }
                }, 1200);

            }
        });
		
		sp_tipicolloqui = (Spinner)view.findViewById(R.id.cmb_TipoColloquio);
		
		DataBaseHelper dbh = new DataBaseHelper(view.getContext(),DataBaseHelper.NONE_DB);
		
		ArrayList<TipologieColloquiVO> tmp = dbh.getAllTipologieColloqui(null);
		al_sp_tipicolloqui = new ArrayList<TipologieColloquiVO>();
		al_sp_tipicolloqui.add(new TipologieColloquiVO());
		al_sp_tipicolloqui.addAll(tmp);
		
		CustomSpinnerAdapter icsa = new CustomSpinnerAdapter(view.getContext(), 
															 R.layout.custom_combo_detail, 
															 al_sp_tipicolloqui.toArray());
						 
		sp_tipicolloqui.setPopupBackgroundResource(R.drawable.spinner_bckground);
		sp_tipicolloqui.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus){
					sp_tipicolloqui.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
				}else{
					sp_tipicolloqui.getBackground().setColorFilter(Color.parseColor("#f1ebeb"), PorterDuff.Mode.SRC_IN);
				}
				
			}
		});		
		sp_tipicolloqui.setAdapter(icsa);			
		
		
		TextView labelData = (TextView)view.findViewById(R.id.labelData);
		labelData.setWidth((dm.widthPixels/2)+20);
		
		TextView labelOra = (TextView)view.findViewById(R.id.labelOra);
		labelOra.setWidth((dm.widthPixels/2));

		txtData = (EditText)view.findViewById(R.id.txtData);
		txtData.setWidth((dm.widthPixels/2));
				
		
		txtOra = (EditText)view.findViewById(R.id.txtOra);
		txtOra.setWidth((dm.widthPixels/2));
		
		txtDescrizione = (EditText)view.findViewById(R.id.txtDescrizione);
		txtDescrizione.addTextChangedListener(new WinkTextWatcher(this.colloquio, "setDescrizione", String.class));
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

		txtAgenzia = (EditText)view.findViewById(R.id.txtAgenzia);
		txtAgenzia.addTextChangedListener(new WinkTextWatcher(this.colloquio, "setCommentoAgenzia", String.class));
		txtAgenzia.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus){
					txtAgenzia.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
				}else{
					txtAgenzia.getBackground().setColorFilter(Color.parseColor("#f1ebeb"), PorterDuff.Mode.SRC_IN);
				}
				
			}
		});

		txtCliente = (EditText)view.findViewById(R.id.txtCliente);
		txtCliente.addTextChangedListener(new WinkTextWatcher(this.colloquio, "setCommentoCliente", String.class));
		txtCliente.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus){
					txtCliente.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
				}else{
					txtCliente.getBackground().setColorFilter(Color.parseColor("#f1ebeb"), PorterDuff.Mode.SRC_IN);
				}
				
			}
		});

		return view;
		
		
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onStart() {
		
		super.onStart();
		
		codAnagrafica 	= getArguments().getInt(ActivityMessages.CODANAGRAFICA_ACTION, 0);
		codColloquio 	= getArguments().getInt(ActivityMessages.CODCOLLOQUIO_ACTION, 0);
		codImmobile 	= getArguments().getInt(ActivityMessages.CODIMMOBILE_ACTION, 0);
				
		
		if (codColloquio != 0){
			DataBaseHelper dbh 		= new DataBaseHelper(getActivity().getBaseContext(),DataBaseHelper.READ_DB);
			colloquio = dbh.getColloquiById(codColloquio, null);
			dbh.close();
		}
		
		if (codImmobile != 0){
			colloquio = new ColloquiVO();
			colloquio.setCodImmobileAbbinato(codImmobile);
		}

		if (codAnagrafica != 0){
			
			colloquio = new ColloquiVO();
			
			DataBaseHelper dbh 		= new DataBaseHelper(getActivity().getBaseContext(),DataBaseHelper.READ_DB);
			AnagraficheVO aVO =  dbh.getAnagraficaById(codAnagrafica, null);
			
			colloquio.getAnagrafiche(getActivity().getBaseContext(), null).add(aVO);
		}

		if (codAnagrafica != 0){
			
			DataBaseHelper dbh = new DataBaseHelper(view.getContext(),DataBaseHelper.NONE_DB);
		
			ArrayList<TipologieColloquiVO> tmp = dbh.getAllTipologieColloqui(null);
			al_sp_tipicolloqui = new ArrayList<TipologieColloquiVO>();
			al_sp_tipicolloqui.add(new TipologieColloquiVO());
			tmp.remove(1);
			al_sp_tipicolloqui.addAll(tmp);
		
			CustomSpinnerAdapter icsa = new CustomSpinnerAdapter(view.getContext(), 
															     R.layout.custom_combo_detail, 
															     al_sp_tipicolloqui.toArray());
			sp_tipicolloqui.setAdapter(icsa);
		}
		setColloquio(colloquio);
	}

	
	public void updateData(Date newdatacolloquio) {
		
		Calendar c = Calendar.getInstance(Locale.ITALIAN);
		Calendar sc = Calendar.getInstance(Locale.ITALIAN);
		sc.setTime(newdatacolloquio);
		
		Date datacolloquio = colloquio.getDataColloquio();
			
		c.setTime(datacolloquio);
		c.set(Calendar.YEAR, sc.get(Calendar.YEAR));
		c.set(Calendar.MONTH, sc.get(Calendar.MONTH));
		c.set(Calendar.DAY_OF_MONTH, sc.get(Calendar.DAY_OF_MONTH));
			
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		txtData.setText(sdf.format(c.getTime()));
		
		colloquio.setDataColloquio(c.getTime());
			
	}

	public void updateTime(Date newdatacolloquio) {
		
		Calendar c = Calendar.getInstance(Locale.ITALIAN);
		Calendar sc = Calendar.getInstance(Locale.ITALIAN);
		sc.setTime(newdatacolloquio);
		
		Date datacolloquio = colloquio.getDataColloquio();
			
		c.setTime(datacolloquio);
		c.set(Calendar.HOUR_OF_DAY, sc.get(Calendar.HOUR_OF_DAY));
		c.set(Calendar.MINUTE, sc.get(Calendar.MINUTE));		
			
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		txtOra.setText(sdf.format(c.getTime()));
		
		colloquio.setDataColloquio(c.getTime());	

	}

}
