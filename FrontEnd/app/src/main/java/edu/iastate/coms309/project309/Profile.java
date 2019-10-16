package edu.iastate.coms309.project309;
import  androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class Profile extends AppCompatActivity {
    private TextView mTextViewResult;
    private RequestQueue mQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        mTextViewResult = findViewById(R.id.text_view_result);
        Button profile = findViewById(R.id.button_parse);

        mQueue = Volley.newRequestQueue(this);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonParse();
            }
        });
    }

    private void jsonParse() {
        String url = "http://coms-309-ss-8.misc.iastate.edu:8080/owners/";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //Log.e("", response.toString());
                            JSONArray jsonArray = response.getJSONArray("profiles");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject user = jsonArray.getJSONObject(i);
                                String id = user.getString("id");
                                String firstName = user.getString("firstName");
                                String lastName = user.getString("lastName");
                                String address = user.getString("address");
                                String telephone = user.getString("telephone");
                                String username = user.getString("username");
                                String password = user.getString("password");
                                Integer points = user.getInt("points");

                                mTextViewResult.append(id+", "+firstName + ", " + lastName+", "+address+", "+telephone+", "+username+
                                        ", " +password+String.valueOf(points) +"\n\n");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }
}

