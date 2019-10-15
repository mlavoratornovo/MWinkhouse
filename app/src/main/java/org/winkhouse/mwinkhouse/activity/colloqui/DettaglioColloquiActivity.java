package org.winkhouse.mwinkhouse.activity.colloqui;

import org.winkhouse.mwinkhouse.activity.adapters.LateralMenuAdapter;
import org.winkhouse.mwinkhouse.activity.anagrafiche.ListaAnagraficheActivity;
import org.winkhouse.mwinkhouse.fragment.colloqui.DettaglioColloquioFragment;
import org.winkhouse.mwinkhouse.util.ActivityMessages;

import org.winkhouse.mwinkhouse.R;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class DettaglioColloquiActivity extends Activity{
	
	private String[] menu_laterale_content = null; 
    private DrawerLayout mDrawerLayout = null;
    private ListView mDrawerList = null;
	private Integer codColloquio = null;
	private Integer codImmobile = null;
	private Integer codAnagrafica = null;
	
	public DettaglioColloquiActivity() {
		// TODO Auto-generated constructor stub
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
		
		codImmobile = getIntent().getIntExtra(ActivityMessages.CODIMMOBILE_ACTION, 0);
		codAnagrafica = getIntent().getIntExtra(ActivityMessages.CODANAGRAFICA_ACTION, 0);
		codColloquio = getIntent().getIntExtra(ActivityMessages.CODCOLLOQUIO_ACTION, 0);
		
		Fragment fragment = new DettaglioColloquioFragment();
		Bundle args = new Bundle();
		args.putInt(ActivityMessages.CODCOLLOQUIO_ACTION, codColloquio);
		args.putInt(ActivityMessages.CODIMMOBILE_ACTION, codImmobile);
		args.putInt(ActivityMessages.CODANAGRAFICA_ACTION, codAnagrafica);
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
		setContentView(R.layout.dettaglio_colloquio_activity);
		
		menu_laterale_content = getResources().getStringArray(R.array.menu_laterale_dettaglio_colloquio);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new LateralMenuAdapter(this, menu_laterale_content));
        
        mDrawerList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            	
                if (position == 0){

                	if ((codColloquio == null) || (codColloquio == 0)){
                		Toast.makeText(DettaglioColloquiActivity.this, "Eseguire il salvataggio del colloquio", Toast.LENGTH_SHORT).show();
                	}else{
                		Intent lista_anagrafiche = new Intent(DettaglioColloquiActivity.this, ListaAnagraficheActivity.class);
                		lista_anagrafiche.putExtra(ActivityMessages.CODCOLLOQUIO_ACTION, codColloquio);
                		startActivity(lista_anagrafiche);
                	}
    				
                }
                
                mDrawerLayout.closeDrawer(mDrawerList);
                
             }
        });
		
	}
	
	@Override
	public void onBackPressed() {
		finish();
	}

	public Integer getCodColloquio() {
		return codColloquio;
	}

	public void setCodColloquio(Integer codColloquio) {
		this.codColloquio = codColloquio;
	}

	
	


}
