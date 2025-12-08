package edu.kirkwood.dao.impl;

import edu.kirkwood.dao.GameDAO;
import edu.kirkwood.model.Game;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static edu.kirkwood.dao.MySQLConnection.getConnection;

public class MySQLGameDAO implements GameDAO {

    @Override
    public List<Game> search(String title) {
        try (Connection connection = getConnection()) {

            CallableStatement statement =
                    connection.prepareCall("{ CALL sp_SearchGamesByTitle(?) }");

            statement.setString(1, title);

            ResultSet resultSet = statement.executeQuery();
            List<Game> games = new ArrayList<>();

            while (resultSet.next()) {
                Game game = new Game();
                game.setId(resultSet.getString("game_id"));
                game.setTitle(resultSet.getString("title"));
                game.setReleaseYear(resultSet.getInt("year"));
                game.setDescription(resultSet.getString("description"));
                games.add(game);
            }

            return games;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
