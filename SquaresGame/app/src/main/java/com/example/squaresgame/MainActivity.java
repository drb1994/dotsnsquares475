package com.example.squaresgame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    Player playerOne = new Player(1, R.color.cyan);
    Player playerTwo = new Player(2, R.color.green);

    protected AlphaAnimation fadeInTop = new AlphaAnimation(0.0f , 1.0f ) ;
    protected AlphaAnimation fadeInLeft = new AlphaAnimation(0.0f , 1.0f ) ;
    protected AlphaAnimation fadeInBottom = new AlphaAnimation(0.0f , 1.0f ) ;
    protected AlphaAnimation fadeInRight = new AlphaAnimation(0.0f , 1.0f ) ;

    ImageView topBar;
    ImageView leftBar;
    ImageView bottomBar;
    ImageView rightBar;

    ImageView mainLogo;

    FloatingActionButton clearButton;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startAnimation();
        mainLogo = findViewById(R.id.main_logo);
        Animation pulse = AnimationUtils.loadAnimation(this, R.anim.pulse);
        mainLogo.startAnimation(pulse);
        startButton = findViewById(R.id.play_btn);
        startButton.startAnimation(pulse);
        editor = prefs.edit();
        clearButton = (FloatingActionButton) findViewById(R.id.clear_prefs_fab);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        clearButton.setOnClickListener(view -> clearSettingsAlertDialog());

        //IF SAVED PREFERENCES EXIST
        System.out.println(prefs.getAll());
        if(!prefs.getAll().isEmpty()){
            clearButton.show();
            //startButton.getBackground().setColorFilter(getResources().getColor(R.color.green3), PorterDuff.Mode.MULTIPLY);
            startButton.setBackgroundColor(getResources().getColor(R.color.light_green));
            playerOne.setColor(prefs.getInt("p1color", 0));
            playerTwo.setColor(prefs.getInt("p2color", 0));
        }else{
            clearButton.hide();
        }
    }



    public void onPlay(View view) {
        if(prefs.getBoolean("sp",false)){
            Intent intent = new Intent(this, GameActivity.class);
            Bundle players = new Bundle();
            players.putSerializable("Player One",playerOne);
            players.putSerializable("Player Two",playerTwo);
            intent.putExtras(players);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this, SettingsActivity.class);
            Bundle players = new Bundle();
            players.putSerializable("Player One", playerOne);
            players.putSerializable("Player Two", playerTwo);
            intent.putExtras(players);
            intent.putExtra("from","play");
            startActivity(intent);
        }
    }

    public void clearSettingsAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to clear your saved preferences?")
                .setPositiveButton("OK", ((dialogInterface, i) -> {
                    onClearSettings();
                })).setNegativeButton("Cancel", null);
        AlertDialog alert = builder.create();
        alert.show();
    }
    public void onClearSettings() {
        clearButton.hide();
        editor.clear();
        editor.apply();
        startButton.setBackgroundColor(getResources().getColor(R.color.cyan));
    }

    private void startAnimation() {
        topBar = findViewById(R.id.top_bar);
        leftBar = findViewById(R.id.left_bar);
        bottomBar = findViewById(R.id.bottom_bar);
        rightBar = findViewById(R.id.right_bar);

        topBar.setBackgroundColor(getResources().getColor(R.color.blue));
        leftBar.setBackgroundColor(getResources().getColor(R.color.cyan));
        bottomBar.setBackgroundColor(getResources().getColor(R.color.green));
        rightBar.setBackgroundColor(getResources().getColor(R.color.light_green));

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

    public void onTutorial(View view) {
        Intent intent = new Intent(this, TutorialActivity.class);
        intent.putExtra("from", "main");
        startActivity(intent);
    }
}