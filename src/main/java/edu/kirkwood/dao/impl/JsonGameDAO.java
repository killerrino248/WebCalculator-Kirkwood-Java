package edu.kirkwood.dao.impl;

import com.google.gson.*;
import edu.kirkwood.dao.GameDAO;
import edu.kirkwood.dao.JsonTypeAdapter;
import edu.kirkwood.model.Game;
import edu.kirkwood.model.json.TmdbGameResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JsonGameDAO implements GameDAO {

    private String apiURL;
    private String apiKey;

    public JsonGameDAO(String apiURL, String apiKey) {
        this.apiURL = apiURL;
        this.apiKey = apiKey;
    }

    public String fetchRawData(String title, int page) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(apiURL + "query=" + title + "&page=" + page)
                .get()
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("accept", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Outputs an unformatted JSON string into a human-readable format
     */
    public void prettyPrint(String json) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        JsonElement jsonElement = JsonParser.parseString(json);
        String formattedJson = gson.toJson(jsonElement);
        System.out.println(formattedJson);
    }

    /**
     * Retrieves all games from the data source that match the given title
     * @param title The game title a user is searching for
     * @return A list of Game objects
     */
    @Override
    public List<Game> search(String title) {
        List<Game> games = new ArrayList<>();
        int currentPage = 1;

        while (true) {
            String rawData = fetchRawData(title, currentPage);

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new JsonTypeAdapter.LocalDateDeserializer())
                    .create();

            TmdbGameResponse gameResponse;
            try {
                gameResponse = gson.fromJson(rawData, TmdbGameResponse.class);
            } catch (JsonSyntaxException e) {
                throw new RuntimeException(e);
            }

            gameResponse.getResults().forEach(result -> {
                Game game = new Game();
                game.setId(result.getId());
                game.setTitle(result.getName());

                if (result.getFirst_release_date() != null) {
                    game.setReleaseYear(result.getFirst_release_date().getYear());
                }

                game.setDescription(result.getSummary());
                games.add(game);
            });

            if (gameResponse.getTotal_pages() > currentPage) {
                currentPage++;
            } else {
                break;
            }
        }

        return games;
    }
}
