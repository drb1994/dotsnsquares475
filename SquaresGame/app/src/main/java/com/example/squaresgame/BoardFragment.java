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

    public static BoardFragment newInstance(String boardSize) {
        Bundle args = new Bundle();
        args.putString("boardSize", boardSize);
        BoardFragment boardFragment = new BoardFragment();
        boardFragment.setArguments(args);
        return(boardFragment);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;

        assert getArguments() != null;
        boardSize = getArguments().getString("boardSize");

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
