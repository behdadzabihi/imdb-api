package com.example.imdb.model;
import lombok.Data;
@Data
public class Person {
    private String nconst;
    private String primaryName;
    private int birthYear;
    private int deathYear;
    private String[] primaryProfession;
    private String[] knownForTitles;

    public boolean isAlive() {
        return deathYear == 0;
    }
}
