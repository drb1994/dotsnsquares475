package com.example.squaresgame;

import android.app.AlertDialog;

import android.content.Intent;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.view.Gravity;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GameBoardActivity extends AppCompatActivity {
    ImageButton undo_last_move, pause_button, tutorial_button;
    TextView player_one_score, player_two_score;
    ImageView game_board;

    Integer undo = 1, currentTurn = 0;

    Player playerOne, playerTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

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

        // Undo button functionality
        undo_last_move.setOnClickListener(view -> {
            if(undo == 1) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setMessage("Are you sure you want to undo the last move?")
                        .setPositiveButton("OK", (dialogInterface, i) -> {
                            changeColor();
                            undo -= 1;
                        }).setNegativeButton("Cancel", null);

                AlertDialog alert = builder.create();
                alert.show();
            }
            else if (undo == 0){
                Toast toast = Toast.makeText(this, "You can only undo the previous move", Toast.LENGTH_LONG);
                toast.show();
            }
        });
        // End of undo button functionality

        // Pause button functionality
        pause_button = findViewById(R.id.ib_pause_menu);
        pause_button.setOnClickListener(view -> {
            FragmentManager fm = getSupportFragmentManager();
            PauseDialogFragment pauseMenu = new PauseDialogFragment(playerOne, playerTwo);
            pauseMenu.show(fm, null);
        });
        //End of pause button functionality

        game_board.setOnClickListener(view -> {
            changeColor();
            undo = 1;
        });
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
}
