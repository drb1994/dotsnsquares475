package com.example.squaresgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {
    Button playerOneColorButton, playerTwoColorButton;
    Player playerOne, playerTwo;
    private Button btn;
    private boolean boardSelected = false;
    private boolean savePreferences = false;
    private boolean flipBoard = false;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    Animation pulse;

    Button smallButton;
    Button mediumButton;
    Button largeButton;

    String boardSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Retrieve the players from the bundle
        Intent intent = getIntent();
        Bundle players = intent.getExtras();
        playerOne = (Player) players.get("Player One");
        playerTwo = (Player) players.get("Player Two");

        pulse = AnimationUtils.loadAnimation(this, R.anim.pulse);

        setContentView(R.layout.activity_settings);
        playerOneColorButton = findViewById(R.id.player_one_color_btn);
        playerOneColorButton.setBackgroundColor(getResources().getColor(playerOne.getColor()));
        playerOneColorButton.setOnClickListener(view -> showColorPickerDialog(playerOne, playerTwo.getColor()));

        playerTwoColorButton = findViewById(R.id.player_two_color_btn);
        playerTwoColorButton.setBackgroundColor(getResources().getColor(playerTwo.getColor()));
        playerTwoColorButton.setOnClickListener(view -> showColorPickerDialog(playerTwo, playerOne.getColor()));

        smallButton = findViewById(R.id.small_board_btn);
        mediumButton = findViewById(R.id.medium_board_btn);
        largeButton = findViewById(R.id.large_board_btn);

        smallButton.startAnimation(pulse);
        mediumButton.startAnimation(pulse);
        largeButton.startAnimation(pulse);

        //PREFERENCES
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = prefs.edit();
        System.out.println(prefs.getAll());


        updateBackground();

    }

    public void updateBackground(){
        View bg = findViewById(R.id.background);
        GradientDrawable bgdrawable = (GradientDrawable) bg.getBackground();
        int[] playerColors = new int[]{getResources().getColor(playerOne.getColor()), getResources().getColor(playerTwo.getColor())};
        bgdrawable.setColors(playerColors);
        bg.getBackground().setAlpha(50);
    }



    public void showColorPickerDialog(Player player, int takenColor) {
        FragmentManager fm = getSupportFragmentManager();
        ColorPickerDialogFragment colorPicker = ColorPickerDialogFragment.newInstance(player, takenColor);
        colorPicker.show(fm, null);
    }

    public void updatePlayerColor(Player player) {
        if(player.getPlayerNum() == 1) {
            playerOneColorButton.setBackgroundColor(getResources().getColor(player.getColor()));
        }
        else {
            playerTwoColorButton.setBackgroundColor(getResources().getColor(player.getColor()));
        }
        updateBackground();
    }

    public void onStartGame(View view) {
        if(boardSelected) {
            Intent intent = new Intent(this, GameActivity.class);

            if(savePreferences){
                editor.putInt("p1color", playerOne.getColor());
                editor.putInt("p2color", playerTwo.getColor());
                editor.putBoolean("fb", flipBoard);
                editor.apply();
            }

            //Create a bundle to send players to GameBoardActivity
            Bundle players = new Bundle();
            players.putSerializable("Player One", playerOne);
            players.putSerializable("Player Two", playerTwo);
            intent.putExtras(players);
            intent.putExtra("size", boardSize);
            intent.putExtra("fb", flipBoard);
            startActivity(intent);
        }
    }

    public void onBoardSelect(View view) {
        Button startButton = findViewById(R.id.start_game_btn);

        smallButton.clearAnimation();
        mediumButton.clearAnimation();
        largeButton.clearAnimation();

        switch (view.getId()) {
            case R.id.small_board_btn:
                boardSize = "small";
                //RESET OTHER BTNS
                deselectButton(mediumButton);
                deselectButton(largeButton);
                //SET PRESSED BTN
                selectButton(smallButton, startButton);
                //SET SAVED PREF
                editor.putString("boardsize", boardSize);
                break;
            case R.id.medium_board_btn:
                boardSize = "medium";
                //RESET OTHER BTNS
                deselectButton(smallButton);
                deselectButton(largeButton);
                //SET PRESSED BTN
                selectButton(mediumButton, startButton);
                //SET SAVED PREF
                editor.putString("boardsize", boardSize);
                break;
            case R.id.large_board_btn:
                boardSize = "large";
                //RESET OTHER BTNS
                deselectButton(smallButton);
                deselectButton(mediumButton);
                //SET PRESSED BTN
                selectButton(largeButton, startButton);
                //SET SAVED PREF
                editor.putString("boardsize",boardSize);
                break;
        }
    }

    public void deselectButton(Button button) {
        button.setBackgroundColor(Color.parseColor("#ACACAC"));
        button.setTextColor(Color.parseColor("#000000"));
    }

    public void selectButton(Button buttonSelected, Button startButton) {
        buttonSelected.setBackgroundColor(Color.parseColor("#000000"));
        buttonSelected.setTextColor(Color.parseColor("#FFFFFF"));
        startButton.setBackgroundColor(getResources().getColor(R.color.light_green));
        startButton.startAnimation(pulse);
        boardSelected = true;
    }

    public void onSavePreferences(View view) {
        savePreferences = !savePreferences;
        editor.putBoolean("sp",savePreferences);
    }
    public void onFlipBoard(View view) {
        flipBoard = !flipBoard;
    }

}