package cz.stokratandroid.tablayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // nova instance adapteru
        TabAdapter mAdapter = new TabAdapter(this);

        // najit na formulari TabLayout a ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        ViewPager2 viewPager = (ViewPager2) findViewById(R.id.viewPager);

        // propojeni ViewPager s adapterem;
        viewPager.setAdapter(mAdapter);

        // nazvy zalozek
        final String[] zalozky = new String[]{"Android", "Verze", "API"};

        // propojeni TabLayoutu s ViewPager
        new TabLayoutMediator(tabLayout, viewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override public void onConfigureTab(TabLayout.Tab tab, int position) {
                        tab.setText(zalozky[position]);
                    }
                }).attach();
    }
}
