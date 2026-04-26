package cz.stokratandroid.mapy4;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.CustomCap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;

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

        // nastavit pozici mapy a jeji zvetseni
        LatLng souradnice = new LatLng(49.9453, 14.1871);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(souradnice,13));

        // trasa
        PolylineOptions trasaVlastnosti = new PolylineOptions();
        trasaVlastnosti.geodesic(true);         // zohlednit zakřivení Zeme
        trasaVlastnosti.color(Color.GRAY);      // barva spojnice
        trasaVlastnosti.add(new LatLng(49.9323, 14.1753));  // souradnice jednotlivych bodu
        trasaVlastnosti.add(new LatLng(49.9328, 14.1799));
        trasaVlastnosti.add(new LatLng(49.9345, 14.1806));
        trasaVlastnosti.add(new LatLng(49.9340, 14.1830));
        trasaVlastnosti.add(new LatLng(49.9369, 14.1846));
        trasaVlastnosti.add(new LatLng(49.9383, 14.1871));
        trasaVlastnosti.add(new LatLng(49.9465, 14.1834));
        trasaVlastnosti.add(new LatLng(49.9511, 14.1925));
        trasaVlastnosti.add(new LatLng(49.9534, 14.2045));
        trasaVlastnosti.add(new LatLng(49.9583, 14.2067));
        trasaVlastnosti.add(new LatLng(49.9611, 14.2036));

        // startovni znacka
        trasaVlastnosti.startCap(new CustomCap(BitmapDescriptorFactory.fromResource(R.drawable.start)));
        // zaobleni konce trasy
        trasaVlastnosti.endCap(new RoundCap());
        map.addPolyline(trasaVlastnosti);

        // cilova znacka
        LatLng souradniceCil = new LatLng(49.9611, 14.2036);
        MarkerOptions vlastnostiZnacky = new MarkerOptions().position(souradniceCil);
        vlastnostiZnacky.icon(BitmapDescriptorFactory.fromResource(R.drawable.cil));
        map.addMarker(vlastnostiZnacky);

/*
        // polygon
        PolygonOptions polygonVlastnosti = new PolygonOptions();
        polygonVlastnosti.add(new LatLng(49.9415, 14.1919));    // souradnice jednotlivych bodu
        polygonVlastnosti.add(new LatLng(49.9411, 14.1873));
        polygonVlastnosti.add(new LatLng(49.9390, 14.1870));
        polygonVlastnosti.add(new LatLng(49.9375, 14.1883));
        polygonVlastnosti.add(new LatLng(49.9385, 14.1918));
        polygonVlastnosti.strokeColor(Color.DKGRAY);            // barva ohraniceni
        polygonVlastnosti.fillColor(0x40808080);                // barva vyplne (808080) a pruhlednost (40)
        Polygon polygon = map.addPolygon(polygonVlastnosti);
*/

/*
        // kruh
        LatLng souradniceKruhu = new LatLng(49.9392, 14.1879);
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(souradniceKruhu);  // stred kruhu
        circleOptions.radius(1200);             // polomer
        circleOptions.strokeColor(Color.BLACK); // barva ohraniceni
        circleOptions.fillColor(0x40808080);    // barva vyplne (808080) a pruhlednost (40)
        circleOptions.strokeWidth(5);           // sila cary
        map.addCircle(circleOptions);
*/
    }
}
