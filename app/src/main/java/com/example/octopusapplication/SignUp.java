package com.example.octopusapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.signup);
        SignUpButton();
    }

    private void SignUpButton(){
        Button SignUp = (Button) findViewById(R.id.LoginLPage);
        SignUp.setOnClickListener (v -> {
            TextView EmailInput = (TextView) findViewById(R.id.EmailInput);
            TextView PasswordInput = (TextView) findViewById(R.id.PasswordInput);
            TextView UsernameInput = (TextView) findViewById(R.id.UsernameInputL);
            Messages Messages = new Messages();
            Context Context = getApplicationContext();

            if (EmailInput.getText().toString().isEmpty()) {
                Messages.OutputErrorMessage(0, Context);
            }
            else if (PasswordInput.getText().toString().isEmpty()) {
                Messages.OutputErrorMessage(1, Context);
            }
            else if (PasswordInput.getText().length() < 8) {
                Messages.OutputErrorMessage(2, Context);
            }
            else if (UsernameInput.getText().toString().isEmpty()) {
               Messages.OutputErrorMessage(3, Context);
            }
            else {
                DatabaseReference UserDatabase = FirebaseDatabase.getInstance().getReference();
                UserDatabase.child(UsernameInput.getText().toString());
                UserDatabase.child(UsernameInput.getText().toString()).child("Email").setValue(EmailInput.getText().toString());
                UserDatabase.child(UsernameInput.getText().toString()).child("Username").setValue(UsernameInput.getText().toString());
                UserDatabase.child(UsernameInput.getText().toString()).child("Password").setValue(BCrypt.hashpw(PasswordInput.getText().toString(), BCrypt.gensalt()));
                Messages.OutputCompletionMessage(0, Context);
                //Intent intent = new Intent(this, CharacterCreation.class);
                //startActivity(intent);
            }


        });
    }



}