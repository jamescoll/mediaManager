package mediaUtilities;

/**
 *
 * This class will store movies using information gathered from the database (i.e. the filesystem) and also
 * information gathered from rotten tomatoes and wikipedia. Certain properties may be phased out - like displayName
 * which represents the string part of the filename in the database but is - divided in different ways. No easy technique
 * except an API look-up will get the english and/or foreign name separately
 *
 *
 * Created by jcoll on 30/05/2014.
 */
public class Movie {


    private int movieId;
    private String displayName;
    private int year;
    private int filesId;

    public Movie() {

    }

    public Movie(int year, String displayName, int movieId) {
        this.year = year;
        this.displayName = displayName;
    }


    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getFilesId() {
        return filesId;
    }

    public void setFilesId(int filesId) {
        this.filesId = filesId;
    }


}
