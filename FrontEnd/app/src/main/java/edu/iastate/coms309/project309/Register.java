package edu.iastate.coms309.project309;

import  androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import edu.iastate.coms309.project309.util.AppController;
import edu.iastate.coms309.project309.util.Const;


public class Register extends AppCompatActivity {

    RequestQueue rq;
    JsonObjectRequest jor;
    EditText firstname, lastname, address, phone ,username, password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        String tag_json_obj = "json_obj_req";

        rq = Volley.newRequestQueue(this);

        firstname = findViewById(R.id.textInputFirstname);
        lastname = findViewById(R.id.textInputLastname);
        address = findViewById(R.id.textInputAddress);
        phone = findViewById(R.id.textInputPhone);
        username = findViewById(R.id.textInputUsername);
        password = findViewById(R.id.textInputPassword);

        findViewById(R.id.buttonRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject js = new JSONObject();
                try {
                    js.put("id", "");
                    js.put("firstName", firstname.getText().toString());
                    js.put("lastName", lastname.getText().toString());
                    js.put("address", address.getText().toString());
                    js.put("telephone", phone.getText().toString());
                    js.put("username", username.getText().toString());
                    js.put("password", password.getText().toString());
                    
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                jor = new JsonObjectRequest(Request.Method.POST, Const.URL_REGISTER, js, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(AppController.TAG, response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(AppController.TAG, "Error: " + error.getMessage());
                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("user", username.toString());
                        params.put("pass", password.toString());

                        return params;
                    }
                };

                rq.add(jor);

                setContentView(R.layout.activity_user_home);
            }
        });


    }
}