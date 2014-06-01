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

    //todo this may fail if the folder.jpg image is in the parent folder of multi-folder folders
    //this is used to trace which files belong in which folder and will be used to associate files with a given movie
    private int folderId;


    public FileSystemScanner() {


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

            folderId = 10000;

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


                    //todo this fails in the case of multifolder directories (Berlin Alexanderplatz)
                    //and in folders in which there are multiple but non-related films (Night on Earth)
                    folderId++;


                    processFiles(entry);


                } else {


                    File tmpFile = new File();
                    tmpFile.setName(entry.getFileName().toString());
                    tmpFile.setType(getFileType(getFileExtension(entry.getFileName().toString())));
                    tmpFile.setPath(entry.toAbsolutePath().toString().substring(pathLength + 1));
                    tmpFile.setExtension(getFileExtension(entry.toAbsolutePath().toString()));
                    tmpFile.setQuality(getFileQuality(entry.getFileName().toString()));
                    tmpFile.setMovieId(folderId);

                    if (!((entry.getFileName().toString().equals("Thumbs.db")) || (entry.getFileName().toString().equals(".DS_Store")))) {
                        jdbc.insertFile(tmpFile);
                    }


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


    private Filequality getFileQuality(String filename) {

        if (filename.toLowerCase().contains(Filequality.SUBPROB.toString().toLowerCase())) {
            return Filequality.SUBPROB;
        } else if (filename.toLowerCase().contains(Filequality.NOSUB.toString().toLowerCase())) {
            return Filequality.NOSUB;
        } else if (filename.toLowerCase().contains(Filequality.DUALAUDIO.toString().toLowerCase())) {
            return Filequality.DUALAUDIO;
        } else if (filename.toLowerCase().contains(Filequality.WRONGLANG.toString().toLowerCase())) {
            return Filequality.WRONGLANG;
        } else if (filename.toLowerCase().contains(Filequality.LOWQUAL.toString().toLowerCase())) {
            return Filequality.LOWQUAL;
        } else return Filequality.NORMAL;


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
