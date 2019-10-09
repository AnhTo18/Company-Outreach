package edu.iastate.coms309.project309;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import edu.iastate.coms309.project309.util.AppController;
import edu.iastate.coms309.project309.util.Const;

public class LoginActivity extends AppCompatActivity {

    EditText user, pass;
    Button reg, login;

    RequestQueue rq;
    JsonObjectRequest jor, jor2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = findViewById(R.id.loginInputUsername);
        pass = findViewById(R.id.loginInputPassword);
        reg = findViewById(R.id.buttonMakeAcct);
        login = findViewById(R.id.buttonLogin);

        rq = Volley.newRequestQueue(this);


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_register);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**
                JSONObject js = new JSONObject();
                try {
                    js.put("username", user.getText().toString());
                    js.put("password", pass.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                jor = new JsonObjectRequest(Request.Method.POST, Const.URL_LOGIN, js, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(AppController.TAG, response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(AppController.TAG, "Error: " + error.getMessage());
                    }
                });

                try {
                    rq.add(jor);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast t = Toast.makeText(getApplicationContext(), "Error sending information to server", Toast.LENGTH_SHORT);
                    t.show();
                }
                */

                String url = Const.URL_LOGIN + "/" + user.getText().toString() + "/" + pass.getText().toString();
                String url_test = "https://api.myjson.com/bins/1137oi";

                jor2 = new JsonObjectRequest(Request.Method.GET, url, null , new Response.Listener<JSONObject>() {
                    String verify = "false";

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(AppController.TAG, "Response:" + response.toString());
                        try {
                            verify = response.getString("verify");
                        } catch (JSONException e) {
                            Toast t = Toast.makeText(getApplicationContext(), "JSON Exception", Toast.LENGTH_SHORT);
                            t.show();
                            e.printStackTrace();
                        }

                        if (verify.equals("true")) {
                            setContentView(R.layout.activity_dev_home);
                        } else {
                            Toast t = Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT);
                            t.show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(AppController.TAG, "Error: " + error.getMessage());
                        Log.d(AppController.TAG, "Error: " + error.getMessage());
                        Toast t = Toast.makeText(getApplicationContext(), "Volley Error", Toast.LENGTH_SHORT);
                        t.show();
                    }
                });



                try {
                    rq.add(jor2);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast t = Toast.makeText(getApplicationContext(), "Error receiving information from server", Toast.LENGTH_SHORT);
                    t.show();
                }

            }
        });


    }
}
