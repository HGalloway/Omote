package com.OctoApp.Octo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.about);

        SetBackButton();
    }

    private void SetBackButton() {
        Button BackButton = findViewById(R.id.BackButtonAbout);
        BackButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, Main.class);
            intent.putExtra("Username", getIntent().getStringExtra("Username"));
            startActivity(intent);
        });
    }
}