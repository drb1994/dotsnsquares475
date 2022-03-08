package com.example.squaresgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    Player playerOne = new Player(1, R.color.cyan);
    Player playerTwo = new Player(2, R.color.green3);

    protected AlphaAnimation fadeInTop = new AlphaAnimation(0.0f , 1.0f ) ;
    protected AlphaAnimation fadeInLeft = new AlphaAnimation(0.0f , 1.0f ) ;
    protected AlphaAnimation fadeInBottom = new AlphaAnimation(0.0f , 1.0f ) ;
    protected AlphaAnimation fadeInRight = new AlphaAnimation(0.0f , 1.0f ) ;

    ImageView topBar;
    ImageView leftBar;
    ImageView bottomBar;
    ImageView rightBar;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startAnimation();

        editor = prefs.edit();

        System.out.println(prefs.getAll());
    }

    public void onPlay(View view) {
        if(prefs.getBoolean("sp",false)){
            Intent intent = new Intent(this, GameBoardActivity.class);
            Bundle players = new Bundle();
            players.putSerializable("Player One",playerOne);
            players.putSerializable("Player Two",playerTwo);
            intent.putExtras(players);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this, SettingsActivity.class);
            //Create a bundle to send players to SettingsActivity
            Bundle players = new Bundle();
            players.putSerializable("Player One", playerOne);
            players.putSerializable("Player Two", playerTwo);
            intent.putExtras(players);
            intent.putExtra("from","play");
            startActivity(intent);
        }
    }

    public void onClearSettings(View view) {
        editor.clear();
        editor.apply();
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
    private void startAnimation() {
        topBar = findViewById(R.id.top_bar);
        leftBar = findViewById(R.id.left_bar);
        bottomBar = findViewById(R.id.bottom_bar);
        rightBar = findViewById(R.id.right_bar);

        topBar.setBackgroundColor(getResources().getColor(R.color.blue));
        leftBar.setBackgroundColor(getResources().getColor(R.color.cyan));
        bottomBar.setBackgroundColor(getResources().getColor(R.color.green3));
        rightBar.setBackgroundColor(getResources().getColor(R.color.green5));

        fadeInTop.setDuration(1200);
        fadeInTop.setFillAfter(true);
        fadeInLeft.setDuration(1200);
        fadeInLeft.setFillAfter(true);
        fadeInBottom.setDuration(1200);
        fadeInBottom.setFillAfter(true);
        fadeInRight.setDuration(1200);
        fadeInRight.setFillAfter(true);

        fadeInLeft.setStartOffset(600);
        fadeInBottom.setStartOffset(1200);
        fadeInRight.setStartOffset(1800);

        topBar.startAnimation(fadeInTop);
        leftBar.startAnimation(fadeInLeft);
        bottomBar.startAnimation(fadeInBottom);
        rightBar.startAnimation(fadeInRight);
    }
}