package cz.stokratandroid.recyclerview2;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cz.stokratandroid.recyclerview2.MainActivity;

class RecyclerAdapter extends RecyclerView.Adapter<RecyclerHolder>  {

    private List<String> androidVerzeData;
    private View rowItem;
    private MainActivity mainActivity;

    // konstruktor adapteru
    public RecyclerAdapter(List<String> data, MainActivity volajici) {
        androidVerzeData=data;
        mainActivity=volajici;
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.polozka, parent, false);
        rowItem.setOnClickListener(mItemClick);
        return new RecyclerHolder(rowItem);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int pozice) {

        // barva pozadi polozky
        holder.itemView.setSelected(holder.itemView.isSelected());

        // ziskani retezce z pole, podle pozice
        String strPolozka = androidVerzeData.get(pozice);

        // rozdeleni retezce na casti
        String[] strVerze = strPolozka.split(":");

        // zpristupneni resources
        Resources res = rowItem.getResources();

        // jmeno resource s logem androidu
        String resNazev = strVerze[1].replaceAll(" ", "").toLowerCase();

        // id resource s logem androidu
        int imgLogoId = res.getIdentifier(resNazev , "drawable", rowItem.getContext().getPackageName());
        //int imgLogoId = res.getIdentifier(resNazev , "drawable", rowItem.getContext().getPackageName());

        // predani hodnot prostrednictvim holderu k zobrazeni
        holder.txtNazev.setText(strVerze[1]);
        holder.txtVerze.setText(strVerze[0]);
        holder.imgLogo.setImageResource(imgLogoId);
    }

    @Override
    public int getItemCount() {
        return androidVerzeData.size();
    }

    private View.OnClickListener mItemClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView txtPolozkaText = (TextView)v.findViewById(R.id.txtName);

            // zobrazeni toast zpravy
            // Toast.makeText(v.getContext(), txtPolozkaText.getText(), Toast.LENGTH_SHORT).show();

            // volani metody tridy MainActivity
            mainActivity.onClickCalled(txtPolozkaText.getText().toString());
        }
    };
}
