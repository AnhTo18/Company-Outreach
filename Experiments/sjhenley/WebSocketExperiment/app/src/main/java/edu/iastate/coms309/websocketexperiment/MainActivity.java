package edu.iastate.coms309.websocketexperiment;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import android.media.session.PlaybackState;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView list;
    RequestQueue rq;
    ArrayList<String> events, companies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = findViewById(R.id.listView);

        events = new ArrayList<>();
        companies = new ArrayList<>();

        final CustomAdapter adapter = new CustomAdapter(getApplicationContext(), events, companies);
        list.setAdapter(adapter);

        String url = "https://api.myjson.com/bins/1h7m20";


        rq = Volley.newRequestQueue(this);
        JsonObjectRequest jar = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray ja = new JSONArray();
                try {
                    ja = response.getJSONArray("events");
                } catch ( JSONException e) {
                    e.printStackTrace();
                }

                for (int i = 0 ; i < ja.length() ; i++) {
                    try {

                        JSONObject j = ja.getJSONObject(i);

                        adapter.add(j.getString("event"),j.getString("company") );

                        Log.d("VOLLLLLLLLLLLLLLLLLLLLLLLLLLLEY", j.getString("event")+ "," + j.getString("company"));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        rq.add(jar);



    }
}
