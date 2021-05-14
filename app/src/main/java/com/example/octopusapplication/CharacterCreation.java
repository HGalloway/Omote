package com.example.octopusapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Hashtable;

public class CharacterCreation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.character_creation);
        SetColorListView();
        SetDoneButton();
    }

    /**
     * <p>Sets the Color ListView. Allows the user to pick the color of the octopus to the mood.</p>
     */
    private void SetColorListView(){
        ListView ColorListView = findViewById(R.id.ColorList);
        MoodAndColor MoodAndColor = new MoodAndColor(0, null);

        MoodAndColor.SetColorsArray();
        final ArrayList<MoodAndColor> ArrayList = new ArrayList<>();
        for (int i = 0; i < MoodAndColor.ColorsList.length; ++i) {
            ArrayList.add(new MoodAndColor(R.drawable.topleft, MoodAndColor.Colors.get(i)));
        }

        Context Context = getApplicationContext();
        ColorListAdapter ListAdapter = new ColorListAdapter(Context, ArrayList, getIntent().getStringExtra("Username"));
        ColorListView.setAdapter(ListAdapter);
    }

    /**
     * <p>Sets the Done Button. Moves to the next screen when clicked.</p>
     */
    private void SetDoneButton(){
        Button Done = findViewById(R.id.Done);
        Done.setOnClickListener(v -> {
            Intent intent = new Intent(this, Main.class);
            intent.putExtra("Username", getIntent().getStringExtra("Username"));
            startActivity(intent);
        });
    }

}