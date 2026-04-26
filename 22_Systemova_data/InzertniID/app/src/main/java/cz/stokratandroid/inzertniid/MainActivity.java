package cz.stokratandroid.inzertniid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick (View view)
    {
        getAAID();
    }

    public void getAAID()
    {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                TextView txtAdvertisingId = (TextView) findViewById(R.id.txtAdvertisingId);
                try {
                    AdvertisingIdClient.Info adInfo = AdvertisingIdClient.getAdvertisingIdInfo(MainActivity.this);
                    String appId = adInfo != null ? adInfo.getId() : "Advertising ID nelze načíst.";
                    txtAdvertisingId.setText(appId);
                } catch (Exception e) {
                    txtAdvertisingId.setText("chyba");
                }
            }
        });
    }
}