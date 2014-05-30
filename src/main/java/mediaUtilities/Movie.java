package mediaUtilities;

/**
 * Created by jcoll on 30/05/2014.
 */
public class Movie {

    private String englishName;
    private String foreignName;
    private int year;

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
