package cz.stokratandroid.datovepripojeni;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtStav = (TextView) findViewById(R.id.txtStav);


        // String stavPripojeni = zjistitStavPripojeni(this);
        // txtStav.setText(stavPripojeni);

        // zaregistrovat receiver pro zmenu typu pripojeni (pokud zatim zaregistrovany neni)
        if (!broadcastAktivni) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(broadcastReceiver, intentFilter);
            broadcastAktivni = true;
        }
    }

    boolean broadcastAktivni = false;
    static TextView txtStav;

    // zachytavani zmeny typu datoveho pripojeni
    private static final BroadcastReceiver broadcastReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);

            Network pripojeni = cm.getActiveNetwork();
            if (pripojeni == null) {
                txtStav.setText("Bez připojení");
                return;
            }

            NetworkCapabilities capabilities = cm.getNetworkCapabilities(pripojeni);
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
                txtStav.setText("Mobilní síť");
            else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) // || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI_AWARE))
                txtStav.setText("WiFi");
            else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH))
                txtStav.setText("Bluetooth");
            else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
                txtStav.setText("Ethernet");
            else
                txtStav.setText("Jiné");
        }
    };


    private String zjistitStavPripojeni(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);

        Network pripojeni = cm.getActiveNetwork();
        if (pripojeni == null)
            return "Bez připojení";

        NetworkCapabilities capabilities = cm.getNetworkCapabilities(pripojeni);
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
            return "Mobilní síť";
        else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) // || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI_AWARE))
            return "WiFi";
        else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH))
            return "Bluetooth";
        else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
            return "Ethernet";
        else
            return "Jiné";
    }
}