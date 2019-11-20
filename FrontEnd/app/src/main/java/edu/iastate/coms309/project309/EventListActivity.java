package edu.iastate.coms309.project309;

import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import android.content.Intent;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;


import edu.iastate.coms309.project309.util.Const;
import edu.iastate.coms309.project309.util.EventAdapter;

public class EventListActivity extends AppCompatActivity {


    ListView list;
    RequestQueue rq;
    ArrayList<String> events, companies;

    private WebSocketClient wc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        list = findViewById(R.id.listView);

        events = new ArrayList<>();
        companies = new ArrayList<>();

        final EventAdapter adapter = new EventAdapter(getApplicationContext(), events, companies);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Const.event = adapter.getEvent(position);
                Const.company = adapter.getCompany(position);
                startActivity(new Intent(EventListActivity.this, EventViewActivity.class));
            }
        });


        rq = Volley.newRequestQueue(this);
        JsonArrayRequest jar = new JsonArrayRequest(Request.Method.GET, Const.URL_EVENT_LIST, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                /*
                try {
                    ja = response.getJSONArray("events");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                 */
                Log.d("Volley", response.toString());
                for (int i = 0; i < response.length(); i++) {
                    try {

                        JSONObject j = response.getJSONObject(i);

                        Log.e("Volley", j.toString());

                        adapter.add(j.getString("eventname"), j.getString("company"));



                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Volley Error", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext(), "Volley Error", Toast.LENGTH_SHORT).show();
            }
        });
        rq.add(jar);


        String w = Const.WS_EVENT_UPDATE + Const.username;
        Draft[] drafts = {new Draft_6455()};

        try {
            Log.d("Socket", "Trying socket");
            wc = new WebSocketClient(new URI(w), drafts[0]) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {

                }

                @Override
                public void onMessage(String s) {
                    Log.d("WS", "run() returned: " + s);

                    if(!s.contains("has Joined the Chat") && !s.contains("Disconnected")) {
                        try {
                            JSONObject j = new JSONObject(s);

                            add(adapter, j.getString("eventname"), "" );
                            //adapter.add(j.getString("event"),j.getString("company"));
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    Log.d("CLOSE", "onClose() returned: " + s);
                }

                @Override
                public void onError(Exception e) {
                    Log.e("Exception:", e.toString());
                    e.printStackTrace();
                }
            };
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        wc.connect();

    }

    private void add(EventAdapter a, String e, String c) {
        final EventAdapter ad = a;
        final String ev = e;
        final String co = c;
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                ad.add(ev,co);
            }
        });
    }

}
