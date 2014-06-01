package servlets;

import fileUtilities.File;
import fileUtilities.JDBCFileDAO;
import mediaUtilities.JDBCMovieDAO;
import mediaUtilities.Movie;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ListMovieServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(final HttpServletRequest request,
                         final HttpServletResponse response) throws ServletException, IOException {

        JDBCMovieDAO jdbcMovieDAO = new JDBCMovieDAO();
        JDBCFileDAO jdbcFileDAO = new JDBCFileDAO();

        ArrayList<Movie> movies = jdbcMovieDAO.selectAllMoviesSortedByYear();

        // Set response content type
        response.setContentType("text/html");

        // Actual logic goes here.
        PrintWriter out = response.getWriter();
        for (Movie m : movies) {
            out.println("<p>" + m.getYear() + " " + m.getDisplayName() + " " + m.getFilesId() + "</p>");
            ArrayList<File> files = jdbcFileDAO.selectAssociatedFiles(m);
            out.print("<p><i>Files</i>");
            for (File f : files) {

                out.println(f.getName() + "      " + f.getType());

            }
            out.print("</p>");
        }
    }
}