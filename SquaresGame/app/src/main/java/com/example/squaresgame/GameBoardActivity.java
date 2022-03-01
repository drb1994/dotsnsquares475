package com.example.squaresgame;

import android.app.AlertDialog;
import android.app.Notification;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.TextView;

public class GameBoardActivity extends AppCompatActivity {
    ImageButton undo_last_move, pause_button, tutorial_button;
    TextView player_one_score, player_two_score;

    Integer undo = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        undo_last_move = findViewById(R.id.ib_undo_button);
        pause_button = findViewById(R.id.ib_pause_menu);
        tutorial_button = findViewById(R.id.ib_tutorial_button);
        player_one_score = findViewById(R.id.tv_player_one_score);
        player_two_score = findViewById(R.id.tv_player_two_score);

        // Undo button functionality
        undo_last_move.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("Are you sure you want to undo the last move?")
                    .setPositiveButton("OK", (dialogInterface, i) -> {
                        if(undo == 1){
                            undo -= 1;
                        } else if(undo == 0){
                            androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(this);
                            builder1.setMessage("You can only undo the previous move");
                            androidx.appcompat.app.AlertDialog alert1 = builder1.create();
                            alert1.show();
                        }
                    }).setNegativeButton("Cancel", null);

            AlertDialog alert = builder.create();
            alert.show();
        });
        // End of undo button functionality

        pause_button.setOnClickListener(view -> {

        });
    }
}
