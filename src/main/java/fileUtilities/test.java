package fileUtilities;


import wikiUtilities.WikiScanner;

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

        FileSystemScanner sfs = new FileSystemScanner();


       /* JDBCFileDAO jd = new JDBCFileDAO();
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

        WikiScanner wScanner = new WikiScanner();

        String movie = " The General";
        int year = 1998;

        wScanner.doWikiLookup(movie);
        wScanner.doWikiLookupMediumForm(movie);
        wScanner.doWikiLookupLongForm(movie, year);



    }
}
