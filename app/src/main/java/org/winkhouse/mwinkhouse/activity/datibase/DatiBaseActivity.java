package org.winkhouse.mwinkhouse.activity.datibase;

import java.util.ArrayList;
import java.util.HashMap;

import org.winkhouse.mwinkhouse.activity.adapters.CustomSpinnerAdapter;
import org.winkhouse.mwinkhouse.activity.listeners.WinkTextWatcher;
import org.winkhouse.mwinkhouse.helpers.DataBaseHelper;
import org.winkhouse.mwinkhouse.models.ClasseEnergeticaVO;
import org.winkhouse.mwinkhouse.models.ClassiClientiVO;
import org.winkhouse.mwinkhouse.models.RiscaldamentiVO;
import org.winkhouse.mwinkhouse.models.StatoConservativoVO;
import org.winkhouse.mwinkhouse.models.TipologiaContattiVO;
import org.winkhouse.mwinkhouse.models.TipologiaStanzeVO;
import org.winkhouse.mwinkhouse.models.TipologieImmobiliVO;
import org.winkhouse.mwinkhouse.models.columns.ClasseEnergeticaColumnNames;
import org.winkhouse.mwinkhouse.models.columns.ClassiClienteColumnNames;
import org.winkhouse.mwinkhouse.models.columns.RiscaldamentiColumnNames;
import org.winkhouse.mwinkhouse.models.columns.StatoConservativoColumnNames;
import org.winkhouse.mwinkhouse.models.columns.TipologieImmobiliColumnNames;

