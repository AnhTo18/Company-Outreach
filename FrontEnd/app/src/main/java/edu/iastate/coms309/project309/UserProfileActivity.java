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
import edu.iastate.coms309.project309.util.RequestController;

public class UserProfileActivity extends AppCompatActivity {
    private TextView mTextViewResult;
    private RequestQueue mQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mTextViewResult = findViewById(R.id.text_view_result);

        mQueue = Volley.newRequestQueue(this);

        jsonParse();
    }

    private void jsonParse() {

        String url = Const.URL_SHOW_USERS + "/" + Const.username;

        RequestController rc = new RequestController(getApplicationContext());
        JSONObject j = rc.requestJsonObject(Request.Method.GET, url);

        try {
            String firstName = j.getString("firstName");
            String lastName = j.getString("lastName");
            String address = j.getString("address");
            String telephone = j.getString("telephone");
            String username = j.getString("username");
            String points = j.getString("points");

            mTextViewResult.append(username + ": " + (points == null ? 0 : points) + " points \n" +
                    firstName + " " + lastName + "\n" +
                    address + "\n" +
                    telephone);
        } catch (JSONException e) {
            Log.e("JSON", "JSON Error: " + e.toString());
            e.printStackTrace();
        }

        /*
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String firstName = response.getString("firstName");
                            String lastName = response.getString("lastName");
                            String address = response.getString("address");
                            String telephone = response.getString("telephone");
                            String username = response.getString("username");
                            String points = response.getString("points");

                            mTextViewResult.append(username + ": " + (points == null ? 0 : points) + " points \n" +
                                                    firstName + " " + lastName + "\n" +
                                                    address + "\n" +
                                                    telephone);
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
        
         */
    }
}

