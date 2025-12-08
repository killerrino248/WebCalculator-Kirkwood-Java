package edu.kirkwood.controller;

import edu.kirkwood.dao.MovieDAO;
import edu.kirkwood.dao.MovieDAOFactory;
import edu.kirkwood.dao.impl.JsonMovieDAO;
import edu.kirkwood.dao.impl.MySQLMovieDAO;
import edu.kirkwood.dao.impl.XmlMovieDAO;
import edu.kirkwood.model.Movie;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value="/movies")
public class MovieServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String search = req.getParameter("movieSearch");
        req.setAttribute("movieSearch", search);

        if (search != null && !search.isEmpty()) {
            try {
                List<Movie> results = getMovieResults(search);

                req.setAttribute("movies", results);

            } catch (RuntimeException ex) {
                req.setAttribute("searchError", "An error occurred while searching for movies.");
            }
        }
        else {
            req.setAttribute("searchError", "No results found.");
        }

        req.getRequestDispatcher("WEB-INF/movies.jsp").forward(req, resp);
    }

    public static List<Movie> getMovieResults(String search) {
        try {
            MovieDAO movieDAO = MovieDAOFactory.getMovieDAO();
            List<Movie> movies = new ArrayList<>();

            if (movieDAO instanceof XmlMovieDAO xmlDAO)
                movies.addAll(xmlDAO.search(search));
            else if (movieDAO instanceof MySQLMovieDAO sqlDAO)
                movies.addAll(sqlDAO.search(search));
            else if (movieDAO instanceof JsonMovieDAO jsonDAO)
                movies.addAll(jsonDAO.search(search));

            return movies;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}