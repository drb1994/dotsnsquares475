package com.example.squaresgame;

import static java.util.logging.Logger.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.Button;

import java.util.logging.Logger;

public class SettingsActivity extends AppCompatActivity {
    Button playerOneColorButton;
    Button playerTwoColorButton;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Retrieve the players from the bundle
        Intent intent = getIntent();
        Bundle players = intent.getExtras();
        Player playerOne = (Player) players.get("Player One");
        Player playerTwo = (Player) players.get("Player Two");

        setContentView(R.layout.activity_settings);

        playerOneColorButton = findViewById(R.id.player_one_color_btn);
        playerOneColorButton.setBackgroundColor(getResources().getColor(playerOne.getColor()));
        playerOneColorButton.setOnClickListener(view -> showColorPickerDialog(playerOne, playerTwo.getColor()));

        playerTwoColorButton = findViewById(R.id.player_two_color_btn);
        playerTwoColorButton.setBackgroundColor(getResources().getColor(playerTwo.getColor()));
        playerTwoColorButton.setOnClickListener(view -> showColorPickerDialog(playerTwo, playerOne.getColor()));

        //IF NAV'D FROM SETTINGS BUTTON
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("from");
            if (value.contains("settings")) {
                btn = (Button) findViewById(R.id.start_game_btn);
                btn.setText("Set Rules");
                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        startActivity(new Intent(SettingsActivity.this, MainActivity.class));
                    }
                });
            }
        }


    }

    public void showColorPickerDialog(Player player, int takenColor) {
        FragmentManager fm = getSupportFragmentManager();
        ColorPickerDialogFragment colorPicker = new ColorPickerDialogFragment(player, takenColor);
        colorPicker.show(fm, null);
    }

    public void updatePlayerColor(Player player) {
        if(player.getPlayerNum() == 1) {
            playerOneColorButton.setBackgroundColor(getResources().getColor(player.getColor()));
        }
        else {
            playerTwoColorButton.setBackgroundColor(getResources().getColor(player.getColor()));
        }
    }

    public void onStartGame(View view) {
        Intent intent = new Intent(this, GameBoardActivity.class);
        startActivity(intent);
    }

}