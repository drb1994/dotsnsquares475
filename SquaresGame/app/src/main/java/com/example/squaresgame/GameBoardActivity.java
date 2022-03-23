package com.example.squaresgame;

import android.app.AlertDialog;

import android.content.Intent;


import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GameBoardActivity extends AppCompatActivity {
    ImageButton undo_last_move, pause_button, tutorial_button;
    TextView player_one_score, player_two_score;
    ImageView game_board;

    Integer undo = 0, currentTurn = 0;

    SharedPreferences prefs;
    Player playerOne, playerTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        prefs = getSharedPreferences("sharedprefs", MODE_PRIVATE);

        Intent intent = getIntent();
        Bundle players = intent.getExtras();
        playerOne = (Player) players.get("Player One");
        playerTwo = (Player) players.get("Player Two");

        undo_last_move = findViewById(R.id.ib_undo_button);
        pause_button = findViewById(R.id.ib_pause_menu);
        tutorial_button = findViewById(R.id.ib_tutorial_button);
        player_one_score = findViewById(R.id.tv_player_one_score);
        player_two_score = findViewById(R.id.tv_player_two_score);
        game_board = findViewById(R.id.iv_game_board);

        changeColor();

        // Undo button
        undo_last_move.setOnClickListener(view -> {
            undoButtonFunctionality();
        });
        // End of undo button

        // Pause button functionality
        pause_button = findViewById(R.id.ib_pause_menu);
        pause_button.setOnClickListener(view -> {
            FragmentManager fm = getSupportFragmentManager();
            PauseDialogFragment pauseMenu = new PauseDialogFragment(playerOne, playerTwo);
            pauseMenu.show(fm, null);
        });
        //End of pause button functionality

        if(prefs.getBoolean("fb",false)){
            game_board.setOnClickListener(view -> {
                changeColor();
                undo = 1;
            });
        } else {
            game_board.setOnClickListener(view -> {
                changeScreenOrientation();
                changeColor();
                undo = 1;
            });
        }
    }

    public void changeScreenOrientation(){
        int orientation = getWindowManager().getDefaultDisplay().getRotation();
        if (orientation == android.view.Surface.ROTATION_0) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
        }
        else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    public void changeColor(){
        if(currentTurn == 0){
            player_one_score.setBackgroundColor(getResources().getColor(playerOne.getColor()));
            player_one_score.setTextColor(getResources().getColor(R.color.white));
            player_two_score.setBackgroundColor(getResources().getColor(R.color.white));
            player_two_score.setTextColor(getResources().getColor(R.color.black));
        }
        if(currentTurn == 1){
            player_one_score.setBackgroundColor(getResources().getColor(R.color.white));
            player_one_score.setTextColor(getResources().getColor(R.color.black));
            player_two_score.setBackgroundColor(getResources().getColor(playerTwo.getColor()));
            player_two_score.setTextColor(getResources().getColor(R.color.white));
        }
        if(currentTurn == 0){
            currentTurn = 1;
        } else {
            currentTurn = 0;
        }
    }

    public void undoButtonFunctionality(){
        if(undo == 1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("Are you sure you want to undo the last move?")
                    .setPositiveButton("OK", (dialogInterface, i) -> {
                        // Everything that needs to be undone goes here
                        changeColor();
                        changeScreenOrientation();
                        undo -= 1;
                    }).setNegativeButton("Cancel", null);

            AlertDialog alert = builder.create();
            alert.show();
        }
        else if (undo == 0){
            Toast toast = Toast.makeText(this, "You can only undo the previous move", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void onTutorial(View view) {
        Intent intent = new Intent(this, TutorialActivity.class);
        startActivity(intent);
    }
}
