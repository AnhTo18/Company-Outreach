package edu.iastate.coms309.project309;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import edu.iastate.coms309.project309.util.AppController;
import edu.iastate.coms309.project309.util.Const;

public class QrScanner extends AppCompatActivity implements View.OnClickListener {
    private Button scanQr;
    private TextView Resulttext;

    private IntentIntegrator qrScan;
    RequestQueue rq;
    JsonObjectRequest jor, jor2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.qr_scanenr);
       scanQr=(Button)findViewById(R.id.buttonQr);
       Resulttext=(TextView)findViewById(R.id.Resulttext);

       rq = Volley.newRequestQueue(this);

       qrScan=new IntentIntegrator(this);
       scanQr.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        qrScan.initiateScan();



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result= IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
            if(result!=null){
                Log.d(AppController.TAG, result.getContents());
            }else{
                super.onActivityResult(requestCode,resultCode,data);
            }

        String url = Const.URL_QR + "/" + result.getContents();

        jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {  //need qrURL
            String company, points;

            @Override
            public void onResponse(JSONObject response) {
                Log.d(AppController.TAG, response.toString());


                try {
                    company = response.getString("company");
                    points = response.getString("points");

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast t = Toast.makeText(getApplicationContext(), "JSON Error", Toast.LENGTH_SHORT);
                    t.show();
                }


                new AlertDialog.Builder(getApplicationContext())
                        .setTitle("Title")
                        .setMessage("Add " + points + " points from " + company + "?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {

                                String url2 = Const.URL_ADD_POINTS + "/" + points + "/" + Const.username;

                                jor2 = new JsonObjectRequest(Request.Method.POST, url2, null, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {

                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast t = Toast.makeText(getApplicationContext(), "Volley Error", Toast.LENGTH_SHORT);
                                        t.show();
                                    }
                                });

                                rq.add(jor2);
                            }})
                        .setNegativeButton(android.R.string.no, null).show();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(AppController.TAG, "Error: " + error.getMessage());
                Toast t = Toast.makeText(getApplicationContext(), "Volley Error", Toast.LENGTH_SHORT);
                t.show();
            }
        });

        try {
            rq.add(jor);
        }catch (NullPointerException e) {
            Toast t = Toast.makeText(getApplicationContext(), "RQ Error", Toast.LENGTH_SHORT);
            t.show();
        }

    }

}
