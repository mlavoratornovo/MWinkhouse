package org.winkhouse.mwinkhouse.activity.immagini;

import java.io.File;

import org.winkhouse.mwinkhouse.models.columns.ImmagineColumnNames;

import org.winkhouse.mwinkhouse.R;
import android.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;

public class DettaglioImmagineActivity extends AppCompatActivity {

	private ImageView immagine = null;
		
	public DettaglioImmagineActivity() {

	}
	
	@Override
	protected void onPostResume() {
		
		super.onPostResume();		
		setImmagine();
		
	}
	
	private void setImmagine(){
		
		String pathImmagine = null;
		
		if (getIntent().getExtras() != null){
			pathImmagine = getIntent().getExtras().getString(ImmagineColumnNames.PATHIMMAGINE);
			getIntent().getExtras().remove(ImmagineColumnNames.PATHIMMAGINE);
		}   
		
        File imgFile = new  File(pathImmagine);

        if(imgFile.exists()){
        	//ImageView iv  = (ImageView)waypointListView.findViewById(R.id.waypoint_picker_photo);
        	Bitmap d = new BitmapDrawable(getApplicationContext().getResources() , imgFile.getAbsolutePath()).getBitmap();
        	int nh = (int) ( d.getHeight() * (512.0 / d.getWidth()) );
        	Bitmap scaled = Bitmap.createScaledBitmap(d, 512, nh, true);
//        	iv.setImageBitmap(scaled);        	
//        	Bitmap b = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        	immagine.setImageBitmap(scaled);
        }

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
//        ActionBar ab = getActionBar();
//		ab.setDisplayHomeAsUpEnabled(true);
		
		setContentView(R.layout.dettaglio_immagine);
		overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);
		
		immagine = findViewById(R.id.immagine);
		
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
