package edu.kirkwood.dao.impl;

import edu.kirkwood.dao.MovieDAO;
import edu.kirkwood.model.Movie;
import edu.kirkwood.model.xml.MovieSearchResult;
import edu.kirkwood.model.xml.OmdbMovieResponse;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.StringReader;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class XmlMovieDAO implements MovieDAO {
    private String url;

    public XmlMovieDAO(String url) {
        this.url = url;
    }

    /**
     * Retrieves all movies that match a given title
     * @param title The title of a movie
     * @return A List of Games objects that match the title
     */
    @Override
    public List<Movie> search(String title) {
        List<MovieSearchResult> results = fetch(title);
        List<Movie> movies = new ArrayList<>();
        results.forEach(result -> {
            Movie movie = new Movie();
            movie.setTitle(result.getTitle());
            movie.setId(result.getId());
            movie.setReleaseYear(result.getReleaseYear());
            movies.add(movie);
        });
        return movies;
    }

    public List<MovieSearchResult> fetch(String title) {
        if(url == null || url.isEmpty()) {
            throw new IllegalArgumentException("Url has to be defined");
        }
        String encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8);
        // Example: https://www.omdbapi.com/?type=movie&apikey=<API-KEY>&r=xml&s=Batman&page=1
        String apiURL = String.format("%s&s=%s&page=1", url, encodedTitle);
        int page = 1;
        List<MovieSearchResult> movies = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        while(true) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiURL))
                    .build();
            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                String rawXml = response.body();
                OmdbMovieResponse omdbMovieResponse = parseXml(rawXml);
//            omdbMovieResponse.getSearchResults().forEach((movie) -> System.out.println(movie.getTitle() + " " + movie.getReleaseYear()));
                if (omdbMovieResponse.getResponse().equals("True")) {
                    movies.addAll(omdbMovieResponse.getSearchResults());
                } else {
                    break;
                }
                page++;
                apiURL = String.format("%s&s=%s&page=%s", url, encodedTitle, page);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return movies;
    }

    /**
     * To parse an XML file converting it into a list of Games objects
     * @param xml The raw XML data
     * @return the OmdbMovieResponse object (contains list of movies, total number of movies, and a response string (True or False))
     */
    public OmdbMovieResponse parseXml(String xml) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(OmdbMovieResponse.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        StringReader reader = new StringReader(xml);
        OmdbMovieResponse movieResponse = (OmdbMovieResponse) unmarshaller.unmarshal(reader);
        return movieResponse;
    }
}
