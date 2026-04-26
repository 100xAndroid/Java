package cz.stokratandroid.sluzba2;

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
    private DataUpdateReceiver dataUpdateReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prijataZprava=this.findViewById(R.id.txtPrijataZprava);
    }

    public void spustitSluzbu (View view) {
        Intent intent = new Intent(this, Sluzba.class);
        startService(intent);
    }

    public void zastavitSluzbu (View view) {
        Intent intent = new Intent(this, Sluzba.class);
        stopService(intent);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (dataUpdateReceiver == null) dataUpdateReceiver = new DataUpdateReceiver();
        IntentFilter intentFilter = new IntentFilter("test");
        registerReceiver(dataUpdateReceiver, intentFilter, RECEIVER_NOT_EXPORTED);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (dataUpdateReceiver != null)
            unregisterReceiver(dataUpdateReceiver);
    }

    class DataUpdateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("test")) {

                int data = intent.getIntExtra("data", -1);
                prijataZprava.setText("Přijatá zpráva: " + Integer.toString(data));
                // Toast.makeText(context, "Prijata zprava: " + data, Toast.LENGTH_SHORT).show();
            }
        }
    }
}