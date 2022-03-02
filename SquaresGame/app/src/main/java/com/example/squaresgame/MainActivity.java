package com.example.squaresgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    Player playerOne = new Player(1, R.color.cyan);
    Player playerTwo = new Player(2, R.color.green3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onPlay(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        //Create a bundle to send players to SettingsActivity
        Bundle players = new Bundle();
        players.putSerializable("Player One", playerOne);
        players.putSerializable("Player Two", playerTwo);
        intent.putExtras(players);
        intent.putExtra("from","play");
        startActivity(intent);
    }

    public void onSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        //Create a bundle to send players to SettingsActivity
        Bundle players = new Bundle();
        players.putSerializable("Player One", playerOne);
        players.putSerializable("Player Two", playerTwo);
        intent.putExtras(players);
        intent.putExtra("from","settings");
        startActivity(intent);
    }
}