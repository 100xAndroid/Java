package cz.stokratandroid.otiskyprstu;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    private TextView txtVysledek;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtVysledek = findViewById(R.id.txtVysledek);

        Executor executor = ContextCompat.getMainExecutor(this);

        biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback(){
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                txtVysledek.setText("Nepodařilo se ověřit");
            }

            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                txtVysledek.setText("Ověřeno");
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                txtVysledek.setText("Chyba - neověřeno");
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Otisk prstu")
                .setNegativeButtonText("Zrušit")
                .setConfirmationRequired(false)
                .build();
    }

    public void testCtecky (View view) {

        BiometricManager biometricManager=BiometricManager.from(this);

        switch (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)){
            case BiometricManager.BIOMETRIC_SUCCESS:
                Toast.makeText(this, "Čtečka otisku prstu je připravena.", Toast.LENGTH_LONG).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(this, "Zařízení neobsahuje čtečku otisku prstu.", Toast.LENGTH_LONG).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Toast.makeText(this, "Zamykání pomocí otisku prstu není v Nastaveních aktivované.", Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(this, "Jiná chyba.", Toast.LENGTH_LONG).show();
        }
    }


    public void overeniOtisku(View view) {
        biometricPrompt.authenticate(promptInfo);
    }

}