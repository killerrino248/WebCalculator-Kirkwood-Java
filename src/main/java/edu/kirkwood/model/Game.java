package edu.kirkwood.model;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Objects;

/*  This is a Plain Old Java Object (POJO), aka Java Bean */
public class Game implements Comparable<Game> {
    private String id;
    private String title;
    private int releaseYear;
    private String description;

    public Game() {
    }

    public Game(String id, String title, int releaseYear, String description) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", releaseYear=" + releaseYear +
                ", description='" + description + '\'' +
                '}';
    }

    /**
     * Compares two Game objects by their ID
     * @param o The other Game object to be compared
     * @return An int indicating the order of two objects
     */
    @Override
    public int compareTo(@NotNull Game o) {
        if (this.id.length() != o.id.length()) {
            return Integer.compare(this.id.length(), o.id.length());
        }
        return this.id.compareToIgnoreCase(o.id);
    }

    // Comparators
    public static Comparator<Game> compareTitle =
            (g1, g2) -> g1.title.compareToIgnoreCase(g2.title);

    public static Comparator<Game> compareYear =
            Comparator.comparingInt(Game::getReleaseYear);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return releaseYear == game.releaseYear &&
                Objects.equals(id, game.id) &&
                Objects.equals(title, game.title) &&
                Objects.equals(description, game.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, releaseYear, description);
    }
}
