package cz.stokratandroid.sqlite1;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    DatabaseHandler dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // vytvorime instanci tridy DatabaseHandler
        dbHelper = new DatabaseHandler(this);

        // pripojeni se k databazi
        dbHelper.pripojitDB();

        // vymazeme z tabulky stara data
        dbHelper.smazatData();

        // vlozime do tabulky iniciacni data
        dbHelper.vlozitZaznam("bez kódového označení", "1.0");
        dbHelper.vlozitZaznam("bez kódového označení", "1.1");
        dbHelper.vlozitZaznam("Cupcake", "1.5");
        dbHelper.vlozitZaznam("Donut", "1.6");
        dbHelper.vlozitZaznam("Eclair", "2.0");
        dbHelper.vlozitZaznam("Eclair", "2.1");
        dbHelper.vlozitZaznam("Froyo", "2.2");
        dbHelper.vlozitZaznam("Gingerbread", "2.3");
        dbHelper.vlozitZaznam("Honeycomb", "3.0");
        dbHelper.vlozitZaznam("Honeycomb", "3.1");
        dbHelper.vlozitZaznam("Honeycomb", "3.2");
        dbHelper.vlozitZaznam("Ice Cream Sandwich", "4.0");
        dbHelper.vlozitZaznam("Jelly Bean", "4.1");
        dbHelper.vlozitZaznam("Jelly Bean", "4.2");
        dbHelper.vlozitZaznam("Jelly Bean", "4.3");
        dbHelper.vlozitZaznam("KitKat", "4.4");
        dbHelper.vlozitZaznam("Lollipop", "5.0");
        dbHelper.vlozitZaznam("Lollipop", "5.1");
        dbHelper.vlozitZaznam("Marshmallow", "6.0");
        dbHelper.vlozitZaznam("Nougat", "7.0");
        dbHelper.vlozitZaznam("Nougat", "7.1");
        dbHelper.vlozitZaznam("Oreo", "8.0");
        dbHelper.vlozitZaznam("Oreo", "8.1");
        dbHelper.vlozitZaznam("Pie", "9.0");
        dbHelper.vlozitZaznam("Android 10", "10");
        dbHelper.vlozitZaznam("Android 11", "11");
        dbHelper.vlozitZaznam("Android 12", "12");
        dbHelper.vlozitZaznam("Android 13", "13");
        dbHelper.vlozitZaznam("Android 14", "14");

        // priklad aktualizace zaznamu
        // dbHelper.UpravitZaznam("4", "test", "test");

        // nacteme data z tabulky
        Cursor data = dbHelper.nacistData();

        // zjistime pocet nalezenych zaznamu
        int pocetZaznamu=data.getCount();

        // najdeme textove pole ve formulari
        TextView dbDataTextView = (TextView) findViewById(R.id.dbData);

        // kontrola, jestli je v tabulce alespon jeden zaznam
        if(pocetZaznamu < 1) {
            dbDataTextView.setText("V databázi nebyl nalezen žádný záznam");
            return;
        }
        else {
            dbDataTextView.setText("Počet záznamů v tabulce: " + pocetZaznamu +"\n\n");
        }

        // data postupne nacteme a zapiseme do textoveho pole
        while (data.moveToNext()) {
            dbDataTextView.append("[" + data.getString(0) + "]");
            dbDataTextView.append(" Android " + data.getString(2));
            dbDataTextView.append(", " + data.getString(1) + "\n");
        }

        // odpojeni se od databaze
        // dbHelper.odpojitDB();
    }
}
