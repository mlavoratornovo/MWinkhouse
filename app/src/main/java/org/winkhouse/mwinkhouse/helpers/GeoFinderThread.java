package org.winkhouse.mwinkhouse.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.winkhouse.mwinkhouse.activity.adapters.HttpGeo2GeoVO;
import org.winkhouse.mwinkhouse.activity.adapters.ListaGeoAdapter;
import org.winkhouse.mwinkhouse.models.GeoVO;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.widget.ListView;


public class GeoFinderThread extends AsyncTask{

	private Context context = null;
	private ArrayList<GeoVO> al_geovo = null;
	private ListView lv_geo = null;
	private ListaGeoAdapter la_geo = null;
	private String citta = null;
	private String webPage = null;
	private ProgressDialog progressDialog = null;
	
	public GeoFinderThread(Context context,ArrayList<GeoVO> al_geovo,ListView lv_geo, ListaGeoAdapter la_geo,String citta) {
		
		this.context = context;
		this.al_geovo = al_geovo;
		this.lv_geo = lv_geo;
		this.la_geo = la_geo;
		this.citta = citta;
		
		progressDialog = new ProgressDialog (context,ProgressDialog.THEME_HOLO_DARK );
		
        progressDialog.setCancelable ( false ) ;
        progressDialog.setMessage ( "Download dati..." ) ;
        progressDialog.setTitle ( "Attendere" ) ;
        progressDialog.setIndeterminate ( true ) ;
	}

	@Override
	protected Object doInBackground(Object... params) {
		HttpURLConnection urlConnection = null;
		try {			
			
			URL url = new URL("http://www.winkhouse.org/find_codcomune.php?typesearch=startwith&name="+this.citta);
			urlConnection = (HttpURLConnection) url.openConnection();
			BufferedReader reader =new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
			
			String data="";

			while ((data = reader.readLine()) != null){
			   webPage += data + "\n";
			}
			
			
			Handler mHandler = new Handler(Looper.getMainLooper());
			mHandler.post(new Runnable() {
				
				@Override
				public void run() {
					al_geovo.clear();
			 		la_geo.clear();
					HttpGeo2GeoVO g2g = new HttpGeo2GeoVO();
					al_geovo = g2g.parse(webPage);					
					lv_geo.invalidateViews();
					la_geo.addAll(al_geovo);
					la_geo.notifyDataSetChanged();	
				}
			});
			
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}catch (IOException e1) {
			e1.printStackTrace();
		}finally {
			urlConnection.disconnect();
	        if(progressDialog != null && progressDialog.isShowing()){
	        	progressDialog.dismiss ( ) ;
	        }
			
		}
		
		return null;
		
	}

	@ Override
    protected void onPreExecute ( ) {
        progressDialog.show ( ) ;
    }

	@Override
	protected void onPostExecute(Object result) {
        if(progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss ( ) ;
        }
	}


}
