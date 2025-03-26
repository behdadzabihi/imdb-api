package com.example.imdb.service;

import com.example.imdb.dto.BestTitleByYearDto;
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


    public List<BestTitleByYearDto> getBestTitlesByGenreEachYear(String genre) {
        Map<Integer, List<Title>> titlesByYear = dataLoaderService.getTitles().values().stream()
                .filter(title -> Arrays.asList(title.getGenres()).contains(genre) && title.getStartYear() > 0)
                .collect(Collectors.groupingBy(Title::getStartYear));

        List<BestTitleByYearDto> bestTitles = new ArrayList<>();

        titlesByYear.forEach((year, titles) -> {
            titles.stream()
                    .filter(title -> dataLoaderService.getTitleRatings().containsKey(title.getTconst()))
                    .max(Comparator.comparingDouble(title -> {
                        TitleRating rating = dataLoaderService.getTitleRatings().get(title.getTconst());
                        return rating.getAverageRating() * Math.log(rating.getNumVotes() + 1);
                    }))
                    .ifPresent(bestTitle -> {
                        TitleRating rating = dataLoaderService.getTitleRatings().get(bestTitle.getTconst());
                        BestTitleByYearDto dto = mapToDto(year, bestTitle, rating);
                        bestTitles.add(dto);
                    });
        });

        return bestTitles;
    }

    private BestTitleByYearDto mapToDto(int year, Title title, TitleRating rating) {
        return new BestTitleByYearDto(year, title.getPrimaryTitle(), rating.getAverageRating());
    }
}

