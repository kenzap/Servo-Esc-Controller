package com.kenzap.servo;


import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;


public class InstructFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener, OnPreferenceClickListener  {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //PREFERENCES FROM XML
        addPreferencesFromResource(R.xml.instruct);

		//Preference preference = findPreference("pref1");
        //preference.setOnPreferenceClickListener(this);
    
    }
    
    @Override
    public void onResume() {
        super.onResume();
        //getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onPause() {
        //getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }
    
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    	
    	
    }

	@Override
	public boolean onPreferenceClick(Preference preference) {
	
		//Log.i("jj", preference.getKey());
		//if(preference.getKey().toString().equals("pref1")){
			
			//startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(C.server+getResources().getString(R.string.link_bal))));
		//}
		return false;
	}

}