package cz.stokratandroid.menu3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // zaregistrovat kontextove menu na obrazek
        ImageView obrazek = (ImageView) findViewById(R.id.obrazek);
        registerForContextMenu(obrazek);

        // zaregistrovat kontextove menu na tlacitko
        Button button = (Button) findViewById(R.id.tlacitko);
        registerForContextMenu(button);
    }

    // udalost vyvolavana pred otevirenim kontextoveho menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();

        if(v.getId()==R.id.obrazek)
            inflater.inflate(R.menu.menu_context1, menu);
        else if(v.getId()==R.id.tlacitko)
            inflater.inflate(R.menu.menu_context2, menu);
    }

    @Override
    public boolean onContextItemSelected (MenuItem item){
        Toast.makeText(this, "Zvolena položka menu " + item.getTitle(), Toast.LENGTH_SHORT).show();
        return super.onContextItemSelected(item);
    }

}