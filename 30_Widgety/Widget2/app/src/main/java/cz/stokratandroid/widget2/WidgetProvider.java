package cz.stokratandroid.widget2;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.format.DateFormat;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Date;

import cz.stokratandroid.widget2.R;

public class WidgetProvider extends AppWidgetProvider {

    private static AppWidgetManager widgetManager;
    private static int[] widgetIds;
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int count = appWidgetIds.length;

        try {
            widgetManager = appWidgetManager;
            widgetIds = Arrays.copyOf(appWidgetIds, appWidgetIds.length);

            // cyklus - pro kazdou existujici instanci widgetu,
            // pripojenou k tomuto provideru
            for (int i = 0; i < count; i++) {
                int widgetId = widgetIds[i];

                // vytvorit instanci layoutu widgetu
                RemoteViews views = null;
                try {
                    views = new RemoteViews(context.getPackageName(), R.layout.widget);

                    // vytvorit intent na tuto tridu a prida parametry
                    Intent clickIntent = new Intent(context, WidgetProvider.class);
                    clickIntent.putExtra(widgetManager.EXTRA_APPWIDGET_ID, widgetId);
                    clickIntent.setAction("btnNastaveniKlik");

                    // vytvorit pending intent, ktery bude vyvolany po kliknuti na
                    // tlacitko Nastaveni
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, widgetId, clickIntent, PendingIntent.FLAG_IMMUTABLE);
                    views.setOnClickPendingIntent(R.id.btnNastaveni, pendingIntent);

                    // jako predchozi, ale pro tlacitko Obnovit
                    Intent clickIntent2 = new Intent(context, WidgetProvider.class);
                    clickIntent2.putExtra(widgetManager.EXTRA_APPWIDGET_ID, widgetId);
                    clickIntent2.setAction("btnObnovitKlik");
                    PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context, widgetId, clickIntent2, PendingIntent.FLAG_IMMUTABLE);
                    views.setOnClickPendingIntent(R.id.btnObnovit, pendingIntent2);

                    // zmeny odeslat widget manageru widgetManager
                    widgetManager.updateAppWidget(widgetId, views);
                } catch (Exception ex) {
                    Toast.makeText(context, "Chyba: " + ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
            nacistDataZeServeru(context);

            super.onUpdate(context, appWidgetManager, appWidgetIds);
        }
        catch (Exception ex) {
            Toast.makeText(context, "Fatal error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        // podle nazvu udalosti rozlisit tlacitko
        String nazevUdalosti = intent.getAction();
        if (nazevUdalosti.equals("btnNastaveniKlik")) {
            otevritNastaveni(context);
        }
        else if (nazevUdalosti.equals("btnObnovitKlik")) {
            Toast.makeText(context, "Počasí bude aktualizováno.", Toast.LENGTH_SHORT).show();
            nacistDataZeServeru(context);
            Toast.makeText(context, "Počasí bylo aktualizováno.", Toast.LENGTH_SHORT).show();
        }
    }

    // otevre MainActivity, ktera slouzi pro nastaveni widgetu
    private void otevritNastaveni(Context context) {
        // intent na tridu MainActivity a jeho spusteni
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ComponentName cn = new ComponentName(context, MainActivity.class);
        intent.setComponent(cn);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    // nacte data ze serveru a zobrazi je v textovem poli
    private void nacistDataZeServeru(Context context) {

        // spustit nacitani dat o pocasi
        if (datovePripojeni(context) == true) {
            GetData getData = null;
            getData = new GetData(context, nastaveniMista(context));
            getData.startAsync();
         }
    }

    // Overi dostupnost datoveho pripojeni
    private boolean datovePripojeni(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            zobrazitText(context, "Datové připojení nedostupné.");
            return false;
        }

        // API verze 21 a vyssi
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
            if (capabilities != null &&
                    (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI_AWARE))
            )
                return true;
        }
        // API verze nizsi nez 21
        else {
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return (netInfo != null && netInfo.isConnected());
        }
        zobrazitText(context, "Datové připojení nedostupné.");
        return false;
    }

    // zobrazi v textovem poli informaci
    public static void zobrazitText (Context context, String text){
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);

        remoteViews.setTextViewText(R.id.txtPocasi, text);
        String aktualniCas=DateFormat.format("d.M. HH:mm", new Date()).toString();
        remoteViews.setTextViewText(R.id.txtDatum, nastaveniMista(context) + ", " + aktualniCas);

        ComponentName name = new ComponentName(context, WidgetProvider.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(name, remoteViews);


/*
        final int count = widgetIds.length;
        for (int i = 0; i < count; i++) {
            int widgetId = widgetIds[i];

            widgetManager.updateAppWidget(widgetId, remoteViews);
        }*/

    }

    // nacte ze sdilenych nastaveni nazev mesta
    private static String nastaveniMista (Context context) {
        // otevrit soubor pro cteni preferenci
        SharedPreferences sharedPreferences = context.getSharedPreferences("Nastaveni", context.MODE_PRIVATE);
        // nacist ulozenou hodnotu
        return sharedPreferences.getString("misto", "Karlovy Vary");
    }
}