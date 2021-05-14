package com.example.octopusapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.profile);
        SetUsernameTitle();
        SetBackButton();
        SetSignOutButton();
    }
    private void SetUsernameTitle(){
        TextView UsernameTitle = findViewById(R.id.UserTitle);
        UsernameTitle.setText(getIntent().getStringExtra("Username"));
    }
    private void SetBackButton(){
        Button BackButton = findViewById(R.id.BackButtonAbout);
        BackButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, Main.class);
            intent.putExtra("Username", getIntent().getStringExtra("Username"));
            startActivity(intent);
        });
    }
    private void SetSignOutButton(){
        Button SignOutButton = findViewById(R.id.SignOut);
        SignOutButton.setOnClickListener(v -> {
            JSONHandler JSONHandler = new JSONHandler();
            Hashtable<String, Boolean> Settings = new Hashtable<>();
            Boolean StayLoggedIn = false;

            Settings.put("StayLoggedIn", StayLoggedIn);
            JSONHandler.WritingToJSONFile("Settings.json", Settings, getApplicationContext());

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("Username", getIntent().getStringExtra("Username"));
            startActivity(intent);
            new Messages().OutputCompletionMessage("Sign Out Complete", getApplicationContext());
        });
    }

}