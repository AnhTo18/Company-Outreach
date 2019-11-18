package edu.iastate.coms309.project309;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.EventLog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.iastate.coms309.project309.util.Const;

public class EventViewActivity extends AppCompatActivity {

    Button qr, shop;
    TextView e, c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);

        qr = findViewById(R.id.buttonGotoQR);
        shop = findViewById(R.id.buttonGotoShop);

        e = findViewById(R.id.textEventName);
        e.setText(Const.event);

        c = findViewById(R.id.textCompanyName);
        c.setText(Const.company);

        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EventViewActivity.this, QrScanner.class));
            }
        });

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EventViewActivity.this, PointShopActivity.class));
            }
        });
    }
}
