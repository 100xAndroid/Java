package cz.stokratandroid.video2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.CamcorderProfile;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements TextureView.SurfaceTextureListener {

    private MediaRecorder mediaRecorder;
    private MediaPlayer prehravac;
    private Surface sface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        povoleniPristupuKExternimSouborum();

        TextureView textureView = (TextureView) findViewById(R.id.textureView);
        textureView.setSurfaceTextureListener(this);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        sface = new Surface(surface);
        inicializacePrehravace ();
    }

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

    private void inicializacePrehravace () {
        // kontrola souboru s ulozenym videem
        File file = new File(Environment.getExternalStorageDirectory().getPath()+"/Movies/test.mp4");
        if(!file.exists()) {
            Toast.makeText(this, "Soubor s videem neexistuje", Toast.LENGTH_SHORT).show();
            return;
        }

        // instance prehravace a jeho spusteni
        try {
            prehravac = new MediaPlayer();
            prehravac.setDataSource(Environment.getExternalStorageDirectory().getPath()+"/Movies/test.mp4");
            prehravac.setSurface(sface);
            prehravac.prepareAsync();
          /*  prehravac.setOnBufferingUpdateListener(this);
            prehravac.setOnCompletionListener(this);
            prehravac.setOnPreparedListener(this);
            prehravac.setOnVideoSizeChangedListener(this);*/
            prehravac.setAudioStreamType(AudioManager.STREAM_MUSIC);

            prehravac.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    prehravac.start();
                    // prehravac.setLooping(true);
                }
            });

            //   prehravac.start();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    private void inicializaceKamery() {

        // nova instance MediaRecorderu + jeho nastaveni
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mediaRecorder.setOrientationHint(90);

        // nova instance bufferu
        SurfaceTexture sft = new SurfaceTexture(0);
        Surface sf = new Surface(sft);
        mediaRecorder.setPreviewDisplay(sf);

        // nastaveni profilu kamery
        CamcorderProfile cpHigh = CamcorderProfile.get(0, CamcorderProfile.QUALITY_HIGH);
        mediaRecorder.setProfile(cpHigh);

        // soubor s ulozenymi daty
        mediaRecorder.setOutputFile(Environment.getExternalStorageDirectory().getPath()+"/Movies/test.mp4");
    }

    // zahaji/ukonci zaznam videa
    public void kameraNahravani (View sender) {
        // pokud bezi prehravani videa, zastavime ho
        if (prehravac != null) {
            prehravac.release();
            prehravac = null;
        }

        Button butNahravani = (Button) findViewById(R.id.butNahravani);
        // spusteni nahravani
        if (mediaRecorder == null) {
            try {
                inicializaceKamery();
                mediaRecorder.prepare();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Chyba inicializace: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            try {
                mediaRecorder.start();
                butNahravani.setText("Konec nahrávání");
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Chyba při startu záznamu: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        else {
            mediaRecorder.stop();
            mediaRecorder = null;
            butNahravani.setText("Nahrávání kamerou");
        }
    }

    // zahaji prehravani
    public void prehravacPrehrat (View sender) {
        if (prehravac != null) {
            prehravac.release();
            prehravac = null;
        }
        inicializacePrehravace();
    }

    // prerusi/obnovi prehravani
    public void prehravacPauza (View sender) {

        if (prehravac == null)
            return;

        if (prehravac.isPlaying()) {
            prehravac.pause();
        } else {
            prehravac.start();
        }
    }

    // ukonci prehravani
    public void prehravacStop (View sender) {
        if (prehravac == null)
            return;

        prehravac.stop();
        prehravac.release();
        prehravac = null;
    }
}