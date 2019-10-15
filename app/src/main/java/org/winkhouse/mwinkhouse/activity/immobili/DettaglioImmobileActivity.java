package org.winkhouse.mwinkhouse.activity.immobili;

import org.winkhouse.mwinkhouse.activity.adapters.LateralMenuAdapter;
import org.winkhouse.mwinkhouse.activity.colloqui.ListaColloquiActivity;
import org.winkhouse.mwinkhouse.activity.stanze.ListaStanzeActivity;
import org.winkhouse.mwinkhouse.fragment.immobili.DettaglioImmobileFragment;
import org.winkhouse.mwinkhouse.models.columns.ImmobiliColumnNames;
import org.winkhouse.mwinkhouse.util.ActivityMessages;

import org.winkhouse.mwinkhouse.R;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class DettaglioImmobileActivity extends AppCompatActivity {
	
	private String[] menu_laterale_content = null; 
    private DrawerLayout mDrawerLayout = null;
    private ListView mDrawerList = null;
    private Integer codImmobile = null;
    
	public DettaglioImmobileActivity() {
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		getData();
	}

	@Override
	protected void onStart() {
		super.onStart();
		getData();
	}
	
	private void getData(){
		
		codImmobile = getIntent().getIntExtra(ImmobiliColumnNames.CODIMMOBILE, 0);
		Fragment fragment = new DettaglioImmobileFragment();
		Bundle args = new Bundle();
		args.putInt(ImmobiliColumnNames.CODIMMOBILE, codImmobile);
		fragment.setArguments(args);

		// Insert the fragment by replacing any existing fragment
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
					   .replace(R.id.content_frame, fragment)
					   .commit();

//    // Highlight the selected item, update the title, and close the drawer
//		mDrawerList.setItemChecked(position, true);
//		setTitle(mPlanetTitles[position]);
//		mDrawerLayout.closeDrawer(mDrawerList);
//	
//		
//		if (codImmobile != 0){
//			DataBaseHelper dbh = new DataBaseHelper(getApplicationContext(),DataBaseHelper.NONE_DB);
//			ImmobiliVO ivo = dbh.getImmobileById(codImmobile, null);
//			ivo = (ivo != null) ? ivo : new ImmobiliVO(); 
//			setImmobileVO(ivo);
//			dbh.close();
//		}else{
//			setImmobileVO(new ImmobiliVO());
//		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dettaglio_immobile_activity);
		
		menu_laterale_content = getResources().getStringArray(R.array.menu_laterale_dettaglio_immobile);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerList = findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new LateralMenuAdapter(this, menu_laterale_content));
        
        mDrawerList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 2){

                	if ((codImmobile == null) || (codImmobile == 0)){
                		Toast.makeText(DettaglioImmobileActivity.this, "Eseguire il salvataggio dell'immobile", Toast.LENGTH_SHORT).show();
                	}else{

                		Intent colloqui = new Intent(DettaglioImmobileActivity.this, ListaColloquiActivity.class);
                		colloqui.putExtra(ActivityMessages.CODIMMOBILE_ACTION, codImmobile);                		
                		startActivity(colloqui);
                	}
    				
                }
                if (position == 1){

                	if ((codImmobile == null) || (codImmobile == 0)){
                		Toast.makeText(DettaglioImmobileActivity.this, "Eseguire il salvataggio dell'immobile", Toast.LENGTH_SHORT).show();
                	}else{

                		Intent stanze = new Intent(DettaglioImmobileActivity.this, ListaStanzeActivity.class);
                		stanze.putExtra(ActivityMessages.CODIMMOBILE_PROPIETARI_ACTION, codImmobile);
                		stanze.putExtra(ActivityMessages.STANZE_IMMOBILI_ACTION, true);
                		startActivity(stanze);
                	}
    				
                }
                if (position == 0){

                	if ((codImmobile == null) || (codImmobile == 0)){
                		Toast.makeText(DettaglioImmobileActivity.this, "Eseguire il salvataggio dell'immobile", Toast.LENGTH_SHORT).show();
                	}else{
                		DettaglioImmobileFragment dif = (DettaglioImmobileFragment)getFragmentManager().findFragmentById(R.id.content_frame);
                		
                		Intent extraInfo = new Intent(DettaglioImmobileActivity.this, DettaglioImmobileExtraInfoActivity.class);
                		extraInfo.putExtra(ActivityMessages.CODIMMOBILE_PROPIETARI_ACTION, codImmobile);
                		startActivity(extraInfo);
                	}                	

    				
                }
                
                mDrawerLayout.closeDrawer(mDrawerList);
                
             }
        });
		
	}
	

	public Integer getCodImmobile() {
		return codImmobile;
	}

	public void setCodImmobile(Integer codImmobile) {
		this.codImmobile = codImmobile;
	}

	@Override
	public void onBackPressed() {
		finish();
	}
	
}
