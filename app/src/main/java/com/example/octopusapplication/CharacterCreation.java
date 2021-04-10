package com.example.octopusapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class CharacterCreation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.character_creation);

        ListView ColorListView = (ListView) findViewById(R.id.ColorList);
        MoodAndColor MoodAndColor = new MoodAndColor(0, null);

        MoodAndColor.SetColorsArray();
        final ArrayList<MoodAndColor> ArrayList = new ArrayList<>();
        for (int i = 0; i < MoodAndColor.ColorsList.length; ++i) {
            ArrayList.add(new MoodAndColor(R.drawable.topleft, MoodAndColor.Colors.get(i)));
        }

        //final ArrayAdapter ListAdapter = new ArrayAdapter(this, R.layout.colorlistline, ArrayList);
        Context Context = getApplicationContext();
        ColorListAdapter ListAdapter = new ColorListAdapter(Context, ArrayList);
        ColorListView.setAdapter(ListAdapter);

    }
}