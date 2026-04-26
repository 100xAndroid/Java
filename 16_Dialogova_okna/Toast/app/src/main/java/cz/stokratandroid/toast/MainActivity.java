package cz.stokratandroid.toast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // nejjednodussi verze toast zpravy
    public void toastDialog1(View sender) {
        // Toast.makeText(getApplicationContext(), "Toast-test", Toast.LENGTH_LONG).show();

        Context kontext = getApplicationContext();
        String zprava = "Toast-test";
        int trvani = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(kontext, zprava, trvani);
        toast.show();
    }

    // toast zprava se zmenenym umistenim na displeji
    // POZOR, OD API VERZE 30 OFICIALNE NEPODPOROVANO
    public void toastDialog2 (View sender) {
        Toast toast = Toast.makeText(getApplicationContext(), "Toast-test", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP|Gravity.END, 100, 450);
        toast.show();
    }

    // toast zprava s vlastnim layoutem
    // POZOR, OD API VERZE 30 OFICIALNE NEPODPOROVANO
    public void toastDialog3 (View sender) {
        // ziskame formular
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.uzivatelsky_toast, (ViewGroup) findViewById(R.id.custom_toast_container));

        // doplneni textu do poli formulare
        TextView text1 = (TextView) layout.findViewById(R.id.text1);
        text1.setText("Toast - test");
        TextView text2 = (TextView) layout.findViewById(R.id.text2);
        text2.setText("Víceřádková, uživatelsky definovaná, toast zpráva.");

        // zobrazeni toast zpravy
        Toast toast = Toast.makeText(getApplicationContext(), "Toast-test", Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}