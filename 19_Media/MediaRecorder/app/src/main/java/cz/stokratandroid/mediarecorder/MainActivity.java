package cz.stokratandroid.mediarecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tlacitkoNahravani = (Button)this.findViewById(R.id.button1);
        inicializacePosuvniku();
    }
    private MediaRecorder zaznamnik;
    private Button tlacitkoNahravani;

    // nahrat zvuk z mikrofonu
    public void zaznamZvuku(View sender) {

        if (zaznamnik == null) {
            // zahajit zaznam zvuku z mikrofonu
            zaznamnik = new MediaRecorder();
            zaznamnik.setAudioSource(MediaRecorder.AudioSource.MIC);
            zaznamnik.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            zaznamnik.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            zaznamnik.setOutputFile("/sdcard/Music/test.amr");
            try {
                zaznamnik.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            zaznamnik.start();

            //zmenit text tlacitka
            tlacitkoNahravani.setText("Konec nahrávání");
        }
        else {
            // ukoncit zaznam zvuku z mikrofonu
            zaznamnik.stop();
            zaznamnik = null;
            //zmenit text tlacitka
            tlacitkoNahravani.setText("Záznam zvuku");

            // informace o ukonceni nahravani
            Toast.makeText(getApplicationContext(), "Konec záznamu.", Toast.LENGTH_SHORT).show();
        }
    }

    private MediaPlayer prehravac;
    private SeekBar posuvnik;
    private Handler mHandler = new Handler();

    // nastaveni listeneru posuvniku
    // jakmile uzivatel posune ukazatelem, upravit pozici v prehravane skladbe
    private void inicializacePosuvniku() {
        // najit posuvnik ve formulari
        posuvnik = (SeekBar) this.findViewById(R.id.seekBar);
        // vytvorit listener ktery ceka na zmenu polohy ukazatele
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

    // spustit prehravani
    public void prehravacHrat(View sender) {
        if (prehravac != null)
            prehravac.reset();

        // umistenni zvukoveho souboru
        Uri souborUmisteni = Uri.parse(Environment.getExternalStorageDirectory().getPath()+ "/Music/test.amr");

        // test, jestli existuje zvukovy soubor
        File zvukovySoubor = new File(souborUmisteni.getPath());
        if(!zvukovySoubor.exists())
            return;

        // nastavit zvukovy soubor a spustit prehravac
        prehravac = MediaPlayer.create(this, souborUmisteni);
        prehravac.start();

        // iniciace posuvniku
        SeekBar posuvnik = (SeekBar) this.findViewById(R.id.seekBar);
        posuvnik.setMax(prehravac.getDuration());
        // Toast.makeText(getApplicationContext(), "Delka skladby: " + prehravac.getDuration(), Toast.LENGTH_SHORT).show();

        MainActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                nastavitPosuvnik();
                mHandler.postDelayed(this, 100);
            }
        });
    }

    // nastavit polohu posuvniku podle pozice v prehravane skladbe
    private void nastavitPosuvnik() {
        if(prehravac != null)
            posuvnik.setProgress(prehravac.getCurrentPosition());
        else
            posuvnik.setProgress(0);
    }

    // prerusit/obnovit prehravani
    public void prehravacPauza(View sender) {
        if (prehravac == null)
            return;

        if (prehravac.isPlaying()) {
            prehravac.pause();
        } else {
            prehravac.start();
        }
    }

    // ukoncit prehravani
    public void prehravacStop(View sender) {
        if (prehravac == null)
            return;

        prehravac.reset();
        prehravac = null;
    }
}