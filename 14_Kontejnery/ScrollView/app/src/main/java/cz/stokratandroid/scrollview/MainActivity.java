package cz.stokratandroid.scrollview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // nacist data do komponenty textView text
        naplnitScrollView();
    }

    // nastavit text do textoveho pole
    private void naplnitScrollView()
    {
        // ziskat komponentu textView
        TextView textField = (TextView)findViewById(R.id.textView1);
        // do textView vlozit text definovany v resourcech string
        textField.setText(R.string.verzeAndroidu);
    }
}