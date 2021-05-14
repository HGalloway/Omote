package com.example.octopusapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;

import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.Observable;

public class Main extends AppCompatActivity {
    private final HashMap<String, String> MoodsAndColors = new HashMap<>();
    private String Username;
    private String CurrentMood;
    boolean init = false;
    private HashMap<String, String> FriendsAndTokens = new HashMap<>();
    private NotificationHandler NotificationHandler;


    @Override
    protected synchronized void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.main);

        Username = getIntent().getStringExtra("Username");

        MoodAndColor MoodAndColor = new MoodAndColor(0, null);
        MoodAndColor.SetColorDictionary();
        DatabaseReference UserDatabase = FirebaseDatabase.getInstance().getReference();

        NotificationHandler = new NotificationHandler();
        NotificationHandler.NotificationListener(Username, getApplicationContext());



        SetMoodsAndColors(MoodAndColor, UserDatabase);
        SetNavButton();
        SetNavigationView();
        ClickConstraintLayout();
    }

    /**
     * @param MoodAndColor
     * @param UserDatabase
     * <p>Sets the MoodAndColor Dictionary with the values in the database.</p>
     */
    private void SetMoodsAndColors(MoodAndColor MoodAndColor, DatabaseReference UserDatabase) {
        for(int i=0; i < MoodAndColor.Moods.length; i++){
            UserDatabase.child(Username).child("Mood&Color").child(MoodAndColor.Moods[i]).addListenerForSingleValueEvent(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    MoodsAndColors.put(dataSnapshot.getKey(), Objects.requireNonNull(dataSnapshot.getValue()).toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
            Thread WaitThread = new Thread(){
                @Override
                public void run() {
                   
                    while(MoodsAndColors.isEmpty()){
                        if (!MoodsAndColors.isEmpty()){
                            break;
                        }
                    }
                    GetCurrentMood(MoodAndColor, UserDatabase);
                    while (CurrentMood == null){
                        if (CurrentMood != null){
                            break;
                        }
                    }
                    runOnUiThread(() -> {
                        SetSpinner(MoodAndColor, UserDatabase);
                        GetFriendsAndTokens(UserDatabase);

                    });
                    init = true;
                }
            };
            WaitThread.start();
        }
    }

    /**
     * @param MoodAndColor
     * @param UserDatabase
     * <p>Gets the current mood from the database and sets it with the SetCurrentMood method.</p>
     */
    private void GetCurrentMood(MoodAndColor MoodAndColor, DatabaseReference UserDatabase){
        UserDatabase.child(Username).child("CurrentMood").addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() == true){
                    SetCurrentMood(MoodAndColor, dataSnapshot.getValue().toString(), UserDatabase);
                }
                else if(dataSnapshot.exists() == false){
                    SetCurrentMood(MoodAndColor, "Happy", UserDatabase);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    /**
     * @param MoodAndColor
     * @param Mood
     * @param UserDatabase
     * <p>Sets the current mood given to the method. Sets the value in the database and sets the image to the corresponding color.</p>
     */
    private void SetCurrentMood(MoodAndColor MoodAndColor, String Mood, DatabaseReference UserDatabase){
        CurrentMood = Mood;
        UserDatabase.child(Username).child("CurrentMood").setValue(Mood);
        SetImage(MoodAndColor, MoodsAndColors.get(Mood));
    }

    /**
     * <p>Sets NavButton. If click it will open the navigation view.</p>
     */
    private void SetNavButton(){
        ImageButton NavButton = findViewById(R.id.NavButton);
        NavigationView NavigationView = findViewById(R.id.Navigation);
        NavButton.setOnClickListener(v -> {
            NavigationView.setVisibility(View.VISIBLE);
        });
    }

    /**
     * <p>Sets an onClickListener for the Constraint layout. This allows the user to get out of the navigation view if needed.</p>
     */
    private void ClickConstraintLayout(){
        ConstraintLayout ConstraintLayout = findViewById(R.id.MainConstraintLayout);
        NavigationView NavigationView = findViewById(R.id.Navigation);
        ConstraintLayout.setOnClickListener(v -> {
            NavigationView.setVisibility(View.GONE);
        });
    }

    /**
     * <p>Sets the NavigationView Buttons. Sends the user to the corresponding page when the buttons are clicked.</p>
     */
    private void SetNavigationView(){
        Button Profile = findViewById(R.id.Profile);
        Profile.setOnClickListener(v -> {
            Intent intent = new Intent(this, Profile.class);
            intent.putExtra("Username", Username);
            startActivity(intent);
        });
        Button Friends = findViewById(R.id.Friends);
        Friends.setOnClickListener(v -> {
            Intent intent = new Intent(this, Friends.class);
            intent.putExtra("Username", Username);
            startActivity(intent);
        });
        Button About = findViewById(R.id.About);
        About.setOnClickListener(v -> {
            Intent intent = new Intent(this, About.class);
            intent.putExtra("Username", Username);
            startActivity(intent);
        });
    }

    /**
     * @param MoodAndColor
     * @param UserDatabase
     * <p>Sets the MoodSpinner. If an item is selected then the current mood is set to the selected item and the prompt is changed.</p>
     */
    private void SetSpinner(MoodAndColor MoodAndColor, DatabaseReference UserDatabase){
        Spinner MoodSpinner = findViewById(R.id.MoodSpinner);
        ArrayAdapter<String> ArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, MoodAndColor.Moods);
        MoodSpinner.setAdapter(ArrayAdapter);
        MoodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        Messages Messages = new Messages();
        int check = 0;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(check);
                if(check > 0) {
                    SetCurrentMood(MoodAndColor, parent.getItemAtPosition(position).toString(), UserDatabase);
                    for(int i= 0; i < MoodSpinner.getAdapter().getCount(); i++)
                    {
                        if(MoodSpinner.getAdapter().getItem(i).toString().contains(CurrentMood))
                        {
                            MoodSpinner.setSelection(i);
                            try {
                                SendMoodChangedNotificationToFriends();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        check++;
                    }
                    Messages.OutputCompletionMessage("Mood selected: " + CurrentMood, getApplicationContext());
                }
                else{
                    for(int i= 0; i < MoodSpinner.getAdapter().getCount(); i++)
                    {
                        if(MoodSpinner.getAdapter().getItem(i).toString().contains(CurrentMood))
                        {
                            MoodSpinner.setSelection(i);
                        }
                    }
                    Messages.OutputCompletionMessage("Mood selected: " + CurrentMood, getApplicationContext());
                    check++;
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }
    private void SendMoodChangedNotificationToFriends() throws IOException {
        for (int i = 0; i < FriendsAndTokens.size(); i++){
            NotificationHandler.SendNotificationToToken(FriendsAndTokens.get(FriendsAndTokens.entrySet().iterator().next().getKey()), Username + "'s mood changed!", Username + "'s mood changed to " + CurrentMood + ".", getApplicationContext());

        }
    }
    private void SetImage(MoodAndColor MoodAndColor, String Item){
        ImageView OctoImage = findViewById(R.id.OctoImage);
        OctoImage.setImageResource(MoodAndColor.PictureList.get(Item));
    }

    private void GetFriendsAndTokens(DatabaseReference UserDatabase){
        UserDatabase.child(Username).child("Friends").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String[] Friends = snapshot.getValue().toString().split("\\s+");
                for (int i = 0; i < Friends.length; i++){
                    int finalI = i;
                    UserDatabase.child(Friends[i]).child("Token").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            FriendsAndTokens.put(Friends[finalI], Objects.requireNonNull(snapshot.getValue()).toString());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}