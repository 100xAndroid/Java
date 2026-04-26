package cz.stokratandroid.menu1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    // udalost vyvolavana pred otevrenim menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // pridat menu na toolbar
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // udalost vyvolana pri kliknuti na polozku menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // zjistime, ktera polozka menu byla zvolena
        // pokud Nastaveni, zobrazit zpravu
        if (item.getItemId() == R.id.nastaveni) {
            Toast.makeText(this, "Zvolena položka menu Nastavení", Toast.LENGTH_SHORT).show();
            return true;
        }
        // na jine polozky nez Nastaveni nereagovat
        else {
            return super.onOptionsItemSelected(item);
        }
    }
}