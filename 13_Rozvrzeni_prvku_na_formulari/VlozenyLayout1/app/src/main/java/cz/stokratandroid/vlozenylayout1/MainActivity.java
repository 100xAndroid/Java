package cz.stokratandroid.vlozenylayout1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import cz.stokratandroid.vlozenylayout1.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick (View view) {
        Toast.makeText(view.getContext(), "Kliknuto na tlačítko", Toast.LENGTH_SHORT).show();
    }
}