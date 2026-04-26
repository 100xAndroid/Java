package cz.stokratandroid.casovac;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private CountDownTimer casovac = null;

    public void spustitCasovac(View view) {
        if (casovac != null) {
            Toast.makeText(this, "Časovač je již spuštěn", Toast.LENGTH_SHORT).show();
            return;
        }

        casovac =  new CountDownTimer(60000, 1000) {
            MediaPlayer prehravac = MediaPlayer.create(getApplicationContext(), R.raw.pipnuti);

            public void onTick(long millisUntilFinished) {
                // spustime prehravani zvuku
                if (prehravac.isPlaying())
                    prehravac.stop();
                prehravac.start();

                // zapiseme informaci do logu
                Log.i("Casovac", "Vyvolana udalost onTick");
            }

            public void onFinish() {
                // konec
            }
        };

        casovac.start();
    }

    public void ukoncitCasovac(View view) {
        if (casovac != null) {
            casovac.cancel();
            casovac = null;
        }
    }

}