package org.winkhouse.mwinkhouse.activity.immobili;

import android.accounts.AccountManager;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.net.wifi.WifiManager;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.content.ContextCompat;
import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.content.pm.PackageManager;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.util.ExponentialBackOff;

import org.winkhouse.mwinkhouse.R;
import org.winkhouse.mwinkhouse.activity.adapters.CustomSpinnerAdapter;
import org.winkhouse.mwinkhouse.helpers.DataBaseHelper;
import org.winkhouse.mwinkhouse.helpers.ExportSearchParamsHelper;
import org.winkhouse.mwinkhouse.service.CloudSearchGDriveService;
import org.winkhouse.mwinkhouse.service.GDriveHelper;
import org.winkhouse.mwinkhouse.helpers.SearchParam;
import org.winkhouse.mwinkhouse.models.ClasseEnergeticaVO;
import org.winkhouse.mwinkhouse.models.RiscaldamentiVO;
import org.winkhouse.mwinkhouse.models.StatoConservativoVO;
import org.winkhouse.mwinkhouse.models.TipologieImmobiliVO;
import org.winkhouse.mwinkhouse.models.columns.ClasseEnergeticaColumnNames;
import org.winkhouse.mwinkhouse.models.columns.ImmobiliColumnNames;
import org.winkhouse.mwinkhouse.models.columns.RiscaldamentiColumnNames;
import org.winkhouse.mwinkhouse.models.columns.StatoConservativoColumnNames;
import org.winkhouse.mwinkhouse.models.columns.TipologieImmobiliColumnNames;

import org.winkhouse.mwinkhouse.service.CloudSearchService;
import org.winkhouse.mwinkhouse.service.WinkCloudHelper;
import org.winkhouse.mwinkhouse.service.WinkCloudSearchService;
import org.winkhouse.mwinkhouse.util.ActivityMessages;
import org.winkhouse.mwinkhouse.util.SysSettingNames;


