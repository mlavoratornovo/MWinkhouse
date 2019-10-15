package org.winkhouse.mwinkhouse.activity.anagrafiche;

import java.util.ArrayList;
import java.util.HashMap;

import org.winkhouse.mwinkhouse.activity.adapters.CustomSpinnerAdapter;
import org.winkhouse.mwinkhouse.helpers.DataBaseHelper;
import org.winkhouse.mwinkhouse.helpers.SearchParam;
import org.winkhouse.mwinkhouse.models.ClassiClientiVO;
import org.winkhouse.mwinkhouse.models.columns.AnagraficheColumnNames;
import org.winkhouse.mwinkhouse.models.columns.ClassiClienteColumnNames;
import org.winkhouse.mwinkhouse.util.ActivityMessages;

import org.winkhouse.mwinkhouse.R;
import android.app.ActionBar;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

public class RicercaAnagraficheActivity extends AppCompatActivity {

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

	
	public RicercaAnagraficheActivity() {

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.ricerca_anagrafiche);
				
		overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);
		
//        ActionBar ab = getActionBar();
//		ab.setDisplayHomeAsUpEnabled(true);
		
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		
		ImageButton ricerca = findViewById(R.id.btn_ricerca_anagrafiche);
		ricerca.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				ArrayList<SearchParam> parametri = new ArrayList<SearchParam>();
							
				if (txtCitta.getText().toString() != null && !txtCitta.getText().toString().trim().equalsIgnoreCase("")){
						
					String value_da = null;					
						
					if (txtCitta.getText().toString() != null && !txtCitta.getText().toString().trim().equalsIgnoreCase("")){
						value_da = txtCitta.getText().toString();
					}
						
					SearchParam sp = new SearchParam(AnagraficheColumnNames.CITTA, "%" + value_da + "%", null, "AND", String.class.getName(),SearchParam.ANAGRAFICHE);
					parametri.add(sp);
				}
				
				if (txtIndirizzo.getText().toString() != null && !txtIndirizzo.getText().toString().trim().equalsIgnoreCase("")){
					
					String value_da = null;					
						
					if (txtIndirizzo.getText().toString() != null && !txtIndirizzo.getText().toString().trim().equalsIgnoreCase("")){
						value_da = txtIndirizzo.getText().toString();
					}
						
					SearchParam sp = new SearchParam(AnagraficheColumnNames.INDIRIZZO, "%" + value_da + "%", null, "AND", String.class.getName(),SearchParam.ANAGRAFICHE);
					parametri.add(sp);
				}

				if (txtProvincia.getText().toString() != null && !txtProvincia.getText().toString().trim().equalsIgnoreCase("")){
					
					String value_da = null;					
						
					if (txtProvincia.getText().toString() != null && !txtProvincia.getText().toString().trim().equalsIgnoreCase("")){
						value_da = txtProvincia.getText().toString();
					}
						
					SearchParam sp = new SearchParam(AnagraficheColumnNames.PROVINCIA, "%" + value_da + "%", null, "AND", String.class.getName(),SearchParam.ANAGRAFICHE);
					parametri.add(sp);
				}
				
				if (txtCap.getText().toString() != null && !txtCap.getText().toString().trim().equalsIgnoreCase("")){
					
					String value_da = null;					
						
					if (txtCap.getText().toString() != null && !txtCap.getText().toString().trim().equalsIgnoreCase("")){
						value_da = txtCap.getText().toString();
					}
						
					SearchParam sp = new SearchParam(AnagraficheColumnNames.CAP, "%" + value_da + "%", null, "AND", String.class.getName(),SearchParam.ANAGRAFICHE);
					parametri.add(sp);
				}

				if (txtRagioneSociale.getText().toString() != null && !txtRagioneSociale.getText().toString().trim().equalsIgnoreCase("")){
					
					String value_da = null;					
						
					if (txtRagioneSociale.getText().toString() != null && !txtRagioneSociale.getText().toString().trim().equalsIgnoreCase("")){
						value_da = txtRagioneSociale.getText().toString();
					}
						
					SearchParam sp = new SearchParam(AnagraficheColumnNames.RAGSOC, "%" + value_da + "%", null, "AND", String.class.getName(),SearchParam.ANAGRAFICHE);
					parametri.add(sp);
				}

				if (txtCognome.getText().toString() != null && !txtCognome.getText().toString().trim().equalsIgnoreCase("")){
					
					String value_da = null;					
						
					if (txtCognome.getText().toString() != null && !txtCognome.getText().toString().trim().equalsIgnoreCase("")){
						value_da = txtCognome.getText().toString();
					}
						
					SearchParam sp = new SearchParam(AnagraficheColumnNames.COGNOME, "%" + value_da + "%", null, "AND", String.class.getName(),SearchParam.ANAGRAFICHE);
					parametri.add(sp);
				}

				if (txtNome.getText().toString() != null && !txtNome.getText().toString().trim().equalsIgnoreCase("")){
					
					String value_da = null;					
						
					if (txtNome.getText().toString() != null && !txtNome.getText().toString().trim().equalsIgnoreCase("")){
						value_da = txtNome.getText().toString();
					}
						
					SearchParam sp = new SearchParam(AnagraficheColumnNames.NOME, "%" + value_da + "%", null, "AND", String.class.getName(),SearchParam.ANAGRAFICHE);
					parametri.add(sp);
				}

				if (txtPiva.getText().toString() != null && !txtPiva.getText().toString().trim().equalsIgnoreCase("")){
					
					String value_da = null;					
						
					if (txtPiva.getText().toString() != null && !txtPiva.getText().toString().trim().equalsIgnoreCase("")){
						value_da = txtPiva.getText().toString();
					}
						
					SearchParam sp = new SearchParam(AnagraficheColumnNames.PIVA, "%" + value_da + "%", null, "AND", String.class.getName(),SearchParam.ANAGRAFICHE);
					parametri.add(sp);
				}

				if (txtCodiceFiscale.getText().toString() != null && !txtCodiceFiscale.getText().toString().trim().equalsIgnoreCase("")){
					
					String value_da = null;					
						
					if (txtCodiceFiscale.getText().toString() != null && !txtCodiceFiscale.getText().toString().trim().equalsIgnoreCase("")){
						value_da = txtCodiceFiscale.getText().toString();
					}
						
					SearchParam sp = new SearchParam(AnagraficheColumnNames.CODICEFISCALE, "%" + value_da + "%", null, "AND", String.class.getName(),SearchParam.ANAGRAFICHE);
					parametri.add(sp);
				}

				ClassiClientiVO ccvo = (ClassiClientiVO)sp_classe_cliente.getSelectedItem();
				if (ccvo.getCodClasseCliente() != 0){

					SearchParam sp = new SearchParam(AnagraficheColumnNames.CODCLASSECLIENTE, ccvo.getCodClasseCliente().toString(), null, "AND", Integer.class.getName(),SearchParam.ANAGRAFICHE);
					parametri.add(sp);
					
				}

				Intent it = new Intent(RicercaAnagraficheActivity.this, ListaAnagraficheActivity.class);
				it.putExtra(ActivityMessages.SEARCH_ACTION, true);
				it.putExtra(SearchParam.class.getName(),parametri.toArray(new SearchParam[parametri.size()]));
				startActivity(it);
				
				
			}
		});

		TextView labelProvincia = findViewById(R.id.labelProvincia);
		labelProvincia.setWidth((dm.widthPixels-2)/2);
		TextView labelCap = findViewById(R.id.labelCap);
		labelCap.setWidth((dm.widthPixels-2)/2);
		
		txtProvincia = findViewById(R.id.txtProvincia);
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
		
		txtCap = findViewById(R.id.txtCap);
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

		txtCognome = findViewById(R.id.txtCognome);
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

		txtCitta = findViewById(R.id.txtCitta);
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

		txtNome = findViewById(R.id.txtNome);
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

		txtIndirizzo = findViewById(R.id.txtIndirizzo);
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

		txtRagioneSociale = findViewById(R.id.txtRagioneSociale);
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

		txtNome = findViewById(R.id.txtNome);
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

		txtPiva = findViewById(R.id.txtPiva);
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

		txtCodiceFiscale = findViewById(R.id.txtCodiceFiscale);
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

		sp_classe_cliente = findViewById(R.id.cmb_classe);
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
		
		DataBaseHelper dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.READ_DB);
		HashMap column_list = new HashMap();
		
		column_list.put(ClassiClienteColumnNames.CODCLASSECLIENTE, null);
		column_list.put(ClassiClienteColumnNames.ORDINE, null);
		column_list.put(ClassiClienteColumnNames.DESCRIZIONE, null);
		
		ArrayList<ClassiClientiVO> tmp = dbh.getAllClassiClienti(column_list);
		
		dbh.getSqllitedb().close();
		
		al_classi_cliente = new ArrayList<ClassiClientiVO>();
		al_classi_cliente.add(new ClassiClientiVO());
		al_classi_cliente.addAll(tmp);
		
		CustomSpinnerAdapter icsa = new CustomSpinnerAdapter(RicercaAnagraficheActivity.this, 
															 R.layout.custom_combo_detail, 
															 al_classi_cliente.toArray());
		
		sp_classe_cliente.setAdapter(icsa);
		dbh.close();

		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int itemId = item.getItemId();
		
		if (itemId == android.R.id.home){
			NavUtils.navigateUpFromSameTask(this);
		}
		return true;
	}

	
}
