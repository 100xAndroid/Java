package cz.stokratandroid.textovezpravy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testOpravneni(42, Manifest.permission.READ_SMS);
    }

    public void nacistSMS(View view) {
        final String[] SLOUPCE = new String[] {
                "_id",
                "address",
                "body"
        };


        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(Uri.parse("content://sms/inbox"), SLOUPCE, null, null, null);

        List<String> zpravySMS = new ArrayList<String>();

        while (cur.moveToNext()) {
            zpravySMS.add( String.format("[%s] %s %s", cur.getString(0), cur.getString(1), cur.getString(2) ));
        }
        cur.close();

        String sSMS = "";
        for (String zpravaSMS : zpravySMS) {
            sSMS = sSMS.concat(zpravaSMS).concat("\n");
        }

        TextView txtSMS = (TextView) findViewById(R.id.txtSMS);
        txtSMS.setText(sSMS);
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