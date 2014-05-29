package fileUtilities;

/**
 * The interface for inserting and deleting files and folders from the database
 * <p/>
 * Created by James on 29/05/2014.
 */
public interface FileDAO {

    //this method populates the filesystem class which contains folders and files
    public void insertFile(File file);

    //this method allows us to find a file by the db id
    public File findByFileId(int folderId);


}
