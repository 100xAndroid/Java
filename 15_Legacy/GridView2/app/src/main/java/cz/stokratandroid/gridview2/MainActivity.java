package cz.stokratandroid.gridview2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        naplnitGridView();
    }

    // Metoda nastavi hodnoty do GridView gridViewVerzeAndroidu
    private void naplnitGridView()
    {
        // vstupní data
        // vzdy dva texty oddelene dvojteckou, adapter si je rozdeli
        String [] arrVerzeAndroidu = {
                "Android 2.0 - 2.1:Eclair",
                "Android 2.2 - 2.2.3:Froyo",
                "Android 2.3 - 2.3.7:Gingerbread",
                "Android 3.0 - 3.2.6:Honeycomb",
                "Android 4.0 - 4.0.4:Ice Cream Sandwich",
                "Android 4.1 - 4.3.1:Jelly Bean",
                "Android 4.4 - 4.4.4:KitKat",
                "Android 5.0 - 5.1.1:Lollipop",
                "Android 6.0 - 6.0.1:Marshmallow",
                "Android 7.0 – 7.1.2:Nougat",
                "Android 8.0 – 8.1:Oreo",
                "Android 9.0:Pie",
                "Android 10 a vyšší:Android 10"
        };

        // vytvorit instanci adapteru
        ListAdapter adapter = new PolozkaAdapter(this, arrVerzeAndroidu);
        // propojeni adapteru s kontejnerem GridView
        GridView gridView = (GridView) findViewById(R.id.gridViewVerzeAndroidu);
        gridView.setAdapter(adapter);


        // vytvoreni instance posluchace udalosti kliknuti na polozku
        AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
            @Override
            // prepsani puvodni metody onItemClick kontejneru
            public void onItemClick(AdapterView<?> parent, View view, int pozice, long id) {
                // zjistit text na vybrane polozce
                String textPolozky = String.valueOf(parent.getItemAtPosition(pozice));
                // zobrazit text na displeji
                Toast.makeText(MainActivity.this, textPolozky.replace(":", " - "), Toast.LENGTH_SHORT).show();
            }
        };

        // nastaveni posluchace udalosti
        gridView.setOnItemClickListener(clickListener);
    }
}
