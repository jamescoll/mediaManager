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


    private String displayName;
    private String englishName;
    private String foreignName;
    private int year;

    public Movie() {

    }

    public Movie(String displayName) {

        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getForeignName() {
        return foreignName;
    }

    public void setForeignName(String foreignName) {
        this.foreignName = foreignName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String name) {
        this.englishName = englishName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }


}
