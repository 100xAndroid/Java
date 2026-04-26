package cz.stokratandroid.viewpager2;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Stranka#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Stranka extends Fragment {

    public Stranka() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stranka, container, false);

        // nacteni a prirazeni vstupnich dat do pomocnych promennych
        String strLogo = getArguments().getString("logo");
        String strNazev = getArguments().getString("nazev");
        String strVerze = getArguments().getString("verze");
        String strData = getArguments().getString("data");

        // odkazy na objekty formulare
        ImageView imgLogo = view.findViewById(R.id.imageView);
        TextView txtNazev = view.findViewById(R.id.textView1);
        TextView txtVerze = view.findViewById(R.id.textView2);
        TextView txtData = view.findViewById(R.id.textView3);

        // zpristupneni resources
        Resources res = getContext().getResources();

        // id resource s logem androidu
        int imgLogoId = res.getIdentifier(strLogo, "drawable",getContext().getPackageName());

        // prirazeni loga do ImageView
        imgLogo.setImageResource(imgLogoId);

        // zapis textovych vstupnich dat na formular
        txtNazev.setText(strNazev);
        txtVerze.setText(strVerze);
        txtData.setText(strData);

        return view;
    }
}