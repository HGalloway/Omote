package com.OctoApp.Octo;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import com.OctoApp.Octo.R.layout;
import com.OctoApp.Octo.R.id;

public class ColorListAdapter extends ArrayAdapter {
    private final Context Context;
    private final List<MoodAndColor> MoodList;
    private final DatabaseReference UserDatabase = FirebaseDatabase.getInstance().getReference();
    private final String Username;

    public ColorListAdapter(Context context, ArrayList<MoodAndColor> list, String IntentUsername) {
        super(context, 0, list);
        Context = context;
        MoodList = list;
        Username = IntentUsername;
    }

    @NonNull
    @Override
    public View getView(int Position, @Nullable View ConvertView, @NonNull ViewGroup parent) {
        MoodAndColor MoodAndColor = new MoodAndColor(0, null);
        View ListItem = LayoutInflater.from(Context).inflate(layout.colorlist, parent, false);

        MoodAndColor.SetColorDictionary();

        SetUpHeaderImage(ListItem, MoodAndColor, "White");
        SetTitleText(ListItem, MoodAndColor, Position);
        SetSpinner(ListItem, MoodAndColor, Position);
        System.out.println(Username);
        return ListItem;
    }

    /**
     * @param ListItem
     * @param MoodAndColor <p>Sets header image</p>
     */
    private void SetUpHeaderImage(View ListItem, MoodAndColor MoodAndColor, String Color) {
        ImageView OctoImageView = ListItem.findViewById(id.OctoImageView);
        OctoImageView.setImageResource(MoodAndColor.PictureList.get(Color));
    }

    /**
     * @param ListItem
     * @param MoodAndColor
     * @param Position     <p<>Sets the Title text on the card</p>
     */
    private void SetTitleText(View ListItem, MoodAndColor MoodAndColor, int Position) {
        TextView MoodName = ListItem.findViewById(id.MoodName);
        MoodName.setText(MoodAndColor.Moods[Position]);
    }

    /**
     * @param ListItem
     * @param MoodAndColor
     * @param Position     <p>Sets the spinner on the card with all of the different colors</p>
     */
    private void SetSpinner(View ListItem, MoodAndColor MoodAndColor, int Position) {
        MoodAndColor.SetColorsArray();

        Spinner ColorSpinner = ListItem.findViewById(id.ColorSpinner);
        ArrayAdapter<String> ArrayAdapter = new ArrayAdapter<String>(Context, android.R.layout.simple_spinner_dropdown_item, MoodAndColor.Colors);
        ArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ColorSpinner.setAdapter(ArrayAdapter);

        ColorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onItemSelected(AdapterView<?> parent, View view, int ItemPosition, long id) {
                String Item = parent.getItemAtPosition(ItemPosition).toString();
                SignUp SignUp = new SignUp();
                for (int i = 0; i < MoodAndColor.ColorsList.length; i++) {
                    if (Item == MoodAndColor.ColorsList[i]) {
                        UserDatabase.child(Username).child("Mood&Color").child(MoodAndColor.Moods[Position]).setValue(MoodAndColor.ColorsList[i]);
                    }
                }
                SetUpHeaderImage(ListItem, MoodAndColor, Item);
                ColorSpinner.setPrompt(Item);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public int getCount() {
        return new MoodAndColor(0, null).Moods.length;
    }
}
