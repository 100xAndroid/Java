package cz.stokratandroid.fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // ziskat spravce pro praci s fragmenty
        FragmentManager manager = getSupportFragmentManager();

        // vytvorit instanci fragmentu Fragment1
        Fragment1 f1 = new Fragment1();
        // v transakci priradit fragment do prvniho constraint layoutu
        manager.beginTransaction()
                .replace(R.id.firstLayout, f1, f1.getTag())
                .commit();

        // vytvorit instanci fragmentu Fragment2
        Fragment2 f2 = new Fragment2();
        // v transakci priradit fragment do druheho constraint layoutu
        manager.beginTransaction()
                .replace(R.id.secondLayout, f2, f2.getTag())
                .commit();

    }
}