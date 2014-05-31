package fileUtilities;


import mediaUtilities.Movie;
import mediaUtilities.MovieScanner;

import java.util.ArrayList;

/**
 *
 * This is a dummy test class for trying out various things. This will eventually be replaced with a spring or similar loader
 *
 * Created by James on 28/05/2014.
 */

public class test {

    public static void main(String args[]) {


        // FileExtensionFinder fExt = new FileExtensionFinder();

        //HashSet<String> exts = fExt.getFileExtensions();

        // for (String ext : exts) {
        //     System.out.println(ext);
        // }

        JDBCFileDAO jd = new JDBCFileDAO();

        // jd.dropFilesTable();

        // jd.createFilesTable();


        FileSystemScanner sfs = new FileSystemScanner();

       /* ArrayList<File> files = jd.findByQuality(Filequality.DUALAUDIO);

        for (File file : files) {
            System.out.println(file.getName());
        }

        files = jd.findByQuality(Filequality.LOWQUAL);

        for (File file : files) {
            System.out.println(file.getName());
        }

        files = jd.findByQuality(Filequality.SUBPROB);

        for (File file : files) {
            System.out.println(file.getName());
        }*/

        MovieScanner mScanner = new MovieScanner();

        mScanner.processMovies();

        ArrayList<Movie> movies = mScanner.getMoviesArrayList();

        for (Movie m : movies) {
            System.out.println(m.getDisplayName() + " *** " + m.getYear());
        }

        //JDBCFileDAO jd = new JDBCFileDAO();
        /*
        // jd.deleteFileById(8014);


        ArrayList<File> results = new ArrayList<File>();

        results = jd.findByFilename("folder.jpg");

        System.out.println(results.size());

        ArrayList<File> newResults = new ArrayList<File>();

        newResults = jd.findByFiletype(Filetype.VIDEO);

        System.out.println(newResults.size());

        ArrayList<File> new2Results = new ArrayList<File>();

        new2Results = jd.findByExtension("mp4");

        System.out.println(new2Results.size());

        File file = jd.findByFileId(10000);

        jd.insertFile(file);*/

        // File file = jd.findByFileId(28690);

        //WikiScanner wScanner = new WikiScanner();

        // MovieScanner mScanner = new MovieScanner();

        //  mScanner.processMovies();

        // wScanner.doWikiLookup(movie);
        // wScanner.doWikiLookupMediumForm(movie);
        // wScanner.doWikiLookupLongForm(movie, year);



    }
}
