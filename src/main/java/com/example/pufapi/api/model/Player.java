package com.example.pufapi.api.model;

public class Player {
    int id;
    String playername;
    int highscore = 0;
    public Player() {

    }

    public Player(String playername) {
        this.playername = playername;
    }

    public Player(int id, String playername, int highscore) {
        this.id = id;
        this.playername = playername;
        this.highscore = highscore;
    }


    public String getPlayername() {
        return playername;
    }
    public int getPlayerId() {
        return id;
    }
    public void setPlayerId(int id) {
        this.id = id;
    }

    public int getHighscore() {
        return highscore;
    }

    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }
}
