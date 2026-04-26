package cz.stokratandroid.internet2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static TextView txtMesto;
    public static TextView txtPredpoved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // pole pro zadani mesta a informaci o pocasi priradit do promennych
        txtMesto = (TextView) findViewById(R.id.txtMesto);
        txtPredpoved = (TextView) findViewById(R.id.txtPredpoved);
    }

    // nacte data ze serveru a zobrazi je v textovem poli
    public void nacistDataZeServeru(View view) {
        // smazat predchozi informaci o pocasi
        txtPredpoved.setText("");
        // spustit nacitani dat o pocasi
        if (datovePripojeni() == true) {
            String mesto = txtMesto.getText().toString().replace(" ", "%20");
            GetData getData = new GetData(this, mesto);
            getData.startAsync();
        }
    }

    // Overi dostupnost datoveho pripojeni
    private boolean datovePripojeni() {
        Context context =  getApplicationContext();
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() == null) {
            txtPredpoved.setText("Datové připojení není dostupné.");
            return false;
        }
        return true;
    }
}