package com.example.imdb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BestTitleByYearDto {

    private int year;
    private String title;
    private double rating;
}
