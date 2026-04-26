package cz.stokratandroid.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // vytvorit kanal oznameni
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notifKanal =
                    new NotificationChannel("NTF01", "Notifikace", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.createNotificationChannel(notifKanal);
        }
    }

    // vytvorit a zobrazit oznameni
    public void OznameniOtevrit_onclick(View view) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "NTF01");
        builder.setContentTitle("Toto je notifikace");
        builder.setContentText("Textový popis notifikace.");
        builder.setSmallIcon(R.drawable.tlapka);
        builder.setAutoCancel(true);

        NotificationManagerCompat manag = NotificationManagerCompat.from(this);
        manag.notify(1, builder.build());
    }

    // zavrit oznameni
    public void OznameniZavrit_onclick(View view) {
        NotificationManagerCompat manag = NotificationManagerCompat.from(this);
        manag.cancel(1);
    }

}