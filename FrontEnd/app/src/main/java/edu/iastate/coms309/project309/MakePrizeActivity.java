package edu.iastate.coms309.project309;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import edu.iastate.coms309.project309.util.Const;

public class MakePrizeActivity extends AppCompatActivity {

    EditText prize, cost, qty;
    Button submit;
    RequestQueue rq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_prize);

        prize = findViewById(R.id.textInputPrizeName);
        cost = findViewById(R.id.textInputCost);
        qty = findViewById(R.id.textInputQty);
        submit = findViewById(R.id.buttonMakePrize);
        rq = Volley.newRequestQueue(this);

        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                JSONObject j = new JSONObject();

                try {

                    j.put("prizename", prize.getText().toString());
                    j.put("cost", cost.getText().toString());
                    j.put("qty", qty.getText().toString());
                    j.put("company", Const.username);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, Const.URL_ADD_PRIZE, j, null, null );

                rq.add(jor);



            }
        });
    }
}
