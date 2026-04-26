package cz.stokratandroid.sluzba3;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class Sluzba extends Service {

    private boolean sluzbaBezi = false;
    private Thread vlakno = null;
    private DataReceiver dataReceiver;
    private int prijataZprava = -1;

    @Override
    public void onCreate() {
        Log.i("Sluzba test", "Service onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // test jestli je sluzba uz spustena, pokud ano, znovu nespoustet
        if (sluzbaBezi) {
            Log.i("Sluzba test", "Sluzba je uz spustena.");
            return Service.START_STICKY;
        }
        else {
            sluzbaBezi = true;
            Log.i("Sluzba test", "Sluzba - onStartCommand");
        }

        // zaregistrovat receiver kvuli prichozim zpravam
        if (dataReceiver == null)
            dataReceiver = new DataReceiver();
        IntentFilter intentFilter = new IntentFilter("zpravaZAktivity");
        registerReceiver(dataReceiver, intentFilter, RECEIVER_NOT_EXPORTED);

        // vytvorit nove vlakno a sluzbu spustit
        vlakno=new Thread(new Runnable() {
            @Override
            public void run() {
                // na toto misto umistime kod, ktery ma sluzba zpracovavat

                MediaPlayer prehravac = MediaPlayer.create(getApplicationContext(), R.raw.pipnuti);
                // v sekundovych intervalech vypisovat do logu zaznamy
                try {
                    for (int i = 0; i < 100; i++) {
                        // vlakno na sekundu uspat
                        Thread.sleep(1000);
                        Log.i("Sluzba test", "Sluzba bezi, krok " + i);

                        if (prijataZprava > -1) {
                            i = prijataZprava;
                            prijataZprava=-1;
                        }

                        // odeslani zpravy ze sluzby, ocekava se, ze ji prijme aktivita
                        Intent broadcastIntent = new Intent();
                        broadcastIntent.setPackage("cz.stokratandroid.sluzba3");
                        broadcastIntent.setAction("zpravaZeSluzby");
                        broadcastIntent.putExtra("data", i);
                        sendBroadcast(broadcastIntent);

                        // spustit prehravani zvuku
                        if (prehravac.isPlaying())
                            prehravac.stop();
                        prehravac.start();
                    }
                }
                catch (InterruptedException e) {
                    Log.i("Sluzba test", "Vyjimka InterruptedException");
                    stopSelf();
                }
                catch (Exception e) {
                    Log.i("Sluzba test", "Vyjimka: " + e.getMessage());
                }
                // sluzba ukoncila svoji cinnost, bude zastavena
                stopSelf();
            }
        });
        vlakno.start();
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        Log.i("Sluzba test", "Sluzba - onBind");
        return null;
    }

    @Override
    public void onDestroy() {
        // odregistrujeme receiver
        if (dataReceiver != null)
            unregisterReceiver(dataReceiver);

        sluzbaBezi = false;
        if (vlakno != null)
            vlakno.interrupt();
        Log.i("Sluzba test", "Sluzba - onDestroy");
    }

    // trida prijimajici data odeslana sluzbou
    class DataReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("zpravaZAktivity")) {
                prijataZprava = intent.getIntExtra("data", -1);
            }
        }
    }

}