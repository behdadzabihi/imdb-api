package com.example.imdb.model;
import lombok.Data;

@Data
public class TitleCrew {
    private String tconst;
    private String[] directors;
    private String[] writers;
}