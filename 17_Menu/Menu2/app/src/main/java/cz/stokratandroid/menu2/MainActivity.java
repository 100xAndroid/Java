package cz.stokratandroid.menu2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // metoda volana kliknutim na tlacitko
    public void otevritPopUpMenu(View v) {
        // vytvorit menu
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_popup, popup.getMenu());

        // kliknuti na polozku menu
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                zvolenaPolozkaMenu(item);
                return true;
            }
        });

        // zobrazit menu
        popup.show();
    }

    // metoda volana po kliknuti na polozku menu
    private void zvolenaPolozkaMenu(MenuItem item) {
        Toast.makeText(this, "Zvolena položka menu " + item.getTitle(), Toast.LENGTH_SHORT).show();
    }

}