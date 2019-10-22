package edu.iastate.coms309.project309;
import  androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.iastate.coms309.project309.util.AppController;
import edu.iastate.coms309.project309.util.Const;

public class UserProfileActivity extends AppCompatActivity {
    private TextView mTextViewResult;
    private RequestQueue mQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mTextViewResult = findViewById(R.id.text_view_result);
        Button buttonParse = findViewById(R.id.button_parse);

        mQueue = Volley.newRequestQueue(this);

        jsonParse();
    }

    private void jsonParse() {

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Const.URL_SHOW_USERS, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0 ; i < response.length() ; i++) {
                                JSONObject user = response.getJSONObject(i);
                                String id = user.getString("id");
                                String firstName = user.getString("firstName");
                                String lastName = user.getString("lastName");
                                String address = user.getString("address");
                                String telephone = user.getString("telephone");
                                String username = user.getString("username");
                                String password = user.getString("password");
                                String points = user.getString("points");

                                mTextViewResult.append(username + ": " + points + "\n");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(AppController.TAG, "Error: " + error.getMessage());
                Log.e(AppController.TAG, "Error: " + error.getMessage());
                Toast t = Toast.makeText(getApplicationContext(), "Volley Error", Toast.LENGTH_SHORT);
                t.show();
            }
        });

        mQueue.add(request);
    }
}

