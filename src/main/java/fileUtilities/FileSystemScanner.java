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
    Properties prop;
    private String[] audioExtensions = {"mp3", "ogg", "wma"};
    private String[] videoExtensions = {"Divx", "Xvid", "rmvb", "ogm", "mkv", "MPEG", "avi", "mpg", "asf", "flv", "mp4", "m4v", "wmv", "mdf", "mpeg", "AVI", "webm", "VOB", "divx"};
    private String[] imageExtensions = {"jpg", "jpeg", "gif", "JPG", "png"};
    private String[] subtitleExtensions = {"sub", "srt", "idx", "SRT"};
    private String[] textExtensions = {"pdf", "txt", "rtf", "nfo"};
    private String[] isoExtensions = {"bin", "dmg", "ISO", "img"};
    private String[] ignoreExtensions = {"db", "DS_Store", "ini", "BUP", "IFO", "smi", "mds", "cue", "rar"};
    private Path path;
    private int pathLength;

    FileSystemScanner() {


        prop = new Properties();
        setParameters();
        jdbc = new JDBCFileDAO();
        processFiles(path);

    }

    private void setParameters() {


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


    private void processFiles(Path path) {


        try {
            DirectoryStream<Path> stream = Files.newDirectoryStream(path);
            for (Path entry : stream) {
                if (Files.isDirectory(entry)) {


                    processFiles(entry);


                } else {

                    File tmpFile = new File();
                    tmpFile.setName(entry.getFileName().toString());
                    tmpFile.setType(getFileType(entry.getFileName().toString()));
                    tmpFile.setPath(entry.toAbsolutePath().toString().substring(pathLength + 1));
                    tmpFile.setExtension(getFileExtension(entry.toAbsolutePath().toString()));
                    tmpFile.setType(getFileType(tmpFile.getExtension()));
                    jdbc.insertFile(tmpFile);


                }

            }


        } catch (IOException e) {
            LOGGER.warning("Error processing file into database");
        }


    }

    private String getFileExtension(String filename) {
        String extension = "";
        int i = filename.lastIndexOf('.');
        int p = Math.max(filename.lastIndexOf('/'), filename.lastIndexOf('\\'));

        if (i > p) {


            extension = filename.substring(i + 1);
        } else {
            LOGGER.info("File doesn't seem to have an extension " + filename);
        }

        return extension;
    }


    private Filetype getFileType(String extension) {

            if (Arrays.asList(audioExtensions).contains(extension)) return Filetype.AUDIO;
            else if (Arrays.asList(videoExtensions).contains(extension)) return Filetype.VIDEO;
            else if (Arrays.asList(imageExtensions).contains(extension)) return Filetype.IMAGE;
            else if (Arrays.asList(subtitleExtensions).contains(extension)) return Filetype.SUBTITLE;
            else if (Arrays.asList(textExtensions).contains(extension)) return Filetype.TEXT;
            else if (Arrays.asList(isoExtensions).contains(extension)) return Filetype.ISO;
            else if (Arrays.asList(ignoreExtensions).contains(extension)) return Filetype.OTHER;
            else return Filetype.UNKNOWN;


    }


}
