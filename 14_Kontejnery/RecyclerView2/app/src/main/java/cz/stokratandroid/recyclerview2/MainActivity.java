package cz.stokratandroid.recyclerview2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        naplnitRecyclerView();
    }

    // Metoda nastavi hodnoty do RecyclerView recViewVerzeAndroidu
    private void naplnitRecyclerView()
    {
        // vstupní data
        // vzdy dva texty oddelene dvojteckou, adapter si je rozdeli
        ArrayList<String> lstVerzeAndroidu = new ArrayList<String> ();
        lstVerzeAndroidu.add("Android 4.0 - 4.0.4:Ice Cream Sandwich");
        lstVerzeAndroidu.add("Android 4.1 - 4.3.1:Jelly Bean");
        lstVerzeAndroidu.add("Android 4.4 - 4.4.4:KitKat");
        lstVerzeAndroidu.add("Android 5.0 - 5.1.1:Lollipop");
        lstVerzeAndroidu.add("Android 6.0 - 6.0.1:Marshmallow");
        lstVerzeAndroidu.add("Android 7.0 - 7.1.2:Nougat");
        lstVerzeAndroidu.add("Android 8.0 – 8.1:Oreo");
        lstVerzeAndroidu.add("Android 9.0:Pie");
        lstVerzeAndroidu.add("Android 10 a vyšší:Android 10");

        RecyclerAdapter adapter = new RecyclerAdapter(lstVerzeAndroidu, this);

        // vytvorime instanci kontejneru RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recViewVerzeAndroidu);

        // vytvorime a priradime Layout Manager, ktery nastavuje chovani komponenty RecyclerView
        // V tomto pripade chceme aby se chovala jako Linear Layout
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // nastaveni oddelovace radku
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        // propojeni adapteru s kontejnerem RecyclerView
        recyclerView.setAdapter(adapter);
    }

    // metoda volana z RecycleAdapter
    public void onClickCalled(String anyValue) {
        Toast.makeText(this, anyValue, Toast.LENGTH_SHORT).show();

        /*
        // spusteni aktivity po kliknuti na polozku
        Intent intent = new Intent(this, NovaAktivita.class);
        intent.putExtra("klic", hodnota); // volitelny parametr
        this.startActivity(intent);
        */
    }
}