import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import java.util.Random;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class RicercaImmobiliActivity extends AppCompatActivity {
	
	private EditText txtProvincia = null;
	private EditText txtCap = null;
	private EditText txtMqDa = null;
	private EditText txtMqA = null;
	private EditText txtAnnoCostruzione = null;
	private EditText txtCitta = null;
	private EditText txtDescrizione = null;
	private EditText txtIndirizzo = null;
	private EditText txtZona = null;
	private Spinner sp_classe_energetica = null;
	private Spinner sp_riscaldamenti = null;
	private Spinner sp_tipo_immobile = null;
	private Spinner sp_statoconservativo = null;	
	
	private ArrayList<StatoConservativoVO> al_statoconservativo = null;
	private ArrayList<ClasseEnergeticaVO> al_classi_energetiche = null;
	private ArrayList<RiscaldamentiVO> al_riscaldamenti = null;
	private ArrayList<TipologieImmobiliVO> al_tipi_immobili = null;
    private GoogleApiClient mGoogleApiClient = null;

    private Animation animazione_rotazione_out = null;

	static final int REQUEST_ACCOUNT_PICKER = 1000;


	static final int REQUEST_AUTHORIZATION = 1001;
	static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
	static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;


	public RicercaImmobiliActivity() {

	}
	
	protected ArrayList<SearchParam> getParamsObjs(){
		
		ArrayList<SearchParam> parametri = new ArrayList<SearchParam>();
		
		if ((txtMqDa.getText().toString() != null && !txtMqDa.getText().toString().trim().equalsIgnoreCase("")) ||
			(txtMqA.getText().toString() != null && !txtMqA.getText().toString().trim().equalsIgnoreCase(""))){
			
			String value_da = null;
			String value_a = null;
			
			if (txtMqDa.getText().toString() != null && !txtMqDa.getText().toString().trim().equalsIgnoreCase("")){
				value_da = txtMqDa.getText().toString();
			}
			if (txtMqA.getText().toString() != null && !txtMqA.getText().toString().trim().equalsIgnoreCase("")){
				value_a = txtMqA.getText().toString();
			}
			
			SearchParam sp = new SearchParam(ImmobiliColumnNames.MQ, value_da, value_a, "AND", Integer.class.getName(),SearchParam.IMMOBILI);
			parametri.add(sp);
		}
		
		if (txtCitta.getText().toString() != null && !txtCitta.getText().toString().trim().equalsIgnoreCase("")){
				
			String value_da = null;					
				
			if (txtCitta.getText().toString() != null && !txtCitta.getText().toString().trim().equalsIgnoreCase("")){
				value_da = txtCitta.getText().toString();
			}
				
			SearchParam sp = new SearchParam(ImmobiliColumnNames.CITTA, "%" + value_da + "%", null, "AND", String.class.getName(),SearchParam.IMMOBILI);
			parametri.add(sp);
		}
		
		if (txtIndirizzo.getText().toString() != null && !txtIndirizzo.getText().toString().trim().equalsIgnoreCase("")){
			
			String value_da = null;					
				
			if (txtIndirizzo.getText().toString() != null && !txtIndirizzo.getText().toString().trim().equalsIgnoreCase("")){
				value_da = txtIndirizzo.getText().toString();
			}
				
			SearchParam sp = new SearchParam(ImmobiliColumnNames.INDIRIZZO, "%" + value_da + "%", null, "AND", String.class.getName(),SearchParam.IMMOBILI);
			parametri.add(sp);
		}

		if (txtZona.getText().toString() != null && !txtZona.getText().toString().trim().equalsIgnoreCase("")){
			
			String value_da = null;					
				
			if (txtZona.getText().toString() != null && !txtZona.getText().toString().trim().equalsIgnoreCase("")){
				value_da = txtZona.getText().toString();
			}
				
			SearchParam sp = new SearchParam(ImmobiliColumnNames.ZONA, "%" + value_da + "%", null, "AND", String.class.getName(),SearchParam.IMMOBILI);
			parametri.add(sp);
		}
		
		ClasseEnergeticaVO cevo = (ClasseEnergeticaVO)sp_classe_energetica.getSelectedItem();
		if (cevo.getCodClasseEnergetica() != 0){

			SearchParam sp = new SearchParam(ImmobiliColumnNames.CODCLASSEENERGETICA, cevo.getCodClasseEnergetica().toString(), null, "AND", Integer.class.getName(),SearchParam.IMMOBILI);
			parametri.add(sp);
			
		}

		RiscaldamentiVO rvo = (RiscaldamentiVO)sp_riscaldamenti.getSelectedItem();
		if (rvo.getCodRiscaldamento() != 0){

			SearchParam sp = new SearchParam(ImmobiliColumnNames.CODRISCALDAMENTO, rvo.getCodRiscaldamento().toString(), null, "AND", Integer.class.getName(),SearchParam.IMMOBILI);
			parametri.add(sp);
			
		}

		TipologieImmobiliVO tivo = (TipologieImmobiliVO)sp_tipo_immobile.getSelectedItem();
		if (tivo.getCodTipologiaImmobile() != 0){

			SearchParam sp = new SearchParam(ImmobiliColumnNames.CODTIPOLOGIA, tivo.getCodTipologiaImmobile().toString(), null, "AND", Integer.class.getName(),SearchParam.IMMOBILI);
			parametri.add(sp);
			
		}
		
		StatoConservativoVO scvo = (StatoConservativoVO)sp_statoconservativo.getSelectedItem();
		if (scvo.getCodStatoConservativo() != 0){

			SearchParam sp = new SearchParam(ImmobiliColumnNames.CODSTATO, scvo.getCodStatoConservativo().toString(), null, "AND", Integer.class.getName(),SearchParam.IMMOBILI);
			parametri.add(sp);
			
		}				
		
		return parametri;
	}


	class LocalRicercaRunner implements Runnable{

        private View v = null;

        public LocalRicercaRunner(View v){
            this.v = v;
        }

        @Override
        public void run() {

            ArrayList<SearchParam> parametri = getParamsObjs();
            Intent it = new Intent(v.getContext(), ListaImmobiliActivity.class);
            it.putExtra(ActivityMessages.SEARCH_ACTION, true);
            it.putExtra(SearchParam.class.getName(),parametri.toArray(new SearchParam[parametri.size()]));
            startActivity(it);

        }

    }

	class startCloudSearch implements Runnable{

        private Context context = null;

        public startCloudSearch(Context context){
            this.context=context;
        }

        public boolean isConnected(Context context) {

            ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = null;

            if (connectivityManager != null) {
                networkInfo = connectivityManager.getActiveNetworkInfo();
            }

            if (networkInfo == null || networkInfo.getState() != NetworkInfo.State.CONNECTED){

                android.net.wifi.WifiManager m = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                m.setWifiEnabled(true);
                int count = 0;
				try {
					Thread.sleep(1200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
                while (!executeCommand()){
                    if (count == 5){
                        return false;
                    }
                    networkInfo = connectivityManager.getActiveNetworkInfo();
                    count ++;
					try {
						Thread.sleep(1200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
                return true;
            }

            return true;
        }

        private boolean executeCommand(){

            System.out.println("executeCommand");
            Runtime runtime = Runtime.getRuntime();
            try
            {
                Process  mIpAddrProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
                int mExitValue = mIpAddrProcess.waitFor();
                System.out.println(" mExitValue "+mExitValue);
                return mExitValue == 0;
            }
            catch (InterruptedException ignore)
            {
                ignore.printStackTrace();
                System.out.println(" Exception:"+ignore);
            }
            catch (IOException e)
            {
                e.printStackTrace();
                System.out.println(" Exception:"+e);
            }
            return false;
        }

        public void run() {

            ArrayList<SearchParam> parametri = getParamsObjs();

            if (parametri.size() > 0){

                int permissionCheckSD = ContextCompat.checkSelfPermission(RicercaImmobiliActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

                if(isConnected(getApplicationContext())) {

                    if (permissionCheckSD != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(RicercaImmobiliActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                    } else {

                        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(RicercaImmobiliActivity.this);
						Random rand = new Random();

						int randomNum = rand.nextInt(1000);

                        if (sharedPref.getString(SysSettingNames.CLOUD_CHANNEL, "").equalsIgnoreCase(SysSettingNames.CLOUD_CHANNEL_FTP)) {

                            Intent it = new Intent(RicercaImmobiliActivity.this, CloudSearchService.class);

                            it.putExtra("randomNo", randomNum);
                            it.putExtra(ActivityMessages.SEARCH_ACTION, true);
                            it.putExtra(SearchParam.class.getName(), parametri.toArray(new SearchParam[parametri.size()]));
                            try {
                                startService(it);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            RicercaImmobiliActivity.this.finish();

                        }else if (sharedPref.getString(SysSettingNames.CLOUD_CHANNEL, "").equalsIgnoreCase(SysSettingNames.CLOUD_CHANNEL_WINKCLOUD)){

                            Intent it = new Intent(RicercaImmobiliActivity.this, WinkCloudSearchService.class);
                            it.putExtra("randomNo", randomNum);
                            it.putExtra(ActivityMessages.SEARCH_ACTION, true);
                            it.putExtra(SearchParam.class.getName(), parametri.toArray(new SearchParam[parametri.size()]));
                            try {
                                startService(it);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            RicercaImmobiliActivity.this.finish();
                        }else{
                            Toast t = Toast.makeText(getApplicationContext(), "Nessun canale di ricerca selezionato", Toast.LENGTH_LONG);
                            t.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
                            t.show();
                        }

                    }


                }else{
                    Toast t = Toast.makeText(getApplicationContext(),"Impossibile connettersi ad internet",Toast.LENGTH_LONG);
                    t.setGravity(Gravity.TOP|Gravity.CENTER,0,0);
                    t.show();
                }
            }else{
                Toast t = Toast.makeText(getApplicationContext(),"Nessun criterio di ricerca selezionato",Toast.LENGTH_LONG);
                t.setGravity(Gravity.TOP|Gravity.CENTER,0,0);
                t.show();
            }
        }


    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
        animazione_rotazione_out = AnimationUtils.loadAnimation(RicercaImmobiliActivity.this, R.anim.activity_close_scale);

		setContentView(R.layout.ricerca_immobili);
		
		overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);
		
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		
		ImageButton ricerca = findViewById(R.id.btn_ricerca_immobili);
		ricerca.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

                v.startAnimation(animazione_rotazione_out);
                v.postDelayed(new LocalRicercaRunner(v), 1200);

			}
		});

		ImageButton ricerca_cloud = findViewById(R.id.btn_ricerca_immobili_cloud);
		ricerca_cloud.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
                v.startAnimation(animazione_rotazione_out);
                v.postDelayed(new startCloudSearch(v.getContext()), 1200);
            }
			
		});

		txtCitta = findViewById(R.id.txtRCitta);
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

		txtIndirizzo = findViewById(R.id.txtRIndirizzo);
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

		txtZona = findViewById(R.id.txtRZona);
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
		
		
		TextView labelMqDa = findViewById(R.id.labelRMqDa);
		labelMqDa.setWidth((dm.widthPixels)/2);
		TextView labelMqA = findViewById(R.id.labelRMqA);
		labelMqA.setWidth((dm.widthPixels)/2);

		txtMqDa = findViewById(R.id.txtRMqDa);
        txtMqDa.setInputType( InputType.TYPE_CLASS_NUMBER );
		txtMqDa.setWidth((dm.widthPixels)/2);
		txtMqDa.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus){
					txtMqDa.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
				}else{
					txtMqDa.getBackground().setColorFilter(Color.parseColor("#f1ebeb"), PorterDuff.Mode.SRC_IN);
				}
				
			}
		});

		
		txtMqA = findViewById(R.id.txtRMqA);
        txtMqA.setInputType( InputType.TYPE_CLASS_NUMBER );
		txtMqA.setWidth((dm.widthPixels)/2);
		txtMqA.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus){
					txtMqA.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
				}else{
					txtMqA.getBackground().setColorFilter(Color.parseColor("#f1ebeb"), PorterDuff.Mode.SRC_IN);
				}
				
			}
		});
		
		sp_classe_energetica = findViewById(R.id.cmb_Rclasse_energetica);
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
		
		DataBaseHelper dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.READ_DB);
		HashMap column_list = new HashMap();
		
		column_list.put(ClasseEnergeticaColumnNames.CODCLASSEENERGETICA, null);
		column_list.put(ClasseEnergeticaColumnNames.NOME, null);
		column_list.put(ClasseEnergeticaColumnNames.DESCRIZIONE, null);
		
		ArrayList<ClasseEnergeticaVO> tmp = dbh.getAllClassiEnergetiche(column_list);
		al_classi_energetiche = new ArrayList<ClasseEnergeticaVO>();
		al_classi_energetiche.add(new ClasseEnergeticaVO());
		al_classi_energetiche.addAll(tmp);
		
		CustomSpinnerAdapter icsa = new CustomSpinnerAdapter(RicercaImmobiliActivity.this, 
																			 R.layout.custom_combo_detail, 
																			 al_classi_energetiche.toArray());
		
		sp_classe_energetica.setAdapter(icsa);
		
		sp_riscaldamenti = findViewById(R.id.cmb_Rriscaldamento);
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
		
		CustomSpinnerAdapter icsa2 = new CustomSpinnerAdapter(RicercaImmobiliActivity.this, 
																			  R.layout.custom_combo_detail, 
																			  al_riscaldamenti.toArray());
		
		sp_riscaldamenti.setAdapter(icsa2);
		
		sp_tipo_immobile = findViewById(R.id.cmb_Rtipologia);
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
		column_list.put(ClasseEnergeticaColumnNames.DESCRIZIONE, null);
		
		ArrayList<TipologieImmobiliVO> tmp1 = dbh.getAllTipologieImmobili(column_list);
		al_tipi_immobili = new ArrayList<TipologieImmobiliVO>();
		al_tipi_immobili.add(new TipologieImmobiliVO());
		al_tipi_immobili.addAll(tmp1);
		
		CustomSpinnerAdapter icsa1 = new CustomSpinnerAdapter(RicercaImmobiliActivity.this, 
																			 R.layout.custom_combo_detail, 
																			 al_tipi_immobili.toArray());
		
		sp_tipo_immobile.setAdapter(icsa1);

		sp_statoconservativo = findViewById(R.id.cmb_Rstato);
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
		column_list.put(ClasseEnergeticaColumnNames.DESCRIZIONE, null);
		
		ArrayList<StatoConservativoVO> tmp3 = dbh.getAllStatoConservativo(column_list);
		al_statoconservativo = new ArrayList<StatoConservativoVO>();
		al_statoconservativo.add(new StatoConservativoVO());
		al_statoconservativo.addAll(tmp3);
		
		CustomSpinnerAdapter icsa3 = new CustomSpinnerAdapter(RicercaImmobiliActivity.this, 
														      R.layout.custom_combo_detail,
															  al_statoconservativo.toArray());
		
		sp_statoconservativo.setAdapter(icsa3);
		
		dbh.getSqllitedb().close();
		
	}

	@AfterPermissionGranted(REQUEST_PERMISSION_GET_ACCOUNTS)
	private void chooseAccount() {
		if (EasyPermissions.hasPermissions(this, Manifest.permission.GET_ACCOUNTS)) {
			if (GDriveHelper.getInstance().getAccoutName() != null) {
                GDriveHelper.getInstance().getmCredential().setSelectedAccountName(GDriveHelper.getInstance().getAccoutName());
//                GDriveHelper.getInstance().doAndUploadRequest();

			} else {
				// Start a dialog from which the user can choose an account
				startActivityForResult(GDriveHelper.getInstance().getmCredential()
                                                                 .newChooseAccountIntent(),
                                       REQUEST_ACCOUNT_PICKER);
			}
		} else {
			// Request the GET_ACCOUNTS permission via a user dialog
			EasyPermissions.requestPermissions(this,
					                           "Seleziona un account google",
					                           REQUEST_PERMISSION_GET_ACCOUNTS,
					                           Manifest.permission.GET_ACCOUNTS);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		switch(requestCode) {

			case REQUEST_GOOGLE_PLAY_SERVICES:
//                GDriveHelper.getInstance().doAndUploadRequest();
                break;

			case REQUEST_ACCOUNT_PICKER:

				if (resultCode == RESULT_OK && data != null && data.getExtras() != null) {

					String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);

					if (accountName != null) {

                        Random rand = new Random();

                        int randomNum = rand.nextInt(1000);

                        GDriveHelper.getInstance().getmCredential().setSelectedAccountName(accountName);
                        Intent it = new Intent(RicercaImmobiliActivity.this, CloudSearchGDriveService.class);

                        it.putExtra("randomNo", randomNum);
                        it.putExtra(ActivityMessages.SEARCH_ACTION, true);
                        ArrayList<SearchParam> parametri = getParamsObjs();
                        it.putExtra(SearchParam.class.getName(), parametri.toArray(new SearchParam[parametri.size()]));
                        try {
                            startService(it);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
					}
				}else{
                    Toast t = Toast.makeText(RicercaImmobiliActivity.this,
                                             "Permesso utilizzo gdrive non concesso",
                                             Toast.LENGTH_LONG);
                    t.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
                    t.show();

                }
				break;
			case REQUEST_AUTHORIZATION:

				if (resultCode == RESULT_OK) {
//                    GDriveHelper.getInstance().doAndUploadRequest();
                }
				break;
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
										   @NonNull String[] permissions,
										   @NonNull int[] grantResults) {

		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);

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
	protected void onStart() {
		super.onStart();

	}

	@Override
	protected void onStop() {
		super.onStop();

	}




}
