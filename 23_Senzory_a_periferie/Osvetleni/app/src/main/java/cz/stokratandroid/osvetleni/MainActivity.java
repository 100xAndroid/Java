package cz.stokratandroid.osvetleni;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    SensorManager sensorManager;
    private TextView txtIntenzita, txtPresnost;

    @Override
    protected void onResume() {
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        Sensor senzorOsvetleni = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (senzorOsvetleni == null) {
            Toast.makeText(getApplicationContext(), "Senzor osvětlení není k dispozici.", Toast.LENGTH_LONG).show();
        }
        else {
            sensorManager.registerListener(this, senzorOsvetleni, SensorManager.SENSOR_DELAY_NORMAL);
        }

        txtIntenzita = (TextView) this.findViewById(R.id.txtIntenzita);
        txtPresnost = (TextView) this.findViewById(R.id.txtPresnost);
        super.onResume();
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_LIGHT){
            txtIntenzita.setText("Intenzita osvětlení: " + event.values[0]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        if(sensor.getType() == Sensor.TYPE_LIGHT){
            txtPresnost.setText("Přesnost: " + accuracy);
        }
    }

}
