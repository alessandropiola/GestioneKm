package it.alessandropiola.gestionekm;


import it.alessandropiola.gestionekm.MyDatabase.ProductsMetaData;
import it.alessandropiola.gestionekm.MyDatabase;
import it.alessandropiola.gestionekm.Alarm;

import com.google.ads.*;

import it.alessandropiola.gestionekm.R;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.io.File; 
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date; 
import java.util.GregorianCalendar;

import jxl.*; 
import jxl.write.*; 
import jxl.write.Number;
import jxl.write.biff.RowsExceededException;

public class Main extends FragmentActivity  {
    private static final String gestionekmLog = "GestioneKmLog";
	// Local Bluetooth adapter
	GoogleAnalyticsTracker tracker;
    private ViewFlipper vf;  
    private float oldTouchValue;
    private Integer intValore = 0;
    
    private AdView adView;
    
    //private BluetoothAdapter mBluetoothAdapter = null;
    // Member object for the chat services
    //private BluetoothChatService mChatService = null;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //Admob-----------------------------------
        // Create the adView
        adView = new AdView(this, AdSize.BANNER, "ca-app-pub-1734692872202604/6781777975");

        // Lookup your LinearLayout assuming it's been given
        // the attribute android:id="@+id/mainLayout"
        LinearLayout layoutAdmob = (LinearLayout)findViewById(R.id.mainLayout);

        // Add the adView to it
        layoutAdmob.addView(adView);

        // Initiate a generic request to load it with an ad
        adView.loadAd(new AdRequest());
        //ADMOB-----------------------------------------------

