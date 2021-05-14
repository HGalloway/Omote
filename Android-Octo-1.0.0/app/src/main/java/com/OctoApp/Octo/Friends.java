package com.OctoApp.Octo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class Friends extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.friends);
        String Username = getIntent().getStringExtra("Username");
        System.out.println(Username);
        SetAddFriendButton(Username);
        SetBackButton(Username);
    }

    private void SetAddFriendButton(String Username) {
        Button AddFriend = findViewById(R.id.AddFriend);
        AddFriend.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddFriend.class);
            intent.putExtra("Username", Username);
            startActivity(intent);
        });
    }

    private void SetBackButton(String Username) {
        Button BackButton = findViewById(R.id.FriendsBackButton);
        BackButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, Main.class);
            intent.putExtra("Username", Username);
            startActivity(intent);
        });
    }

}