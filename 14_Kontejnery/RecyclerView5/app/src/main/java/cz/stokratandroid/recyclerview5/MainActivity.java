package cz.stokratandroid.recyclerview5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> lstVerzeAndroidu = new ArrayList<String> ();
    RecyclerAdapter adapter = new RecyclerAdapter(lstVerzeAndroidu);

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
        lstVerzeAndroidu.add("Android 4.0 - 4.0.4:Ice Cream Sandwich");
        lstVerzeAndroidu.add("Android 4.1 - 4.3.1:Jelly Bean");
        lstVerzeAndroidu.add("Android 4.4 - 4.4.4:KitKat");
        lstVerzeAndroidu.add("Android 5.0 - 5.1.1:Lollipop");
        lstVerzeAndroidu.add("Android 6.0 - 6.0.1:Marshmallow");
        lstVerzeAndroidu.add("Android 7.0 - 7.1.2:Nougat");
        lstVerzeAndroidu.add("Android 8.0 – 8.1:Oreo");
        lstVerzeAndroidu.add("Android 9.0:Pie");
        lstVerzeAndroidu.add("Android 10 a vyšší:Android 10");

        // vytvorime instanci kontejneru RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recViewVerzeAndroidu);

        // vytvorime a priradime Layout Manager, ktery nastavuje chovani komponenty RecyclerView
        // V tomto pripade chceme aby se chovala jako Linear Layout
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // nastaveni oddelovace radku
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        // propojeni adapteru s kontejnerem RecyclerView
        recyclerView.setAdapter(adapter);

        PosouvaniHelper posouvaniHelper = new PosouvaniHelper(this) {
            @Override
            public NakreslitTlacitko nakreslitTlacitko1(RecyclerView.ViewHolder viewHolder) {
                return new PosouvaniHelper.NakreslitTlacitko(
                        "Info",
                        Color.parseColor("#744bdd"),
                        new PosouvaniHelper.NakreslitTlacitkoClickListener() {
                            @Override
                            public void onClick(final int pos) {
                                String[] data = lstVerzeAndroidu.get(pos).split(":");
                                Toast.makeText(getApplicationContext(), "INFO - ".concat(data[1]), Toast.LENGTH_LONG).show();
                            }
                        }
                );
            }
            @Override
            public NakreslitTlacitko nakreslitTlacitko2(RecyclerView.ViewHolder viewHolder) {
                return new PosouvaniHelper.NakreslitTlacitko(
                        "Smazat",
                        Color.parseColor("#db4141"),
                        new PosouvaniHelper.NakreslitTlacitkoClickListener() {
                            @Override
                            public void onClick(int pos) {
                                String[] data = lstVerzeAndroidu.get(pos).split(":");
                                Toast.makeText(getApplicationContext(), "SMAZAT - ".concat(data[1]), Toast.LENGTH_LONG).show();
                            }
                        }
                );
            }
        };
        posouvaniHelper.pripojitRecyclerView(recyclerView);
    }
}
