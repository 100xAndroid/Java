package cz.stokratandroid.gridview1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
        // znak \n je pouzity kvuli zalomeni textu
        String [] arrVerzeAndroidu = {
                "Android\n2.0 - 2.1",
                "Android\n2.2 - 2.2.3",
                "Android\n2.3 - 2.3.7",
                "Android\n3.0 - 3.2.6",
                "Android\n4.0 - 4.0.4",
                "Android\n4.1 - 4.3.1",
                "Android\n4.4 - 4.4.4",
                "Android\n5.0 - 5.1.1",
                "Android\n6.0 - 6.0.1",
                "Android\n7.0 - 7.1.2",
                "Android\n8.0 - 8.1",
                "Android\n9.0",
                "Android\n10",
                "Android\n11",
                "Android\n12",
                "Android\n13",
                "Android\n14"
        };

        // vytvorit instanci adapteru
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrVerzeAndroidu);
        // propojeni adapteru s kontejnerem GridView
        GridView gridView = (GridView) findViewById(R.id.gridViewVerzeAndroidu);
        gridView.setAdapter(adapter);


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
        gridView.setOnItemClickListener(clickListener);
    }
}
