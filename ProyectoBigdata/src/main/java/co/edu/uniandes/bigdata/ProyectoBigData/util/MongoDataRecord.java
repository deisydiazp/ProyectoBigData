/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.bigdata.ProyectoBigData.util;

/**
 *
 * @author Rodrigo B
 */
public class MongoDataRecord {

    private String hashtag;
    private String user;
    private String date;
    private String sentiment;

    private Double tweets;
    private Double retweets;
    private Double followers;
    
    private String week;

    public MongoDataRecord(){
        //sin implementacion
    }
    
    public MongoDataRecord(String hashtag, String user, String date, String sentiment, Double tweets, Double retweets, Double followers) {
        this.hashtag = hashtag;
        this.user = user;
        this.date = date;
        this.sentiment = sentiment;
        this.tweets = tweets;
        this.retweets = retweets;
        this.followers = followers;
    }

    public String getHashtag() {
        return hashtag;
    }

    public String getUser() {
        return user;
    }

    public String getDate() {
        return date;
    }

    public String getSentiment() {
        return sentiment;
    }

    public Double getTweets() {
        return tweets;
    }

    public Double getRetweets() {
        return retweets;
    }

    public Double getFollowers() {
        return followers;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }

    public void setTweets(Double tweets) {
        this.tweets = tweets;
    }

    public void setRetweets(Double retweets) {
        this.retweets = retweets;
    }

    public void setFollowers(Double followers) {
        this.followers = followers;
    }
    
    
    
    public String getWeek(){
        week = "Semana ";
        int dia = Integer.parseInt(date);
        
        if(dia < 10){
            week += "1";
        }else if(dia > 10 && dia < 17){
            week += "2";
        }else if(dia > 17 && dia < 24){
            week += "3";
        }else if(dia > 24){
            week += "4";
        }
        
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}
