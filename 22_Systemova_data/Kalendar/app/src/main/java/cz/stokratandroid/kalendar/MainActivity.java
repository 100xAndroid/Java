package cz.stokratandroid.kalendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testOpravneni();
    }

    public void nacistKalendare(View view) {

        // sloupce ktere chceme vypsat
        final String[] SLOUPCE = new String[] {
                CalendarContract.Calendars._ID,
                CalendarContract.Calendars.IS_PRIMARY,
                CalendarContract.Calendars.VISIBLE,
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
                CalendarContract.Calendars.OWNER_ACCOUNT
        };

        List<String> kalendare = new ArrayList<String>();
        try
        {
            String PODMINKA = CalendarContract.Calendars.IS_PRIMARY + " = ?";
            String[] PODMINKA_HODNOTY = new String[]{"1"};
            String RAZENI = CalendarContract.Calendars.DEFAULT_SORT_ORDER;

            ContentResolver contentResolver = this.getContentResolver();
            Cursor cursor = contentResolver.query(CalendarContract.Calendars.CONTENT_URI, SLOUPCE, PODMINKA, PODMINKA_HODNOTY, RAZENI);

            if(cursor.getCount() > 0)
            {
                while (cursor.moveToNext()) {
                    System.out.println(String.format("Id: %s, základní kalendář %s, viditelný %s, název: %s", cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4)));
                    kalendare.add( String.format("%s: %s", cursor.getString(0), cursor.getString(3)) );
                }
            }
            cursor.close();
        }
        catch(AssertionError ex)
        {
            ex.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        String seznamKalendaru = "";
        for (String kalendar : kalendare) {
            seznamKalendaru = seznamKalendaru.concat(kalendar).concat("\n");
        }

        TextView txtKalendar = (TextView) findViewById(R.id.txtKalendare);
        txtKalendar.setText(seznamKalendaru);
    }


    public void nacistUpozorneni(View view) {
        final String[] SLOUPCE = new String[] {
                CalendarContract.CalendarAlerts.EVENT_ID,
                CalendarContract.CalendarAlerts.BEGIN,
                CalendarContract.CalendarAlerts.TITLE,
                CalendarContract.CalendarAlerts.ORGANIZER
        };

        ContentResolver contentResolver = this.getContentResolver();
        Cursor cursor = contentResolver.query(CalendarContract.CalendarAlerts.CONTENT_URI, SLOUPCE, null, null, null);

        List<String> lstUpozorneni = new ArrayList<String>();

        // prochazeni dat, jejich vypis do console a ulozeni do seznamu
        while (cursor.moveToNext()) {

            long upozorneniID = cursor.getLong(0);
            long zacatek = cursor.getLong(1);
            String nazev = cursor.getString(2);
            String kalendar = cursor.getString(3);

            String calendarInfo = String.format("Upozorneni ID: %s\nZacatek: %s\nNazev: %s\nKalendar: %s", upozorneniID, zacatek, nazev, kalendar);
            System.out.println(calendarInfo);

            DateFormat df = new SimpleDateFormat("dd.MM. HH:mm");
            lstUpozorneni.add(String.format("[%s] %s %s", upozorneniID, df.format(zacatek), nazev));
        }

        cursor.close();

        String seznamUdalosti = "";
        for (String upozorneni : lstUpozorneni) {
            seznamUdalosti = seznamUdalosti.concat(upozorneni).concat("\n");
        }

        TextView txtUpozorneni = (TextView) findViewById(R.id.txtUpozorneni);
        txtUpozorneni.setText(seznamUdalosti);
    }

    int OPRAVNENI_ZADOST = 1;
    private void testOpravneni() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALENDAR}, OPRAVNENI_ZADOST);
        }
    }





    // tato metoda je zde jenom pro ilustraci, v prikladu neni pouzita
    public void nacistUdalosti(View view) {
        final String[] SLOUPCE = new String[] {
                CalendarContract.Instances.EVENT_ID,
                CalendarContract.Instances.BEGIN,
                CalendarContract.Instances.TITLE,
                CalendarContract.Instances.ORGANIZER
        };

        // Omezeni na rozsah vracenych zaznamu podle datumu od-do
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2021, 9, 23, 8, 0);
        long startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(2022, 1, 24, 8, 0);
        long endMillis = endTime.getTimeInMillis();

        Uri.Builder builder = CalendarContract.Instances.CONTENT_URI.buildUpon();
        ContentUris.appendId(builder, startMillis);
        ContentUris.appendId(builder, endMillis);

        /*
        // omezeni na konkretni kalendar
        String selection = CalendarContract.Instances.CALENDAR_ID + " = 3";
        String[] selectionArgs = new String[] {"207"};
        */

        Cursor cursor =  getContentResolver().query(builder.build(), SLOUPCE, null, null, null);

        List<String> lstUdalosti = new ArrayList<String>();

        // prochazeni dat, jejich vypis do console a ulozeni do seznamu
        while (cursor.moveToNext()) {

            long udalostID = cursor.getLong(0);
            long zacatek = cursor.getLong(1);
            String nazev = cursor.getString(2);
            String kalendar = cursor.getString(3);

            String calendarInfo = String.format("Udalost ID: %s\nZacatek: %s\nNazev: %s\nKalendar: %s", udalostID, zacatek, nazev, kalendar);
            System.out.println(calendarInfo);

            DateFormat df = new SimpleDateFormat("dd.MM. HH:mm");
            lstUdalosti.add(String.format("[%s] %s %s", udalostID, df.format(zacatek), nazev));
        }

        cursor.close();

        String seznamUdalosti = "";
        for (String udalost : lstUdalosti) {
            seznamUdalosti = seznamUdalosti.concat(udalost).concat("\n");
        }

        TextView txtUpozorneni = (TextView) findViewById(R.id.txtUpozorneni);
        txtUpozorneni.setText(seznamUdalosti);
    }

    // tato metoda je zde jenom pro ilustraci, v prikladu neni pouzita
    public void zjistitPrimarniKalendar() {

        final String[] EVENT_PROJECTION = new String[] {
                CalendarContract.Calendars._ID,
                CalendarContract.Calendars.ACCOUNT_NAME,
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
                CalendarContract.Calendars.OWNER_ACCOUNT
        };

        ContentResolver contentResolver = getContentResolver();
        String selection = CalendarContract.Calendars.VISIBLE + " = 1 AND "  + CalendarContract.Calendars.IS_PRIMARY + "=1";
        Cursor cur = contentResolver.query(CalendarContract.Calendars.CONTENT_URI, EVENT_PROJECTION, selection, null, null);

        ArrayList<String> calendarInfos = new ArrayList<>();
        while (cur.moveToNext()) {
            long calID = 0;
            String displayName = null;
            String accountName = null;
            String ownerName = null;

            // Get the field values
            calID = cur.getLong(0);
            displayName = cur.getString(1);
            accountName = cur.getString(1);
            ownerName = cur.getString(3);

            String calendarInfo = String.format("Calendar ID: %s\nDisplay Name: %s\nAccount Name: %s\nOwner Name: %s", calID, displayName, accountName, ownerName);
            System.out.println("Id: " + calendarInfo);
        }
    }
}