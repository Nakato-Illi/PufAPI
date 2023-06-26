package com.example.pufapi.api.model;

import java.sql.Blob;

public class Figure {
    String figurename;
    String figureimg;
    Blob img;

    public String getFigurename() {
        return figurename;
    }

    public void setFigurename(String figurename) {
        this.figurename = figurename;
    }

    public Figure(String figurename) {
        this.figurename = figurename;
    }

    public String getFigureimg() {
        return figureimg;
    }

    public void setFigureimg(String figureimg) {
        this.figureimg = figureimg;
    }

    public Blob getImg() {
        return img;
    }

    public void setImg(Blob img) {
        this.img = img;
    }
}