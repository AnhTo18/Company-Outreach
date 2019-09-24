package edu.iastate.coms309.project309;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

// import android.support.v7.app.AppCompatActivity; cannot resolve symbol "v7"
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;

public class DevHome extends AppCompatActivity {

    Button b1;
    public static TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_home);

        result = findViewById(R.id.text_result);
        b1 = findViewById(R.id.qr_button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startScanner();
            }
        });
    }

    private void startScanner() {
        new IntentIntegrator(this).initiateScan();
    }
}
