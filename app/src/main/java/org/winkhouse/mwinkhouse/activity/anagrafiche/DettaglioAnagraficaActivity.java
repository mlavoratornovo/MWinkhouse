package org.winkhouse.mwinkhouse.activity.anagrafiche;

import org.winkhouse.mwinkhouse.activity.adapters.LateralMenuAdapter;
import org.winkhouse.mwinkhouse.activity.colloqui.ListaColloquiActivity;
import org.winkhouse.mwinkhouse.fragment.anagrafiche.DettaglioAnagraficaFragment;
import org.winkhouse.mwinkhouse.models.columns.AnagraficheColumnNames;
import org.winkhouse.mwinkhouse.util.ActivityMessages;

import org.winkhouse.mwinkhouse.R;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class DettaglioAnagraficaActivity extends AppCompatActivity {
		
	private String[] menu_laterale_content = null; 
    private DrawerLayout mDrawerLayout = null;
    private ListView mDrawerList = null;
    private Integer codAnagrafica = null;
	
	public DettaglioAnagraficaActivity() {

	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
		onStart();
	}

	@Override
	protected void onStart() {
		super.onStart();
		codAnagrafica = getIntent().getIntExtra(AnagraficheColumnNames.CODANAGRAFICA, 0);
		Fragment dettaglioAnagrafica = new DettaglioAnagraficaFragment();
		Bundle args = new Bundle();
		args.putInt(AnagraficheColumnNames.CODANAGRAFICA, codAnagrafica);
		dettaglioAnagrafica.setArguments(args);

		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
					   .replace(R.id.content_frame, dettaglioAnagrafica)
					   .commit();
		
	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dettaglio_anagrafica_activity);
		
		menu_laterale_content = getResources().getStringArray(R.array.menu_laterale_dettaglio_anagrafica);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerList = findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new LateralMenuAdapter(this, menu_laterale_content));
        
        mDrawerList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            	
                if (position == 0){
                	
                	if ((codAnagrafica == null) || (codAnagrafica == 0)){
                		Toast.makeText(DettaglioAnagraficaActivity.this, "Eseguire il salvataggio dell'anagrafica", Toast.LENGTH_SHORT).show();
                	}else{

                		Intent colloqui = new Intent(DettaglioAnagraficaActivity.this, ListaColloquiActivity.class);
                		colloqui.putExtra(ActivityMessages.CODANAGRAFICA_ACTION, codAnagrafica);                		
                		startActivity(colloqui);
                	}
    				
                }
                if (position == 1){
                	
    				
                }
                if (position == 2){
                	
    				
                }
                if (position == 3){
                	
    				
                }
                if (position == 4){
                	
    				
                }
                
                mDrawerLayout.closeDrawer(mDrawerList);
                
             }
        });
		
	}
	
	@Override
	public void onBackPressed() {
		finish();
	}

}
