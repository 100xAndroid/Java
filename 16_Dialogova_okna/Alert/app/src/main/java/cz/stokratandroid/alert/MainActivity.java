package cz.stokratandroid.alert;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // zakladni alert dialog s jednim tlacitkem
    public void alertDialog01(View sender) {
        // vytvorit instanci tridy AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // nastaveni parametru dialogu
        builder.setTitle("Záhlaví");
        builder.setMessage("Test - alert dialog");
        builder.setPositiveButton("Zavřít", null);

        // zobrazeni dialogu
        builder.create().show();
    }

    // dialog vyvola rutinu pri kliknuti na tlacitko
    public void alertDialog02(View sender) {
        // vytvorit instanci tridy AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // nastaveni parametru dialogu
        builder.setTitle("Záhlaví");
        builder.setMessage("Test - alert dialog");
        // definujeme listener pro tlačítko
        DialogInterface.OnClickListener butListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getApplicationContext(), "Stisknuto tlačítko Zavřít", Toast.LENGTH_LONG).show();
            }
        };
        builder.setPositiveButton("Zavřít", butListener);
        // zobrazit dialog
        builder.create().show();
    }

    // dialog zobrazi uzivatelsky formular
    public void alertDialog03(View sender) {
        // vytvorit instanci tridy AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // serializace formular
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.uzivatelsky_alert, null);

        // nastaveni parametru dialogu a tlacitka
        builder.setTitle("Záhlaví");
        builder.setMessage("Test - alert dialog");
        builder.setPositiveButton("OK", null);
        builder.setNegativeButton("Zrušit", null);
        builder.setNeutralButton("Nic", null);
        // vlozit do dialogu pripraveny formular
        builder.setView(alertLayout);

        // zobrazit dialog
        builder.create().show();
    }

    // dialog zobrazi seznam polozek
    public void alertDialog10(View sender) {
        // pole nazvu polozek seznamu
        String [] poleNazvu = { "Volba 1", "Volba 2", "Volba 3", "Volba 4" };

        // vytvorit instanci tridy AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // nastaviit zahlavi dialogu
        builder.setTitle("Záhlaví");

        // nastavit seznam a listener pro kliknuti
        builder.setItems(poleNazvu, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int volba) {
                Toast.makeText(getApplicationContext(), "Zvolena možnost č. " + volba, Toast.LENGTH_LONG).show();
            }
        });

        // zobrazit dialog
        builder.create().show();
    }

    // pole, do ktereho se budou ukladat vybrane polozky
    ArrayList<Integer> zvolenePolozky = new ArrayList<Integer>();

    // dialog zobrazi zaskrtavaci seznam polozek
    public void alertDialog11(View sender) {
        // pole nazvu seznamu
        String [] poleNazvu = { "Volba 1", "Volba 2", "Volba 3", "Volba 4" };

        // vytvorit instanci tridy AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // nastavit zahlavi dialogu
        builder.setTitle("Záhlaví");

        // nastavit seznam a listener pro kliknuti
        builder.setMultiChoiceItems(poleNazvu, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int volba, boolean zaskrtnuto) {
                if (zaskrtnuto) {
                    // pri zaskrtnuti pridat do seznamu
                    zvolenePolozky.add(volba);
                } else if (zvolenePolozky.contains(volba)) {
                    // pri odskrtnuti vyradit ze seznamu
                    zvolenePolozky.remove(Integer.valueOf(volba));
                }
            }
        });

        // listener pro tlačítko
        DialogInterface.OnClickListener butListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getApplicationContext(), "Zvolené položky: " + zvolenePolozky.toString(), Toast.LENGTH_LONG).show();
            }
        };
        builder.setPositiveButton("Hotovo", butListener);

        // zobrazit dialog
        builder.create().show();

    }

    // dialog nastaveni data
    public void alertDialog20(View sender) {

        // nastavit datum, napr. 1.1.2040
        int iRok = 2040;
        int iMesic = 0; // pozor, mesice se v Java kalendari cisluji od 0
        int iDen = 1;

        // definujeme listener pro zvolene datum v dialogu
        DatePickerDialog.OnDateSetListener datumListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int rok, int mesic, int den) {
                String strVysledek = String.format("Zvolené datum %d.%d.%d", den, mesic, rok);
                Toast.makeText(getApplicationContext(), strVysledek, Toast.LENGTH_LONG).show();
            }
        };

        // vytvorit instanci dialogu
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, datumListener, iRok, iMesic, iDen);
        // zobrazit dialog
        datePickerDialog.show();
    }

    // dialog nastaveni casu
    public void alertDialog21(View sender) {

        // ziskat aktualni cas
        final Calendar kalendar = Calendar.getInstance();
        int iHodina = kalendar.get(Calendar.HOUR_OF_DAY);
        int iMinuta = kalendar.get(Calendar.MINUTE);

        // listener pro zvoleny cas v dialogu
        TimePickerDialog.OnTimeSetListener casListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hod, int min) {
                String strVysledek = String.format("Zvolený čas %d:%d", hod, min);
                Toast.makeText(getApplicationContext(), strVysledek, Toast.LENGTH_LONG).show();
            }
        };

        // vytvorit instanci dialogu
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, casListener, iHodina, iMinuta, true);
        // zobrazit dialog
        timePickerDialog.show();
    }

    // dialog nastaveni data v ruznych barevnych tematech
    public void alertDialogTema1(View sender) {

        int iTema = android.R.style.Theme_DeviceDefault_Dialog_Alert;

        int butId = sender.getId();
        if (butId == R.id.button30)
            iTema = android.R.style.Theme_DeviceDefault_Dialog_Alert;
        else if (butId == R.id.button31)
            iTema = android.R.style.Theme_DeviceDefault_Light_Dialog_Alert;
        else if (butId == R.id.button32)
            iTema = android.R.style.Theme_Holo_Light_Panel;
        else if (butId == R.id.button40)
            iTema = R.style.datepicker;

        // nastavit datum, napr. 1.1.2040
        int iRok = 2040;
        int iMesic = 0;  // pozor, mesice se v Java kalendari cisluji od 0
        int iDen = 1;

        // vytvorit instanci dialogu
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, iTema, null, iRok, iMesic, iDen);
        // zobrazt dialog
        datePickerDialog.show();
    }
}