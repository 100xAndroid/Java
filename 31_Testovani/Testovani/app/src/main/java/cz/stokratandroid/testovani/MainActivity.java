package cz.stokratandroid.testovani;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public static int testovaciMetoda (int param1, int param2) {
        int verze = android.os.Build.VERSION.SDK_INT;
        int soucet = param1 + param2;
        return soucet;
    }

    public static int testovaciMetoda2 () {
        int verze = android.os.Build.VERSION.SDK_INT;
        return verze;
    }
}