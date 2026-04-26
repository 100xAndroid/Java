package cz.stokratandroid.mapy1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        // pripravime si souradnice ktere chceme zobrazit
        LatLng souradnice = new LatLng(49.396, 15.589);
        // umistit znacku na zvolene souradnice
        map.addMarker(new MarkerOptions().position(souradnice).title("Jihlavské náměstí"));
        // nastavit zobrazeni mapy a jeji zvetseni
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(souradnice,15));
    }
}