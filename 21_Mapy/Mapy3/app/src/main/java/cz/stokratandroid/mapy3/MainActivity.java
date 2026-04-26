package cz.stokratandroid.mapy3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

// pro udalost kliknuti primo na znacku je nutne navic pouzit implements OnMarkerClickListener
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
        LatLng souradnice = new LatLng(49.939, 14.188);

        // nastavit vlastnosti znacky
        MarkerOptions vlastnostiZnacky = new MarkerOptions().position(souradnice);
        vlastnostiZnacky.icon(BitmapDescriptorFactory.fromResource(R.drawable.znacka));

        Marker znacka1 = map.addMarker(vlastnostiZnacky);
        znacka1.setTag("znacka1");

        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            // definice kompletniho okna (nevyuzijeme)
            @Override
            public View getInfoWindow(Marker znacka) {
                return null;
            }

            // definice obsahu informacniho okna
            @Override
            public View getInfoContents(Marker znacka) {
                // ukazatel na formular marker_info
                View v = getLayoutInflater().inflate(R.layout.marker_info, null);

                // ziskat reference na objekty ve formulari
                TextView txtNadpis = (TextView) v.findViewById(R.id.txtNadpis);
                ImageView imgObrazek = (ImageView) v.findViewById(R.id.imgObrazek);
                TextView txtPopis = (TextView) v.findViewById(R.id.txtPopis);

                if (znacka.getTag()=="znacka1") {
                    txtNadpis.setText("Hrad Karlštejn");
                    imgObrazek.setImageResource(R.drawable.karlstejn);
                    txtPopis.setText("Středověký hrad, založený králem Karlem IV. v roce 1348. První část hradu byla dokončena roku 1357.");
                }
                return v;
            }
        });

        // udalost kliknuti do informacniho okna
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker znacka) {
                if (znacka.getTag()=="znacka1") {
                    Toast.makeText(MainActivity.this, "Informace o hradu Karlštejn", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // kdyby bylo potreba reagovat na kliknuti primo na znacku, nastavit listener pro kliknuti
        // map.setOnMarkerClickListener(this);

        // nastavit pozici mapy a jeji zvetseni
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(souradnice,12));
    }

    /*
    // udalost pro kliknuti primo na znacku
    @Override
    public boolean onMarkerClick(final Marker znacka) {

        if (znacka.getTag()=="znacka1") {
            Toast.makeText(this, "Informace o hradu Karlštejn", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
    */
}