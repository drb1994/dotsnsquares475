package com.example.squaresgame;

import android.annotation.SuppressLint;
import android.app.AlertDialog;

import android.content.Intent;


import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.preference.PreferenceManager;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

    ImageView line1;
    View parent;

    Integer undo = 0, currentTurn = 1, currentSquare = 0;
    int previousScore;
    Boolean doubleScore = false;
    Boolean flipBoard;

    int player_one_score = 0, player_two_score = 0;

    Player playerOne, playerTwo;
    String boardSize;
    int squares;

    String previousLine;
    String[] previousSquare = new String[2], previousSquareID = new String[2];

    SharedPreferences prefs;

    HashMap<String, Boolean> gameboard = new HashMap<>();
    Boolean pointScored = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        Intent intent = getIntent();
        Bundle players = intent.getExtras();
        playerOne = (Player) players.get("Player One");
        playerTwo = (Player) players.get("Player Two");
        flipBoard = intent.getBooleanExtra("fb", false);

        if(!prefs.getAll().isEmpty()) {
            boardSize = prefs.getString("boardsize", "small");
            flipBoard = prefs.getBoolean("fb", false);
        }

        if(intent.getStringExtra("size") != null) {
            boardSize = intent.getStringExtra("size");
        }

        squares = getSquares(boardSize);

        undo_last_move = findViewById(R.id.ib_undo_button);
        pause_button = findViewById(R.id.ib_pause_menu);
        tutorial_button = findViewById(R.id.ib_tutorial_button);
        player_one_score_view = findViewById(R.id.tv_player_one_score);
        player_two_score_view = findViewById(R.id.tv_player_two_score);
        board_container = findViewById(R.id.board_container);

        //changeColor();
        initBoard();
        gameStart();
        updateBackground();


        line1 = findViewById(R.id.connect_1_5);

