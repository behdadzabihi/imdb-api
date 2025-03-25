package com.example.imdb.model;
import lombok.Data;

@Data
public class TitleRating {
    private String tconst;
    private double averageRating;
    private int numVotes;
}
