package com.example.squaresgame;

import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

public class ColorPickerDialogFragment extends DialogFragment {
    private final ImageButton[] colorButton = new ImageButton[6];

    public ColorPickerDialogFragment() {
        //Must remain empty
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_color_picker, container, false);

        //Initialize the buttons
        colorButton[0] = view.findViewById(R.id.cyan);
        colorButton[1] = view.findViewById(R.id.blue);
        colorButton[2] = view.findViewById(R.id.purple);
        colorButton[3] = view.findViewById(R.id.green3);
        colorButton[4] = view.findViewById(R.id.green4);
        colorButton[5] = view.findViewById(R.id.green5);

        //On click listener for each button
        for (ImageButton imageButton : colorButton) {
            imageButton.setOnClickListener(view1 -> {
                //Send back color selected
                dismiss();
            });
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
