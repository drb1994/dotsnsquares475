package com.example.squaresgame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import java.util.Objects;

public class GameOverDialogFragment extends DialogFragment {
    Player playerOne, playerTwo;
    Button restart, home;
    String boardSize;
    int player, score;
    boolean draw;

    TextView textView, scoreView, playerName;

    public static GameOverDialogFragment newInstance(Player playerOne, Player playerTwo, String boardSize, int player, int score, boolean draw) {
        Bundle args = new Bundle();
        args.putSerializable("playerOne", playerOne);
        args.putSerializable("playerTwo", playerTwo);
        args.putString("boardSize", boardSize);
        args.putInt("player", player);
        args.putInt("score", score);
        args.putBoolean("draw", draw);
        GameOverDialogFragment gameOverDialogFragment = new GameOverDialogFragment();
        gameOverDialogFragment.setArguments(args);
        return(gameOverDialogFragment);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_game_over, container, false);

        assert getArguments() != null;
        playerOne = (Player) getArguments().getSerializable("playerOne");
        playerTwo = (Player) getArguments().getSerializable("playerTwo");
        boardSize = getArguments().getString("boardSize");
        player = getArguments().getInt("player");
        score = getArguments().getInt("score");
        draw = getArguments().getBoolean("draw");

        Objects.requireNonNull(getDialog()).setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);

        textView = view.findViewById(R.id.textView);
        scoreView = view.findViewById(R.id.tv_player_score);
        playerName = view.findViewById(R.id.tv_player_name);

        if(draw) {
            textView.setText("Draw");
            setPlayer();
        }
        else {
            textView.setText("Winner");
            if(player == 1)
                setPlayer(playerOne, "Player One");
            else
                setPlayer(playerTwo, "Player Two");
        }
        scoreView.setText(String.valueOf(score));

        restart = view.findViewById(R.id.restart);
        restart.setOnClickListener(view12 -> {
            Intent intent = new Intent(getActivity(), GameActivity.class);
            //Create a bundle to send players to SettingsActivity
            Bundle players = new Bundle();
            players.putSerializable("Player One", playerOne);
            players.putSerializable("Player Two", playerTwo);
            intent.putExtras(players);
            intent.putExtra("from","");
            intent.putExtra("size", boardSize);
            //Close the current activity
            requireActivity().finish();
            startActivity(intent);
        });

        home = view.findViewById(R.id.home);
        home.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        });

        return view;
    }

    public void setPlayer() {
//        GradientDrawable scoreBackground = (GradientDrawable) scoreView.getBackground();
//        int[] playerColors = new int[]{getResources().getColor(playerOne.getColor()), getResources().getColor(playerTwo.getColor())};
//        scoreBackground.setColors(playerColors);
        scoreView.setTextColor(getResources().getColor(R.color.black));
    }

    public void setPlayer(Player thisPlayer, String playerText) {
        playerName.setText(playerText);
        Drawable scoreBackground = scoreView.getBackground();
        scoreBackground.setColorFilter(getResources().getColor(thisPlayer.getColor()), PorterDuff.Mode.MULTIPLY);
        scoreView.setTextColor(getResources().getColor(R.color.white));
    }
}
