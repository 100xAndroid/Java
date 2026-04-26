package cz.stokratandroid.resassets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void nacistRes(View sender)
    {
        // nazev souboru a jeho umisteni
        String NAZEV_SOUBORU = "tmp_test.txt";
        File adresar = getBaseContext().getCacheDir();

        try {
            // otevrit soubor pro cteni
            Resources r = getResources();
            InputStream inStream = r.openRawResource(R.raw.test_raw);

            String nactenyText ="";

            // vytvorit instanci bufferu
            BufferedReader buff = new BufferedReader(new InputStreamReader(inStream));

            // soubor postupne, po radcich, nacist do bufferu
            String radka = "";
            while ((radka = buff.readLine()) != null) {
                nactenyText=nactenyText.concat(radka).concat("\n");
            }
            /*
            do {
                nactenyText=nactenyText.concat(radka).concat("\n");
                radka = buff.readLine();
            } while (radka != null);
            */

            // ulozime text do formulare
            EditText editText1 = (EditText)findViewById(R.id.editText1);
            editText1.setText(nactenyText);
        }
        catch (IOException e) {
            // zobrazit informaci o chybe
            String textChyby = "Nedaří se načíst..\n" + e.getMessage();
            Toast.makeText(MainActivity.this, textChyby, Toast.LENGTH_SHORT).show();
        }
    }

    public void nacistAssets(View sender)
    {
        // nazev souboru a jeho umisteni
        String NAZEV_SOUBORU = "tmp_test.txt";
        File adresar = getBaseContext().getCacheDir();

        try {
            // otevreme soubor pro cteni
            Context context = getApplicationContext();
            InputStream inStream = context.getAssets().open("test_assets.txt");

            String nactenyText ="";

            // vytvorit instanci bufferu
            BufferedReader buff = new BufferedReader(new InputStreamReader(inStream));

            // soubor postupne, po radkach, nacist do bufferu
            String radka = "";
            while ((radka = buff.readLine()) != null) {
                nactenyText=nactenyText.concat(radka).concat("\n");
            }

            // ulozime text do formulare
            EditText editText1 = (EditText)findViewById(R.id.editText1);
            editText1.setText(nactenyText);
        }
        catch (IOException e) {
            // zobrazi informaci o chybe
            String textChyby = "Nedaří se načíst..\n" + e.getMessage();
            Toast.makeText(MainActivity.this, textChyby, Toast.LENGTH_SHORT).show();
        }
    }
}