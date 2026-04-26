package cz.stokratandroid.recyclerview2;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerHolder extends RecyclerView.ViewHolder {

    public TextView txtNazev;
    public TextView txtVerze;
    public ImageView imgLogo;

    public RecyclerHolder(View itemView) {
        super(itemView);

        // nalezeni jednotlivych objektu formulare a prirazeni do promennych
        txtNazev = itemView.findViewById(R.id.txtName);
        txtVerze = itemView.findViewById(R.id.txtVersion);
        imgLogo = itemView.findViewById(R.id.imgLogo);
    }
}
