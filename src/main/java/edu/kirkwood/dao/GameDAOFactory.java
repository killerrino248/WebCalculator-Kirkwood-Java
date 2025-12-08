package edu.kirkwood.dao;

//import edu.kirkwood.dao.impl.JsonGameDAO;
import edu.kirkwood.dao.impl.MySQLGameDAO;
import edu.kirkwood.dao.impl.XmlGameDAO;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GameDAOFactory {
    private static Properties properties = new Properties();

    static {
        try (InputStream input = GameDAOFactory.class.getClassLoader()
                .getResourceAsStream("application.properties")) {

            if (input == null) {
                throw new RuntimeException("application.properties file not found");
            }
            properties.load(input);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a GameDAO (XMLGameDAO, MySQLGameDAO, JsonGameDAO, etc.)
     */
    public static GameDAO getGameDAO() {
        String sourceType = properties.getProperty("datasource.type");

        if (sourceType == null || sourceType.isEmpty()) {
            throw new RuntimeException("Unknown datasource type");
        }

        switch (sourceType.toUpperCase()) {
            case "XML":
                String xmlApiURL = properties.getProperty("xml.apiurl");
                if (xmlApiURL == null || xmlApiURL.isEmpty()) {
                    throw new RuntimeException("xml.apiurl is empty");
                }
                return new XmlGameDAO(xmlApiURL);

            case "MYSQL":
                return new MySQLGameDAO();

//            case "JSON":
//                String jsonApiURL = properties.getProperty("json.apiURL");
//                if (jsonApiURL == null || jsonApiURL.isEmpty()) {
//                    throw new IllegalArgumentException("json.apiURL is required");
//                }
//
//                String jsonReadAccessToken = properties.getProperty("json.apiReadAccessToken");
//                if (jsonReadAccessToken == null || jsonReadAccessToken.isEmpty()) {
//                    throw new IllegalArgumentException("json.apiReadAccessToken is required");
//                }
//
//                return new JsonGameDAO(jsonApiURL, jsonReadAccessToken);

            // Example placeholder:
            // case "MONGODB":
            //     return new MongoGameDAO();

            default:
                throw new RuntimeException("Unsupported datasource type: " + sourceType);
        }
    }
}
