 package org.winkhouse.mwinkhouse.activity.immobili;

import org.winkhouse.mwinkhouse.activity.listeners.WinkTextWatcher;
import org.winkhouse.mwinkhouse.helpers.DataBaseHelper;
import org.winkhouse.mwinkhouse.models.ImmobiliVO;
import org.winkhouse.mwinkhouse.util.ActivityMessages;
import org.winkhouse.mwinkhouse.util.EditedActivity;

import org.winkhouse.mwinkhouse.R;
import android.app.ActionBar;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class DettaglioImmobileExtraInfoActivity extends AppCompatActivity
												implements EditedActivity{

	private DisplayMetrics dm = null;
	private Animation animazione_rotazione_out = null;
	private EditText txt_mutuo = null;
	private EditText txt_spese = null;
	private CheckBox chkAffittabile = null;
	private CheckBox chkVisionabile = null;
	private EditText txt_desAgenzia = null;
	private EditText txt_desMutuo = null;
	
	private WinkTextWatcher w_txt_mutuo = null;	
	private WinkTextWatcher w_txt_spese = null;
	private WinkTextWatcher w_txt_desAgenzia = null;
	private WinkTextWatcher w_txt_desMutuo = null;
	
	private boolean edited = false;
	
	public boolean isEdited() {
		return edited;
	}

	public void setEdited(boolean edited) {
		this.edited = edited;
	}

	private ImmobiliVO immobile = null;
	
	@Override
	protected void onStart() {	
		super.onStart();
		setImmobile();
	}

	public DettaglioImmobileExtraInfoActivity() {

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.dettaglio_immobile_extra_info);
//        ActionBar ab = getActionBar();
//		ab.setDisplayHomeAsUpEnabled(true);
		
		animazione_rotazione_out = AnimationUtils.loadAnimation(this, R.anim.activity_close_scale);
		
		overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);
		
		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		
