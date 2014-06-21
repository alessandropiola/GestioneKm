package it.alessandropiola.gestionekm;




import java.io.IOException;
import java.util.List;
import java.util.Locale;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;



public class Tragitto extends Activity
{
	Double geoLat = 0.00;
	Double geoLng = 0.00;
	LocationManager locationManager;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tragitto);
        
		// Otteniamo il riferimento al LocationManager
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// Verifichiamo se il GPS è abilitato altrimenti avvisiamo l'utente
		if(!locationManager.isProviderEnabled("gps")){
			//Toast.makeText(this, "GPS è attualmente disabilitato. E' possibile abilitarlo dal menu impostazioni.", Toast.LENGTH_LONG).show();
		
		}
		// Registriamo il LocationListener al provider GPS
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

        
    }
    
    LocationListener locationListener = new LocationListener() {
		@Override
		public void onLocationChanged(Location location) {
			// Aggiorna il marker della mappa
			// Aggiorna la location
			geoLat = getRound(location.getLatitude(), 5);//location.getLatitude()*1E6;
			geoLng = getRound(location.getLongitude(), 5);
			Log.w("TARGA", "recuperata lat e lon");
           // Toast toast = Toast.makeText(getBaseContext(), "FIX AVVENUTO", Toast.LENGTH_LONG);
        	// Visualizziamo il toast
           // toast.show(); 
            EditText posizatt = (EditText)	findViewById(R.id.posizoneattuale);
            Geocoder gc = new Geocoder(getApplicationContext(), Locale.getDefault());
            StringBuilder sb = new StringBuilder();
            try {
                    List<Address> addresses = gc.getFromLocation(geoLat, geoLng, 1);

                    if (addresses.size() > 0) {
                            Address address = addresses.get(0);
                            sb.append(address.getLocality());
                            
                    }
                    Toast toast2 = Toast.makeText(getBaseContext(), Double.toString(geoLat) + '-' + Double.toString(geoLng) +'-'+ sb.toString() , Toast.LENGTH_LONG);
                	// Visualizziamo il toast
                   // toast.show();
                    

            } catch (IOException e) {}
            
            posizatt.setText("Casa-"+sb.toString()+"-Casa");
		}


		@Override
		public void onProviderDisabled(String provider) {
			//Toast.makeText(AdvancedAppWidgetTestActivity.this,
				//	"onProviderDisabled "+provider, Toast.LENGTH_SHORT).show();

		}

		@Override
		public void onProviderEnabled(String provider) {
	//		Toast.makeText(AdvancedAppWidgetTestActivity.this,
		//			"onProviderEnabled "+provider, Toast.LENGTH_SHORT).show();


		}

		@Override
		public void onStatusChanged(String provider, int status,Bundle extras) {
            if(locationManager != null){
	         locationManager.removeUpdates(locationListener);
	         

            }}
		

        };
        
	    @Override
	    public void onPause() {
	        super.onPause();
	        Log.w("TARGA", "ferma il listener");
	        
            if(locationManager != null){
            	Log.w("TARGA", " se passo");
	         locationManager.removeUpdates(locationListener);

            }
            };
            
		public static double getRound(double x, int digits){
			double powerOfTen = Math.pow(10, digits);
			return ((double)Math.round(x * powerOfTen) / powerOfTen);

		};
    
    public void actualpos(View button) {

        


    }
        //-------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------
	//startKm
	//salvo nel registro la posizione iniziale dei KM
	public void sendtragitto(View button) {
		Log.w("TARGA", "tragitto");
		
	    RadioButton myOption1 = (RadioButton)findViewById(R.id.RB01);
	    RadioButton myOption2 = (RadioButton)findViewById(R.id.RB02);
	    RadioButton myOption3 = (RadioButton)findViewById(R.id.RB03);
	    EditText posizatt = (EditText)	findViewById(R.id.posizoneattuale);
	    
        String strValorenew = "nessuno";
        if (myOption1.isChecked()){
			strValorenew = "Casa-Lavoro-Casa";
		}
		if (myOption2.isChecked()){
			strValorenew = "Casa-altro-Casa";
		}
		if (myOption3.isChecked()){
			
			
			strValorenew = posizatt.getText().toString();
		}
		SharedPreferences prefs1 = getSharedPreferences("it.alessandropiola.gestionekm_preferences", 0);
		String TargaCar = prefs1.getString("TargaAuto", "http://www.google.com");
		String data = prefs1.getString("appoggiodata", "20000101");
		Log.w("TARGA", TargaCar);
		if (TargaCar.toString().equals("Targa Auto")){
            Toast toast = Toast.makeText(this, "Errore!!!!!\nTarga non inserita correttamente", Toast.LENGTH_LONG);
        	// Visualizziamo il toast
            toast.show();				
		}
		else {
	    	// inserisco i valori in web
	        // e registro il valore per la prossima apertura

			//sendtohttp("http://alessandropiola.brinkster.net/gestionens/gKm_tragitto.asp?Targa="+TargaCar+"&DateT="+data+"&Tragitto="+strValorenew+"","valore1","valore2","valore3","valore4");
			MyDatabase db=new MyDatabase(this);
		    db.open();
		    db.tragitto(TargaCar, data,strValorenew);
		    db.close();
		    
			Toast toast = Toast.makeText(this, "Dati salvati Tragitto:"+strValorenew, Toast.LENGTH_LONG);
	        toast.show();	
		}		
	}	
	//-------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------
}
