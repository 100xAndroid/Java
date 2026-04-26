package cz.stokratandroid.vlakno;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Thread vlakno = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void spustitVlakno(View view) {
        if (vlakno != null) {
            Toast.makeText(this, "Vlákno je již spuštěno", Toast.LENGTH_SHORT).show();
            return;
        }

        vlakno = new Thread(new Runnable() {
            @Override
            public void run() {

                MediaPlayer prehravac = MediaPlayer.create(getApplicationContext(), R.raw.pipnuti);

                try {
                    for (int i = 0; i < 100; i++) {

                        // spustit prehravani zvuku
                        if (prehravac.isPlaying())
                            prehravac.stop();
                        prehravac.start();

                        // zapisat informaci do logu
                        Log.i("Vlakno", "Vlakno bezi, krok " + i);

                        // zastavit vlakno na 1 sekundu
                        Thread.sleep(1000);
                    }
                }
                catch (Exception e) {
                    Log.i("Sluzba test", "Vyjimka: " + e.getMessage());
                }
            }
        });
        vlakno.start();
    }

    public void ukoncitVlakno(View view) {
        if (vlakno != null) {
            vlakno.interrupt();
            vlakno = null;
        }
    }

}