import org.winkhouse.mwinkhouse.R;
import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DatiBaseActivity extends AppCompatActivity {

	private Spinner sp_classe_energetica = null;
	private Spinner sp_riscaldamenti = null;
	private Spinner sp_tipo_immobile = null;
	private Spinner sp_statoconservativo = null;
	private Spinner sp_anagrafiche = null;
	private Spinner sp_classe_cliente = null;
	private Spinner sp_tipi_contatti = null;
	private Spinner sp_tipi_stanze = null;
	
	private ArrayList<StatoConservativoVO> al_statoconservativo = null;
	private ArrayList<ClasseEnergeticaVO> al_classi_energetiche = null;
	private ArrayList<RiscaldamentiVO> al_riscaldamenti = null;
	private ArrayList<TipologieImmobiliVO> al_tipi_immobili = null;
	private ArrayList<TipologiaStanzeVO> al_tipi_stanze = null;
	
	private ArrayList<ClassiClientiVO> al_classi_cliente = null;
	private ArrayList<TipologiaContattiVO> al_tipicontatti = null;
	
	private CustomSpinnerAdapter classienergeticheAdapter = null;
	private CustomSpinnerAdapter riscaldamentiAdapter = null;
	private CustomSpinnerAdapter tipiimmobiliAdapter = null;
	private CustomSpinnerAdapter statoconservativoAdapter = null;
	private CustomSpinnerAdapter classiclienteAdapter = null;
	private CustomSpinnerAdapter tipicontattiAdapter = null;
	private CustomSpinnerAdapter tipistanzeAdapter = null;
			
	private Dialog dialog = null;
	private DisplayMetrics dm = null;
	private Animation animazione_rotazione_out = null;
	
	private EditText et_datobase = null;
	
	public DatiBaseActivity() {}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
//        ActionBar ab = getActionBar();
//		ab.setDisplayHomeAsUpEnabled(true);
		
		animazione_rotazione_out = AnimationUtils.loadAnimation(this, R.anim.activity_close_scale);
		
		setContentView(R.layout.dati_base);
		
		overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);
		
		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		
		sp_classe_energetica = findViewById(R.id.cmb_classe_energetica);
		sp_classe_energetica.setPopupBackgroundResource(R.drawable.spinner_bckground);
		
		ImageButton btn_new_ce = findViewById(R.id.btn_new_ce);
		btn_new_ce.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				v.startAnimation(animazione_rotazione_out);
				ClasseEnergeticaVO ce = new ClasseEnergeticaVO();				
				openDettaglioDatiBasePopup(v.getContext(), ce);
				
			}
		});
		
		ImageButton btn_edit_ce = findViewById(R.id.btn_edit_ce);
		btn_edit_ce.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				v.startAnimation(animazione_rotazione_out);
				openDettaglioDatiBasePopup(v.getContext(), sp_classe_energetica.getSelectedItem());
				
			}
		});
		
		ImageButton btn_del_ce = findViewById(R.id.btn_del_ce);
		btn_del_ce.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				v.startAnimation(animazione_rotazione_out);
				
				DataBaseHelper dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.NONE_DB);
				
				ClasseEnergeticaVO ceVO = (ClasseEnergeticaVO)sp_classe_energetica.getSelectedItem();
				dbh.deleteClasseEnergetica(ceVO);
				dbh.resetClasseEnergetica(ceVO.getCodClasseEnergetica());
				dbh.close();
				
				refreshData();								
			}
		});
		
		sp_riscaldamenti = findViewById(R.id.cmb_riscaldamento);
		sp_riscaldamenti.setPopupBackgroundResource(R.drawable.spinner_bckground);
		
		ImageButton btn_new_riscaldamento = findViewById(R.id.btn_new_riscaldamento);
		btn_new_riscaldamento.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				v.startAnimation(animazione_rotazione_out);
				RiscaldamentiVO r = new RiscaldamentiVO();				
				openDettaglioDatiBasePopup(v.getContext(), r);
				
			}
		});
		
		ImageButton btn_edit_riscaldamento = findViewById(R.id.btn_edit_riscaldamento);
		btn_edit_riscaldamento.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				v.startAnimation(animazione_rotazione_out);
				openDettaglioDatiBasePopup(v.getContext(), sp_riscaldamenti.getSelectedItem());
				
			}
		});
		
		ImageButton btn_del_riscaldamento = findViewById(R.id.btn_del_riscaldamento);
		btn_del_riscaldamento.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				v.startAnimation(animazione_rotazione_out);
				
				DataBaseHelper dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.NONE_DB);
				
				RiscaldamentiVO rVO = (RiscaldamentiVO)sp_riscaldamenti.getSelectedItem();
				dbh.deleteRiscaldamento(rVO);
				dbh.resetRiscaldamento(rVO.getCodRiscaldamento());
				dbh.close();
				
				refreshData();								
			}
		});
		
		sp_tipo_immobile = findViewById(R.id.cmb_tipologia_immobili);
		sp_tipo_immobile.setPopupBackgroundResource(R.drawable.spinner_bckground);
		
		ImageButton btn_new_tipo = findViewById(R.id.btn_new_tipo);
		btn_new_tipo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				v.startAnimation(animazione_rotazione_out);
				TipologieImmobiliVO ti = new TipologieImmobiliVO();				
				openDettaglioDatiBasePopup(v.getContext(), ti);
				
			}
		});
		
		ImageButton btn_edit_tipo = findViewById(R.id.btn_edit_tipo);
		btn_edit_tipo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				v.startAnimation(animazione_rotazione_out);
				openDettaglioDatiBasePopup(v.getContext(), sp_tipo_immobile.getSelectedItem());
				
			}
		});
		
		ImageButton btn_del_tipo = findViewById(R.id.btn_del_tipo);
		btn_del_tipo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				v.startAnimation(animazione_rotazione_out);
				
				DataBaseHelper dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.NONE_DB);
				
				TipologieImmobiliVO tVO = (TipologieImmobiliVO)sp_tipo_immobile.getSelectedItem();
				dbh.deleteTipologieImmobile(tVO);
				dbh.resetTipologieImmobile(tVO.getCodTipologiaImmobile());
				dbh.close();
				
				refreshData();								
			}
		});
		
		sp_statoconservativo = findViewById(R.id.cmb_stato);
		sp_statoconservativo.setPopupBackgroundResource(R.drawable.spinner_bckground);
		
		ImageButton btn_new_stato = findViewById(R.id.btn_new_stato);
		btn_new_stato.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				v.startAnimation(animazione_rotazione_out);
				StatoConservativoVO sc = new StatoConservativoVO();				
				openDettaglioDatiBasePopup(v.getContext(), sc);
				
			}
		});
		
		ImageButton btn_edit_stato = findViewById(R.id.btn_edit_stato);
		btn_edit_stato.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				v.startAnimation(animazione_rotazione_out);
				openDettaglioDatiBasePopup(v.getContext(), sp_statoconservativo.getSelectedItem());
				
			}
		});
		
		ImageButton btn_del_stato = findViewById(R.id.btn_del_stato);
		btn_del_stato.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				v.startAnimation(animazione_rotazione_out);
				
				DataBaseHelper dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.NONE_DB);
				
				StatoConservativoVO scVO = (StatoConservativoVO)sp_statoconservativo.getSelectedItem();
				dbh.deleteStatoConservativo(scVO);
				dbh.resetStatoConservativo(scVO.getCodStatoConservativo());
				dbh.close();
				
				refreshData();								
			}
		});
		
		sp_classe_cliente = findViewById(R.id.cmb_classe);
		sp_classe_cliente.setPopupBackgroundResource(R.drawable.spinner_bckground);
		
		ImageButton btn_new_classe = findViewById(R.id.btn_new_classe);
		btn_new_classe.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				v.startAnimation(animazione_rotazione_out);
				ClassiClientiVO cc = new ClassiClientiVO();				
				openDettaglioDatiBasePopup(v.getContext(), cc);
				
			}
		});
		
		ImageButton btn_edit_classe = findViewById(R.id.btn_edit_classe);
		btn_edit_classe.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				v.startAnimation(animazione_rotazione_out);
				openDettaglioDatiBasePopup(v.getContext(), sp_classe_cliente.getSelectedItem());
				
			}
		});
		
		ImageButton btn_del_classe = findViewById(R.id.btn_del_classe);
		btn_del_classe.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				v.startAnimation(animazione_rotazione_out);
				
				DataBaseHelper dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.NONE_DB);
				
				ClassiClientiVO ccVO = (ClassiClientiVO)sp_classe_cliente.getSelectedItem();
				dbh.deleteClassiClienti(ccVO);
				dbh.resetClassiClienti(ccVO.getCodClasseCliente());
				dbh.close();
				
				refreshData();								
			}
		});
		
		sp_tipi_contatti = findViewById(R.id.cmb_TipoContatto);
		sp_tipi_contatti.setPopupBackgroundResource(R.drawable.spinner_bckground);
		
		ImageButton btn_new_TipoContatto = findViewById(R.id.btn_new_TipoContatto);
		btn_new_TipoContatto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				v.startAnimation(animazione_rotazione_out);
				TipologiaContattiVO tc = new TipologiaContattiVO();				
				openDettaglioDatiBasePopup(v.getContext(), tc);
				
			}
		});
		
		ImageButton btn_edit_TipoContatto = findViewById(R.id.btn_edit_TipoContatto);
		btn_edit_TipoContatto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				v.startAnimation(animazione_rotazione_out);
				openDettaglioDatiBasePopup(v.getContext(), sp_tipi_contatti.getSelectedItem());
				
			}
		});
		
		ImageButton btn_del_TipoContatto = findViewById(R.id.btn_del_TipoContatto);
		btn_del_TipoContatto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				v.startAnimation(animazione_rotazione_out);
				
				DataBaseHelper dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.NONE_DB);
				
				TipologiaContattiVO tcVO = (TipologiaContattiVO)sp_tipi_contatti.getSelectedItem();
				dbh.deleteTipologiaContatti(tcVO);
				dbh.resetTipologiaContatti(tcVO.getCodTipologiaContatto());
				dbh.close();
				
				refreshData();								
			}
		});

		sp_tipi_stanze = findViewById(R.id.cmb_TipoStanza);
		sp_tipi_stanze.setPopupBackgroundResource(R.drawable.spinner_bckground);
		
		ImageButton btn_new_TipoStanza = findViewById(R.id.btn_new_TipoStanza);
		btn_new_TipoStanza.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				v.startAnimation(animazione_rotazione_out);
				TipologiaStanzeVO ts = new TipologiaStanzeVO();				
				openDettaglioDatiBasePopup(v.getContext(), ts);
				
			}
		});
		
		ImageButton btn_edit_TipoStanza = findViewById(R.id.btn_edit_TipoStanza);
		btn_edit_TipoStanza.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				v.startAnimation(animazione_rotazione_out);
				openDettaglioDatiBasePopup(v.getContext(), sp_tipi_stanze.getSelectedItem());
				
			}
		});
		
		ImageButton btn_del_TipoStanza = findViewById(R.id.btn_del_TipoStanza);
		btn_del_TipoStanza.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				v.startAnimation(animazione_rotazione_out);
				
				DataBaseHelper dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.NONE_DB);
				
				TipologiaStanzeVO tsVO = (TipologiaStanzeVO)sp_tipi_stanze.getSelectedItem();
				dbh.deleteTipologiaStanze(tsVO);
				dbh.resetTipologiaStanze(tsVO.getCodTipologiaStanza());
				dbh.close(); 
				
				refreshData();								
			}
		});
		
		updateControls();
		
	}
	
	public ArrayList<ClasseEnergeticaVO> getClassiEnergetiche(DataBaseHelper dbh){
		
		HashMap column_list = new HashMap();
		
		column_list.put(ClasseEnergeticaColumnNames.CODCLASSEENERGETICA, null);
		column_list.put(ClasseEnergeticaColumnNames.NOME, null);
		column_list.put(ClasseEnergeticaColumnNames.DESCRIZIONE, null);
		
		ArrayList<ClasseEnergeticaVO> tmp = dbh.getAllClassiEnergetiche(column_list);
		al_classi_energetiche = new ArrayList<ClasseEnergeticaVO>();
		al_classi_energetiche.add(new ClasseEnergeticaVO());
		al_classi_energetiche.addAll(tmp);
		
		return al_classi_energetiche;
		
	}

	public ArrayList<RiscaldamentiVO> getRiscaldamenti(DataBaseHelper dbh){
		
		HashMap column_list = new HashMap();
		
		column_list.put(RiscaldamentiColumnNames.CODRISCALDAMENTO, null);
		column_list.put(RiscaldamentiColumnNames.DESCRIZIONE, null);
		
		ArrayList<RiscaldamentiVO> tmp2 = dbh.getAllRiscaldamenti(column_list);
		al_riscaldamenti = new ArrayList<RiscaldamentiVO>();
		al_riscaldamenti.add(new RiscaldamentiVO());
		al_riscaldamenti.addAll(tmp2);
		
		return al_riscaldamenti;
		
	}

	public ArrayList<TipologieImmobiliVO> getTipologieImmobili(DataBaseHelper dbh){
		
		HashMap column_list = new HashMap();
		
		column_list.put(TipologieImmobiliColumnNames.CODTIPOLOGIAIMMOBILE, null);
		column_list.put(TipologieImmobiliColumnNames.DESCRIZIONE, null);
		
		ArrayList<TipologieImmobiliVO> tmp1 = dbh.getAllTipologieImmobili(column_list);
		al_tipi_immobili = new ArrayList<TipologieImmobiliVO>();
		al_tipi_immobili.add(new TipologieImmobiliVO());
		al_tipi_immobili.addAll(tmp1);
		
		return al_tipi_immobili;
		
	}

	public ArrayList<StatoConservativoVO> getStatoConservativo(DataBaseHelper dbh){
		
		HashMap column_list = new HashMap();
		
		column_list.put(StatoConservativoColumnNames.CODSTATOCONSERVATIVO, null);
		column_list.put(StatoConservativoColumnNames.DESCRIZIONE, null);
		
		ArrayList<StatoConservativoVO> tmp3 = dbh.getAllStatoConservativo(column_list);
		al_statoconservativo = new ArrayList<StatoConservativoVO>();
		al_statoconservativo.add(new StatoConservativoVO());
		al_statoconservativo.addAll(tmp3);
		
		return al_statoconservativo;
		
	}

	public ArrayList<ClassiClientiVO> getClassiClienti(DataBaseHelper dbh){
		
		HashMap column_list = new HashMap();
		
		column_list.put(ClassiClienteColumnNames.CODCLASSECLIENTE, null);
		column_list.put(ClassiClienteColumnNames.ORDINE, null);
		column_list.put(ClassiClienteColumnNames.DESCRIZIONE, null);
		
		ArrayList<ClassiClientiVO> tmp4 = dbh.getAllClassiClienti(column_list);
						
		al_classi_cliente = new ArrayList<ClassiClientiVO>();
		al_classi_cliente.add(new ClassiClientiVO());
		al_classi_cliente.addAll(tmp4);
		
		return al_classi_cliente;
		
	}

	public ArrayList<TipologiaContattiVO> getTipologiaContatti(DataBaseHelper dbh){
		
		HashMap column_list = new HashMap();
		
		ArrayList<TipologiaContattiVO> tmp5 = dbh.getAllTipicontatti(null);		
		
		al_tipicontatti = new ArrayList<TipologiaContattiVO>();
		al_tipicontatti.add(new TipologiaContattiVO());
		al_tipicontatti.addAll(tmp5);
		
		return al_tipicontatti;
		
	}

	public ArrayList<TipologiaStanzeVO> getTipologiaStanze(DataBaseHelper dbh){
		
		HashMap column_list = new HashMap();
		
		ArrayList<TipologiaStanzeVO> tmp6 = dbh.getAllTipiStanze(null);		
		
		al_tipi_stanze = new ArrayList<TipologiaStanzeVO>();
		al_tipi_stanze.add(new TipologiaStanzeVO());
		al_tipi_stanze.addAll(tmp6);
		
		return al_tipi_stanze;
		
	}

	public void updateControls(){
		
		DataBaseHelper dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.NONE_DB);
		
		classienergeticheAdapter = new CustomSpinnerAdapter(DatiBaseActivity.this, 
				 										    R.layout.custom_combo_detail, 
				 										    getClassiEnergetiche(dbh));

		sp_classe_energetica.setAdapter(classienergeticheAdapter);
		
		
		riscaldamentiAdapter = new CustomSpinnerAdapter(DatiBaseActivity.this, 
														R.layout.custom_combo_detail, 
														getRiscaldamenti(dbh));

		sp_riscaldamenti.setAdapter(riscaldamentiAdapter);
	
		
		tipiimmobiliAdapter = new CustomSpinnerAdapter(DatiBaseActivity.this, 
				 									   R.layout.custom_combo_detail, 
				 									   getTipologieImmobili(dbh));

		sp_tipo_immobile.setAdapter(tipiimmobiliAdapter);
		

		statoconservativoAdapter = new CustomSpinnerAdapter(DatiBaseActivity.this, 
				  											R.layout.custom_combo_detail, 
				  											getStatoConservativo(dbh));

		sp_statoconservativo.setAdapter(statoconservativoAdapter);
		
		classiclienteAdapter = new CustomSpinnerAdapter(DatiBaseActivity.this, 
				 										R.layout.custom_combo_detail, 
				 										getClassiClienti(dbh));

		sp_classe_cliente.setAdapter(classiclienteAdapter);
				
		tipicontattiAdapter = new CustomSpinnerAdapter(DatiBaseActivity.this, 
													   R.layout.custom_combo_detail, 
													   getTipologiaContatti(dbh));
		
		sp_tipi_contatti.setAdapter(tipicontattiAdapter);

		tipistanzeAdapter = new CustomSpinnerAdapter(DatiBaseActivity.this, 
				   									 R.layout.custom_combo_detail, 
				   									 getTipologiaStanze(dbh));

		sp_tipi_stanze.setAdapter(tipistanzeAdapter);

		dbh.close();
		
	}
	
	class DatoBaseClickListener implements OnClickListener{

		private Object c = null;
		
		public DatoBaseClickListener(Object c){
			this.c = c;
		}

		@Override
		public void onClick(View v) {
			v.startAnimation(animazione_rotazione_out);
			DataBaseHelper dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.NONE_DB);
			
			if (c instanceof ClasseEnergeticaVO){
				if (!dbh.saveClasseEnergetica((ClasseEnergeticaVO)c,null)){
					Toast.makeText(getApplicationContext(), "Salvataggio classe energetica non avvenuto", Toast.LENGTH_SHORT).show();
										 		        
				}				
			}
			
			if (c instanceof RiscaldamentiVO){
				if (!dbh.saveRiscaldamento((RiscaldamentiVO)c,null)){
					Toast.makeText(getApplicationContext(), "Salvataggio riscaldamento non avvenuto", Toast.LENGTH_SHORT).show();
										 		        
				}				
			}
			
			if (c instanceof TipologieImmobiliVO){
				if (!dbh.saveTipologieImmobili((TipologieImmobiliVO)c,null)){
					Toast.makeText(getApplicationContext(), "Salvataggio tipo immobile non avvenuto", Toast.LENGTH_SHORT).show();
										 		        
				}				
			}
			
			if (c instanceof StatoConservativoVO){
				if (!dbh.saveStatoConservativo((StatoConservativoVO)c,null)){
					Toast.makeText(getApplicationContext(), "Salvataggio stato conservativo non avvenuto", Toast.LENGTH_SHORT).show();
										 		        
				}								
			}
			
			if (c instanceof ClassiClientiVO){
				if (!dbh.saveClassiClienti((ClassiClientiVO)c,null)){
					Toast.makeText(getApplicationContext(), "Salvataggio classe cliente non avvenuto", Toast.LENGTH_SHORT).show();
										 		        
				}								
				
			}
			
			if (c instanceof TipologiaContattiVO){
				if (!dbh.saveTipologiaContatti((TipologiaContattiVO)c,null)){
					Toast.makeText(getApplicationContext(), "Salvataggio tipologia contatti non avvenuto", Toast.LENGTH_SHORT).show();
										 		        
				}								
				
			}
			
			if (c instanceof TipologiaStanzeVO){
				if (!dbh.saveTipologiaStanze((TipologiaStanzeVO)c,null)){
					Toast.makeText(getApplicationContext(), "Salvataggio tipologia stanze non avvenuto", Toast.LENGTH_SHORT).show();
										 		        
				}								
				
			}
			
			dbh.close();
			
//			Intent datibase = new Intent(v.getContext(), DatiBaseActivity.class);
//			v.getContext().startActivity(datibase);
			refreshData();
			dialog.dismiss();								
			
		}
		
	}
	
	Runnable run = new Runnable(){
		
	     public void run(){
	    	 
	    	DataBaseHelper dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.NONE_DB);
	    	
	    	classienergeticheAdapter.clear();
	    	classienergeticheAdapter.addAll(getClassiEnergetiche(dbh));
	    	classienergeticheAdapter.notifyDataSetChanged();
	    	
	    	riscaldamentiAdapter.clear();
	    	riscaldamentiAdapter.addAll(getRiscaldamenti(dbh));
	    	riscaldamentiAdapter.notifyDataSetChanged();
	    	
	    	tipiimmobiliAdapter.clear();
	    	tipiimmobiliAdapter.addAll(getTipologieImmobili(dbh));
	    	tipiimmobiliAdapter.notifyDataSetChanged();
	    	
	    	statoconservativoAdapter.clear();
	    	statoconservativoAdapter.addAll(getStatoConservativo(dbh));
	    	statoconservativoAdapter.notifyDataSetChanged();
	    	
	    	classiclienteAdapter.clear();
	    	classiclienteAdapter.addAll(getClassiClienti(dbh));
	    	classiclienteAdapter.notifyDataSetChanged();
	    	
	    	tipicontattiAdapter.clear();
	    	tipicontattiAdapter.addAll(getTipologiaContatti(dbh));
	    	tipicontattiAdapter.notifyDataSetChanged();

	    	tipistanzeAdapter.clear();
	    	tipistanzeAdapter.addAll(getTipologiaStanze(dbh));
	    	tipistanzeAdapter.notifyDataSetChanged();

	 		dbh.close();
	     }
	};
	
	protected void refreshData(){
		DatiBaseActivity.this.runOnUiThread(run);
	}

	public void openDettaglioDatiBasePopup(Context c, Object datobase){
		
		dialog = new Dialog(c);		
		
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_nuovo_datobase);
		dialog.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
		  
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();		
	    lp.copyFrom(dialog.getWindow().getAttributes());
	    lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
	    LinearLayout ll = dialog.findViewById(R.id.dialog_dati_base_layout);
	    ll.getLayoutParams().width = (dm.widthPixels - (dm.widthPixels/10 * 2));
	    
		TextView labeltop = dialog.findViewById(R.id.labeldatobase);
		
		et_datobase = dialog.findViewById(R.id.datobase);
		
		String labeltopText = null;
		String setterName = null;
		String showString = null;
		
		if (datobase instanceof ClasseEnergeticaVO){
			setterName = "setNome";
			labeltopText = "Classe energetica";
			showString = ((ClasseEnergeticaVO)datobase).getNome();
		}
		
		if (datobase instanceof RiscaldamentiVO){
			setterName = "setDescrizione";
			labeltopText = "Riscaldamento";
			showString = ((RiscaldamentiVO)datobase).getDescrizione();
		}
		
		if (datobase instanceof TipologieImmobiliVO){			
			setterName = "setDescrizione";
			labeltopText = "Tipo immobile";
			showString = ((TipologieImmobiliVO)datobase).getDescrizione();
		}
		
		if (datobase instanceof StatoConservativoVO){
			setterName = "setDescrizione";
			labeltopText = "Stato conservativo";
			showString = ((StatoConservativoVO)datobase).getDescrizione();
		}
		
		if (datobase instanceof ClassiClientiVO){
			setterName = "setDescrizione";
			labeltopText = "Classe cliente";
			showString = ((ClassiClientiVO)datobase).getDescrizione();
		}
		
		if (datobase instanceof TipologiaContattiVO){
			setterName = "setDescrizione";
			labeltopText = "Tipo Contatto";
			showString = ((TipologiaContattiVO)datobase).getDescrizione();
		}
		
		if (datobase instanceof TipologiaStanzeVO){
			setterName = "setDescrizione";
			labeltopText = "Tipo Stanza";
			showString = ((TipologiaStanzeVO)datobase).getDescrizione();
		}		
		
		et_datobase.setWidth(dm.widthPixels);
		et_datobase.setText(showString);
		labeltop.setText(labeltopText);
		
		WinkTextWatcher wtw_contatto = new WinkTextWatcher(datobase, setterName, String.class);
		et_datobase.addTextChangedListener(wtw_contatto);
		et_datobase.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus){
					et_datobase.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
				}else{
					et_datobase.getBackground().setColorFilter(Color.parseColor("#f1ebeb"), PorterDuff.Mode.SRC_IN);
				}
				
			}
		});

		ImageButton salva = dialog.findViewById(R.id.btn_salva);
		salva.setOnClickListener(new DatoBaseClickListener(datobase));
		
		ImageButton chiudi = dialog.findViewById(R.id.btn_chiudi);
		chiudi.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				v.startAnimation(animazione_rotazione_out);
				dialog.dismiss();
				
			}
		});
		
//		dbh.close();
		
		dialog.show();
		dialog.getWindow().setAttributes(lp);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int itemId = item.getItemId();
		
		if (itemId == android.R.id.home){
			NavUtils.navigateUpFromSameTask(this);
		}
		return true;
	}

	
	@Override
	public void onBackPressed() {
		finish();
	}

}
