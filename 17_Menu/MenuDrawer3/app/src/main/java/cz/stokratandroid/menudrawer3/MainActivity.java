package cz.stokratandroid.menudrawer3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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

        nastavitDrawer();

        // nastaveni tlacitka
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // nastaveni posluchace udalosti kliknuti do menu
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // zobrazi text na displeji
        Toast.makeText(MainActivity.this, "Zvolena položka " + item.getTitle(),
                Toast.LENGTH_SHORT).show();

        // zjistime, ktera polozka byla zvolena
        int id = item.getItemId();
        if (id == R.id.nav_70) {
            // akce po volbe Androidu verze 7.0
        } else if (id == R.id.nav_71) {
            // akce po volbe Androidu verze 7.1
        } else if (id == R.id.nav_80) {
            // akce po volbe Androidu verze 8.0
        } else if (id == R.id.nav_81) {
            // akce po volbe Androidu verze 8.1
        } else if (id == R.id.nav_90) {
            // akce po volbe Androidu verze 9.0
        } else if (id == R.id.nav_10) {
            // akce po volbe Androidu verze 10+
        }

        // zavrit drawer menu
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}
