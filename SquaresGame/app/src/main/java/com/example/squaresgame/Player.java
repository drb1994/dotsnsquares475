package com.example.squaresgame;

import java.io.Serializable;

public class Player implements Serializable {
    public String name;
    public int num;
    public int color;

    public Player(String name, int num, int color) {
        this.name = name;
        this.num = num;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getNum() {
        return num;
    }
}
