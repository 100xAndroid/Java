package cz.stokratandroid.video3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Range;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private TextureView textureView;
    private CameraDevice kamera;
    private Size velikostObrazu;
    private CaptureRequest.Builder captureRequestBuilder;
    private CameraCaptureSession cameraSession;
    private Handler backgroundHandler;
    private HandlerThread handlerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textureView = (TextureView) this.findViewById(R.id.textureView);
    }

    TextureView.SurfaceTextureListener textureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            //open your camera here
            spustitKameru();
        }
        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            // Transform you image captured size according to the surface width and height
        }
        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }
        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        }
    };

    private void spustitKameru() {

        // test pristupu ke kamere
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Aplikace nemá  přístup ke kameře", Toast.LENGTH_LONG).show();
            // ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA_PERMISSIO);
            return;
        }

        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            // zjistit ID hlavni kamery
            String kameraId = manager.getCameraIdList()[0];
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(kameraId);
            StreamConfigurationMap vlastnostiKamery = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);

            // zjistit rozliseni kamery
            velikostObrazu = vlastnostiKamery.getOutputSizes(SurfaceTexture.class)[0];

            // spusteni kamery
            manager.openCamera(kameraId, stateCallback, null);

        } catch (CameraAccessException e) {
            e.printStackTrace();
            Toast.makeText(this, "Chyba: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private final CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice camera) {
            // udalost vyvolana pri spusteni kamery
            kamera = camera;
            kameraPreviewStart();
        }

        @Override
        public void onDisconnected(CameraDevice camera) {
            kamera.close();
        }

        @Override
        public void onError(CameraDevice camera, int error) {
            kamera.close();
            kamera = null;
            Toast.makeText(getApplicationContext(), String.format("Chyba kamery: %i", error), Toast.LENGTH_LONG).show();
        }
    };

    private void kameraPreviewStart() {
        try {
            SurfaceTexture texture = textureView.getSurfaceTexture();
            if (texture == null) {
                Toast.makeText(this, "Chyba, texture je null", Toast.LENGTH_LONG).show();
                return;
            }
            texture.setDefaultBufferSize(velikostObrazu.getWidth(), velikostObrazu.getHeight());

            Surface surface = new Surface(texture);
            captureRequestBuilder = kamera.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureRequestBuilder.addTarget(surface);

            kamera.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback(){
                @Override
                public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                    // kamera není pripravena
                    if (kamera == null) {
                        return;
                    }
                    // pokud je session pripravena, zahájít preview
                    cameraSession = cameraCaptureSession;
                    updatePreview();
                }
                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                    Toast.makeText(getApplicationContext(), "Chyba konfigurace", Toast.LENGTH_SHORT).show();
                }
            }, null);

        } catch (CameraAccessException e) {
            e.printStackTrace();
            Toast.makeText(this, "Chyba: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void updatePreview() {
        if (kamera == null) {
            return;
        }
        captureRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON);
        // -- dalsi mozna nastaveni --
        // captureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
        // captureRequestBuilder.set(CaptureRequest.CONTROL_VIDEO_STABILIZATION_MODE, CaptureRequest.CONTROL_VIDEO_STABILIZATION_MODE_OFF);
        // captureRequestBuilder.set(CaptureRequest.LENS_OPTICAL_STABILIZATION_MODE, CaptureRequest.LENS_OPTICAL_STABILIZATION_MODE_OFF);
        // captureRequestBuilder.set(CaptureRequest.LENS_FOCUS_DISTANCE, .2f);
        // captureRequestBuilder.set(CaptureRequest.SENSOR_EXPOSURE_TIME, 1000000L);
        // captureRequestBuilder.set(CaptureRequest.CONTROL_CAPTURE_INTENT, CaptureRequest.CONTROL_CAPTURE_INTENT_STILL_CAPTURE);
        // captureRequestBuilder.set(CaptureRequest.CONTROL_AE_EXPOSURE_COMPENSATION, CaptureRequest.CONTROL_AE_MODE_ON);
        // captureRequestBuilder.set(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER, CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER_CANCEL);
        // captureRequestBuilder.set(CaptureRequest.SENSOR_SENSITIVITY, 100);
        // captureRequestBuilder.set(CaptureRequest.CONTROL_AE_EXPOSURE_COMPENSATION, 1);

        captureRequestBuilder.set(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE, new Range(15,15));

        try {
            cameraSession.setRepeatingRequest(captureRequestBuilder.build(), null, backgroundHandler);
        } catch (CameraAccessException e){
            e.printStackTrace();
            Toast.makeText(this, "Chyba: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private Range<Integer> getRange() {
        CameraCharacteristics chars = null;
        try {
            CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            chars = manager.getCameraCharacteristics(manager.getCameraIdList()[0]);
            Range<Integer>[] ranges = chars.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES);
            Range<Integer> result = null;
            for (Range<Integer> range : ranges) {
                int upper = range.getUpper();
                // 10 - min range upper for my needs
                if (upper >= 10) {
                    if (result == null || upper < result.getUpper().intValue()) {
                        result = range;
                    }
                }
            }
            if (result == null) {
                result = ranges[0];
            }
            return result;
        } catch (CameraAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startBackgroundThread();

        if (textureView.isAvailable()) {
            spustitKameru();
        }
        else {
            textureView.setSurfaceTextureListener(textureListener);
        }
    }

    private void startBackgroundThread() {
        handlerThread = new HandlerThread("Camera background");
        handlerThread.start();

        backgroundHandler = new Handler(handlerThread.getLooper());
    }

    @Override
    protected void onPause() {
        stopBackgroundThread();
        super.onPause();
    }

    private void stopBackgroundThread() {
        handlerThread.quitSafely();
        try {
            handlerThread.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        backgroundHandler = null;
        handlerThread = null;
    }
}