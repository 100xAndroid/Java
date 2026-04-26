package cz.stokratandroid.recyclerview5;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class RecyclerAdapter extends RecyclerView.Adapter<RecyclerHolder> {

    private List<String> androidVerzeData;
    private View rowItem;

    // konstruktor adapteru
    public RecyclerAdapter(List<String> data) {
        androidVerzeData=data;
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.polozka, parent, false);
        return new RecyclerHolder(rowItem);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int pozice) {

        // ziskani retezce z pole, podle pozice
        String strItem = androidVerzeData.get(pozice);

        // rozdeleni retezce na casti
        String[] strVersion = strItem.split(":");

        // zpristupneni resources
        Resources res = rowItem.getResources();
        // jmeno resource s logem androidu
        String resName = strVersion[1].replaceAll(" ", "").toLowerCase();
        // id resource s logem androidu
        int imgLogoId = res.getIdentifier(resName , "drawable", rowItem.getContext().getPackageName());

        // predani hodnot holderu k zobrazeni
        holder.txtName.setText(strVersion[1]);
        holder.txtVersion.setText(strVersion[1]);
        holder.imgLogo.setImageResource(imgLogoId);
    }

    @Override
    public int getItemCount() {
        return androidVerzeData.size();
    }

}
