
package com.example.imdb.service;

import com.example.imdb.model.*;
import com.example.imdb.util.DataUtil;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
@Getter
public class DataLoaderService {
    private static final Logger logger = LoggerFactory.getLogger(DataLoaderService.class);
    //Because the project is a test and the project description states
    // that the data should be read locally, I placed the data in a zipped
    // format locally on the local system. In real life, databases and even
    // caching services such as Redis and cloud services are used.
    private static final String BASE_PATH = "C:\\Users\\Behdad\\Desktop\\data\\";
    private final Map<String, Title> titles = new ConcurrentHashMap<>();
    private final Map<String, TitleCrew> titleCrews = new ConcurrentHashMap<>();
    private final Map<String, List<TitlePrincipal>> titlePrincipals = new ConcurrentHashMap<>();
    private final Map<String, Person> names = new ConcurrentHashMap<>();
    private final Map<String, TitleRating> titleRatings = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        logger.info("Starting data loading...");
        loadTitles();
        loadTitleCrews();
        loadTitlePrincipals();
        loadNames();
        loadTitleRatings();
        logger.info("Data loading completed.");
    }

    private void loadTitles() {
        logger.info("Loading titles...");
        try (Stream<String> reader = DataUtil.readGzipFile(BASE_PATH + "title.basics.tsv.gz")) {

            reader.forEach(line -> {
                try {
                    String[] row = line.split("\t");
                    processTitleRow(row);
                } catch (Exception e) {
                    logger.error("Error processing title row: {}", line, e);
                }
            });
            if (logger.isDebugEnabled()) {
                logger.debug("Loaded {} titles.", titles.size());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load titles", e);
        }
    }

    private void processTitleRow(String[] row) {
        try {
            Title title = new Title();
            title.setTconst(row[0]);
            title.setTitleType(row[1]);
            title.setPrimaryTitle(row[2]);
            title.setOriginalTitle(row[3]);
            title.setAdult(Boolean.parseBoolean(row[4]));
            title.setStartYear(DataUtil.parseSafeInt(row[5]));
            title.setEndYear(row[6].equals("\\N") ? 0 : DataUtil.parseSafeInt(row[6]));
            title.setRuntimeMinutes(row[7].equals("\\N") ? 0 : DataUtil.parseSafeInt(row[7]));
            title.setGenres(row[8].equals("\\N") ? new String[0] : row[8].split(","));
            titles.put(title.getTconst(), title);
        } catch (Exception e) {
            logger.error("Error processing title row: {}", Arrays.toString(row), e);
        }
    }

    private void loadTitleCrews() {
        logger.info("Loading title crews...");
        try (Stream<String> reader = DataUtil.readGzipFile(BASE_PATH + "title.crew.tsv.gz")) {

            reader.forEach(line -> {
                try {
                    String[] row = line.split("\t");
                    processCrewRow(row);
                } catch (Exception e) {
                    logger.error("Error processing title crew row: {}", line, e);
                }
            });
            if (logger.isDebugEnabled()) {
                logger.debug("Loaded {} title crews.", titleCrews.size());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load title crews", e);
        }
    }

    private void processCrewRow(String[] row) {
        try {
            TitleCrew crew = new TitleCrew();
            crew.setTconst(row[0]);
            crew.setDirectors(row[1].equals("\\N") ? new String[0] : row[1].split(","));
            crew.setWriters(row[2].equals("\\N") ? new String[0] : row[2].split(","));
            titleCrews.put(crew.getTconst(), crew);
        } catch (Exception e) {
            logger.error("Error title crew row: {}", Arrays.toString(row), e);
        }
    }

    private void loadTitlePrincipals() {
        logger.info("Loading title principals...");
        try (Stream<String> reader = DataUtil.readGzipFile(BASE_PATH + "title.principals.tsv.gz")) {

            reader.forEach(line -> {
                try {
                    String[] row = line.split("\t");
                    processPrincipalRow(row);
                } catch (Exception e) {
                    logger.error("Error processing title principals row: {}", line, e);
                }
            });
            if (logger.isDebugEnabled()) {
                logger.debug("Loaded {} title principals.", titlePrincipals.size());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load title principals", e);
        }
    }

    private void processPrincipalRow(String[] row) {
        try {
            TitlePrincipal principal = new TitlePrincipal();
            principal.setTconst(row[0]);
            principal.setOrdering(Integer.parseInt(row[1]));
            principal.setNconst(row[2]);
            principal.setCategory(row[3]);
            principal.setJob(row[4].equals("\\N") ? "" : row[4].split(",")[0]);
            principal.setCharacters(row[5].equals("\\N") ? "" : row[5].split(",")[0]);
            titlePrincipals.computeIfAbsent(principal.getTconst(),
                    k -> Collections.synchronizedList(new ArrayList<>())).add(principal);
        } catch (Exception e) {
            logger.error("Error title principals row: {}", Arrays.toString(row), e);
        }
    }

    private void loadNames() {
        logger.info("Loading names...");
        try (Stream<String> reader = DataUtil.readGzipFile(BASE_PATH + "name.basics.tsv.gz")) {

            reader.forEach(line -> {
                try {
                    String[] row = line.split("\t");
                    processNameRow(row);
                } catch (Exception e) {
                    logger.error("Error processing names row: {}", line, e);
                }
            });
            if (logger.isDebugEnabled()) {
                logger.debug("Loaded {} names.", names.size());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load names", e);
        }
    }

    private void loadTitleRatings() {
        logger.info("Loading title rating...");
        try (Stream<String> reader = DataUtil.readGzipFile(BASE_PATH + "title.ratings.tsv.gz")) {

            reader.forEach(line -> {
                try {
                    String[] row = line.split("\t");
                    processRatingRow(row);
                } catch (Exception e) {
                    logger.error("Error processing title rating row: {}", line, e);
                }
            });
            if (logger.isDebugEnabled()) {
                logger.debug("Loaded {} Title Rating.", titleRatings.size());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load title ratings", e);
        }
    }

    private void processRatingRow(String[] row) {
        try {
            TitleRating rating = new TitleRating();
            rating.setTconst(row[0]);
            rating.setAverageRating(DataUtil.parseSafeDouble(row[1]));
            rating.setNumVotes(DataUtil.parseSafeInt(row[2]));
            titleRatings.put(rating.getTconst(), rating);
        } catch (Exception e) {
            logger.error("Error title rating row: {}", Arrays.toString(row), e);
        }
    }

    private void processNameRow(String[] row) {
        try {
            Person person = new Person();
            person.setNconst(row[0]);
            person.setPrimaryName(row[1]);
            person.setBirthYear((DataUtil.parseSafeInt(row[2])));
            person.setDeathYear(DataUtil.parseSafeInt(row[3]));
            person.setPrimaryProfession((row[4].equals("\\N") ? new String[0] : row[4].split(",")));
            person.setKnownForTitles((row[5].equals("\\N") ? new String[0] : row[5].split(",")));
            names.put(person.getNconst(), person);
        } catch (Exception e) {
            logger.error("Error title rating row: {}", Arrays.toString(row), e);
        }
    }

    public boolean hasSameAliveDirectorWriter(TitleCrew crew) {
        return Arrays.stream(crew.getDirectors())
                .anyMatch(directorId -> Arrays.asList(crew.getWriters()).contains(directorId) &&
                        isPersonAlive(directorId));
    }

    public boolean isPersonAlive(String personId) {
        Person person = getNames().get(personId);
        return person != null && !person.isAlive();
    }
}