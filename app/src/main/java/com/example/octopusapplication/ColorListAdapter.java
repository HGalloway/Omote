package com.example.octopusapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.octopusapplication.R.*;

public class ColorListAdapter extends ArrayAdapter {
    private Context Context;
    private List<MoodAndColor> MoodList;

    public ColorListAdapter(Context context, ArrayList<MoodAndColor> list) {
        super(context, 0 , list);
        Context = context;
        MoodList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View ConvertView, @NonNull ViewGroup parent) {
        View ListItem = LayoutInflater.from(Context).inflate(layout.colorlist, parent, false);
        ImageView OctoImageView = (ImageView) ListItem.findViewById(id.OctoImageView);
        OctoImageView.setImageResource(drawable.topleft);

        TextView MoodName = (TextView) ListItem.findViewById(id.MoodName);
        MoodAndColor MoodAndColor = new MoodAndColor(0, null);
        MoodName.setText(MoodAndColor.Moods[position]);

        MoodAndColor.SetColorsArray();

        Spinner ColorSpinner = (Spinner) ListItem.findViewById(id.ColorSpinner);
        ArrayAdapter<String> ArrayAdapter = new ArrayAdapter<String>(Context, android.R.layout.simple_spinner_dropdown_item, MoodAndColor.Colors);
        ArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        ColorSpinner.setAdapter(ArrayAdapter);

        ColorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String Item = parent.getItemAtPosition(position).toString();
                Toast.makeText(Context, MoodName.getText().toString() + ": " + Item, Toast.LENGTH_SHORT).show();
                ColorSpinner.setPrompt(Item);
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return ListItem;
    }


    @Override
    public int getCount() {
        return new MoodAndColor(0,null).Moods.length;
    }
}
