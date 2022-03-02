package com.example.squaresgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;

import java.util.Objects;

public class PauseDialogFragment extends DialogFragment {
    Player playerOne, playerTwo;
    Button settings, restart, endGame;

    public PauseDialogFragment(Player playerOne, Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_pause_menu, container, false);

        settings = view.findViewById(R.id.settings);
        settings.setOnClickListener(view13 -> {
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            //Create a bundle to send players to SettingsActivity
            Bundle players = new Bundle();
            players.putSerializable("Player One", playerOne);
            players.putSerializable("Player Two", playerTwo);
            intent.putExtras(players);
            intent.putExtra("from","settings");
            startActivity(intent);
        });

        restart = view.findViewById(R.id.restart);
        restart.setOnClickListener(view12 -> {
            Intent intent = new Intent(getActivity(), GameBoardActivity.class);
            //Create a bundle to send players to SettingsActivity
            Bundle players = new Bundle();
            players.putSerializable("Player One", playerOne);
            players.putSerializable("Player Two", playerTwo);
            intent.putExtras(players);
            intent.putExtra("from","settings");
            //Close the current activity
            requireActivity().finish();
            startActivity(intent);
        });

        endGame = view.findViewById(R.id.end_game);
        endGame.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        });

        return view;
    }
}
