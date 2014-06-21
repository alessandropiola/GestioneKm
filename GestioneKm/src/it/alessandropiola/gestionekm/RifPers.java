
	// il tragitto prevede di salvare tramire impostazioni base
	// oppure tramire trcupero via da GPs

	// impostazini base sono Casa-Lavoro-Casa Oppure Casa-Altro-Casa

	package it.alessandropiola.gestionekm;

	import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

	import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

	import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

	public class RifPers  extends FragmentActivity 
	{
	    private static final String CambioData = "RifPers";



		/** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState)
	    {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.cambiodata);
	        Log.w(CambioData, "CambioData");
	        
	    }
	    

	    
		
	    public static class DatePickerFragment extends DialogFragment
	    			implements DatePickerDialog.OnDateSetListener {
	    	@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);
			
			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
			}
			
			public void onDateSet(DatePicker view, int year, int month, int day) {
			// Do something with the date chosen by the user
			}
	    }
	    
	    public void savechangedate(View button) {
			Log.w(CambioData, "CambioData");
			
			DialogFragment newFragment = new DatePickerFragment();
		    newFragment.show(getSupportFragmentManager(), "datePicker");
		    
		DatePicker Dataselezionata = (DatePicker)findViewById(R.id.datePicker01);
			//Sottraggo alla data x giorni per partire dal giorno sesso
	        String strGiorno = Integer.toString(Dataselezionata.getDayOfMonth());
	        if ( strGiorno.length() < 2) strGiorno = "0" + strGiorno;
			
	        String strMese = Integer.toString(1+Dataselezionata.getMonth());
			if ( strMese.length() < 2) strMese = "0" + strMese;
			
            String strData = Integer.toString(Dataselezionata.getYear())+strMese+strGiorno;
            Log.w(CambioData, strData);
			SharedPreferences prefs2 = getSharedPreferences("it.alessandropiola.gestionekm_preferences", 0);
	        SharedPreferences.Editor editor = prefs2.edit();
	        editor.putString("appoggiodata",strData);
	        if (editor.commit()) {
	            Toast toast = Toast.makeText(this, "Dati salvati", Toast.LENGTH_SHORT);
	            	// Visualizziamo il toast
	            toast.show();	


	            } else {
	                Toast toast = Toast.makeText(this, "Data odierna NON SALVATA. ERRORE\nCHIUDERE E RIAPRIRE IL PROGRAMMA", Toast.LENGTH_LONG);
	            	// Visualizziamo il toast
	            toast.show();          	
	            }
	        SharedPreferences prefs1 = getSharedPreferences("it.alessandropiola.gestionekm_preferences", 0);
			String TargaCar = prefs1.getString("TargaAuto", "http://www.google.com");
			String data = prefs1.getString("appoggiodata", "20000101");
			//sendtohttp("http://alessandropiola.brinkster.net/gestionens/insert_day.asp?Targa="+TargaCar+"&DateT="+data+"&Dow="+dateString1+"","valore1","valore2","valore3","valore4");
		    
	        GregorianCalendar dataoggi = new GregorianCalendar();
	        Log.w(CambioData, data.substring(0,4 ));
	        Log.w(CambioData, data.substring(4,6 ));
	        //Log.w("TARGA", data.substring(0,4 ));
	        

	        
	        GregorianCalendar cal= new GregorianCalendar(Integer.valueOf(data.substring(0,4 )), Integer.valueOf(data.substring(4,6).toString())-1, Integer.valueOf(data.substring(6,8).toString()), 00, 00, 00); // 14/05/2010 14:42:00
	        SimpleDateFormat sdf=new SimpleDateFormat("E");
	        String dateString1 = sdf.format(cal.getTime());
	        
			MyDatabase db=new MyDatabase(this);
		    db.open();
	        Cursor c_Ad = db.fetchDay(TargaCar,data);
	        if (c_Ad.getCount()==0) {
	        	db.open();
	    	    db.createday(TargaCar, data,dateString1);
	    	    db.close();        	
	        }
	        
		    db.close();
			
		
			}
		
			}

