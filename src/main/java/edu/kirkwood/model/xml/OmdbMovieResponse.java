package edu.kirkwood.model.xml;

import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement(name = "root")
@XmlAccessorType(XmlAccessType.FIELD)
public class OmdbMovieResponse {
    @XmlElement(name = "result")
    private List<MovieSearchResult> searchResults;
    @XmlAttribute(name = "totalResults")
    private int totalResults;
    @XmlAttribute(name = "response")
    private String response;

    public List<MovieSearchResult> getSearchResults() {
        return searchResults;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public String getResponse() {
        return response;
    }
}
