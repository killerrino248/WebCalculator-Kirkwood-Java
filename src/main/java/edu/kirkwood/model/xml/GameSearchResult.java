package edu.kirkwood.model.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class GameSearchResult {
    @XmlAttribute(name = "id")          // or imdbID if still using OMDb structure
    private String id;
    @XmlAttribute(name = "title")
    private String title;
    @XmlAttribute(name = "year")
    private int releaseYear;
    // Optional field if the XML contains it, otherwise stays null.
    @XmlAttribute(name = "description")
    private String description;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "GameSearchResult{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", releaseYear=" + releaseYear +
                ", description='" + description + '\'' +
                '}';
    }
}
