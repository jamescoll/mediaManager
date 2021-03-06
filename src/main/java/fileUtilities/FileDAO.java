package fileUtilities;

import mediaUtilities.Movie;

import java.util.ArrayList;

/**
 * The interface for inserting and deleting files and folders from the database
 * <p/>
 * Created by James on 29/05/2014.
 */
public interface FileDAO {

    //this method populates the filesystem class which contains folders and files
    public void insertFile(File file);

    //this method deletes a file from the database
    public void deleteFile(File file);

    //this method deletes a file by id from the database
    public void deleteFileById(int id);

    //this method allows us to find a file by the db id
    public File findByFileId(int folderId);

    //this method allows us to find files by the filename
    public ArrayList<File> findByFilename(String fileName);

    //this method allows us to find files by the filename
    public ArrayList<File> findByFiletype(Filetype fileType);

    //this method allows us to find files by the extension as string
    public ArrayList<File> findByExtension(String extension);

    //this method checks if a file is already in the table
    public boolean fileInTable(File file);

    //this method selects all files marked with filetype movie into an arraylist for processing
    public ArrayList<File> selectAllMovies();

    //this method selects all files into an arraylist for processing
    public ArrayList<File> selectAllFiles();

    //this method selects all files by quality
    public ArrayList<File> findByQuality(Filequality quality);

    //this method deletes the files table - use with care
    public void dropFilesTable();

    //this method creates the files table - use with care
    public void createFilesTable();

    //this method selects all files associated with a given movie
    public ArrayList<File> selectAssociatedFiles(Movie m);

}
