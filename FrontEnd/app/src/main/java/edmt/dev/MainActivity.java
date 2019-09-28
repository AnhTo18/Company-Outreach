package edmt.dev;

import android.Manifest;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qrscanner.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity {
    private ZXingScannerView scannerView;
    private TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scannerView=(ZXingScannerView)findViewById(R.id.zxscan);
        txtResult=(TextView)findViewById(R.id.zxscan);

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionLinstener(){
            @Override
            public void onPermissionGranted (PermissionGrantedResponse response){
                    scannerView.setResultHandler(context.MainActivity.this);
                    scannerView.startCamera();
            }
            @Override
            public void onPermissionDenied (PermissionDeniedResponse response){
                Toast.makeText(context.MainActivity.this, text: "You must accept this permission", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onPermissionRationaleShouldBeShown (PermissionRequest
            permission, PermissionToken token){
            }
        })
                .check();
            }
            @Override
            protected void onDestroy(){
                scannerView.stopCamera();
                super.onDestroy();
            }

            @Override
            public void handleResult(Result rawResult){
                txtResult.setText(rawResult.getText());
            }
        }


