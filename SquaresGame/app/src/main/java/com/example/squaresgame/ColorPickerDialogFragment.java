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

    public static ColorPickerDialogFragment newInstance(Player player, int takenColor) {
        Bundle args = new Bundle();
        args.putSerializable("player", player);
        args.putInt("takenColor", takenColor);
        ColorPickerDialogFragment colorPickerDialogFragment = new ColorPickerDialogFragment();
        colorPickerDialogFragment.setArguments(args);
        return(colorPickerDialogFragment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_color_picker, container, false);

        assert getArguments() != null;
        player = (Player) getArguments().getSerializable("player");
        takenColor = getArguments().getInt("takenColor");

        int[] idButton = new int[] {R.id.cyan, R.id.blue, R.id.purple, R.id.green3, R.id.green4, R.id.green5};
        View[] taken = new View[6];
        int[] idView = new int[] {R.id.cyan_taken, R.id.blue_taken, R.id.purple_taken, R.id.green3_taken, R.id.green4_taken, R.id.green5_taken};
        int[] color = new int[] {R.color.cyan, R.color.blue, R.color.purple, R.color.light_purple, R.color.green, R.color.light_green};

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
