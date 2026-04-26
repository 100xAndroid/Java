package cz.stokratandroid.telefonniseznam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testOpravneni(42, Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS);

        nacistKontakty(null);
    }

    public void nacistKontakty(View view) {
        final String[] SLOUPCE_KONTAKT = new String[] {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };

        final String[] SLOUPCE_TEL = new String[] {
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };

        List<String> kontakty = new ArrayList<String>();

        ContentResolver cr = getContentResolver();
        Cursor cur1 = cr.query(ContactsContract.Contacts.CONTENT_URI, SLOUPCE_KONTAKT, null, null, null);

        while (cur1.moveToNext()) {
            String id = cur1.getString(0);
            String jmeno = cur1.getString(1);
            System.out.println("Jmeno : " + jmeno + ", ID : " + id);

            // nacteni kontaktnich udaju (tel. cisel)
            Cursor cur2 = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    SLOUPCE_TEL,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                    new String[]{id}, null);

            // prochazeni nalezenych kontaktnich udaju (tel. cisel)
            while (cur2.moveToNext()) {
                String phone = cur2.getString(0);
                kontakty.add(String.format("[%s] %s %s", id, jmeno, phone));
            }
            cur2.close();
        }

        String sKontakty = "";
        for (String kontakt : kontakty) {
            sKontakty = sKontakty.concat(kontakt).concat("\n");
        }

        TextView txtKontakty = (TextView) findViewById(R.id.txtKontakty);
        txtKontakty.setText(sKontakty);

        cur1.close();
    }


    private void testOpravneni(int callbackId, String... permissionsId) {
        boolean permissions = true;
        for (String p : permissionsId) {
            permissions = permissions && ContextCompat.checkSelfPermission(this, p) == PackageManager.PERMISSION_GRANTED;
        }

        if (!permissions)
            ActivityCompat.requestPermissions(this, permissionsId, callbackId);
    }

}