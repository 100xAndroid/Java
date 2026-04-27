package cz.stokratandroid.widget2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // otevrit soubor pro cteni preferenci
        SharedPreferences sharedPreferences = getSharedPreferences("Nastaveni", Context.MODE_PRIVATE);
        // naciste ulozenou hodnotu
        String misto = sharedPreferences.getString("misto", "Karlovy Vary");
        // zapsat hodnotu do pole
        EditText editText = (EditText)findViewById(R.id.txtMisto);
        editText.setText(misto);
    }

    // ulozit zvolene mesto do sdilenych preferenci
    public void ulozitNastaveni (View view) {
        // nacist hodnoty z formulare
        EditText editText1 = (EditText)findViewById(R.id.txtMisto);
        String inputText = editText1.getText().toString();

        // otevrit soubor pro zapis preferenci
        SharedPreferences sharedPreferences = getSharedPreferences("Nastaveni", Context.MODE_PRIVATE);
        // vytvorit objekt editor preferenci
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // do editoru ulozit klic a hodnotu z formulare
        editor.putString("misto", inputText);
        // data ulozit
        editor.apply();

        // informace pro uzivatele
        Toast.makeText(getApplicationContext(), "Nastavení uloženo.", Toast.LENGTH_LONG).show();
    }
}
