package com.example.octopusapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private JSONObject Settings;
    private Boolean Loggedin = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        //GetLoggedInSetting();

        if (Loggedin == null) {
            Button SignUp = (Button) findViewById(R.id.SignUp);
            SignUp.setOnClickListener(v -> {
                Intent intent = new Intent(this, SignUp.class);
                startActivity(intent);

            });

            Button Login = (Button) findViewById(R.id.Login);
            Login.setOnClickListener(v -> {
                Intent intent = new Intent(this, Login.class);
                startActivity(intent);
            });
        }
        else if(Loggedin == true){
            Intent intent = new Intent(this, SignUp.class);
            startActivity(intent);
        }


    }
    private void GetLoggedInSetting(){
        Context Context = getApplicationContext();
        JSONHandler JSONHandler = new JSONHandler();
        try {
            Settings = JSONHandler.ReadJSONFile(Context, "Settings.json");
            Loggedin = (Boolean) Settings.get("StayLoggedIn");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}