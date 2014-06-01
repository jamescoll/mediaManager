package mediaUtilities;


import fileUtilities.File;
import fileUtilities.JDBCFileDAO;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * The movie scanner will populate the movie database table with the display name of each movie and also an id connecting it to related
 * files. The initial movie object is barebones and will be embellished by information gathered from the web.
 * <p/>
 * Created by jcoll on 30/05/2014.
 */



public class MovieScanner {

    private HashSet<String> uniqueNames;
    private JDBCFileDAO jdbcFileDAO;
    private JDBCMovieDAO jdbcMovieDAO;
    private ArrayList<File> filesArrayList;
    private ArrayList<Movie> moviesArrayList;
    //this is trickier than it looks because some words are subsets of each other Part1 if removed from Part10 will leave a trailing 0
    //using correct order in this list is important
    private String[] removeWords = {
            "SUBPROB", "NOSUB", "DUALAUDIO", "WRONGLANG", "LOWQUAL", "Part10", "Part11", "Part12", "Part13", "Part14", "Part15", "Part16",
            "Part1", "Part2", "Part3", "Part4", "Part5", "Part6", "Part7", "Part8", "Part9", "Part10", "CD1", "CD2",
            "P01", "P02", "P03", "P04", "P05", "P06", "P07", "P08", "P09", "P10", "Parte 1", "Parte 2", "Parte 3", "Parte 4", "Parte 5", "Part 1", "Part 2"
    };

    public MovieScanner() {
        jdbcFileDAO = new JDBCFileDAO();
        jdbcMovieDAO = new JDBCMovieDAO();
        uniqueNames = new HashSet<String>();
        moviesArrayList = new ArrayList<Movie>();

    }


    public void processMovies() {


        filesArrayList = jdbcFileDAO.selectAllMovies();

        for (File file : filesArrayList) {

            String movieName = file.getName();
            Movie currentMovie = new Movie();

            movieName = removeWords(movieName);


            int startBracket = movieName.indexOf('[');
            int endBracket = movieName.indexOf(']');
            int year = 0;

            //extract the year and get rid of the leading brackets
            if (startBracket != -1) {
                year = Integer.parseInt(movieName.substring(startBracket + 1, startBracket + 5));
                movieName = movieName.substring(endBracket + 1);
            }

            //get rid of the extension
            int lastDot = movieName.lastIndexOf('.');

            movieName = movieName.substring(0, lastDot).trim();

            //use our hashset to check if this is a unique film
            if (uniqueNames.add((movieName))) {

                currentMovie.setYear(year);
                currentMovie.setDisplayName(movieName);
                currentMovie.setFilesId(file.getMovieId());
                moviesArrayList.add(currentMovie);
            }


        }

        //add movies to db
        for (Movie m : moviesArrayList) {
            jdbcMovieDAO.insertBasicMovie(m);
        }

    }


    public String removeWords(String movieName) {

        for (String word : removeWords) {


            String wordPreExtended = " - " + word;
            String wordPrePostExtended = " - " + word + " - ";
            movieName = movieName.replace(wordPreExtended, "");
            movieName = movieName.replace(wordPrePostExtended, "");
            movieName = movieName.replace(word, "");
            movieName = movieName.trim();

        }

        return movieName;
    }


}
