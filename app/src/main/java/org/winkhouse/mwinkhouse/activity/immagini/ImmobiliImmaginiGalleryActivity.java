package org.winkhouse.mwinkhouse.activity.immagini;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.winkhouse.mwinkhouse.activity.adapters.ImmaginiImmobiliAdapter;
import org.winkhouse.mwinkhouse.helpers.DataBaseHelper;
import org.winkhouse.mwinkhouse.helpers.ImmaginiHelper;
import org.winkhouse.mwinkhouse.models.ImmagineVO;
import org.winkhouse.mwinkhouse.models.columns.ImmagineColumnNames;
import org.winkhouse.mwinkhouse.models.columns.ImmobiliColumnNames;
import org.winkhouse.mwinkhouse.util.HardwareUtils;

import org.winkhouse.mwinkhouse.R;
import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;

public class ImmobiliImmaginiGalleryActivity extends AppCompatActivity {
	
	public static final String PATH_CAMERA_SHOOT = "PCS";
	public static final int REQUEST_IMAGE_CAPTURE = 1;
	public static final String KEY_IMAGE_SCALE = "imagescale";
	public static final String KEY_IMAGE_QUALITY = "imagequality";
	
	private ImmaginiImmobiliAdapter adapter = null;
	private ListView lv_gallery = null;
	private ArrayList<ImmagineVO> list = null;
	private ImageView dettaglio = null;
	
	private int cod_immobile = 0;
	
	private File photoFile = null;
	
	private HashMap<Integer,Object> selected = new HashMap<Integer,Object>();
	
	public ImmobiliImmaginiGalleryActivity() {
	
	}
	
	protected ArrayList<ImmagineVO> getData(){
		
		list = new ArrayList();
			
		if (getIntent().getExtras() != null){
			cod_immobile = getIntent().getExtras().getInt(ImmobiliColumnNames.CODIMMOBILE);
			getIntent().getExtras().remove(ImmobiliColumnNames.CODIMMOBILE);
		}   
	    
	    DataBaseHelper dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.READ_DB);
	     			 
	    ArrayList<ImmagineVO> ivos = dbh.getImmaginiImmobile(cod_immobile, null);
			 
        Iterator<ImmagineVO> it = ivos.iterator();
	     while(it.hasNext()){
	    	 list.add(it.next());
	     }
	     dbh.getSqllitedb().close();
		return list;
	}

	@Override
	protected void onPostResume() {
		super.onPostResume();
		list.clear();
		list = getData();
		adapter.clear();
		for (ImmagineVO immagineVO : list) {
			adapter.add(immagineVO);	
		}		
		adapter.notifyDataSetChanged();		
	}

	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.immobili_image_gallery);
        
        overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);
        
//        ActionBar ab = getActionBar();
//		ab.setDisplayHomeAsUpEnabled(true);
		
        lv_gallery = findViewById(R.id.immobili_gallery);
        List list = getData();

	    adapter = new ImmaginiImmobiliAdapter(ImmobiliImmaginiGalleryActivity.this, 
	      							          R.layout.dettaglio_gallery_immagini, 
	    		 						      list);
	    lv_gallery.setAdapter(adapter);	
