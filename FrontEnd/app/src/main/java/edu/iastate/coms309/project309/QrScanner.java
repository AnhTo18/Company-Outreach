package edu.iastate.coms309.project309;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;
public class QrScanner extends AppCompatActivity implements View.OnClickListener {
    private Button scanQr;
    private TextView Resulttext;
    private IntentIntegrator qr;

    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.qr_scanenr);
       scanQr=(Button)findViewById(R.id.buttonQr);
       Resulttext=(TextView)findViewById(R.id.Resulttext);

       scanQr.setOnClickListener(this);
       qrScan=new IntentIntegrator(this);
    }

    @Override
    public void onClick(View v){
        qrScan.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result= IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
            if(result!=null){
                if(result.getContents()==null){
                    Toast.makeText(this,"No result found,",Toast.LENGTH_LONG).show();
                }else{
                    Resulttext.setText("Scanned: "+ result.getContents());
                }
            }else{
                super.onActivityResult(requestCode,resultCode,data);
            }


    }

}
