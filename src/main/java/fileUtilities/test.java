package fileUtilities;


/**
 * Created by James on 28/05/2014.
 */

public class test {

    public static void main(String args[]) {


        // FileExtensionFinder fExt = new FileExtensionFinder();

        //  HashSet<String> exts = fExt.getFileExtensions();

        //  for(String ext:exts) {
        //      System.out.println(ext);
        //  }

        //FileSystemScanner sfs = new FileSystemScanner();
        //sfs.search();

        JDBCFileDAO jdbc = new JDBCFileDAO();

        File file = new File();

        file = jdbc.findByFileId(1827);

        System.out.println(file.getName());
        System.out.println(file.getPath());
        System.out.println(file.getId());
        System.out.println(file.getType().name());

        ///blaarghhh
    }
}
