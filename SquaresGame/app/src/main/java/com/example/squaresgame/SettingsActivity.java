package com.example.squaresgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
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

    String boardSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Retrieve the players from the bundle
        Intent intent = getIntent();
        Bundle players = intent.getExtras();
        playerOne = (Player) players.get("Player One");
        playerTwo = (Player) players.get("Player Two");

        setContentView(R.layout.activity_settings);

        playerOneColorButton = findViewById(R.id.player_one_color_btn);
        playerOneColorButton.setBackgroundColor(getResources().getColor(playerOne.getColor()));
        playerOneColorButton.setOnClickListener(view -> showColorPickerDialog(playerOne, playerTwo.getColor()));

        playerTwoColorButton = findViewById(R.id.player_two_color_btn);
        playerTwoColorButton.setBackgroundColor(getResources().getColor(playerTwo.getColor()));
        playerTwoColorButton.setOnClickListener(view -> showColorPickerDialog(playerTwo, playerOne.getColor()));

        //PREFERENCES
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = prefs.edit();
        System.out.println(prefs.getAll());
        //IF NAV'D FROM SETTINGS BUTTON
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("from");
            if (value.contains("settings")) {
                btn = (Button) findViewById(R.id.start_game_btn);
                btn.setText("Set Rules");
                btn.setOnClickListener(v -> {
                    if(boardSelected) {
                        Intent intent1 = new Intent(SettingsActivity.this, MainActivity.class);

                        if(savePreferences){
                            editor.putInt("p1color", playerOne.getColor());
                            editor.putInt("p2color", playerTwo.getColor());
                            editor.putBoolean("fb", flipBoard);
                            editor.apply();
                        }

                        //Create a bundle to send players to GameBoardActivity
                        Bundle players1 = new Bundle();
                        players1.putSerializable("Player One", playerOne);
                        players1.putSerializable("Player Two", playerTwo);
                        intent1.putExtras(players1);
                        intent1.putExtra("size", boardSize);
                        startActivity(intent1);
                    }
                });
            }
        }
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
            startActivity(intent);
        }
    }

    public void onBoardSelect(View view) {
        Button smallButton = findViewById(R.id.small_board_btn);
        Button mediumButton = findViewById(R.id.medium_board_btn);
        Button largeButton = findViewById(R.id.large_board_btn);
        Button startButton = findViewById(R.id.start_game_btn);

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
        startButton.setBackgroundColor(getResources().getColor(R.color.green3));
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