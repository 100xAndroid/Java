package cz.stokratandroid.internalstorage;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ulozitData(View sender)
    {
        // nacist hodnoty z formulare
        EditText editText1 = (EditText)findViewById(R.id.editText1);
        String inputText = editText1.getText().toString();

        String NAZEV_SOUBORU = "test.txt";
        try {
            // otevrit soubor pro zapis
            FileOutputStream outStream = openFileOutput(NAZEV_SOUBORU, Context.MODE_PRIVATE);

            // zapsat text
            outStream.write(inputText.getBytes());

            // uzavrit soubor
            outStream.close();
        } catch (Exception e) {
            // zobrazi text, v pripade chyby
            Toast.makeText(MainActivity.this, "Nedaří se zapsat..." + e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }

        // zjistit misto ulozeni souboru
        String umisteni = "Uloženo: " + getFilesDir().getAbsolutePath() + "/" + NAZEV_SOUBORU;
        // pro informaci zobrazit umisteni souboru
        Snackbar.make(findViewById(android.R.id.content), umisteni, Snackbar.LENGTH_LONG).setTextMaxLines(5).show();
    }

    public void nacistData(View sender)
    {
        String NAZEV_SOUBORU = "test.txt";
        try {
            // otevrit soubor pro cteni
            FileInputStream inStream = openFileInput(NAZEV_SOUBORU);

            // vytvorit instanci bufferu
            StringBuffer nactenyText = new StringBuffer();

            // soubor postupne, po znacich, nacist do bufferu
            int znak;
            while ((znak = inStream.read()) != -1) {
                nactenyText.append((char)znak);
            }

            /*
            // ***********************************************
            // Priklad nacteni souboru po radcich.
            // Rychlejsi, ale vhodne pouze pro textove soubory
            // ***********************************************

            // vytvorit instanci readeru a bufferu pro cteni
            InputStreamReader reader = new InputStreamReader(inStream);
            BufferedReader bufReader = new BufferedReader(reader);
            StringBuffer nactenyText = new StringBuffer();

            // text postupne, po radcich, nacist do bufferu
            String radekTextu;
            while ((radekTextu = bufReader.readLine()) != null) {
                nactenyText.append(radekTextu + "\n");
            }
            */
            // ulozit text do formulare
            EditText editText1 = (EditText)findViewById(R.id.editText1);
            editText1.setText(nactenyText);
        }
        catch (IOException e) {
            // zobrazit text, v pripade chyby
            String textChyby = "Nedaří se načíst.. \n" + e.getMessage();
            Toast.makeText(MainActivity.this, textChyby, Toast.LENGTH_SHORT).show();
        }
    }
}