package cz.stokratandroid.gps1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener {

    protected LocationManager locationManager;
    private TextView txtSirka, txtDelka, txtVyska, txtPresnost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtSirka = (TextView)findViewById(R.id.txtSirka);
        txtDelka = (TextView)findViewById(R.id.txtDelka);
        txtVyska = (TextView)findViewById(R.id.txtVyska);
        txtPresnost = (TextView)findViewById(R.id.txtPresnost);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    protected void onResume() {

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(this, "Aplikace nemá povolené zjišťování polohy.", Toast.LENGTH_SHORT).show();
        }
        else {
            // Parametery :
            //   provider    -  zpusob ziskavani informaci o poloze (GPS nebo sit)
            //   minTime     -  interval ziskavani informaci o poloze (v milisekundach). Cim je cas
            //                  kratsi, tim je poloha presnejsi ale zaroven vyssi spotreba baterie
            //   minDistance -  nejmensi zmena vzdalenosti, aby byla vyvolana udalost onLocationChanged
            //   listener    -  urcuje, ktera udalost onLocationChanged bude pri zmene polohy volana
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 1, this);
        }
        // zavolat původní onResume událost
        super.onResume();
    }

    @Override
    protected void onPause() {
        // konec zjišťování polohy
        locationManager.removeUpdates(this);
        // zavolat původní onPause událost
        super.onPause();
    }

    @Override
    public void onLocationChanged(Location location) {
        zobrazitSouradniceGps(location);
    }

    @Override
    public void onProviderDisabled(String provider) {
        // zobrazit uzivateli Nastaveni polohy
        // Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        // startActivity(intent);
        Toast.makeText(getBaseContext(), "GPS je vypnuta!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(getBaseContext(), "GPS je zapnuta.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    // zobrazi ziskane souradnice
    private void zobrazitSouradniceGps (Location loc) {
        txtSirka.setText("Zeměpisná šířka: " + loc.getLatitude());
        txtDelka.setText("Zeměpisná délka: " + loc.getLongitude());
        txtVyska.setText("Nadmořská výška: " + loc.getAltitude());
        txtPresnost.setText("Přesnost: " + loc.getAccuracy());
    }
}