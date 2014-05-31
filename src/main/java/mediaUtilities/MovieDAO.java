package mediaUtilities;

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
}
