package com.example.squaresgame;

import android.app.AlertDialog;

import android.content.Intent;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.preference.PreferenceManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class GameActivity extends AppCompatActivity {
    ImageButton undo_last_move, pause_button, tutorial_button;
    TextView player_one_score_view, player_two_score_view;
    FrameLayout board_container;

    ImageView sq1,sq2,sq3;

    Integer undo = 0, currentTurn = 1;

    int player_one_score = 0, player_two_score = 0;

    Player playerOne, playerTwo;
    String boardSize;

    HashMap<String, Boolean> gameboard = new HashMap<String, Boolean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        Intent intent = getIntent();
        Bundle players = intent.getExtras();
        playerOne = (Player) players.get("Player One");
        playerTwo = (Player) players.get("Player Two");

        if(intent.getStringExtra("size") != null) {
            boardSize = intent.getStringExtra("size");
        }

        undo_last_move = findViewById(R.id.ib_undo_button);
        pause_button = findViewById(R.id.ib_pause_menu);
        tutorial_button = findViewById(R.id.ib_tutorial_button);
        player_one_score_view = findViewById(R.id.tv_player_one_score);
        player_two_score_view = findViewById(R.id.tv_player_two_score);
        board_container = findViewById(R.id.board_container);

        //changeColor();
        initBoard();
        gameStart();

        Fragment gameBoard = new BoardFragment(getSize());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.board_container, gameBoard)
                .commit();

        // Undo button functionality
        undo_last_move.setOnClickListener(view -> {
            if(undo == 1) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setMessage("Are you sure you want to undo the last move?")
                        .setPositiveButton("OK", (dialogInterface, i) -> {
                            changeTurn();
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
//        game_board.setOnClickListener(view -> {
//            changeColor();
//            undo = 1;
//        });
    }
    public void gameStart(){
        player_one_score_view.setBackgroundColor(getResources().getColor(playerOne.getColor()));
    }
    public void changeTurn(){
        if(currentTurn == 2){
            player_one_score_view.setBackgroundColor(getResources().getColor(playerOne.getColor()));
            player_one_score_view.setTextColor(getResources().getColor(R.color.white));
            player_two_score_view.setBackgroundColor(getResources().getColor(R.color.white));
            player_two_score_view.setTextColor(getResources().getColor(R.color.black));
        }
        if(currentTurn == 1){
            player_one_score_view.setBackgroundColor(getResources().getColor(R.color.white));
            player_one_score_view.setTextColor(getResources().getColor(R.color.black));
            player_two_score_view.setBackgroundColor(getResources().getColor(playerTwo.getColor()));
            player_two_score_view.setTextColor(getResources().getColor(R.color.white));
        }
        if(currentTurn == 1){
            currentTurn = 2;
        } else {
            currentTurn = 1;
        }
    }
    public void onTutorial(View view) {
        Intent intent = new Intent(this, TutorialActivity.class);
        startActivity(intent);
    }

    public String getSize() {
        SharedPreferences prefs;
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if(!prefs.getAll().isEmpty()) {
            boardSize = prefs.getString("boardsize", "small");
        }
        return boardSize;
    }
    public void onMoveSelect(View view){
        if(currentTurn == 1 && !gameboard.get(String.valueOf(view.getTag()))){
            view.setBackgroundColor(getResources().getColor(playerOne.getColor()));
            gameboard.put(String.valueOf(view.getTag()), true);
            checkForCompletedSquare();
            changeTurn();
        }else if(currentTurn == 2 && !gameboard.get(String.valueOf(view.getTag()))){
            view.setBackgroundColor(getResources().getColor(playerTwo.getColor()));
            gameboard.put(String.valueOf(view.getTag()), true);
            checkForCompletedSquare();
            changeTurn();
        }
    }
    public void initBoard(){
        //squares
        gameboard.put("sq1", false);
        gameboard.put("sq2", false);
        gameboard.put("sq3", false);
        gameboard.put("sq4", false);
        gameboard.put("sq5", false);
        gameboard.put("sq6", false);
        //horizontal
        gameboard.put("h1-1", false);
        gameboard.put("h1-2", false);
        gameboard.put("h2-1", false);
        gameboard.put("h2-2", false);
        gameboard.put("h3-1", false);
        gameboard.put("h3-2", false);
        gameboard.put("h4-1", false);
        gameboard.put("h4-2", false);
        //vertical
        gameboard.put("v1-1", false);
        gameboard.put("v1-2", false);
        gameboard.put("v1-3", false);
        gameboard.put("v2-1", false);
        gameboard.put("v2-2", false);
        gameboard.put("v2-3", false);
        gameboard.put("v3-1", false);
        gameboard.put("v3-2", false);
        gameboard.put("v3-3", false);
        if(boardSize.equalsIgnoreCase("medium") || boardSize.equalsIgnoreCase("large")){
            //squares
            gameboard.put("sq7", false);
            gameboard.put("sq8", false);
            gameboard.put("sq9", false);
            gameboard.put("sq10", false);
            gameboard.put("sq11", false);
            gameboard.put("sq12", false);
            //horizontal
            gameboard.put("h1-3", false);
            gameboard.put("h2-3", false);
            gameboard.put("h3-3", false);
            gameboard.put("h4-3", false);
            gameboard.put("h5-1", false);
            gameboard.put("h5-2", false);
            gameboard.put("h5-3", false);
            //vertical
            gameboard.put("v1-4", false);
            gameboard.put("v2-4", false);
            gameboard.put("v3-4", false);
            gameboard.put("v4-1", false);
            gameboard.put("v4-2", false);
            gameboard.put("v4-3", false);
            gameboard.put("v4-4", false);
        }
        else if(boardSize.equalsIgnoreCase("large")){

        }
    }
    public void checkForCompletedSquare(){
        ImageView sq1,sq2,sq3;
        if(boardSize.equalsIgnoreCase("small")){
            squareChecker("sq1","h1-1","h2-1","v1-1","v1-2");
            squareChecker("sq2","h1-2","h2-2","v1-2","v1-3");
            squareChecker("sq3","h2-1","h3-1","v2-1","v2-2");
            squareChecker("sq4","h2-2","h3-2","v2-2","v2-3");
            squareChecker("sq5","h3-1","h4-1","v3-1","v3-2");
            squareChecker("sq6","h3-2","h4-2","v3-2","v3-3");
        }
        if(boardSize.equalsIgnoreCase("medium")){
            squareChecker("sq1","h1-1","h2-1","v1-1","v1-2");
            squareChecker("sq2","h1-2","h2-2","v1-2","v1-3");
            squareChecker("sq3","h1-3","h2-3","v1-3","v1-4");
            squareChecker("sq4","h2-1","h3-1","v2-1","v2-2");
            squareChecker("sq5","h2-2","h3-2","v2-2","v2-3");
            squareChecker("sq6","h2-3","h3-3","v2-3","v2-4");
            squareChecker("sq7","h3-1","h4-1","v3-1","v3-2");
            squareChecker("sq8","h3-2","h4-2","v3-2","v3-3");
            squareChecker("sq9","h3-3","h4-3","v3-3","v3-4");
            squareChecker("sq10","h4-1","h5-1","v4-1","v4-2");
            squareChecker("sq11","h4-2","h5-2","v4-2","v4-3");
            squareChecker("sq12","h4-3","h5-3","v4-3","v4-4");
        }

    }
    public void squareChecker(String square, String top, String bottom, String left, String right){
        if(!gameboard.get(square) && (gameboard.get(top) && gameboard.get(bottom)
        && gameboard.get(left) && gameboard.get(right))){
            if(currentTurn == 1){
                player_one_score++;
            }else if(currentTurn == 2){
                player_two_score++;
            }
            gameboard.put(square, true);
            updateScores();
        }
    }
    public void updateScores(){
        player_one_score_view = findViewById(R.id.tv_player_one_score);
        player_two_score_view = findViewById(R.id.tv_player_two_score);
        player_one_score_view.setText(String.valueOf(player_one_score));
        player_two_score_view.setText(String.valueOf(player_two_score));
    }
}
