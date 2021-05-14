package com.example.octopusapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;

public class Login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.login);
        LoginButton();
    }
    public String Username;

    /**
     * <p>Sets up the Login button</p>
     */
    private void LoginButton(){
        Button LoginButton = (Button) findViewById(R.id.LoginLPage);
        LoginButton.setOnClickListener(v -> {
            Messages Messages = new Messages();
            Context Context = getApplicationContext();
            EditText UsernameInput = findViewById(R.id.UsernameInputL);

            if (CheckIfInputsAreEmpty(Context, Messages) == false){
                Username = UsernameInput.getText().toString();
                WriteStayLoggedInSetting(Context, Messages);
                CheckUser(Context, Messages);
            }
        });
    }

    /**
     * @param Context
     * @param Messages
     * @return true or false
     * <p>Checks if the inputs are empty. If any are empty a message is sent to the user and it returns true. If the inputs are filled it returns false.</p>
     */
    private boolean CheckIfInputsAreEmpty(Context Context, Messages Messages){
        EditText UsernameInput = (EditText) findViewById(R.id.UsernameInputL);
        EditText PasswordInput = (EditText) findViewById(R.id.PasswordInput);
        if (UsernameInput.getText().toString().isEmpty()){
            Messages.OutputErrorMessage(3, Context);
            return true;
        }
        else if (PasswordInput.getText().toString().isEmpty()){
            Messages.OutputErrorMessage(1, Context);
            return true;
        }
        else{
            return false;
        }

    }

    /**
     *
     * @param Context
     * @param Messages
     *
     * <p>Writes the StayLoggedIn variable to the Settings.json file. StayLoggedIn equals the state of the switch.</p>
     */
    private void WriteStayLoggedInSetting(Context Context, Messages Messages){
        Switch StayLoggedInSwitch = findViewById(R.id.StayLogedInSwitch);
        Boolean SwitchState = StayLoggedInSwitch.isChecked();
        Hashtable<String, String> Settings = new Hashtable<>();
        Settings.put("StayLoggedIn", SwitchState.toString());
        Settings.put("Username", Username);
        new JSONHandler().WritingToJSONFile("Settings", Settings, Context);
    }

    /**
     * @param Context
     * @param Messages
     * <p>Checks users inputed username based on the information in the database.</p>
     */
    private void CheckUser(Context Context, Messages Messages){
        EditText UsernameInput = (EditText) findViewById(R.id.UsernameInputL);
        EditText PasswordInput = (EditText) findViewById(R.id.PasswordInput);
        DatabaseReference UserDatabase = FirebaseDatabase.getInstance().getReference();

        UserDatabase.child(UsernameInput.getText().toString()).child("Username").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //System.out.println(snapshot.getValue().toString());
                if (snapshot.getValue() == null ) {
                    Messages.OutputCompletionMessage("User does not exist. Please try again", Context);
                }
                else if(UsernameInput.getText().toString().isEmpty()){
                    Messages.OutputErrorMessage(3, Context);
                }
                else {
                    CheckPassword(Context, Messages);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    /**
     * @param Context
     * @param Messages
     * <p>Checks password against the users password hash</p>
     */
    private void CheckPassword(Context Context, Messages Messages){
        EditText UsernameInput = (EditText) findViewById(R.id.UsernameInputL);
        EditText PasswordInput = (EditText) findViewById(R.id.PasswordInput);
        DatabaseReference UserDatabase = FirebaseDatabase.getInstance().getReference();

        UserDatabase.child(UsernameInput.getText().toString()).child("Password").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (BCrypt.checkpw(PasswordInput.getText().toString(), snapshot.getValue().toString())) {
                    Messages.OutputCompletionMessage("Login Sucessful", Context);
                    Intent intent = new Intent(getApplicationContext(), Main.class);
                    intent.putExtra("Username", UsernameInput.getText().toString());
                    startActivity(intent);
                }
                else {
                    Messages.OutputCompletionMessage("Wrong password. Please try again.", Context);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}