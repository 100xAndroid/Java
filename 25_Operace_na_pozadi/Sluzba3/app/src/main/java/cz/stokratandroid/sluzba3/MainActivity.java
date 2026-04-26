package cz.stokratandroid.sluzba3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView prijataZprava;
    private DataReceiver dataReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prijataZprava=this.findViewById(R.id.txtPrijataZprava);
    }

    // kliknuti na tlacitko Spustit sluzbu
    public void spustitSluzbu (View view) {
        Intent intent = new Intent(this, Sluzba.class);
        startService(intent);
    }

    // kliknuti na tlacitko Zastavit sluzbu
    public void zastavitSluzbu (View view) {
        Intent intent = new Intent(this, Sluzba.class);
        stopService(intent);
    }

    // udalost onResume, volana pri spusteni aktivity
    @Override
    public void onResume() {
        super.onResume();

        if (dataReceiver == null)
            dataReceiver = new DataReceiver();
        IntentFilter intentFilter = new IntentFilter("zpravaZeSluzby");
        registerReceiver(dataReceiver, intentFilter, RECEIVER_EXPORTED);
    }

    // udalost onPause, volana pri preruseni aktivity
    @Override
    public void onPause() {
        super.onPause();

        if (dataReceiver != null)
            unregisterReceiver(dataReceiver);
    }

    public void poslatZpravu(View view) {
        // ziskame hodnotu z textoveno (numerickeho) pole
        TextView txtZpravaKOdeslani = findViewById(R.id.txtZpravaKOdeslani);
        int intZpravaKOdeslani = Integer.parseInt(txtZpravaKOdeslani.getText().toString());

        // odesleme intent zpravu
        Intent broadcastIntent = new Intent();
        broadcastIntent.setPackage("cz.stokratandroid.sluzba3");
        broadcastIntent.setAction("zpravaZAktivity");
        broadcastIntent.putExtra("data", intZpravaKOdeslani);
        sendBroadcast(broadcastIntent);
    }

    // trida prijimajici data odeslana sluzbou
    class DataReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("zpravaZeSluzby")) {

                int data = intent.getIntExtra("data", -1);
                prijataZprava.setText("Přijatá zpráva: " + Integer.toString(data));
            }
        }
    }
}