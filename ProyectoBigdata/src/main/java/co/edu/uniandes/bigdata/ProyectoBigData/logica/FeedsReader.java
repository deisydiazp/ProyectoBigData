/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.bigdata.ProyectoBigData.logica;

import com.saxonica.xqj.SaxonXQDataSource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultSequence;
import static org.apache.commons.io.FileUtils.readFileToString;

/**
 *
 * @author Rodrigo B
 */
public class FeedsReader {

    public static final String[] categories = {"Business", "Tech", "World"};
    public static final String ALL_CATEGORIES = "All";
    public static final int BUSINESS = 0;
    public static final int TECH = 1;
    public static final int WORLD = 2;

    public static final String[] sources = {"HP", "WSJ"};
    public static final int HP = 0;
    public static final int WSF = 1;

    // url RSS feeds [category, source, url]
    public static final String[][] URLS = {
        {categories[BUSINESS], sources[HP], "http://www.huffingtonpost.com/feeds/verticals/business/index.xml"},
        {categories[BUSINESS], sources[WSF], "http://www.wsj.com/xml/rss/3_7014.xml"},
        {categories[TECH], sources[HP], "http://www.huffingtonpost.com/feeds/verticals/technology/index.xml"},
        {categories[TECH], sources[WSF], "http://www.wsj.com/xml/rss/3_7455.xml"},
        {categories[WORLD], sources[HP], "http://www.huffingtonpost.com/feeds/verticals/world/index.xml"},
        {categories[WORLD], sources[WSF], "http://www.wsj.com/xml/rss/3_7085.xml"}};

    public static final int CATEGORY = 0;
    public static final int SOURCE = 1;
    public static final int URL = 2;

    private static final String PATH_FOLDER = System.getProperty("java.io.tmpdir").replaceAll("\\\\", "/");

    private List<Feed> feedsAvailable = null;

    public List<Feed> getFeedsAvailable() {
        return feedsAvailable;
    }

    public void setFeedsAvailable(List<Feed> feedsAvailable) {
        this.feedsAvailable = feedsAvailable;
    }

    public static String getXMLName(String xmlCategory, String xmlSource) {
        return xmlCategory + "_" + xmlSource;
    }

    public static String getXMLPath(String xmlName) {
        return PATH_FOLDER + "xmls/" + xmlName + ".xml";
    }

    public static String getXQPath(String xmlName) {
        return PATH_FOLDER + xmlName + ".xq";
    }

