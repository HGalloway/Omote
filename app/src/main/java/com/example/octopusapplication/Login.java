package com.example.octopusapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.login);
        LoginButton();
    }
    public String username;
    private void LoginButton(){
        Button LoginButton = (Button) findViewById(R.id.LoginLPage);
        LoginButton.setOnClickListener(v -> {
            Messages Messages = new Messages();
            Context Context = getApplicationContext();

            if (CheckIfInputsAreEmpty(Context, Messages) == false){
                WriteStayLoggedInSetting(Context, Messages);
                CheckUser(Context, Messages);
            }
        });
    }
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
    private void WriteStayLoggedInSetting(Context Context, Messages Messages){
        Switch StayLoggedInSwitch = (Switch) (findViewById(R.id.StayLogedInSwitch));
        Boolean SwitchState = StayLoggedInSwitch.isChecked();
        JSONObject JSONObject = new JSONObject();
        try {
            JSONObject.put("StayLoggedIn", SwitchState);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String userString = JSONObject.toString();
        try {
            File file = new File(Context.getFilesDir(), "Settings.json");
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(userString);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private boolean UserExists = false;
    private boolean CheckUser(Context Context, Messages Messages){
        EditText UsernameInput = (EditText) findViewById(R.id.UsernameInputL);
        EditText PasswordInput = (EditText) findViewById(R.id.PasswordInput);
        DatabaseReference UserDatabase = FirebaseDatabase.getInstance().getReference();

        UserDatabase.child(UsernameInput.getText().toString()).child("Username").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //System.out.println(snapshot.getValue().toString());
                if (snapshot.getValue() == null || UsernameInput.getText().toString().isEmpty()) {
                    Messages.OutputCompletionMessage("User does not exist. Please try again", Context);
                }
                else {
                    CheckPassword(Context, Messages);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return UserExists;
    }

    private void CheckPassword(Context Context, Messages Messages){
        EditText UsernameInput = (EditText) findViewById(R.id.UsernameInputL);
        EditText PasswordInput = (EditText) findViewById(R.id.PasswordInput);
        DatabaseReference UserDatabase = FirebaseDatabase.getInstance().getReference();

        UserDatabase.child(UsernameInput.getText().toString()).child("Password").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if (BCrypt.checkpw(PasswordInput.getText().toString(), snapshot.getValue().toString())) {
                    Messages.OutputCompletionMessage("Login Sucessful", Context);
                    //Move to character screen
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