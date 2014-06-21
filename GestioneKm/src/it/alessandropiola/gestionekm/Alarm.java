package it.alessandropiola.gestionekm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;
import android.widget.Toast;


public class Alarm extends BroadcastReceiver {

    
     private static final String Allarme = "Alarm";

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@SuppressLint("NewApi")
	@Override
     public void onReceive(Context context, Intent intent) 
     {   

         Log.w(Allarme, "open activity ");
         // Put here YOUR code.
        // Intent intent2 = new Intent(context, Main.class);
        // intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // context.startActivity(intent2);
         Calendar c = Calendar.getInstance(); 
         int seconds = c.get(Calendar.SECOND);
         int hour = c.get(Calendar.HOUR);
         int minute = c.get(Calendar.MINUTE);

         Builder mBuilder =
        	        new NotificationCompat.Builder(context)
        	        .setSmallIcon(R.drawable.icon)
        	        .setContentTitle("Sono le "+String.valueOf(hour)+":"+String.valueOf(minute)+":"+String.valueOf(seconds))
        	        .setContentText("Vuoi inserire ora i KM?");
        
         
         Intent resultIntent = new Intent(context, Main.class);

        	// The stack builder object will contain an artificial back stack for the
        	// started Activity.
        	// This ensures that navigating backward from the Activity leads out of
        	// your application to the Home screen.
        	TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        	// Adds the back stack for the Intent (but not the Intent itself)
        	stackBuilder.addParentStack(Main.class);
        	// Adds the Intent that starts the Activity to the top of the stack
        	stackBuilder.addNextIntent(resultIntent);
        	PendingIntent resultPendingIntent =
        	        stackBuilder.getPendingIntent(
        	            0,
        	            PendingIntent.FLAG_UPDATE_CURRENT
        	        );
        	mBuilder.setContentIntent(resultPendingIntent);
        	mBuilder.setAutoCancel(true);
        	NotificationManager mNotificationManager =
        	    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        	// mId allows you to update the notification later on.
        	mNotificationManager.notify(1, mBuilder.build());
    
     }

 public void SetAlarm(Context context)
 {
     Log.i(Allarme, "SET ON");
     SharedPreferences prefs1 = context.getSharedPreferences("it.alessandropiola.gestionekm_preferences", 0);
	 String Orario = prefs1.getString("oranotifica", "08:15");
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
     
     
     //date.getTimezoneOffset();
     am.setRepeating(AlarmManager.RTC, StartTime, 1000 * 60 * 60 * 24, pi); // Millisec * Second * Minute
     //am.setRepeating(AlarmManager.RTC_WAKEUP, StartTime, 1000 * 60 * 2 * 1, pi); // Millisec * Second * Minute
     Toast.makeText(context, "Alarm Set at time:"+DataOggi+" "+inputString, Toast.LENGTH_LONG).show();
 }

 public void CancelAlarm(Context context)
 {
     Intent intent = new Intent(context, Alarm.class);
     PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
     AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
     alarmManager.cancel(sender);
 }
}
