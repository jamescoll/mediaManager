package wikiUtilities;



/**
 * Created by jcoll on 29/05/2014.
 */
public interface WikiDAO {


    //this method populates the filesystem class which contains folders and files
    public void insertPage(WikiPage page);

    //this method allows us to find a file by the db id
    public WikiPage findByTitle(String pageTitle);
}
