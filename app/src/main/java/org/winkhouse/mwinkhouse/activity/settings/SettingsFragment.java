package org.winkhouse.mwinkhouse.activity.settings;


import org.winkhouse.mwinkhouse.R;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class SettingsFragment extends PreferenceFragment {

	public SettingsFragment() {
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.winkhouse_settings);
//		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
		
	}

	@Override
	public void onPause() {
		
		super.onPause();
	//	getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
		
	}

//	@Override
//	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,String key) {
//		Preference pref = findPreference(key);
//		if (pref instanceof EditTextPreference){
//			
//			try {
//				pref.setSummary(Integer.valueOf(((EditTextPreference)pref).getText()).toString());
//			} catch (NumberFormatException e) {
//				pref.setSummary("100");
//			}
//		}
//		
//	}
	

}
