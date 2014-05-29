package fileUtilities;


import java.util.HashSet;

/**
 * Created by James on 28/05/2014.
 */

public class test {

    public static void main(String args[]) {


        FileExtensionFinder fExt = new FileExtensionFinder();

        HashSet<String> exts = fExt.getFileExtensions();

        for (String ext : exts) {
            System.out.println(ext);
        }

        FileSystemScanner sfs = new FileSystemScanner();
        sfs.search();


    }
}
