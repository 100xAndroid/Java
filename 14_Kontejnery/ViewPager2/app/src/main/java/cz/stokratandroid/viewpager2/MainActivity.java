package cz.stokratandroid.viewpager2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // vstupní data
        // vzdy tri texty oddelene dvojteckou, adapter si je rozdeli
        String [] arrVerzeAndroidu = {
                "Android 4.0:Ice Cream Sandwich:Aktualizace 4.0.1, 4.0.2, 4.0.3 a 4.0.4\nAPI verze 14, 15",
                "Android 4.1 - 4.3:Jelly Bean:Aktualizace 4.1.1, 4.1.2, 4.2.1, 4.2.2 a 4.3.1\nAPI verze 16-18",
                "Android 4.4:KitKat:Aktualizace 4.4.1, 4.4.2, 4.4.3, 4.4.4, 4.4W, 4.4W.1 a 4.4W.2\nAPI verze 19, 20",
                "Android 5.0:Lollipop:Aktualizace 5.0.1, 5.0.2 a 5.1.1\nAPI verze 21, 22",
                "Android 6.0:Marshmallow:Aktualizace 6.0.1\nAPI verze 23",
                "Android 7.0:Nougat:Aktualizace 7.1.1 a 7.1.2\nAPI verze 24, 25",
                "Android 8.0 – 8.1:Oreo:Bez aktualizací\nAPI verze 26.27",
                "Android 9.0:Pie:Bez aktualizací\nAPI verze 28",
                "Android 10:Android 10:Bez aktualizací\nAPI verze 29"
        };

        // nova instance adapteru
        PageAdapter mAdapter = new PageAdapter(this, arrVerzeAndroidu);

        // prirazeni adapteru k ViewPager
        ViewPager2 viewPager = (ViewPager2) findViewById(R.id.viewPager);
        viewPager.setAdapter(mAdapter);

        // viewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        // viewPager.setPageTransformer(new MarginPageTransformer(500));

        // nastaveni transformace stranky
        viewPager.setPageTransformer(new PageTransformer());
    }

}