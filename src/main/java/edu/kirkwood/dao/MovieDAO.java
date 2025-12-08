package edu.kirkwood.dao;

import edu.kirkwood.model.Movie;

import java.util.List;

public interface MovieDAO {

    // This is an abstract method
    // An abstract method has no implementation (no curly brackets, no access modifer)
    // You only need to define the method's name, inputs, and outputs
    /**
     * Retrieves all movies that match a given title
     * @param title The title of a movie
     * @return A List of Games objects that match the title
     */
    List<Movie> search(String title);
}
