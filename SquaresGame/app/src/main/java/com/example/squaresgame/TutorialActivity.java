package com.example.squaresgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TutorialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        Intent intent = getIntent();
        String from = intent.getStringExtra("from");

        Button returnBtn = findViewById(R.id.return_btn);
        if (from.equals("game")) {
            returnBtn.setOnClickListener(view -> {
                Intent intent1 = new Intent();
                setResult(RESULT_OK, intent1);
                finish();
            });
        }
        else
            returnBtn.setVisibility(View.GONE);
    }
}