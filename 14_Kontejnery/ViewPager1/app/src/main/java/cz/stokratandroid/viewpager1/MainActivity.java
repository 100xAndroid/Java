package cz.stokratandroid.viewpager1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // nova instance adapteru
        PageAdapter mAdapter = new PageAdapter(this);

        // prirazeni adapteru k ViewPager
        ViewPager2 viewPager = (ViewPager2) findViewById(R.id.viewPager);
        viewPager.setAdapter(mAdapter);
    }
}
