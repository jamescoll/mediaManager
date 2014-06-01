package serverApplication;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import servlets.ListMovieServlet;

import java.io.File;

//todo move web stuff (jsp) from resources into a more logical directory
//todo servlet should be elsewhere in its own package look into this

//remove test after backing up functionality (future date move to junit tests

public class Application {

    public static void main(String[] args) throws Exception {

        String webappDirLocation = "src/main/resources/";
        Tomcat tomcat = new Tomcat();


        //this code pwns the database...be careful

/*        JDBCFileDAO jd = new JDBCFileDAO();

        jd.dropFilesTable();

        jd.createFilesTable();


        JDBCMovieDAO md = new JDBCMovieDAO();

        md.dropMoviesTable();

        md.createMoviesTable();

        System.out.println("Dropped and recreated database tables");

        FileSystemScanner sfs = new FileSystemScanner();

        MovieScanner mScanner = new MovieScanner();


        mScanner.processMovies();


        System.out.println("Scanned files and movies - launching server"); */

        //The port that we should run on can be set into an environment variable
        //Look for that variable and default to 8080 if it isn't there.
        String webPort = System.getenv("PORT");
        if (webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }

        tomcat.setPort(Integer.valueOf(webPort));

        tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());
        System.out.println("configuring app with basedir: " + new File("./" + webappDirLocation).getAbsolutePath());
        File base = new File(System.getProperty("java.io.tmpdir"));
        Context rootCtx = tomcat.addContext("/mediaManager", base.getAbsolutePath());
        Tomcat.addServlet(rootCtx, "listMovieServlet", new ListMovieServlet());
        rootCtx.addServletMapping("/list", "listMovieServlet");

        tomcat.start();
        tomcat.getServer().await();


    }
}