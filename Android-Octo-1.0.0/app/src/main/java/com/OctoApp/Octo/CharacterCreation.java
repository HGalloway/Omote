package com.OctoApp.Octo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

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
    private void SetColorListView() {
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
    private void SetDoneButton() {
        Button Done = findViewById(R.id.Done);
        Done.setOnClickListener(v -> {
            Intent intent = new Intent(this, Main.class);
            intent.putExtra("Username", getIntent().getStringExtra("Username"));
            startActivity(intent);
            Messages Messages = new Messages();
            Messages.OutputCompletionMessage(0, getApplicationContext());
        });
    }

}