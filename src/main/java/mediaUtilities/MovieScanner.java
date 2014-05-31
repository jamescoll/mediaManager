package mediaUtilities;


import fileUtilities.File;
import fileUtilities.JDBCFileDAO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 *
 * The movie scanner will populate the movie database table with the display name of each movie. In later versions
 * there will need to be a unique id for each unique movie and other information which will be provided by the lookup.
 * It will be useful to also read in other relevant data (year, for example) while doing this lookup.
 *
 * Created by jcoll on 30/05/2014.
 */

//todo establish a linking relationship by id with the movie on the file table there needs to be some kind of UID
//so relationships can be created

public class MovieScanner {

    private JDBCFileDAO jdbcFileDAO;
    private ArrayList<File> filesArrayList;
    private ArrayList<Movie> moviesArrayList;

    private String[] removeWords = {
            "SUBPROB", "NOSUB", "DUALAUDIO", "WRONGLANG", "LOWQUAL", "Part1", "Part2", "Part3", "Part4", "Part5", "Part6", "Part7",
            "Part8", "Part9", "Part10", "Part11", "Part12", "Part13", "Part14", "Part15", "Part16", "CD1", "CD2",
            "P01", "P02", "P03", "P04", "P05", "P06", "P07", "P08", "P09", "P10", "Parte 1", "Parte 2", "Parte 3", "Parte 4", "Parte 5", "Part 1", "Part 2"
    };

    public MovieScanner() {
        jdbcFileDAO = new JDBCFileDAO();
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

            //set up a movie object
            currentMovie.setYear(year);
            currentMovie.setDisplayName(movieName);

            //use a hashset to eliminate duplicates
            checkDuplicates();

            moviesArrayList.add(currentMovie);




        }


    }

    private void checkDuplicates() {

        HashSet<Movie> hs = new HashSet<Movie>();
        hs.addAll(moviesArrayList);
        moviesArrayList.clear();
        moviesArrayList.addAll(hs);
        Collections.sort(moviesArrayList);
    }

    private String removeWords(String movieName) {

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

    public ArrayList<Movie> getMoviesArrayList() {
        return moviesArrayList;
    }


}
