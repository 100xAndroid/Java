package cz.stokratandroid.zahlaviformulare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // nastaveni Toolbaru
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // nastaveni textu zahlavi
        // getSupportActionBar().setTitle("Test");

        // nastaveni barvy zahlavi
        // ColorDrawable barvaZahlavi = new ColorDrawable(Color.argb(128, 255, 0, 0));
        // getSupportActionBar().setBackgroundDrawable(barvaZahlavi);
    }
}