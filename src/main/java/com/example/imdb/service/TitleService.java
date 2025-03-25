package com.example.imdb.service;

import com.example.imdb.model.Title;
import com.example.imdb.model.TitleRating;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Service
@AllArgsConstructor
public class TitleService {
    private final DataLoaderService dataLoaderService;

    public Map<Integer, Title> getBestTitlesByGenreEachYear(String genre) {
        Map<Integer, List<Title>> titlesByYear = dataLoaderService.getTitles().values().stream()
                .filter(title -> Arrays.asList(title.getGenres()).contains(genre) && title.getStartYear() > 0)
                .collect(Collectors.groupingBy(Title::getStartYear));

        Map<Integer, Title> bestTitles = new HashMap<>();

        titlesByYear.forEach((year, titles) -> {
            titles.stream()
                    .filter(title -> dataLoaderService.getTitleRatings().containsKey(title.getTconst()))
                    .max(Comparator.comparingDouble(title -> {
                        TitleRating rating = dataLoaderService.getTitleRatings().get(title.getTconst());
                        return rating.getAverageRating() * Math.log(rating.getNumVotes() + 1);
                    }))
                    .ifPresent(bestTitle -> bestTitles.put(year, bestTitle));
        });

        return bestTitles;
    }
}

