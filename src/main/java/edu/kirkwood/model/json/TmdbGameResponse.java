package edu.kirkwood.model.json;

import java.util.List;

public class TmdbGameResponse {
    private List<GameSearchResult> results;
    private int total_pages;

    public List<GameSearchResult> getResults() {
        return results;
    }

    public int getTotal_pages() {
        return total_pages;
    }
}
