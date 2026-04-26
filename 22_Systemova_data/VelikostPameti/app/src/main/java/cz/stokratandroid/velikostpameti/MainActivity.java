package cz.stokratandroid.velikostpameti;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // velikost pameti
        ActivityManager actManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        actManager.getMemoryInfo(memInfo);
        float operacniPametCelkem = (float) memInfo.totalMem / (1000*1000*1000);
        float operacniPametVolna = (float) memInfo.availMem / (1000*1000*1000);

        // velikost uloziste
        File cesta = Environment.getDataDirectory();
        StatFs stat = new StatFs(cesta.getPath());
        float ulozisteCelkem = (float)stat.getTotalBytes() / (1000*1000*1000);
        float ulozisteVolne = (float)stat.getFreeBytes() / (1000*1000*1000);

        // velikost uloziste, alternativni zpusob
        File cesta2 = Environment.getDataDirectory();
        StatFs stat2 = new StatFs(cesta2.getPath());
        long velikostBloku = stat2.getBlockSizeLong();
        long celkemBloku = stat2.getBlockCountLong();
        long volnychBloku = stat2.getAvailableBlocksLong();
        float ulozisteCelkem2 = (float)celkemBloku * velikostBloku / (1000*1000*1000);
        float ulozisteVolne2 = (float)volnychBloku * velikostBloku / (1000*1000*1000);

        // velikost uloziste, druhy alternativni zpusob
        File soubor = new File(getFilesDir().getAbsoluteFile().toString());
        float ulozisteCelkem3 = (float)soubor.getTotalSpace() / (1000*1000*1000);
        float ulozisteVolne3 = (float)soubor.getFreeSpace() / (1000*1000*1000);

        // zobrazeni vysledku
        TextView txtPametCelkem = (TextView) findViewById(R.id.txtPametCelkem);
        TextView txtPametVolna = (TextView) findViewById(R.id.txtPametVolna);
        TextView txtUlozisteCelkem = (TextView) findViewById(R.id.txtUlozisteCelkem);
        TextView txtUlozisteVolne = (TextView) findViewById(R.id.txtUlozisteVolne);

        txtPametCelkem.setText(String.format("%.02f GB", operacniPametCelkem));
        txtPametVolna.setText(String.format("%.02f GB", operacniPametVolna));
        txtUlozisteCelkem.setText(String.format("%.02f GB", ulozisteCelkem));
        txtUlozisteVolne.setText(String.format("%.02f GB", ulozisteVolne));
    }
}