package com.example.squaresgame;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BoardFragment extends Fragment {
    String boardSize;

    public BoardFragment(String boardSize) {
        this.boardSize = boardSize;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;

        switch(boardSize) {
            case "small":
                view = inflater.inflate(R.layout.board_small, container, false);
                break;
            case "medium":
                view = inflater.inflate(R.layout.board_medium, container, false);
                break;
            case "large":
                view = inflater.inflate(R.layout.board_large, container, false);
                break;
        }

        return view;
    }
}
