package org.winkhouse.mwinkhouse.util;

import android.content.Context;
import android.net.ConnectivityManager;

public class NetworkUtils {

	public NetworkUtils() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean isOnline(Context c) {
	    ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);

	    return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
	}
	

}
