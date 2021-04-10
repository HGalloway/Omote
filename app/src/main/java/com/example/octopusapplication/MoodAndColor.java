package com.example.octopusapplication;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MoodAndColor {
    public final String[] Moods = {"Happy", "Sad", "Angry", "Neutral"};
    public List<String> Colors = new ArrayList<>();
    public final String[] ColorsList = {"Red", "Orange", "Yellow", "White", "Black", "Light Blue", "Blue", "Pink", "Purple"};

    private int ImageDrawable;
    private String Mood;

    public MoodAndColor(int ImageDrawable, String Mood) {
        this.ImageDrawable = ImageDrawable;
        this.Mood = Mood;
    }

    public void SetColorsArray(){
        for (int i = 0; i < ColorsList.length; ++i) {
            Colors.add(ColorsList[i]);
        }
    }
}
