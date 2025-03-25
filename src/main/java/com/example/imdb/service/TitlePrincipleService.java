package com.example.imdb.service;

import com.example.imdb.model.Title;
import com.example.imdb.model.TitlePrincipal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;
@Service
@Getter
@AllArgsConstructor
public class TitlePrincipleService {

    private final TitleService titleService;
    private final DataLoaderService loaderService;

    public List<Title> getCommonTitlesByActors(String actor1Id, String actor2Id) {
        Set<String> actor1Titles = findTitlesForActor(actor1Id);
        Set<String> actor2Titles = findTitlesForActor(actor2Id);

        actor1Titles.retainAll(actor2Titles);

        return actor1Titles.stream()
                .map(loaderService.getTitles()::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private Set<String> findTitlesForActor(String actorId) {
        return loaderService.getTitlePrincipals().values().stream()
                .flatMap(List::stream)
                .filter(principal -> principal.getNconst().equals(actorId) &&
                        (principal.getCategory().equals("actor") || principal.getCategory().equals("actress")))
                .map(TitlePrincipal::getTconst)
                .collect(Collectors.toSet());
    }
}