//		ImageButton btn_salva = (ImageButton)findViewById(R.id.btn_salva);
//		btn_salva.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				v.startAnimation(animazione_rotazione_out);								
//				DataBaseHelper dbh = new DataBaseHelper(v.getContext(),DataBaseHelper.NONE_DB);
//				if (immobile != null){
//					if (dbh.saveImmobile(immobile)){						
//						AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext(),AlertDialog.THEME_HOLO_DARK);
//		 		        alertDialog.setTitle("Risultato salvataggio");	 
//		 		        alertDialog.setMessage("Salvataggio immobile avvenuto con successo");
//		 		        alertDialog.setIcon(R.drawable.adept_commit);
//		 		        alertDialog.setPositiveButton("Chiudi", null);
//		 		        alertDialog.show();
//					}else{
//						AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext(),AlertDialog.THEME_HOLO_DARK);
//		 		        alertDialog.setTitle("Risultato salvataggio");	 
//		 		        alertDialog.setMessage("Salvataggio immobile non avvenuto");
//		 		        alertDialog.setIcon(R.drawable.button_cancel);
//		 		        alertDialog.setPositiveButton("Chiudi", null);
//		 		        alertDialog.show();
//						
//					}
//					 
//				}else{
//					Toast.makeText(v.getContext(), "Campo indirizzo obbligatorio",Toast.LENGTH_SHORT).show();
//				}
//				dbh.close();
//				
//			}
//		});
//
//		ImageButton btn_cancella = (ImageButton)findViewById(R.id.btn_cancella);
//		btn_cancella.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				v.startAnimation(animazione_rotazione_out);
//				AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext(),AlertDialog.THEME_HOLO_DARK);
//		        alertDialog.setTitle("Conferma cancellazione ...");
//		        alertDialog.setMessage("Procedere con la cancellazione dell'immobile ?");
//		        alertDialog.setIcon(R.drawable.help);
//		        ArrayList<Integer> alcod = new ArrayList<Integer>();
//		        alcod.add(immobile.getCodImmobile());
//		        
//		        alertDialog.setPositiveButton("SI", new DialogImmobiliCancellaListener(alcod,v.getContext()));
//		        alertDialog.setNegativeButton("NO", null);
//		        alertDialog.show();				
//				
//				
//			}
//		});		
//		
//		ImageButton btn_annulla = (ImageButton)findViewById(R.id.btn_annulla);
//		btn_annulla.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				v.startAnimation(animazione_rotazione_out);
//				AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext(),AlertDialog.THEME_HOLO_DARK);
//		        alertDialog.setTitle("Conferma annullamento modifiche ...");
//		        alertDialog.setMessage("Procedere con l'annullamento delle modifiche apportate all'immobile ?");
//		        alertDialog.setIcon(R.drawable.help);
//		        alertDialog.setPositiveButton("SI", new DialogImmobiliAnnullaListener(DettaglioImmobileExtraInfoActivity.this,immobile,v.getContext()));
//		        alertDialog.setNegativeButton("NO", null);
//		        alertDialog.show();				
//				
//			}
//		});
		
		
		
		TextView labelMutuo = findViewById(R.id.labelMutuo);
		labelMutuo.setWidth((dm.widthPixels/2));
		
		TextView labelSpese = findViewById(R.id.labelSpese);
		labelSpese.setWidth((dm.widthPixels/2));
		
		txt_mutuo = findViewById(R.id.txtMutuo);
		txt_mutuo.setWidth((dm.widthPixels/2));
		txt_mutuo.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus){
					txt_mutuo.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
				}else{
					txt_mutuo.getBackground().setColorFilter(Color.parseColor("#f1ebeb"), PorterDuff.Mode.SRC_IN);
				}
				
			}
		});

		txt_spese = findViewById(R.id.txtSpese);
		txt_spese.setWidth((dm.widthPixels/2));
		txt_spese.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus){
					txt_spese.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
				}else{
					txt_spese.getBackground().setColorFilter(Color.parseColor("#f1ebeb"), PorterDuff.Mode.SRC_IN);
				}
				
			}
		});

		TextView labelAffittabile = findViewById(R.id.labelAffittabile);
		labelAffittabile.setWidth((dm.widthPixels/2));
		
		TextView labelVisionabile = findViewById(R.id.labelVisionabile);
		labelVisionabile.setWidth((dm.widthPixels/2));
		
		int id = Resources.getSystem().getIdentifier("btn_check", "drawable", "android");
		
		chkAffittabile = findViewById(R.id.chkAffittabile);
		chkAffittabile.setWidth((dm.widthPixels/2));
		chkAffittabile.setButtonDrawable(id);		
		
		chkVisionabile = findViewById(R.id.chkVisionabile);
		chkVisionabile.setWidth((dm.widthPixels/2));
		chkVisionabile.setButtonDrawable(id);

		txt_desAgenzia = findViewById(R.id.txtDesAgenzia);
		txt_desAgenzia.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus){
					txt_desAgenzia.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
				}else{
					txt_desAgenzia.getBackground().setColorFilter(Color.parseColor("#f1ebeb"), PorterDuff.Mode.SRC_IN);
				}
				
			}
		});

		txt_desMutuo = findViewById(R.id.txtDesMutuo);
		txt_desMutuo.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus){
					txt_desMutuo.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
				}else{
					txt_desMutuo.getBackground().setColorFilter(Color.parseColor("#f1ebeb"), PorterDuff.Mode.SRC_IN);
				}
				
			}
		});
		
	}
	
	public void setVisionabile(View view){
		boolean checked = ((CheckBox) view).isChecked();
		immobile.setVisione(checked);
		edited = true;
	}
	
	public void setAffittabile(View view){
		boolean checked = ((CheckBox) view).isChecked();
		immobile.setAffittabile(checked);
		edited = true;
		
	}
	
	protected void setImmobile(){
		
		if (getIntent().getExtras() != null){
			
			Integer codImmobile = getIntent().getExtras().getInt(ActivityMessages.CODIMMOBILE_PROPIETARI_ACTION);
			DataBaseHelper dbh = new DataBaseHelper(DettaglioImmobileExtraInfoActivity.this, DataBaseHelper.NONE_DB);
			
			this.immobile = dbh.getImmobileById(codImmobile, null);
			
			if (w_txt_mutuo != null){
				txt_mutuo.removeTextChangedListener(w_txt_mutuo);				
			}
			
			if (w_txt_spese != null){
				txt_spese.removeTextChangedListener(w_txt_spese);				
			}

			if (w_txt_desAgenzia != null){
				txt_desAgenzia.removeTextChangedListener(w_txt_desAgenzia);				
			}

			if (w_txt_desMutuo != null){
				txt_desMutuo.removeTextChangedListener(w_txt_desMutuo);				
			}

			txt_mutuo.setText(this.immobile.getMutuo().toString());
			txt_spese.setText(this.immobile.getSpese().toString());
			
			chkAffittabile.setChecked(this.immobile.getAffittabile());
			chkVisionabile.setChecked(this.immobile.getVisione());
			
			txt_desAgenzia.setText(this.immobile.getVarie());
			txt_desMutuo.setText(this.immobile.getMutuoDescrizione());
			
			w_txt_mutuo = new WinkTextWatcher(this.immobile, "setMutuo", Double.class, DettaglioImmobileExtraInfoActivity.this);
			txt_mutuo.addTextChangedListener(w_txt_mutuo);
			
			w_txt_spese = new WinkTextWatcher(this.immobile, "setSpese", Double.class, DettaglioImmobileExtraInfoActivity.this);
			txt_spese.addTextChangedListener(w_txt_spese);
			
			w_txt_desAgenzia = new WinkTextWatcher(this.immobile, "setVarie", String.class, DettaglioImmobileExtraInfoActivity.this);
			txt_desAgenzia.addTextChangedListener(w_txt_desAgenzia);

			w_txt_desMutuo = new WinkTextWatcher(this.immobile, "setMutuoDescrizione", String.class, DettaglioImmobileExtraInfoActivity.this);
			txt_desMutuo.addTextChangedListener(w_txt_desMutuo);

			edited = false;
		}   

	}

	@Override
	protected void onStop() {
		if (edited){
			DataBaseHelper dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.NONE_DB);				
			if (this.immobile != null && this.immobile.getCodImmobile() != 0){			
				dbh.saveImmobile(this.immobile);
			}
			dbh.close();
			edited = false;
//			AlertDialog.Builder alertDialog = new AlertDialog.Builder(DettaglioImmobileExtraInfoActivity.this,AlertDialog.THEME_HOLO_DARK);
//	        alertDialog.setTitle("Salvataggio modifiche ...");
//	        alertDialog.setMessage("Vuoi salvare le modifiche apportate ?");
//	        alertDialog.setIcon(R.drawable.help);
//	        alertDialog.setPositiveButton("SI", new DialogDettaglioImmobileExtraInfoSaveListener(DettaglioImmobileExtraInfoActivity.this,immobile));
//	        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener(){
//        		@Override
//        		public void onClick(DialogInterface dialog, int which) {
//        			finish();
//        		}});
//
//	        alertDialog.show();
		}		
		super.onStop();
	}

	@Override
	protected void onPause() {
		if (edited){			
			DataBaseHelper dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.NONE_DB);				
			if (this.immobile != null && this.immobile.getCodImmobile() != 0){			
				dbh.saveImmobile(this.immobile);
			}
			dbh.close();
			edited = false;
//			AlertDialog.Builder alertDialog = new AlertDialog.Builder(DettaglioImmobileExtraInfoActivity.this,AlertDialog.THEME_HOLO_DARK);
//	        alertDialog.setTitle("Salvataggio modifiche ...");
//	        alertDialog.setMessage("Vuoi salvare le modifiche apportate ?");
//	        alertDialog.setIcon(R.drawable.help);
//	        alertDialog.setPositiveButton("SI", new DialogDettaglioImmobileExtraInfoSaveListener(DettaglioImmobileExtraInfoActivity.this,immobile));
//	        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener(){
//									        		@Override
//									        		public void onClick(DialogInterface dialog, int which) {
//									        			finish();
//									        		}
//	        });
//	        alertDialog.show();
		}		
		super.onPause();
	}
	
	
	
}
