package com.example.octopusapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.widget.Button;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private JSONObject Settings;
    private Boolean StayLoggedIn;
    private String Username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        try {
            GetLoggedInSetting();
            CheckUserInput();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>Gets the StayLoggedIn variable from the Settings.json file.</p>
     */
    private void GetLoggedInSetting() throws IOException, JSONException{
        File SettingsFile = new File("/data/data/com.example.octopusapplication/files/Settings.json");
        if (SettingsFile.exists()){
            JSONHandler JSONHandler = new JSONHandler();
            Settings = JSONHandler.ReadJSONFile(getApplicationContext(), "Settings");
            StayLoggedIn = Settings.getBoolean("StayLoggedIn");
            Username = Settings.getString("Username");
        }
        if (!SettingsFile.exists()) {
            StayLoggedIn = false;
        }
    }

    /**
     * <p> Checks if the Settings.json file has been made and if it hasn't or if the variable StayLoggedIn equals false then it will go into MainActivity. If true then it will go into the main page.</p>
     */
    private void CheckUserInput() throws IOException, JSONException {
        if (StayLoggedIn.equals(false)) {
            Button SignUp = findViewById(R.id.SignUp);
            SignUp.setOnClickListener(v -> {
                Intent intent = new Intent(this, SignUp.class);
                startActivity(intent);
            });

            Button Login = findViewById(R.id.Login);
            Login.setOnClickListener(v -> {
                Intent intent = new Intent(this, Login.class);
                startActivity(intent);
            });
        }
        else if(StayLoggedIn.equals(true)){
            System.out.println(Username);
            Intent intent = new Intent(this, Main.class);
            intent.putExtra("Username", Username);
            startActivity(intent);
        }
    }

}