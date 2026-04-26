package cz.stokratandroid.explicitniintent;

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

    // spusteni aktivity Activity2
    public void intent1 (View view) {
        Intent intent = new Intent(this, Activity2.class);
        startActivity(intent);
    }

    // predani parametru a spusteni aktivity Activity3
    public void intent2 (View view) {
        Intent intent = new Intent(this, Activity3.class);
        intent.putExtra("param1", "test");
        intent.putExtra("param2", 10);
        startActivity(intent);
    }
}