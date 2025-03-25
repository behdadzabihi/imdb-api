package com.example.imdb.controller;

import com.example.imdb.model.*;
import com.example.imdb.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


@RestController
@RequestMapping("/api/imdb")
@RequiredArgsConstructor
public class ImdbController {

    private final CrewService crewService;
    private final TitleService titleService;
    private final TitlePrincipleService titlePrincipleService;
    private final RequestCounterService requestCounterService;

    @GetMapping("/titles/same-director-writer-alive")
    public List<Title> getTitlesWithSameDirectorWriterAlive() {
        requestCounterService.increment();
        return crewService.getTitlesWithSameDirectorWriterAlive();
    }

    @GetMapping("/titles/common-actors")
    public List<Title> getTitlesWithCommonActors(@RequestParam String actor1Id, @RequestParam String actor2Id) {
        requestCounterService.increment();
        return titlePrincipleService.getCommonTitlesByActors(actor1Id, actor2Id);
    }

    @GetMapping("/titles/best-by-genre")
    public Map<Integer, Title> getBestTitlesByGenreEachYear(@RequestParam String genre) {
        requestCounterService.increment();
        return titleService.getBestTitlesByGenreEachYear(genre);
    }
}
