package cz.stokratandroid.menudrawer1;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

    public TextView txtPolozkaText;

    public RecyclerHolder(View itemView) {
        super(itemView);

        itemView.setOnClickListener(this);
        txtPolozkaText = itemView.findViewById(R.id.txtPolozkaText);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), txtPolozkaText.getText(), Toast.LENGTH_SHORT).show();
    }
}