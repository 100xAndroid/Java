package cz.stokratandroid.listview1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
        String [] arrVerzeAndroidu = { "Android 4.0 - 4.0.4",
                "Android 4.1 - 4.3.1",
                "Android 4.4 - 4.4.4",
                "Android 5.0 - 5.1.1",
                "Android 6.0 - 6.0.1",
                "Android 7.0 - 7.1.2",
                "Android 8.0 - 8.1",
                "Android 9.0",
                "Android 10",
                "Android 11",
                "Android 12",
                "Android 13",
                "Android 14" };

        // vytvorime instanci adapteru
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrVerzeAndroidu);
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
                Toast.makeText(MainActivity.this, textPolozky, Toast.LENGTH_SHORT).show();
            }
        };

        // nastaveni posluchace udalosti
        listView.setOnItemClickListener(clickListener);
    }
}
