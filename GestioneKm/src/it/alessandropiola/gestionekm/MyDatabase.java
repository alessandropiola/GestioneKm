package it.alessandropiola.gestionekm;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class MyDatabase {  

    SQLiteDatabase mDb;
    DbHelper mDbHelper;
    Context mContext;
    private static final String DB_NAME="NotaSpese";//nome del db
    private static final int DB_VERSION=2; //numero di versione del nostro db
	private static final String TAG_LOG = "NotaSpeseDB";
    
    public MyDatabase(Context ctx){
            mContext=ctx;
            mDbHelper=new DbHelper(ctx, DB_NAME, null, DB_VERSION);   //quando istanziamo questa classe, istanziamo anche l'helper (vedi sotto)     
    }
    
    public void open(){  //il database su cui agiamo è leggibile/scrivibile
            mDb=mDbHelper.getWritableDatabase();
            
    }
    
    public void close(){ //chiudiamo il database su cui agiamo
            mDb.close();
    }
    
    
    public void createday(String Targa,String Data,String dow){ //chiudiamo il database su cui agiamo
    	//cancello tutto e ripristino le tabelle
    	Log.e(TAG_LOG,"Creo il giorno");
      	mDb.execSQL("insert into NotaSpeseRecord (Targa,Data,DOw,StartKm,StopKm,Tragitto,Rifornimento,litri,pasti,albergo,trasporto,altro) " +
      			"VALUES ('"+Targa+"','"+Data+"','"+dow+"',' ',' ',' ',' ',' ',' ',' ',' ',' ')");
    	}    
    
    public void SaveStarkKm(String Targa,String Data,String Km){ //chiudiamo il database su cui agiamo
    	//cancello tutto e ripristino le tabelle
    	Log.e(TAG_LOG,"UPD Start KM del "+Data);
    	mDb.execSQL("UPDATE NotaSpeseRecord set StartKm='"+Km+"' WHERE Targa='"+Targa+"' and Data='"+Data+"'");
    	}    
    public void SaveStopKm(String Targa,String Data,String Km){ //chiudiamo il database su cui agiamo
    	//cancello tutto e ripristino le tabelle
    	Log.e(TAG_LOG,"UPD Stop KM del "+Data);
    	mDb.execSQL("UPDATE NotaSpeseRecord set StopKm='"+Km+"' WHERE Targa='"+Targa+"' and Data='"+Data+"'");
    	}    
    public void tragitto(String Targa,String Data,String tragitto){ //chiudiamo il database su cui agiamo
    	//cancello tutto e ripristino le tabelle
    	Log.i(TAG_LOG,"UPD tragitto"+tragitto);
    	
    	mDb.execSQL("UPDATE NotaSpeseRecord set Tragitto='"+tragitto+"' WHERE Targa='"+Targa+"' and Data='"+Data+"'");
    	}  
    public void rifornimento(String Targa,String Data,String rifornimento,String litri){ //chiudiamo il database su cui agiamo
    	//cancello tutto e ripristino le tabelle
    	Log.i(TAG_LOG,"UPD rifornimento"+rifornimento);
    	mDb.execSQL("UPDATE NotaSpeseRecord set Rifornimento='"+rifornimento+"',Litri='"+litri+"' WHERE Targa='"+Targa+"' and Data='"+Data+"'");
    	}  
    
    public void pasti(String Targa,String Data,String pasti){ //chiudiamo il database su cui agiamo
    	//cancello tutto e ripristino le tabelle
    	Log.i(TAG_LOG,"UPD pasti");
    	mDb.execSQL("UPDATE NotaSpeseRecord set Pasti='"+pasti+"' WHERE Targa='"+Targa+"' and Data='"+Data+"'");
    	} 
    
    public void albergo(String Targa,String Data,String albergo){ //chiudiamo il database su cui agiamo
    	//cancello tutto e ripristino le tabelle
    	Log.i(TAG_LOG,"UPD albergo");
    	mDb.execSQL("UPDATE NotaSpeseRecord set Albergo='"+albergo+"' WHERE Targa='"+Targa+"' and Data='"+Data+"'");
    	} 
    public void trasporti(String Targa,String Data,String trasporti){ //chiudiamo il database su cui agiamo
    	//cancello tutto e ripristino le tabelle
    	Log.i(TAG_LOG,"UPD trasp");
    	mDb.execSQL("UPDATE NotaSpeseRecord set Trasporto='"+trasporti+"' WHERE Targa='"+Targa+"' and Data='"+Data+"'");
    	} 
    public void altro(String Targa,String Data,String altro){ //chiudiamo il database su cui agiamo
    	//cancello tutto e ripristino le tabelle
    	Log.i(TAG_LOG,"UPD altro");
    	mDb.execSQL("UPDATE NotaSpeseRecord set Altro='"+altro+"' WHERE Targa='"+Targa+"' and Data='"+Data+"'");
    	} 
    
    
    public Cursor fetchDay(String Targa,String data){ //metodo per fare la query di tutti i dati ritiri
    	return mDb.query(ProductsMetaData.Notaspese_record_Table, null,"Targa='"+Targa+"' and trim(DATA)='"+data+"'",null,null,null,null);
    	}
    
    public Cursor fetchMese(String Targa,String data){ //metodo per fare la query di tutti i dati ritiri
    	return mDb.query(ProductsMetaData.Notaspese_record_Table, null,"Targa='"+Targa+"' and DATA like '%"+data+"%'",null,null,null,null);
    	}
   
    static class ProductsMetaData {  // i metadati della tabella, accessibili ovunque
            static final String Notaspese_record_Table = "NotaSpeseRecord";
            static final String IDAd = "_id";
            static final String TARGA = "Targa";
            static final String DATA = "Data";
            static final String DOF = "Dow";
            static final String STARTKM = "StartKm";
            static final String STOPKM = "StopKm";
            static final String TRAGITTO = "Tragitto";
            static final String RIFORNIMENTO = "Rifornimento";
            static final String LITRI = "Litri";
            static final String PASTI = "Pasti";
            static final String ALBERGO = "Albergo";
            static final String TRASPORTO = "Trasporto";
            static final String ALTRO = "Altro";

           // static final String Battery_StatoService = "BatteryStatoService";
           // static final String StatoService = "statoservice";
   
    }
    private static final String NotaSpese_status_result = "CREATE TABLE IF NOT EXISTS "  //codice sql di creazione della tabella
    				+ ProductsMetaData.Notaspese_record_Table + " (" 
    				+ ProductsMetaData.IDAd+ " integer primary key autoincrement, "
    				+ ProductsMetaData.TARGA + " text not null, "
    				+ ProductsMetaData.DATA + " text not null, " 
    				+ ProductsMetaData.DOF + " text, " 
    				+ ProductsMetaData.STARTKM + " text, " 
    				+ ProductsMetaData.STOPKM + " text, " 
    				+ ProductsMetaData.TRAGITTO + " text, " 
    				+ ProductsMetaData.RIFORNIMENTO + " text, " 
    				+ ProductsMetaData.LITRI + " text, " 
    				+ ProductsMetaData.PASTI + " text, " 
    				+ ProductsMetaData.ALBERGO + " text, " 
    				+ ProductsMetaData.TRASPORTO + " text, " 
    				+ ProductsMetaData.ALTRO + " text " 
    				+ "); ";
    
    private class DbHelper extends SQLiteOpenHelper { //classe che ci aiuta nella creazione del db

            public DbHelper(Context context, String data, CursorFactory factory,int version) {
                    super(context, data, factory, version);
            }

            @Override
            public void onCreate(SQLiteDatabase _db) { //solo quando il db viene creato, creiamo la tabella

               	_db.execSQL(NotaSpese_status_result);
                        
            }

            @Override
            public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
                    //qui mettiamo eventuali modifiche al db, se nella nostra nuova versione della app, il db cambia numero di versione
            	Log.w("database", "old:"+Integer.toString(oldVersion));
            	Log.w("database", "new:"+Integer.toString(newVersion));
            	//la versione 1 è uhe
				}
            	
            }

    }
            
