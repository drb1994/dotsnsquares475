package com.example.squaresgame;

import java.io.Serializable;

public class Player implements Serializable {
    public int playerNum;
    public int color;

    public Player(int playerNum, int color) {
        this.playerNum = playerNum;
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getPlayerNum() {
        return playerNum;
    }
}
