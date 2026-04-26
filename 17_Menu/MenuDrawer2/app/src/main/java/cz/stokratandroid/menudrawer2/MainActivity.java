package cz.stokratandroid.menudrawer2;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // nastaveni Toolbaru
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        naplnitRecyclerView();
        nastavitDrawer();

        // nastaveni tlacitka
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void nastavitDrawer () {
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_otevrit, R.string.drawer_zavrit) {

            // tato udalost se zavola jakmile se menu otevre
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Verze Androidu");
            }

            // tato udalost se zavola jakmile se menu zavre
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(getTitle().toString());
            }
        };

        // nastaveni prepinace a udalosti
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

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

        // vytvorime instanci adapteru
        RecyclerAdapter adapter = new RecyclerAdapter(lstVerzeAndroidu);

        // najdeme na formulari kontejner RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.navList);

        // vytvorime a priradime Layout Manager, ktery nastavuje chovani komponenty RecyclerView
        // V tomto pripade chceme, aby se chovala jako Linear Layout
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // nastaveni oddelovace radku
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        // propojeni adapteru s kontejnerem RecyclerView
        recyclerView.setAdapter(adapter);
    }
}
