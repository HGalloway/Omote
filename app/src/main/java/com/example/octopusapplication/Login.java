package com.example.octopusapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Switch;

import org.json.JSONObject;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.login);
        LoginButton();
    }

    private void LoginButton(){
        //Switch StayLoggedInSwitch = (Switch) (findViewById(R.id.StayLogedInSwitch));
        //JSONObject JSONObject = new JSONObject();
        //JSONObject.put("StayLoggedIn", StayLoggedInSwitch.)
    }
}