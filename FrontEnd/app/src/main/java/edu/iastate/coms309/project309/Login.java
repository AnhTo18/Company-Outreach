package edu.iastate.coms309.project309;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import edu.iastate.coms309.project309.util.AppController;
import edu.iastate.coms309.project309.util.Const;

public class Login extends AppCompatActivity {

    EditText user, pass;
    Button reg, login;

    RequestQueue rq;
    JsonObjectRequest jor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = findViewById(R.id.loginInputUsername);
        pass = findViewById(R.id.loginInputPassword);
        reg = findViewById(R.id.buttonMakeAcct);
        login = findViewById(R.id.buttonLogin);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_register);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                }) {

                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();


                        return params;
                    }
                };

                rq.add(jor);
            }
        });


    }
}
