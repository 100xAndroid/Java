package cz.stokratandroid.video1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.Surface;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        povoleniPristupuKExternimSouborum();
    }

    private MediaRecorder kamera;

    // metoda pozada o pravo pristupu k externim souborum
    private void povoleniPristupuKExternimSouborum() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()){
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        }
    }

    private void inicializaceKamery() {

        // nova instance MediaRecorderu
        kamera = new MediaRecorder();
        kamera.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        kamera.setVideoSource(MediaRecorder.VideoSource.CAMERA);

        // nova instance bufferu
        SurfaceTexture sft = new SurfaceTexture(0);
        Surface sf = new Surface(sft);
        kamera.setPreviewDisplay(sf);

        // nastaveni profilu kamery
        CamcorderProfile cpHigh = CamcorderProfile.get(0, CamcorderProfile.QUALITY_HIGH);
        kamera.setProfile(cpHigh);

        // soubor s ulozenymi daty
        kamera.setOutputFile(Environment.getExternalStorageDirectory().getPath()+"/Movies/test.mp4");
    }

    public void kameraNahravani (View sender) {
        if (kamera == null) {
            try {
                inicializaceKamery();
                kamera.prepare();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Chyba inicializace: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            try {
                kamera.start();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Chyba při startu záznamu: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            //zmenit text tlacitka
            Button tlacitkoNahravani = (Button)this.findViewById(R.id.butNahravani);
            tlacitkoNahravani.setText("Konec nahrávání");
        }
        else {
            kamera.stop();
            kamera = null;

            //zmenit text tlacitka
            Button tlacitkoNahravani = (Button)this.findViewById(R.id.butNahravani);
            tlacitkoNahravani.setText("Nahrávání kamerou");
        }
    }

    private VideoView prehravac;


    // zahajit prehravani
    public void prehravacPrehrat (View sender) {
        prehravac = (VideoView)findViewById(R.id.videoView);

        Uri souborUmisteni = Uri.parse(Environment.getExternalStorageDirectory().getPath()+ "/Movies/test.mp4");
        prehravac.setVideoURI(souborUmisteni);
        prehravac.requestFocus();
        prehravac.start();
    }

    // prerusit/obnovit prehravani
    public void prehravacPauza (View sender) {
        if (prehravac == null)
            return;

        if (prehravac.isPlaying()) {
            prehravac.pause();
        } else {
            prehravac.start();
        }
    }

    // ukoncit prehravani
    public void prehravacStop (View sender) {
        if (prehravac == null)
            return;

        prehravac.stopPlayback();
        prehravac = null;
    }
}