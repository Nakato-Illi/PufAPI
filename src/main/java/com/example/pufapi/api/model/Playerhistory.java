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

    public void setDate(Long date) {
        this.date = date;
    }

    public String getPlayername() {
        return this.playername;
    }

    public void setPlayername(String playername) {
        this.playername = playername;
    }

    public Integer getHighscore() {
        return this.highscore;
    }

    public void setHighscore(Integer highscore) {
        this.highscore = highscore;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
