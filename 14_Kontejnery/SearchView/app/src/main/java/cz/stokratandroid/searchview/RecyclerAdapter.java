package cz.stokratandroid.searchview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerHolder> implements Filterable {

    private List<String> androidVerze;
    private List<String> androidVerzeVse;

    public RecyclerAdapter (List<String> data){
        this.androidVerze = data;
        this.androidVerzeVse = new ArrayList<>(data);
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
    public Filter getFilter() {
        return androidVerzeFiltr;
    }
    private Filter androidVerzeFiltr = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<String> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(androidVerzeVse);
            } else {
                String filtrovacíText = constraint.toString().toLowerCase().trim();
                for (String item : androidVerzeVse) {
                    if (item.toLowerCase().contains(filtrovacíText)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            androidVerze.clear();
            androidVerze.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    @Override
    public int getItemCount() {
        return androidVerze.size();
    }

}

