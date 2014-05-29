package fileUtilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * This class provides the methods for scanning through a file system and populates a file table based on what it finds.
 * In the future this will be called as a batch process and cron job.
 * <p/>
 * Created by James on 29/05/2014.
 */
public class FileSystemScanner {

    private final static Logger LOGGER = Logger.getLogger(FileSystemScanner.class.getName());
    JDBCFileDAO jdbc;
    //todo expand these lists as other extensions become apparent
    private String[] ignoreExtensions = {"db", "DS_Store"};
    private String[] imageExtensions = {"jpg", "jpeg", "gif"};
    private String[] videoExtensions = {"avi", "mpg"};
    private String[] subtitleExtensions = {"sub", "srt", "idx"};
    private String[] audioExtensions = {"mp3", "ogg"};
    //todo give these variables better names
    private Path path;
    private int pathLength;

    FileSystemScanner() {


        setParameters();
        jdbc = new JDBCFileDAO();

        search();

    }

    private void setParameters() {

        Properties prop = new Properties();
        InputStream input = null;


        try {

            String filename = "config.properties";
            input = new FileInputStream(filename);


            prop.load(input);

            path = FileSystems.getDefault().getPath(prop.getProperty("filepath"));

            pathLength = path.toAbsolutePath().toString().length();

        } catch (IOException e) {
            LOGGER.warning("IO Exception occurred");

        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    LOGGER.warning("IO Exception occurred");
                }
            }
        }
    }


    //todo this will ultimately fill an arraylist of files and folders
    //for now we will just take a file at once and dump it in the db
    public void search() {


        DirectoryStream<Path> ds = null;
        try {
            ds = Files.newDirectoryStream(path);

            for (Path file : ds) {

                listFiles(file);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        LOGGER.info("File System Scanner search() function called.");


    }

    private void listFiles(Path path) throws IOException {


        DirectoryStream<Path> stream = Files.newDirectoryStream(path);
        for (Path entry : stream) {
            if (Files.isDirectory(entry)) {


                listFiles(entry);


            } else {

                File tmpFile = new File();
                tmpFile.setName(entry.getFileName().toString());
                tmpFile.setType(getFileType(entry.getFileName().toString()));
                tmpFile.setPath(entry.toAbsolutePath().toString().substring(pathLength + 1));
                jdbc.insertFile(tmpFile);


            }

        }


    }


    private Filetype getFileType(String filename) {


        String extension = "";
        int i = filename.lastIndexOf('.');
        int p = Math.max(filename.lastIndexOf('/'), filename.lastIndexOf('\\'));

        if (i > p) {


            extension = filename.substring(i + 1);

            if (Arrays.asList(audioExtensions).contains(extension)) return Filetype.AUDIO;
            else if (Arrays.asList(videoExtensions).contains(extension)) return Filetype.VIDEO;
            else if (Arrays.asList(imageExtensions).contains(extension)) return Filetype.IMAGE;
            else if (Arrays.asList(subtitleExtensions).contains(extension)) return Filetype.SUBTITLE;
            else return Filetype.OTHER;


        }


        return Filetype.UNKNOWN;
    }


}
