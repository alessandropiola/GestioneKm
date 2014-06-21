package it.alessandropiola.gestionekm;

import it.alessandropiola.gestionekm.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceClickListener;
import android.widget.Toast;
	 
	public class Impostazioni extends PreferenceActivity {
	        @Override
	        protected void onCreate(Bundle savedInstanceState) {
	                super.onCreate(savedInstanceState);
	                addPreferencesFromResource(R.xml.impostazioni);
	                // Get the custom preference
	               // Preference customPref = (Preference) findPreference("customPref");

	        }
	        protected void onDestroy() {
	        	super.onDestroy();
		        SharedPreferences prefs1 = getSharedPreferences("it.alessandropiola.gestionekm_preferences", 0);
				boolean ifActive = prefs1.getBoolean("notificaalarm",false);
				//setto la notifica
		        if (ifActive) {
		        	alarmdel();
		        	alarmset();
					
				} else {
					alarmdel();
				}
	        	
	        }

	    	public void alarmset() {
	    		Alarm alarm = new Alarm();
	    		alarm.SetAlarm(this);
	    		
	    	}	
	    	public void alarmdel() {
	    		Alarm alarm = new Alarm();
	    		alarm.CancelAlarm(this);
	    		
	    	}
}