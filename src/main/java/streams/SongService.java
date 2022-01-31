package streams;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class SongService {
    private List<Song> songs = new ArrayList<>();

    public void addSong(Song song) {
        songs.add(song);
    }

    public Optional<Song> shortestSong() {
        return songs.stream()
                .min(Comparator.comparingInt(Song::getLength));
    }

    public List<Song> findSongByTitle(String title) {
        return songs.stream()
                .filter(s->s.getTitle().equals(title))
                .toList();
    }

    public boolean isPerformerInSong(Song song, String performer) {
      return song.getPerformers().stream()
              .anyMatch(p->p.equals(performer));
    }

    public List<String> titlesBeforeDate(LocalDate beforeDate) {
        return songs.stream()
                .filter(s->s.getRelease().isBefore(beforeDate))
                .map(Song::getTitle)
                .toList();
    }
}
