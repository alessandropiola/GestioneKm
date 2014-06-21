package it.alessandropiola.gestionekm;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;

import it.alessandropiola.gestionekm.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

	 
	public class Info extends  Activity {
		
		private static final int mail_REQUEST = 3500;
	
	        @Override
	        protected void onCreate(Bundle savedInstanceState) {
	                super.onCreate(savedInstanceState);
	                setContentView(R.layout.info);
	                

	                Log.e("info", "init");

	    	}
	        
	    	public void mail(View button) {
	    		

	            String emailTo = "alessandro.gbridge@gmail.com";
	            String subject = "Nota Spese - Idee o Personalizzazioni";
	            String emailText= "";

	            
	            final Intent intent_email = new Intent(android.content.Intent.ACTION_SEND); 
	            intent_email.setType("plain/text");
	            intent_email.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {emailTo});                          
	            intent_email.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
	            intent_email.putExtra(android.content.Intent.EXTRA_TEXT, emailText);
	    		//L'allegato è opzionale
	    		startActivityForResult(intent_email,mail_REQUEST);

	    	}
	    	public void paypal(View button) {
	    		Log.i("menu", "paypal");
	    		Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://plus.google.com/u/0/104339313987143978225/posts"));
	    		//Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=7A7A4AQBVJ3GN"));
	    		startActivity(viewIntent);  
	    		//https://plus.google.com/u/0/104339313987143978225/posts
  		
	    	}
	    	public void appmarket(View button) {
	    		Intent goToMarket = null;
	    		goToMarket = new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id=it.alessandropiola.bronzedonation"));
	    		startActivity(goToMarket);
	    		//details?id=it.alessandropiola.bronzedonation
	    	}
	        @Override
	        protected void onDestroy() {

	                super.onDestroy();
	        }

	        @Override
	        protected void onPause() {

	                super.onPause();
	        }
                      
     }
	        
	

	                
	                
