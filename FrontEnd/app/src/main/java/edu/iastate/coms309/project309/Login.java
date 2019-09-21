package edu.iastate.coms309.project309;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.text.TextUtils;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity
{

    EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.textInputUsername);
        password = findViewById(R.id.textInputPassword);

        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                userLogin();
            }
        });
    }

    private void userLogin()
    {
        final String usr = username.getText().toString();
        final String pw = password.getText().toString();

        if (TextUtils.isEmpty(usr))
        {
            username.setError("Please enter your username");
            username.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(pw))
        {
            password.setError("Please enter your password");
            password.requestFocus();
            return;
        }

        StringRequest sr = new StringRequest(Request.Method.POST, Paths.LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);

                    if (json.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), json.getString("message"), Toast.LENGTH_SHORT).show();
                        JSONObject userJSON = json.getJSONObject("user");

                        User user = new User(userJSON.getString("username"));


                    } else {
                        Toast.makeText(getApplicationContext(), json.getString("message"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", usr);
                    params.put("password", pw);
                    return params;
                }
            };

        MySingleton.getInstance(this).addToRequestQueue(sr);
        }
    }