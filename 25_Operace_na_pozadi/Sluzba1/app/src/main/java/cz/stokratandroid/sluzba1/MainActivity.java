package cz.stokratandroid.sluzba1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void spustitSluzbu (View view) {
        Intent intent = new Intent(this, Sluzba.class);
        startService(intent);
    }

    public void zastavitSluzbu (View view) {
        Intent intent = new Intent(this, Sluzba.class);
        stopService(intent);
    }

}