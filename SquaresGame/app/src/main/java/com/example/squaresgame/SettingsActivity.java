package com.example.squaresgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {
    Button playerOneColorButton, playerTwoColorButton;
    Player playerOne, playerTwo;
    private Button btn;

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
        //Create a bundle to send players to GameBoardActivity
        Bundle players = new Bundle();
        players.putSerializable("Player One", playerOne);
        players.putSerializable("Player Two", playerTwo);
        intent.putExtras(players);
        startActivity(intent);
    }

    public void onBoardSelect(View view) {
        Button smallButton = findViewById(R.id.small_board_btn);
        Button mediumButton = findViewById(R.id.medium_board_btn);
        Button largeButton = findViewById(R.id.large_board_btn);

        switch (view.getId()) {
            case R.id.small_board_btn:
                //RESET OTHER BTNS
                mediumButton.setBackgroundColor(Color.parseColor("#ACACAC"));
                mediumButton.setTextColor(Color.parseColor("#000000"));
                largeButton.setBackgroundColor(Color.parseColor("#ACACAC"));
                largeButton.setTextColor(Color.parseColor("#000000"));
                //SET PRESSED BTN
                smallButton.setBackgroundColor(Color.parseColor("#000000"));
                smallButton.setTextColor(Color.parseColor("#FFFFFF"));
                System.out.println("small");
                break;
            case R.id.medium_board_btn:
                //RESET OTHER BTNS
                smallButton.setBackgroundColor(Color.parseColor("#ACACAC"));
                smallButton.setTextColor(Color.parseColor("#000000"));
                largeButton.setBackgroundColor(Color.parseColor("#ACACAC"));
                largeButton.setTextColor(Color.parseColor("#000000"));
                //SET PRESSED BTN
                mediumButton.setBackgroundColor(Color.parseColor("#000000"));
                mediumButton.setTextColor(Color.parseColor("#FFFFFF"));
                System.out.println("medium");
                break;
            case R.id.large_board_btn:
                //RESET OTHER BTNS
                smallButton.setBackgroundColor(Color.parseColor("#ACACAC"));
                smallButton.setTextColor(Color.parseColor("#000000"));
                mediumButton.setBackgroundColor(Color.parseColor("#ACACAC"));
                mediumButton.setTextColor(Color.parseColor("#000000"));
                //SET PRESSED BTN
                largeButton.setBackgroundColor(Color.parseColor("#000000"));
                largeButton.setTextColor(Color.parseColor("#FFFFFF"));
                System.out.println("large");
                break;
        }
    }

}