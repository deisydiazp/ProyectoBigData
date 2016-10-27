/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.bigdata.ProyectoBigData.logica;

import co.edu.uniandes.bigdata.ProyectoBigData.util.MongoDataRecord;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bson.Document;

/**
 *
 * @author Rodrigo B
 */
public class TwitterColombiaLogica {
    
    // BD Mongo
    public static final String DB_SERVER = "localhost";
    public static final int DB_PORT = 27017;
    public static final String DB_NAME = "Grupo10Twitter";
    public static final String COLLECTION_NAME = "OriginalData";

    private final MongoDatabase mongoDB;
    private final MongoCollection mongoCollection;
    
    private final List<MongoDataRecord> mongoResults = new ArrayList<>();

    public TwitterColombiaLogica() {

        // Mongo connection
        mongoDB = new MongoClient(DB_SERVER, DB_PORT).getDatabase(DB_NAME);
        if (mongoDB.getCollection(COLLECTION_NAME) == null) {
            mongoDB.createCollection(COLLECTION_NAME);
        }
        mongoCollection = mongoDB.getCollection(COLLECTION_NAME);

    }
    
    public void getTwitterDashboard(String tagString, String userString){
        
        if(tagString == null || tagString.equals("")){
            tagString = "\".\""; // regex para buscar todos los valores sin filtro
        }
        
        if(userString == null || userString.equals("")){
            userString = "\".\""; // regex para buscar todos los valores sin filtro
        }
        
        getMongoFunctionResults("getGeneralInfo(" + tagString + ", " + userString + ")");
        
    }
    
    public void getMongoFunctionResults(String functionStringWithParameters){
        Document docMongo = mongoDB.runCommand(new Document("$eval", functionStringWithParameters));
        String collectionResults = docMongo.getString("retval");
        
        mongoResults.clear();
        
        MongoCursor cursor = mongoDB.getCollection(collectionResults).find().iterator();
        
        while (cursor.hasNext()) {
            String resultString = cursor.next().toString();
            
            // obtener propiedades del resultado
            String hashtag = getValueFromMongoResult(resultString, "hashtag", false),
                user = getValueFromMongoResult(resultString, "user", false),
                date = getValueFromMongoResult(resultString, "date", false),
                sentiment = getValueFromMongoResult(resultString, "sentiment", false);
            
            float tweets = Float.parseFloat(getValueFromMongoResult(resultString, "totalTweets", true)), 
                retweets = Float.parseFloat(getValueFromMongoResult(resultString, "totalReTweet", true)), 
                followers = Float.parseFloat(getValueFromMongoResult(resultString, "totalFollowers", true));
            
            mongoResults.add(new MongoDataRecord(hashtag, user, date, sentiment, tweets, retweets, followers));
            
            System.out.println(hashtag + "|" + user + "|" +  date + "|" +  sentiment + "|" +  tweets + "|" +  retweets + "|" +  followers);
        }
    }
    
    public String getValueFromMongoResult(String mongoResult, String property, boolean returnsNumberIfNoExists){
        
        Pattern regexItems = Pattern.compile("(?s)(?<=" + property + "=).+?(?=,|})");
        Matcher mItems = regexItems.matcher(mongoResult);
        
        if (mItems.find()) {
            return mItems.group(0);
        }else{
            if(returnsNumberIfNoExists){
                return "0";
            }else{
                return "";
            }
        }
    }
    
}