        //info
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout,
                                       (ViewGroup) findViewById(R.id.toast_layout_root));

        ImageView image = (ImageView) layout.findViewById(R.id.image);
        image.setImageResource(R.drawable.swipe);
        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText("Usa lo swipe per cambiare data");

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

        
        //vf = (ViewFlipper) findViewById(R.id.viewFlipper1);


        //AdManager.setTestDevices( new String[] { AdManager.TEST_EMULATOR } );
        // Leggiamo le Preferences customPref
        SharedPreferences prefscustomPref = getSharedPreferences("it.alessandropiola.gestionekm_preferences", Context.MODE_PRIVATE);
		SharedPreferences prefs = getSharedPreferences("gestioneKm", Context.MODE_PRIVATE);
		// Leggiamo l'informazione associata alla proprietÃ  TEXT_DATA
		String lastvalue = prefs.getString("lastValue", "000001");
		String targaAuto = prefscustomPref.getString("TargaAuto", "Nothing");
		String Email = prefscustomPref.getString("Email", "");
		String Nome = prefscustomPref.getString("Nome", "");
		Integer CountCommento = prefs.getInt("Intcommento", 0);
		boolean NoDonate = prefscustomPref.getBoolean("Donate", true);
		
		
		
		
		Log.w(gestionekmLog,"Inserito Targa:"+targaAuto);
		Log.w(gestionekmLog,"Valore Km Salvato:"+lastvalue);
		//nessuna donazione attivo la pubb
		/*if (NoDonate) {
			LinearLayout layout2 = (LinearLayout)findViewById(R.id.ads);
		    AdView adView2 = new AdView(this, AdSize.BANNER, "a14d1dd92e5be3a");
		    layout2.addView(adView2);
		    AdRequest request2 = new AdRequest();
		    if (android.os.Build.MODEL.equals("google_sdk")) {
		    request2.setTesting(true);
		    }
		    adView2.loadAd(request2);
		    
		    /*
		    Button b1 = (Button) findViewById(R.id.beer);
		    LayoutParams lp = (LayoutParams) b1.getLayoutParams();
            int intNodonate = 50;
			lp.height=intNodonate ;
 		    b1.setLayoutParams(lp);
 		    
		    Button b2 = (Button) findViewById(R.id.altro);
		    LayoutParams lp2 = (LayoutParams) b2.getLayoutParams();
            lp2.height=intNodonate;
 		    b2.setLayoutParams(lp2);
 		    
		    Button b3 = (Button) findViewById(R.id.fineKm);
		    LayoutParams lp3 = (LayoutParams) b3.getLayoutParams();
            lp3.height=intNodonate;
 		    b3.setLayoutParams(lp3); 
 		    
		    Button b4 = (Button) findViewById(R.id.startKm);
		    LayoutParams lp4 = (LayoutParams) b4.getLayoutParams();
            lp4.height=intNodonate;
 		    b4.setLayoutParams(lp4); 
 		    
 		    
		    Button b5 = (Button) findViewById(R.id.tragitto);
		    LayoutParams lp5 = (LayoutParams) b5.getLayoutParams();
            lp5.height=intNodonate;
 		    b5.setLayoutParams(lp5); 
 		    
		    Button b6 = (Button) findViewById(R.id.lettura);
		    LayoutParams lp6 = (LayoutParams) b6.getLayoutParams();
            lp6.height=intNodonate;
 		    b6.setLayoutParams(lp6); 
 		    
		    Button b7 = (Button) findViewById(R.id.fineKm);
		    LayoutParams lp7 = (LayoutParams) b7.getLayoutParams();
            lp7.height=intNodonate;
 		    b7.setLayoutParams(lp7); */
		
		
		//}
		    
		
		
		
		
		
		Log.w(gestionekmLog, CountCommento.toString());
        

		Log.w(gestionekmLog,targaAuto);
		
	/*	NumberPicker c1 = (NumberPicker) findViewById(R.id.np1);
		c1.setMaxValue(9);
		c1.setMinValue(0);
		NumberPicker c2 = (NumberPicker) findViewById(R.id.np2);
		c2.setMaxValue(9);
		c2.setMinValue(0);
		NumberPicker c3 = (NumberPicker) findViewById(R.id.np3);
		c3.setMaxValue(9);
		c3.setMinValue(0);
		NumberPicker c4 = (NumberPicker) findViewById(R.id.np4);
		c4.setMaxValue(9);
		c4.setMinValue(0);
		NumberPicker c5 = (NumberPicker) findViewById(R.id.np5);
		c5.setMaxValue(9);
		c5.setMinValue(0);
		NumberPicker c6 = (NumberPicker) findViewById(R.id.np6);
		c6.setMaxValue(9);
		c6.setMinValue(0);
		


  
	    c1.setValue(Integer.valueOf(lastvalue.substring(0, 1).toString()));
        c2.setValue(Integer.valueOf(lastvalue.substring(1, 2).toString()));
        c3.setValue(Integer.valueOf(lastvalue.substring(2, 3).toString()));
        c4.setValue(Integer.valueOf(lastvalue.substring(3, 4).toString()));
        c5.setValue(Integer.valueOf(lastvalue.substring(4, 5).toString()));
        c6.setValue(Integer.valueOf(lastvalue.substring(5, 6).toString()));*/
	        
        if (CountCommento > 20) {
	        AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setTitle("Nota Spese");
	        //Seto il messaggio da visualizzare
	        builder.setMessage("Vuoi lasciare un commento?");
	
	        builder.setCancelable(false)
	               .setPositiveButton("Già Fatto!", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	           	        SharedPreferences settings = getSharedPreferences("gestioneKm", 0);
	        	        SharedPreferences.Editor editor = settings.edit();
	        	        editor.putInt("Intcommento",-1);
	        	        editor.commit();
	                   }
	               })
	               .setNegativeButton("OK", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                        dialog.cancel();
	            		    Uri uri = Uri.parse("market://details?id=it.alessandropiola.gestionekm");
	            		    Intent pagina_google = new Intent(Intent.ACTION_VIEW, uri);
	            		    startActivity(pagina_google);
	                   }
	               });
	        AlertDialog alert = builder.create();
	        alert.show();
		} 
        
        //Animation bouncedx = AnimationUtils.loadAnimation(this, R.anim.sx);
        //findViewById(R.id.info).startAnimation(bouncedx);

        
        // leggo il valore della targa e se = noting forzo l'aggiornamento
		if (targaAuto.toString().equals("Nothing")) {
	        AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setTitle("Nota Spese");
	        //Seto il messaggio da visualizzare
	        builder.setMessage("Per procedere correttamente devi inserire i dati richiesti." +
	        		"\n");
	
	        builder.setCancelable(false)
	               .setNegativeButton("Close", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                        dialog.cancel();
	            			Intent i = new Intent(Intent.ACTION_VIEW);
	            			// Creiamo l'Intent
	            			Intent intent = new Intent();
	            			ComponentName component = new ComponentName(Main.this, Impostazioni.class);
	            			intent.setComponent(component);
	            			// La lanciamo
	            			startActivity(intent);
	                   }
	               });
	        AlertDialog alert = builder.create();
	        alert.show();
		}          
        // a questo punto faccio l'insert se non presente della riga all'interno del DB
        GregorianCalendar dataoggi = new GregorianCalendar();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat formatter1 = new SimpleDateFormat("E");
        Date currentTime_1 = new Date();
        String dateString = formatter.format(currentTime_1);
        String dateString1 = formatter1.format(currentTime_1);
        
        SharedPreferences prefs2 = getSharedPreferences("it.alessandropiola.gestionekm_preferences", 0);
        SharedPreferences.Editor editor = prefs2.edit();
        editor.putString("appoggiodata",dateString);
        if (editor.commit()) {
            //Toast toast = Toast.makeText(this, "Giorno Creato", Toast.LENGTH_SHORT);
            	// Visualizziamo il toast
            //toast.show();	
            } else {
                Toast toast2 = Toast.makeText(this, "Data odierna NON SALVATA. ERRORE\nCHIUDERE E RIAPRIRE IL PROGRAMMA", Toast.LENGTH_LONG);
            	// Visualizziamo il toast
            toast2.show();          	
            }
        TextView DataImpostata = (TextView) findViewById(R.id.datadelgiorno); 
        DataImpostata.setTextSize(20);
        String Datagg = (String) dateString.subSequence(6, 8);
        String Datamm = (String) dateString.subSequence(4, 6);
        String Dataaa = (String) dateString.subSequence(0, 4);
        DataImpostata.setText(Datagg+"/"+Datamm+"/"+Dataaa );
        

        SharedPreferences prefs1 = getSharedPreferences("it.alessandropiola.gestionekm_preferences", 0);
		String TargaCar = prefs1.getString("TargaAuto", "http://www.google.com");
		String data = prefs1.getString("appoggiodata", "20000101");
		//sendtohttp("http://alessandropiola.brinkster.net/gestionens/insert_day.asp?Targa="+TargaCar+"&DateT="+data+"&Dow="+dateString1+"","valore1","valore2","valore3","valore4");
	    
		MyDatabase db=new MyDatabase(this);
	    db.open();
        Cursor c_Ad = db.fetchDay(TargaCar,data);
        if (c_Ad.getCount()==0) {
        	db.open();
    	    db.createday(TargaCar, data,dateString1);
    	    db.close();        	
        }
        
	    db.close();
		Log.w("Gestione KM", "fine main");
		
	     
	    
    }
    
    public boolean onTouchEvent(MotionEvent touchevent) {
        switch (touchevent.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            {
                oldTouchValue = touchevent.getX();
                break;
            }
            case MotionEvent.ACTION_UP:
            {
                //if(this.searchOk==false) return false;
                float currentX = touchevent.getX();
                if (oldTouchValue < currentX)
                {
                	intValore--;
                	CambioDataValore(intValore);
                	//vf.setInAnimation(Main.inFromLeftAnimation());
                    //vf.setOutAnimation(Main.outToRightAnimation());
                    //vf.showNext();
                }
                if (oldTouchValue > currentX)
                {
                	intValore++;
                	CambioDataValore(intValore);
                	//vf.setInAnimation(Main.inFromRightAnimation());
                    //vf.setOutAnimation(Main.outToLeftAnimation());
                    //vf.showPrevious();
                }
            break;
            }
        }
        return false;
    }
	private void CambioDataValore(Integer intValore) {
		// TODO Auto-generated method stub
		int daysToAdd = intValore;
		GregorianCalendar dataoggi = new GregorianCalendar();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		dataoggi.add(Calendar.DATE, daysToAdd); // number of days to add
		String dateString1 = formatter.format(dataoggi.getTime());
		

		
        String strData = dateString1;
        Log.w(gestionekmLog, strData);
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
	    

        Log.w(gestionekmLog, "CambioData"+data.substring(0,4 ));
        Log.w(gestionekmLog, "CambioData"+data.substring(4,6 ));
        //Log.w("TARGA", data.substring(0,4 ));
        

        
        GregorianCalendar cal= new GregorianCalendar(Integer.valueOf(data.substring(0,4 )), Integer.valueOf(data.substring(4,6).toString())-1, Integer.valueOf(data.substring(6,8).toString()), 00, 00, 00); // 14/05/2010 14:42:00
        SimpleDateFormat sdf=new SimpleDateFormat("E");
        String dateStringE = sdf.format(cal.getTime());
        
		MyDatabase db=new MyDatabase(this);
	    db.open();
        Cursor c_Ad = db.fetchDay(TargaCar,data);
        if (c_Ad.getCount()==0) {
        	db.open();
    	    db.createday(TargaCar, data,dateStringE);
    	    db.close();        	
        }
        
	    db.close();
	    
        TextView DataImpostata = (TextView) findViewById(R.id.datadelgiorno); 
        DataImpostata.setTextSize(20);
        DataImpostata.setTextColor(android.graphics.Color.RED);
        String Datagg = (String) data.subSequence(6, 8);
        String Datamm = (String) data.subSequence(4, 6);
        String Dataaa = (String) data.subSequence(0, 4);
        DataImpostata.setText(Datagg+"/"+Datamm+"/"+Dataaa );
		
	}

	//for the previous movement
	public static Animation inFromRightAnimation() {

    	Animation inFromRight = new TranslateAnimation(
    	Animation.RELATIVE_TO_PARENT,  +1.0f, Animation.RELATIVE_TO_PARENT,  0.0f,
    	Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
    	);
    	inFromRight.setDuration(350);
    	inFromRight.setInterpolator(new AccelerateInterpolator());
    	return inFromRight;
    	}
    public static Animation outToLeftAnimation() {
    	Animation outtoLeft = new TranslateAnimation(
    	 Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,  -1.0f,
    	 Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
    	);
    	outtoLeft.setDuration(350);
    	outtoLeft.setInterpolator(new AccelerateInterpolator());
    	return outtoLeft;
    	}    
    // for the next movement
    public static Animation inFromLeftAnimation() {
    	Animation inFromLeft = new TranslateAnimation(
    	Animation.RELATIVE_TO_PARENT,  -1.0f, Animation.RELATIVE_TO_PARENT,  0.0f,
    	Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
    	);
    	inFromLeft.setDuration(350);
    	inFromLeft.setInterpolator(new AccelerateInterpolator());
    	return inFromLeft;
    	}
    public static Animation outToRightAnimation() {
    	Animation outtoRight = new TranslateAnimation(
    	 Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,  +1.0f,
    	 Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
    	);
    	outtoRight.setDuration(350);
    	outtoRight.setInterpolator(new AccelerateInterpolator());
    	return outtoRight;
    	}

    protected void onStart(){
    	super.onStart();
    	Log.w(gestionekmLog, "START ON");
        SharedPreferences prefs1 = getSharedPreferences("it.alessandropiola.gestionekm_preferences", 0);
		String strData = prefs1.getString("appoggiodata", "20000101");

		
		
		
		TextView DataImpostata = (TextView) findViewById(R.id.datadelgiorno); 
        DataImpostata.setTextSize(20);
        String Datagg = (String) strData.subSequence(6, 8);
        String Datamm = (String) strData.subSequence(4, 6);
        String Dataaa = (String) strData.subSequence(0, 4);
        DataImpostata.setText(Datagg+"/"+Datamm+"/"+Dataaa );
        
        SharedPreferences prefscustomPref = getSharedPreferences("it.alessandropiola.gestionekm_preferences", Context.MODE_PRIVATE);
		SharedPreferences prefs = getSharedPreferences("gestioneKm", Context.MODE_PRIVATE);
		// Leggiamo l'informazione associata alla proprietÃ  TEXT_DATA
		String lastvalue = prefs.getString("lastValue", "000001");

		NumberPicker c1 = (NumberPicker) findViewById(R.id.np1);
		c1.setMaxValue(9);
		c1.setMinValue(0);
		NumberPicker c2 = (NumberPicker) findViewById(R.id.np2);
		c2.setMaxValue(9);
		c2.setMinValue(0);
		NumberPicker c3 = (NumberPicker) findViewById(R.id.np3);
		c3.setMaxValue(9);
		c3.setMinValue(0);
		NumberPicker c4 = (NumberPicker) findViewById(R.id.np4);
		c4.setMaxValue(9);
		c4.setMinValue(0);
		NumberPicker c5 = (NumberPicker) findViewById(R.id.np5);
		c5.setMaxValue(9);
		c5.setMinValue(0);
		NumberPicker c6 = (NumberPicker) findViewById(R.id.np6);
		c6.setMaxValue(9);
		c6.setMinValue(0);
		


  
	    c1.setValue(Integer.valueOf(lastvalue.substring(0, 1).toString()));
        c2.setValue(Integer.valueOf(lastvalue.substring(1, 2).toString()));
        c3.setValue(Integer.valueOf(lastvalue.substring(2, 3).toString()));
        c4.setValue(Integer.valueOf(lastvalue.substring(3, 4).toString()));
        c5.setValue(Integer.valueOf(lastvalue.substring(4, 5).toString()));
        c6.setValue(Integer.valueOf(lastvalue.substring(5, 6).toString()));
    }

	public void export(View button) throws IOException, RowsExceededException, WriteException {
		
		//-------------------------------------------
		//incremento la variabile commento
		SharedPreferences prefs = getSharedPreferences("gestioneKm", Context.MODE_PRIVATE);
		Integer CountCommento = prefs.getInt("Intcommento", 0);
	    if (CountCommento >= 0){
	    	SharedPreferences settings = getSharedPreferences("gestioneKm", 0);
		    SharedPreferences.Editor editor = settings.edit();
		    editor.putInt("Intcommento",CountCommento+1);
		    editor.commit();		
	    }

	    SharedPreferences prefscustomPref = getSharedPreferences("it.alessandropiola.gestionekm_preferences", Context.MODE_PRIVATE);
		boolean NoDonate = prefscustomPref.getBoolean("Donate", true);
	//	if (NoDonate) {
	//		
	//		Intent myIntent = new Intent(getBaseContext(), adwidgt.class);
	//		myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	//		startActivity(myIntent);

		//}
		
		
        Log.w(gestionekmLog,"lettura web");
        GregorianCalendar dataoggi = new GregorianCalendar();
        SimpleDateFormat formatter = new SimpleDateFormat(
        "yyyyMM");
        Date currentTime_1 = new Date();
        String dateString = formatter.format(currentTime_1);
        Log.w(gestionekmLog,dateString);
		SharedPreferences prefs1 = getSharedPreferences("it.alessandropiola.gestionekm_preferences", 0);
		String TargaCar = prefs1.getString("TargaAuto", "http://www.google.com");
		String EmailSend = prefs1.getString("Email", "");
		String Nome = prefs1.getString("Nome", "");
		String Extras = prefs1.getString("Code", "");
		boolean ifsend = prefs1.getBoolean("ifsend",false);
		
		//Uri uri = prefs1("http://alessandropiola.brinkster.net/gestionens/gKm_read.asp?Targa="+TargaCar+"&Mese="+dateString);
	    //Intent pagina_google = new Intent(Intent.ACTION_VIEW, uri);
	    //startActivity(pagina_google);
		ArrayList<String> testata = new ArrayList<String>();
		testata.add("giorno");
		testata.add("");
		testata.add("Inizio Km");
		testata.add("Tragitto");
		testata.add("Fine Km");
		testata.add("Km del gg");
		testata.add("Rifornimento");
		testata.add("Litri");
		testata.add("");
		testata.add("Pasti");
		testata.add("Albergo");
		testata.add("Trasporto");
		testata.add("Altro");
		testata.add("Totale");
		
		int row = 0;
		
		WritableWorkbook workbook = Workbook.createWorkbook(new File("/sdcard/ExportNotaSpese.xls"));
		WritableSheet sheet = workbook.createSheet("First Sheet", 0);
        WritableCellFormat wcf = new WritableCellFormat();
 		wcf = new WritableCellFormat();
        wcf.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
        //Di colore Rosso
        WritableCellFormat wcfRosso = new WritableCellFormat();
        wcfRosso = new WritableCellFormat();
        wcfRosso.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
        wcfRosso.setBackground(jxl.format.Colour.RED);
		Label label = new Label(3, row, "Dipendente:"+Nome); 
		sheet.addCell(label); 

		
		sheet.setColumnView(3,40);
		Log.w(gestionekmLog,String.valueOf(sheet.getColumnView(1)));
			
		Label label2 = new Label(5, row,"Mese:",wcf); 
		sheet.addCell(label2); 
		Label label3 = new Label(6, row,dateString.substring(4),wcf); 
		sheet.addCell(label3);
		Label label4 = new Label(7, row,"Veicolo:",wcf); 
		sheet.addCell(label4);
		Label label40 = new Label(8, row,TargaCar,wcf); 
		sheet.addCell(label40);
		
		
		row++;
		for(int i=0; i<testata.size(); i++){
			
			Label label5 = new Label(i, row, testata.get(i),wcf); 
			sheet.addCell(label5); 			
			
		}
		MyDatabase db=new MyDatabase(this);
	    db.open();
        Cursor c_Ad = db.fetchMese(TargaCar,dateString);

        

	    row++;
	    int w;
		for(w=row;w<32+row;w++) {
			for(int r=0;r<8;r++) {
	        	Label label1000 = new Label(r, w, "",wcf); 
	    		sheet.addCell(label1000);	
				}
			for(int r=9;r<14;r++) {
				Label label1000 = new Label(r, w, "",wcf); 
		    	sheet.addCell(label1000);	
				}
		}
        
        //row++;
        if (c_Ad.getCount()!=0) {
        	while(c_Ad.moveToNext()) {
        		
            	int Data = c_Ad.getColumnIndex(ProductsMetaData.DATA); //indici delle colonne DataClick
            	int dow = c_Ad.getColumnIndex(ProductsMetaData.DOF); //indici delle colonne DataClick
            	int start = c_Ad.getColumnIndex(ProductsMetaData.STARTKM); //indici delle colonne DataClick
            	int stop = c_Ad.getColumnIndex(ProductsMetaData.STOPKM); //indici delle colonne DataClick
            	int trag = c_Ad.getColumnIndex(ProductsMetaData.TRAGITTO); //indici delle colonne DataClick
            	int rifo = c_Ad.getColumnIndex(ProductsMetaData.RIFORNIMENTO); //indici delle colonne DataClick
            	int lit = c_Ad.getColumnIndex(ProductsMetaData.LITRI); //indici delle colonne DataClick
            	int pas = c_Ad.getColumnIndex(ProductsMetaData.PASTI); //indici delle colonne DataClick
            	int alber = c_Ad.getColumnIndex(ProductsMetaData.ALBERGO); //indici delle colonne DataClick
            	int trasp = c_Ad.getColumnIndex(ProductsMetaData.TRASPORTO); //indici delle colonne DataClick
            	int alt = c_Ad.getColumnIndex(ProductsMetaData.ALTRO); //indici delle colonne DataClick
            	
            	//int intPosizione = c_Ad.getPosition()+row;
            	int intPosizione = Integer.valueOf(c_Ad.getString(Data).toString().substring(6))+row;
            	Label label101 = new Label(0, intPosizione, c_Ad.getString(Data).toString().substring(6),wcf); 
        		sheet.addCell(label101);
        		Label label102 = new Label(1, intPosizione, c_Ad.getString(dow).toString(),wcf); 
        		sheet.addCell(label102);
        		Label label103 = new Label(2, intPosizione, c_Ad.getString(start).toString(),wcf); 
        		sheet.addCell(label103);
        		Label label5 = new Label(4, intPosizione, c_Ad.getString(stop).toString(),wcf); 
        		sheet.addCell(label5);
        	    Label label6 = new Label(3, intPosizione, c_Ad.getString(trag).toString(),wcf); 
        		sheet.addCell(label6);
        		
        		//formula KM
        		Formula f1=new Formula(5,intPosizione,"SUM(E"+(intPosizione+1)+"-C"+(intPosizione+1)+")");       // creating Formula for calculating the sum of the cells using Formula class
        		 
        		sheet.addCell(f1);
        		
        		if (Extras.equals("10111976")) {
        			
	        		if (c_Ad.getString(dow).toString().equals("lun")) {
	        			Formula f_spalma01=new Formula(8,intPosizione,"SUM(C"+(intPosizione)+"-E"+(intPosizione-3)+")");       // creating Formula for calculating the sum of the cells using Formula class
	        			sheet.addCell(f_spalma01);
	        		}
	        	}
        		
        		
        		String Valrifo = c_Ad.getString(rifo);
        		if (Valrifo.startsWith("P")) {
        			Valrifo = Valrifo.replace("P", "");
     			    Number label7 = new Number(6, intPosizione, Integer.valueOf(Valrifo) ,wcfRosso); 
     			    
     			    sheet.addCell(label7);        			
        			
        		}
        		   else { 
        			   Number label7 = new Number(6, intPosizione, c_Ad.getInt(rifo) ,wcf); 
        			   sheet.addCell(label7);
        		   		}
        		
        		
        		Number label8 = new Number(7, intPosizione, c_Ad.getInt(lit),wcf); 
        		sheet.addCell(label8);
        		
        		Number label9 = new Number(9, intPosizione, c_Ad.getInt(pas),wcf); 
        		sheet.addCell(label9);
        		Number label10 = new Number(10, intPosizione, c_Ad.getInt(alber),wcf); 
        		sheet.addCell(label10);
        		Number label11 = new Number(11, intPosizione, c_Ad.getInt(trasp),wcf); 
        		sheet.addCell(label11);
        		Number label12 = new Number(12, intPosizione, c_Ad.getInt(alt),wcf); 
        		sheet.addCell(label12);
        	    

//formula per il totale spese
        		Formula f2=new Formula(13,intPosizione,"SUM(J"+(intPosizione+1)+
        													"+K"+(intPosizione+1)+
        													"+L"+(intPosizione+1)+
        													"+M"+(intPosizione+1)+")");       // creating Formula for calculating the sum of the cells using Formula class
        	    sheet.addCell(f2);
        	 }
       	
        }
        
	    db.close();
	    
	    row = row+31+1;
	    //faccio le somme
	    Label label201 = new Label(3, row, "Totale Km percorsi per lavoro",wcf); 
		sheet.addCell(label201);
		Formula f3=new Formula(5,row,"SUM(F4:F34)");       // creating Formula for calculating the sum of the cells using Formula class
	    sheet.addCell(f3);
		Formula f30=new Formula(6,row,"SUM(G3:G33)");       // creating Formula for calculating the sum of the cells using Formula class
	    sheet.addCell(f30);
		Formula f31=new Formula(7,row,"SUM(H3:H33)");       // creating Formula for calculating the sum of the cells using Formula class
	    sheet.addCell(f31);
		Formula f32=new Formula(13,row,"SUM(N3:N33)");       // creating Formula for calculating the sum of the cells using Formula class
	    sheet.addCell(f32);
	    row++;
		row++;
		Label label202 = new Label(3, row, "Km inizio mese come da tachimetro",wcf); 
		sheet.addCell(label202);
		Formula f4=new Formula(5,row,"C4");       // creating Formula for calculating the sum of the cells using Formula class
	    sheet.addCell(f4);
	    row++;
	    Label label203 = new Label(3, row, "Km fine mese come da tachimetro",wcf); 
		sheet.addCell(label203);
		Label label303 = new Label(9, row, "firma del dipendente",wcf); 
		sheet.addCell(label303);
		Formula f5=new Formula(5,row,"E34");       // creating Formula for calculating the sum of the cells using Formula class
	    sheet.addCell(f5);
	    
	    row++;
		Label label204 = new Label(3, row, "Km percorsi complessivamente nel mese",wcf); 
		sheet.addCell(label204);
		Formula f6=new Formula(5,row,"F38-F37");       // creating Formula for calculating the sum of the cells using Formula class
	    sheet.addCell(f6);
	   	row++;
		Label label205 = new Label(3, row, "Km per lavoro",wcf); 
		sheet.addCell(label205);
		Label label304 = new Label(9, row, "firma capo servizio",wcf); 
		sheet.addCell(label304);
		Formula f7=new Formula(5,row,"F35");       // creating Formula for calculating the sum of the cells using Formula class
	    sheet.addCell(f7);
	    row++;
		Label label206 = new Label(3, row, "Km personali",wcf); 
		sheet.addCell(label206);		
		Formula f8=new Formula(5,row,"F39-F40");       // creating Formula for calculating the sum of the cells using Formula class
	    sheet.addCell(f8);
		//There are a couple of points to note here. Firstly, the cell's location in the sheet is specified as part of the constructor information. Once created, it is not possible to change a cell's location, although the cell's contents may be altered. 

	    //sheet.mergeCells(3, 4, 4, 4);
		//sheet.mergeCells(7, 8, 8, 8);
		
		sheet.mergeCells(9, 37, 13, 38);
		sheet.mergeCells(9, 39, 13, 40);
		
		
		// All sheets and cells added. Now write out the workbook 
		workbook.write(); 
		workbook.close();
		
		
		Toast toast = Toast.makeText(this, "ExportNotaSpese.xls creato correttamente sulla SD", Toast.LENGTH_LONG);
		toast.show();
		if (!android.os.Build.MODEL.equals("google_sdk")) {
			
	        // INIZIO IL TRACKER
		    tracker = GoogleAnalyticsTracker.getInstance();
	
		    // Start the tracker in manual dispatch mode...
		    tracker.startNewSession("UA-22975371-4",this);
		    tracker.trackPageView("Export to XLS");
		    tracker.dispatch();
		    tracker.stopSession();

        }
		
		if (ifsend == true ) {

			
			Intent i = new Intent(Intent.ACTION_SEND);
			i.setType("plain/text");
			i.putExtra(Intent.EXTRA_EMAIL  , new String[]{EmailSend});
			i.putExtra(Intent.EXTRA_SUBJECT, "Nota Spese");
			i.putExtra(Intent.EXTRA_TEXT   , "in allegato il file excel");
			//L'allegato è opzionale
			Uri attachment = Uri.parse("file:///sdcard/ExportNotaSpese.xls");
			i.putExtra(Intent.EXTRA_STREAM, attachment);
			if (!android.os.Build.MODEL.equals("google_sdk")) {
	    		
	            // INIZIO IL TRACKER
	    	    tracker = GoogleAnalyticsTracker.getInstance();
	
	    	    // Start the tracker in manual dispatch mode...
	    	    tracker.startNewSession("UA-22975371-4",this);
	    	    tracker.trackPageView("SEND EMAIL to "+EmailSend);
	    	    tracker.dispatch();
	    	    tracker.stopSession();

            }
			
			try {
			    startActivity(Intent.createChooser(i, "Send mail..."));
			} catch (android.content.ActivityNotFoundException ex) {
			    Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
			}
		}
		
	}
	public void fineKm(View button) {
		//-------------------------------------------
		//incremento la variabile commento
		SharedPreferences prefs = getSharedPreferences("gestioneKm", Context.MODE_PRIVATE);
		Integer CountCommento = prefs.getInt("Intcommento", 0);
	    if (CountCommento >= 0){
	    	SharedPreferences settings = getSharedPreferences("gestioneKm", 0);
		    SharedPreferences.Editor editor = settings.edit();
		    editor.putInt("Intcommento",CountCommento+1);
		    editor.commit();		
	    }
	    //-------------------------------------------	
        SharedPreferences prefs1 = getSharedPreferences("it.alessandropiola.gestionekm_preferences", 0);
		String TargaCar = prefs1.getString("TargaAuto", "http://www.google.com");
		String data = prefs1.getString("appoggiodata", "20000101");
		Log.w(gestionekmLog, TargaCar);
		if (TargaCar.toString().equals("Targa Auto")){
            Toast toast = Toast.makeText(this, "Errore!!!!!\nTarga non inserita correttamente", Toast.LENGTH_LONG);
        	// Visualizziamo il toast
            toast.show();				
		}
		else {
			NumberPicker c1 = (NumberPicker) findViewById(R.id.np1);
	        NumberPicker c2 = (NumberPicker) findViewById(R.id.np2);
	        NumberPicker c3 = (NumberPicker) findViewById(R.id.np3);
	        NumberPicker c4 = (NumberPicker) findViewById(R.id.np4);
	        NumberPicker c5 = (NumberPicker) findViewById(R.id.np5);
	        NumberPicker c6 = (NumberPicker) findViewById(R.id.np6);
	        String strValorenew = String.valueOf(c1.getValue())+String.valueOf(c2.getValue())+
	        		String.valueOf(c3.getValue())+String.valueOf(c4.getValue())+String.valueOf(c5.getValue())+
	        		String.valueOf(c6.getValue());
	        // inserisco i valori in web
	        // e registro il valore per la prossima apertura
	        SharedPreferences settings = getSharedPreferences("gestioneKm", 0);
	        SharedPreferences.Editor editor = settings.edit();
	        editor.putString("lastValue",strValorenew);
	        editor.putString("targa",TargaCar.toString());
	        if (editor.commit()) {
	            Toast toast = Toast.makeText(this, "Fine KM salvato", Toast.LENGTH_LONG);
	            	// Visualizziamo il toast
	            toast.show();	
	            }
	        
	        //sendtohttp("http://alessandropiola.brinkster.net/gestionens/gKm_fineKm.asp?Targa="+TargaCar+"&DateT="+data+"&StopKm="+strValorenew+"","valore1","valore2","valore3","valore4");
			MyDatabase db=new MyDatabase(this);
		    db.open();
		    db.SaveStopKm(TargaCar, data,strValorenew);
		    db.close();
		}	
	}	
	//-------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------

		
	//-------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------
	//startKm
	//salvo nel registro la posizione iniziale dei KM
	public void startKm(View button) {
		//-------------------------------------------
		//incremento la variabile commento
		SharedPreferences prefs = getSharedPreferences("gestioneKm", Context.MODE_PRIVATE);
		Integer CountCommento = prefs.getInt("Intcommento", 0);
	    if (CountCommento >= 0){
	    	SharedPreferences settings = getSharedPreferences("gestioneKm", 0);
		    SharedPreferences.Editor editor = settings.edit();
		    editor.putInt("Intcommento",CountCommento+1);
		    editor.commit();		
	    }
	    //-------------------------------------------	

		//UA-22975371-4
 /*       if (!android.os.Build.MODEL.equals("google_sdk")) {
    		
            // INIZIO IL TRACKER
    	    tracker = GoogleAnalyticsTracker.getInstance();

    	    // Start the tracker in manual dispatch mode...
    	    tracker.startNewSession("UA-22975371-4",this);
    	    tracker.trackPageView("StartKm");
    	    tracker.dispatch();
    	    tracker.stopSession();

            }*/
        SharedPreferences prefs1 = getSharedPreferences("it.alessandropiola.gestionekm_preferences", 0);
		String TargaCar = prefs1.getString("TargaAuto", "http://www.google.com");
		String data = prefs1.getString("appoggiodata", "20000101");
		Log.w(gestionekmLog, TargaCar);
		if (TargaCar.toString().equals("Targa Auto")){
            Toast toast = Toast.makeText(this, "Errore!!!!!\nTarga non inserita correttamente", Toast.LENGTH_LONG);
        	// Visualizziamo il toast
            toast.show();				
		}
		else {
			NumberPicker c1 = (NumberPicker) findViewById(R.id.np1);
	        NumberPicker c2 = (NumberPicker) findViewById(R.id.np2);
	        NumberPicker c3 = (NumberPicker) findViewById(R.id.np3);
	        NumberPicker c4 = (NumberPicker) findViewById(R.id.np4);
	        NumberPicker c5 = (NumberPicker) findViewById(R.id.np5);
	        NumberPicker c6 = (NumberPicker) findViewById(R.id.np6);
	        String strValorenew = String.valueOf(c1.getValue())+String.valueOf(c2.getValue())+
	        		String.valueOf(c3.getValue())+String.valueOf(c4.getValue())+String.valueOf(c5.getValue())+
	        		String.valueOf(c6.getValue());
	    	// inserisco i valori in web
	        // e registro il valore per la prossima apertura
	        SharedPreferences settings = getSharedPreferences("gestioneKm", 0);
	        SharedPreferences.Editor editor = settings.edit();
	        editor.putString("lastValue",strValorenew);
	        editor.putString("targa",TargaCar.toString());
	        if (editor.commit()) {
	            Toast toast = Toast.makeText(this, "Start KM salvato", Toast.LENGTH_LONG);
	            	// Visualizziamo il toast
	            toast.show();	
	            }
	        
	        //ora invio i dati al sito intenet
	        //sendtohttp("http://alessandropiola.brinkster.net/gestionens/gKm_startKm.asp?Targa="+TargaCar+"&DateT="+data+"&StartKm="+strValorenew+"","valore1","valore2","valore3","valore4");
			MyDatabase db=new MyDatabase(this);
		    db.open();
		    db.SaveStarkKm(TargaCar, data,strValorenew);
		    db.close();
		}	
	}	
	//-------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------
	public void birra(View button) {
		NumberPicker c1 = (NumberPicker) findViewById(R.id.np1);
        NumberPicker c2 = (NumberPicker) findViewById(R.id.np2);
        NumberPicker c3 = (NumberPicker) findViewById(R.id.np3);
        NumberPicker c4 = (NumberPicker) findViewById(R.id.np4);
        NumberPicker c5 = (NumberPicker) findViewById(R.id.np5);
        NumberPicker c6 = (NumberPicker) findViewById(R.id.np6);
        //String strValorenew = String.valueOf(c1.getCurrent())+String.valueOf(c2.getCurrent())+String.valueOf(c3.getCurrent())+String.valueOf(c4.getCurrent())+String.valueOf(c5.getCurrent())+String.valueOf(c6.getCurrent());
        //if (strValorenew.equals("101176")){
        //	Toast.makeText(this, "Donation ok", Toast.LENGTH_LONG).show();
       // 	SharedPreferences settings = getSharedPreferences("it.alessandropiola.gestionekm_preferences", 0);
       // 	SharedPreferences.Editor editor = settings.edit();
       // 	editor.putBoolean("Donate",false);
       // 	editor.commit();
        	
        //}
        if (!android.os.Build.MODEL.equals("google_sdk")) {
    		
            // INIZIO IL TRACKER
    	    tracker = GoogleAnalyticsTracker.getInstance();

    	    // Start the tracker in manual dispatch mode...
    	    tracker.startNewSession("UA-22975371-4",this);
    	    tracker.trackPageView("Birra");
    	    tracker.dispatch();
    	    tracker.stopSession();

            }

	Log.w(gestionekmLog,"pro");	
    AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
    //Setto il titolo del dialog
    alertDialog.setTitle("Nota Spese");
    //Setto il messaggio da visualizzare
    alertDialog.setMessage("Nota Spese for Android è un programma FREE.\n " +
    		"<Offrimi una birra> ha lo scopo di apportare un aiuto economico allo sviluppatore" +
    		" e non ha altre finalità.\n" +
    		"");
    //Disabilito il tasto fisico del dispositivo
    alertDialog.setCancelable(false);
    //Aggiungo il tasto di chiusura del dialog
    alertDialog.setPositiveButton("vai", new DialogInterface.OnClickListener() {
        //Alla pressione del pulsante "chiudi" distruggo il dialog
        public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
		    Uri uri = Uri.parse("market://details?id=it.alessandropiola.bronzedonation");
		    Intent pagina_google = new Intent(Intent.ACTION_VIEW, uri);
		    startActivity(pagina_google);
        };


    });
    
    alertDialog.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Accediamo agli eventuali MenuItem di sistema
		super.onCreateOptionsMenu(menu);
		Log.i(gestionekmLog, "onCreateOptionsMenu");
		// Impostiamo l'ordine iniziale
		int order = Menu.FIRST;
		// Creiamo il primo gruppo di MenuItem
		int GROUPA = 0;
		menu.add(GROUPA, 0, order++, "about");
		menu.add(GROUPA, 1, order++, "Impostazioni");
		return true;
	}
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		Log.i(gestionekmLog, "onPrepareOptionsMenu");
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.i(gestionekmLog, "onOptionsItemSelected Called!");
		Log.i(gestionekmLog, Integer.toString(item.getItemId()));
		
		if (item.getItemId() == Menu.FIRST-1) {
			Log.i(gestionekmLog, "ItemA1 Selected");
            //Creo una finestra di dialogo
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            //Setto il titolo del dialog
            alertDialog.setTitle("Nota Spese");
            //Setto il messaggio da visualizzare
            alertDialog.setMessage("Nota Spese permette la registrazione dei dati necessari per la compilazione di una nota spese aziendale.\nAttualmente è configurata secondo un mio standard.\nSe desideri delle modifiche contattami tramite mail a alessandro.gbridge@gmail.com." +
    		"");
            //Disabilito il tasto fisico del dispositivo
            alertDialog.setCancelable(false);
            //Aggiungo il tasto di chiusura del dialog
            alertDialog.setPositiveButton("Chiudi", new DialogInterface.OnClickListener() {
                                //Alla pressione del pulsante "chiudi" distruggo il dialog
                                public void onClick(DialogInterface dialog, int which) {
                                        // TODO Auto-generated method stub
                                        //alertDialog.
                                }
                        });                                             
            //Mostro il dialog
            alertDialog.show();
			
			return true;
			
		} else if (item.getItemId() == Menu.FIRST) {
			Log.i(gestionekmLog, "ItemA2 Selected");
			Intent i = new Intent(Intent.ACTION_VIEW);
			// Creiamo l'Intent
			Intent intent = new Intent();
			ComponentName component = new ComponentName(Main.this, Impostazioni.class);
			intent.setComponent(component);
			// La lanciamo
			startActivity(intent);
      
                                            
          
			
			

			return false;
		
		// Importante richiamare l'implementazione padre nel caso
		// di item non gestiti
		//return super.onOptionsItemSelected(item);
		  };
		return  super.onOptionsItemSelected(item);
	}
	public void tragitto(View button) {
		//-------------------------------------------
		//incremento la variabile commento
		SharedPreferences prefs = getSharedPreferences("gestioneKm", Context.MODE_PRIVATE);
		Integer CountCommento = prefs.getInt("Intcommento", 0);
	    if (CountCommento >= 0){
	    	SharedPreferences settings = getSharedPreferences("gestioneKm", 0);
		    SharedPreferences.Editor editor = settings.edit();
		    editor.putInt("Intcommento",CountCommento+1);
		    editor.commit();		
	    }

		
		Intent i = new Intent(button.getContext(), Tragitto.class);
		button.getContext().startActivity(i);
	}
	public void altro(View button) {
		//-------------------------------------------
		//incremento la variabile commento
		SharedPreferences prefs = getSharedPreferences("gestioneKm", Context.MODE_PRIVATE);
		Integer CountCommento = prefs.getInt("Intcommento", 0);
	    if (CountCommento >= 0){
	    	SharedPreferences settings = getSharedPreferences("gestioneKm", 0);
		    SharedPreferences.Editor editor = settings.edit();
		    editor.putInt("Intcommento",CountCommento+1);
		    editor.commit();		
	    }

		Intent i = new Intent(button.getContext(), Altro.class);
		button.getContext().startActivity(i);
	}
	
	public void cambiadata(View button) {
		//-------------------------------------------
		//incremento la variabile commento
		SharedPreferences prefs = getSharedPreferences("gestioneKm", Context.MODE_PRIVATE);
		Integer CountCommento = prefs.getInt("Intcommento", 0);
	    if (CountCommento >= 0){
	    	SharedPreferences settings = getSharedPreferences("gestioneKm", 0);
		    SharedPreferences.Editor editor = settings.edit();
		    editor.putInt("Intcommento",CountCommento+1);
		    editor.commit();		
	    }



	}	
	

	public void info(View button) {

		if (!android.os.Build.MODEL.equals("google_sdk")) {
			    		
            // INIZIO IL TRACKER
    	    tracker = GoogleAnalyticsTracker.getInstance();
    	    // Start the tracker in manual dispatch mode...
    	    tracker.startNewSession("UA-22975371-4",this);
    	    tracker.trackPageView("Info");
    	    tracker.dispatch();
    	    tracker.stopSession();
            }
		//apro ina finestra per li info generali
		Intent i = new Intent(button.getContext(), Info.class);
		button.getContext().startActivity(i);		
		
	}


}

