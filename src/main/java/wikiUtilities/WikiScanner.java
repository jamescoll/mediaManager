package wikiUtilities;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;


public class WikiScanner {


    public int wikiFound = 0;
    public int wikiUnfound = 0;

    public void doWikiLookup(String movieName) {


        //todo look at this for examples of how to strip first wikipedia article
        //https://stackoverflow.com/questions/8286786/wikipedia-first-paragraph
        //it seems that getting the url right will be tricky and involve case rules
        //returning the first paragraph on three test cases has worked though
        //getting this bit right is going to involve some work with text and parsing text


        movieName = convertNameToWikiFriendly(movieName);
        //this parses the dom so i can get at text by tags

        //first issue is with case
        //second will be with disambiguation...this can we dealt with by a look-ahead at whether the url refers a string containing 'this may refer to'
        //third will involve the insertion of (film) or (year_film) and checking the result...again if it doesn't contain disambig. text or a 404 it's probably good
        org.jsoup.nodes.Document doc = null;
        try {
            doc = Jsoup.connect("http://en.wikipedia.org/wiki/" + movieName).get();
            wikiFound++;
        } catch (IOException e) {
            System.out.println("***No wiki page present for standard lookup: " + movieName);
            System.out.println();
            return;
            //e.printStackTrace();

        }

        System.out.println("****Standard Look Up***** Using URL: " + "http://en.wikipedia.org/wiki/" + movieName);

        //adding a null pointer exception here for NOW to handle ungracefully the potential 404 nullpointer meh that
        //results from no page return here

        try {
          /*  org.jsoup.nodes.Element contentDiv = doc.select("p").first();
            org.jsoup.nodes.Element contentDiv1 = doc.select("table").first();
            String html = contentDiv.toString();
            String html1 = contentDiv1.toString();
            //this strips out the tags from the returned html
            System.out.println("***Description***");
            System.out.println(Jsoup.parse(html).text());
            System.out.println("***Details***");
            System.out.println(Jsoup.parse(html1).text());
            System.out.println();*/

            org.jsoup.nodes.Element summary = doc.select("table").first();

            System.out.println(summary.text());

            org.jsoup.select.Elements all = doc.getAllElements();
            for (Element elem : all) {
                if (elem.tagName() == "h2" || elem.tagName() == "p") {
                    System.out.println(elem.text());
                }
            }

        } catch (NullPointerException e) {
            //System.out.println("No wiki page present for this lookup");
        }
    }

    public void doWikiLookupMediumForm(String movieName) {
        //todo look at this for examples of how to strip first wikipedia article
        //https://stackoverflow.com/questions/8286786/wikipedia-first-paragraph
        //it seems that getting the url right will be tricky and involve case rules
        //returning the first paragraph on three test cases has worked though
        //getting this bit right is going to involve some work with text and parsing text


        //todo... in non-arseways code this would be written as an option which looked at the return value
        //and whether it was a 404 or disambig and then would reformat the request string
        movieName = convertNameToWikiFriendly(movieName);
        movieName = movieName + "_(film)";
        //this parses the dom so i can get at text by tags

        //first issue is with case
        //second will be with disambiguation...this can we dealt with by a look-ahead at whether the url refers a string containing 'this may refer to'
        //third will involve the insertion of (film) or (year_film) and checking the result...again if it doesn't contain disambig. text or a 404 it's probably good
        org.jsoup.nodes.Document doc = null;
        try {
            doc = Jsoup.connect("http://en.wikipedia.org/wiki/" + movieName).get();
            wikiFound++;
        } catch (IOException e) {
            System.out.println("***No wiki page present for medium lookup: " + movieName);
            System.out.println();
            return;
            //e.printStackTrace();

        }

        System.out.println("****Medium-form Look Up***** Using URL: " + "http://en.wikipedia.org/wiki/" + movieName);
        //adding a null pointer exception here for NOW to handle ungracefully the potential 404 nullpointer meh that
        //results from no page return here

        try {
           /* org.jsoup.select.Elements h2links = doc.select("h2");
            org.jsoup.select.Elements plinks = doc.select("p");
            for(Element elem: h2links) {
                System.out.println(elem.text());
            }


            for(Element elem: plinks){
                System.out.println(elem.)
                System.out.println(elem.text());
            }*/

            //here we are attempting to traverse the DOM nodes and just get h2 and p elements
            //this is because we want them in order so we can make some kind of sense of them in a db context
            //this will be a many-to-many relationship with a l-u table ultimately

            //we'll also want to get the first table on the page for the summary

            org.jsoup.nodes.Element summary = doc.select("table").first();

            System.out.println(summary.text());

            org.jsoup.select.Elements all = doc.getAllElements();
            for (Element elem : all) {
                if (elem.tagName() == "h2" || elem.tagName() == "p") {
                    System.out.println(elem.text());
                }
            }

            //todo this commented code was initially getting the first found paragraph for a given page
            //whereas above will get ps...however what is wanted is to get the description and the p together
            /*org.jsoup.nodes.Element contentDiv = doc.select("p").first();
            org.jsoup.nodes.Element contentDiv1 = doc.select("table").first();
            String html = contentDiv.toString();
            String html1 = contentDiv1.toString();
            //this strips out the tags from the returned html
            System.out.println("***Description***");
            System.out.println(Jsoup.parse(html).text());
            System.out.println("***Details***");
            System.out.println(Jsoup.parse(html1).text());
            System.out.println();*/

        } catch (NullPointerException e) {
            //System.out.println("No wiki page present for this lookup");
        }


    }


