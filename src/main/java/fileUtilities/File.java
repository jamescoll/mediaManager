package fileUtilities;


/**
 * A class representing a file on the file system. This class will interact with a mysql database.
 * The path attribute represents the path below that path set in the config.properties file.
 * <p/>
 * Created by James on 28/05/2014.
 */
public class File {

    private Filetype type;
    private int id;
    private String name;
    private String path;
    private String extension;


    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }




    public Filetype getType() {
        return type;
    }

    public void setType(Filetype type) {
        this.type = type;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


}
