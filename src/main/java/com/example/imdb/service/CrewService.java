package com.example.imdb.service;

import com.example.imdb.model.Title;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Service
@AllArgsConstructor
public class CrewService {

    private final TitleService titleService;
    private DataLoaderService dataLoaderService;

    public List<Title> getTitlesWithSameDirectorWriterAlive() {
        return dataLoaderService.getTitleCrews().values().stream()
                .filter(crew -> crew.getDirectors() != null && crew.getDirectors().length > 0 &&
                        crew.getWriters() != null && crew.getWriters().length > 0)
                .filter(crew -> dataLoaderService.hasSameAliveDirectorWriter(crew))
                .map(crew -> dataLoaderService.getTitles().get(crew.getTconst()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}

