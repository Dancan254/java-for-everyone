package collections.solutions;

import java.util.*;

/**
 * Exercise 3.2 — Music Playlist Organizer
 *
 * Demonstrates grouping objects by enum, collecting unique values
 * into a Set, filtering, and counting occurrences per enum constant.
 */
public class MusicPlaylistOrganizer {

    enum Genre {
        POP, ROCK, JAZZ, CLASSICAL, HIP_HOP, ELECTRONIC
    }

    // ---- Song class ----
    static class Song {
        private final String title;
        private final String artist;
        private final Genre genre;

        Song(String title, String artist, Genre genre) {
            this.title = title;
            this.artist = artist;
            this.genre = genre;
        }

        public String getTitle() {
            return title;
        }

        public String getArtist() {
            return artist;
        }

        public Genre getGenre() {
            return genre;
        }

        @Override
        public String toString() {
            return "\"" + title + "\" by " + artist;
        }
    }

    // ---- Filter songs by a given genre ----
    public static List<Song> filterByGenre(List<Song> songs, Genre genre) {
        List<Song> result = new ArrayList<>();
        for (Song s : songs) {
            if (s.getGenre() == genre) {
                result.add(s);
            }
        }
        return result;
    }

    // ---- Count songs per genre ----
    public static Map<Genre, Integer> countByGenre(List<Song> songs) {
        Map<Genre, Integer> counts = new EnumMap<>(Genre.class);
        for (Genre g : Genre.values()) {
            counts.put(g, 0);
        }
        for (Song s : songs) {
            counts.put(s.getGenre(), counts.get(s.getGenre()) + 1);
        }
        return counts;
    }

    public static void main(String[] args) {

        // 1. Create a playlist with 10+ songs
        List<Song> playlist = new ArrayList<>(Arrays.asList(
                new Song("Blinding Lights", "The Weeknd", Genre.POP),
                new Song("Shape of You", "Ed Sheeran", Genre.POP),
                new Song("Levitating", "Dua Lipa", Genre.POP),
                new Song("Bohemian Rhapsody", "Queen", Genre.ROCK),
                new Song("Stairway to Heaven", "Led Zeppelin", Genre.ROCK),
                new Song("Take Five", "Dave Brubeck", Genre.JAZZ),
                new Song("So What", "Miles Davis", Genre.JAZZ),
                new Song("Clair de Lune", "Debussy", Genre.CLASSICAL),
                new Song("Lose Yourself", "Eminem", Genre.HIP_HOP),
                new Song("Strobe", "Deadmau5", Genre.ELECTRONIC),
                new Song("Levels", "Avicii", Genre.ELECTRONIC)));

        // 2. Group songs by genre
        Map<Genre, List<Song>> byGenre = new EnumMap<>(Genre.class);
        for (Genre g : Genre.values()) {
            byGenre.put(g, new ArrayList<>());
        }
        for (Song s : playlist) {
            byGenre.get(s.getGenre()).add(s);
        }

        System.out.println("=== Songs Grouped by Genre ===");
        for (Map.Entry<Genre, List<Song>> entry : byGenre.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                System.out.println(entry.getKey() + ":");
                for (Song s : entry.getValue()) {
                    System.out.println("  " + s);
                }
            }
        }

        // 3. Unique artists
        Set<String> uniqueArtists = new TreeSet<>();
        for (Song s : playlist) {
            uniqueArtists.add(s.getArtist());
        }
        System.out.println("\nUnique artists (" + uniqueArtists.size() + "): " + uniqueArtists);

        // 4. Filter example
        System.out.println("\n--- ROCK songs ---");
        for (Song s : filterByGenre(playlist, Genre.ROCK)) {
            System.out.println("  " + s);
        }

        // 5. Count by genre and find the genre with the most songs
        Map<Genre, Integer> counts = countByGenre(playlist);
        System.out.println("\n--- Song Count by Genre ---");
        Genre topGenre = null;
        int maxCount = 0;
        for (Map.Entry<Genre, Integer> entry : counts.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                topGenre = entry.getKey();
            }
        }
        System.out.println("\nGenre with most songs: " + topGenre + " (" + maxCount + ")");
    }
}
