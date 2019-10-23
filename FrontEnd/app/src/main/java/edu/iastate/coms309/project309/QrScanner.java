package edu.iastate.coms309.project309;

import androidx.appcompat.app.AppCompatActivity;
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
    private String qr;
   // private String points="";
    private IntentIntegrator qrScan;
    RequestQueue rq;
    JsonObjectRequest jor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.qr_scanenr);
       scanQr=(Button)findViewById(R.id.buttonQr);
       Resulttext=(TextView)findViewById(R.id.Resulttext);


       qrScan=new IntentIntegrator(this);
       scanQr.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        qrScan.initiateScan();

        JSONObject js = new JSONObject();
        try {
            js.put("qr", qr); //need to know where to send qr code
        } catch (JSONException e) {
            e.printStackTrace();
        }

        jor = new JsonObjectRequest(Request.Method.POST, Const.URL_QR, js, new Response.Listener<JSONObject>() {

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result= IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        /*
        points="";
        for(int i=0;i<result.toString().length();i++){
            if(result.toString().substring(0,5).equals("309ss8")){
                points=points+result.toString().substring(6,8);
            }
            else{

            }
        }
        */

            if(result!=null){
                if(result.getContents()==null){
                    Toast.makeText(this,"No result found,",Toast.LENGTH_LONG).show();
                }else{
                    Resulttext.setText(result.toString());
                    qr=result.toString();
                }
            }else{
                super.onActivityResult(requestCode,resultCode,data);
            }

    }

}
