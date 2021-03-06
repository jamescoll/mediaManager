package fileUtilities;

import mediaUtilities.Movie;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;


//todo fix bug in inserting SUBPROB and NOSUB

//todo add correct SQLException handling

//todo add check if already exists to create table method

/**
 * This is the JDBCFileDAO class for handling operations on the File table
 *
 * Created by James on 29/05/2014.
 */
public class JDBCFileDAO implements FileDAO {


    private final static Logger LOGGER = Logger.getLogger(JDBCFileDAO.class.getName());

    private Properties prop;

    private Connection conn = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;


    private String databaseUrl;

    public JDBCFileDAO() {
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

        String sql = "INSERT INTO FILES (Filetype, Filename, Filepath, Fileextension, Filequality, Filemovieid) VALUES (?, ?, ?, ?, ?, ?)";

        if (!fileInTable(file)) {

            try {

                conn = DriverManager.getConnection(databaseUrl, prop);

                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setInt(1, file.getType().ordinal());
                preparedStatement.setString(2, file.getName());
                preparedStatement.setString(3, file.getPath());
                preparedStatement.setString(4, file.getExtension());
                preparedStatement.setInt(5, file.getQuality().ordinal());
                preparedStatement.setInt(6, file.getMovieId());
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
            LOGGER.warning(file.getName() + " already exists in table");


        }

    }

    @Override
    public void deleteFile(File file) {

        String sql = "DELETE FROM FILES WHERE Filetype = ? AND Filename = ? AND Filepath = ? AND Fileextension = ? AND Filequality=? AND Filemovieid = ?";


        try {

            conn = DriverManager.getConnection(databaseUrl, prop);

            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, file.getType().ordinal());
            preparedStatement.setString(2, file.getName());
            preparedStatement.setString(3, file.getPath());
            preparedStatement.setString(4, file.getExtension());
            preparedStatement.setInt(5, file.getQuality().ordinal());
            preparedStatement.setInt(6, file.getMovieId());
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
    public void dropFilesTable() {
        String sql = "DROP TABLE FILES";

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

    public void createFilesTable() {
        String sql = "CREATE TABLE `files` (\n" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `Filetype` int(11) NOT NULL,\n" +
                "  `Filequality` int(11) NOT NULL,\n" +
                "  `Filepath` varchar(400) NOT NULL,\n" +
                "  `Filename` varchar(300) NOT NULL,\n" +
                "  `Fileextension` varchar(10) NOT NULL,\n" +
                "  `Filemovieid` int(11) NOT NULL,\n" +
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
    public ArrayList<File> selectAssociatedFiles(Movie m) {
        String sql = "SELECT * FROM FILES WHERE Filemovieid = ?";

        ArrayList<File> fileArrayList = new ArrayList<File>();

        try {

            conn = DriverManager.getConnection(databaseUrl, prop);

            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, m.getFilesId());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                File file = new File();
                file.setId(resultSet.getInt("id"));
                file.setType(mapIntToFiletype(resultSet.getInt("Filetype")));
                file.setName(resultSet.getString("Filename"));
                file.setPath(resultSet.getString("Filepath"));
                file.setExtension(resultSet.getString("Fileextension"));
                file.setQuality(mapIntToFilequality(resultSet.getInt("Filequality")));
                file.setMovieId(resultSet.getInt("Filemovieid"));
                fileArrayList.add(file);
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


        return fileArrayList;
    }

    @Override
    public void deleteFileById(int id) {

        String sql = "DELETE FROM FILES WHERE id = ?";


        try {

            conn = DriverManager.getConnection(databaseUrl, prop);

            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
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

            while (resultSet.next()) {
                file.setId(resultSet.getInt("id"));
                file.setType(mapIntToFiletype(resultSet.getInt("Filetype")));
                file.setName(resultSet.getString("Filename"));
                file.setPath(resultSet.getString("Filepath"));
                file.setExtension(resultSet.getString("Fileextension"));
                file.setQuality(mapIntToFilequality(resultSet.getInt("Filequality")));
                file.setMovieId(resultSet.getInt("Filemovieid"));
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

    @Override
    public ArrayList<File> findByFilename(String fileName) {
        String sql = "SELECT * FROM FILES WHERE Filename = ?";

        ArrayList<File> fileArrayList = new ArrayList<File>();

        try {

            conn = DriverManager.getConnection(databaseUrl, prop);

            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, fileName);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                File file = new File();
                file.setId(resultSet.getInt("id"));
                file.setType(mapIntToFiletype(resultSet.getInt("Filetype")));
                file.setName(resultSet.getString("Filename"));
                file.setPath(resultSet.getString("Filepath"));
                file.setExtension(resultSet.getString("Fileextension"));
                file.setQuality(mapIntToFilequality(resultSet.getInt("Filequality")));
                file.setMovieId(resultSet.getInt("Filemovieid"));
                fileArrayList.add(file);
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


        return fileArrayList;
    }

    @Override
    public ArrayList<File> selectAllMovies() {
        String sql = "SELECT * FROM FILES WHERE Filetype = 1";

        ArrayList<File> fileArrayList = new ArrayList<File>();

        try {

            conn = DriverManager.getConnection(databaseUrl, prop);

            preparedStatement = conn.prepareStatement(sql);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                File file = new File();
                file.setId(resultSet.getInt("id"));
                file.setType(mapIntToFiletype(resultSet.getInt("Filetype")));
                file.setName(resultSet.getString("Filename"));
                file.setPath(resultSet.getString("Filepath"));
                file.setExtension(resultSet.getString("Fileextension"));
                file.setQuality(mapIntToFilequality(resultSet.getInt("Filequality")));
                file.setMovieId(resultSet.getInt("Filemovieid"));
                fileArrayList.add(file);
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


        return fileArrayList;
    }

    @Override
    public ArrayList<File> selectAllFiles() {
        String sql = "SELECT * FROM FILES";

        ArrayList<File> fileArrayList = new ArrayList<File>();

        try {

            conn = DriverManager.getConnection(databaseUrl, prop);

            preparedStatement = conn.prepareStatement(sql);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                File file = new File();
                file.setId(resultSet.getInt("id"));
                file.setType(mapIntToFiletype(resultSet.getInt("Filetype")));
                file.setName(resultSet.getString("Filename"));
                file.setPath(resultSet.getString("Filepath"));
                file.setExtension(resultSet.getString("Fileextension"));
                file.setQuality(mapIntToFilequality(resultSet.getInt("Filequality")));
                file.setMovieId(resultSet.getInt("Filemovieid"));
                fileArrayList.add(file);
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


        return fileArrayList;
    }


    @Override
    public ArrayList<File> findByFiletype(Filetype fileType) {
        String sql = "SELECT * FROM FILES WHERE Filetype = ?";

        ArrayList<File> fileArrayList = new ArrayList<File>();

        try {

            conn = DriverManager.getConnection(databaseUrl, prop);

            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, fileType.ordinal());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                File file = new File();
                file.setId(resultSet.getInt("id"));
                file.setType(mapIntToFiletype(resultSet.getInt("Filetype")));
                file.setName(resultSet.getString("Filename"));
                file.setPath(resultSet.getString("Filepath"));
                file.setExtension(resultSet.getString("Fileextension"));
                file.setQuality(mapIntToFilequality(resultSet.getInt("Filequality")));
                file.setMovieId(resultSet.getInt("Filemovieid"));
                fileArrayList.add(file);
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


        return fileArrayList;
    }

    @Override
    public ArrayList<File> findByExtension(String extension) {
        String sql = "SELECT * FROM FILES WHERE Fileextension = ?";

        ArrayList<File> fileArrayList = new ArrayList<File>();

        try {

            conn = DriverManager.getConnection(databaseUrl, prop);

            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, extension);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                File file = new File();
                file.setId(resultSet.getInt("id"));
                file.setType(mapIntToFiletype(resultSet.getInt("Filetype")));
                file.setName(resultSet.getString("Filename"));
                file.setPath(resultSet.getString("Filepath"));
                file.setExtension(resultSet.getString("Fileextension"));
                file.setQuality(mapIntToFilequality(resultSet.getInt("Filequality")));
                file.setMovieId(resultSet.getInt("Filemovieid"));
                fileArrayList.add(file);
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


        return fileArrayList;
    }

    @Override
    public ArrayList<File> findByQuality(Filequality quality) {
        String sql = "SELECT * FROM FILES WHERE Filequality = ?";

        ArrayList<File> fileArrayList = new ArrayList<File>();

        try {

            conn = DriverManager.getConnection(databaseUrl, prop);

            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, quality.ordinal());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                File file = new File();
                file.setId(resultSet.getInt("id"));
                file.setType(mapIntToFiletype(resultSet.getInt("Filetype")));
                file.setName(resultSet.getString("Filename"));
                file.setPath(resultSet.getString("Filepath"));
                file.setExtension(resultSet.getString("Fileextension"));
                file.setQuality(mapIntToFilequality(resultSet.getInt("Filequality")));
                file.setMovieId(resultSet.getInt("Filemovieid"));
                fileArrayList.add(file);
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


        return fileArrayList;
    }

    @Override
    public boolean fileInTable(File file) {


        String sql = "SELECT * FROM FILES WHERE Filetype = ? AND Filename = ? AND Filepath = ? AND Fileextension = ? AND Filequality = ? AND Filemovieid = ?";

        try {

            conn = DriverManager.getConnection(databaseUrl, prop);

            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, file.getType().ordinal());
            preparedStatement.setString(2, file.getName());
            preparedStatement.setString(3, file.getPath());
            preparedStatement.setString(4, file.getExtension());
            preparedStatement.setInt(5, file.getQuality().ordinal());
            preparedStatement.setInt(6, file.getMovieId());
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


    private Filetype mapIntToFiletype(int filetypeAsInt) {

        switch (filetypeAsInt) {
            case 0:
                return Filetype.AUDIO;
            case 1:
                return Filetype.VIDEO;
            case 2:
                return Filetype.IMAGE;
            case 3:
                return Filetype.SUBTITLE;
            case 4:
                return Filetype.TEXT;
            case 5:
                return Filetype.ISO;
            case 6:
                return Filetype.OTHER;
            default:
                return Filetype.UNKNOWN;

        }
    }

    private Filequality mapIntToFilequality(int filequalityAsInt) {

        switch (filequalityAsInt) {
            case 0:
                return Filequality.NORMAL;
            case 1:
                return Filequality.SUBPROB;
            case 2:
                return Filequality.NOSUB;
            case 3:
                return Filequality.DUALAUDIO;
            case 4:
                return Filequality.WRONGLANG;
            case 5:
                return Filequality.LOWQUAL;
            default:
                return Filequality.NORMAL;

        }
    }


}
