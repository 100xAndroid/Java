package cz.stokratandroid.recyclerview5;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

    public TextView txtName;
    public TextView txtVersion;
    public ImageView imgLogo;

    public RecyclerHolder(View itemView) {
        super(itemView);

        // registrace udalosti kliknuti na polozku
        itemView.setOnClickListener(this);

        // nalezeni jednotlivych elementu a prirazeni do promennych
        txtName = itemView.findViewById(R.id.txtName);
        txtVersion = itemView.findViewById(R.id.txtVersion);
        imgLogo = itemView.findViewById(R.id.imgLogo);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), txtName.getText(), Toast.LENGTH_SHORT).show();
    }
}
