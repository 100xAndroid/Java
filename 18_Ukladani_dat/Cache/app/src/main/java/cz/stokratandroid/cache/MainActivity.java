package cz.stokratandroid.cache;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ulozitData(View sender)
    {
        // nacteni hodnot z formulare
        EditText editText1 = (EditText)findViewById(R.id.editText1);
        String inputText = editText1.getText().toString();

        // nazev souboru a jeho umisteni
        String NAZEV_SOUBORU = "tmp_test.txt";
        File adresar = getBaseContext().getCacheDir();
        // File adresar = getBaseContext().getExternalCacheDir();

        // zapis dat do souboru
        File file = new File(adresar, NAZEV_SOUBORU);
        FileWriter writer = null;
            try {
            writer = new FileWriter(file);
            writer.write(inputText);
            writer.close();
        } catch (IOException e) {
            String txtChyba = "Data se nepodařilo zapsat. " + e.getMessage();
            Toast.makeText(MainActivity.this, txtChyba, Toast.LENGTH_SHORT).show();
            return;
        }
        // zjistit misto ulozeni souboru
        String umisteni = "Uloženo: " + adresar + "/" + NAZEV_SOUBORU;
        // pro informaci zaobrazit umisteni souboru
        Snackbar.make(findViewById(android.R.id.content), umisteni, Snackbar.LENGTH_LONG).setTextMaxLines(5).show();
    }

    public void nacistData(View sender)
    {
        // nazev souboru a jeho umisteni
        String NAZEV_SOUBORU = "tmp_test.txt";
        File adresar = getBaseContext().getCacheDir();
        // File adresar = getBaseContext().getExternalCacheDir();

        try {
            // otevrit soubor pro cteni
            File soubor = new File(adresar, NAZEV_SOUBORU);
            FileInputStream inStream = new FileInputStream(soubor);

            // vytvorit instanci bufferu
            StringBuffer nactenyText = new StringBuffer();

            // soubor postupne, po znacich, nacist do bufferu
            int znak;
            while ((znak = inStream.read()) != -1) {
                nactenyText.append((char)znak);
            }

            // ulozit text do formulare
            EditText editText1 = (EditText)findViewById(R.id.editText1);
            editText1.setText(nactenyText);
        }
        catch (IOException e) {
            // zobrazit informaci o chybe
            String textChyby = "Nedaří se načíst.. \n" + e.getMessage();
            Toast.makeText(MainActivity.this, textChyby, Toast.LENGTH_SHORT).show();
        }
    }
}