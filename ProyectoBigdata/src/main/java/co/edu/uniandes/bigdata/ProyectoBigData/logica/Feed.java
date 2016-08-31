/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.bigdata.ProyectoBigData.logica;

/**
 *
 * @author Rodrigo B
 */
public class Feed {
    
    private String category;
    private String source;
    private String title;
    private String url;
    private String pubDate;

    public Feed(String category, String source, String title, String url, String pubDate) {
        this.category = category;
        this.source = source;
        this.title = title;
        this.url = url;
        this.pubDate = pubDate;
    }
    
    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @return the pubDate
     */
    public String getPubDate() {
        return pubDate;
    }
    
}
