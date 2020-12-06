package org.winkhouse.mwinkhouse.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import org.winkhouse.mwinkhouse.util.ActivityMessages;
import org.winkhouse.mwinkhouse.util.SDFileSystemUtils;

import org.winkhouse.mwinkhouse.R;
import org.winkhouse.mwinkhouse.helpers.DataBaseHelper;
import org.winkhouse.mwinkhouse.helpers.ExportDataWirelessThread;
import org.winkhouse.mwinkhouse.helpers.ImportDataWirelessThread;
import org.winkhouse.mwinkhouse.models.AnagraficheVO;
import org.winkhouse.mwinkhouse.models.ImmobiliVO;


import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ImportActivity extends Activity implements ActivityCompat.OnRequestPermissionsResultCallback{
	private DisplayMetrics dm = null;
	private final static String REGEX_IPADDRES = "\\b(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b";
    private static final int REQUEST_WRITE_PERMISSION = 786;

    private Boolean havePermissions = false;
	private EditText etpathzipfile = null;

	private TextView top_text = null;
    private TextView labelPercorsoZip = null;
	private TextView bottom_text = null;
	private TextView top_label = null;
	private TextView bottom_label = null;
	private ImageButton salva = null; 
	private ProgressBar top_progress = null;
	private ProgressBar bottom_progress = null;
	private AlertDialog.Builder alertDialog = null;

	private ArrayList itemsToExport = null;

	private int current_type = -1;
	
	Animation animazione_rotazione_out = null;
	
	public ImportActivity() {
		
	}

	public class ThreadSincro{

		private Boolean status = null;
		private String filename = null;

		public ThreadSincro(Boolean status,String filename){
			this.status = status;
			this.filename = filename;
		}

		public synchronized Boolean getStatus(){
			return this.status;
		}

		public synchronized void setStatus(Boolean status){
			this.status = status;
		}

		public synchronized String getFilename() {
			return filename;
		}

		public synchronized void setFilename(String filename) {
			this.filename = filename;
		}


	}

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        havePermissions = requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED;
    }

	class SalvaExportListener implements OnClickListener{

        public SalvaExportListener() {

        }

        @Override
        public void onClick(View arg0) {

            arg0.startAnimation(animazione_rotazione_out);
            if (havePermissions == true) {
                File f = new File(etpathzipfile.getText().toString());
                if (!etpathzipfile.getText().toString().trim().equalsIgnoreCase("") && (!f.exists() || !f.isDirectory())) {

                        Toast.makeText( ImportActivity.this, "Selezionare la cartella di esportazione", Toast.LENGTH_LONG).show();

                } else {

                    try {
                        ThreadSincro ts = new ThreadSincro(true, null);
                        ExportDataWirelessThread edwt = new ExportDataWirelessThread(
                                ImportActivity.this,
                                top_progress,
                                top_text,
                                ts, itemsToExport,
                                etpathzipfile.getText().toString());
                        edwt.start();


                    } catch (Exception e) {
                        Log.d("WINKHOUSE", e.toString());
                        Toast.makeText(ImportActivity.this, "Impossibile esportare", Toast.LENGTH_SHORT).show();
                    }

                }
            }else{
                Toast.makeText(ImportActivity.this, "Non sono stati concessi i permessi di lettura e scrittura", Toast.LENGTH_LONG).show();
            }

        }
	}
	
	
	class SalvaImportListener implements OnClickListener {

        public SalvaImportListener() {

        }

        @Override
        public void onClick(View arg0) {

            arg0.startAnimation(animazione_rotazione_out);
            if (havePermissions == true) {
                if (!etpathzipfile.getText().toString().trim().equalsIgnoreCase("")) {
                    File f = new File(etpathzipfile.getText().toString());
                    if (f.exists()) {
                        try {

                            ThreadSincro ts = new ThreadSincro(true, etpathzipfile.getText().toString());
                            ImportDataWirelessThread idwt = new ImportDataWirelessThread(ImportActivity.this, top_progress, top_text, ts);
                            idwt.start();


                        } catch (Exception e) {
                            Log.d("WINKHOUSE", e.toString());
                            Toast.makeText(ImportActivity.this, "Impossibile contattare winkhouse", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(ImportActivity.this, "Impossibile trovare il file", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(ImportActivity.this, "Selezionare file zip", Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(ImportActivity.this, "Non sono stati concessi i permessi di lettura e scrittura", Toast.LENGTH_LONG).show();
            }

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 123 && resultCode == RESULT_OK) {
            try {
                Uri selectedfile = data.getData();
                if (selectedfile.toString().contains("winkhouse")) {

                    String docId = DocumentsContract.getDocumentId(selectedfile);
                    etpathzipfile.setText(Environment.getExternalStorageDirectory() + File.separator + docId.split(":")[1]);
                }else{
                    Toast.makeText(ImportActivity.this,"Si possono selezionare solo i file nella cartella winkhouse", Toast.LENGTH_LONG).show();
                }
            }catch (Exception e){
                Toast.makeText(ImportActivity.this,"Utilizzare il selettore file di Android", Toast.LENGTH_LONG).show();
            }

        }

        if(requestCode == 124 && resultCode == RESULT_OK) {
            try {

                Uri selectedfile = data.getData();
                if (selectedfile.toString().contains("winkhouse")) {
                        Uri docUri = DocumentsContract.buildDocumentUriUsingTree(selectedfile,
                                DocumentsContract.getTreeDocumentId(selectedfile));

                        String path = SDFileSystemUtils.getPath(this, docUri);

                        etpathzipfile.setText(path);
                }else{
                    Toast.makeText(ImportActivity.this,"Si possono selezionare solo cartelle nella cartella winkhouse", Toast.LENGTH_LONG).show();
                }
            }catch (Exception e){
                Toast.makeText(ImportActivity.this,"Utilizzare il selettore file di Android", Toast.LENGTH_LONG).show();
            }

        }
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		 
		animazione_rotazione_out = AnimationUtils.loadAnimation(this, R.anim.activity_close_scale);

		dm = new DisplayMetrics();
 		getWindowManager().getDefaultDisplay().getMetrics(dm);
		overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);
		 
        setContentView(R.layout.import_activity);

        top_label = findViewById(R.id.top_label);
        top_label.setText("Avanzamento download file");

        labelPercorsoZip = findViewById(R.id.labelPercorsoZip);

 		DataBaseHelper dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.NONE_DB);

 		etpathzipfile = findViewById(R.id.pathzipfile);
        etpathzipfile.setMaxWidth(300);
        etpathzipfile.setOnFocusChangeListener(new OnFocusChangeListener() {
 			
 			@Override
 			public void onFocusChange(View v, boolean hasFocus) {
 				if (hasFocus){
                    etpathzipfile.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
 				}else{
                    etpathzipfile.getBackground().setColorFilter(Color.parseColor("#f1ebeb"), PorterDuff.Mode.SRC_IN);
 				}
 				
 			}
 		});
        etpathzipfile.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (current_type == ActivityMessages.IMPORT_ACTION) {
                    File f = new File(Environment.getExternalStorageDirectory().getPath() +
                            File.separator +
                            "winkhouse" +
                            File.separator +
                            "import");

                    if (!f.exists()){
                        f.mkdirs();
                    }

                    Intent intent = new Intent().setDataAndType(Uri.parse(Environment.getExternalStorageDirectory().getPath() +
                                    File.separator +
                                    "winkhouse" +
                                    File.separator +
                                    "import"),
                            "application/zip")
                            .setAction(Intent.ACTION_GET_CONTENT);

                    startActivityForResult(Intent.createChooser(intent, "Seleziona un file zip"), 123);
                }else{
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
                        i.addCategory(Intent.CATEGORY_DEFAULT);
                        startActivityForResult(Intent.createChooser(i, "Seleziona cartella"), 124);
                    }
                }
                return true;
            }
        });

 		salva = findViewById(R.id.btn_avvia);
 		if (current_type == ActivityMessages.IMPORT_ACTION){
 			salva.setOnClickListener(new SalvaImportListener());
 		}else{
 			salva.setOnClickListener(new SalvaExportListener());
 		}
         
  		 ImageButton chiudi = findViewById(R.id.btn_chiudi);
 		 chiudi.setOnClickListener(new OnClickListener() {
 			
 			@Override
 			public void onClick(View v) {
 				v.startAnimation(animazione_rotazione_out);
 				finish();
 				
 			}
 		});
 		 
 		top_progress = findViewById(R.id.top_progress);
 		top_text = findViewById(R.id.top_text);

        if ((ActivityCompat.checkSelfPermission(ImportActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ||
            (ActivityCompat.checkSelfPermission(ImportActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)){
            ActivityCompat.requestPermissions(ImportActivity.this,
                                              new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                              REQUEST_WRITE_PERMISSION);
        }else{
            havePermissions = true;
        }

	}


	@Override
	protected void onStart() {
		
		super.onStart();
        ArrayList<Integer> alImmobili = getIntent().getExtras()
                                                   .getIntegerArrayList(ActivityMessages.IMMOBILI_LIST);
        ArrayList<Integer> alAnagrafiche = getIntent().getExtras()
                                                      .getIntegerArrayList(ActivityMessages.ANAGRAFICHE_LIST);

        if (((alImmobili != null) && (alImmobili.size() > 0)) ||
            ((alAnagrafiche != null) && (alAnagrafiche.size() > 0))){
            current_type = ActivityMessages.EXPORT_ACTION;
        }else{
            current_type = ActivityMessages.IMPORT_ACTION;
        }

        DataBaseHelper dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.NONE_DB);

        if (current_type == ActivityMessages.IMPORT_ACTION){
            top_label.setText("Avanzamento importazione dati");
            salva.setOnClickListener(new SalvaImportListener());
            setTitle("Importazione");
            labelPercorsoZip.setText("File zip da importare");
        }else{

            top_label.setText("Avanzamento esportazione dati");
            salva.setOnClickListener(new SalvaExportListener());
            setTitle("Esportazione");
            labelPercorsoZip.setText("Cartella dove esportare il file zip");

            itemsToExport = new ArrayList();

            if (alImmobili != null && alAnagrafiche == null){
                for (Integer codimmobile : alImmobili) {
                    itemsToExport.add(dbh.getImmobileById(codimmobile, null));
                }
            }

            if (alImmobili == null && alAnagrafiche != null){
                for (Integer codanagrafica : alAnagrafiche) {
                    itemsToExport.add(dbh.getAnagraficaById(codanagrafica, null));
                }
            }

        }

	}

}