//        Fragment gameBoard = new BoardFragment(getSize());
        Fragment gameBoard = BoardFragment.newInstance(getSize());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.board_container, gameBoard)
                .commit();

        undo_last_move.setOnClickListener(view -> undoMoveAlertDialog());
        // End of undo button functionality

        // Pause button functionality
        pause_button = findViewById(R.id.ib_pause_menu);
        pause_button.setOnClickListener(view -> {
            FragmentManager fm = getSupportFragmentManager();
            PauseDialogFragment pauseMenu = new PauseDialogFragment(playerOne, playerTwo, boardSize);
            pauseMenu.show(fm, null);
        });
        //End of pause button functionality
    }

    public void updateBackground(){
        View bg = findViewById(R.id.background);
        GradientDrawable bgdrawable = (GradientDrawable) bg.getBackground();

        if(currentTurn == 1){
            int[] playerOneColors = new int[]{getResources().getColor(playerOne.getColor()), getResources().getColor(R.color.white)};
            bgdrawable.setColors(playerOneColors);

        }else if(currentTurn == 2){
            int[] playerTwoColors = new int[]{getResources().getColor(R.color.white), getResources().getColor(playerTwo.getColor())};
            bgdrawable.setColors(playerTwoColors);
        }
        bg.getBackground().setAlpha(50);

    }

    public void gameStart(){
        //player_one_score_view.setBackgroundColor(getResources().getColor(playerOne.getColor()));
        Animation pulse = AnimationUtils.loadAnimation(this, R.anim.pulse);
        player_one_score_view.startAnimation(pulse);
        Drawable sb = player_one_score_view.getBackground();
        sb.setColorFilter(getResources().getColor(playerOne.getColor()), PorterDuff.Mode.MULTIPLY);
    }
    public void changeTurn(){
        Drawable p1sb = player_one_score_view.getBackground();
        Drawable p2sb = player_two_score_view.getBackground();
        Animation pulse = AnimationUtils.loadAnimation(this, R.anim.pulse);
        if(currentTurn == 2) {
            player_one_score_view.startAnimation(pulse);
            player_two_score_view.clearAnimation();
            p1sb.setColorFilter(getResources().getColor(playerOne.getColor()), PorterDuff.Mode.MULTIPLY);
            p2sb.clearColorFilter();
            player_one_score_view.setTextColor(getResources().getColor(R.color.white));
            player_two_score_view.setTextColor(getResources().getColor(R.color.black));

        }
        if(currentTurn == 1){
            player_two_score_view.startAnimation(pulse);
            player_one_score_view.clearAnimation();
            p1sb.clearColorFilter();
            p2sb.setColorFilter(getResources().getColor(playerTwo.getColor()), PorterDuff.Mode.MULTIPLY);
            player_one_score_view.setTextColor(getResources().getColor(R.color.black));
            player_two_score_view.setTextColor(getResources().getColor(R.color.white));
        }
        if(currentTurn == 1){
            if (flipBoard) {
                this.findViewById(android.R.id.content).setScaleY(-1);
                this.findViewById(android.R.id.content).setScaleX(-1);
            }
            currentTurn = 2;
        } else {
            if (flipBoard) {
                this.findViewById(android.R.id.content).setScaleY(1);
                this.findViewById(android.R.id.content).setScaleX(1);
            }
            currentTurn = 1;
        }
        updateBackground();
    }

    public void undoMoveAlertDialog(){
        if(undo == 1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("Are you sure you want to undo the last move?")
                    .setPositiveButton("OK", (dialogInterface, i) -> {
                        undoMove();
                        undo = 0;
                    }).setNegativeButton("Cancel", null);

            AlertDialog alert = builder.create();
            alert.show();
        }
        else if (undo == 0){
            Toast toast = Toast.makeText(this, "You can only undo the previous move", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @SuppressLint("ResourceAsColor")
    public void undoMove(){
        ImageView line, sq;

        //Change the previous clicked line back to white
        int resID = getResources().getIdentifier(previousLine, "id", getPackageName());
        line = findViewById(resID);
        line.setBackgroundColor(android.R.color.transparent);

        gameboard.put(String.valueOf(line.getTag()), false);

        if(pointScored){
            //Change the previous clicked square back to white
            if(doubleScore) {
                int sqID1 = getResources().getIdentifier(previousSquareID[0], "id", getPackageName());
                int sqID2 = getResources().getIdentifier(previousSquareID[1], "id", getPackageName());
                sq = findViewById(sqID1);
                sq.setBackgroundColor(android.R.color.transparent);
                sq = findViewById(sqID2);
                sq.setBackgroundColor(android.R.color.transparent);

                gameboard.put(previousSquare[0], false);
                gameboard.put(previousSquare[1], false);

                //Change the score back
                if(currentTurn == 1){
                    player_one_score -= 2;
                }else if(currentTurn == 2){
                    player_two_score -= 2;
                }
            } else {
                int sqID = getResources().getIdentifier(previousSquareID[currentSquare], "id", getPackageName());
                sq = findViewById(sqID);
                sq.setBackgroundColor(android.R.color.transparent);

                gameboard.put(previousSquare[currentSquare], false);

                //Change the score back
                if(currentTurn == 1){
                    player_one_score--;
                }else if(currentTurn == 2){
                    player_two_score--;
                }
            }

            updateScores();
        }
        else{
            changeTurn();
        }
    }

    public void onTutorial(View view) {
        Intent intent = new Intent(this, TutorialActivity.class);
        intent.putExtra("from", "game");
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

    public void expandTouchWindow(ImageView view){
        Rect hitRect = new Rect();
        view.getHitRect(hitRect);
        hitRect.left -= 400;
        hitRect.top -= 400;
        hitRect.right += 400;
        hitRect.bottom += 400;
        TouchDelegate delegate = new TouchDelegate(hitRect, view);
        ((ViewGroup) view.getParent()).setTouchDelegate(delegate);
    }

    public void onMoveSelect(View view){
        doubleScore = false;
        pointScored = false;
        if(currentTurn == 1 && !gameboard.get(String.valueOf(view.getTag()))){
            previousScore = player_one_score;
            view.setBackgroundColor(getResources().getColor(playerOne.getColor()));
            gameboard.put(String.valueOf(view.getTag()), true);
            checkForCompletedSquare();
            if(player_one_score - previousScore == 2){
                doubleScore = true;
            }
            if(!pointScored){
                changeTurn();
            }
        }else if(currentTurn == 2 && !gameboard.get(String.valueOf(view.getTag()))){
            previousScore = player_two_score;
            view.setBackgroundColor(getResources().getColor(playerTwo.getColor()));
            gameboard.put(String.valueOf(view.getTag()), true);
            checkForCompletedSquare();
            if(player_two_score - previousScore == 2){
                doubleScore = true;
            }
            if(!pointScored){
                changeTurn();
            }
        }
        //Keep track of last line placed for undo button
        undo = 1;
        previousLine = String.valueOf(view.getId());
    }
    public void initBoard(){
        //small
        for(int i = 1; i <= 6; i++){
            //squares
            gameboard.put("sq"+i, false);
            if(i <= 4){
                for(int j = 1; j <= 3; j++){
                    if(j <= 2){
                        //horizontal -- h1-1 through h4-2
                        gameboard.put("h"+i+"-"+j, false);
                    }
                    if(i <= 3){
                        //vertical -- v1-1 through v3-3
                        gameboard.put("v"+i+"-"+j, false);
                    }
                }
            }
        }
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
        if(boardSize.equalsIgnoreCase("large")){
            //squares
            gameboard.put("sq13", false);
            gameboard.put("sq14", false);
            gameboard.put("sq15", false);
            gameboard.put("sq16", false);
            gameboard.put("sq17", false);
            gameboard.put("sq18", false);
            gameboard.put("sq19", false);
            gameboard.put("sq20", false);
            //horizontal
            gameboard.put("h1-4", false);
            gameboard.put("h2-4", false);
            gameboard.put("h3-4", false);
            gameboard.put("h4-4", false);
            gameboard.put("h5-4", false);
            gameboard.put("h6-1", false);
            gameboard.put("h6-2", false);
            gameboard.put("h6-3", false);
            gameboard.put("h6-4", false);

            //vertical
            gameboard.put("v1-5", false);
            gameboard.put("v2-5", false);
            gameboard.put("v3-5", false);
            gameboard.put("v4-5", false);
            gameboard.put("v5-1", false);
            gameboard.put("v5-2", false);
            gameboard.put("v5-3", false);
            gameboard.put("v5-4", false);
            gameboard.put("v5-5", false);

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
        if(boardSize.equalsIgnoreCase("large")){
            //FIRST ROW
            squareChecker("sq1","h1-1","h2-1","v1-1","v1-2");
            squareChecker("sq2","h1-2","h2-2","v1-2","v1-3");
            squareChecker("sq3","h1-3","h2-3","v1-3","v1-4");
            squareChecker("sq4","h1-4","h2-4","v1-4","v1-5");
            //SECOND ROW
            squareChecker("sq5","h2-1","h3-1","v2-1","v2-2");
            squareChecker("sq6","h2-2","h3-2","v2-2","v2-3");
            squareChecker("sq7","h2-3","h3-3","v2-3","v2-4");
            squareChecker("sq8","h2-4","h3-4","v2-4","v2-5");
            //THIRD ROW
            squareChecker("sq9","h3-1","h4-1","v3-1","v3-2");
            squareChecker("sq10","h3-2","h4-2","v3-2","v3-3");
            squareChecker("sq11","h3-3","h4-3","v3-3","v3-4");
            squareChecker("sq12","h3-4","h4-4","v3-4","v3-5");
            //FOURTH ROW
            squareChecker("sq13","h4-1","h5-1","v4-1","v4-2");
            squareChecker("sq14","h4-2","h5-2","v4-2","v4-3");
            squareChecker("sq15","h4-3","h5-3","v4-3","v4-4");
            squareChecker("sq16","h4-4","h5-4","v4-4","v4-5");
            //FIFTH ROW
            squareChecker("sq17","h5-1","h6-1","v5-1","v5-2");
            squareChecker("sq18","h5-2","h6-2","v5-2","v5-3");
            squareChecker("sq19","h5-3","h6-3","v5-3","v5-4");
            squareChecker("sq20","h5-4","h6-4","v5-4","v5-5");
        }

    }
    public void squareChecker(String square, String top, String bottom, String left, String right){
        ImageView sq;
        int resID = getResources().getIdentifier(square, "id", getPackageName());
        sq = findViewById(resID);
        if(!gameboard.get(square) && (gameboard.get(top) && gameboard.get(bottom)
                && gameboard.get(left) && gameboard.get(right))){
            if(currentTurn == 1){
                player_one_score++;
                sq.setBackgroundColor(getResources().getColor(playerOne.getColor()));
                pointScored = true;
            }else if(currentTurn == 2){
                player_two_score++;
                sq.setBackgroundColor(getResources().getColor(playerTwo.getColor()));
                pointScored = true;
            }
            gameboard.put(square, true);
            updateScores();
            //Keep track of last completed square for undo button
            if(currentSquare == 0){
                currentSquare = 1;
            } else {
                currentSquare = 0;
            }

            if(currentSquare == 0){
                previousSquareID[0] = String.valueOf(sq.getId());
                previousSquare[0] = square;
            } else if(currentSquare == 1){
                previousSquareID[1] = String.valueOf(sq.getId());
                previousSquare[1] = square;
            }

        }
    }
    public void updateScores(){
        player_one_score_view = findViewById(R.id.tv_player_one_score);
        player_two_score_view = findViewById(R.id.tv_player_two_score);
        player_one_score_view.setText(String.valueOf(player_one_score));
        player_two_score_view.setText(String.valueOf(player_two_score));

        if(player_one_score + player_two_score == squares) {
            if (player_one_score > player_two_score)
                gameOver(1, player_one_score, false);
            else if (player_two_score > player_one_score)
                gameOver(2, player_two_score, false);
            else
                gameOver(1, player_two_score, true);
        }
    }

    public void expandTouchArea(final View bigView, final View smallView, final int extraPadding) {
        bigView.post(() -> {
            Rect rect = new Rect();
            smallView.getHitRect(rect);
            rect.top -= extraPadding;
            rect.left -= extraPadding;
            rect.right += extraPadding;
            rect.bottom += extraPadding;
            bigView.setTouchDelegate(new TouchDelegate(rect, smallView));
        });
    }

    public int getSquares(String boardSize) {
        switch (boardSize) {
            case "large":
                return(20);
            case "medium":
                return(12);
            default:
                return(6);
        }
    }

    public void gameOver(int player, int score, boolean draw) {
        FragmentManager fm = getSupportFragmentManager();
        GameOverDialogFragment gameOver = GameOverDialogFragment.newInstance(playerOne, playerTwo, boardSize, player, score, draw);
        gameOver.show(fm, null);
    }

}
