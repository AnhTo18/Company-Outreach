package com.example.userlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import net.sourceforge.jtds.jdbc.*;

public class MainActivity extends AppCompatActivity {

    private EditText Name;
    //keep user name
    private EditText Password;
    //keep user password
    private TextView Info;
    //keep info
    private Button Login;
    //button on app
    private int counter = 5;
    //count number of attempts for incorrect passwords, 5 total attempts

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = (EditText)findViewById(R.id.etName);
        //update Name
        Password = (EditText)findViewById(R.id.etPassword);
        //update Password
        Info = (TextView)findViewById(R.id.tvInfo);
        //update Info
        Login = (Button)findViewById(R.id.btnLogin);
        //update Login

        Info.setText("Number of Attempts left: 5");
        //Display current attempts left

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmPassword(Name.getText().toString(), Password.getText().toString());
                //on click calls the function of confirmPassword
            }
        });

    }
    private void confirmPassword(String userName, String userPassword)
    {
        if((userName.equals("Admin")) && (userPassword.equals("1234")))
        {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        }
        else
        {

            counter--;
            //password is incorrect, decrease the counter by 1

            Info.setText("Number of Attempts left: " + String.valueOf(counter));

            if(counter == 0)
            {
                Login.setEnabled(false);
                //locks the login button
                //no more attempts left
            }

        }
    }
}
