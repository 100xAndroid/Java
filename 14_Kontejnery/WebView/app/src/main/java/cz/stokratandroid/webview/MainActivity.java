package cz.stokratandroid.webview;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spustitBrowser();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void spustitBrowser()
    {
        // String url="https://cs.wikipedia.org/wiki/Android_(opera%C4%8Dn%C3%AD_syst%C3%A9m)";
        String url="file:///android_asset/verzeAndroidu.html";
        // najdit komponentu WebView
        WebView browser = (WebView) this.findViewById(R.id.webView);
        // nastavit parametry browseru
        browser.getSettings().setJavaScriptEnabled(true);
        browser.setWebViewClient(new WebViewClient());
        // zobrazit webovou stranku
        browser.loadUrl(url);
    }
}
