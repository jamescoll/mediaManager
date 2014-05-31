package fileUtilities;


import mediaUtilities.JDBCMovieDAO;
import mediaUtilities.Movie;
import mediaUtilities.MovieScanner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

//todo add folderId or similar id to the movie and to the file fields which will allow for easy fetching of files related to a movie
//consider the case for multipart media like berlin alexanderplatz etc...

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

        jd.dropFilesTable();

        jd.createFilesTable();

        JDBCMovieDAO md = new JDBCMovieDAO();

        md.dropMoviesTable();

        md.createMoviesTable();


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

        ArrayList<Movie> movies = new ArrayList<Movie>();

        //for (Movie m : movies) {
        //  System.out.println("[" + m.getYear() + "] " + m.getDisplayName());
        // }

        //todo...hashmap works... let's figure out this approach and use it
        Iterator it = mScanner.uniqueNames.entrySet().iterator();
        while (it.hasNext()) {

            Map.Entry pairs = (Map.Entry) it.next();
            // System.out.println(pairs.getKey() + " = " + pairs.getValue());
            int year = Integer.parseInt(pairs.getValue().toString());
            String movieName = pairs.getKey().toString();
            Movie m = new Movie(year, movieName);
            movies.add(m);
            md.insertBasicMovie(m);
            it.remove(); // avoids a ConcurrentModificationException
        }


        for (Movie m : movies) {
            System.out.println("[" + m.getYear() + "] " + m.getDisplayName());
        }

        // System.out.println();
        //  System.out.println(mScanner.removeWords("Berlin Alexanderplatz - Part10.avi"));

        // System.out.println("Number of movies is " + movies.size());

       /* System.out.println(movies.get(12).getDisplayName());
        System.out.println(movies.get(12).getYear());
        System.out.println(movies.get(13).getDisplayName());
        System.out.println(movies.get(13).getYear());

        System.out.println(movies.get(12).getDisplayName().trim() == "The Vampires - Les Vampires");*/

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
