package cz.stokratandroid.widget2;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;

import cz.stokratandroid.widget2.WidgetProvider;

public class GetData {


    String mesto = "Karlovy Vary";
    String data = "";
    Context context;

    // konstruktor tridy
    public GetData (Context cont, String nazevMesta) {
        context=cont;
        mesto = nazevMesta.replace(" ", "%20"); // nahradit pripadne mezery
    }

    // operace, ktera bude spustena na pozadi
    protected Void startAsync() {

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {

                try
                {
                    URL url= new URL("https://api.openweathermap.org/data/2.5/weather?q="
                            + mesto
                            + "&units=metric&lang=cz&appid=df15c19897e64a504b06ce36ae0dce85");
                    HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");   // pro predani dat pouzita metoda GET
                    conn.setReadTimeout(10000);     // timeout v milisekundach
                    conn.setConnectTimeout(15000);  // timeout v milisekundach
                    conn.setDoOutput(false);        // priznak - telo zpravy neodesilat

                    InputStream stream = conn.getInputStream();
                    BufferedReader buff = new BufferedReader(new InputStreamReader(stream));

                    // nacteni dat po jednotlivych radcich
                    String radka = "";
                    while (radka != null)
                    {
                        radka = buff. readLine();
                        data = data + radka;
                    }
                }
                catch(Exception e) {
                    // MainActivity.txtPredpoved.setText(e.getMessage());
                    WidgetProvider.zobrazitText(context, e.getMessage());
                }

                handler.post(new Runnable() {
                    // udalost volana po skonceni operace
                    @Override
                    public void run() {
                        String dekodovanaData = dekodovatJson (data);
                        // MainActivity.txtPredpoved.setText(dekodovanaData);
                        WidgetProvider.zobrazitText(context, dekodovanaData);
                    }
                });
            }
        });

        return null;
    }


    // rozparsovani dat z formatu JSON
    private String dekodovatJson(String data) {
        try {
            // parsovani teploty
            JSONObject jObj = new JSONObject(data);
            jObj = new JSONObject(jObj.getString("main"));
            String teplota = jObj.getString("temp");
            // prevod na cislo, abychom mohli nastavit potrebny pocet desetinnych mist
            double teplotaNum = Double.parseDouble(teplota);

            // parsovani popisu pocasi
            jObj = new JSONObject(data);
            JSONArray jArray = new JSONArray(jObj.getString("weather"));
            jObj = new JSONObject(jArray.getString(0));
            String text = jObj.getString("description");

            return String.format("%s, %s°C", text, teplota);

        } catch (JSONException e) {
            return e.getMessage();
        }
    }
}