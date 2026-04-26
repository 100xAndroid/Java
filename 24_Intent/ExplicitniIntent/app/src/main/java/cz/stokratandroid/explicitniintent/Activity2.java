package cz.stokratandroid.explicitniintent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class Activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);
    }

    // zavrit aktivitu a vratit se na predchozi
    public void zpet (View view) {
        finish();
    }
}