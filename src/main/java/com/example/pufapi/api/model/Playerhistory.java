package com.example.pufapi.api.model;

public class Playerhistory {
    String playername;
    Integer highscore;
    Long date;

    String result;

    public Playerhistory(String playername,
                         Integer highscore,
                         Long date,
                         String result) {
        this.playername = playername;
        this.highscore = highscore;
        this.date = date;
        this.result = result;
    }

    public Long getDate() {
        return this.date;
    }

    public String getPlayername() {
        return this.playername;
    }

    public Integer getHighscore() {
        return this.highscore;
    }

    public String getResult() {
        return this.result;
    }
}
