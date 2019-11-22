package edu.iastate.coms309.project309;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class UserHomeActivity extends AppCompatActivity {


    Button events, comps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        events = findViewById(R.id.buttonEventList);
        comps = findViewById(R.id.buttonCompanyList);

        events.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHomeActivity.this, EventListActivity.class));
            }
        });

        comps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHomeActivity.this, CompanyListActivity.class));
            }
        });

    }
}
