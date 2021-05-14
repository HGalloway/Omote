package com.example.octopusapplication;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

public class MoodAndColor {
    public final String[] Moods = {"Happy", "Sad", "Angry", "Neutral"};
    public List<String> Colors = new ArrayList<>();
    public final String[] ColorsList = {"Red", "Orange", "Yellow", "White", "Black", "Light Blue", "Blue", "Pink", "Purple"};
    public final Hashtable<String, Integer> PictureList = new Hashtable<>();

    private int ImageDrawable;
    private String Mood;

    public MoodAndColor(int ImageDrawable, String Mood) {
        this.ImageDrawable = ImageDrawable;
        this.Mood = Mood;
    }

    public void SetColorsArray(){
        for (int i = 0; i < ColorsList.length; i++) {
            Colors.add(ColorsList[i]);
        }
    }

    public void SetColorDictionary(){
        int[] Drawables = {R.drawable.red, R.drawable.orange, R.drawable.yellow, R.drawable.white, R.drawable.black, R.drawable.light_blue, R.drawable.blue, R.drawable.pink, R.drawable.purple};
        for (int i = 0; i < Drawables.length; i++) {
            PictureList.put(ColorsList[i], Drawables[i]);
        }
    }
}
