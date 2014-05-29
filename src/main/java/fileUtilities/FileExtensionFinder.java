package fileUtilities;

/**
 *
 * This class looks through a given directory or file system and picks out
 * all of the file extensions for later identification.
 *
 * It places them in a HashSet for now as this means I don't have to sort
 * for uniqueness.
 *
 * Created by James on 28/05/2014.
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Properties;
import java.util.logging.Logger;


public class FileExtensionFinder {


    private final static Logger LOGGER = Logger.getLogger(FileExtensionFinder.class.getName());
    private HashSet<String> extensions;
    private Path path;


    FileExtensionFinder() {

        extensions = new HashSet<String>();
        setParameters();
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

    public HashSet<String> getFileExtensions() {
        return extensions;
    }


    private void search() {


        DirectoryStream<Path> ds = null;
        try {
            ds = Files.newDirectoryStream(path);

            for (Path file : ds) {

                listFiles(file);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        LOGGER.info("File extension search completed with " + extensions.size() + " extensions found.");


    }

    private void listFiles(Path path) throws IOException {


        DirectoryStream<Path> stream = Files.newDirectoryStream(path);
        for (Path entry : stream) {
            if (Files.isDirectory(entry)) {
                listFiles(entry);
            }

            extractFileExtensions(entry);

        }


    }

    private void extractFileExtensions(Path entry) {

        String filename = entry.toString();
        String extension = "";
        int i = filename.lastIndexOf('.');
        int p = Math.max(filename.lastIndexOf('/'), filename.lastIndexOf('\\'));

        if (i > p) {


            String tmp = filename.substring(i + 1);
            extensions.add(tmp);
        }
    }


}
