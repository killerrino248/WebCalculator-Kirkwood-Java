package edu.kirkwood.dao.impl;

import edu.kirkwood.dao.GameDAO;
import edu.kirkwood.model.Game;
import edu.kirkwood.model.xml.GameSearchResult;
import edu.kirkwood.model.xml.OmdbGameResponse;
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

public class XmlGameDAO implements GameDAO {
    private String url;

    public XmlGameDAO(String url) {
        this.url = url;
    }

    /**
     * Retrieves all games that match a given title
     * @param title The title of a game
     * @return A List of Game objects that match the title
     */
    @Override
    public List<Game> search(String title) {
        List<GameSearchResult> results = fetch(title);
        List<Game> games = new ArrayList<>();
        results.forEach(result -> {
            Game game = new Game();
            game.setTitle(result.getTitle());
            game.setId(result.getId());
            game.setReleaseYear(result.getReleaseYear());
            game.setDescription(result.getDescription());
            games.add(game);
        });
        return games;
    }

    public List<GameSearchResult> fetch(String title) {
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("URL must be defined.");
        }

        String encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8);
        String apiURL = String.format("%s&s=%s&page=1", url, encodedTitle);

        int page = 1;
        List<GameSearchResult> games = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();

        while (true) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiURL))
                    .build();

            try {
                HttpResponse<String> response = client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );

                String rawXml = response.body();
                OmdbGameResponse omdbResponse = parseXml(rawXml);

                if (omdbResponse.getResponse().equals("True")) {
                    games.addAll(omdbResponse.getSearchResults());
                } else {
                    break;
                }

                page++;
                apiURL = String.format("%s&s=%s&page=%s", url, encodedTitle, page);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return games;
    }

    /**
     * Parses XML into an OmdbGameResponse object
     * @param xml The raw XML data
     * @return an OmdbGameResponse containing list of games, total count, response value
     */
    public OmdbGameResponse parseXml(String xml) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(OmdbGameResponse.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        StringReader reader = new StringReader(xml);
        return (OmdbGameResponse) unmarshaller.unmarshal(reader);
    }
}
