package com.example.imdb.controller;

import com.example.imdb.dto.BestTitleByYearDto;
import com.example.imdb.model.*;
import com.example.imdb.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.constraints.NotBlank;
import java.util.*;


@RestController
@RequestMapping("/api/imdb")
@RequiredArgsConstructor
@Validated
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

    @GetMapping("/common-actors")
    public List<Title> getTitlesWithCommonActors(
            @RequestParam @NotBlank(message = "actor1Id must not be blank") String actor1Id,
            @RequestParam @NotBlank(message = "actor2Id must not be blank") String actor2Id) {
        requestCounterService.increment();
        return titlePrincipleService.getCommonTitlesByActors(actor1Id, actor2Id);
    }

    @GetMapping("/best-by-genre")
    public List<BestTitleByYearDto> getBestTitlesByGenreEachYear(
            @RequestParam @NotBlank(message = "genre must not be blank") String genre) {
        requestCounterService.increment();
        return titleService.getBestTitlesByGenreEachYear(genre);
    }
}
