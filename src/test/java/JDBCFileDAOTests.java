import fileUtilities.File;
import fileUtilities.Filetype;
import fileUtilities.JDBCFileDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by jcoll on 29/05/2014.
 */

public class JDBCFileDAOTests {

    private final static Logger LOGGER = Logger.getLogger(JDBCFileDAOTests.class.getName());


    Properties prop;
    private Connection conn = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private String databaseUrl;

    @Before
    public void testConnection() {
        prop = new Properties();
        InputStream input = null;


        try {

            String filename = "database.properties";
            input = new FileInputStream(filename);

            prop.load(input);


            databaseUrl = prop.getProperty("databaseURL");


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


    @Test
    public void testCorrectFileReturned() {

        JDBCFileDAO jdbc = new JDBCFileDAO();

        File file = jdbc.findByFileId(1850);

        org.junit.Assert.assertEquals(file.getName(), "Thumbs.db");


    }

    @Test
    public void testIncorrectFileReturned() {

        JDBCFileDAO jdbc = new JDBCFileDAO();

        File file = jdbc.findByFileId(1000);

        org.junit.Assert.assertNotEquals(file.getName(), "dummy.txt");

    }

    @Test
    public void testDBInsertionWithIDFails() {

        //todo for now this merely tests th
        JDBCFileDAO jdbc = new JDBCFileDAO();

        File file = new File();

        file.setId(1);
        file.setName("Dummy Entry");
        file.setPath("Z:/Dummy Path/Dummy Folder");
        file.setType(Filetype.UNKNOWN);

        jdbc.insertFile(file);


    }

}
