package fileUtilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by James on 29/05/2014.
 */
public class JDBCFileDAO implements FileDAO {

    private final static Logger LOGGER = Logger.getLogger(JDBCFileDAO.class.getName());
    Properties prop;
    private Connection conn = null;

    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private String databaseUrl;

    JDBCFileDAO() {
        setParameters();
    }

    private void setParameters() {

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


    @Override
    public void insertFile(File file) {

        String sql = "INSERT INTO FILES (Filetype, Filename, Filepath) VALUES (?, ?, ?)";


        try {

            conn = DriverManager.getConnection(databaseUrl, prop);

            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, file.getType().ordinal());
            preparedStatement.setString(2, file.getName());
            preparedStatement.setString(3, file.getPath());
            preparedStatement.executeUpdate();
            preparedStatement.close();


        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }

    }


    @Override
    public File findByFileId(int folderId) {

        String sql = "SELECT * FROM FILES WHERE id = ?";

        File file = new File();

        try {

            conn = DriverManager.getConnection(databaseUrl, prop);

            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, folderId);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                file.setId(resultSet.getInt("id"));
                file.setType(mapIntToFiletype(resultSet.getInt("Filetype")));
                file.setName(resultSet.getString("Filename"));
                file.setPath(resultSet.getString("Filepath"));
            }



        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }


        return file;


    }

    private Filetype mapIntToFiletype(int filetypeAsInt) {

        switch(filetypeAsInt) {
            case 0 : return Filetype.AUDIO;
            case 1 : return Filetype.VIDEO;
            case 2 : return Filetype.IMAGE;
            case 3 : return Filetype.SUBTITLE;
            case 4 : return Filetype.OTHER;
            case 5 : return Filetype.UNKNOWN;
            default : return Filetype.UNKNOWN;

        }
    }
}
