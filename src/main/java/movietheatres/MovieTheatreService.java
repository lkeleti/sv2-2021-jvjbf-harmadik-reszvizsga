package movietheatres;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.*;

public class MovieTheatreService {
    private Map<String, List<Movie>> shows = new LinkedHashMap<>();

    public Map<String, List<Movie>> getShows() {
        return new LinkedHashMap<>(shows);
    }

    public void readFromFile(Path path) {
        try (BufferedReader br = new BufferedReader(Files.newBufferedReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] theatreAndMovie = line.split("-");
                String[] movieData = theatreAndMovie[1].split(";");
                String key = theatreAndMovie[0];
                shows.computeIfAbsent(key, m -> new ArrayList<>());
                shows.get(key).add(new Movie(movieData[0], LocalTime.parse(movieData[1])));
            }
            sortShows();
        } catch (IOException ioe) {
            throw new IllegalStateException("Can't read file!", ioe);
        }
    }

    public List<String> findMovie(String title) {
        List<String> result = new ArrayList<>();

        for (Map.Entry<String, List<Movie>> entry : shows.entrySet()) {
            if (entry.getValue().stream()
                    .anyMatch(m -> m.getTitle().equals(title))) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    public LocalTime findLatestShow(String title) {
        Optional<Movie> result;

        result = shows.values().stream()
                .flatMap(Collection::stream)
                .filter(m -> m.getTitle().equals(title))
                .max(Comparator.comparing(Movie::getStartTime));

        if (result.isEmpty()) {
            throw new IllegalArgumentException("Can't find movie!");
        } else {
            return result.get().getStartTime();
        }
    }

    private void sortShows() {
        shows.values().stream()
                .forEach(l -> l.sort(Comparator.comparing(Movie::getStartTime)));
    }
}
