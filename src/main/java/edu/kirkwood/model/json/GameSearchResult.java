package edu.kirkwood.model.json;

import java.time.LocalDate;

public class GameSearchResult {
    private String id;
    private String name;
    private String summary;
    private LocalDate first_release_date;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSummary() {
        return summary;
    }

    public LocalDate getFirst_release_date() {
        return first_release_date;
    }

    @Override
    public String toString() {
        return "GameSearchResult{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", summary='" + summary + '\'' +
                ", first_release_date=" + first_release_date +
                '}';
    }
}
