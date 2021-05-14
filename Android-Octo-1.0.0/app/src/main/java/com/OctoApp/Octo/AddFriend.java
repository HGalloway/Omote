package com.OctoApp.Octo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class AddFriend extends AppCompatActivity {
    final String[] FCMToken = new String[1];
    DatabaseReference UserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.addfriend);
        String Username = getIntent().getStringExtra("Username");
        SetAddFriendButton(Username);
        UserDatabase = FirebaseDatabase.getInstance().getReference();
    }

    private void SetAddFriendButton(String Username) {
        Button AddFriend = findViewById(R.id.addFriend);
        EditText UsernameInput = findViewById(R.id.UsernameInput);
        Messages Messages = new Messages();
        AddFriend.setOnClickListener(v -> {
            if (UsernameInput.getText().toString().isEmpty()) {
                Messages.OutputErrorMessage(3, getApplicationContext());
            } else {
                UserDatabase.child(UsernameInput.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() == null) {
                            Messages.OutputErrorMessage(5, getApplicationContext());
                        } else if (dataSnapshot.getValue() != null) {
                            PutInformationIntoDatabase(Username, UsernameInput.getText().toString());
                            Messages.OutputCompletionMessage("Friend Added!", getApplicationContext());

                            Intent intent = new Intent(getApplicationContext(), Friends.class);
                            intent.putExtra("Username", Username);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void PutInformationIntoDatabase(String Username, String FriendsUsername) {
        UserDatabase.child(Username).child("Friends").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    UserDatabase.child(Username).child("Friends").setValue(snapshot.getValue() + " " + FriendsUsername);
                } else {
                    UserDatabase.child(Username).child("Friends").setValue(FriendsUsername);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        UserDatabase.child(FriendsUsername).child("Friends").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    UserDatabase.child(FriendsUsername).child("Friends").setValue(snapshot.getValue() + " " + Username);
                } else {
                    UserDatabase.child(FriendsUsername).child("Friends").setValue(Username);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}