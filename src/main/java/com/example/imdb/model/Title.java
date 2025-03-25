package com.example.imdb.model;
import lombok.Data;

@Data
public class Title {
    private String tconst;
    private String titleType;
    private String primaryTitle;
    private String originalTitle;
    private boolean isAdult;
    private int startYear;
    private int endYear;
    private int runtimeMinutes;
    private String[] genres;

}
