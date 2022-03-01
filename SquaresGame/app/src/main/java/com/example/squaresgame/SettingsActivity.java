package com.example.squaresgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button playerOneColorButton = findViewById(R.id.player_one_color_btn);
        playerOneColorButton.setOnClickListener(view -> showColorPickerDialog());
        Button playerTwoColorButton = findViewById(R.id.player_two_color_btn);
        playerTwoColorButton.setOnClickListener(view -> showColorPickerDialog());
    }

    public void showColorPickerDialog() {
        FragmentManager fm = getSupportFragmentManager();
        ColorPickerDialogFragment colorPicker = new ColorPickerDialogFragment();
        colorPicker.show(fm, null);
    }
}