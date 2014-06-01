package mediaUtilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by James on 31/05/2014.
 */
public class JDBCMovieDAO implements MovieDAO {

    private final static Logger LOGGER = Logger.getLogger(JDBCMovieDAO.class.getName());

    private Properties prop;

    private Connection conn = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;


    private String databaseUrl;

    public JDBCMovieDAO() {
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
    public void insertBasicMovie(Movie movie) {

        String sql = "INSERT INTO MOVIES (Movieyear, Moviedisplayname, Moviefilesid) VALUES (?, ?, ?)";

        if (!movieInTable(movie)) {

            try {

                conn = DriverManager.getConnection(databaseUrl, prop);

                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setInt(1, movie.getYear());
                preparedStatement.setString(2, movie.getDisplayName());
                preparedStatement.setInt(3, movie.getFilesId());
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

        } else {
            LOGGER.warning(movie.getDisplayName() + " already exists in table");


        }

    }

    @Override
    public void deleteBasicMovie(Movie movie) {

        String sql = "DELETE FROM MOVIES WHERE Moviedisplayname = ? AND Movieyear = ?";


        try {

            conn = DriverManager.getConnection(databaseUrl, prop);

            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, movie.getDisplayName());
            preparedStatement.setInt(2, movie.getYear());
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

    public boolean movieInTable(Movie movie) {


        String sql = "SELECT * FROM MOVIES WHERE Movieyear = ? AND Moviedisplayname = ?";

        try {

            conn = DriverManager.getConnection(databaseUrl, prop);

            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, movie.getYear());
            preparedStatement.setString(2, movie.getDisplayName());

            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                return false;
            } else return true;

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
    public void dropMoviesTable() {
        String sql = "DROP TABLE MOVIES";

        try {

            conn = DriverManager.getConnection(databaseUrl, prop);

            preparedStatement = conn.prepareStatement(sql);
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
    public void createMoviesTable() {
        String sql = "CREATE TABLE `movies` (\n" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `Moviedisplayname` varchar(300) NOT NULL,\n" +
                "  `Movieyear` int(11) NOT NULL,\n" +
                "  `Moviefilesid` int(11) NOT NULL,\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=28684 DEFAULT CHARSET=utf8";

        try {

            conn = DriverManager.getConnection(databaseUrl, prop);

            preparedStatement = conn.prepareStatement(sql);
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
    public Movie selectMovie(int movieId) {
        String sql = "SELECT * FROM MOVIES WHERE id = ?";

        Movie movie = new Movie();

        try {

            conn = DriverManager.getConnection(databaseUrl, prop);

            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, movieId);


            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                movie.setMovieId(resultSet.getInt("id"));
                movie.setDisplayName(resultSet.getString("Moviedisplayname"));
                movie.setYear(resultSet.getInt("Movieyear"));
                movie.setFilesId(resultSet.getInt("Moviefilesid"));
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

        return movie;
    }
}
