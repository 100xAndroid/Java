package cz.stokratandroid.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializacePosuvniku();
    }

    private Ringtone vyzvaneni;
    // prehraje zvuk vyzvaneni
    public void prehratZvuk1(View sender) {
        if (vyzvaneni == null) {
            Uri vyzvaneniUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            vyzvaneni = RingtoneManager.getRingtone(getApplicationContext(), vyzvaneniUri);
            vyzvaneni.play();
        }
        else {
            vyzvaneni.stop();
            vyzvaneni = null;
        }
    }

    // prehraje zvuk klinuti
    public void prehratZvuk2 (View sender) {

        // spustime prehravani zvuku
        MediaPlayer prehravac = MediaPlayer.create(this, R.raw.kliknuti);
        prehravac.start();

        Toast.makeText(getApplicationContext(), "Stisknuto tlačítko", Toast.LENGTH_SHORT).show();
    }

    // prehraje zvuk
    public void prehratZvuk3(View sender) {

        // spustime prehravani zvuku
        MediaPlayer prehravac = MediaPlayer.create(this, R.raw.zvuk);
        prehravac.start();

        // nastavime listener pro konec prehravani
        prehravac.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(final MediaPlayer mp) {
                Toast.makeText(getApplicationContext(), "Konec přehrávání", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private MediaPlayer prehravac;
    private SeekBar posuvnik;
    private Handler handler = new Handler();

    // nastaveni listeneru posuvniku
    // jakmile uzivatel posune ukazatelem, upravime pozici v prehravane skladbe
    private void inicializacePosuvniku() {
        // najdeme posuvnik ve formulari
        posuvnik = (SeekBar) this.findViewById(R.id.seekBar);
        // vytvorime listener ktery ceka na zmenu polohy ukazatele
        posuvnik.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int pozice, boolean odUzivatele) {
                if (prehravac == null)
                    return;
                if(odUzivatele)
                    prehravac.seekTo(pozice);
            }
        });
    }

    // spusti prehravani
    public void prehravacHrat(View sender) {
        if (prehravac != null)
            prehravac.reset();

        // nastavit zvukovy soubor a spustit prehravac
        prehravac = MediaPlayer.create(this, R.raw.android_latest_2013);
        prehravac.start();

        // iniciace posuvniku
        SeekBar posuvnik = (SeekBar) this.findViewById(R.id.seekBar);
        posuvnik.setMax(prehravac.getDuration());
        // Toast.makeText(getApplicationContext(), "Delka skladby: " + prehravac.getDuration(), Toast.LENGTH_SHORT).show();

        MainActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                nastavitPosuvnik();
                handler.postDelayed(this, 100);
            }
        });
    }

    // nastavi polohu posuvniku podle pozice v prehravane skladbe
    private void nastavitPosuvnik() {
        if(prehravac != null)
            posuvnik.setProgress(prehravac.getCurrentPosition());
        else
            posuvnik.setProgress(0);
    }

    // prerusi/obnovi prehravani
    public void prehravacPauza(View sender) {
        if (prehravac == null)
            return;

        if (prehravac.isPlaying()) {
            prehravac.pause();
        } else {
            prehravac.start();
        }
    }

    // ukonci prehravani
    public void prehravacStop(View sender) {
        if (prehravac == null)
            return;

        prehravac.reset();
        prehravac = null;
    }
}