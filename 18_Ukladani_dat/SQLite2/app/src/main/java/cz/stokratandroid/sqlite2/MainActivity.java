package cz.stokratandroid.sqlite2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    // proměnná s odkazem na fragment se seznamem verzi
    public View fragmentZalozkaVerze;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // nova instance adapteru
        TabAdapter mAdapter = new TabAdapter(this);

        // najdeme na formulari TabLayout a ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        ViewPager2 viewPager = (ViewPager2) findViewById(R.id.viewPager);

        // propojeni ViewPager s adapterem;
        viewPager.setAdapter(mAdapter);

        // nazvy zalozek
        final String[] zalozky = new String[]{"Verze", "Vložit", "Upravit", "Smazat"};

        // propojeni TabLayoutu s ViewPager
        new TabLayoutMediator(tabLayout, viewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        tab.setText(zalozky[position]);
                    }
                }).attach();
    }

    // Nacte data z DB tabulky, vytvori adapter a propoji ho s gridView
    public void nacistData () {

        // test, jestli uz existuje zalozka verzi
        if (fragmentZalozkaVerze == null)
            return;

        // instance tridy DatabaseHandler
        DatabaseHandler dbHelper = new DatabaseHandler(fragmentZalozkaVerze.getContext());

        // pripojeni se k databazi
        dbHelper.pripojitDB();

        // nacteme data z tabulky
        Cursor data = dbHelper.nacistData();

        // vytvorime instanci adapteru
        String[] arrFrom = new String[] {"_id", "Verze", "Nazev"};
        int[] arrTo = new int[] {R.id.polozka_ID, R.id.polozka_verze, R.id.polozka_nazev};
        SimpleCursorAdapter adapter = null;
        try {
            adapter = new SimpleCursorAdapter(fragmentZalozkaVerze.getContext(), R.layout.polozka_gridu, data, arrFrom, arrTo, 0);
        }
        catch (Exception ex) {
            Toast.makeText(fragmentZalozkaVerze.getContext(), "Chyba: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        // propojeni adapteru s kontejnerem GridView
        GridView gridView = (GridView) fragmentZalozkaVerze.findViewById(R.id.gridViewVerzeAndroidu);
        gridView.setAdapter(adapter);

        // odpojeni se od databaze
        dbHelper.odpojitDB();
    }

    // udalost tlacitka Vlozit zaznam
    public void butVlozitZaznam(View view) {

        // instance tridy DatabaseHandler
        DatabaseHandler dbHelper = new DatabaseHandler(this);

        // pripojeni se k databazi
        dbHelper.pripojitDB();

        TextView txtVerze = (TextView) findViewById(R.id.textVlozitVerze);
        TextView txtNazev = (TextView) findViewById(R.id.textVlozitNazev);
        dbHelper.vlozitZaznam(txtNazev.getText().toString(), txtVerze.getText().toString());

        Toast.makeText(this, "Záznam byl zapsaný do databáze", Toast.LENGTH_SHORT).show();

        // znovunacteni dat do gridu a odpojeni se od DB
        nacistData();

        dbHelper.odpojitDB();
    }

    // udalost tlacitka Upravit zaznam
    public void butUpravitZaznam(View view) {

        // instance tridy DatabaseHandler
        DatabaseHandler dbHelper = new DatabaseHandler(this);

        // pripojeni se k databazi
        dbHelper.pripojitDB();

        TextView txtId = (TextView) findViewById(R.id.textUpravitId);
        TextView txtVerze = (TextView) findViewById(R.id.textUpravitVerze);
        TextView txtNazev = (TextView) findViewById(R.id.textUpravitNazev);
        dbHelper.upravitZaznam(txtId.getText().toString(), txtNazev.getText().toString(), txtVerze.getText().toString());

        Toast.makeText(this, "Záznam byl zapsaný do databáze", Toast.LENGTH_SHORT).show();

        //  ZalozkaVerze zalozkaVerze = (Fragment) view.find ViewWithTag("test");

        // znovunacteni dat do gridu a odpojeni se od DB
        nacistData();

        dbHelper.odpojitDB();
    }

    // udalost tlacitka Smazat zaznam
    public void butSmazatZaznam(View view) {

        // instance tridy DatabaseHandler
        DatabaseHandler dbHelper = new DatabaseHandler(this);

        // pripojeni se k databazi
        dbHelper.pripojitDB();

        TextView txtId = (TextView) findViewById(R.id.textSmazatId);
        dbHelper.smazatZaznam(txtId.getText().toString());

        Toast.makeText(this, "Záznam byl vymazaný z databáze", Toast.LENGTH_SHORT).show();

        // znovunacteni dat do gridu a odpojeni se od DB
        nacistData();

        dbHelper.odpojitDB();
    }

    // udalost tlacitka Smazat zaznamy
    public void butSmazatZaznamy(View view) {

        // instance tridy DatabaseHandler
        DatabaseHandler dbHelper = new DatabaseHandler(this);

        // pripojeni se k databazi
        dbHelper.pripojitDB();

        dbHelper.smazatData();

        Toast.makeText(this, "Záznamy byly vymazané z databáze", Toast.LENGTH_SHORT).show();

        // znovunacteni dat do gridu a odpojeni se od DB
        nacistData();

        dbHelper.odpojitDB();
    }
}