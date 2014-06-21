package it.alessandropiola.gestionekm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

public class RebootAlarm extends BroadcastReceiver {

    private static final String Allarme = "AlarmReboot";

	@Override
    public void onReceive(Context context, Intent intent) {
        // Do your stuff
        //Log.i(Allarme, "SET ON");
        SharedPreferences prefs1 = context.getSharedPreferences("it.alessandropiola.gestionekm_preferences", 0);
        String Orario = prefs1.getString("oranotifica", "08:15");
        boolean ifActive = prefs1.getBoolean("notificaalarm",false);
		if (ifActive) {
			
			Orario = Orario.replace(".", ":");
	        Orario = Orario.replace(" ", "");
	        Orario = Orario + ":00.000";
	        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
	        Intent i = new Intent(context, Alarm.class);
	        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
	        
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	        //sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
	        String inputString = Orario;
	        Date date = null;
	        
	        Calendar c = Calendar.getInstance(); 
	        int giorno = c.get(Calendar.DAY_OF_MONTH);
	        int mese = c.get(Calendar.MONTH)+1;
	        int anno = c.get(Calendar.YEAR);
	        
	        String DataOggi = String.valueOf(anno)+
	   				"-"+String.valueOf(mese)+
	   				"-"+String.valueOf(giorno);
	        Log.w(Allarme,DataOggi);
	        
	        
	        try {
	        	date = sdf.parse(DataOggi+" "+inputString);
	        } catch (ParseException e) {
	   		// TODO Auto-generated catch block
	        	e.printStackTrace();
	        }
	        long StartTime = date.getTime(); 
	        
	        
	        am.setRepeating(AlarmManager.RTC, StartTime, 1000 * 60 * 60 * 24, pi); // Millisec * Second * Minute
	      }
        
    }
}