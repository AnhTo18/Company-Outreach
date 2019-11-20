package edu.iastate.coms309.project309;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.EventLog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.iastate.coms309.project309.util.Const;

public class EventViewActivity extends AppCompatActivity {

    Button qr, shop, sub;
    TextView e, c, loc, date, time;
    RequestQueue rq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);

        qr = findViewById(R.id.buttonGotoQR);
        shop = findViewById(R.id.buttonGotoShop);

        shop.setText(Const.company + " Points Shop");

        e = findViewById(R.id.textEventName);
        e.setText(Const.event);

        c = findViewById(R.id.textCompanyName);
        c.setText(Const.company);

        loc = findViewById(R.id.textLocation);
        date = findViewById(R.id.textDate);
        time = findViewById(R.id.textTime);


        rq = Volley.newRequestQueue(this);
        JsonObjectRequest jar = new JsonObjectRequest(Request.Method.GET, Const.URL_EVENT_LIST + "event5", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Volley", response.toString());

                try {
                    loc.setText(response.getString("location"));
                    date.setText(response.getString("date"));
                    time.setText(response.getString("time"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                /*
                JSONArray ja = new JSONArray();
                try {
                    ja = response.getJSONArray("events");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < ja.length(); i++) {
                    try {

                        JSONObject j = ja.getJSONObject(i);
                        loc.setText(j.getString("location"));
                        date.setText(j.getString("date"));
                        time.setText(j.getString("time"));

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Volley Error", Toast.LENGTH_SHORT).show();
                    }
                }

                 */
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext(), "Volley Error", Toast.LENGTH_SHORT).show();
            }
        });
        rq.add(jar);

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

        sub = findViewById(R.id.buttonSubscribe);
        sub.setText("Subscribe to " + Const.company);
    }
}
