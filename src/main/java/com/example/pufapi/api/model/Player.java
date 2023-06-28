package com.example.pufapi.api.model;

public class Player {
    int id;
    String playername;
    int highscore = 0;

    public String getPlayername() {
        return playername;
    }

    public int getHighscore() {
        return highscore;
    }

}