//	    lv_gallery.setOnItemLongClickListener(new OnItemLongClickListener() {
//
//			@Override
//			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
//				ImmagineVO ivo = (ImmagineVO)arg0.getItemAtPosition(arg2);
//				String imagePath = Environment.getExternalStorageDirectory() + File.separator + "winkhouse/import/immagini" + 
//		 				 		   File.separator + ivo.getCodImmobile().toString() + File.separator + ivo.getPathImmagine();
//				
//				Intent dettaglio_immagine = new Intent(ImmobiliImmaginiGalleryActivity.this, DettaglioImmagineActivity.class);
//				dettaglio_immagine.putExtra(ImmagineColumnNames.PATHIMMAGINE, imagePath);
//				ImmobiliImmaginiGalleryActivity.this.startActivity(dettaglio_immagine);
//				
//				return true;
//			}
//	    	
//		}); 
//	    {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//								
//				ImmagineVO ivo = (ImmagineVO)arg0.getItemAtPosition(arg2);
//				String imagePath = Environment.getExternalStorageDirectory() + File.separator + "winkhouse/import/immagini" + 
//		 				 		   File.separator + ivo.getCodImmobile().toString() + File.separator + ivo.getPathImmagine();
//				
//				Intent dettaglio_immagine = new Intent(ImmobiliImmaginiGalleryActivity.this, DettaglioImmagineActivity.class);
//				dettaglio_immagine.putExtra(ImmagineColumnNames.PATHIMMAGINE, imagePath);
//				ImmobiliImmaginiGalleryActivity.this.startActivity(dettaglio_immagine);
//
//			}
//	    	 
//	     });
	    

    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
			HardwareUtils hu = new HardwareUtils();
			if (HardwareUtils.isIntentAvailable(ImmobiliImmaginiGalleryActivity.this, MediaStore.ACTION_IMAGE_CAPTURE)){
				getMenuInflater().inflate(R.menu.menu_gallery, menu);
			}
		}
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int itemId = item.getItemId();
		
		if (itemId == android.R.id.home){
			NavUtils.navigateUpFromSameTask(this);
		}else if (itemId == R.id.scatta_foto) {
			dispatchTakePictureIntent(REQUEST_IMAGE_CAPTURE);
		} else if (itemId == R.id.cancella) {
			ImmaginiHelper ih = new ImmaginiHelper();
			DataBaseHelper dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.WRITE_DB);
			ih.cancellaImmagini(ImmobiliImmaginiGalleryActivity.this,
					 			 dbh,
					 			 cod_immobile, 
					 			 selected.keySet().toArray(new Integer[selected.keySet().size()]));
			dbh.getSqllitedb().close();
			Intent lista_immagini = new Intent(ImmobiliImmaginiGalleryActivity.this, ImmobiliImmaginiGalleryActivity.class);
			lista_immagini.putExtra(ImmagineColumnNames.CODIMMOBILE, cod_immobile);
			ImmobiliImmaginiGalleryActivity.this.startActivity(lista_immagini);
		}

		return true;
	}
	
	private void dispatchTakePictureIntent(int actionCode) {
		
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
		        // Create the File where the photo should go
	        try {
	            photoFile = new ImmaginiHelper().createImageFile(cod_immobile);
	        } catch (IOException ex) {
	            // Error occurred while creating the File
	            
	        }
	        // Continue only if the File was successfully created
	        if (photoFile != null) {
	            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(photoFile));
	            getIntent().putExtra(PATH_CAMERA_SHOOT, photoFile.getAbsolutePath());
	            startActivityForResult(takePictureIntent, actionCode);
	        }
	    }
			
		
	}
		
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if (getIntent().getExtras().getString(PATH_CAMERA_SHOOT) != null){
			
			photoFile = new File(getIntent().getExtras().getString(PATH_CAMERA_SHOOT));
			if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && photoFile.exists()) {
				
				ImmagineVO ivo = new ImmagineVO();
				ivo.setCodImmobile(cod_immobile);
				String file_name = photoFile.getAbsolutePath().substring(photoFile.getAbsolutePath().lastIndexOf(File.separator)+1);
				
				ImmaginiHelper ih = new ImmaginiHelper();
				Point p = ih.getImageDimension(photoFile.getAbsolutePath());
				
				SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
				
				String scale = sharedPref.getString(KEY_IMAGE_SCALE, "0");
				int quality = sharedPref.getInt(KEY_IMAGE_QUALITY, 100);
				
				float dscale = Float.valueOf(scale).floatValue();				
				
				if (dscale > 0.0){
					float fx = p.x*dscale;
					float fy = p.y*dscale;					
					ih.resizeImage(photoFile.getAbsolutePath(), new Float(fx).intValue(), new Float(fy).intValue(),quality);
				}else{					
					ih.resizeImage(photoFile.getAbsolutePath(), p.x, p.y,quality);
				}
				
				ivo.setPathImmagine(file_name);
				DataBaseHelper dbHelper = new DataBaseHelper(getApplicationContext(),DataBaseHelper.NONE_DB);
				dbHelper.saveImmagine(ivo);
				dbHelper.close();
				onPostResume();
				
		    }
			
		}
		
	}

	public void onCheckboxClicked(View view) {

	    boolean checked = ((CheckBox) view).isChecked();
	    
	    Integer codimmagine = (Integer)view.getTag();
	    
	    int id = view.getId();
		if (id == R.id.chk_item) {
			if (checked){
				selected.put(codimmagine, null);
			}else{
				selected.remove(codimmagine);
			}
		}
	}
	
	public void onImageClicked(View view){
		
	    Integer codimmagine = (Integer)view.getTag();
	    
	    DataBaseHelper dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.NONE_DB);
	    
		ImmagineVO ivo = dbh.getImmagineById(codimmagine, null);
		
		dbh.close();
		
		String imagePath = Environment.getExternalStorageDirectory() + File.separator + "winkhouse/immagini" +
 				 		   File.separator + ivo.getCodImmobile().toString() + File.separator + ivo.getPathImmagine();
		
		Intent dettaglio_immagine = new Intent(ImmobiliImmaginiGalleryActivity.this, DettaglioImmagineActivity.class);
		
		dettaglio_immagine.putExtra(ImmagineColumnNames.PATHIMMAGINE, imagePath);
		ImmobiliImmaginiGalleryActivity.this.startActivity(dettaglio_immagine);
		
	}

	@Override
	public void onBackPressed() {
		finish();
	}
}
