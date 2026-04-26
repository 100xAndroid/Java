package cz.stokratandroid.recyclerview1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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
        ArrayList<String> lstVerzeAndroidu = new ArrayList<String> ();
        lstVerzeAndroidu.add("Android 4.0 - 4.0.4");
        lstVerzeAndroidu.add("Android 4.1 - 4.3.1");
        lstVerzeAndroidu.add("Android 4.4 - 4.4.4");
        lstVerzeAndroidu.add("Android 5.0 - 5.1.1");
        lstVerzeAndroidu.add("Android 6.0 - 6.0.1");
        lstVerzeAndroidu.add("Android 7.0 - 7.1.2");
        lstVerzeAndroidu.add("Android 8.0 - 8.1");
        lstVerzeAndroidu.add("Android 9.0");
        lstVerzeAndroidu.add("Android 10");
        lstVerzeAndroidu.add("Android 11");
        lstVerzeAndroidu.add("Android 12");
        lstVerzeAndroidu.add("Android 13");
        lstVerzeAndroidu.add("Android 14");
        lstVerzeAndroidu.add("Android 15");
        lstVerzeAndroidu.add("Android 16");
        lstVerzeAndroidu.add("Android 17");

        // vytvorit instanci adapteru
        RecyclerAdapter adapter = new RecyclerAdapter(lstVerzeAndroidu);

        // najit na formulari kontejner RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recViewVerzeAndroidu);

        // vytvorit a priradit Layout Manager, ktery nastavuje chovani komponenty RecyclerView
        // V tomto pripade chceme aby se chovala jako Linear Layout
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // nastaveni oddelovace radku
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        // propojeni adapteru s kontejnerem RecyclerView
        recyclerView.setAdapter(adapter);
    }
}