    public void doWikiLookupLongForm(String movieName, int year) {
        //todo look at this for examples of how to strip first wikipedia article
        //https://stackoverflow.com/questions/8286786/wikipedia-first-paragraph
        //it seems that getting the url right will be tricky and involve case rules
        //returning the first paragraph on three test cases has worked though
        //getting this bit right is going to involve some work with text and parsing text


        //todo... in non-arseways code this would be written as an option which looked at the return value
        //and whether it was a 404 or disambig and then would reformat the request string
        movieName = convertNameToWikiFriendly(movieName);
        movieName = movieName + "_(" + year + "_film)";
        //this parses the dom so i can get at text by tags

        //first issue is with case
        //second will be with disambiguation...this can we dealt with by a look-ahead at whether the url refers a string containing 'this may refer to'
        //third will involve the insertion of (film) or (year_film) and checking the result...again if it doesn't contain disambig. text or a 404 it's probably good
        org.jsoup.nodes.Document doc = null;
        try {
            doc = Jsoup.connect("http://en.wikipedia.org/wiki/" + movieName).get();
            wikiFound++;
        } catch (IOException e) {
            System.out.println("***No wiki page present for long lookup: " + movieName);
            System.out.println();
            return;

        }

        System.out.print("****Long-form Look Up***** Using URL: " + "http://en.wikipedia.org/wiki/" + movieName);
        //adding a null pointer exception here for NOW to handle ungracefully the potential 404 nullpointer meh that
        //results from no page return here

        try {


            org.jsoup.nodes.Element summary = doc.select("table").first();

            System.out.println(summary.text());

            org.jsoup.select.Elements all = doc.getAllElements();
            for (Element elem : all) {
                if (elem.tagName() == "h2" || elem.tagName() == "p") {
                    System.out.println(elem.text());
                }
            }

           /* org.jsoup.nodes.Element contentDiv = doc.select("p").first();
            org.jsoup.nodes.Element contentDiv1 = doc.select("table").first();
            String html = contentDiv.toString();
            String html1 = contentDiv1.toString();
            //this strips out the tags from the returned html
            System.out.println("***Description***");
            System.out.println(Jsoup.parse(html).text());
            System.out.println("***Details***");
            System.out.println(Jsoup.parse(html1).text());
            System.out.println();*/

        } catch (NullPointerException e) {
            //System.out.println("No wiki page present for this lookup");
        }
    }


    //todo this function will need to do a lot more than this very soon - see above comment

    private String convertNameToWikiFriendly(String movieName) {
        movieName = movieName.substring(1);
        movieName = movieName.replaceAll(" ", "_");
        movieName = movieName.replaceAll("_The_", "_the_");
        movieName = movieName.replaceAll("_Of_", "_of_");
        movieName = movieName.replaceAll("_A_", "_a_");
        //here we need to use a 'stopword' or preposition list to ensure they are lowercased unless in first position

        return movieName;
    }
}
