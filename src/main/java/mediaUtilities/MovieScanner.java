package mediaUtilities;


import fileUtilities.File;
import fileUtilities.JDBCFileDAO;

import java.util.ArrayList;

/**
 * Created by jcoll on 30/05/2014.
 */
public class MovieScanner {

    private JDBCFileDAO jdbcFileDAO;
    private ArrayList<File> filesArrayList;


    public MovieScanner() {
        jdbcFileDAO = new JDBCFileDAO();
        //filesArrayList = new ArrayList<File>();
        setParameters();
    }

    private void setParameters() {

    }

    public void processMovies() {
        filesArrayList = jdbcFileDAO.selectAllMovies();
        ArrayList<String> titlesArrayList = new ArrayList<String>();

        for (File file : filesArrayList) {
            System.out.println(file.getName());
            titlesArrayList = processTitle(file.getName());
        }

        for (String title : titlesArrayList) {
            System.out.println(title);
        }
    }

    private ArrayList<String> processTitle(String filename) {

        ArrayList<String> titles = new ArrayList<String>();

        String title = "";
        String qualityMarker = "";

        int bracketStart = filename.indexOf('[');
        int bracketEnd = filename.indexOf(']');

        if (bracketStart != -1) {
            filename = filename.substring(bracketEnd + 1);
        }


        int lastDash = filename.lastIndexOf('-');
        int lastDot = filename.lastIndexOf('.');

        if (lastDash != -1) {

            qualityMarker = filename.substring(lastDash + 1, lastDot).trim();

        } else {
            title = filename.substring(0, lastDot).trim();

        }


        if (!qualityMarker.isEmpty()) {

            if (isAllUpper(qualityMarker)) {

                title = filename.substring(0, lastDot).trim();


            }
        }

        System.out.println("English title: " + title);

        System.out.println("Quality marker: " + qualityMarker);


        return titles;
    }


    //todo for now we are going to have to leave this unimplemented
    //but this moves towards getting at the foreign title from the folder name
    //the easiest approach here will be to use brackets for the foreign name

/*
    private ArrayList<String> processTitle(String filename) {

        ArrayList<String> titles = new ArrayList<String>();

        String processedEnglishTitle = "";
        String processedForeignTitle = "";
        String qualityMarker = "";

        int bracketStart = filename.indexOf('[');
        int bracketEnd = filename.indexOf(']');

        if(bracketStart != -1) {
            filename = filename.substring(bracketEnd + 1);
        }



        int lastDash = filename.lastIndexOf('-');
        int lastDot = filename.lastIndexOf('.');

        if(lastDash != -1) {
            processedEnglishTitle = filename.substring(0, lastDash).trim();
            processedForeignTitle = filename.substring(lastDash+1, lastDot).trim();
        } else {
            processedEnglishTitle = filename.substring(0, lastDot).trim();

        }



        if(!processedForeignTitle.isEmpty()) {

            if(isAllUpper(processedForeignTitle)) {

                qualityMarker = processedForeignTitle;
                processedForeignTitle = "";


            }
        }

        System.out.println("English title: " + processedEnglishTitle);
        System.out.println("Foreign title: " + processedForeignTitle);
        System.out.println("Quality marker: " + qualityMarker);



        return titles;
    }*/

    private boolean isAllUpper(String s) {
        for (char c : s.toCharArray()) {
            if (Character.isLetter(c) && Character.isLowerCase(c)) {
                return false;
            }
        }
        return true;
    }
}
