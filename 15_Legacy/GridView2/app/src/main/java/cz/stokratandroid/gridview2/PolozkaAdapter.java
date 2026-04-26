package cz.stokratandroid.gridview2;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PolozkaAdapter  extends ArrayAdapter<String> {

    // lokalni promenne
    private Context context;
    private String[] androidVerze;

    // konstruktor adapteru
    public PolozkaAdapter(Context context, String[] androidVerze) {
        super(context, R.layout.polozka, androidVerze);
        // ulozeni parametru do lokalnich promennych
        this.context = context;
        this.androidVerze = androidVerze;
    }

    @Override
    // predefinovani metody getView tak, aby vracela nami nadefinovanou polozku
    public View getView(int pozice, View convertView, ViewGroup parent) {
        // prevedeni polozky na Viev
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.polozka, parent, false);

        // ziskani retezce z pole, podle pozice
        String strItem = getItem(pozice);
        // rozdeleni retezce na casti
        String[] strVersion = strItem.split(":");
        // nalezeni jednotlivych elementu a prirazeni do promennych
        ImageView imgLogo = (ImageView) convertView.findViewById(R.id.imgLogo);
        TextView txtName = (TextView) convertView.findViewById(R.id.txtName);
        TextView txtVersion = (TextView) convertView
                .findViewById(R.id.txtVersion);
        // zpristupneni resources
        Resources res = context.getResources();
        // jmeno resource s logem androidu
        String resName = strVersion[1].replaceAll(" ", "").toLowerCase();
        // id resource s logem androidu
        int imgLogoId = res.getIdentifier(resName, "drawable",
                context.getPackageName());
        // prirazeni loga do ImageView
        imgLogo.setImageResource(imgLogoId);
        // prirazeni nazvu a verze do textovych poli
        txtName.setText(strVersion[1]);
        txtVersion.setText(strVersion[0]);

        // upravene View odeslat jako navratovou hodnotu
        return convertView;
    }
}
