package edu.iastate.coms309.pointshopexperiment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    GridView grid;
    RequestQueue rq;
    ArrayList<String[]> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        grid = findViewById(R.id.gridView);
        data = new ArrayList<>();

        String url = "";

        /*
        rq = Volley.newRequestQueue(this);
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("coupons");

                    for (int i = 0 ; i < jsonArray.length() ; i++) {
                        JSONObject j = jsonArray.getJSONObject(i);
                        String[] s = {j.getString("item"), j.getString("price")};
                        data.add(s);
                    }
                } catch (JSONException e) {
                    Log.e("Volley", "Volley Error");
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();
            }
        });

        rq.add(jor);
        */

        for (int i = 0 ; i < 10 ; i++) {
            String[] s = {"item " + i, "$10"};
            data.add(s);
        }
        CustomAdapter adapter = new CustomAdapter(getApplicationContext(), data);
        grid.setAdapter(adapter);
    }
}
