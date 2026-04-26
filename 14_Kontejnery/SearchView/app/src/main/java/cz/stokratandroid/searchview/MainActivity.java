package cz.stokratandroid.searchview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    // promenna s ukazatelem na adapter
    RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        naplnitRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // pridat do Menu box pro hledani
        MenuInflater inflanter = getMenuInflater();
        inflanter.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView)item.getActionView();

        // posluchac udalosti zmeny hodnoty v poli pro hledani
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // nastaveni filtru adapteru
                adapter.getFilter().filter(newText);

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    // metoda nastavi hodnoty do RecycelrView recViewVerzeAndroidu
    private void naplnitRecyclerView() {

        // nacteme data z resources
        ArrayList<String> lstVerzeAndroidu = new ArrayList<String>();
        lstVerzeAndroidu.addAll(Arrays.asList(getResources().getStringArray(R.array.androidVerze)));

        // vytvorime instanci adapteru
        adapter = new RecyclerAdapter(lstVerzeAndroidu);

        // najdeme na formulari kontejner RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recViewVerzeAndroidu);

        // vytvorime a priradime Layout Manager, ktery nastavuje chovani komponenty RecyclerView
        // V tomto pripade chceme aby se chovala jako Linear Layout
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // nastaveni oddelovace radku
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        // nastavit pevnou velikost recyclerView (nemenit ji podle poctu polozek)
        recyclerView.setHasFixedSize(true);

        // propojeni adapteru s kontejnerem RecyclerView
        recyclerView.setAdapter(adapter);
    }
}