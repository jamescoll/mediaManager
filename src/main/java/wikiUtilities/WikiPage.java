package wikiUtilities;

import java.util.ArrayList;

/**
 * This class has been created to represent a wikipedia page. A web scraping service will be called in order to
 * populate an array of these pages and select those which should go into the database. A page will contain an arraylist
 * of sections which contain titles as
 * <p/>
 * Created by jcoll on 29/05/2014.
 */
public class WikiPage {

    String pageURL;
    String pageTitle;
    WikiSection introduction;
    //todo the summary will need to be processed in order to get database details...but this will be run elsewhere in the system
    //as the text by itself will be useful

    //todo these are two special cases of wiki pages which contain the introductory description
    //and the 'summary' of the data we may not need to separate them but it may be handy as we may use them everytime
    WikiSection summary;
    ArrayList<WikiSection> sections;

    public WikiPage() {
        sections = new ArrayList<WikiSection>();
    }

    public String getPageURL() {
        return pageURL;
    }

    public void setPageURL(String pageURL) {
        this.pageURL = pageURL;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public WikiSection getIntroduction() {
        return introduction;
    }

    public void setIntroduction(WikiSection introduction) {
        this.introduction = introduction;
    }


    public WikiSection getSummary() {
        return summary;
    }

    public void setSummary(WikiSection summary) {
        this.summary = summary;
    }

    public ArrayList<WikiSection> getSections() {
        return sections;
    }

    public void setSections(ArrayList<WikiSection> sections) {
        this.sections = sections;
    }


}
