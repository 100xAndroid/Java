package cz.stokratandroid.temata2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static int iTema=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (iTema == 1) {
            setTheme(R.style.Theme_Tema1);
        }
        else if (iTema == 2) {
            setTheme(R.style.Theme_Tema2);
        }
        else {
            setTheme(R.style.Theme_Tema3);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickTema1 (View view) {
        iTema = 1;
        this.recreate();
    }

    public void onClickTema2 (View view) {
        iTema = 2;
        this.recreate();
    }

    public void onClickTema3 (View view) {
        iTema = 3;
        this.recreate();
    }

}