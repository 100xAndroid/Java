package cz.stokratandroid.expandablelistview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ExpandableListView expandbleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // nacist data do komponenty ExpandableListView
        setGroupData();
        setpolozkaGroupData();

        // ziskat odkaz na ExpandableListView
        expandbleList = (ExpandableListView)findViewById(R.id.expListViewNastaveniAndroidu);
        // zrusit oddelovac polozek, pouzijeme vlastni
        expandbleList.setDividerHeight(0);

        // vytvorit adapter a predat mu vstupni data
        PolozkaAdapter mNewAdapter = new PolozkaAdapter(MainActivity.this, skupiny, polozky);
        expandbleList.setAdapter(mNewAdapter);

        // listener kliknuti na polozku
        expandbleList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int polozkaPosition, long id) {
                ArrayList<String> aktualniPolozka = (ArrayList<String>) polozky.get(groupPosition);

                Toast.makeText(getBaseContext(), skupiny.get(groupPosition) + " " + aktualniPolozka.get(polozkaPosition), Toast.LENGTH_SHORT).show();
                return false;

            }
        });

    }

    // list se seznamem skupin
    ArrayList<String> skupiny = new ArrayList<String>();
    public void setGroupData() {
        skupiny.add("Ice Cream Sandwich");
        skupiny.add("Jelly Bean");
        skupiny.add("KitKat");
        skupiny.add("Lollipop");
        skupiny.add("Marshmallow");
        skupiny.add("Nougat");
        skupiny.add("Oreo");
        skupiny.add("Pie");
        skupiny.add("10");
        skupiny.add("11");
        skupiny.add("12");
        skupiny.add("13");
        skupiny.add("14");
    }

    // list se seznamem objektu, kde kazdym objektem je seznam polozek
    ArrayList<Object> polozky = new ArrayList<Object>();
    public void setpolozkaGroupData() {

        // Ice Cream Sandvwich
        ArrayList<String> polozka = new ArrayList<String>();
        polozka.add("4.0");
        polozka.add("4.0.1");
        polozka.add("4.0.2");
        polozky.add(polozka);

        // Jelly Bean
        polozka = new ArrayList<String>();
        polozka.add("4.1");
        polozka.add("4.1.1");
        polozka.add("4.1.2");
        polozka.add("4.2.1");
        polozka.add("4.2.1");
        polozka.add("4.2.2");
        polozka.add("4.3");
        polozka.add("4.3.1");
        polozky.add(polozka);

        // KitKat
        polozka = new ArrayList<String>();
        polozka.add("4.4");
        polozka.add("4.4.1");
        polozka.add("4.4.2");
        polozka.add("4.4.3");
        polozka.add("4.4.4");
        polozky.add(polozka);

        // Lollipop
        polozka = new ArrayList<String>();
        polozka.add("5.0");
        polozka.add("5.0.1");
        polozka.add("5.0.2");
        polozka.add("5.1");
        polozka.add("5.1.1");
        polozky.add(polozka);

        // Marshmallow
        polozka = new ArrayList<String>();
        polozka.add("6.0");
        polozka.add("6.0.1");
        polozky.add(polozka);

        // Nougat
        polozka = new ArrayList<String>();
        polozka.add("7.0");
        polozka.add("7.1");
        polozka.add("7.1.1");
        polozka.add("7.1.2");
        polozky.add(polozka);

        // Oreo
        polozka = new ArrayList<String>();
        polozka.add("8.0");
        polozka.add("8.1");
        polozky.add(polozka);

        // Pie
        polozka = new ArrayList<String>();
        polozka.add("9.0");
        polozky.add(polozka);

        // 10
        polozka = new ArrayList<String>();
        polozka.add("10");
        polozky.add(polozka);

        // 11
        polozka = new ArrayList<String>();
        polozka.add("11");
        polozky.add(polozka);

        // 12
        polozka = new ArrayList<String>();
        polozka.add("12");
        polozky.add(polozka);

        // 13
        polozka = new ArrayList<String>();
        polozka.add("13");
        polozky.add(polozka);

        // 14
        polozka = new ArrayList<String>();
        polozka.add("14");
        polozky.add(polozka);

    }

}