    private void loadXmlFeed(String xmlCategory, String xmlSource, String xmlUrl) {
        try {

            URL url = new URL(xmlUrl);
            String xmlName = getXMLName(xmlCategory, xmlSource);
            File file = new File(getXMLPath(xmlName));
            org.apache.commons.io.FileUtils.copyURLToFile(url, file);

        } catch (MalformedURLException ex) {
            Logger.getLogger(FeedsReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FeedsReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Limpia los titulos de valores no deseados como los retornados por el
     * HuffingtonPost
     */
    private String cleanTitle(String title) {
        return title.replace("<![CDATA[", "").replace("]]>", "");
    }

    private List<Feed> filterXquery(String category, String source, String property, String filterText, boolean excludes) throws IOException, XQException {

        String fileName = getXMLName(category, source);
        String xmlPath = getXMLPath(fileName);

        if (filterText == null) {
            filterText = "";
        }

        String exclude_xquery_strg_ini = "";
        String exclude_xquery_strg_end = "";
        if (excludes) {
            exclude_xquery_strg_ini = "not(";
            exclude_xquery_strg_end = ")";
        }

        String xq_query = "";

        if (property.equals("title_description")) { // create xQuery looking for title and description
            xq_query += "for $item in doc(\"" + xmlPath + "\")/rss/channel/item\n";
            xq_query += "where " + exclude_xquery_strg_ini
                    + "contains(upper-case($item/title),upper-case(\"" + filterText + "\"))"
                    + " or contains(upper-case($item/description),upper-case(\"" + filterText + "\"))"
                    + exclude_xquery_strg_end + "\n";
            xq_query += "return concat($item/data(title), '|', $item/data(pubDate), '|', $item/data(link))\n\n";
        } else { // create xQuery for property
            xq_query += "for $item in doc(\"" + xmlPath + "\")/rss/channel/item\n";
            xq_query += "where " + exclude_xquery_strg_ini
                    + "contains(upper-case($item/title),upper-case(\"" + filterText + "\"))"
                    + exclude_xquery_strg_end + "\n";
            xq_query += "return concat($item/data(title), '|', $item/data(pubDate), '|', $item/data(link))\n\n";
        }

        // create xq file for evidence
        File file = new File(getXQPath(fileName));
        FileWriter out = new FileWriter(file);
        out.write(xq_query);
        out.close();

        // executes XQuery
        XQDataSource ds = new SaxonXQDataSource();
        XQConnection conn = ds.getConnection();
        XQPreparedExpression exp = conn.prepareExpression(xq_query);
        XQResultSequence result = exp.executeQuery();

        // creates feeds List splitting values from xq result
        List<Feed> feeds = new ArrayList<>();

        while (result.next()) {
            String feedText = result.getItemAsString(null);
            String[] feed = feedText.split("\\|");
            String title = feed[0], pubDate = feed[1], urlLink = feed[2];
            feeds.add(new Feed(category, source, cleanTitle(title), urlLink, pubDate));
        }

        return feeds;

    }

    private List<Feed> filterRegex(String category, String source, String filterText, boolean excludes) throws IOException {

        File xmlFile = new File(getXMLPath(getXMLName(category, source)));

        String xmlString = readFileToString(xmlFile, StandardCharsets.UTF_8);

        // Get information inside Items
        Pattern regexItems = Pattern.compile("<item>[\\s\\S]*?<\\/item>");
        Matcher mItems = regexItems.matcher(xmlString);

        xmlString = "";
        while (mItems.find()) {
            xmlString += mItems.group(0);
        }

        // Get information inside titles
        Pattern regexTitles = Pattern.compile("(?:<title>)(.+)(?:<\\/title>)");
        Matcher mTitles = regexTitles.matcher(xmlString);

        // creates feeds List splitting values from regex result
        List<Feed> feeds = new ArrayList<>();

        while (mTitles.find()) {
            String title = mTitles.group(1);

            if (excludes) {
                if (filterText == null || !title.contains(filterText)) {
                    feeds.add(new Feed(category, source, cleanTitle(title), null, null));
                }
            } else if (filterText == null || title.contains(filterText)) {
                feeds.add(new Feed(category, source, cleanTitle(title), null, null));
            }
        }

        return feeds;
    }

    public void updateFeeds() {
        for (String[] feed : URLS) {
            loadXmlFeed(feed[CATEGORY], feed[SOURCE], feed[URL]);
        }
    }
    
    public List<Feed> getAllFeeds() throws IOException, XQException{
        return filterFeedsRegex(ALL_CATEGORIES, null, true);
    }

    public List<Feed> filterFeedsXquery(String category, String feedProperty, String filterText, boolean excludes) throws IOException, XQException {

        feedsAvailable = new ArrayList<>();

        for (String[] feed : URLS) {
            if (feed[CATEGORY].equals(category) || category.equals(ALL_CATEGORIES)) {
                feedsAvailable.addAll(filterXquery(feed[CATEGORY], feed[SOURCE], feedProperty, filterText, excludes));
            }
        }

        return feedsAvailable;
    }

    public List<Feed> filterFeedsRegex(String category, String filterText, boolean excludes) throws IOException, XQException {

        feedsAvailable = new ArrayList<>();

        for (String[] feed : URLS) {
            if (feed[CATEGORY].equals(category) || category.equals(ALL_CATEGORIES)) {
                feedsAvailable.addAll(filterRegex(feed[CATEGORY], feed[SOURCE], filterText, excludes));
            }
        }

        return feedsAvailable;
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public static void main(String[] args) throws MalformedURLException, IOException, XQException {

        FeedsReader test = new FeedsReader();
        test.updateFeeds();
        List<Feed> feeds = test.filterFeedsXquery(ALL_CATEGORIES, "title_description", "money", false);
        //List<Feed> feeds = test.filterFeedsRegex(ALL_CATEGORIES, "the ", true);

        for (Iterator<Feed> iterator = feeds.iterator(); iterator.hasNext();) {
            System.out.println(iterator.next().getTitle());
        }

    }

}
