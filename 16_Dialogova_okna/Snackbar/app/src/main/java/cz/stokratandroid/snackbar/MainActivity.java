package cz.stokratandroid.snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // nejjednodussi verze snackbar zpravy
    public void SnackbarDialog1 (View sender) {
        View view = findViewById(android.R.id.content);
        Snackbar.make(view, "Snackbar - test", Snackbar.LENGTH_LONG).show();
    }

    // snackbar zprava s tlacitkem
    public void SnackbarDialog2 (View sender) {
        // posluchac udalosti kliknuti na tlacitko
        View.OnClickListener onClickListener;
        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Kliknuto na tlačítko snackbaru", Toast.LENGTH_LONG).show();
            }
        };

        // definice zpravy
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Snackbar - test", Snackbar.LENGTH_LONG);

        // nastaveni tlacitka
        snackbar.setAction("Tlačítko", onClickListener);
        snackbar.setActionTextColor(Color.YELLOW);

        // nastaveni barvy snackbaru
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.GRAY);

        // nastaveni barvy textu
        TextView textView = (TextView) snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);

        // nastaveni maximalniho poctu radku (standardne zobrazi max dva radky)
        textView.setMaxLines(5);

        // zobrazeni snackbaru
        snackbar.show();
    }
}
