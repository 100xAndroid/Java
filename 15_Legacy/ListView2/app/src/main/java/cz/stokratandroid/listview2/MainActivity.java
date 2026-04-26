package cz.stokratandroid.listview2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        naplnitListView();
    }

    // Metoda nastavi hodnoty do ListView listViewVerzeAndroidu
    private void naplnitListView()
    {
        // vstupní data
        // vzdy dva texty oddelene dvojteckou, adapter si je rozdeli
        String [] arrVerzeAndroidu = {
                "Android 4.0 - 4.0.4:Ice Cream Sandwich",
                "Android 4.1 - 4.3.1:Jelly Bean",
                "Android 4.4 - 4.4.4:KitKat",
                "Android 5.0 - 5.1.1:Lollipop",
                "Android 6.0 - 6.0.1:Marshmallow",
                "Android 7.0 - 7.1.2:Nougat",
                "Android 8.0 – 8.1:Oreo",
                "Android 9.0:Pie",
                "Android 10 a vyšší:Android 10"
        };

        // vytvorime instanci adapteru
        ListAdapter adapter = new PolozkaAdapter(this, arrVerzeAndroidu);
        // propojeni adapteru s kontejnerem ListView
        ListView listView = (ListView) findViewById(R.id.listViewVerzeAndroidu);
        listView.setAdapter(adapter);

        // vytvoreni instance posluchace udalosti kliknuti na polozku
        AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
            @Override
            // prepsani puvodni metody onItemClick kontejneru
            public void onItemClick(AdapterView<?> parent, View view, int pozice, long id) {
                // zjisti text na vybrane polozce
                String textPolozky = String.valueOf(parent.getItemAtPosition(pozice));
                // zobrazi text na displeji
                Toast.makeText(MainActivity.this, textPolozky.replace(":", " - "), Toast.LENGTH_SHORT).show();
            }
        };

        // nastaveni posluchace udalosti
        listView.setOnItemClickListener(clickListener);
    }
}
