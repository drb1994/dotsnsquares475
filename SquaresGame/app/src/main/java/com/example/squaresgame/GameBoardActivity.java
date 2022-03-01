package com.example.squaresgame;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageButton;

public class GameBoardActivity extends AppCompatActivity {
    ImageButton undo_last_move;

    Integer undo = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        undo_last_move = findViewById(R.id.ib_undo_button);
        // minor change to merge
        // maybe add a second comment will help
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
    }
}
