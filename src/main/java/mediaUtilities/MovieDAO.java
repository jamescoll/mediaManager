package mediaUtilities;

import java.util.ArrayList;

/**
 * Created by jcoll on 30/05/2014.
 */
public interface MovieDAO {

    //this method inserts a movie by year and display name with no other properties set
    public void insertBasicMovie(Movie movie);

    //this method deletes a movie identified by display name and year
    public void deleteBasicMovie(Movie movie);

    //this method deletes the movies table - use with care
    public void dropMoviesTable();

    //this method creates the movies table - use with care
    public void createMoviesTable();

    //this method allows us to select a given movie by id
    public Movie selectMovie(int movieId);

    //this method allows us to select all movies
    public ArrayList<Movie> selectAllMovies();

    //this method allows us to select them sorted by year
    public ArrayList<Movie> selectAllMoviesSortedByYear();


}
