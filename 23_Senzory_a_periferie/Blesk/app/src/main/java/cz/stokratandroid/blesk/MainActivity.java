package cz.stokratandroid.blesk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private CameraManager cameraManager;
    private String cameraId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // test, jestli zarizeni obsahuje blesk
        boolean jeBleskPodporovan = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (!jeBleskPodporovan) {
            Toast.makeText(getApplicationContext(), "Zařízení nepodporuje blesk.", Toast.LENGTH_LONG).show();
        }

        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        cameraId = idFotoaparatuSBleskem();

    }
    private String idFotoaparatuSBleskem () {
        String cameraId = "";
        /*
        // nejjednodussi zpusob - blesk ma kamera s id = 0
        return "0";
         */

        /*
        // ziskat id hlavni kamery
        try {
            cameraId = cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            Toast.makeText(getApplicationContext(), "Chyba: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        */

        // Najit fotoaparat s bleskem a zjistit jeho id.
        // Projit postupne vsechny fotoaparaty dostupne v zarizeni..
        try {
            String[] fotoaparaty = cameraManager.getCameraIdList();
            if (fotoaparaty.length == 0) {
                Toast.makeText(getApplicationContext(), "Zařízení neobsahuje fotoaparát.", Toast.LENGTH_LONG).show();
                return "";
            }

            for (String camId : fotoaparaty) {
                CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(camId);
                // zjistit, jestli ma blesk
                Boolean maBlesk = characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                // zjistit jestli je blesk vzadu, vepredu nebo jestli je externi
                Integer umisteniBlesku = characteristics.get(CameraCharacteristics.LENS_FACING);
                // pokud ma blesk, zapamatovat si jeho id
                if (maBlesk)
                    cameraId = camId;
            }
        }
        catch (CameraAccessException ex) {
            Toast.makeText(getApplicationContext(), "Chyba: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        if (cameraId.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Zařízení neobsahuje blesk.", Toast.LENGTH_LONG).show();
        }
        return cameraId;
    }

    // rozsvitit blesk fotoaparatu
    public void rozsvitit_click (View view) {
        try {
            cameraManager.setTorchMode(cameraId, true);
            Toast.makeText(getApplicationContext(), "Blesk je zapnutý", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Chyba: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // zhasnout blesk fotoaparatu
    public void zhasnout_click (View view) {
        try {
            cameraManager.setTorchMode(cameraId, false);
            Toast.makeText(getApplicationContext(), "Blesk je vypnutý", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Chyba: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}