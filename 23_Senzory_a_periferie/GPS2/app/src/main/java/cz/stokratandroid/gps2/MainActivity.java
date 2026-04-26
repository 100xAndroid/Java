package cz.stokratandroid.gps2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {

    protected LocationManager locationManagerGps = null;
    protected LocationManager locationManagerNet;
    private TextView txtGpsSirka, txtGpsDelka, txtGpsVyska, txtGpsPresnost;
    private TextView txtSitSirka, txtSitDelka, txtSitVyska, txtSitPresnost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtGpsSirka = (TextView)findViewById(R.id.txtGpsSirka);
        txtGpsDelka = (TextView)findViewById(R.id.txtGpsDelka);
        txtGpsVyska = (TextView)findViewById(R.id.txtGpsVyska);
        txtGpsPresnost = (TextView)findViewById(R.id.txtGpsPresnost);
        locationManagerGps = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        txtSitSirka = (TextView)findViewById(R.id.txtSitSirka);
        txtSitDelka = (TextView)findViewById(R.id.txtSitDelka);
        txtSitVyska = (TextView)findViewById(R.id.txtSitVyska);
        txtSitPresnost = (TextView)findViewById(R.id.txtSitPresnost);
        locationManagerNet = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

/*
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(this, "Nejsou prava", Toast.LENGTH_SHORT);
        }
        // Parameters :
        //   First(provider)    :  the name of the provider with which to register
        //   Second(minTime)    :  the minimum time interval for notifications,
        //                         in milliseconds. This field is only used as a hint
        //                         to conserve power, and actual time between location
        //                         updates may be greater or lesser than this value.
        //   Third(minDistance) :  the minimum distance interval for notifications, in meters
        //   Fourth(listener)   :  a {#link LocationListener} whose onLocationChanged(Location)
        //                         method will be called for each location update
        locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 2000,1, this);
        */
/*
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            String msg = "New Latitude: " + location.getLatitude() + "New Longitude: " + location.getLongitude();
            Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
        }
*/
        // Getting GPS status
        //  isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // Getting network status
        //  isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

    }

    @Override
    protected void onResume() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(getBaseContext(), "Aplikace nemá povolené zjišťování polohy.", Toast.LENGTH_SHORT).show();
        }
        else {
            // Parametery :
            //   provider    -  zpusob ziskavani informaci o poloze (GPS nebo sit)
            //   minTime     -  interval ziskavani informaci o poloze (v milisekundach). Cim je cas
            //                  kratsi, tim je poloha presnejsi ale zaroven vyssi spotreba baterie
            //   minDistance -  nejmensi zmena vzdalenosti, aby byla vyvolana udalost onLocationChanged
            //   listener    -  urcuje, ktera udalost onLocationChanged bude pri zmene polohy volana
            locationManagerGps.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 1, this);

            locationManagerNet.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 1, this);
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        locationManagerGps.removeUpdates(this);
        locationManagerNet.removeUpdates(this);
        super.onPause();
    }

    @Override
    public void onLocationChanged(Location location) {
        zobrazitSouradnice(location);
    }

    @Override
    public void onProviderDisabled(String provider) {
        // zobrazit uzivateli Nastaveni polohy
        // Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        // startActivity(intent);

        if (provider.equals(LocationManager.GPS_PROVIDER)) {
            TextView txtGpsSirka = (TextView)findViewById(R.id.txtGps);
            txtGpsSirka.setText("GPS (vypnuto)");
        }
        else if (provider.equals(LocationManager.NETWORK_PROVIDER)) {
            TextView txtGpsSirka = (TextView)findViewById(R.id.txtSit);
            txtGpsSirka.setText("Mobilní síť (vypnuto)");
        }
    }

    @Override
    public void onProviderEnabled(String provider) {
        if (provider.equals(LocationManager.GPS_PROVIDER)) {
            TextView txtGpsSirka = (TextView)findViewById(R.id.txtGps);
            txtGpsSirka.setText("GPS");
        }
        else if (provider.equals(LocationManager.NETWORK_PROVIDER)) {
            TextView txtGpsSirka = (TextView)findViewById(R.id.txtSit);
            txtGpsSirka.setText("Mobilní síť");
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    // zobrazi ziskane souradnice
    private void zobrazitSouradnice (Location loc) {
        if (loc.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            txtGpsSirka.setText("Zeměpisná šířka: " + loc.getLatitude());
            txtGpsDelka.setText("Zeměpisná délka: " + loc.getLongitude());
            txtGpsVyska.setText("Nadmořská výška: " + loc.getAltitude());
            txtGpsPresnost.setText("Přesnost: " + loc.getAccuracy());
        }
        else {
            txtSitSirka.setText("Zeměpisná šířka: " + loc.getLatitude());
            txtSitDelka.setText("Zeměpisná délka: " + loc.getLongitude());
            txtSitVyska.setText("Nadmořská výška: " + loc.getAltitude());
            txtSitPresnost.setText("Přesnost: " + loc.getAccuracy());
        }

        try {
            Geocoder myLocation = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> adresy = myLocation.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);

            if (adresy.size() > 0) {
                TextView txtMisto = (TextView) findViewById(R.id.txtMisto);
                Address adresa = adresy.get(0);
                String misto = adresa.getThoroughfare() + " " + adresa.getFeatureName()
                        + ", " + adresa.getLocality() + ", " + adresa.getCountryCode();
                txtMisto.setText(misto);
            }
        }
        catch (IOException e){
        }
    }

}