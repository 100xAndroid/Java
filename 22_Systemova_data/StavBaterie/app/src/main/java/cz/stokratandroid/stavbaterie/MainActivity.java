package cz.stokratandroid.stavbaterie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView txtStavAktualni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        zjistitStavBaterie();

        // najit TextView a ulozit odkaz do promenne
        txtStavAktualni = (TextView) this.findViewById(R.id.txtStavAktualni);
        // zaregistrovat receiver zmeny urovne nabiti baterie
        this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    private void zjistitStavBaterie () {
        // zjistit stav pomoci tridy BatteryManager
        BatteryManager bm = (BatteryManager) getSystemService(BATTERY_SERVICE);
        int stavBaterie = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);

        // zapsat stav do TextView na  formulari
        TextView txtStav = (TextView)findViewById(R.id.txtStavSpusteni);
        txtStav.setText(String.format("Stav při spuštění: %d%%", stavBaterie));
    }

    private final BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // zjistit uroven
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 100);
            // zjistit max rozsah
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
            // spocitat aktualni stav
            int stavBaterie = level * 100 / scale;
            // zapsat vysledek do TextView na fomrulari
            txtStavAktualni.setText(String.valueOf(stavBaterie) + "%");
        }
    };

}