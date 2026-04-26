package cz.stokratandroid.nestedscrollview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // nacist data do komponenty textView text
        naplnitNestedScrollView();
    }

    // nastavit text do textoveho pole
    private void naplnitNestedScrollView()
    {
        // ziskat komponentu textView
        TextView textField = (TextView)findViewById(R.id.textView2);
        // do textView vlozit text definovany v resourcech string
        textField.setText(R.string.verzeAndroidu);
    }

}