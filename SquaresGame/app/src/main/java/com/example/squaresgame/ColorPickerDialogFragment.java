package com.example.squaresgame;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.DialogFragment;

public class ColorPickerDialogFragment extends DialogFragment {
    private final ImageButton[] colorButton = new ImageButton[6];
    Player player;
    int takenColor;

    public ColorPickerDialogFragment(Player player, int takenColor) {
        this.player = player;
        this.takenColor = takenColor;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_color_picker, container, false);

        int[] idButton = new int[] {R.id.red, R.id.blue, R.id.yellow, R.id.green, R.id.pink, R.id.purple};
        View[] taken = new View[6];
        int[] idView = new int[] {R.id.red_taken, R.id.blue_taken, R.id.yellow_taken, R.id.green_taken, R.id.pink_taken, R.id.purple_taken};
        int[] color = new int[] {R.color.red1, R.color.blue1, R.color.yellow1, R.color.green1, R.color.pink1, R.color.purple1};

        //Initialize the buttons
        for(int i = 0; i < colorButton.length; i++) {
            //Have to declare this outside the lambda expression, so that I can use it inside
            int num = i;
            //Find the button by id
            colorButton[num] = view.findViewById(idButton[i]);

            //If the color of the button is taken by the other player
            if(color[num] == takenColor) {
                //Set the taken drawable (an X over the button) to visible and do not set an onClickListener
                taken[num] = view.findViewById(idView[i]);
                taken[num].setVisibility(View.VISIBLE);
            }
            else {
                //Set the onClickListener
                colorButton[num].setOnClickListener(view1 -> {
                    //Set the players color to the chosen one
                    player.setColor(color[num]);
                    //Update the color on settings page
                    ((SettingsActivity) requireActivity()).updatePlayerColor(player);
                    //Dismiss the dialog
                    dismiss();
                });
            }
        }

        return view;
    }
}
