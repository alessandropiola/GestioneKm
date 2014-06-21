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
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Altro extends Activity
{
    private static final String gestionekmLog = "LogAltro";
	private float oldTouchValue;
    private Integer intValore = 0;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.altro);
        RadioButton rRif = (RadioButton)	findViewById(R.id.RBa01);
        rRif.setOnCheckedChangeListener(mCorkyListener);
        

		GregorianCalendar dataoggi = new GregorianCalendar();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		dataoggi.add(Calendar.DATE, 0); // number of days to add
		String data = formatter.format(dataoggi.getTime());
        TextView DataImpostata = (TextView) findViewById(R.id.datealetro); 
        DataImpostata.setTextSize(20);
        String Datagg = (String) data.subSequence(6, 8);
        String Datamm = (String) data.subSequence(4, 6);
        String Dataaa = (String) data.subSequence(0, 4);
        DataImpostata.setText("Data:"+Datagg+"/"+Datamm+"/"+Dataaa );
        
 


    }
 // Create an anonymous implementation of OnClickListener
    private OnCheckedChangeListener mCorkyListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
			RadioButton rRif = (RadioButton)	findViewById(R.id.RBa01);
			if (rRif.isChecked())  {
				EditText Litri = (EditText) findViewById(R.id.litri);
				Litri.setEnabled(true);
				Litri.setVisibility(1);
				CheckBox rCB = (CheckBox) findViewById(R.id.checkBox1);
				rCB.setVisibility(1);

				
			} 
			
			else {
				
			// TODO Auto-generated method stub
			EditText Litri = (EditText) findViewById(R.id.litri);
			Litri.setEnabled(false);
			Litri.setVisibility(4);
			CheckBox rCB = (CheckBox) findViewById(R.id.checkBox1);
			rCB.setVisibility(4);

			}
		}


    };
//On click	inviaaltro
	public void inviaaltro(View button) {
		//prelevo il tipo di click sull checkbox
		//controllo anche che ci sia stato un click
		//leggo il valore e lo spedisco al server
		RadioButton rRif = (RadioButton)	findViewById(R.id.RBa01);
		RadioButton rPas = (RadioButton) findViewById(R.id.RBa02);
		RadioButton rAlb = (RadioButton) findViewById(R.id.RBa03);
		RadioButton rTra = (RadioButton) findViewById(R.id.RBa04);
		RadioButton rAlt = (RadioButton) findViewById(R.id.RBa05);
		EditText eValore = (EditText) findViewById(R.id.eTextAltro);

		//DatePicker Data = (DatePicker) findViewById(R.id.timepicker_input);
        SharedPreferences prefs1 = getSharedPreferences("it.alessandropiola.gestionekm_preferences", 0);
		String TargaCar = prefs1.getString("TargaAuto", "http://www.google.com");
		String data = prefs1.getString("appoggiodata", "20000101");
		
        String strValorenew = eValore.getText().toString();
		if (rRif.isChecked())  {
			EditText eValore2 = (EditText) findViewById(R.id.litri);
			String strValoreLitri =  eValore2.getText().toString();
			CheckBox rCB = (CheckBox) findViewById(R.id.checkBox1);
			if (rCB.isChecked()) {
				strValorenew = 'P'+strValorenew;
								
			}

			MyDatabase db=new MyDatabase(this);
		    db.open();
		    db.rifornimento(TargaCar, data,strValorenew,strValoreLitri);
		    db.close();
		    Toast toast = Toast.makeText(this, "Dati salvati", Toast.LENGTH_LONG);
	        toast.show();	
		}
		if (rPas.isChecked())  {
			MyDatabase db=new MyDatabase(this);
		    db.open();
		    db.pasti(TargaCar, data,strValorenew);
		    db.close();
		    Toast toast = Toast.makeText(this, "Dati salvati", Toast.LENGTH_LONG);
	        toast.show();	
		}
		if (rAlb.isChecked())  {
			MyDatabase db=new MyDatabase(this);
		    db.open();
		    db.albergo(TargaCar, data,strValorenew);
		    db.close();
		    Toast toast = Toast.makeText(this, "Dati salvati", Toast.LENGTH_LONG);
	        toast.show();	
		}
		if (rTra.isChecked())  {
			MyDatabase db=new MyDatabase(this);
		    db.open();
		    db.trasporti(TargaCar, data,strValorenew);
		    db.close();
		    Toast toast = Toast.makeText(this, "Dati salvati", Toast.LENGTH_LONG);
	        toast.show();	
		}
		if (rAlt.isChecked())  {
			//sendtohttp("http://alessandropiola.brinkster.net/gestionens/gKm_altro.asp?Targa="+TargaCar+"&DateT="+data+"&Altro="+strValorenew+"","valore1","valore2","valore3","valore4");
			MyDatabase db=new MyDatabase(this);
		    db.open();
		    db.altro(TargaCar, data,strValorenew);
		    db.close();
		    Toast toast = Toast.makeText(this, "Dati salvati", Toast.LENGTH_LONG);
	        toast.show();	
		}
		
	}
	
    public boolean onTouchEvent(MotionEvent touchevent) {
    	Log.w(gestionekmLog, "motion");
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
	    
        TextView DataImpostata = (TextView) findViewById(R.id.datealetro); 
        DataImpostata.setTextSize(20);
        DataImpostata.setTextColor(android.graphics.Color.RED);
        String Datagg = (String) data.subSequence(6, 8);
        String Datamm = (String) data.subSequence(4, 6);
        String Dataaa = (String) data.subSequence(0, 4);
        DataImpostata.setText("Data:"+Datagg+"/"+Datamm+"/"+Dataaa );
		
	}
}
