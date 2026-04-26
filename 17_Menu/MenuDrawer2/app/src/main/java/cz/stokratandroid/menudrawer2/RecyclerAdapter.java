package cz.stokratandroid.menudrawer2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerHolder> {

    private List<String> androidVerze;
    public RecyclerAdapter (List<String> data){
        this.androidVerze = data;
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.polozka, parent, false);
        return new RecyclerHolder(rowItem);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        // barva pozadi polozky
        holder.itemView.setSelected(holder.itemView.isSelected());
        // text polozky
        holder.txtPolozkaText.setText(androidVerze.get(position));
    }

    @Override
    public int getItemCount() {
        return androidVerze.size();
    }
}
