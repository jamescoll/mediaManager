package mediaUtilities;


import fileUtilities.File;
import fileUtilities.JDBCFileDAO;

import java.util.ArrayList;

/**
 *
 * The movie scanner will populate the movie database table with the display name of each movie. In later versions
 * there will need to be a unique id for each unique movie and other information which will be provided by the lookup.
 * It will be useful to also read in other relevant data (year, for example) while doing this lookup.
 *
 * Created by jcoll on 30/05/2014.
 */
public class MovieScanner {

    private JDBCFileDAO jdbcFileDAO;
    private ArrayList<File> filesArrayList;
    private ArrayList<Movie> moviesArrayList;

    private String[] removeWords = {
            "SUBPROB", "NOSUB", "DUALAUDIO", "WRONGLANG", "LOWQUAL", "Part1", "Part2"
    };

    public MovieScanner() {
        jdbcFileDAO = new JDBCFileDAO();
        setParameters();
    }

    private void setParameters() {

    }

    public void processMovies() {


        filesArrayList = jdbcFileDAO.selectAllMovies();


        //first let's remove the trailing words from the word list

        for (File file : filesArrayList) {

            String movieName = file.getName();

            for (String word : removeWords) {

                word = " - " + word;
                movieName = movieName.replace(word, "");

            }

            moviesArrayList.add(new Movie(movieName));

        }


    }

    public ArrayList<Movie> getMoviesArrayList() {
        return moviesArrayList;
    }

    private ArrayList<String> processTitle(String filename) {

        ArrayList<String> titles = new ArrayList<String>();

        String title = "";
        String qualityMarker = "";

        int bracketStart = filename.indexOf('[');
        int bracketEnd = filename.indexOf(']');

        if (bracketStart != -1) {
            filename = filename.substring(bracketEnd + 1);
        }


        int lastDash = filename.lastIndexOf('-');
        int lastDot = filename.lastIndexOf('.');

        if (lastDash != -1) {

            qualityMarker = filename.substring(lastDash + 1, lastDot).trim();

        } else {
            title = filename.substring(0, lastDot).trim();

        }


        if (!qualityMarker.isEmpty()) {

            if (isAllUpper(qualityMarker)) {

                title = filename.substring(0, lastDot).trim();


            }
        }

        System.out.println("English title: " + title);

        System.out.println("Quality marker: " + qualityMarker);


        return titles;
    }




    private boolean isAllUpper(String s) {
        for (char c : s.toCharArray()) {
            if (Character.isLetter(c) && Character.isLowerCase(c)) {
                return false;
            }
        }
        return true;
    }
}
