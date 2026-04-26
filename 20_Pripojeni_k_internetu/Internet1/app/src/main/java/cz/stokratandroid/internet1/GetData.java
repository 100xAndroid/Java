package cz.stokratandroid.internet1;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;

public class GetData {
    String nactenaData = "";

    // operace, ktera bude spustena na pozadi
    protected Void startAsync() {

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    URL url = new URL("https://www.jsonkeeper.com/b/XFIM");
                    HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                    InputStream stream = conn.getInputStream();
                    BufferedReader buff = new BufferedReader(new InputStreamReader(stream));

                    String radka = "";
                    do {
                        nactenaData = nactenaData + radka;
                        radka = buff.readLine();
                    } while (radka != null);
                } catch (Exception e) {
                    // MainActivity.jsonTextView.setText(e.getMessage());
                    nactenaData = "Chyba: " + e.getMessage();
                }

                handler.post(new Runnable() {
                    // udalost volana po skonceni operace
                    @Override
                    public void run() {
                        MainActivity.jsonTextView.setText(nactenaData);
                    }
                });
            }
        });

        return null;
    }
}

