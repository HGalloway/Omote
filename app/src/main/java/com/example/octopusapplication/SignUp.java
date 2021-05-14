package com.example.octopusapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class SignUp extends AppCompatActivity {
    public String Username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.signup);
        SignUpButton();
    }

    /**
     * <p>Sets up the sign up button.</p>
     */
    private void SignUpButton(){
        Button SignUp = findViewById(R.id.LoginLPage);
        SignUp.setOnClickListener (v -> {
            TextView EmailInput = findViewById(R.id.EmailInput);
            TextView PasswordInput = findViewById(R.id.PasswordInput);
            TextView UsernameInput = findViewById(R.id.UsernameInputL);
            Messages Messages = new Messages();
            Context Context = getApplicationContext();
            DatabaseReference UserDatabase = FirebaseDatabase.getInstance().getReference();


            if (CheckInput(EmailInput, PasswordInput, UsernameInput, Messages, Context, UserDatabase));
            else {
                PutDataInDatabase(EmailInput, PasswordInput, UsernameInput, UserDatabase);
                Messages.OutputCompletionMessage(0, Context);
                Username = UsernameInput.getText().toString();
                Toast.makeText(Context, Username, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, CharacterCreation.class);
                intent.putExtra("Username", Username);
                startActivity(intent);
            }
        });
    }

    /**
     * @param EmailInput
     * @param PasswordInput
     * @param UsernameInput
     * @param Messages
     * @param Context
     * @return true or false
     * <p></p>
     * <p>Checks input. If input is invalid then returns true. If input is valid then returns false.</p>
     */
    private boolean CheckInput(TextView EmailInput, TextView PasswordInput, TextView UsernameInput, Messages Messages, Context Context, DatabaseReference UserDatabase){
        if (EmailInput.getText().toString().isEmpty()) {
            Messages.OutputErrorMessage(0, Context);
            return false;
        }
        else if (PasswordInput.getText().toString().isEmpty()) {
            Messages.OutputErrorMessage(1, Context);
            return true;
        }
        else if (PasswordInput.getText().length() < 8) {
            Messages.OutputErrorMessage(2, Context);
            return true;
        }
        else if (UsernameInput.getText().toString().isEmpty()) {
            Messages.OutputErrorMessage(3, Context);
            return true;
        }
        else if(UsernameInput.getText().toString().equals("Version")){
            Messages.OutputErrorMessage(4, Context);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * @param EmailInput
     * @param PasswordInput
     * @param UsernameInput
     * Puts users data into database.
     */
    private void PutDataInDatabase(TextView EmailInput, TextView PasswordInput, TextView UsernameInput, DatabaseReference UserDatabase){
        UserDatabase.child(UsernameInput.getText().toString());
        UserDatabase.child(UsernameInput.getText().toString()).child("Email").setValue(EmailInput.getText().toString());
        UserDatabase.child(UsernameInput.getText().toString()).child("Username").setValue(UsernameInput.getText().toString());
        UserDatabase.child(UsernameInput.getText().toString()).child("Password").setValue(BCrypt.hashpw(PasswordInput.getText().toString(), BCrypt.gensalt()));
        UserDatabase.child(UsernameInput.getText().toString()).child("Friends").setValue("");
    }


}