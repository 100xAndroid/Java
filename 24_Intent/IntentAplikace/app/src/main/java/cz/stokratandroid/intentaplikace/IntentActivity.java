package cz.stokratandroid.intentaplikace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

public class IntentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);

        // ziskat predavany intent
        Intent myIntent = getIntent();

        // zajima nas pouze intent typu VIEW
        if (myIntent.getAction().equals(Intent.ACTION_VIEW)) {

            // nacist predana data
            Uri data = myIntent.getData();

            // data zobrazit
            TextView txtView = (TextView)this.findViewById(R.id.textView);
            txtView.setText(data.toString());
        }
    }
}