package fileUtilities;


import java.util.ArrayList;

/**
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

        FileSystemScanner sfs = new FileSystemScanner();

        ArrayList<File> files = jd.findByQuality(Filequality.DUALAUDIO);

        for (File file : files) {
            System.out.println(file.getName());
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
